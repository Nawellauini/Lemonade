package com.example.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lemonade.ui.theme.LemonadeTheme
import kotlin.random.Random

// Énumération pour les étapes
enum class LemonadeStep {
    SELECT, SQUEEZE, DRINK, RESTART
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LemonadeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LemonApp(Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun LemonApp(modifier: Modifier = Modifier) {
    var currentStep by remember { mutableStateOf(LemonadeStep.SELECT) }
    var pressCount by remember { mutableStateOf(0) }
    var requiredPresses by remember { mutableStateOf(0) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (currentStep) {
            LemonadeStep.SELECT -> {
                Text(text = stringResource(R.string.lemon_select), fontSize = 18.sp)
                Spacer(modifier = Modifier.height(16.dp))
                Image(
                    painter = painterResource(R.drawable.lemon_tree),
                    contentDescription = stringResource(R.string.lemon_tree_content_description),
                    modifier = Modifier
                        .size(100.dp)
                        .clickable {
                            currentStep = LemonadeStep.SQUEEZE
                            requiredPresses = Random.nextInt(2, 5)
                        }
                )
            }
            LemonadeStep.SQUEEZE -> {
                Text(text = stringResource(R.string.lemon_squeeze), fontSize = 18.sp)
                Text(text = "Press the lemon ${requiredPresses - pressCount} more times", fontSize = 16.sp)
                Spacer(modifier = Modifier.height(16.dp))
                Image(
                    painter = painterResource(R.drawable.lemon_squeeze),
                    contentDescription = stringResource(R.string.lemon_content_description),
                    modifier = Modifier
                        .size(100.dp)
                        .clickable {
                            if (pressCount < requiredPresses) {
                                pressCount++
                            } else {
                                currentStep = LemonadeStep.DRINK
                                pressCount = 0
                            }
                        }
                )
            }
            LemonadeStep.DRINK -> {
                Text(text = stringResource(R.string.lemon_drink), fontSize = 18.sp)
                Spacer(modifier = Modifier.height(16.dp))
                Image(
                    painter = painterResource(R.drawable.lemon_drink),
                    contentDescription = stringResource(R.string.lemon_drink_content_description),
                    modifier = Modifier
                        .size(100.dp)
                        .clickable { currentStep = LemonadeStep.RESTART }
                )
            }
            LemonadeStep.RESTART -> {
                Text(text = stringResource(R.string.lemon_restart), fontSize = 18.sp)
                Spacer(modifier = Modifier.height(16.dp))
                Image(
                    painter = painterResource(R.drawable.lemon_restart),
                    contentDescription = stringResource(R.string.empty_glass_content_description),
                    modifier = Modifier
                        .size(100.dp)
                        .clickable {
                            currentStep = LemonadeStep.SELECT
                            pressCount = 0
                        }
                )
            }
        }
    }
}
// change 1

@Preview(showBackground = true)
@Composable
fun LemonAppPreview() {
    LemonadeTheme {
        LemonApp()
    }
}
