package com.example.matchmaking

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.matchmaking.ui.theme.MatchmakingTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue
import com.example.matchmaking.R
import com.example.matchmaking.ui.theme.lalezarFamily
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

class Perfil : ComponentActivity() {
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
                    TinderLikeCards()
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TinderLikeCards() {
    val contexto = LocalContext.current

    var usuarios by remember { mutableStateOf(mutableListOf<Usuario>()) }
    var showDialog by remember { mutableStateOf(false) }
    var showText by remember { mutableStateOf(false) }
    var showPerfil by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        usuarios = fetchUsuarios().toMutableList()
    }

    val usuariosSnapshot = snapshotFlow { usuarios }.collectAsState(initial = mutableListOf())

    val usuarioLogado = remember { mutableStateOf<Usuario?>(null) }

    LaunchedEffect(key1 = Unit) {
        usuarioLogado.value = fetchUsuarioLogado()
    }

    if (showPerfil) {
        AlertDialog(
            modifier = Modifier
                .fillMaxSize()
                .padding(0.dp, 20.dp),
            onDismissRequest = { showPerfil = false },
            title = {
                usuarioLogado.value?.let { usuario ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(24, 30, 41))
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        val painter = rememberAsyncImagePainter(
                            ImageRequest.Builder(LocalContext.current)
                                .data(data = usuario.foto_perfil)
                                .apply(block = fun ImageRequest.Builder.() {
                                    crossfade(true)
                                }).build()
                        )

                        Row {
                            Image(
                                painter = painter,
                                contentDescription = "Foto usuário",
                                modifier = Modifier
                                    .width(100.dp)
                                    .height(100.dp)
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )

                            Spacer(modifier = Modifier.width(10.dp))

                            Column {
                                Spacer(modifier = Modifier.height(20.dp))

                                Row {
                                    Text(
                                        text = usuario.username,
                                        fontSize = 24.sp,
                                        fontFamily = lalezarFamily,
                                        color = Color.White
                                    )

                                    Spacer(modifier = Modifier.width(20.dp))

                                    Image(
                                        painter = painterResource(id = R.mipmap.estrela),
                                        contentDescription = "Icone estrela",
                                        modifier = Modifier
                                            .height(30.dp)
                                    )

                                    Spacer(modifier = Modifier.width(3.dp))

                                    Text(
                                        text = "${usuario.nota}",
                                        fontSize = 18.sp,
                                        fontFamily = lalezarFamily,
                                        color = Color.White
                                    )
                                }

                                Spacer(modifier = Modifier.height(5.dp))

                                Row {
                                    Text(
                                        text = usuario.nome,
                                        fontSize = 18.sp,
                                        fontFamily = lalezarFamily,
                                        color = Color.White
                                    )

                                    Spacer(modifier = Modifier.width(5.dp))

                                    Text(
                                        text = usuario.sobrenome,
                                        fontSize = 20.sp,
                                        fontFamily = lalezarFamily,
                                        color = Color.White
                                    )
                                }
                            }
                        }
                    }
                }
            },
            text = {
                usuarioLogado.value?.let { usuario ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = usuario.biografia,
                                fontSize = 20.sp,
                                fontFamily = lalezarFamily,
                                color = Color.White,
                                textAlign = TextAlign.Center
                            )
                        }

