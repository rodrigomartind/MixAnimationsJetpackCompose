package com.rodrigodominguez.mixanimationsjetpackcompose.starbucksdemo

import android.util.MutableBoolean
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.*
import com.rodrigodominguez.mixanimationsjetpackcompose.R
import com.rodrigodominguez.mixanimationsjetpackcompose.ui.StarbucksGreenColor
import java.util.*

val DIMENSION_16_DP = 16.dp
val DIMENSION_24_DP = 24.dp
val DIMENSION_28_DP = 28.dp

val FONT_18_SP = 18.sp
val FONT_24_SP = 24.sp

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun StarbucksDemo() {
    var componentHeight by remember { mutableStateOf(1000f) }
    val swipeableState = rememberSwipeableState("Bottom")
    val anchors = mapOf(0f to "Bottom", componentHeight to "Top")

    var animateToEnd by remember { mutableStateOf(false) }
    val progress by animateFloatAsState(
        targetValue = if (animateToEnd) 1f else 0f,
        animationSpec = tween(1000)
    )

    var cs1 = ConstraintSet(
        """
            {
                starbucksBody: {
                  bottom: ['parent','bottom']
                }
            }
    """
    )

    var cs2 = ConstraintSet(
        """
            {
                starbucksBody: {
                bottom: ['parent','bottom', 86]
                }
            }
    """
    )

    var cs3 = ConstraintSet(
        """
            {
              starbucksBody: {
                bottom: ['parent', 'bottom', 486],
              } 
            }
    """
    )

    var start by remember { mutableStateOf(cs1) }
    var end by remember { mutableStateOf(cs2) }
    var progressSwipe = remember { Animatable(0f) }

    var config by remember { mutableStateOf(0) }
    var inTransition by remember { mutableStateOf(false) }


    var started by remember { mutableStateOf(false) }
    if (started) {
        LaunchedEffect(config) {
            if (!inTransition) {
                inTransition = true
                progressSwipe.animateTo(
                    1f,
                    animationSpec = tween(2000)
                )
                inTransition = false
                progressSwipe.snapTo(0f)
                when (config) {
                    0 -> {
                        start = cs1
                        end = cs2
                    }
                    1 -> {
                        start = cs2
                        end = cs3
                    }
                }
            } else {
                inTransition = false
                progressSwipe.animateTo(
                    0f,
                    animationSpec = tween(2000)
                )
            }
        }
    }

    //val mprogress = (swipeableState.offset.value / componentHeight)



    MotionLayout(start = start, end = end,
        progress = progressSwipe.value,
        debug = EnumSet.of(MotionLayoutDebugFlags.NONE),
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .swipeable(
                state = swipeableState,
                anchors = anchors,
                // resistance = null,
                reverseDirection = true,
                thresholds = { _, _ -> FractionalThreshold(0.3f) },
                orientation = Orientation.Vertical
            )
            .onSizeChanged { size ->
                componentHeight = size.height.toFloat()
            }
    ) {
        val sizeCup = remember { mutableStateOf("L") }
        val listCup = remember {
            mutableListOf<String>()
        }

        StarbucksDetail(listCup)
        StarbucksBody(sizeCup, listCup) {
            if (!animateToEnd) {
                animateToEnd = !animateToEnd
            }
            started = true;
            if (!inTransition) {
                config = (config + 1) % 2
            } else if (config > 0) {
                config = (config - 1) % 2
            }
        }
    }

}

@ExperimentalAnimationApi
@Composable
fun StarbucksDetail(listItemsSelected: MutableList<String>) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .layoutId("starbucksDetail")
            .background(StarbucksGreenColor)
    ) {
        val (listBottom) = createRefs()

        LazyRow(
            contentPadding = PaddingValues(horizontal = DIMENSION_16_DP),
            modifier = Modifier
                .constrainAs(listBottom) {
                    bottom.linkTo(parent.bottom, DIMENSION_16_DP)
                }
                .fillMaxWidth()
                .height(56.dp)
        ) {
            itemsIndexed(listItemsSelected) { index, item ->
                val visibility by remember { mutableStateOf(listItemsSelected.contains(item) && item == listItemsSelected[index]) }
                AnimatedVisibility(
                    visible = visibility
                ) {
                    Box(
                        modifier = Modifier
                            .width(56.dp)
                            .height(56.dp)
                            .clip(
                                CircleShape
                            )
                            .background(Color.White)

                    ) {
                        Text(item, modifier = Modifier.align(Alignment.Center))
                    }
                }
            }
        }
    }
}


