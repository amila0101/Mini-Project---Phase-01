package com.sm.travelapp.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sm.travelapp.data.Product
import java.text.NumberFormat
import java.util.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingScreen(
    product: Product,
    onNavigateBack: () -> Unit,
    onBookingComplete: () -> Unit,
    modifier: Modifier = Modifier
) {
    var numberOfPeople by remember { mutableStateOf("1") }
    var selectedDate by remember { mutableStateOf("") }
    var isDatePickerVisible by remember { mutableStateOf(false) }
    var isAnimated by remember { mutableStateOf(false) }
    var selectedPaymentMethod by remember { mutableStateOf<PaymentMethod?>(null) }
    var holderName by remember { mutableStateOf("") }
    var holderEmail by remember { mutableStateOf("") }
    var holderPhone by remember { mutableStateOf("") }

    val paymentMethods = remember {
        listOf(
            PaymentMethod("card", "Credit Card", Icons.Default.Star, "4242"),
            PaymentMethod("gpay", "Google Pay", Icons.Default.Place),
            PaymentMethod("paypal", "PayPal", Icons.Default.Settings)
        )
    }

    LaunchedEffect(Unit) {
        isAnimated = true
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Background gradient
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                            MaterialTheme.colorScheme.background
                        )
                    )
                )
        )

    Scaffold(
        topBar = {
                LargeTopAppBar(
                    title = {
                        Column {
                            Text(
                                "Book Your Trip",
                                style = MaterialTheme.typography.headlineMedium.copy(
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Text(
                                "Complete your booking details",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                            )
                        }
                    },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                        }
                    },
                    colors = TopAppBarDefaults.largeTopAppBarColors(
                        containerColor = Color.Transparent
                    )
                )
            }
        ) { padding ->
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Destination Card
                item {
                    AnimatedVisibility(
                        visible = isAnimated,
                        enter = fadeIn() + slideInHorizontally()
                    ) {
                        DestinationCard(product)
                    }
                }

                // Ticket Holder Card
                item {
                    AnimatedVisibility(
                        visible = isAnimated,
                        enter = fadeIn() + slideInHorizontally(
                            initialOffsetX = { it / 2 }
                        )
                    ) {
                        TicketHolderCard(
                            holderName = holderName,
                            holderEmail = holderEmail,
                            holderPhone = holderPhone,
                            onHolderNameChange = { holderName = it },
                            onHolderEmailChange = { holderEmail = it },
                            onHolderPhoneChange = { holderPhone = it }
                        )
                    }
                }

                // Booking Details Card
                item {
                    AnimatedVisibility(
                        visible = isAnimated,
                        enter = fadeIn() + slideInHorizontally(
                            initialOffsetX = { it / 3 }
                        )
                    ) {
                        BookingDetailsCard(
                            numberOfPeople = numberOfPeople,
                            onNumberOfPeopleChange = { numberOfPeople = it },
                            selectedDate = selectedDate,
                            onDateClick = { isDatePickerVisible = true }
                        )
                    }
                }

                // Price Summary Card
                item {
                    AnimatedVisibility(
                        visible = isAnimated,
                        enter = fadeIn() + slideInHorizontally(
                            initialOffsetX = { it / 4 }
                        )
                    ) {
                        PriceSummaryCard(
                            product = product,
                            numberOfPeople = numberOfPeople.toIntOrNull() ?: 1
                        )
                    }
                }

                // Payment Methods Card
                item {
                    AnimatedVisibility(
                        visible = isAnimated,
                        enter = fadeIn() + slideInHorizontally(
                            initialOffsetX = { it / 5 }
                        )
                    ) {
                        PaymentMethodsCard(
                            paymentMethods = paymentMethods,
                            selectedPaymentMethod = selectedPaymentMethod,
                            onPaymentMethodSelected = { selectedPaymentMethod = it }
                        )
                    }
                }

                // Book Now Button
                item {
                    AnimatedVisibility(
                        visible = isAnimated,
                        enter = fadeIn() + slideInVertically(
                            initialOffsetY = { it / 2 }
                        )
                    ) {
                        Button(
                            onClick = onBookingComplete,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            shape = RoundedCornerShape(12.dp),
                            enabled = selectedDate.isNotEmpty() && 
                                     numberOfPeople.isNotEmpty() && 
                                     selectedPaymentMethod != null &&
                                     holderName.isNotEmpty() &&
                                     holderEmail.isNotEmpty() &&
                                     holderPhone.isNotEmpty()
                        ) {
                            Icon(
                                Icons.Default.Check,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Confirm Booking")
                        }
                    }
                }
            }
        }
    }

    // Date Picker Dialog
    if (isDatePickerVisible) {
        DatePickerDialog(
            onDismissRequest = { isDatePickerVisible = false },
            onDateSelected = { 
                selectedDate = it
                isDatePickerVisible = false
            }
        )
    }
}

