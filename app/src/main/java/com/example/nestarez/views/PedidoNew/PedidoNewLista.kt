package com.example.nestarez.views.PedidoNew

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.nestarez.Listas.ListaDetallePedido
import com.example.nestarez.Listas.calcularTotal
import com.example.nestarez.Listas.limpiarLista
import com.example.nestarez.LogicaNegocio.Entidades.ClienteEntidad
import com.example.nestarez.LogicaNegocio.Entidades.DetallePedidoEntidad
import com.example.nestarez.LogicaNegocio.Entidades.PedidoEntidad
import com.example.nestarez.LogicaNegocio.Entidades.ProductoEntidad
import com.example.nestarez.LogicaNegocio.ManejadorF.ManejadorClientes
import com.example.nestarez.LogicaNegocio.ManejadorF.ManejadorPedidos

import com.example.nestarez.componentesUI.BotonGenerico
import com.example.nestarez.componentesUI.BotonGenericoLista
import com.example.nestarez.componentesUI.CajaTextoGenerico
import com.example.nestarez.componentesUI.DialogoCliente
import com.example.nestarez.componentesUI.ElementosDetallePedido
import com.example.nestarez.componentesUI.ElementosProductoPedidoNew
import com.example.nestarez.componentesUI.MensajeToast
import com.google.firebase.Timestamp
import java.time.LocalDate

//val ListaDetallePedido = mutableStateListOf<DetallePedidoEntidad>()

@Composable
fun ListaPedidoNew() {
    val FRClientes = ManejadorClientes()
    val FRPedido = ManejadorPedidos()

    // Usar remember para que total se actualice reactivo
    var total by remember { mutableStateOf(0.0) }

    // Estado para el dialogo
    var verDialogo by remember { mutableStateOf(false) }

    // Contexto para usar en el dialogo
    val contexto = LocalContext.current

    // Estado mutable para el detalle seleccionado (para editar cantidades)
    var seleccionDetalle by remember { mutableStateOf<DetallePedidoEntidad?>(null) }

    // Lista global de detalles de pedido
    val listaDetallePedidoState = remember { ListaDetallePedido }

    // Box principal para contener la UI
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp, start = 20.dp, end = 20.dp)
            .shadow(50.dp, shape = RoundedCornerShape(16.dp))
            .background(Color(0XFFFFEBEB), shape = RoundedCornerShape(16.dp))
    ) {
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceAround) {
            // Mostrar el dialogo de cliente si se activa
            if (verDialogo) {
                total = calcularTotal()  // Recalcular total al abrir el dialogo
                DialogoCliente(
                    cancelaAction = {
                        verDialogo = false
                    },
                    aceptaAction = { cliente ->
                        // Agregar cliente si no existe
                        FRClientes.agregarCliente(cliente)

                        // Agregar pedido con detalles
                        FRPedido.agregarPedidoConDetallesAnidados(
                            PedidoEntidad(
                                id_cliente = cliente.id_cliente!!,
                                nombre_cliente = cliente.nombre_lower,
                                fecha_pedido = Timestamp.now(),
                                total = total,
                                estado = "Pendiente"
                            ),
                            listaDetallePedidoState, // Usar lista global
                            onSuccess = {
                                Log.d("Firestore", "Pedido y detalles agregados exitosamente")
                            },
                            onFailure = { e ->
                                Log.e("Firestore", "Error al agregar pedido y detalles", e)
                            }
                        )
                        // Limpiar lista de detalles y cerrar el dialogo
                        limpiarLista()
                        verDialogo = false
                    }
                )
            }
            // Mostrar los productos seleccionados en LazyColumn
            if (listaDetallePedidoState.isNotEmpty()) {
                LazyColumn(modifier = Modifier.fillMaxHeight(0.85f)) {
                    items(listaDetallePedidoState) { element ->
                        // Mostrar cada detalle con opción de añadir o eliminar
                        ElementosDetallePedido(detallePedidoEntidad = element, onAdd = {
                            element.cantidad++
                            element.subtotal = element.precio_unitario * element.cantidad
                            total = calcularTotal()  // Recalcular total al modificar cantidad
                        }, onDelete = {
                            element.cantidad--
                            element.subtotal = element.precio_unitario * element.cantidad
                            total = calcularTotal()  // Recalcular total al eliminar cantidad

                            if (element.cantidad == 0) {
                                listaDetallePedidoState.remove(element)
                            }
                        })
                    }
                }

                // Botones para cancelar o finalizar pedido
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    BotonGenericoLista(texto = "Cancelar", icono = Icons.Default.Clear) {
                        limpiarLista()  // Limpia la lista de detalles
                    }
                    BotonGenericoLista(texto = "Finalizar", icono = Icons.Default.Add) {
                        verDialogo = true
                    }
                }
            } else {
                // Mensaje si no hay productos seleccionados
                Text(
                    text = "No hay productos\n\nseleccionados",
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = Color(0xFFBF360C) // Rojo quemado
                )
            }
        }
    }
}