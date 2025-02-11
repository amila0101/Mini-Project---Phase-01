package com.sm.travelapp.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.clickable
import androidx.compose.ui.layout.ContentScale
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.ui.viewinterop.AndroidView
import android.annotation.SuppressLint
import coil.compose.AsyncImage
import com.sm.travelapp.R
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.Spring
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.MarkerState
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.animation.core.*
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.animation.fadeIn

private const val API_KEY = "79aaf7a5e1e542eeb9306a1ad2204556"

@SuppressLint("SetJavaScriptEnabled")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    var selectedContinent by remember { mutableStateOf<String?>(null) }
    var searchQuery by remember { mutableStateOf("") }
    var selectedCountry by remember { mutableStateOf<Country?>(null) }

    val continents = remember {
        listOf(
            Continent("Asia", listOf(
                Country("Japan", "Tokyo", 35.6762, 139.6503),
                Country("South Korea", "Seoul", 37.5665, 126.9780),
                Country("Thailand", "Bangkok", 13.7563, 100.5018),
                Country("Singapore", "Singapore", 1.3521, 103.8198),
                Country("Vietnam", "Hanoi", 21.0285, 105.8542)
            )),
            Continent("Europe", listOf(
                Country("France", "Paris", 48.8566, 2.3522),
                Country("Italy", "Rome", 41.9028, 12.4964),
                Country("Spain", "Madrid", 40.4168, -3.7038),
                Country("Germany", "Berlin", 52.5200, 13.4050),
                Country("Netherlands", "Amsterdam", 52.3676, 4.9041)
            )),
            Continent("North America", listOf(
                Country("USA", "Washington D.C.", 38.9072, -77.0369),
                Country("Canada", "Ottawa", 45.4215, -75.6972),
                Country("Mexico", "Mexico City", 19.4326, -99.1332),
                Country("Costa Rica", "San José", 9.9281, -84.0907),
                Country("Panama", "Panama City", 8.9824, -79.5199)
            )),
            Continent("South America", listOf(
                Country("Brazil", "Brasília", -15.7975, -47.8919),
                Country("Argentina", "Buenos Aires", -34.6037, -58.3816),
                Country("Peru", "Lima", -12.0464, -77.0428),
                Country("Colombia", "Bogotá", 4.7110, -74.0721)
            )),
            Continent("Africa", listOf(
                Country("South Africa", "Pretoria", -25.7479, 28.2293),
                Country("Egypt", "Cairo", 30.0444, 31.2357),
                Country("Morocco", "Rabat", 34.0209, -6.8416),
                Country("Kenya", "Nairobi", -1.2921, 36.8219)
            )),
            Continent("Oceania", listOf(
                Country("Australia", "Canberra", -35.2809, 149.1300),
                Country("New Zealand", "Wellington", -41.2866, 174.7756),
                Country("Fiji", "Suva", -18.1416, 178.4419)
            ))
        )
    }

    val mapHtml = remember {
        """
        <!DOCTYPE html>
        <html>
        <head>
            <link rel="stylesheet" href="https://unpkg.com/leaflet@1.7.1/dist/leaflet.css" />
            <script src="https://unpkg.com/leaflet@1.7.1/dist/leaflet.js"></script>
            <style>
                #map { height: 100%; width: 100%; }
                body { margin: 0; padding: 0; height: 100vh; }
            </style>
        </head>
        <body>
            <div id="map"></div>
            <script>
                var map = L.map('map').setView([20, 0], 2);
                
                // Use OpenStreetMap tiles instead
                L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
                    attribution: '© OpenStreetMap contributors',
                    maxZoom: 19
                }).addTo(map);
                
                var marker;
                function updateMarker(lat, lng) {
                    if (marker) {
                        map.removeLayer(marker);
                    }
                    marker = L.marker([lat, lng]).addTo(map);
                    map.setView([lat, lng], 6);
                }
            </script>
        </body>
        </html>
        """
    }

    // Update default camera position
    val defaultLocation = LatLng(20.0, 0.0) // Center of the world map
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(defaultLocation, 2f)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            SmallTopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(
                        onClick = onNavigateBack,
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f),
                                shape = CircleShape
                            )
                            .padding(4.dp)
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                    }
                },
                actions = {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 16.dp),
                        placeholder = { Text("Search destinations...") },
                        leadingIcon = {
                            Icon(Icons.Default.Search, contentDescription = null)
                        },
                        trailingIcon = if (searchQuery.isNotEmpty()) {
                            {
                                IconButton(onClick = { searchQuery = "" }) {
                                    Icon(Icons.Default.Clear, "Clear")
                                }
                            }
                        } else null,
                        singleLine = true,
                        shape = RoundedCornerShape(24.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f),
                            unfocusedBorderColor = MaterialTheme.colorScheme.surface
                        )
                    )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Map
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                properties = MapProperties(
                    isMyLocationEnabled = false,
                    mapType = MapType.NORMAL,
                    isTrafficEnabled = false
                ),
                uiSettings = MapUiSettings(
                    zoomControlsEnabled = true,
                    myLocationButtonEnabled = false,
                    mapToolbarEnabled = false,
                    compassEnabled = true,
                    zoomGesturesEnabled = true,
                    scrollGesturesEnabled = true,
                    rotationGesturesEnabled = true,
                    tiltGesturesEnabled = true
                )
            ) {
                // Add markers for popular destinations
                continents.forEach { continent ->
                    continent.countries.forEach { country ->
                        MarkerInfoWindow(
                            state = MarkerState(position = LatLng(country.latitude, country.longitude)),
                            title = country.name,
                            snippet = country.capital
                        )
                    }
                }
            }

            // Bottom sheet with destinations
            AnimatedVisibility(
                visible = true,
                enter = slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = tween(300, easing = FastOutSlowInEasing)
                ),
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                    color = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f),
                    tonalElevation = 8.dp
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        // Animated handle indicator
                        val handleAlpha by rememberInfiniteTransition(label = "handle").animateFloat(
                            initialValue = 0.4f,
                            targetValue = 0.8f,
                            animationSpec = infiniteRepeatable(
                                animation = tween(1000),
                                repeatMode = RepeatMode.Reverse
                            ),
                            label = "handle alpha"
                        )
                        
                        Box(
                            modifier = Modifier
                                .width(40.dp)
                                .height(4.dp)
                                .clip(RoundedCornerShape(2.dp))
                                .background(
                                    MaterialTheme.colorScheme.onSurfaceVariant
                                        .copy(alpha = handleAlpha)
                                )
                                .align(Alignment.CenterHorizontally)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Title with fade in animation
                        var titleVisible by remember { mutableStateOf(false) }
                        LaunchedEffect(Unit) {
                            titleVisible = true
                        }
                        AnimatedVisibility(
                            visible = titleVisible,
                            enter = fadeIn(tween(500, delayMillis = 300))
                        ) {
                            Text(
                                "Popular Destinations",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Animated LazyColumn
                        val listScale by rememberInfiniteTransition(label = "list").animateFloat(
                            initialValue = 0.98f,
                            targetValue = 1f,
                            animationSpec = infiniteRepeatable(
                                animation = tween(2000),
                                repeatMode = RepeatMode.Reverse
                            ),
                            label = "list scale"
                        )

                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp)
                                .graphicsLayer {
                                    scaleX = listScale
                                    scaleY = listScale
                                },
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(
                                items = continents,
                                key = { it.name }
                            ) { continent ->
                                ModernContinentCard(
                                    continent = continent,
                                    isExpanded = selectedContinent == continent.name,
                                    selectedCountry = selectedCountry,
                                    onExpandClick = {
                                        selectedContinent = if (selectedContinent == continent.name) null else continent.name
                                    },
                                    onCountryClick = { country ->
                                        selectedCountry = country
                                        scope.launch {
                                            cameraPositionState.animate(
                                                update = CameraUpdateFactory.newLatLngZoom(
                                                    LatLng(country.latitude, country.longitude),
                                                    10f
                                                ),
                                                durationMs = 1000
                                            )
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ModernContinentCard(
    continent: Continent,
    isExpanded: Boolean,
    selectedCountry: Country?,
    onExpandClick: () -> Unit,
    onCountryClick: (Country) -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onExpandClick)
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.primaryContainer,
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            getContinentIcon(continent.name),
                            contentDescription = null,
                            modifier = Modifier.padding(8.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    Column {
                        Text(
                            text = continent.name,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${continent.countries.size} destinations",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                Icon(
                    imageVector = if (isExpanded) 
                        Icons.Default.KeyboardArrowUp 
                    else 
                        Icons.Default.KeyboardArrowDown,
                    contentDescription = if (isExpanded) "Collapse" else "Expand"
                )
            }

            if (isExpanded) {
                Divider(modifier = Modifier.padding(horizontal = 8.dp))
                Column(
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    continent.countries.forEach { country ->
                        ModernCountryItem(
                            country = country,
                            isSelected = selectedCountry == country,
                            onClick = { onCountryClick(country) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ModernCountryItem(
    country: Country,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 8.dp, vertical = 4.dp),
        color = animateColorAsState(
            targetValue = if (isSelected) 
                MaterialTheme.colorScheme.primaryContainer 
            else 
                Color.Transparent,
            label = "background"
        ).value
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = CircleShape,
                color = if (isSelected)
                    MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                else
                    MaterialTheme.colorScheme.surfaceVariant,
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Place,
                    contentDescription = null,
                    modifier = Modifier.padding(12.dp),
                    tint = if (isSelected)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Column {
                Text(
                    text = country.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = country.capital,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Lat: ${country.latitude}, Long: ${country.longitude}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun getContinentIcon(continentName: String): ImageVector {
    return when (continentName) {
        "Asia" -> Icons.Default.Place
        "Europe" -> Icons.Default.LocationOn
        "North America" -> Icons.Default.Place
        "South America" -> Icons.Default.LocationOn
        "Africa" -> Icons.Default.Place
        "Oceania" -> Icons.Default.LocationOn
        else -> Icons.Default.Place
    }
}

private data class Continent(
    val name: String,
    val countries: List<Country>
)

private data class Country(
    val name: String,
    val capital: String,
    val latitude: Double,
    val longitude: Double
) 