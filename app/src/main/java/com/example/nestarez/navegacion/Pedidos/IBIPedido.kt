package com.example.nestarez.navegacion.Pedidos

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.PendingActions
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.nestarez.navegacion.ElementoNav

sealed class IBIPedido(val icono: ImageVector, val titulo: String, val ruta: String) {
    object ItemInicio :
        IBIPedido(Icons.Default.PendingActions, "Pendientes", ElementoNav.PedidoPrincipal.ruta)
    object ItemLista :
        IBIPedido(Icons.Default.CheckCircle, "Finalizados", ElementoNav.PedidoLista.ruta)
}