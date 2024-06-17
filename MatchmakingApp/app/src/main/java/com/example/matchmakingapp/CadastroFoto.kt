package com.example.matchmakingapp

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.matchmakingapp.ui.theme.MatchmakingAppTheme
import com.example.matchmakingapp.ui.theme.lalezarFamily

class CadastroFoto : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val extras = intent.extras

        setContent {
            MatchmakingAppTheme {
                Surface (
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ){
                    CadastroFoto(extras)
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun CadastroFoto(extras: Bundle?) {

    val contexto = LocalContext.current

    val blue = Color(42, 62, 185)

    Column(
        modifier = Modifier
            .padding(10.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "MatchMaking",
            fontSize = 30.sp,
            color = blue,
            fontFamily = lalezarFamily
        )

        CreateSpacingHeight(space = 50)

        Text(
            text = "Escolha até 6 fotos:",
            fontSize = 25.sp,
            color = blue,
            fontFamily = lalezarFamily
        )

        CreateSpacingHeight(space = 50)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            PhotoCard()
            PhotoCard()
            PhotoCard()
        }

        CreateSpacingHeight(space = 20)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            PhotoCard()
            PhotoCard()
            PhotoCard()
        }

        CreateSpacingHeight(space = 100)

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
               modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Button(onClick = {
                    val cadastro = Intent(contexto, MainActivity::class.java)

                    contexto.startActivity(cadastro)
                },
                    shape = RoundedCornerShape(20),
                    modifier = Modifier.size(width = 150.dp, height = 50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = blue, contentColor = Color.White)
                ) {
                    Text(text = "Anterior", fontFamily = lalezarFamily, fontSize = 20.sp)
                }

                Button(onClick = { /*TODO*/ },
                    shape = RoundedCornerShape(20),
                    modifier = Modifier.size(width = 150.dp, height = 50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = blue, contentColor = Color.White)
                ) {
                    Text(text = "Próximo", fontFamily = lalezarFamily, fontSize = 20.sp)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoCard() {
    Box(
        modifier = Modifier
            .height(160.dp)
            .width(100.dp)
            .background(Color.LightGray, RoundedCornerShape(16.dp))
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(5.dp)
                .height(30.dp)
                .width(30.dp)
                .background(Color(42, 62, 185), RoundedCornerShape(50.dp))
        ) {
            Icon(
                Icons.Default.Add,
                contentDescription = "Adicionar",
                tint = Color.White,
                modifier = Modifier.align(Alignment.Center)
                )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    MatchmakingAppTheme {
        CadastroFoto(null)
    }
}