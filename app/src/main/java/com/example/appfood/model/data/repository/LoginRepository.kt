package com.example.appfood.model.data.repository

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import com.example.appfood.R
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.OAuthProvider
import kotlinx.coroutines.tasks.await

class LoginRepository(
    private val auth: FirebaseAuth,
    private val credentialManager: CredentialManager
) {

    suspend fun signInWithEmail(email: String, password: String): FirebaseUser? {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            val user = result.user
            if (user != null && user.isEmailVerified) user else {
                auth.signOut()
                null
            }
        } catch (e: Exception) {
            Log.w(TAG, "signInWithEmail:failure", e)
            null
        }
    }

    suspend fun createAccount(email: String, password: String): FirebaseUser? {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val user = result.user
            user?.sendEmailVerification()?.await()
            user
        } catch (e: Exception) {
            Log.w(TAG, "createAccount:failure", e)
            null
        }
    }

    suspend fun signInWithGoogle(context: Context): FirebaseUser? {
        val googleIdOption = GetGoogleIdOption.Builder()
            .setServerClientId(context.getString(R.string.default_web_client_id))
            .setFilterByAuthorizedAccounts(false)
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        return try {
            val result = credentialManager.getCredential(context, request)
            val credential = result.credential
            if (credential is CustomCredential && credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                val idToken = googleIdTokenCredential.idToken
                val authCredential = GoogleAuthProvider.getCredential(idToken, null)
                val authResult = auth.signInWithCredential(authCredential).await()
                authResult.user
            } else {
                Log.w(TAG, "Credential is not of type Google ID!")
                null
            }
        } catch (e: Exception) {
            Log.e(TAG, "Google Sign-In failed: ${e.message}", e)
            null
        }
    }

    suspend fun signInWithApple(context: Context): FirebaseUser? {
        return try {
            val provider = OAuthProvider.newBuilder("apple.com")
                .addCustomParameter("locale", "en")
                .setScopes(listOf("email", "name"))
                .build()

            val result = auth.startActivityForSignInWithProvider(context as Activity, provider).await()
            result.user
        } catch (e: Exception) {
            Log.e(TAG, "Apple Sign-In failed: ${e.message}", e)
            null
        }
    }

    fun getCurrentUser(): FirebaseUser? = auth.currentUser

    fun signOut() {
        auth.signOut()
    }

    companion object {
        private const val TAG = "LoginRepository"
    }
    suspend fun deleteAccount(): Boolean {
        return try {
            auth.currentUser?.delete()?.await()
            true
        } catch (e: Exception) {
            Log.e(TAG, "Account deletion failed: ${e.message}")
            false
        }
    }

}