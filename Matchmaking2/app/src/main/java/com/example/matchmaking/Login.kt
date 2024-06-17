package com.example.matchmaking

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.matchmaking.ui.theme.MatchmakingTheme
import com.example.matchmaking.ui.theme.lalezarFamily
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val extras = intent.extras

        setContent {
            MatchmakingTheme {
                // A surface container using the 'background' color from the theme
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    BackgroundImage()
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = Color(241, 241, 241, 0)
                    ) {
                        TelaLogin("Android")
                    }
                }
            }
        }
    }
}

@Composable
fun TelaLogin(name: String, modifier: Modifier = Modifier) {
    val contexto = LocalContext.current
    val dataStoreManager = DataStoreManager(contexto)

    val email = remember { mutableStateOf("")}
    val senha = remember { mutableStateOf("")}
    var checkedState by remember { mutableStateOf(false) }
    val errorMessage = remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        dataStoreManager.emailFlow.collect { savedEmail ->
            savedEmail?.let {
                email.value = it
            }
        }
        dataStoreManager.passwordFlow.collect { savedPassword ->
            savedPassword?.let {
                senha.value = it
            }
        }
        dataStoreManager.rememberMeFlow.collect { savedRememberMe ->
            checkedState = savedRememberMe
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(241, 241, 241, 0))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "MatchMaking",
            fontSize = 40.sp,
            fontFamily = lalezarFamily,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(40.dp))

        Box(
            modifier = Modifier
                .shadow(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(10.dp)
                )
                .background(Color.White)
                .size(300.dp, 400.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp, 0.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Login",
                    fontSize = 32.sp,
                    fontFamily = lalezarFamily,
                    color = Color(65, 80, 183)
                )

                Spacer(modifier = Modifier.height(20.dp))

                TextBox(title = "E-mail", input = email)

                Spacer(modifier = Modifier.height(20.dp))

                TextBox(title = "Senha", input = senha, isPassword = true)

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Checkbox(
                        checked = checkedState,
                        onCheckedChange = { checkedState = it},
                        colors = CheckboxDefaults.colors(
                            checkedColor = Color(42, 62, 185, 255),
                            checkmarkColor = Color(65, 80, 183),
                            uncheckedColor = Color(42, 62, 185, 255)
                        )
                    )

                    Text(
                        text = "Lembrar usuário",
                        fontSize = 16.sp,
                        fontFamily = lalezarFamily,
                        color = Color(65, 80, 183)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                if (errorMessage.value.isNotEmpty()) {
                    Snackbar(
                        modifier = Modifier.padding(16.dp),
                        action = {
                            TextButton(onClick = { errorMessage.value = "" }) {
                                Text("Fechar")
                            }
                        }
                    ) {
                        Text("usuário ou senha incorretos")
                    }
                }

                Button(onClick = {
                    val auth = Firebase.auth

                    auth.signInWithEmailAndPassword(email.value, senha.value)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val user = auth.currentUser

                                coroutineScope.launch {
                                    if (checkedState) {
                                        dataStoreManager.saveCredentials(email.value, senha.value, checkedState)
                                    } else {
                                        dataStoreManager.clearCredentials()
                                    }
                                }

                                val logar = Intent(contexto, Perfil::class.java)

                                contexto.startActivity(logar)
                            } else {
                                errorMessage.value = "Falha no login: ${task.exception?.message}"
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
                        text = "ENTRAR",
                        fontSize = 16.sp,
                        fontFamily = lalezarFamily,
                        color = Color.White
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            val criarConta = Intent(contexto, CadastroCore::class.java)

            contexto.startActivity(criarConta)
        },
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                Color.Transparent
            )
        ) {
            Text(
                text = "Ainda não tem uma conta? Criar.",
                fontSize = 16.sp,
                fontFamily = lalezarFamily,
                color = Color(65, 80, 183)
            )
        }
    }
}

@Composable
fun BackgroundImage() {
    val image = painterResource(id = R.drawable.fundologin)
    val aspectRatio = image.intrinsicSize.width / image.intrinsicSize.height

    Image(
        painter = image,
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(aspectRatio)
            .padding(top = 0.dp),
    )
}

@Preview(showBackground = true)
@Composable
fun BackgroundImagePreview() {
    BackgroundImage()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextBox(title: String, input: MutableState<String>, isPassword: Boolean = false, textColor: Color = Color(42, 62, 185)) {
    val visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None

    OutlinedTextField(
        value = input.value,
        onValueChange = { newValue -> input.value = newValue },
        label = { Text(title) },
        singleLine = true,
        visualTransformation = visualTransformation,
        modifier = Modifier
            .fillMaxWidth(),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            containerColor = Color.White,
            focusedTextColor = textColor,
            unfocusedTextColor = Color(65, 80, 183, 128),
            focusedLabelColor = Color(42, 62, 185, 255),
            unfocusedLabelColor = Color(65, 80, 183),
            cursorColor = Color(42, 62, 185, 255)
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateBox(title: String, input: MutableState<String>) {
    OutlinedTextField(
        value = input.value,
        onValueChange = { newValue -> input.value = newValue },
        label = { Text(title) },
        singleLine = true,
        modifier = Modifier
            .width(110.dp),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.White,
            focusedTextColor = Color(42, 62, 185, 255),
            unfocusedTextColor = Color(65, 80, 183, 128),
            focusedLabelColor = Color(42, 62, 185, 255),
            unfocusedLabelColor = Color(65, 80, 183),
            unfocusedIndicatorColor = Color(65, 80, 183),
            focusedIndicatorColor = Color(42, 62, 185, 255),
            cursorColor = Color(42, 62, 185, 255)
        )
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MatchmakingTheme {
        TelaLogin("Android")
    }
}