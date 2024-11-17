package com.example.nestarez.LogicaNegocio.Entidades

data class PedidoEntidad(
    var id_pedido: String? = null,
    var id_cliente: String = "", // Referencia al id de cliente
    var fecha_pedido: String = "",
    var total: Double = 0.0,
    var estado: String = ""
)
