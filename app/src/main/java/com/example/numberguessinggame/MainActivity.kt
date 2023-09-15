@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.numberguessinggame

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign.Companion.Center
import com.example.numberguessinggame.ui.theme.NumberGuessingGameTheme
import kotlin.random.Random


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NumberGuessingGameTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NumberGuessingGameApp()
                }
            }
        }
    }
}


private fun matchingGuessedNum(guessedAmount: Int, rand: Int): String {
    if (rand == guessedAmount) {
        return "Correct"
    } else if (rand >= guessedAmount) {
        return "Hint: It's higher"
    }
    return "Hint: It's lower"
}

private fun generateRand(hinted: String, randNum: Int): Int {
    if (hinted === "Correct" || randNum === 0) {
        return Random.nextInt(0, 11)
    }
    return randNum
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NumberGuessingGameApp() {
    var guessedInput by remember { mutableStateOf("") }
    var hinted by remember { mutableStateOf("")}
    var randNum = 0
    randNum = generateRand(hinted, randNum)
    var buttonState = "PLAY"

    val guessedAmount = guessedInput.toIntOrNull() ?: 0

    Scaffold (
        topBar = {
            TopAppBar()
        },
    ) {
        Column(
            modifier = Modifier
                .padding(it),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Text(
                text = stringResource(R.string.title),
                style = TextStyle(
                    fontSize = 25.sp,
                    fontWeight = FontWeight(500),
                    textAlign = TextAlign.Center
                )
            )
            Spacer(
                modifier = Modifier
                    .height(100.dp)
            )
            EditNumberField(
                label = R.string.placeholder,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                ),
                value = guessedInput,
                onValueChanged = {guessedInput = it},
                modifier = Modifier
                    .background(color = Color.Transparent)
                    .padding(bottom = 32.dp)
                    ,
            )
            Spacer(
                modifier = Modifier
                    .height(100.dp)
            )
            Text(stringResource(R.string.hint, hinted))
            Text(randNum.toString())
            Button(
                onClick = {
                    hinted = matchingGuessedNum(guessedAmount, randNum)
                    buttonState = "PLAY AGAIN"
                          },
                modifier = Modifier.wrapContentWidth(Alignment.CenterHorizontally)
            ) {
                Text(buttonState)
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        {
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Magenta)
                    .padding(50.dp)
                ,
            ) {
                Text (
                    text = stringResource(R.string.app_name),
                    color = Color.White,
                    textAlign = TextAlign.Start
                )
            }
        }
    )
}

@Composable
fun EditNumberField(
    @StringRes label: Int,
    keyboardOptions: KeyboardOptions,
    value: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        singleLine = true,
        modifier = modifier,
        onValueChange = onValueChanged,
        label = { Text(stringResource(label)) },
        keyboardOptions = keyboardOptions
    )
}


@Preview
@Composable
fun Preview() {
    NumberGuessingGameApp()
}