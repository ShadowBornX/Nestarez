package com.example.nestarez.views.Pedido

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nestarez.LogicaNegocio.Entidades.DetallePedidoEntidad
import com.example.nestarez.LogicaNegocio.Entidades.PedidoEntidad
import com.example.nestarez.LogicaNegocio.Entidades.ProductoEntidad
import com.example.nestarez.LogicaNegocio.ManejadorF.ManejadorPedidos
import com.example.nestarez.R
import com.example.nestarez.componentesUI.CajaTextoGenericoIcon
import com.example.nestarez.componentesUI.DialogoConfirm
import com.example.nestarez.componentesUI.TopBarraRetorno
import com.example.nestarez.fonts.fontInria

@Composable
fun PedidoInicio() {
    val FRpedido = ManejadorPedidos()
    var pedidoB by remember { mutableStateOf("") }
    val listapedidos by FRpedido.buscarPedidosPorNombre(pedidoB.toLowerCase(),"Pendiente").collectAsState(initial = emptyList())
    var detallesPedido by remember { mutableStateOf<List<DetallePedidoEntidad>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }
    // Estado para controlar qué pedido está expandido
    var expandedPedidoId by remember { mutableStateOf<String?>(null) }


    val valLetra = remember { Regex("[A-Za-z\\s]*") }

    var abrirDialogoConfirm by remember { mutableStateOf(false) }
    var seleccionPedido by remember { mutableStateOf<PedidoEntidad?>(null) }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp, start = 20.dp, end = 20.dp)
            .shadow(50.dp, shape = RoundedCornerShape(16.dp))
            .background(Color(0XFFFFEBEB), shape = RoundedCornerShape(16.dp))
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CajaTextoGenericoIcon(
                icon = Icons.Default.Search,
                valor = pedidoB,
                label = "Buscar Nombre",
                with = 1f
            ) {
                if (it.matches(valLetra)) {
                    pedidoB = it
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            LazyColumn {
                items(listapedidos) { element ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        colors = CardDefaults.cardColors(Color(0XFFFFD4BC))
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(5.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = element.nombre_cliente.toUpperCase(), color = Color.Black,
                                fontFamily = fontInria,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.weight(1f)) // Espacio flexible
                            Text(
                                text = "S/ ${element.total}",
                                color = Color.Black,
                                fontFamily = fontInria,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                            IconButton(onClick = {
                                isLoading = true
                                FRpedido.obtenerDetallesPedido(
                                    idPedido = element.id_pedido.toString(),
                                    onSuccess = { detalles ->
                                        detallesPedido =
                                            detalles // Guardar detalles en el estado
                                        isLoading = false // Finaliza el loading
                                    },
                                    onFailure = { e -> // Guardar mensaje de error
                                        isLoading = false // Finaliza el loading
                                    }
                                )
                                expandedPedidoId =
                                    if (expandedPedidoId == element.id_pedido) null else element.id_pedido
                            }) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(id = R.drawable.add),
                                    contentDescription = "",
                                    tint = Color(0xFF5B2626)
                                )
                            }
                        }
                    }

                    // Mostrar detalles si el pedido está expandido
                    if (expandedPedidoId == element.id_pedido) {
                        if (isLoading) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) { CircularProgressIndicator(modifier = Modifier.padding(16.dp)) }

                        } else if (detallesPedido.isNotEmpty()) {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(5.dp),
                                colors = CardDefaults.cardColors(Color(0x00E5E5E5))
                            ) {
                                Column(
                                    modifier = Modifier.padding(5.dp)

                                ) {
                                    detallesPedido.forEach { detalle ->
                                        Card(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(1.dp),
                                            colors = CardDefaults.cardColors(Color(0xFFE5E5E5))
                                        ) {

                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(vertical = 2.dp),
                                                horizontalArrangement = Arrangement.SpaceEvenly,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Box(
                                                    modifier = Modifier
                                                        .fillMaxWidth(0.1f)
                                                        .background(Color.Transparent),
                                                    contentAlignment = Alignment.Center
                                                ) {
                                                    Text(
                                                        text = "${detalle.cantidad}",
                                                        fontWeight = FontWeight.Bold,
                                                        color = Color.Black,
                                                        textAlign = TextAlign.Center,
                                                        fontFamily = fontInria,
                                                    )
                                                }
                                                Box(
                                                    modifier = Modifier
                                                        .fillMaxWidth(0.7f)
                                                        .background(Color.Transparent)
                                                        .drawBehind {
                                                            val strokeWidth = 1.dp.toPx()
                                                            val color = Color.Black
                                                            // Borde izquierdo
                                                            drawLine(
                                                                color,
                                                                Offset(0f, 0f),
                                                                Offset(0f, size.height),
                                                                strokeWidth
                                                            )
                                                            // Borde derecho
                                                            drawLine(
                                                                color,
                                                                Offset(size.width, 0f),
                                                                Offset(size.width, size.height),
                                                                strokeWidth
                                                            )
                                                        },
                                                    contentAlignment = Alignment.Center
                                                ) {
                                                    Text(
                                                        text = detalle.nombre_producto,
                                                        fontWeight = FontWeight.Bold,
                                                        color = Color.Black,
                                                        fontFamily = fontInria,
                                                        textAlign = TextAlign.Center,
                                                        modifier = Modifier.padding(horizontal = 5.dp)
                                                    )
                                                }
                                                Box(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .background(Color.Transparent)
                                                        .drawBehind {
                                                            val strokeWidth = 1.dp.toPx()
                                                            val color = Color.Black
                                                            // Borde izquierdo
                                                            drawLine(
                                                                color,
                                                                Offset(0f, 0f),
                                                                Offset(0f, size.height),
                                                                strokeWidth
                                                            )
                                                        },
                                                    contentAlignment = Alignment.Center
                                                ) {
                                                    Text(
                                                        text = "S/ ${detalle.subtotal}",
                                                        fontWeight = FontWeight.Bold,
                                                        color = Color.Black,
                                                        fontFamily = fontInria,
                                                        textAlign = TextAlign.Center
                                                    )
                                                }
                                            }
                                            //HorizontalDivider(color = Color.Black)
                                        }
                                        Spacer(modifier = Modifier.height(5.dp))

                                        //Text(text = "Detalle: ${detalle.nombre_producto} - S/. ${detalle.subtotal}")
                                    }

                                    Button(
                                        onClick = {
                                            element.estado = "Finalizado"
                                            abrirDialogoConfirm = true
                                            seleccionPedido = element
                                        },
                                        modifier = Modifier.fillMaxWidth(),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = Color(0xFFD84315), // Verde para aceptar
                                            contentColor = Color.White // Texto blanco
                                        ),
                                        shape = RoundedCornerShape(8.dp)
                                    ) {
                                        Text(
                                            text = "Finalizar",
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                        }

                    }
                }
            }
        }
        if (abrirDialogoConfirm && seleccionPedido != null) {
            DialogoConfirm(
                "¿Desea Finalizar el Pedido?",
                cancelaAction = { abrirDialogoConfirm = false }) {
                seleccionPedido!!.id_pedido?.let { FRpedido.actualizarPedido(seleccionPedido!!) }
                abrirDialogoConfirm = false
            }
        }

    }
}
