package com.rodrigodominguez.mixanimationsjetpackcompose

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.rodrigodominguez.mixanimationsjetpackcompose.starbucksdemo.CardDemoComposable
import com.rodrigodominguez.mixanimationsjetpackcompose.starbucksdemo.StarbucksDemo

@ExperimentalMaterialApi
class MainActivity : AppCompatActivity() {
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(color = MaterialTheme.colors.background) {
                    StarbucksDemo()
                    //CardDemoComposable()
                }
            }
        }
    }


    @Preview(showBackground = true, name = "Strabucks preview")
    @Composable
    fun DefaultPreview() {
        MaterialTheme {
            //StarbucksDemo()
            CardDemoComposable()
        }
    }
}

