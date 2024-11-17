package com.example.nestarez.views.PedidoNew

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.rememberNavController
import com.example.nestarez.LogicaNegocio.Entidades.ClienteEntidad
import com.example.nestarez.LogicaNegocio.Entidades.DetallePedidoEntidad
import com.example.nestarez.LogicaNegocio.Entidades.PedidoEntidad
import com.example.nestarez.LogicaNegocio.Entidades.ProductoEntidad
import com.example.nestarez.LogicaNegocio.ManejadorF.ManejadorClientes
import com.example.nestarez.LogicaNegocio.ManejadorF.ManejadorPedidos
import com.example.nestarez.componentesUI.BotonGenerico
import com.example.nestarez.componentesUI.CajaTextoGenerico
import com.example.nestarez.componentesUI.ElementosDetallePedido
import com.example.nestarez.componentesUI.ElementosProductoPedidoNew
import java.time.LocalDate

val ListaDetallePedido = mutableStateListOf<DetallePedidoEntidad>()

@Composable
fun ListaPedidoNew() {
    val FRClientes = ManejadorClientes()
    val FRPedido = ManejadorPedidos()
    var total = 0.0
    val fecha = LocalDate.now()
    var DNI by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var verDialogo by remember { mutableStateOf(false) }
    val valNum = remember { Regex("^\\d+(\\.\\d+)?\$") }
    var seleccionDetalle by remember { mutableStateOf<DetallePedidoEntidad?>(null) }
    Column {
        LazyColumn {
            items(ListaDetallePedido) { element ->

                ElementosDetallePedido(detallePedidoEntidad = element) {
                    seleccionDetalle = element

                }

            }
        }
        BotonGenerico(texto = "Finalizar", icono = Icons.Default.Add) {
            verDialogo = true
        }

        if (verDialogo) {
            ListaDetallePedido.forEach() { element ->
                total += element.subtotal
            }
            AlertDialog(onDismissRequest = { /*TODO*/ }, confirmButton = {
                Button(onClick = {
                    FRClientes.agregarCliente(ClienteEntidad(DNI, nombre, direccion, telefono))
                    FRPedido.agregarPedidoConDetallesAnidados(PedidoEntidad(
                        id_cliente = DNI,
                        fecha_pedido = fecha.toString(),
                        total = total,
                        estado = "Pendiente"
                    ),
                        ListaDetallePedido,
                        onSuccess = {
                            Log.d(
                                "Firestore",
                                "Pedido y detalles agregados exitosamente"
                            )
                        },
                        onFailure = { e ->
                            Log.e(
                                "Firestore",
                                "Error al agregar pedido y detalles",
                                e
                            )
                        })
                    ListaDetallePedido.clear()
                    verDialogo = false
                }) {
                    Text(text = "Ok")
                }
            }, text = {
                Column {
                    CajaTextoGenerico(valor = DNI, label = "DNI O RUC (Obligatorio)") {
                        if (it.matches(valNum) || it.isEmpty()) {
                            DNI = it
                        }
                    }
                    CajaTextoGenerico(valor = nombre, label = "Nombre o Razon Social (Opcional)") {
                        nombre = it
                    }
                    CajaTextoGenerico(valor = direccion, label = "Direccion (Opcional)") {
                        direccion = it
                    }
                    CajaTextoGenerico(valor = telefono, label = "Telefono (Opcional)") {
                        telefono = it
                    }
                }
            })
        }
    }
}