                        FlowRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(400.dp)
                                .verticalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            usuario.jogos_favoritos.forEach { jogo ->
                                OutlinedCard(
                                    modifier = Modifier
                                        .padding(10.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color.Transparent
                                    ),
                                    shape = RoundedCornerShape(20.dp),
                                    border = BorderStroke(2.dp, Color(65, 80, 183))
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .height(40.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = jogo,
                                            modifier = Modifier
                                                .padding(5.dp, 0.dp),
                                            fontSize = 20.sp,
                                            fontFamily = lalezarFamily,
                                            color = Color(65, 80, 183),
                                        )
                                    }
                                }
                            }

                            usuario.generos_favoritos.forEach { genero ->
                                OutlinedCard(
                                    modifier = Modifier
                                        .padding(10.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color.Transparent
                                    ),
                                    shape = RoundedCornerShape(20.dp),
                                    border = BorderStroke(2.dp, Color(76, 175, 80, 255))
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .height(40.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = genero,
                                            modifier = Modifier
                                                .padding(5.dp, 0.dp),
                                            fontSize = 20.sp,
                                            fontFamily = lalezarFamily,
                                            color = Color(76, 175, 80, 255),
                                        )
                                    }
                                }
                            }

                            usuario.consoles.forEach { console ->
                                OutlinedCard(
                                    modifier = Modifier
                                        .padding(10.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color.Transparent
                                    ),
                                    shape = RoundedCornerShape(20.dp),
                                    border = BorderStroke(2.dp, Color(255, 152, 0, 255))
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .height(40.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = console,
                                            modifier = Modifier
                                                .padding(5.dp, 0.dp),
                                            fontSize = 20.sp,
                                            fontFamily = lalezarFamily,
                                            color = Color(255, 152, 0, 255),
                                        )
                                    }
                                }
                            }

                            usuario.interesses.forEach { interesse ->
                                OutlinedCard(
                                    modifier = Modifier
                                        .padding(10.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color.Transparent
                                    ),
                                    shape = RoundedCornerShape(20.dp),
                                    border = BorderStroke(2.dp, Color(244, 67, 54, 255))
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .height(40.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = interesse,
                                            modifier = Modifier
                                                .padding(5.dp, 0.dp),
                                            fontSize = 20.sp,
                                            fontFamily = lalezarFamily,
                                            color = Color(244, 67, 54, 255),
                                        )
                                    }
                                }
                            }
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
                        onClick = { showPerfil = false },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(65, 80, 183)
                        )
                    ) {
                        Text(
                            text = "FECHAR",
                            fontSize = 25.sp,
                            fontFamily = lalezarFamily,
                            color = Color.White
                        )
                    }
                }
            },
            containerColor = Color(24, 30, 41)
        )
    }

    LazyRow(modifier = Modifier.fillMaxSize()) {
        items(usuariosSnapshot.value.size) { index ->
            val usuario = usuarios[index]
            var offsetX by remember { mutableStateOf(0f) }

            val offsetXState = animateFloatAsState(
                targetValue = offsetX,
                animationSpec = spring(), label = ""
            )

            Card(
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 6.dp
                ),
                modifier = Modifier
                    .background(Color(255, 255, 255, 255))
                    .width(width = 390.dp)
                    .height(height = 770.dp)
                    .padding(16.dp)
                    .graphicsLayer {
                        // var zIndex = usuarios.size - index.toInt()
                        translationX = offsetXState.value
                    }
                    .draggable(
                        orientation = Orientation.Horizontal,
                        state = rememberDraggableState { delta ->
                            offsetX += delta
                        },
                        onDragStopped = {
                            if (offsetX > 100.dp.value || offsetX < 0.dp.value) {
                                usuarios.removeAt(index)
                            }
                            offsetX = 0f
                        }
                    )

            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(24, 30, 41, 255)),
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.TopEnd)
                            .padding(16.dp),
                        contentAlignment = Alignment.TopEnd
                    ) {
                        Button(
                            onClick = {
                                showText = true
                                coroutineScope.launch {
                                    delay(10000)
                                    showText = false
                                }
                            },
                            shape = CircleShape
                        ) {
                            Text("+")
                        }
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp)
                                .background(Color(24, 30, 41, 255)),
                        ) {
                            val painter = rememberAsyncImagePainter(
                                ImageRequest.Builder(LocalContext.current)
                                    .data(data = usuario.foto_perfil)
                                    .apply(block = fun ImageRequest.Builder.() {
                                        crossfade(true)
                                    }).build()
                            )

                            Image(
                                painter = painter,
                                contentDescription = "Foto de Perfil",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            ) {

                            Text(
                                text = usuario.username,
                                fontSize = 20.sp,
                                fontFamily = lalezarFamily,
                                color = Color.White
                            )

                            Spacer(modifier = Modifier.width(35.dp))

                            Text(
                                text = usuario.dt_nascimento,
                                fontSize = 20.sp,
                                fontFamily = lalezarFamily,
                                color = Color.White
                            )

                            Spacer(modifier = Modifier.width(35.dp))

                            Image(
                                painter = painterResource(id = R.mipmap.estrela),
                                contentDescription = "Icone estrela",
                                modifier = Modifier
                                    .height(30.dp)
                            )

                            Spacer(modifier = Modifier.width(5.dp))

                            Text(
                                text = usuario.nota.toString(),
                                fontSize = 20.sp,
                                fontFamily = lalezarFamily,
                                color = Color.White
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp, 0.dp),
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Text(
                                text = usuario.nome,
                                fontSize = 16.sp,
                                fontFamily = lalezarFamily,
                                color = Color.White
                            )

                            Spacer(modifier = Modifier.width(3.dp))

                            Text(
                                text = usuario.sobrenome,
                                fontSize = 16.sp,
                                fontFamily = lalezarFamily,
                                color = Color.White
                            )
                        }

                        Spacer(modifier = Modifier.height(15.dp))

                        Text(
                            text = usuario.biografia,
                            fontSize = 20.sp,
                            fontFamily = lalezarFamily,
                            color = Color.White
                        )

                        Spacer(modifier = Modifier.height(15.dp))

                        FlowRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp)
                                .verticalScroll(rememberScrollState()),
                            Arrangement.Center
                        ) {
                            usuario.jogos_favoritos.forEach { jogo ->
                                OutlinedCard(
                                    modifier = Modifier
                                        .padding(10.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color.Transparent
                                    ),
                                    shape = RoundedCornerShape(20.dp),
                                    border = BorderStroke(2.dp, Color(65, 80, 183))
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .height(40.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = jogo,
                                            modifier = Modifier
                                                .padding(5.dp, 0.dp),
                                            fontSize = 20.sp,
                                            fontFamily = lalezarFamily,
                                            color = Color(65, 80, 183),
                                        )
                                    }
                                }
                            }

                            usuario.generos_favoritos.forEach { genero ->
                                OutlinedCard(
                                    modifier = Modifier
                                        .padding(10.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color.Transparent
                                    ),
                                    shape = RoundedCornerShape(20.dp),
                                    border = BorderStroke(2.dp, Color(76, 175, 80, 255))
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .height(40.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = genero,
                                            modifier = Modifier
                                                .padding(5.dp, 0.dp),
                                            fontSize = 20.sp,
                                            fontFamily = lalezarFamily,
                                            color = Color(76, 175, 80, 255),
                                        )
                                    }
                                }
                            }

                            usuario.consoles.forEach { console ->
                                OutlinedCard(
                                    modifier = Modifier
                                        .padding(10.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color.Transparent
                                    ),
                                    shape = RoundedCornerShape(20.dp),
                                    border = BorderStroke(2.dp, Color(255, 152, 0, 255))
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .height(40.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = console,
                                            modifier = Modifier
                                                .padding(5.dp, 0.dp),
                                            fontSize = 20.sp,
                                            fontFamily = lalezarFamily,
                                            color = Color(255, 152, 0, 255),
                                        )
                                    }
                                }
                            }

                            usuario.interesses.forEach { interesse ->
                                OutlinedCard(
                                    modifier = Modifier
                                        .padding(10.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color.Transparent
                                    ),
                                    shape = RoundedCornerShape(20.dp),
                                    border = BorderStroke(2.dp, Color(244, 67, 54, 255))
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .height(40.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = interesse,
                                            modifier = Modifier
                                                .padding(5.dp, 0.dp),
                                            fontSize = 20.sp,
                                            fontFamily = lalezarFamily,
                                            color = Color(244, 67, 54, 255),
                                        )
                                    }
                                }
                            }
                        }
                        Row {
                            Button(onClick = {
                                showPerfil = true
                            },
                                modifier = Modifier
                                    .height(50.dp)
                                    .width(150.dp),
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(
                                    Color(65, 80, 183)
                                )
                            ) {
                                Text(
                                    text = "PERFIL",
                                    fontSize = 25.sp,
                                    fontFamily = lalezarFamily,
                                    color = Color.White
                                )
                            }

                            Spacer(modifier = Modifier.width(10.dp))

                            Button(onClick = {
                                val sairConta = Intent(contexto, MainActivity::class.java)

                                contexto.startActivity(sairConta)
                            },
                                modifier = Modifier
                                    .height(50.dp)
                                    .width(150.dp),
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(
                                    Color(65, 80, 183)
                                )
                            ) {
                                Text(
                                    text = "SAIR",
                                    fontSize = 25.sp,
                                    fontFamily = lalezarFamily,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    if (showDialog) {
        LaunchedEffect(Unit) {
            delay(1000)
            showDialog = false
        }

        Dialog(onDismissRequest = { showDialog = false }) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(100.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("❤️")
            }

        }

        if (showText) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Yellow),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "Texto Exemplo",
                    modifier = Modifier.padding(16.dp),
                    )
            }
        }
    }
}

suspend fun fetchUsuarios(): List<Usuario> {
    val usuarios = mutableListOf<Usuario>()
    val querySnapshot = Firebase.firestore.collection("users").get().await()
    for (document in querySnapshot.documents) {
        document.toObject(Usuario::class.java)?.let { usuarios.add(it) }
    }
    return usuarios
}

suspend fun fetchUsuarioLogado(): Usuario? {
    val usuarioId = Firebase.auth.currentUser?.uid
    usuarioId?.let {
        val querySnapshot = Firebase.firestore.collection("users")
            .whereEqualTo("id_usuario", usuarioId)
            .get()
            .await()

        for (document in querySnapshot.documents) {
            return document.toObject(Usuario::class.java)
        }
    }
    return null
}