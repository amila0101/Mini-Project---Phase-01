package com.sm.travelapp.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun BottomNavBar(
    currentRoute: String,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier
    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = currentRoute == "home",
            onClick = { onNavigate("home") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Place, contentDescription = "Destinations") },
            label = { Text("Destinations") },
            selected = currentRoute == "destinations",
            onClick = { onNavigate("destinations") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Info, contentDescription = "About") },
            label = { Text("About") },
            onClick = { onNavigate("about") },
            selected = currentRoute == "about"
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Email, contentDescription = "Contact") },
            label = { Text("Contact") },
            onClick = { onNavigate("contact") },
            selected = currentRoute == "contact"
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
            label = { Text("Profile") },
            selected = currentRoute == "profile",
            onClick = { onNavigate("profile") }
        )
    }
} 