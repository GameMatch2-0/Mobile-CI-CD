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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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

class CadastroTags : ComponentActivity() {
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
                    TelaCadastroTags(extras)
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun TelaCadastroTags(extras: Bundle?) {
    val contexto = LocalContext.current

    var showJogos by remember { mutableStateOf(false) }
    var showGeneros by remember { mutableStateOf(false) }
    var showPlataformas by remember { mutableStateOf(false) }
    var showHobbies by remember { mutableStateOf(false) }
    var showOrientacoes by remember { mutableStateOf(false) }

    val jogosUsuario = remember { mutableStateListOf<String>() }
    val generosUsuario = remember { mutableStateListOf<String>() }
    val plataformasUsuario = remember { mutableStateListOf<String>() }
    val hobbiesUsuario = remember { mutableStateListOf<String>() }
    val orientacoesUsuario = remember { mutableStateListOf<String>() }

    val jogos = listOf("GTA", "Red Dead", "Minecraft", "Sonic", "League of Legends", "Hades", "God of War", "Rocket League", "Bioshock", "Fortnite", "Genshin Impact")
    val generos = listOf("Ação", "Terror", "RPG", "Aventura", "Cartas", "Plataforma", "Corrida", "Esporte", "Luta")
    val plataformas = listOf("PS5", "PS4", "XBox One", "XBox Series", "Switch", "PC", "Mobile")
    val hobbies = listOf("Dormir", "Academia", "Música", "Viagens", "Esportes", "Series", "Desenhar", "Filmes", "Carros")
    val orientacoes = listOf("Heterossexual", "Homossexual", "Demissexual", "Pansexual", "Bissexual", "Assexual")

    if (showJogos) {
        AlertDialog(
            onDismissRequest = { showJogos = false },
            title = {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "JOGOS FAVORITOS",
                        fontSize = 25.sp,
                        fontFamily = lalezarFamily,
                        color = Color(65, 80, 183)
                    )
                }
            },
            text = {
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    Arrangement.Center
                ) {
                    jogos.forEach { jogo ->
                        OutlinedButton(
                            onClick = {
                                if (jogosUsuario.contains(jogo)) {
                                    jogosUsuario.remove(jogo)
                                } else {
                                    jogosUsuario.add(jogo)
                                }
                            },
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = if (jogosUsuario.contains(jogo)) Color(65, 80, 183) else Color.White,
                                contentColor = if (jogosUsuario.contains(jogo)) Color.White else Color(65, 80, 183),
                            ),
                            border = BorderStroke(1.dp, Color(65, 80, 183)),
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier
                                .height(50.dp)
                                .padding(5.dp, 5.dp)
                        ) {
                            Text(text = jogo)
                        }
                    }
                }
            },
            confirmButton = {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Button(
                        onClick = { showJogos = false },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(65, 80, 183)
                        )
                    ) {
                        Text(
                            text = "CONFIRMAR",
                            fontSize = 25.sp,
                            fontFamily = lalezarFamily,
                            color = Color.White
                        )
                    }
                }
            }
        )
    }

    if (showGeneros) {
        AlertDialog(
            onDismissRequest = { showGeneros = false },
            title = {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "GÊNEROS FAVORITOS",
                        fontSize = 25.sp,
                        fontFamily = lalezarFamily,
                        color = Color(65, 80, 183)
                    )
                }
            },
            text = {
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    Arrangement.Center
                ) {
                    generos.forEach { genero ->
                        OutlinedButton(
                            onClick = {
                                if (generosUsuario.contains(genero)) {
                                    generosUsuario.remove(genero)
                                } else {
                                    generosUsuario.add(genero)
                                }
                            },
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = if (generosUsuario.contains(genero)) Color(65, 80, 183) else Color.White,
                                contentColor = if (generosUsuario.contains(genero)) Color.White else Color(65, 80, 183),
                            ),
                            border = BorderStroke(1.dp, Color(65, 80, 183)),
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier
                                .height(50.dp)
                                .padding(5.dp, 5.dp)
                        ) {
                            Text(text = genero)
                        }
                    }
                }
            },
            confirmButton = {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Button(
                        onClick = { showGeneros = false },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(65, 80, 183)
                        )
                    ) {
                        Text(
                            text = "CONFIRMAR",
                            fontSize = 25.sp,
                            fontFamily = lalezarFamily,
                            color = Color.White
                        )
                    }
                }
            }
        )
    }

    if (showPlataformas) {
        AlertDialog(
            onDismissRequest = { showPlataformas = false },
            title = {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "PLATAFORMAS",
                        fontSize = 25.sp,
                        fontFamily = lalezarFamily,
                        color = Color(65, 80, 183)
                    )
                }
            },
            text = {
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    Arrangement.Center
                ) {
                    plataformas.forEach { plataforma ->
                        OutlinedButton(
                            onClick = {
                                if (plataformasUsuario.contains(plataforma)) {
                                    plataformasUsuario.remove(plataforma)
                                } else {
                                    plataformasUsuario.add(plataforma)
                                }
                            },
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = if (plataformasUsuario.contains(plataforma)) Color(65, 80, 183) else Color.White,
                                contentColor = if (plataformasUsuario.contains(plataforma)) Color.White else Color(65, 80, 183),
                            ),
                            border = BorderStroke(1.dp, Color(65, 80, 183)),
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier
                                .height(50.dp)
                                .padding(5.dp, 5.dp)
                        ) {
                            Text(text = plataforma)
                        }
                    }
                }
            },
            confirmButton = {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Button(
                        onClick = { showPlataformas = false },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(65, 80, 183)
                        )
                    ) {
                        Text(
                            text = "CONFIRMAR",
                            fontSize = 25.sp,
                            fontFamily = lalezarFamily,
                            color = Color.White
                        )
                    }
                }
            }
        )
    }

    if (showHobbies) {
        AlertDialog(
            onDismissRequest = { showHobbies = false },
            title = {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "HOBBIES",
                        fontSize = 25.sp,
                        fontFamily = lalezarFamily,
                        color = Color(65, 80, 183)
                    )
                }
            },
            text = {
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    Arrangement.Center
                ) {
                    hobbies.forEach { hobbie ->
                        OutlinedButton(
                            onClick = {
                                if (hobbiesUsuario.contains(hobbie)) {
                                    hobbiesUsuario.remove(hobbie)
                                } else {
                                    hobbiesUsuario.add(hobbie)
                                }
                            },
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = if (hobbiesUsuario.contains(hobbie)) Color(65, 80, 183) else Color.White,
                                contentColor = if (hobbiesUsuario.contains(hobbie)) Color.White else Color(65, 80, 183),
                            ),
                            border = BorderStroke(1.dp, Color(65, 80, 183)),
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier
                                .height(50.dp)
                                .padding(5.dp, 5.dp)
                        ) {
                            Text(text = hobbie)
                        }
                    }
                }
            },
            confirmButton = {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Button(
                        onClick = { showHobbies = false },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(65, 80, 183)
                        )
                    ) {
                        Text(
                            text = "CONFIRMAR",
                            fontSize = 25.sp,
                            fontFamily = lalezarFamily,
                            color = Color.White
                        )
                    }
                }
            }
        )
    }

    if (showOrientacoes) {
        AlertDialog(
            onDismissRequest = { showOrientacoes = false },
            title = {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "ORIENTAÇÃO SEXUAL",
                        fontSize = 25.sp,
                        fontFamily = lalezarFamily,
                        color = Color(65, 80, 183)
                    )
                }
            },
            text = {
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    Arrangement.Center
                ) {
                    orientacoes.forEach { orientacao ->
                        OutlinedButton(
                            onClick = {
                                if (orientacoesUsuario.contains(orientacao)) {
                                    orientacoesUsuario.remove(orientacao)
                                } else {
                                    orientacoesUsuario.add(orientacao)
                                }
                            },
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = if (orientacoesUsuario.contains(orientacao)) Color(65, 80, 183) else Color.White,
                                contentColor = if (orientacoesUsuario.contains(orientacao)) Color.White else Color(65, 80, 183),
                            ),
                            border = BorderStroke(1.dp, Color(65, 80, 183)),
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier
                                .height(50.dp)
                                .padding(5.dp, 5.dp)
                        ) {
                            Text(text = orientacao)
                        }
                    }
                }
            },
            confirmButton = {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Button(
                        onClick = { showOrientacoes = false },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(65, 80, 183)
                        )
                    ) {
                        Text(
                            text = "CONFIRMAR",
                            fontSize = 25.sp,
                            fontFamily = lalezarFamily,
                            color = Color.White
                        )
                    }
                }
            }
        )
    }

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
            text = "Seus Interesses",
            fontSize = 25.sp,
            fontFamily = lalezarFamily,
            color = Color(65, 80, 183)
        )

        Spacer(modifier = Modifier.height(30.dp))

        Button(onClick = {
            showJogos = true
        },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                Color(65, 80, 183)
            )
        ) {
            Text(
                text = "JOGOS FAVORITOS",
                fontSize = 25.sp,
                fontFamily = lalezarFamily,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        Button(onClick = {
            showGeneros = true
        },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                Color(65, 80, 183)
            )
        ) {
            Text(
                text = "GÊNEROS FAVORITOS",
                fontSize = 25.sp,
                fontFamily = lalezarFamily,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        Button(onClick = {
            showPlataformas = true
        },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                Color(65, 80, 183)
            )
        ) {
            Text(
                text = "PLATAFORMAS",
                fontSize = 25.sp,
                fontFamily = lalezarFamily,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        Button(onClick = {
            showHobbies = true
        },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                Color(65, 80, 183)
            )
        ) {
            Text(
                text = "HOBBIES",
                fontSize = 25.sp,
                fontFamily = lalezarFamily,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        Button(onClick = {
            showOrientacoes = true
        },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                Color(65, 80, 183)
            )
        ) {
            Text(
                text = "ORIENTAÇÃO SEXUAL",
                fontSize = 25.sp,
                fontFamily = lalezarFamily,
                color = Color.White
            )
        }

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(0.dp, 20.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            Button(onClick = {
                val voltarCadastroFotos = Intent(contexto, CadastroFotos::class.java)

                contexto.startActivity(voltarCadastroFotos)
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
                val db = Firebase.firestore
                val uid = Firebase.auth.currentUser?.uid

                val userUpdates: HashMap<String, Any> = hashMapOf(
                    "jogos_favoritos" to jogosUsuario,
                    "generos_favoritos" to generosUsuario,
                    "consoles" to plataformasUsuario,
                    "interesses" to hobbiesUsuario,
                    "orientacao_sexual" to orientacoesUsuario
                )

                db.collection("users")
                    .document(uid!!)
                    .update(userUpdates)
                    .addOnSuccessListener {
                        Log.d(TAG, "Atualização bem-sucedida")

                        val telaPerfil = Intent(contexto, Perfil::class.java)
                        contexto.startActivity(telaPerfil)
                    }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "Erro na atualização", e)
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
                    text = "Criar Conta",
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
fun GreetingPreview4() {
    MatchmakingTheme {
        TelaCadastroTags(null)
    }
}