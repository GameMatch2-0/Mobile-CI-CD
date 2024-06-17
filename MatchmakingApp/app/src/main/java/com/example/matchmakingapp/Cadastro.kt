package com.example.matchmakingapp

import android.content.Intent
import android.os.Bundle
import android.widget.ScrollView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.matchmakingapp.ui.theme.MainBlue
import com.example.matchmakingapp.ui.theme.MatchmakingAppTheme
import com.example.matchmakingapp.ui.theme.lalezarFamily

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MatchmakingAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    Tela("Android")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Tela(name: String, modifier: Modifier = Modifier) {
    val contexto = LocalContext.current

    val nome = remember {
        mutableStateOf("")
    }
    val nickname = remember {
        mutableStateOf("")
    }
    val email = remember {
        mutableStateOf("")
    }
    val senha = remember {
        mutableStateOf("")
    }
    val confirmacaoSenha = remember {
        mutableStateOf("")
    }
    val dia = remember {
        mutableStateOf("")
    }
    val mes = remember {
        mutableStateOf("")
    }
    val ano = remember {
        mutableStateOf("")
    }
    val genero = remember {
        mutableStateOf("")
    }

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
        CreateSpacingHeight(space = 5)

        Text(text = "Cadastro", fontSize = 25.sp, fontFamily = lalezarFamily)
        CreateSpacingHeight(space = 5)

        TextBoxAndTitle(title = "Nome:", input = nome)
        CreateSpacingHeight(space = 15)

        TextBoxAndTitle(title = "Nickname", input = nickname)
        CreateSpacingHeight(space = 15)


        TextBoxAndTitle(title = "Email:", input = email)
        CreateSpacingHeight(space = 15)


        TextBoxAndTitle(title = "Senha:", input = senha)
        CreateSpacingHeight(space = 15)


        TextBoxAndTitle(title = "Confirmação de senha:", input = confirmacaoSenha)
        CreateSpacingHeight(space = 15)

        Column (
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = AbsoluteAlignment.Left
        ){
        Text(text = "Data de nascimento", fontSize = 15.sp, fontFamily = lalezarFamily)
        }
        Row {
            DatesInput(type = "Dia", input = dia)
            CreateSpacingWidth(space = 18)

            DatesInput(type = "Mês", input = mes)
            CreateSpacingWidth(space = 18)

            DatesInput(type = "Ano", input = ano)
            CreateSpacingWidth(space = 18)

        }
        CreateSpacingHeight(space = 15)

        Column (
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = AbsoluteAlignment.Left
        ){
        Text(text = "Genero", fontFamily = lalezarFamily, fontSize = 15.sp)
        }
        Row {
            GenderButton(type = "Masculino", input = genero)
            CreateSpacingWidth(space = 18)
            GenderButton(type = "Feminino", input = genero)
            CreateSpacingWidth(space = 18)
            GenderButton(type = "Outros", input = genero)
            CreateSpacingWidth(space = 18)
        }
        CreateSpacingHeight(space = 15)
        
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = AbsoluteAlignment.Right
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Button(onClick = {
                    val login = Intent(contexto, Login::class.java)

                    contexto.startActivity(login)
                },
                    shape = RoundedCornerShape(20),
                    modifier = Modifier.size(width = 150.dp, height = 50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = blue, contentColor = Color.White)
                ) {
                    Text(text = "Voltar", fontFamily = lalezarFamily, fontSize = 20.sp)
                }

                Button(onClick = {
                    val cadastroFoto = Intent(contexto, CadastroFoto::class.java)

                    contexto.startActivity(cadastroFoto)
                },
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
fun TextBoxAndTitle(title: String, input: MutableState<String>) {
    Column {
        Text(text = title, fontFamily = lalezarFamily)
        TextField(
            value = input.value,
            onValueChange = { newValue -> input.value = newValue },
            modifier = Modifier
                .fillMaxWidth()
                .size(width = 310.dp, height = 48.dp),
            shape = RoundedCornerShape(20),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color(231, 231, 232),
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatesInput(type: String, input: MutableState<String>) {
    Column {
        TextField(
            value = input.value,
            onValueChange = { newValue -> input.value = newValue },
            label = { Text(text = type, fontFamily = lalezarFamily, color = Color.Black) },
            modifier = Modifier
                .size(width = 110.dp, height = 55.dp),
            shape = RoundedCornerShape(20),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color(231, 231, 232),
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedLabelColor = Color.Black,
                focusedLabelColor = Color.Black,
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
        )
    }
}

@Composable
fun GenderButton(type: String, input: MutableState<String>) {
    val blue = Color(42, 62, 185)
    Button(
        onClick = {
            input.value = type
        },
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.size(width = 110.dp, height = 40.dp),
        colors = ButtonDefaults.buttonColors(containerColor = blue, contentColor = Color.White)
    ) {
        Text(text = type, fontFamily = lalezarFamily, fontSize = 14.sp)
    }
}

@Composable
fun CreateSpacingHeight(space: Int) {
    Spacer(modifier = Modifier.height(space.dp))
}

@Composable
fun CreateSpacingWidth(space: Int) {
    Spacer(modifier = Modifier.width(space.dp))
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MatchmakingAppTheme {
        Tela("Android")
    }
}