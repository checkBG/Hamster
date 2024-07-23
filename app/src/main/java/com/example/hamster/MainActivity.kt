package com.example.hamster

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hamster.ui.theme.HamsterTheme
import kotlin.math.roundToInt
import kotlin.system.exitProcess

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HamsterTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    HamsterApp()
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HamsterApp() {
    var goldCoins by remember { mutableIntStateOf(0) }

    var maxEnergy by remember { mutableIntStateOf(100) }
    var energy by remember { mutableIntStateOf(maxEnergy) }
    var goldCoinsOnTap by remember { mutableIntStateOf(1) }

    var percentEnergy by remember { mutableIntStateOf(((energy / maxEnergy.toFloat()) * 100).roundToInt()) }

    HamsterImageAndButton(
        goldCoins = goldCoins,
        energy = energy,
        energyIconResourceId = when (percentEnergy) {
            in 85..100 -> R.drawable.baseline_battery_full_24
            in 70..85 -> R.drawable.baseline_battery_6_bar_24
            in 60..70 -> R.drawable.baseline_battery_5_bar_24
            in 40..60 -> R.drawable.baseline_battery_4_bar_24
            in 20..40 -> R.drawable.baseline_battery_3_bar_24
            in 10..20 -> R.drawable.baseline_battery_2_bar_24
            in 1..10 -> R.drawable.baseline_battery_1_bar_24
            else -> R.drawable.outline_battery_charging_full_24
        },
        percentEnergy = percentEnergy,
        addOnClick = {
            if (energy >= goldCoinsOnTap) {
                goldCoins += goldCoinsOnTap
                energy -= goldCoinsOnTap
            } else {
                if (energy > 0) {
                    goldCoins += energy
                    energy = 0
                } else {
                    maxEnergy = (maxEnergy * 1.05).roundToInt()
                    energy = maxEnergy
                    goldCoinsOnTap++
                }
            }
            percentEnergy = ((energy / maxEnergy.toFloat()) * 100).roundToInt()
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HamsterImageAndButton(
    goldCoins: Int,
    energy: Int,
    energyIconResourceId: Int,
    percentEnergy: Int,
    addOnClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val imageHamster =
        painterResource(id = R.drawable.cute_hamster_isolated_on_transparent_background_generative_ai_png)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.topAppBar)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { exitProcess(0) }) {
                        Icon(
                            Icons.Filled.Close,
                            contentDescription = stringResource(R.string.navigationIconClose)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            Icons.Filled.KeyboardArrowDown,
                            contentDescription = stringResource(R.string.actionsIconCollapse)
                        )
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            Icons.Filled.MoreVert,
                            contentDescription = stringResource(R.string.actionIconSettings)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.LightGray)
            )
        },
        bottomBar = {
            BottomAppBar {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.binance),
                        contentDescription = stringResource(R.string.binance_content),
                        tint = colorResource(id = R.color.gold),
                        modifier = modifier.size(40.dp)
                    )
                }

                Spacer(modifier = modifier.weight(weight = 1F, fill = true))

                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.pick_hammer_outline),
                        contentDescription = stringResource(R.string.mining_content)
                    )
                }

                Spacer(modifier = modifier.weight(weight = 1F, fill = true))

                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.profile_users_group_silhouette),
                        contentDescription = stringResource(R.string.friends_content),
                        tint = colorResource(id = R.color.turquoise)
                    )
                }

                Spacer(modifier = modifier.weight(weight = 1F, fill = true))

                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.stack_of_coins),
                        contentDescription = stringResource(R.string.tasks_content)
                    )
                }
            }
        },
        modifier = modifier
    ) { innerPadding ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.group_of_almond_nuts_concept_free_png),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = modifier.size(700.dp)
            )

            Column(
                modifier = modifier
                    .fillMaxSize()
                    .wrapContentHeight(align = Alignment.CenterVertically)
            ) {
                Row(modifier = modifier) {
                    Row(modifier = modifier) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_currency_bitcoin_24),
                            contentDescription = stringResource(R.string.goldIconDescription),
                            tint = Color.Unspecified,
                            modifier = modifier.size(75.dp)
                        )

                        Text(
                            text = goldCoins.toString(),
                            fontSize = 30.sp,
                            modifier = modifier.align(alignment = Alignment.CenterVertically)
                        )
                    }

                    Spacer(modifier = modifier.weight(weight = 1F, fill = true))

                    Row(
                        horizontalArrangement = Arrangement.End,
                        modifier = modifier
                    ) {
                        Box(modifier = modifier) {

                            Icon(
                                painter = painterResource(id = energyIconResourceId),
                                contentDescription = stringResource(R.string.energy_content),
                                tint = Color.Unspecified,
                                modifier = modifier.size(75.dp)
                            )

                            Text(
                                text = "$percentEnergy%",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = modifier.align(alignment = Alignment.Center)
                            )

                        }
                        Text(
                            text = energy.toString(),
                            fontSize = 30.sp,
                            modifier = modifier
                                .align(alignment = Alignment.CenterVertically)
                                .padding(end = 12.dp)
                        )
                    }
                }

                Button(
                    onClick = addOnClick,
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.burgundy)),
                    modifier = modifier
                        .padding(innerPadding)
                ) {
                    Image(
                        painter = imageHamster,
                        contentDescription = stringResource(R.string.hamster_description)
                    )
                }
            }
        }
    }
}