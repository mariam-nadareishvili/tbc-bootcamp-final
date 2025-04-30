package com.tbc.bookli.core.ui.components

import android.graphics.Typeface
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.yml.charts.common.model.PlotType
import co.yml.charts.ui.piechart.charts.PieChart
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData
import com.tbc.bookli.core.ui.model.BookUi

@Composable
fun RotatingCirclePieChart(books: List<BookUi>) {
    val genreChartData = remember { prepareGenreChartData(books) }

    val angle by rememberInfiniteTransition(label = "").animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(20000, easing = LinearEasing)
        ),
        label = ""
    )

    val pieChartConfig = PieChartConfig(
        isAnimationEnable = true,
        sliceLabelTextSize = 16.sp,
        sliceLabelTypeface = Typeface.SANS_SERIF,
        isClickOnSliceEnabled = true,
        showSliceLabels = true,
        sliceLabelTextColor = Color.Black,
        animationDuration = 1000,
        backgroundColor = MaterialTheme.colorScheme.background
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(genreChartData.genres) { genre ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(16.dp)
                                .background(genre.color, shape = RoundedCornerShape(4.dp))
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "${genre.name} (${genre.count})",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(64.dp))

        Box(
            modifier = Modifier
                .size(320.dp)
                .graphicsLayer {
                    rotationZ = angle
                }
                .align(Alignment.CenterHorizontally),
            contentAlignment = Alignment.Center
        ) {
            PieChart(
                modifier = Modifier.fillMaxSize(),
                pieChartData = genreChartData.pieChartData,
                pieChartConfig = pieChartConfig
            )
        }
    }
}

data class GenreChartItem(
    val name: String,
    val count: Int,
    val color: Color
)

data class GenreChartData(
    val pieChartData: PieChartData,
    val genres: List<GenreChartItem>
)

fun prepareGenreChartData(books: List<BookUi>): GenreChartData {
    val genreFrequencyMap = books
        .flatMap { it.genres }
        .groupingBy { it.name }
        .eachCount()

    val genreItems = genreFrequencyMap.map { (genreName, count) ->
        val color = generateRandomColors()
        GenreChartItem(
            name = genreName,
            count = count,
            color = color
        )
    }

    val pieChartData = PieChartData(
        slices = genreItems.map {
            PieChartData.Slice(
                label = it.name,
                value = it.count.toFloat(),
                color = it.color
            )
        },
        plotType = PlotType.Pie
    )

    return GenreChartData(pieChartData, genreItems)
}

fun generateRandomColors(): Color {
    return Color(red = (0..255).random(), (0..255).random(), (0..255).random())
}