@Composable
fun StarbucksBody(
    sizeCup: MutableState<String>,
    listCup: MutableList<String>,
    animateToNewItemAdded: () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .layoutId("starbucksBody")
            .background(
                Color.White,
                shape = RoundedCornerShape(
                    bottomEnd = DIMENSION_16_DP,
                    bottomStart = DIMENSION_16_DP
                )
            )
            .padding(bottom = 44.dp)
    ) {
        val (image, title, description, priceBox, addButton) = createRefs()
        Image(
            painter = painterResource(id = R.drawable.mocha),
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .constrainAs(image) {
                    top.linkTo(parent.top)
                }
        )
        Text(
            text = "Mocha Frapuccino",
            fontSize = FONT_24_SP,
            modifier = Modifier.constrainAs(title) {
                top.linkTo(image.bottom, DIMENSION_16_DP)
                end.linkTo(parent.end, DIMENSION_16_DP)
                start.linkTo(parent.start, DIMENSION_16_DP)
                width = Dimension.fillToConstraints
            }
        )
        Text(
            text = "Mocha sauce, Frappuccino roast coffee, milk and ice all come together for a mocha flavor that ll leave you wanting more.",
            fontSize = FONT_18_SP,
            modifier = Modifier.constrainAs(description) {
                top.linkTo(title.bottom, DIMENSION_16_DP)
                end.linkTo(parent.end, DIMENSION_16_DP)
                start.linkTo(parent.start, DIMENSION_16_DP)
                width = Dimension.fillToConstraints
            }
        )
        SizeCupComponent(
            modifier = Modifier
                .fillMaxWidth()
                .height(96.dp)
                .constrainAs(priceBox) {
                    top.linkTo(description.bottom, DIMENSION_16_DP)
                    end.linkTo(parent.end, DIMENSION_16_DP)
                    start.linkTo(parent.start, DIMENSION_16_DP)
                    width = Dimension.fillToConstraints
                }, sizeCup
        )
        Button(
            onClick = {
                animateToNewItemAdded()
                listCup.add(sizeCup.value)
            },
            modifier = Modifier
                .height(56.dp)
                .constrainAs(addButton) {
                    top.linkTo(priceBox.bottom, DIMENSION_16_DP)
                    end.linkTo(parent.end, DIMENSION_16_DP)
                    start.linkTo(parent.start, DIMENSION_16_DP)
                    width = Dimension.fillToConstraints
                },
            shape = RoundedCornerShape(DIMENSION_28_DP),
            colors = ButtonDefaults.outlinedButtonColors(
                backgroundColor = StarbucksGreenColor,
                contentColor = Color.White
            )
        ) {
            Text(text = "Add to bag")
        }
    }
}

@Composable
fun SizeCupComponent(modifier: Modifier, size: MutableState<String>) {
    ConstraintLayout(
        modifier = modifier
    ) {
        val priceCup = remember { mutableStateOf("3,55") }

        val (price, boxSize) = createRefs()
        Text(
            text = "$ ${priceCup.value}",
            fontSize = 34.sp,
            modifier = Modifier.constrainAs(price) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            })
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.constrainAs(boxSize) {
                start.linkTo(price.end)
                end.linkTo(parent.end)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                width = Dimension.fillToConstraints
            }) {
            Button(
                onClick = {
                    priceCup.value = "3,55"
                    size.value = "L"
                },
                border = BorderStroke(2.dp, StarbucksGreenColor),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .width(65.dp)
                    .height(65.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = StarbucksGreenColor)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.coffee_takeaway_outline),
                    contentDescription = "",
                    modifier = Modifier
                        .width(30.dp)
                        .height(30.dp)
                )
            }
            Button(
                onClick = {
                    priceCup.value = "3,95"
                    size.value = "M"
                },
                border = BorderStroke(2.dp, StarbucksGreenColor),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .width(65.dp)
                    .height(65.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = StarbucksGreenColor)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.coffee_takeaway_outline),
                    contentDescription = "",
                    modifier = Modifier
                        .width(40.dp)
                        .height(40.dp)
                )
            }
            Button(
                onClick = {
                    priceCup.value = "4,20"
                    size.value = "XL"
                },
                border = BorderStroke(2.dp, StarbucksGreenColor),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .width(65.dp)
                    .height(65.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = StarbucksGreenColor)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.coffee_takeaway_outline),
                    contentDescription = "",
                    modifier = Modifier
                        .width(50.dp)
                        .height(50.dp)
                )
            }

        }

    }
}