package com.example.nestarez.componentesUI.Listas

import androidx.compose.runtime.mutableStateListOf
import com.example.nestarez.LogicaNegocio.Entidades.DetallePedidoEntidad

val ListaDetallePedido = mutableStateListOf<DetallePedidoEntidad>()

fun calcularTotal(): Double {
    return ListaDetallePedido.sumOf { it.subtotal }
}

fun limpiarLista() {
    ListaDetallePedido.clear()
}