@Composable
private fun DestinationCard(product: Product) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column {
            AsyncImage(
                model = product.imageRes,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )
            
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = product.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
private fun TicketHolderCard(
    holderName: String,
    holderEmail: String,
    holderPhone: String,
    onHolderNameChange: (String) -> Unit,
    onHolderEmailChange: (String) -> Unit,
    onHolderPhoneChange: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                "Ticket Holder Details",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            // Name Field
            OutlinedTextField(
                value = holderName,
                onValueChange = onHolderNameChange,
                label = { Text("Full Name") },
                leadingIcon = {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier.fillMaxWidth()
            )

            // Email Field
            OutlinedTextField(
                value = holderEmail,
                onValueChange = onHolderEmailChange,
                label = { Text("Email") },
                leadingIcon = {
                    Icon(
                        Icons.Default.Email,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier.fillMaxWidth()
            )

            // Phone Field
            OutlinedTextField(
                value = holderPhone,
                onValueChange = onHolderPhoneChange,
                label = { Text("Phone Number") },
                leadingIcon = {
                    Icon(
                        Icons.Default.Phone,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun BookingDetailsCard(
    numberOfPeople: String,
    onNumberOfPeopleChange: (String) -> Unit,
    selectedDate: String,
    onDateClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                "Booking Details",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            // Number of People
            OutlinedTextField(
                value = numberOfPeople,
                onValueChange = { if (it.isEmpty() || it.toIntOrNull() != null) onNumberOfPeopleChange(it) },
                label = { Text("Number of People") },
                leadingIcon = {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            // Date Selection
            OutlinedCard(
                onClick = onDateClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.DateRange,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = selectedDate.ifEmpty { "Select Date" },
                        color = if (selectedDate.isEmpty()) 
                            MaterialTheme.colorScheme.onSurfaceVariant 
                        else 
                            MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}

@Composable
private fun PriceSummaryCard(
    product: Product,
    numberOfPeople: Int
) {
    val formatter = remember { NumberFormat.getCurrencyInstance() }
    val totalPrice = remember(numberOfPeople) { product.price * numberOfPeople }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                "Price Summary",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Price per person")
                Text(formatter.format(product.price))
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Number of people")
                Text("× $numberOfPeople")
            }

            Divider()

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Total Price",
                    fontWeight = FontWeight.Bold
                )
                Text(
                    formatter.format(totalPrice),
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun PaymentMethodsCard(
    paymentMethods: List<PaymentMethod>,
    selectedPaymentMethod: PaymentMethod?,
    onPaymentMethodSelected: (PaymentMethod) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                "Payment Method",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            paymentMethods.forEach { method ->
                PaymentMethodItem(
                    paymentMethod = method,
                    isSelected = method == selectedPaymentMethod,
                    onClick = { onPaymentMethodSelected(method) }
                )
            }

            // Add New Payment Method Button
            OutlinedButton(
                onClick = { /* Handle add payment method */ },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Add Payment Method")
            }
        }
    }
}

@Composable
private fun PaymentMethodItem(
    paymentMethod: PaymentMethod,
    isSelected: Boolean,
    onClick: () -> Unit
) {
            Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) 
                MaterialTheme.colorScheme.primaryContainer 
            else 
                MaterialTheme.colorScheme.surface
        ),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Payment Method Icon
            Surface(
                shape = CircleShape,
                color = if (isSelected) 
                    MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                else 
                    MaterialTheme.colorScheme.surfaceVariant
            ) {
                Icon(
                    imageVector = paymentMethod.icon,
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .padding(8.dp),
                    tint = if (isSelected) 
                        MaterialTheme.colorScheme.primary 
                    else 
                        MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = paymentMethod.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium
                )
                if (paymentMethod.last4.isNotEmpty()) {
                    Text(
                        text = "•••• ${paymentMethod.last4}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Selected",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
private fun DatePickerDialog(
    onDismissRequest: () -> Unit,
    onDateSelected: (String) -> Unit
) {
    // Implement your date picker dialog here
    // You can use a custom implementation or a library
}

private data class PaymentMethod(
    val id: String,
    val name: String,
    val icon: ImageVector,
    val last4: String = ""
) 