package com.example.nestarez.componentesUI

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nestarez.Listas.ListaDetallePedido
import com.example.nestarez.LogicaNegocio.Entidades.ClienteEntidad
import com.example.nestarez.LogicaNegocio.Entidades.DetallePedidoEntidad
import com.example.nestarez.LogicaNegocio.Entidades.PedidoEntidad
import com.example.nestarez.LogicaNegocio.Entidades.ProductoEntidad
import com.example.nestarez.LogicaNegocio.ManejadorF.ManejadorClientes
import com.example.nestarez.LogicaNegocio.ManejadorF.ManejadorPedidos
import com.example.nestarez.fonts.fontFredoka
import com.example.nestarez.fonts.fontInria
import com.google.firebase.Timestamp
import java.time.LocalDate

import java.util.Locale

@Composable
fun DialogoAgregar(
    producto: ProductoEntidad,
    listaAction: () -> Unit
) {
    var cantidad by remember { mutableStateOf(1) }
    var subtotal by remember { mutableStateOf(producto.precio) }

    AlertDialog(
        containerColor = Color(0xFFE8F5E9), // Fondo verde suave
        onDismissRequest = {},
        dismissButton = {
            TextButton(onClick = { listaAction() }) {
                Text(
                    text = "Cancelar",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color(0xFFEF5350) // Rojo quemado
                )
            }
        },
        title = {
            Text(
                text = "Agregar Producto",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2E7D32), // Verde oscuro
                textAlign = TextAlign.Center,
                fontFamily = fontFredoka,
                modifier = Modifier.fillMaxWidth()
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = producto.nombre_producto,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4E342E), // Verde intermedio
                    fontFamily = fontInria,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
                Row(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Subtotal:",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        fontFamily = fontInria,
                        color = Color(0xFF4E342E) // Verde más claro
                    )
                    Text(
                        text = "S/${"%.2f".format(subtotal)}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = fontInria,
                        color = Color(0xFF1B5E20) // Verde oscuro
                    )
                }
                Row(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    IconButtonGenerico(
                        icono = Icons.Default.RemoveCircle,
                        color = Color(0xFFEF5350)
                    ) {
                        if (cantidad > 1) {
                            cantidad -= 1
                            subtotal -= producto.precio
                        }
                    }
                    Text(
                        text = cantidad.toString(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        fontFamily = fontInria,
                        color = Color(0xFF2E7D32) // Verde oscuro
                    )
                    IconButtonGenerico(icono = Icons.Default.AddCircle, color = Color(0xFF4CAF50)) {
                        if (cantidad < producto.stock) {
                            cantidad += 1
                            subtotal += producto.precio
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val DPedido = DetallePedidoEntidad(
                        id_detalle = null,
                        id_producto = producto.id_producto.toString(),
                        nombre_producto = producto.nombre_producto,
                        cantidad = cantidad,
                        precio_unitario = producto.precio,
                        subtotal = subtotal
                    )
                    var nuevo = true
                    ListaDetallePedido.forEach { element ->
                        if (element.nombre_producto == producto.nombre_producto) {
                            element.cantidad += cantidad
                            element.subtotal += subtotal
                            nuevo = false
                        }
                    }
                    if (nuevo) {
                        ListaDetallePedido.add(DPedido)
                    }
                    listaAction()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF388E3C), // Verde para aceptar
                    contentColor = Color.White // Texto blanco
                ),
                shape = RoundedCornerShape(8.dp) // Bordes redondeados
            ) {
                Text(
                    text = "OK",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    )
}


@Composable
fun DialogoConfirm(mensaje: String, cancelaAction: () -> Unit, aceptaAction: () -> Unit) {
    AlertDialog(
        containerColor = Color(0xFFFBE9E7), // Fondo melocotón suave
        onDismissRequest = { cancelaAction() },
        title = {
            Text(
                text = "Confirmación",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFBF360C), // Rojo quemado
                fontFamily = fontFredoka,
                textAlign = TextAlign.Center
            )
        },
        text = {
            Text(
                text = mensaje,
                fontSize = 16.sp,
                color = Color(0xFF4E342E), // Marrón oscuro
                fontFamily = fontInria,
                textAlign = TextAlign.Center
            )
        },
        confirmButton = {
            Button(
                onClick = { aceptaAction() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFD84315), // Naranja quemado
                    contentColor = Color.White // Texto blanco
                ),
                shape = RoundedCornerShape(8.dp) // Bordes redondeados
            ) {
                Text(
                    text = "Aceptar",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        dismissButton = {
            TextButton(onClick = { cancelaAction() }) {
                Text(
                    text = "Cancelar",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFBF360C) // Rojo quemado
                )
            }
        }
    )
}


@Composable
fun DialogoEditar(
    producto: ProductoEntidad,
    cancelaAction: () -> Unit,
    aceptaAction: (ProductoEntidad) -> Unit
) {
    val items = listOf("Pastel", "Postre", "Pan")

    var nombre by remember { mutableStateOf(producto.nombre_producto) }
    var descripcion by remember { mutableStateOf(producto.descripcion) }
    var precio by remember { mutableStateOf(producto.precio.toString()) }
    var stock by remember { mutableStateOf(producto.stock.toString()) }
    var categoria by remember { mutableStateOf(producto.categoria) }

    val valLetra = remember { Regex("[A-Za-z\\s]*") }
    val valNum2 = remember { Regex("^\\d+(\\.\\d+)?\$") }
    val valNum = remember { Regex("^\\d+(\\.\\d+)?\$|^\\d+\\.\$") }

    AlertDialog(
        containerColor = Color(0xFFFBE9E7), // Fondo melocotón suave
        onDismissRequest = { cancelaAction() },
        title = {
            Text(
                text = "Editar Información",
                fontSize = 20.sp, // Tamaño de título
                fontWeight = FontWeight.Bold,
                color = Color(0xFFBF360C), // Rojo quemado
                fontFamily = fontFredoka, // Tipografía consistente
                textAlign = TextAlign.Center
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SpinnerMenu(
                    lista = items,
                    label = "Categoría",
                    productoIn = categoria,
                    fonsize = 16f
                ) { nuevo ->
                    categoria = nuevo
                }
                Spacer(modifier = Modifier.height(10.dp))
                CajaTextoGenericoEdit(valor = nombre, label = "Nombre", false) {
                    if (it.matches(valLetra)) {
                        nombre = it
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                CajaTextoGenericoEdit(valor = descripcion, label = "Descripción", false) {
                    if (it.matches(valLetra)) {
                        descripcion = it
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                CajaTextoGenericoEdit(valor = precio, label = "Precio", true) {
                    if (it.matches(valNum) || it.isEmpty()) {
                        precio = it
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                CajaTextoGenericoEdit(valor = stock, label = "Stock", true) {
                    if (it.matches(valNum) || it.isEmpty()) {
                        stock = it
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val elemento = ProductoEntidad(
                        producto.id_producto,
                        nombre,
                        nombre.lowercase(),
                        descripcion,
                        categoria,
                        precio.toDouble(),
                        stock.toInt()
                    )
                    aceptaAction(elemento)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFD84315), // Naranja quemado
                    contentColor = Color.White // Texto blanco
                ),
                shape = RoundedCornerShape(8.dp) // Bordes redondeados
            ) {
                Text(
                    text = "Aceptar",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        dismissButton = {
            TextButton(onClick = { cancelaAction() }) {
                Text(
                    text = "Cancelar",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFBF360C) // Rojo quemado
                )
            }
        }
    )
}


@Composable
fun DialogoDetalles(
    producto: ProductoEntidad,
    closeAction: () -> Unit
) {
    AlertDialog(
        containerColor = Color(0xFFFBE9E7), // Fondo melocotón suave
        onDismissRequest = {},
        title = {
            Text(
                text = "Información del Producto",
                fontSize = 20.sp, // Tamaño similar a los títulos de
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.Bold,
                color = Color(0xFFBF360C), // Rojo quemado
                fontFamily = fontFredoka, // Tipografía consistente
                textAlign = TextAlign.Center
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Nombre:",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color(0xFF4E342E), // Marrón oscuro
                        fontFamily = fontInria
                    )
                    Text(
                        text = producto.nombre_producto,
                        color = Color.DarkGray,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Right,
                        fontSize = 16.sp,
                        fontFamily = fontInria
                    )
                }
                Text(
                    text = "Descripción:",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color(0xFF4E342E),
                    fontFamily = fontInria,
                    modifier = Modifier.padding(vertical = 5.dp, horizontal = 5.dp)
                )
                Text(
                    text = producto.descripcion,
                    color = Color.DarkGray,
                    fontSize = 16.sp,
                    fontFamily = fontInria,
                    modifier = Modifier.padding(horizontal = 10.dp)
                )
                Row(
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Precio:",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color(0xFF4E342E),
                        fontFamily = fontInria
                    )
                    Text(
                        text = "S/${producto.precio}",
                        color = Color.DarkGray,
                        fontSize = 16.sp,
                        fontFamily = fontInria
                    )
                }
                Row(
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Stock:",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color(0xFF4E342E),
                        fontFamily = fontInria
                    )
                    Text(
                        text = "${producto.stock} uds.",
                        color = Color.DarkGray,
                        fontSize = 16.sp,
                        fontFamily = fontInria
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { closeAction() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFD84315), // Naranja quemado
                    contentColor = Color.White // Texto blanco
                ),
                shape = RoundedCornerShape(8.dp) // Bordes redondeados
            ) {
                Text(
                    text = "OK",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif
                )
            }
        }
    )
}

@Composable
fun DialogoCliente(cancelaAction: () -> Unit, aceptaAction: (ClienteEntidad) -> Unit) {
    val FRClientes = ManejadorClientes()

    var DNI by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    val valNum = remember { Regex("^\\d+(\\.\\d+)?\$") }
    val valLetra = remember { Regex("[A-Za-z\\s]*") }
    val contexto = LocalContext.current
    var clienteExistente by remember { mutableStateOf<ClienteEntidad?>(null) }

    LaunchedEffect(DNI) {
        if (DNI.isNotEmpty()) {
            // Aquí se debe obtener el cliente por DNI
            FRClientes.obtenerClientePorId(DNI).collect { cliente ->
                clienteExistente = cliente
                // Si existe un cliente, completamos los campos
                if (cliente != null) {
                    nombre = cliente.nombre
                    direccion = cliente.direccion
                    telefono = cliente.telefono
                }
            }
        }
    }

    val isFormValid = nombre.isNotEmpty() && DNI.isNotEmpty() && DNI.length in 8..11

    AlertDialog(
        containerColor = Color(0xFFFBE9E7),
        title = {
            Text(
                text = "Información del Cliente",
                fontSize = 20.sp, // Tamaño similar a los títulos de
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.Bold,
                color = Color(0xFFBF360C), // Rojo quemado
                fontFamily = fontFredoka, // Tipografía consistente
                textAlign = TextAlign.Center
            )
        },
        dismissButton = {
            TextButton(onClick = { cancelaAction() }) {
                Text(
                    text = "Cancelar",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFBF360C) // Rojo quemado
                )
            }
        },
        onDismissRequest = { cancelaAction() },
        confirmButton = {
            Button(
                onClick = {
                    if (isFormValid) {
                        val cliente = ClienteEntidad(
                            DNI,
                            nombre,
                            nombre.lowercase(),
                            direccion,
                            telefono
                        )
                        aceptaAction(cliente)
                    } else {
                        // Puedes mostrar un mensaje de error
                        MensajeToast(contexto, "Complete los campos Obligatorios")
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFD84315), // Naranja quemado
                    contentColor = Color.White // Texto blanco
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = "Aceptar")
            }
        },
        text = {
            Column {
                CajaTextoGenerico(
                    valor = DNI,
                    label = "DNI O RUC (Obligatorio)",
                    isNum = true,
                    size = 16f
                ) {
                    if (it.matches(valNum) || it.isEmpty()) {
                        DNI = it
                    }

                }
                Spacer(modifier = Modifier.height(10.dp))
                CajaTextoGenerico(
                    valor = nombre,
                    label = "Nombre o Razon Social (Obligatorio)",
                    isNum = false,
                    size = 16f
                ) {
                    if (it.matches(valLetra)) {
                        nombre = it
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                CajaTextoGenerico(
                    valor = direccion,
                    label = "Direccion (Opcional)",
                    isNum = false,
                    size = 16f
                ) {
                    direccion = it
                }
                Spacer(modifier = Modifier.height(10.dp))
                CajaTextoGenerico(
                    valor = telefono,
                    label = "Telefono (Opcional)",
                    isNum = true,
                    size = 16f
                ) {
                    if (it.matches(valNum) || it.isEmpty()) {
                        telefono = it
                    }
                }
            }
        },
    )
}

