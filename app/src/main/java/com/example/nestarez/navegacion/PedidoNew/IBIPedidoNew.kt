package com.example.nestarez.navegacion.PedidoNew

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.nestarez.navegacion.ElementoNav

sealed class IBIPedidoNew(val icono: ImageVector, val titulo: String, val ruta: String) {
    object ItemInicio :
        IBIPedidoNew(Icons.Default.Search, "Buscar", ElementoNav.PedidosNewPrincipal.ruta)
    object ItemLista :
        IBIPedidoNew(Icons.Default.Checklist, "Lista", ElementoNav.PedidosNewLista.ruta)
}