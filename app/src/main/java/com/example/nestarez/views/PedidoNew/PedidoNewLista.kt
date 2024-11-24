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
import androidx.compose.runtime.mutableStateMapOf
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
import com.example.nestarez.LogicaNegocio.ManejadorF.ManejadorProductos

import com.example.nestarez.componentesUI.BotonGenerico
import com.example.nestarez.componentesUI.BotonGenericoLista
import com.example.nestarez.componentesUI.CajaTextoGenerico
import com.example.nestarez.componentesUI.DialogoCliente
import com.example.nestarez.componentesUI.ElementosDetallePedido
import com.example.nestarez.componentesUI.ElementosProductoPedidoNew
import com.example.nestarez.componentesUI.MensajeToast
import com.google.firebase.Timestamp
import java.time.LocalDate

@Composable
fun ListaPedidoNew() {
    val FRClientes = ManejadorClientes()
    val FRPedido = ManejadorPedidos()
    val FRProductos = ManejadorProductos()
    val total = remember { mutableStateOf(0.0) }
    val verDialogo = remember { mutableStateOf(false) }
    val stockMap = remember { mutableStateMapOf<String, Int>() }

    // Crear una copia reactiva de ListaDetallePedido
    val listaCopia = remember { mutableStateListOf<DetallePedidoEntidad>() }

    // Sincronizar la lista local con la global al cargar la vista
    LaunchedEffect(ListaDetallePedido) {
        listaCopia.clear()
        listaCopia.addAll(ListaDetallePedido)
    }

    // Cargar el stock inicial para todos los productos
    LaunchedEffect(Unit) {
        listaCopia.forEach { detalle ->
            FRProductos.obtenerStockActual(detalle.id_producto) { stockActual ->
                if (stockActual != null) {
                    stockMap[detalle.id_producto] = stockActual
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp, start = 20.dp, end = 20.dp)
            .shadow(50.dp, shape = RoundedCornerShape(16.dp))
            .background(Color(0XFFFFEBEB), shape = RoundedCornerShape(16.dp))
    ) {
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceAround) {
            if (verDialogo.value) {
                total.value = listaCopia.sumOf { it.subtotal } // Usar la copia para el cálculo
                DialogoCliente(
                    cancelaAction = { verDialogo.value = false },
                    aceptaAction = { cliente ->
                        FRClientes.agregarCliente(cliente)
                        FRPedido.agregarPedidoConDetallesAnidados(
                            PedidoEntidad(
                                id_cliente = cliente.id_cliente!!,
                                nombre_cliente = cliente.nombre_lower,
                                fecha_pedido = Timestamp.now(),
                                total = total.value,
                                estado = "Pendiente"
                            ),
                            listaCopia, // Usar la copia para enviar los detalles
                            onSuccess = { Log.d("Firestore", "Pedido agregado") },
                            onFailure = { e -> Log.e("Firestore", "Error al agregar pedido", e) }
                        )
                        FRProductos.actualizarStockPorDetalles(listaCopia)
                        listaCopia.clear()
                        verDialogo.value = false
                    }
                )
            }

            if (listaCopia.isNotEmpty()) {
                LazyColumn(modifier = Modifier.fillMaxHeight(0.85f)) {
                    items(listaCopia, key = { it.id_producto }) { element ->
                        val stockDisponible = stockMap[element.id_producto] ?: 0
                        ElementosDetallePedido(detallePedidoEntidad = element, stock = stockDisponible, onAdd = {
                            if (element.cantidad < stockDisponible) {
                                element.cantidad++
                                element.subtotal = element.precio_unitario * element.cantidad
                                total.value = listaCopia.sumOf { it.subtotal }
                                // Sincronizar con la lista global
                                ListaDetallePedido.clear()
                                ListaDetallePedido.addAll(listaCopia)
                            }
                        }, onDelete = {
                            element.cantidad--
                            element.subtotal = element.precio_unitario * element.cantidad
                            total.value = listaCopia.sumOf { it.subtotal }

                            if (element.cantidad == 0) {
                                listaCopia.remove(element)
                            }
                            // Sincronizar con la lista global
                            ListaDetallePedido.clear()
                            ListaDetallePedido.addAll(listaCopia)
                        })
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    BotonGenericoLista(texto = "Cancelar", icono = Icons.Default.Clear) {
                        listaCopia.clear()
                        ListaDetallePedido.clear() // Limpiar también la lista global
                    }
                    BotonGenericoLista(texto = "Finalizar", icono = Icons.Default.Add) {
                        verDialogo.value = true
                    }
                }
            } else {
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
