package com.example.nestarez.LogicaNegocio.Entidades

import com.google.firebase.Timestamp

data class PedidoEntidad(
    var id_pedido: String? = null,
    var id_cliente: String = "", // Referencia al id de cliente
    var nombre_cliente: String = "",
    var fecha_pedido: Timestamp? = null,
    var total: Double = 0.0,
    var estado: String = ""
)
