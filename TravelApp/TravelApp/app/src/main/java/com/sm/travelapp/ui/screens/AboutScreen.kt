package com.sm.travelapp.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.sm.travelapp.R
import kotlinx.coroutines.delay
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.LocationOn

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isLoaded by remember { mutableStateOf(false) }
    
    LaunchedEffect(Unit) {
        delay(100)
        isLoaded = true
    }

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(
                        onClick = onNavigateBack,
                        modifier = Modifier
                            .padding(8.dp)
                            .background(
                                MaterialTheme.colorScheme.surface.copy(alpha = 0.9f),
                                CircleShape
                            )
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // App Logo Animation
            AnimatedVisibility(
                visible = isLoaded,
                enter = fadeIn() + expandVertically()
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_launcher_foreground),
                    contentDescription = "App Logo",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primaryContainer)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // App Title Animation
            AnimatedVisibility(
                visible = isLoaded,
                enter = fadeIn(
                    initialAlpha = 0f,
                    animationSpec = tween(durationMillis = 1000, delayMillis = 300)
                ) + slideInVertically(
                    initialOffsetY = { 50 },
                    animationSpec = tween(durationMillis = 1000, delayMillis = 300)
                )
            ) {
                Text(
                    text = "Traveller.com",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Version Number Animation
            AnimatedVisibility(
                visible = isLoaded,
                enter = fadeIn(
                    initialAlpha = 0f,
                    animationSpec = tween(durationMillis = 1000, delayMillis = 500)
                )
            ) {
                Text(
                    text = "Version 1.0.0",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Features Section
            AnimatedFeaturesList(isLoaded = isLoaded)

            Spacer(modifier = Modifier.height(32.dp))

            // Contact Section Animation
            AnimatedVisibility(
                visible = isLoaded,
                enter = fadeIn(
                    initialAlpha = 0f,
                    animationSpec = tween(durationMillis = 1000, delayMillis = 1000)
                ) + expandVertically(
                    animationSpec = tween(durationMillis = 1000, delayMillis = 1000)
                )
            ) {
                ContactSection()
            }
        }
    }
}

@Composable
private fun AnimatedFeaturesList(isLoaded: Boolean) {
    val features = listOf(
        Triple(Icons.Default.Place, "Explore Destinations", "Discover amazing places around the world"),
        Triple(Icons.Default.LocationOn, "Interactive Maps", "Navigate with ease using our detailed maps"),
        Triple(Icons.Default.Favorite, "Save Favorites", "Keep track of your favorite destinations"),
        Triple(Icons.Default.Info, "Travel Info", "Get detailed information about each location")
    )

    features.forEachIndexed { index, (icon, title, description) ->
        AnimatedVisibility(
            visible = isLoaded,
            enter = fadeIn(
                initialAlpha = 0f,
                animationSpec = tween(durationMillis = 1000, delayMillis = 700 + (index * 100))
            ) + slideInHorizontally(
                initialOffsetX = { 50 },
                animationSpec = tween(durationMillis = 1000, delayMillis = 700 + (index * 100))
            )
        ) {
            FeatureItem(icon, title, description)
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun FeatureItem(icon: ImageVector, title: String, description: String) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun ContactSection() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Contact Us",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        ContactItem(Icons.Default.Email, "support@traveller.com")
        ContactItem(Icons.Default.Phone, "+1 234 567 890")
        ContactItem(Icons.Default.LocationOn, "123 Travel Street, World")
    }
}

@Composable
private fun ContactItem(icon: ImageVector, text: String) {
    Row(
        modifier = Modifier.padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
} 