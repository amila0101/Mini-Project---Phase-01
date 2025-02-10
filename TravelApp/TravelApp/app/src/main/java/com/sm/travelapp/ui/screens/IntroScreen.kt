package com.sm.travelapp.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun IntroScreen(
    onIntroFinished: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    
    val introPages = listOf(
        IntroPage(
            icon = Icons.Default.Place,
            title = "Explore the World",
            description = "Discover amazing destinations and create unforgettable memories with our curated travel experiences",
            backgroundColor = Color(0xFF2196F3) // Material Blue
        ),
        IntroPage(
            icon = Icons.Default.LocationOn,
            title = "Plan Your Journey",
            description = "Find the perfect destinations, create custom itineraries, and get expert travel recommendations",
            backgroundColor = Color(0xFF4CAF50) // Material Green
        ),
        IntroPage(
            icon = Icons.Default.Star,
            title = "Travel Like a Pro",
            description = "Access insider tips, local guides, and exclusive deals to make your travel dreams a reality",
            backgroundColor = Color(0xFF9C27B0) // Material Purple
        )
    )

    val pagerState = rememberPagerState(pageCount = { introPages.size })
    var showLoadingScreen by remember { mutableStateOf(false) }

    if (showLoadingScreen) {
        LoadingScreen(onLoadingComplete = onIntroFinished)
    } else {
        Box(
            modifier = modifier.fillMaxSize()
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                IntroPage(
                    page = introPages[page],
                    modifier = Modifier.fillMaxSize()
                )
            }

            // Move indicators and button to separate Boxes
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 32.dp) // Increase bottom padding
            ) {
                // Next/Get Started button at the top
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 48.dp) // Wider padding for button
                ) {
                    Button(
                        onClick = {
                            if (pagerState.currentPage < introPages.size - 1) {
                                scope.launch {
                                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                                }
                            } else {
                                showLoadingScreen = true
                            }
                        },
                        shape = RoundedCornerShape(28.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = introPages[pagerState.currentPage].backgroundColor
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .padding(horizontal = 48.dp),
                        elevation = ButtonDefaults.elevatedButtonElevation(
                            defaultElevation = 8.dp,
                            pressedElevation = 4.dp
                        )
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = if (pagerState.currentPage < introPages.size - 1) 
                                    "Continue" 
                                else 
                                    "Get Started",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Icon(
                                imageVector = if (pagerState.currentPage < introPages.size - 1)
                                    Icons.Default.ArrowForward
                                else
                                    Icons.Default.Done,
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Page indicators below the button
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    repeat(introPages.size) { index ->
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 4.dp)
                                .size(if (pagerState.currentPage == index) 12.dp else 8.dp)
                                .clip(CircleShape)
                                .background(
                                    if (pagerState.currentPage == index)
                                        Color.White
                                    else
                                        Color.White.copy(alpha = 0.5f)
                                )
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun IntroPage(
    page: IntroPage,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(page.backgroundColor)
            .fillMaxSize()
    ) {
        // Background decoration
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            page.backgroundColor.copy(alpha = 0.8f),
                            page.backgroundColor
                        )
                    )
                )
        )

        // Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(80.dp))
            
            // Icon with animation
            Box(
                modifier = Modifier
                    .size(160.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.1f))
                    .padding(32.dp)
            ) {
                Icon(
                    imageVector = page.icon,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    tint = Color.White
                )
            }
            
            Spacer(modifier = Modifier.height(48.dp))
            
            // Title with animation
            Text(
                text = page.title,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 24.dp)
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Description with animation
            Text(
                text = page.description,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White.copy(alpha = 0.8f),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .fillMaxWidth(0.8f)
            )
        }
    }
}

private data class IntroPage(
    val icon: ImageVector,
    val title: String,
    val description: String,
    val backgroundColor: Color
) 