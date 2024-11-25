package com.example.nestarez.componentesUI

import android.graphics.Typeface
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nestarez.LogicaNegocio.Entidades.DetallePedidoEntidad
import com.example.nestarez.LogicaNegocio.Entidades.ProductoEntidad
import com.example.nestarez.R
import com.example.nestarez.componentesUI.fonts.fontInria

@Composable
fun ElementosDetallePedido(
    detallePedidoEntidad: DetallePedidoEntidad,
    stock: Int,
    onAdd: () -> Unit,
    onDelete: () -> Unit
) {
    // Usamos el ID del producto como clave única
    var cantidad by remember { mutableStateOf(detallePedidoEntidad.cantidad) }
    var precio by remember { mutableStateOf(detallePedidoEntidad.precio_unitario * cantidad) }

    // Sincronizamos la cantidad y el precio con la entidad
    LaunchedEffect(cantidad) {
        detallePedidoEntidad.cantidad = cantidad
        detallePedidoEntidad.subtotal = precio
    }

    // Usamos 'key' para asegurar que cada item tenga un estado independiente
    key(detallePedidoEntidad.id_producto) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            colors = CardDefaults.cardColors(Color(0xFFFFD4BC))
        ) {
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = detallePedidoEntidad.nombre_producto,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontSize = 20.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .fillMaxWidth(0.7f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        IconButtonGenerico(
                            icono = Icons.Default.RemoveCircle,
                            color = Color(0xFFEF5350)
                        ) {
                            if (cantidad > 0) {
                                cantidad -= 1
                                precio -= detallePedidoEntidad.precio_unitario
                                onDelete()
                            }
                        }
                        Text(
                            text = cantidad.toString(),
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            fontFamily = fontInria,
                            color = Color(0xFF2E7D32) // Verde oscuro
                        )
                        IconButtonGenerico(
                            icono = Icons.Default.AddCircle,
                            color = Color(0xFF4CAF50)
                        ) {
                            if (cantidad < stock) {
                                cantidad += 1
                                precio += detallePedidoEntidad.precio_unitario
                                onAdd()
                            }
                        }
                    }
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
}

@Composable
fun ElementosProducto(
    producto: ProductoEntidad,
    onDetail: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        colors = CardDefaults.cardColors(Color(0xFFFFD4BC))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(0.80f),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = producto.nombre_producto,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontFamily = FontFamily(typeface = Typeface.DEFAULT_BOLD)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "S/ ${producto.precio}", color = Color(0xFFA44949),
                        fontFamily = FontFamily(typeface = Typeface.MONOSPACE),
                        modifier = Modifier.fillMaxWidth(0.4f)
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(0.9f),
                        horizontalArrangement = Arrangement.End
                    ) {
                        IconButtonGenerico(icono = ImageVector.vectorResource(id = R.drawable.info)) { onDetail() }
                        IconButtonGenerico(icono = ImageVector.vectorResource(id = R.drawable.edit)) { onEdit() }
                        IconButtonGenerico(icono = ImageVector.vectorResource(id = R.drawable.delete)) { onDelete() }
                    }
                }
            }
            Icon(
                imageVector = obtenerIconoPorCategoria(producto.categoria),
                contentDescription = null,
                modifier = Modifier.size(50.dp),
                tint = Color(0xFF996262)
            )
            /*VerticalDivider(modifier = Modifier.padding(3.dp))
            Column(
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(text = "S/${producto.precio}")
                Text(text = "Stock:\n${producto.stock}")
            }
            VerticalDivider(modifier = Modifier.padding(3.dp))*/


        }
    }
    Spacer(modifier = Modifier.padding(2.dp))
    //invocar a la funcion del dialogo

}


@Composable
fun ElementosProductoPedidoNew(
    producto: ProductoEntidad,
    onAdd: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        colors = CardDefaults.cardColors(Color(0XFFFFD4BC))
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(0.75f),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = producto.nombre_producto,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontSize = 20.sp
                )
                Text(text = "S/${producto.precio}", color = Color.Black)
            }
            IconButton(onClick = { onAdd() }) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.add),
                    contentDescription = "",
                    tint = Color(0xFF5B2626)
                )
            }
        }
    }

    Spacer(modifier = Modifier.padding(2.dp))
    //invocar a la funcion del dialogo

}