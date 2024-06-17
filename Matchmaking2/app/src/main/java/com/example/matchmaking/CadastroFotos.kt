package com.example.matchmaking

import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.matchmaking.ui.theme.MatchmakingTheme
import com.example.matchmaking.ui.theme.lalezarFamily
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await
import java.util.*
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.google.firebase.firestore.FieldValue

class CadastroFotos : ComponentActivity() {
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
                    TelaCadastroFotos(extras)
                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun TelaCadastroFotos(extras: Bundle?) {
    val contexto = LocalContext.current
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
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
            text = "Escolha Até 6 Fotos",
            fontSize = 25.sp,
            fontFamily = lalezarFamily,
            color = Color(65, 80, 183)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 20.dp),
        ) {
            PhotoCard(onClick = {
                galleryLauncher.launch("image/*")
            })

            Spacer(modifier = Modifier.width(10.dp))

            PhotoCard(onClick = {
                galleryLauncher.launch("image/*")
            })

            Spacer(modifier = Modifier.width(10.dp))

            PhotoCard(onClick = {
                galleryLauncher.launch("image/*")
            })
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 20.dp),
        ) {
            PhotoCard(onClick = {
                galleryLauncher.launch("image/*")
            })

            Spacer(modifier = Modifier.width(10.dp))

            PhotoCard(onClick = {
                galleryLauncher.launch("image/*")
            })

            Spacer(modifier = Modifier.width(10.dp))

            PhotoCard(onClick = {
                galleryLauncher.launch("image/*")
            })
        }

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(0.dp, 20.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            Button(onClick = {
                val voltarCadastroCore = Intent(contexto, CadastroCore::class.java)

                contexto.startActivity(voltarCadastroCore)
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
                val cadastrarTags = Intent(contexto, CadastroTags::class.java)

                selectedImageUri?.let { uri ->
                    uploadImageToFirebase(uri)
                }

                contexto.startActivity(cadastrarTags)
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

@Composable
fun PhotoCard(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .shadow(
                elevation = 10.dp,
                shape = RoundedCornerShape(16.dp)
            )
            .height(160.dp)
            .width(110.dp)
            .background(Color.White, RoundedCornerShape(16.dp))
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(5.dp)
                .height(30.dp)
                .width(30.dp)
                .background(Color(42, 62, 185), RoundedCornerShape(50.dp))
                .clickable { onClick() }
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
fun GreetingPreview3() {
    MatchmakingTheme {
        TelaCadastroFotos(null)
    }
}

fun uploadImageToFirebase(uri: Uri) {
    val storage = Firebase.storage
    val storageRef = storage.reference
    val auth = Firebase.auth
    val user = auth.currentUser
    val uid = user?.uid

    uid?.let {
        val imageRef = storageRef.child("images/${UUID.randomUUID()}.jpg")

        imageRef.putFile(uri)
            .addOnSuccessListener {
                imageRef.downloadUrl.addOnSuccessListener { downloadUri ->
                    saveUriToFirestore(downloadUri.toString(), uid)
                }
            }
            .addOnFailureListener { exception ->
                Log.d("Firebase Storage", "Erro ao salvar imagem", exception)
            }
    }
}

fun saveUriToFirestore(downloadUri: String, uid: String) {
    val db = Firebase.firestore

    val userDocRef = db.collection("users").document(uid)
    userDocRef.update("foto_perfil", downloadUri)
        .addOnSuccessListener {
            Log.d("Firestore", "Atualização bem-sucedida")
        }
        .addOnFailureListener { exception ->
            Log.d("Firestore", "Erro ao atualizar", exception)
        }
}