package com.example.nestarez.LogicaNegocio.Entidades

data class ProductoEntidad(
    var id_producto: String? = null,
    var nombre_producto: String = "",
    var nombre_lower:String = "",
    var descripcion: String = "",
    var categoria: String = "",
    var precio: Double = 0.0,
    var stock: Int = 0
)