package com.example.nestarez.navegacion.Productos

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.nestarez.navegacion.ElementoNav

sealed class IBIProductos(val icono: ImageVector, val titulo: String, val ruta: String) {
    object ItemInicio :
        IBIProductos(Icons.Default.Search, "Buscar", ElementoNav.ProductosPrincipal.ruta)
    object ItemNew :
        IBIProductos(Icons.Default.Add, "Nuevo", ElementoNav.ProductosNew.ruta)
}