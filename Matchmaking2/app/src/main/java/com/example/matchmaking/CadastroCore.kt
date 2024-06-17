package com.example.matchmaking

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.matchmaking.ui.theme.MatchmakingTheme
import com.example.matchmaking.ui.theme.lalezarFamily
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class CadastroCore : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val extras = intent.extras

        setContent {
            MatchmakingTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    TelaCadastroCore(extras)
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun TelaCadastroCore(extras: Bundle?) {
    val contexto = LocalContext.current

    val nome = remember { mutableStateOf("") }
    val username = remember { mutableStateOf("")}
    val email = remember { mutableStateOf("")}
    val senha = remember { mutableStateOf("")}
    val senhaConfirme = remember { mutableStateOf("")}
    val dia = remember { mutableStateOf("")}
    val mes = remember { mutableStateOf("")}
    val ano = remember { mutableStateOf("")}
    val genero = remember { mutableStateOf("") }

    val options = listOf("Homem", "Mulher", "Outro")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp, 0.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = "MatchMaking",
            fontSize = 40.sp,
            fontFamily = lalezarFamily,
            color = Color(65, 80, 183)
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = "Criar Conta",
            fontSize = 25.sp,
            fontFamily = lalezarFamily,
            color = Color(65, 80, 183)
        )

        Spacer(modifier = Modifier.height(10.dp))
        
        TextBox(title = "Nome", input = nome)

        Spacer(modifier = Modifier.height(10.dp))

        TextBox(title = "Nickname", input = username)

        Spacer(modifier = Modifier.height(10.dp))

        TextBox(title = "E-mail", input = email)

        Spacer(modifier = Modifier.height(10.dp))

        TextBox(title = "Senha", input = senha, isPassword = true)

        Spacer(modifier = Modifier.height(10.dp))

        val confirmPasswordColor = if (senha.value == senhaConfirme.value) Color(42, 62, 185) else Color.Red
        TextBox(title = "Confirme sua senha", input = senhaConfirme, isPassword = true, textColor = confirmPasswordColor)

        Spacer(modifier = Modifier.height(10.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Data de nascimento",
                color = Color(42, 62, 185)
            )

            Row {
                DateBox(title = "Dia", input = dia)

                Spacer(modifier = Modifier.width(10.dp))

                DateBox(title = "Mês", input = mes)

                Spacer(modifier = Modifier.width(10.dp))

                DateBox(title = "Ano", input = ano)
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Me identifico como",
                color = Color(42, 62, 185)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                options.forEach { text ->
                    OutlinedButton(
                        onClick = { genero.value = text },
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = if (text == genero.value) Color(65, 80, 183) else Color.White,
                            contentColor = if (text == genero.value) Color.White else Color(65, 80, 183),
                        ),
                        border = BorderStroke(1.dp, Color(65, 80, 183)),
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier
                            .width(120.dp)
                            .height(50.dp)
                            .padding(5.dp, 5.dp)
                    ) {
                        Text(text = text)
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(0.dp, 20.dp),
            verticalAlignment = Alignment.Bottom
        ) {
             Button(onClick = {
                 val voltarLogin = Intent(contexto, MainActivity::class.java)

                 contexto.startActivity(voltarLogin)
             },
                modifier = Modifier
                    .width(170.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    Color.LightGray
                )
            ) {
                Text(
                    text = "VOLTAR",
                    fontSize = 16.sp,
                    fontFamily = lalezarFamily,
                    color = Color(65, 80, 183)
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Button(onClick = {
                if (senha.value != senhaConfirme.value) {
                    return@Button
                }

                val auth = Firebase.auth

                auth.createUserWithEmailAndPassword(email.value, senha.value)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val uid = auth.currentUser?.uid
                            val dt_nascimento = "${ano.value}-${mes.value}-${dia.value}"
                            val dt_cadastro = com.google.firebase.Timestamp.now()
                            val cadastrarFoto = Intent(contexto, CadastroFotos::class.java)

                            val user = hashMapOf(
                                "nome" to nome.value,
                                "sobrenome" to "",
                                "biografia" to "Olá, estou utilizando o MatchMaking!",
                                "deleted" to false,
                                "contato" to "11912345678",
                                "dt_cadastro" to dt_cadastro,
                                "dt_nascimento" to dt_nascimento,
                                "nota" to 5.0,
                                "username" to username.value,
                                "email" to email.value,
                                "senha" to senha.value,
                                "identidadeGenero" to genero.value,
                                "id_usuario" to uid
                            )

                            val db = Firebase.firestore
                            db.collection("users")
                                .document(uid!!)
                                .set(user)
                                .addOnSuccessListener {
                                    Log.d(TAG, "Deu bom")

                                    contexto.startActivity(cadastrarFoto)
                                }
                                .addOnFailureListener { e ->
                                    Log.w(TAG, "Deu ruim")
                                }
                        } else {
                            Log.w(TAG, "Deu ruim demais", task.exception)
                        }
                    }
            },
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    Color(65, 80, 183)
                )
            ) {
                Text(
                    text = "CONTINUAR",
                    fontSize = 16.sp,
                    fontFamily = lalezarFamily,
                    color = Color.White
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    MatchmakingTheme {
        TelaCadastroCore(null)
    }
}