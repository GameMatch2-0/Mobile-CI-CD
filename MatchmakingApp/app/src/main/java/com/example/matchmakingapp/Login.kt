package com.example.matchmakingapp

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.matchmakingapp.ui.theme.MatchmakingAppTheme
import com.example.matchmakingapp.ui.theme.lalezarFamily

class Login : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val extras = intent.extras

        setContent {
            MatchmakingAppTheme {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.White
                ) {
                    Login(extras)
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun Login(extras: Bundle?) {

    val contexto = LocalContext.current

    val blue = Color(42, 62, 185)

    val valorEmail = remember { mutableStateOf("")}
    val valorSenha = remember { mutableStateOf("")}

    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "MatchMaking",
                fontSize = 30.sp,
                color = blue,
                fontFamily = lalezarFamily
            )

            CreateSpacingHeight(space = 50)

            Column {
                Text(
                    text = "Bem vindo(a)!",
                    fontSize = 25.sp,
                    color = Color.Black,
                    fontFamily = lalezarFamily
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            fun isValidEmail(email: String): Boolean {
                val emailRegex = Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}\$")
                return emailRegex.matches(email)
            }

            TextBoxAndTitle(title = "Email:", input = valorEmail)
            CreateSpacingHeight(space = 15)


            TextBoxAndTitle(title = "Senha:", input = valorSenha)
            CreateSpacingHeight(space = 15)

            Spacer(modifier = Modifier.height(16.dp))

            val context = LocalContext.current
            Button(onClick = {
                if (valorSenha.value.isEmpty() || valorEmail.value.isEmpty()){
                    Toast.makeText(context ,"Valor inválido", Toast.LENGTH_SHORT).show()
                }else if (valorSenha.value.length < 6) {
                    Toast.makeText(context ,"Deve conter mais de 6 caracteres", Toast.LENGTH_SHORT).show()
                } else {
                    val cadastro = Intent(contexto, MainActivity::class.java)

                    contexto.startActivity(cadastro)
                }
            },
                shape = RoundedCornerShape(20),
                modifier = Modifier.size(width = 150.dp, height = 50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = blue, contentColor = Color.White)
            ) {
                Text(text = "Login", fontFamily = lalezarFamily, fontSize = 20.sp)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Preview(showBackground = true)
@Composable
fun GreetingPreview3() {
    MatchmakingAppTheme {
        Login(null)
    }
}