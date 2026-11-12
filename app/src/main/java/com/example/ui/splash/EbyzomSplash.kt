package com.example.ui.splash

import android.app.Activity
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import kotlinx.coroutines.delay

@Composable
fun EbyzomSplash(
  appName: String,
  tagline: String,
  logo: Painter,
  paper: Color,
  ink: Color,
  inkSoft: Color,
  onFinished: () -> Unit,
) {
  val view = LocalView.current
  if (!view.isInEditMode) {
    SideEffect {
      val window = (view.context as Activity).window
      WindowCompat.getInsetsController(window, view).apply {
        isAppearanceLightStatusBars = true
        isAppearanceLightNavigationBars = true
      }
    }
  }

  val alphaAnim = remember { Animatable(0f) }
  val scaleAnim = remember { Animatable(0.92f) }

  LaunchedEffect(Unit) {
    alphaAnim.animateTo(1f, animationSpec = tween(700))
    scaleAnim.animateTo(1f, animationSpec = tween(700))
    delay(1500)
    onFinished()
  }

  Box(
    Modifier
      .fillMaxSize()
      .background(paper)
      .statusBarsPadding()
      .clickable(onClick = onFinished),
    contentAlignment = Alignment.Center,
  ) {
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier
        .padding(horizontal = 16.dp)
        .alpha(alphaAnim.value)
        .scale(scaleAnim.value)
    ) {
      Image(
        painter = logo,
        contentDescription = appName,
        modifier = Modifier
          .fillMaxWidth(0.9f)
          .heightIn(min = 260.dp, max = 360.dp)
          .padding(horizontal = 12.dp),
        contentScale = ContentScale.Fit,
      )
      Spacer(Modifier.height(16.dp))
      Text(
        text = tagline,
        color = inkSoft,
        fontSize = 17.sp,
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.Medium,
        textAlign = TextAlign.Center,
        lineHeight = 24.sp,
        modifier = Modifier.padding(horizontal = 24.dp),
      )
    }
  }
}
