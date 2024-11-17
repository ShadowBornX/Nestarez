package com.example.nestarez.LogicaNegocio.Entidades

data class ClienteEntidad(
    var id_cliente: String? = null, // Usamos String para la clave en Firebase
    var nombre: String = "",
    var direccion: String = "",
    var telefono: String = ""
)