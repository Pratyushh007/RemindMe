package com.pratyush.remainderapp.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pratyush.remainderapp.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var showContent by remember { mutableStateOf(false) }
    var showForm by remember { mutableStateOf(false) }
    var showFooter by remember { mutableStateOf(false) }

    // Animate entrance
    LaunchedEffect(Unit) {
        delay(100)
        showContent = true
        delay(300)
        showForm = true
        delay(200)
        showFooter = true
    }

    // Subtle background animation
    val infiniteTransition = rememberInfiniteTransition(label = "login_bg")
    val bgOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(8000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "bg_shift"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        PurplePrimary.copy(alpha = 0.08f + bgOffset * 0.04f),
                        OffWhite,
                        White,
                    )
                )
            )
    ) {
        // Subtle decorative circles in background
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(
                color = PurpleLight.copy(alpha = 0.3f),
                radius = 200f,
                center = Offset(size.width * 0.9f, size.height * 0.1f)
            )
            drawCircle(
                color = PurpleLight.copy(alpha = 0.15f),
                radius = 150f,
                center = Offset(size.width * 0.1f, size.height * 0.85f)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(80.dp))

            // Logo + Header section
            AnimatedVisibility(
                visible = showContent,
                enter = fadeIn(tween(600)) + slideInVertically(
                    initialOffsetY = { -40 },
                    animationSpec = tween(600, easing = EaseOutCubic)
                )
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // App Icon
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        // Soft shadow glow
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .alpha(0.25f)
                                .blur(16.dp)
                                .clip(RoundedCornerShape(26.dp))
                                .background(PurplePrimary)
                        )

                        Box(
                            modifier = Modifier
                                .size(88.dp)
                                .shadow(
                                    elevation = 12.dp,
                                    shape = RoundedCornerShape(24.dp),
                                    spotColor = PurplePrimary.copy(alpha = 0.3f)
                                )
                                .clip(RoundedCornerShape(24.dp))
                                .background(
                                    brush = Brush.linearGradient(
                                        colors = listOf(
                                            PurpleVibrant,
                                            PurplePrimary,
                                        )
                                    )
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Email,
                                contentDescription = "RemindMe",
                                modifier = Modifier.size(44.dp),
                                tint = Color.White
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(28.dp))

                    Text(
                        text = "Welcome Back",
                        style = MaterialTheme.typography.displayMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = PurplePrimary,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Sign in to continue to RemindMe",
                        style = MaterialTheme.typography.bodyLarge,
                        color = TextSecondary,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            // Form section
            AnimatedVisibility(
                visible = showForm,
                enter = fadeIn(tween(500)) + slideInVertically(
                    initialOffsetY = { 50 },
                    animationSpec = tween(500, easing = EaseOutCubic)
                )
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Email Address",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        color = TextPrimary,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    // Email Input
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        placeholder = {
                            Text(
                                "you@example.com",
                                color = DarkGray.copy(alpha = 0.5f)
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(58.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color.Transparent,
                            focusedBorderColor = PurplePrimary,
                            unfocusedContainerColor = LightGray,
                            focusedContainerColor = LightGray.copy(alpha = 0.7f),
                            cursorColor = PurplePrimary,
                        ),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = { onLoginSuccess() }
                        )
                    )

                    Spacer(modifier = Modifier.height(28.dp))

                    // Continue Button
                    Button(
                        onClick = { onLoginSuccess() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(58.dp)
                            .shadow(
                                elevation = 8.dp,
                                shape = RoundedCornerShape(16.dp),
                                spotColor = PurplePrimary.copy(alpha = 0.4f)
                            ),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent
                        ),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    brush = Brush.horizontalGradient(
                                        colors = listOf(
                                            BlueAccent,
                                            PurplePrimary,
                                            PurpleVibrant,
                                        )
                                    )
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "Continue",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.SemiBold
                                    ),
                                    color = Color.White
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                    contentDescription = "Continue",
                                    tint = Color.White,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Sign up link
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Don't have an account? Sign up",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Medium
                            ),
                            color = PurplePrimary,
                            modifier = Modifier.clickable { /* Sign up action */ }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Footer
            AnimatedVisibility(
                visible = showFooter,
                enter = fadeIn(tween(500, delayMillis = 200))
            ) {
                Text(
                    text = "By continuing, you agree to our Terms & Privacy Policy",
                    style = MaterialTheme.typography.labelMedium,
                    color = TextSecondary.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 32.dp)
                )
            }
        }
    }
}
