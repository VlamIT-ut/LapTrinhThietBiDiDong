package com.example.appfood.view.ui.screens.map

import android.location.Geocoder
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.appfood.R
import com.example.appfood.viewModel.LocationViewModel
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.osmdroid.util.GeoPoint
import java.util.Locale

@Suppress("DEPRECATION")
@Composable
fun DeliveryLocationScreen(
    navController: NavController,
    locationViewModel: LocationViewModel = viewModel()
) {
    val context = LocalContext.current
    var searchQuery by remember { mutableStateOf("") }
    remember { LocationServices.getFusedLocationProviderClient(context) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        locationViewModel.loadAddressFromFirebase()
    }

    Scaffold(
        modifier = Modifier.statusBarsPadding(),
        topBar = {
            Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                Text(
                   stringResource(R.string.address),
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = {
                        searchQuery = it
                        locationViewModel.searchAddressSuggestions(context, searchQuery)
                    },
                    label = { Text(stringResource(R.string.search_address)) },
                    modifier = Modifier.fillMaxWidth()
                )
                if (locationViewModel.suggestions.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 150.dp) // giới hạn chiều cao gợi ý
                            .background(Color.White)
                            .border(1.dp, Color.Gray)
                    ) {
                        items(locationViewModel.suggestions) { suggestion ->
                            Text(
                                text = suggestion,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        searchQuery = suggestion
                                        coroutineScope.launch(Dispatchers.IO) {
                                            val geocoder = Geocoder(context, Locale("vi", "VN"))
                                            val results = geocoder.getFromLocationName(suggestion, 1)
                                            if (!results.isNullOrEmpty()) {
                                                val loc = results[0]
                                                val geoPoint = GeoPoint(loc.latitude, loc.longitude)
                                                locationViewModel.updateSelectedPoint(geoPoint)
                                                locationViewModel.updateSelectedAddress(suggestion)
                                                locationViewModel.setMapCenter(geoPoint)
                                            }
                                        }
                                        locationViewModel.suggestions = emptyList()
                                    }
                                    .padding(8.dp)
                            )
                        }
                    }
                }
            }
        },
        bottomBar = {
            Surface(tonalElevation = 4.dp, modifier = Modifier.navigationBarsPadding()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(onClick = {
                        val defaultPoint = GeoPoint(10.762622, 106.660172)
                        locationViewModel.updateSelectedPoint(defaultPoint)
                        locationViewModel.updateSelectedAddress("Tâm bản đồ mặc định")
                        locationViewModel.setMapCenter(defaultPoint)
                    }) {
                        Text(stringResource(R.string.reset_map))
                    }
                    Button(onClick = {
                        locationViewModel.saveAddressToFirebase { success ->
                            // Có thể báo Toast nếu muốn
                        }
                        navController.popBackStack()
                    }) {
                        Text(stringResource(R.string.confirm_location))
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(8.dp)
            ) {
                OsmMapSelector(locationViewModel = locationViewModel)
            }

            locationViewModel.selectedPoint?.let {
                Text("Selected: ${it.latitude}, ${it.longitude}", modifier = Modifier.padding(16.dp))
            }
            locationViewModel.selectedAddress?.let {
                Text("Address: $it", modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp))
            }
        }

        LaunchedEffect(searchQuery) {
            delay(400)
            if (searchQuery.length > 3) {
                locationViewModel.searchAddressSuggestions(context, searchQuery)
            }
        }
    }
}
