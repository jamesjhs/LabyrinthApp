package com.jahosi.labyrinth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jahosi.labyrinth.ui.theme.LabyrinthTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            LabyrinthTheme {
                LabyrinthLaunchScreen()
            }
        }
    }
}

@Composable
fun LabyrinthLaunchScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(LabyrinthColors.Background)
            .background(
                Brush.radialGradient(
                    colors = listOf(
                        LabyrinthColors.Panel,
                        LabyrinthColors.Background,
                        Color.Black
                    ),
                    radius = 980f
                )
            )
            .safeDrawingPadding()
            .padding(horizontal = 24.dp, vertical = 20.dp)
    ) {
        LabyrinthGridBackdrop(Modifier.matchParentSize())

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            AppStatusStrip()

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                BasicText(
                    text = "LABYRINTH",
                    style = LabyrinthTypography.Display.copy(
                        color = LabyrinthColors.Primary,
                        textAlign = TextAlign.Center
                    )
                )
                BasicText(
                    text = "Native Android skeleton",
                    style = LabyrinthTypography.Title.copy(
                        color = LabyrinthColors.TextSecondary,
                        textAlign = TextAlign.Center
                    )
                )
            }

            RendererPlaceholder(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.45f)
            )

            BasicText(
                modifier = Modifier.fillMaxWidth(),
                text = "Gameplay is not implemented yet.",
                style = LabyrinthTypography.Body.copy(
                    color = LabyrinthColors.TextSecondary,
                    textAlign = TextAlign.Center
                )
            )
        }
    }
}

@Composable
private fun AppStatusStrip(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasicText(
            text = "v${BuildConfig.VERSION_NAME}",
            style = LabyrinthTypography.Label.copy(color = LabyrinthColors.TextSecondary)
        )
        BasicText(
            text = "Skeleton only",
            style = LabyrinthTypography.Label.copy(
                color = LabyrinthColors.Accent,
                fontWeight = FontWeight.SemiBold
            )
        )
    }
}

@Composable
private fun RendererPlaceholder(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .border(
                width = 1.dp,
                brush = Brush.linearGradient(
                    listOf(LabyrinthColors.Primary, LabyrinthColors.Accent)
                ),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(1.dp)
            .background(
                color = LabyrinthColors.Panel.copy(alpha = 0.7f),
                shape = RoundedCornerShape(8.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val step = size.minDimension / 7f
            val stroke = Stroke(width = 2f)

            for (index in 1..6) {
                val line = index * step
                drawLine(
                    color = LabyrinthColors.Primary.copy(alpha = 0.18f),
                    start = Offset(line, 0f),
                    end = Offset(line, size.height),
                    strokeWidth = stroke.width
                )
                drawLine(
                    color = LabyrinthColors.Accent.copy(alpha = 0.14f),
                    start = Offset(0f, line),
                    end = Offset(size.width, line),
                    strokeWidth = stroke.width
                )
            }

            drawRect(
                color = LabyrinthColors.Primary.copy(alpha = 0.28f),
                topLeft = Offset(step, step),
                size = androidx.compose.ui.geometry.Size(step * 4f, step),
                style = stroke
            )
            drawRect(
                color = LabyrinthColors.Accent.copy(alpha = 0.25f),
                topLeft = Offset(step * 2f, step * 3f),
                size = androidx.compose.ui.geometry.Size(step * 3f, step * 2f),
                style = stroke
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BasicText(
                text = "Renderer area",
                style = LabyrinthTypography.PanelTitle.copy(
                    color = LabyrinthColors.TextPrimary,
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.height(6.dp))
            BasicText(
                text = "Canvas proof arrives in Phase 4",
                style = LabyrinthTypography.Body.copy(color = LabyrinthColors.TextSecondary)
            )
        }
    }
}

@Composable
private fun LabyrinthGridBackdrop(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val columns = 10
        val rows = 14
        val xStep = size.width / columns
        val yStep = size.height / rows

        repeat(columns + 1) { index ->
            val x = index * xStep
            drawLine(
                color = LabyrinthColors.Primary.copy(alpha = 0.06f),
                start = Offset(x, 0f),
                end = Offset(x, size.height),
                strokeWidth = 1f
            )
        }

        repeat(rows + 1) { index ->
            val y = index * yStep
            drawLine(
                color = LabyrinthColors.Accent.copy(alpha = 0.05f),
                start = Offset(0f, y),
                end = Offset(size.width, y),
                strokeWidth = 1f
            )
        }
    }
}

private object LabyrinthColors {
    val Background = Color(0xFF030609)
    val Panel = Color(0xFF071418)
    val Primary = Color(0xFF45FFE6)
    val Accent = Color(0xFFFF4FD8)
    val TextPrimary = Color(0xFFEAF7F5)
    val TextSecondary = Color(0xFF9AB8BD)
}

private object LabyrinthTypography {
    val Display = TextStyle(
        fontWeight = FontWeight.Black,
        fontSize = 44.sp,
        lineHeight = 50.sp,
        letterSpacing = 0.sp
    )
    val PanelTitle = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    )
    val Title = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.sp
    )
    val Body = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.sp
    )
    val Label = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.sp
    )
}
