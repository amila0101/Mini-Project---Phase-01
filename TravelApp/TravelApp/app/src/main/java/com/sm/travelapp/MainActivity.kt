package com.sm.travelapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.sm.travelapp.data.Product
import com.sm.travelapp.data.ProductRepository
import com.sm.travelapp.ui.components.ProductGrid
import com.sm.travelapp.ui.screens.BookingScreen
import com.sm.travelapp.ui.screens.HomeScreen
import com.sm.travelapp.ui.screens.LoginScreen
import com.sm.travelapp.ui.screens.ProductDetailsScreen
import com.sm.travelapp.ui.screens.ProfileScreen
import com.sm.travelapp.ui.screens.RegisterScreen
import com.sm.travelapp.ui.screens.BookingSuccessScreen
import com.sm.travelapp.ui.theme.TravelAppTheme
import com.sm.travelapp.data.Profile
import com.sm.travelapp.data.BookedDestination
import com.sm.travelapp.data.BookingStatus
import com.sm.travelapp.ui.screens.AllDestinationsScreen
import com.sm.travelapp.ui.screens.AboutUsScreen
import com.sm.travelapp.ui.screens.ContactUsScreen
import com.sm.travelapp.ui.screens.LoadingScreen
import com.sm.travelapp.ui.components.BottomNavBar
import com.sm.travelapp.ui.screens.ServiceScreen
import com.sm.travelapp.ui.screens.ReviewScreen
import com.sm.travelapp.ui.screens.MapScreen
import com.sm.travelapp.ui.screens.IntroScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TravelAppTheme {
                var isLoggedIn by remember { mutableStateOf(false) }
                var isRegistering by remember { mutableStateOf(false) }
                var selectedProduct by remember { mutableStateOf<Product?>(null) }
                var isBooking by remember { mutableStateOf(false) }
                var isBookingSuccess by remember { mutableStateOf(false) }
                var isViewingProfile by remember { mutableStateOf(false) }
                var isViewingAllDestinations by remember { mutableStateOf(false) }
                var isViewingAboutUs by remember { mutableStateOf(false) }
                var isViewingContactUs by remember { mutableStateOf(false) }
                var isLoading by remember { mutableStateOf(true) }
                var currentRoute by remember { mutableStateOf("home") }
                var isViewingServices by remember { mutableStateOf(false) }
                var isViewingReviews by remember { mutableStateOf(false) }
                var isViewingMap by remember { mutableStateOf(false) }
                var showIntro by remember { mutableStateOf(true) }

                // Dummy profile data
                val profile = Profile(
                    name = "John Doe",
                    email = "john.doe@example.com"
                )
                
                // Dummy bookings data
                val bookings = remember {
                    listOf(
                        BookedDestination(
                            product = ProductRepository.products[0],
                            bookingDate = "2024-03-15",
                            numberOfPeople = 2,
                            totalPrice = 1999.98,
                            status = BookingStatus.UPCOMING
                        ),
                        BookedDestination(
                            product = ProductRepository.products[1],
                            bookingDate = "2024-02-20",
                            numberOfPeople = 1,
                            totalPrice = 1299.99,
                            status = BookingStatus.COMPLETED
                        )
                    )
                }

                if (showIntro) {
                    IntroScreen(
                        onIntroFinished = {
                            showIntro = false
                        }
                    )
                } else {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        bottomBar = {
                            if (isLoggedIn && !isBooking && !isBookingSuccess && selectedProduct == null) {
                                BottomNavBar(
                                    currentRoute = when {
                                        isViewingAllDestinations -> "destinations"
                                        isViewingProfile -> "profile"
                                        isViewingAboutUs -> "about"
                                        isViewingContactUs -> "contact"
                                        isViewingServices -> "services"
                                        else -> "home"
                                    },
                                    onNavigate = { route ->
                                        when (route) {
                                            "home" -> {
                                                isViewingProfile = false
                                                isViewingAboutUs = false
                                                isViewingContactUs = false
                                                isViewingAllDestinations = false
                                                isViewingServices = false
                                                currentRoute = "home"
                                            }
                                            "destinations" -> {
                                                isViewingProfile = false
                                                isViewingAboutUs = false
                                                isViewingContactUs = false
                                                isViewingAllDestinations = true
                                                isViewingServices = false
                                                currentRoute = "destinations"
                                            }
                                            "profile" -> {
                                                isViewingProfile = true
                                                isViewingAboutUs = false
                                                isViewingContactUs = false
                                                isViewingAllDestinations = false
                                                isViewingServices = false
                                                currentRoute = "profile"
                                            }
                                            "about" -> {
                                                isViewingProfile = false
                                                isViewingAboutUs = true
                                                isViewingContactUs = false
                                                isViewingAllDestinations = false
                                                isViewingServices = false
                                                currentRoute = "about"
                                            }
                                            "contact" -> {
                                                isViewingProfile = false
                                                isViewingAboutUs = false
                                                isViewingContactUs = true
                                                isViewingAllDestinations = false
                                                isViewingServices = false
                                                currentRoute = "contact"
                                            }
                                            "services" -> {
                                                isViewingProfile = false
                                                isViewingAboutUs = false
                                                isViewingContactUs = false
                                                isViewingAllDestinations = false
                                                isViewingServices = true
                                                currentRoute = "services"
                                            }
                                        }
                                    }
                                )
                            }
                        }
                    ) { innerPadding ->
                        when {
                            isLoading -> {
                                LoadingScreen(
                                    onLoadingComplete = { isLoading = false }
                                )
                            }
                            !isLoggedIn && !isRegistering -> {
                                LoginScreen(
                                    onLoginClick = { email, password ->
                                        // TODO: Implement actual login logic
                                        isLoggedIn = true
                                    },
                                    onRegisterClick = { isRegistering = true },
                                    modifier = Modifier.padding(innerPadding)
                                )
                            }
                            !isLoggedIn && isRegistering -> {
                                RegisterScreen(
                                    onRegisterClick = { name, email, password ->
                                        // TODO: Implement actual registration logic
                                        isLoggedIn = true
                                        isRegistering = false
                                    },
                                    onNavigateBack = { isRegistering = false },
                                    modifier = Modifier.padding(innerPadding)
                                )
                            }
                            isViewingProfile -> {
                                ProfileScreen(
                                    profile = profile,
                                    bookings = bookings,
                                    onNavigateBack = { 
                                        isViewingProfile = false
                                        currentRoute = "home"
                                    },
                                    onEditProfile = { /* Handle edit profile */ },
                                    onLogout = {
                                        isLoggedIn = false
                                        isViewingProfile = false
                                        currentRoute = "home"
                                    }
                                )
                            }
                            isBookingSuccess && selectedProduct != null -> {
                                BookingSuccessScreen(
                                    product = selectedProduct!!,
                                    onBackToHome = {
                                        selectedProduct = null
                                        isBookingSuccess = false
                                    }
                                )
                            }
                            isBooking && selectedProduct != null -> {
                                BookingScreen(
                                    product = selectedProduct!!,
                                    onNavigateBack = { isBooking = false },
                                    onBookingComplete = {
                                        isBooking = false
                                        isBookingSuccess = true
                                    }
                                )
                            }
                            selectedProduct != null -> {
                                ProductDetailsScreen(
                                    product = selectedProduct!!,
                                    onNavigateBack = { selectedProduct = null },
                                    onBookNowClick = { isBooking = true }
                                )
                            }
                            isViewingAllDestinations -> {
                                AllDestinationsScreen(
                                    products = ProductRepository.products,
                                    onProductClick = { product ->
                                        selectedProduct = product
                                        isViewingAllDestinations = false
                                        currentRoute = "home"
                                    },
                                    onNavigateBack = { 
                                        isViewingAllDestinations = false
                                        currentRoute = "home"
                                    }
                                )
                            }
                            isViewingAboutUs -> {
                                AboutUsScreen(
                                    onNavigateBack = { 
                                        isViewingAboutUs = false
                                        currentRoute = "home"
                                    }
                                )
                            }
                            isViewingContactUs -> {
                                ContactUsScreen(
                                    onNavigateBack = { 
                                        isViewingContactUs = false
                                        currentRoute = "home"
                                    }
                                )
                            }
                            isViewingServices -> {
                                ServiceScreen(
                                    onNavigateBack = { 
                                        isViewingServices = false
                                        currentRoute = "home"
                                    }
                                )
                            }
                            isViewingReviews -> {
                                ReviewScreen(
                                    onNavigateBack = { 
                                        isViewingReviews = false
                                        currentRoute = "home"
                                    }
                                )
                            }
                            isViewingMap -> {
                                MapScreen(
                                    onNavigateBack = { 
                                        isViewingMap = false
                                        currentRoute = "home"
                                    }
                                )
                            }
                            else -> {
                                HomeScreen(
                                    products = ProductRepository.products,
                                    profile = profile,
                                    onProductClick = { product -> selectedProduct = product },
                                    onProfileClick = { isViewingProfile = true },
                                    onShowAllClick = { isViewingAllDestinations = true },
                                    onAboutUsClick = { isViewingAboutUs = true },
                                    onContactUsClick = { isViewingContactUs = true },
                                    onServicesClick = { isViewingServices = true },
                                    onReviewsClick = { isViewingReviews = true },
                                    onMapClick = { isViewingMap = true },
                                    modifier = Modifier.padding(innerPadding)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}