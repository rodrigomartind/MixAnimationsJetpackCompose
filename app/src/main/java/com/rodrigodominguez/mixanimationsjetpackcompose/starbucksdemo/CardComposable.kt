package com.rodrigodominguez.mixanimationsjetpackcompose.starbucksdemo

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.rodrigodominguez.mixanimationsjetpackcompose.R
import com.rodrigodominguez.mixanimationsjetpackcompose.ui.Color0CardBlur
import com.rodrigodominguez.mixanimationsjetpackcompose.ui.Color1CardBlur
import com.rodrigodominguez.mixanimationsjetpackcompose.ui.Color2CardBlur

@Composable
fun CardDemoComposable() {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color0CardBlur)
    ) {
        val (cardGlass, shape0, shape1, logo, contactless, chip, number, name, amount) = createRefs()

        val guideline = createGuidelineFromTop(fraction = 0.5f)

        Box(modifier = Modifier
            .height(200.dp)
            .width(200.dp)
            .clip(CircleShape)
            .background(Color1CardBlur)
            .constrainAs(shape0) {
                top.linkTo(cardGlass.top)
                start.linkTo(cardGlass.end)
                end.linkTo(cardGlass.end)
            }
        )


        Box(modifier = Modifier
            .height(200.dp)
            .width(200.dp)
            .clip(CircleShape)
            .background(Color2CardBlur)
            .constrainAs(shape1) {
                bottom.linkTo(cardGlass.bottom)
                start.linkTo(cardGlass.start)
                end.linkTo(cardGlass.start)
                top.linkTo(cardGlass.bottom)
            })

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(40.dp))
                .border(width = 2.dp, color = Color.White, shape = RoundedCornerShape(40.dp))
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.4f),
                            Color.White.copy(alpha = 0.1f)
                        )
                    )
                )
                .height(360.dp)
                .width(235.dp)
                .constrainAs(cardGlass) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                }
        )

        Image(
            painter = painterResource(id = R.drawable.chip),
            contentDescription = "",
            modifier = Modifier
                .height(56.dp)
                .width(56.dp)
                .constrainAs(chip) {
                    top.linkTo(cardGlass.top, 16.dp)
                    start.linkTo(cardGlass.start, 16.dp)
                }
        )

        Image(
            painter = painterResource(id = R.drawable.ic_visa_inc_logo_black),
            contentDescription = "",
            modifier = Modifier
                .height(23.dp)
                .width(64.dp)
                .constrainAs(logo) {
                    bottom.linkTo(cardGlass.bottom, 24.dp)
                    start.linkTo(cardGlass.start, 24.dp)
                }
        )

        Image(
            painter = painterResource(id = R.drawable.contactless_24px_black),
            contentDescription = "",
            modifier = Modifier
                .height(36.dp)
                .width(36.dp)
                .constrainAs(contactless) {
                    bottom.linkTo(cardGlass.bottom, 20.dp)
                    end.linkTo(cardGlass.end, 20.dp)
                }
        )

        Text(
            text = "**** 0097",
            fontSize = 28.sp,
            color = Color.White,
            modifier = Modifier.constrainAs(number) {
                top.linkTo(cardGlass.top, 20.dp)
                end.linkTo(cardGlass.end, 20.dp)
            })

        Text(text = "Rodrigo Dominguez",
            fontSize = 18.sp,
            color = Color.DarkGray,
            modifier = Modifier.constrainAs(name) {
                bottom.linkTo(guideline)
                start.linkTo(cardGlass.start, 20.dp)
                end.linkTo(cardGlass.end, 20.dp)
            })

        Text(text = "USD 25,000",
            fontSize = 22.sp,
            color = Color.Black,
            modifier = Modifier.constrainAs(amount) {
                top.linkTo(guideline)
                start.linkTo(cardGlass.start, 20.dp)
                end.linkTo(cardGlass.end, 20.dp)
            })
    }
}

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Preview(showBackground = true, name = "Strabucks preview")
@Composable
fun DefaultPreview() {
    MaterialTheme {
        StarbucksDemo()
    }
}
