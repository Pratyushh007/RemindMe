package com.pratyush.remainderapp.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pratyush.remainderapp.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun IntroScreen(
    onIntroFinished: () -> Unit
) {
    // Animation states
    var showLogo by remember { mutableStateOf(false) }
    var showTitle by remember { mutableStateOf(false) }
    var showSubtitle by remember { mutableStateOf(false) }
    var showParticles by remember { mutableStateOf(false) }
    var fadeOutAll by remember { mutableStateOf(false) }

    // Continuous animations
    val infiniteTransition = rememberInfiniteTransition(label = "intro_infinite")

    // Floating animation for logo
    val floatOffset by infiniteTransition.animateFloat(
        initialValue = -8f,
        targetValue = 8f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = EaseInOutCubic),
            repeatMode = RepeatMode.Reverse
        ),
        label = "float"
    )

    // Rotating glow
    val glowRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(6000, easing = LinearEasing)
        ),
        label = "glow_rotation"
    )

    // Pulsing scale for rings
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 0.9f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = EaseInOutCubic),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    // Particle positions
    val particle1Y by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing)
        ),
        label = "particle1"
    )
    val particle2Y by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing)
        ),
        label = "particle2"
    )
    val particle3Y by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(2500, easing = LinearEasing)
        ),
        label = "particle3"
    )

    // Logo scale animation
    val logoScale by animateFloatAsState(
        targetValue = if (showLogo) 1f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "logo_scale"
    )

    // Overall fade out
    val fadeAlpha by animateFloatAsState(
        targetValue = if (fadeOutAll) 0f else 1f,
        animationSpec = tween(500, easing = EaseOutCubic),
        label = "fade_out"
    )

    // Sequence the animations
    LaunchedEffect(Unit) {
        delay(300)
        showParticles = true
        delay(200)
        showLogo = true
        delay(600)
        showTitle = true
        delay(400)
        showSubtitle = true
        delay(2500) // Wait for the rest of the 4 seconds
        fadeOutAll = true
        delay(500)
        onIntroFinished()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .alpha(fadeAlpha)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        PurpleDark,
                        PurplePrimary,
                        PurpleSecondary,
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        // Animated background particles
        if (showParticles) {
            Canvas(
                modifier = Modifier.fillMaxSize()
            ) {
                val w = size.width
                val h = size.height

                // Floating orbs
                drawCircle(
                    color = Color.White.copy(alpha = 0.06f),
                    radius = 120f,
                    center = Offset(w * 0.15f, h * particle1Y)
                )
                drawCircle(
                    color = Color.White.copy(alpha = 0.04f),
                    radius = 80f,
                    center = Offset(w * 0.8f, h * particle2Y)
                )
                drawCircle(
                    color = Color.White.copy(alpha = 0.05f),
                    radius = 60f,
                    center = Offset(w * 0.5f, h * particle3Y)
                )
                drawCircle(
                    color = Color.White.copy(alpha = 0.03f),
                    radius = 150f,
                    center = Offset(w * 0.7f, h * 0.3f)
                )
                drawCircle(
                    color = Color.White.copy(alpha = 0.04f),
                    radius = 100f,
                    center = Offset(w * 0.2f, h * 0.7f)
                )

                // Rotating gradient ring
                rotate(glowRotation, pivot = Offset(w / 2, h / 2)) {
                    drawCircle(
                        brush = Brush.sweepGradient(
                            colors = listOf(
                                Color.White.copy(alpha = 0.08f),
                                Color.Transparent,
                                Color.White.copy(alpha = 0.05f),
                                Color.Transparent,
                                Color.White.copy(alpha = 0.08f),
                            )
                        ),
                        radius = 250f * pulseScale,
                        center = Offset(w / 2, h / 2)
                    )
                }
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp)
                .offset(y = floatOffset.dp)
        ) {
            // Glowing backdrop behind icon
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .scale(logoScale)
            ) {
                // Outer glow ring
                Box(
                    modifier = Modifier
                        .size(140.dp)
                        .scale(pulseScale)
                        .alpha(0.3f)
                        .blur(20.dp)
                        .clip(RoundedCornerShape(35.dp))
                        .background(Color.White)
                )

                // Icon container
                Box(
                    modifier = Modifier
                        .size(110.dp)
                        .clip(RoundedCornerShape(28.dp))
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    PurpleVibrant,
                                    PurplePrimary,
                                    PurpleDark,
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    // Inner subtle gradient overlay
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = Brush.radialGradient(
                                    colors = listOf(
                                        Color.White.copy(alpha = 0.2f),
                                        Color.Transparent
                                    ),
                                    radius = 200f
                                )
                            )
                    )
                    Icon(
                        imageVector = Icons.Outlined.Email,
                        contentDescription = "RemindMe Logo",
                        modifier = Modifier.size(56.dp),
                        tint = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(36.dp))

            // App Title
            AnimatedVisibility(
                visible = showTitle,
                enter = fadeIn(tween(600)) + slideInVertically(
                    initialOffsetY = { 40 },
                    animationSpec = tween(600, easing = EaseOutCubic)
                )
            ) {
                Text(
                    text = "RemindMe",
                    style = MaterialTheme.typography.displayLarge.copy(
                        fontSize = 42.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = (-1).sp
                    ),
                    color = Color.White,
                    textAlign = TextAlign.Center,
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Subtitle
            AnimatedVisibility(
                visible = showSubtitle,
                enter = fadeIn(tween(600)) + slideInVertically(
                    initialOffsetY = { 30 },
                    animationSpec = tween(600, easing = EaseOutCubic)
                )
            ) {
                Text(
                    text = "Never forget what matters",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 17.sp,
                        letterSpacing = 0.5.sp
                    ),
                    color = Color.White.copy(alpha = 0.8f),
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(60.dp))

            // Loading dots animation
            AnimatedVisibility(
                visible = showSubtitle,
                enter = fadeIn(tween(400, delayMillis = 300))
            ) {
                LoadingDots()
            }
        }
    }
}

@Composable
private fun LoadingDots() {
    val infiniteTransition = rememberInfiniteTransition(label = "dots")

    val dot1Alpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(600),
            repeatMode = RepeatMode.Reverse,
            initialStartOffset = StartOffset(0)
        ),
        label = "dot1"
    )
    val dot2Alpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(600),
            repeatMode = RepeatMode.Reverse,
            initialStartOffset = StartOffset(200)
        ),
        label = "dot2"
    )
    val dot3Alpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(600),
            repeatMode = RepeatMode.Reverse,
            initialStartOffset = StartOffset(400)
        ),
        label = "dot3"
    )

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        listOf(dot1Alpha, dot2Alpha, dot3Alpha).forEach { alpha ->
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .alpha(alpha)
                    .clip(RoundedCornerShape(50))
                    .background(Color.White)
            )
        }
    }
}
