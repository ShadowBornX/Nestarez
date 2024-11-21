package com.example.nestarez.componentesUI

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nestarez.LogicaNegocio.Entidades.DetallePedidoEntidad
import com.example.nestarez.fonts.fontInria

@Composable
fun ElementosDetallePedido(
    detallePedidoEntidad: DetallePedidoEntidad,
    onAdd: () -> Unit,  // Callback para actualizar la cantidad (sumar)
    onDelete: () -> Unit  // Callback para actualizar la cantidad (restar)
) {
    // Subtotal recalculado al cambiar la cantidad
    var cantidad by remember { mutableStateOf(detallePedidoEntidad.cantidad) }
    var precio by remember { mutableStateOf(detallePedidoEntidad.precio_unitario * cantidad) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        colors = CardDefaults.cardColors(Color(0xFFFFD4BC))
    ) {
        Column(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = detallePedidoEntidad.nombre_producto,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontSize = 20.sp,
            )
            Row(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .fillMaxWidth(0.7f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    // Decrease quantity (onDelete)
                    IconButtonGenerico(
                        icono = Icons.Default.RemoveCircle,
                        color = Color(0xFFEF5350)
                    ) {

                        cantidad -= 1
                        precio -= detallePedidoEntidad.precio_unitario

                        onDelete()  // Disminuir cantidad
                    }

                    // Display current quantity
                    Text(
                        text = cantidad.toString(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        fontFamily = fontInria,
                        color = Color(0xFF2E7D32) // Verde oscuro
                    )

                    // Increase quantity (onAdd)
                    IconButtonGenerico(
                        icono = Icons.Default.AddCircle,
                        color = Color(0xFF4CAF50)
                    ) {
                        cantidad += 1
                        precio += detallePedidoEntidad.precio_unitario
                        onAdd()  // Aumentar cantidad
                    }
                }
                // Mostrar el subtotal recalculado
                Text(
                    text = "S/. ${"%.2f".format(precio)}",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontSize = 16.sp
                )
            }
        }
    }
}