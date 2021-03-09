/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androiddevchallenge.ui.theme.MyTheme

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyTheme {
                MyApp()
            }
        }
    }
}

// Start building your app here!
@Composable
fun MyApp() {
    val DEFAULT_SECS = 10L * 1000L
    val timerVM = TimerVM(DEFAULT_SECS)
    val cntDownTmr = TimerCountDown(timerVM)
    val howManySecs: Long by timerVM.msecsRemaining.observeAsState(DEFAULT_SECS)
    var timerRunning by rememberSaveable { mutableStateOf(false) }

    Surface(color = MaterialTheme.colors.background, modifier = Modifier.fillMaxWidth(1.0f)) {
        Column(modifier = Modifier.padding(8.dp)) {
            ClockDisplay(howManySec = howManySecs)
            Spacer(modifier = Modifier.padding(8.dp))
            if (!timerRunning) {
                Button(
                    modifier = Modifier.fillMaxWidth(1.0f),
                    onClick = {
                        Log.d("TIMER", "Start pressed")
                        if (!timerRunning) {
                            timerRunning = true
                            cntDownTmr.start()
                        }
                    }
                ) {
                    Text(text = "START")
                }
            }
        }
    }
}

// Start building your app here!
@Composable
fun ClockDisplay(howManySec: Long) {
    val textToShow = if (howManySec > 0) "$howManySec seconds" else "Finished!"

    val transition = updateTransition(targetState = howManySec)
    val dpText = transition.animateDp() {
        if (it % 1000L == 0L) 24.dp else 8.dp
    }

    val backgroundColor = transition.animateColor() {
        if (it % 1000L == 0L) Color.Yellow else Color.Cyan
    }

    Crossfade(targetState = howManySec, animationSpec = tween(3000)) {
        if (howManySec > 0) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(1.0f)
                    .padding(dpText.value, dpText.value, dpText.value, dpText.value)
                    .background(backgroundColor.value, RoundedCornerShape(10)),
                text = textToShow,
                textAlign = TextAlign.Center
            )
        } else {
            Text(
                modifier = Modifier
                    .fillMaxWidth(1.0f)
                    .background(Color.Green, RoundedCornerShape(10)),
                text = textToShow,
                textAlign = TextAlign.Center, fontSize = 32.sp
            )
        }
    }
}

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    MyTheme {
        MyApp()
    }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    MyTheme(darkTheme = true) {
        MyApp()
    }
}
