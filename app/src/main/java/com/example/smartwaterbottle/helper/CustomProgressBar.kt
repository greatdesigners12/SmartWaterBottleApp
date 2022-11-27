package com.example.smartwaterbottle.helper

import androidx.compose.runtime.Composable
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.icu.text.DateTimePatternGenerator.DisplayWidth
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*

import java.util.*

// on below line we are creating a function for custom progress bar.
@Composable
fun customProgressBar(progress : MutableState<Float>) {
    // in this method we are creating a column
    Column(
        // in this column we are specifying modifier to
        // align the content within the column
        // to center of the screen.
        modifier = Modifier
            .fillMaxWidth(),

        // on below line we are specifying horizontal
        // and vertical alignment for the content of our column
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // in this column we are creating a variable
        // for the progress of our progress bar.


        // on the below line we are creating a box.
        Box(
            // inside this box we are adding a modifier
            // to add rounded clip for our box with
            // rounded radius at 15
            modifier = Modifier
                .clip(RoundedCornerShape(15.dp))
                // on below line we are specifying
                // height for the box
                .height(30.dp)

                // on below line we are specifying
                // background color for box.
                .background(Color(0xFFE2E3E4))

                // on below line we are
                // specifying width for the box.
                .width(300.dp)
        ) {
            // in this box we are creating one more box.
            Box(
                // on below line we are adding modifier to this box.
                modifier = Modifier
                    // on below line we are adding clip \
                    // for the modifier with round radius as 15 dp.
                    .clip(RoundedCornerShape(15.dp))

                    // on below line we are
                    // specifying height as 30 dp
                    .height(30.dp)

                    // on below line we are adding background
                    // color for our box as brush
                    .background(
                        // on below line we are adding brush for background color.
                        Brush.horizontalGradient(
                            // in this color we are specifying a gradient
                            // with the list of the colors.
                            listOf(
                                // on below line we are adding two colors.
                                Color(0xFF6DD6E4),
                                Color.Cyan
                            )
                        )
                    )
                    // on below line we are specifying width for the inner box
                    .width(300.dp * progress.value / 100)
            )
            // on below line we are creating a text for our box
            Text(
                // in text we are displaying a text as progress bar value.
                text = if(progress.value != -1f) "${progress.value} %" else "Waiting...",

                // on below line we are adding
                // a modifier to it as center.
                modifier = Modifier.align(Alignment.Center),

                // on below line we are adding
                // font size to it.
                fontSize = 15.sp,

                // on below line we are adding
                // font weight as bold.
                fontWeight = FontWeight.Bold,

                // on below line we are
                // specifying color for our text
                color = Color.White
            )
        }

    }
}
