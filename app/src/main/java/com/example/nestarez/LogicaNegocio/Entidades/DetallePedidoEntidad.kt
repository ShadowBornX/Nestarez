package com.example.nestarez.LogicaNegocio.Entidades

data class DetallePedidoEntidad(
    var id_detalle: String? = null,
    var id_producto: String = "", // Referencia al id de producto
    var nombre_producto:String = "",
    var cantidad: Int = 0,
    var precio_unitario: Double = 0.0,
    var subtotal: Double = 0.0
)
