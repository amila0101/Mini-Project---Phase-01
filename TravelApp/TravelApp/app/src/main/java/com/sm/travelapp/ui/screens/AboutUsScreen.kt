package com.sm.travelapp.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import com.sm.travelapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutUsScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(
                        "About Us",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                AboutSection()
            }

            item {
                TeamSection(
                    members = listOf(
                        TeamMember(
                            name = "B.M Manthila",
                            role = "CEO & Founder",
                            description = "15+ years in travel industry",
                            imageRes = R.drawable.profilebg
                        ),
                        TeamMember(
                            name = "I.I Maduwantha",
                            role = "Head of Corporate Travel",
                            description = "Expert in luxury travel",
                            imageRes = R.drawable.profilebg
                        ),
                        TeamMember(
                            name = "L.M Wickramarachchi",
                            role = "Senior Consultant",
                            description = "Passionate about service",
                            imageRes = R.drawable.profilebg
                        ),
                        TeamMember(
                            name = "M.L.Thathsara",
                            role = "Operations Head",
                            description = "Logistics specialist",
                            imageRes = R.drawable.profilebg
                        ),
                        TeamMember(
                            name = "K.G.D.A Buddhika (reZcipE)",
                            role = "Developer & Maintenance",
                            description = "Web Application Maintenance and Security",
                            imageRes = R.drawable.profilebg
                        )
                    )
                )
            }

            item {
                ContactSection()
            }
        }
    }
}

data class TeamMember(
    val name: String,
    val role: String,
    val description: String,
    val imageRes: Int = R.drawable.profilebg // Default profile image
)

@Composable
private fun TeamSection(members: List<TeamMember>) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Our Team",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        members.forEach { member ->
            TeamMemberCard(member = member)
        }
    }
}

@Composable
private fun TeamMemberCard(member: TeamMember) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Profile Image
            AsyncImage(
                model = member.imageRes,
                contentDescription = "Profile picture of ${member.name}",
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = member.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                
                Text(
                    text = member.role,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.primary
                )
                
                Text(
                    text = member.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun AboutSection() {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "About Travel App",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = "Travel App is your ultimate companion for discovering and planning amazing travel experiences. Our team of dedicated professionals works tirelessly to bring you the best travel content and features.",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
private fun ContactSection() {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Contact Us",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = "Have questions or feedback? We'd love to hear from you! Reach out to us through any of the following channels:",
            style = MaterialTheme.typography.bodyLarge
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ContactItem(
                icon = Icons.Default.Email,
                text = "support@travelapp.com"
            )
            ContactItem(
                icon = Icons.Default.Phone,
                text = "+1 (555) 123-4567"
            )
            ContactItem(
                icon = Icons.Default.Place,
                text = "123 Travel Street, Adventure City, AC 12345"
            )
        }
    }
}

@Composable
private fun ContactItem(
    icon: ImageVector,
    text: String
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium
        )
    }
} 