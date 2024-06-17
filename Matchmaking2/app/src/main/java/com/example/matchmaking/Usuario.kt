package com.example.matchmaking

data class Usuario(
    val nome: String = "",
    val sobrenome: String = "",
    val username: String = "",
    val biografia: String = "",
    val dt_nascimento: String = "",
    val identidadeGenero: String = "",
    val foto_perfil: String = "",
    val nota: Double = 0.0,
    val consoles: List<String> = listOf(),
    val generos_favoritos: List<String> = listOf(),
    val interesses: List<String> = listOf(),
    val jogos_favoritos: List<String> = listOf(),
)