package com.example.nestarez.componentesUI

import android.graphics.Typeface
import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BakeryDining
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Icecream
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Details
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.nestarez.LogicaNegocio.Entidades.DetallePedidoEntidad
import com.example.nestarez.LogicaNegocio.Entidades.ProductoEntidad
import com.example.nestarez.R
import com.example.nestarez.navegacion.PedidoNew.IBIPedidoNew
import com.example.nestarez.navegacion.PedidoNew.RAPedidoNew
import com.example.nestarez.navegacion.Productos.IBIProductos
import com.example.nestarez.navegacion.Productos.RAProductos
import com.example.nestarez.views.PedidoNew.ListaDetallePedido
import kotlinx.coroutines.channels.ticker
import org.w3c.dom.Text

@Preview
@Composable
fun PreviewSquareButton() {

}


@Composable
fun CajaTextoGenericoi(valor: String, label: String, onvalue: (String) -> Unit) {
    OutlinedTextField(
        value = valor,
        label = { Text(text = label) },
        onValueChange = onvalue,
        //modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun CajaTextoGenerico(
    valor: String,
    label: String,
    onvalue: (String) -> Unit
) {
    TextField(
        value = valor,
        onValueChange = onvalue,
        placeholder = {
            if (valor.isEmpty()) {
                Text(
                    text = label,
                    color = Color(0xff7B7B7B),
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)
                )
            }
        },
        textStyle = TextStyle(
            color = Color.Black,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        ),
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color.White,
                shape = RoundedCornerShape(15.dp)
            )
            .padding(vertical = 5.dp, horizontal = 15.dp),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.Transparent, // Fondo interno transparente
            focusedContainerColor = Color.Transparent,  // Fondo interno transparente
            cursorColor = Color(0xff7B7B7B),                  // Color del cursor
            focusedIndicatorColor = Color.Transparent,  // Sin línea inferior al enfocarse
            unfocusedIndicatorColor = Color.Transparent // Sin línea inferior al desenfocarse
        ),
        shape = RoundedCornerShape(15.dp) // Mantiene esquinas redondeadas
    )
}

@Composable
fun CajaTextoGenericoIcon(
    valor: String,
    icon: ImageVector,
    label: String,
    onValueChange: (String) -> Unit
) {
    TextField(
        value = valor,
        onValueChange = onValueChange,
        placeholder = {
            if (valor.isEmpty()) {
                Text(
                    text = label,
                    color = Color(0xff7B7B7B),
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)
                )
            }
        },
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(30.dp),
                tint = Color(0xff7B7B7B)
            )
        },
        textStyle = TextStyle(
            color = Color.Black,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        ),
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color(0xFFD9D9D9),
                shape = RoundedCornerShape(25.dp)
            )
            .padding(vertical = 5.dp, horizontal = 15.dp),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.Transparent, // Fondo interno transparente
            focusedContainerColor = Color.Transparent,  // Fondo interno transparente
            cursorColor = Color(0xff7B7B7B),                  // Color del cursor
            focusedIndicatorColor = Color.Transparent,  // Sin línea inferior al enfocarse
            unfocusedIndicatorColor = Color.Transparent // Sin línea inferior al desenfocarse
        ),
        shape = RoundedCornerShape(25.dp) // Mantiene esquinas redondeadas
    )
}


@Composable
fun CajaTextoGenericoDes(valor: String, label: String, onvalue: (String) -> Unit) {
    TextField(
        value = valor,
        onValueChange = onvalue,
        placeholder = {
            if (valor.isEmpty()) {
                Text(
                    text = label,
                    color = Color(0xff7B7B7B),
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)
                )
            }
        },
        textStyle = TextStyle(
            color = Color.Black,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        ),
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color.White,
                shape = RoundedCornerShape(15.dp)
            )
            .padding(vertical = 5.dp, horizontal = 15.dp),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.Transparent, // Fondo interno transparente
            focusedContainerColor = Color.Transparent,  // Fondo interno transparente
            cursorColor = Color(0xff7B7B7B),                  // Color del cursor
            focusedIndicatorColor = Color.Transparent,  // Sin línea inferior al enfocarse
            unfocusedIndicatorColor = Color.Transparent // Sin línea inferior al desenfocarse
        ),
        shape = RoundedCornerShape(15.dp), // Mantiene esquinas redondeadas
        maxLines = 3
    )
}

@Composable
fun BotonNav(
    icono: ImageVector,
    texto: String,
    fraccion: Float,
    color: Color,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth(fraccion)
            .height(150.dp)
            .padding(5.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = color)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = texto,
                style = TextStyle(
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                ),
                color = Color.White,
            )
            // Icono en el centro
            Image(
                imageVector = icono,
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color.White),
                modifier = Modifier.size(80.dp) // Tamaño del icono
            )

        }
    }
}


val PNItems = listOf(
    IBIPedidoNew.ItemInicio,
    IBIPedidoNew.ItemLista,
)

@Composable
fun NIPedidoNew(navControlador: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
    ) {
        BottomAppBar(containerColor = Color(0xFFFE6565)) {
            NavigationBar(containerColor = Color(0xFFFE6565)) {
                PNItems.forEach { elemento ->
                    //Obtener ruta actual
                    val rutaActual = RAPedidoNew(navControlador = navControlador) == elemento.ruta
                    NavigationBarItem(
                        selected = rutaActual,
                        onClick = {
                            navControlador.navigate(elemento.ruta) {
                                popUpTo(navControlador.graph.startDestinationId) {
                                    inclusive = false
                                }
                                popUpTo(navControlador.graph.id) {
                                    inclusive = true
                                }
                                launchSingleTop = true

                            }
                        },
                        colors = NavigationBarItemDefaults.colors(

                            selectedIconColor = Color.White, // Color del ícono seleccionado
                            unselectedIconColor = Color.White, // Color del ícono no seleccionado
                            indicatorColor = Color.Transparent
                        ),
                        icon = {
                            Icon(
                                imageVector = elemento.icono,
                                contentDescription = null,
                                //tint = Color.White,
                                modifier = Modifier.size(50.dp)
                            )
                        },
                        label = {
                            Text(
                                text = elemento.titulo,
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        })
                }
            }
        }
    }
}

val ProdItems = listOf(
    IBIProductos.ItemInicio,
    IBIProductos.ItemNew,
)

@Composable
fun NIProductos(navControlador: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
    ) {
        BottomAppBar(containerColor = Color(0xFFFE6565)) {
            NavigationBar(containerColor = Color(0xFFFE6565)) {
                ProdItems.forEach { elemento ->
                    //Obtener ruta actual
                    val rutaActual = RAProductos(navControlador = navControlador) == elemento.ruta
                    NavigationBarItem(
                        selected = rutaActual,
                        onClick = { navControlador.navigate(elemento.ruta) },
                        colors = NavigationBarItemDefaults.colors(

                            selectedIconColor = Color.White, // Color del ícono seleccionado
                            unselectedIconColor = Color.White, // Color del ícono no seleccionado
                            indicatorColor = Color.Transparent
                        ),
                        icon = {
                            Icon(
                                imageVector = elemento.icono,
                                contentDescription = null,
                                modifier = Modifier.size(50.dp)
                            )
                        },
                        label = {
                            Text(
                                text = elemento.titulo,
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        })
                }
            }
        }
    }
}

@Composable
fun BotonGenerico(texto: String, icono: ImageVector, onclick: () -> Unit) {
    Button(onClick = onclick, colors = ButtonDefaults.buttonColors(Color(0xFFEF6C6C)) ) {
        Icon(imageVector = icono, contentDescription = null, modifier = Modifier.size(35.dp), tint = Color.White)
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = texto, fontSize = 25.sp, color = Color.White)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarra(titulo: String, colorBarra: Color) {
    TopAppBar(
        title = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 10.dp)
            ) {
                Text(
                    text = titulo,
                    style = TextStyle(fontSize = 30.sp, fontWeight = FontWeight.Bold),
                    modifier = Modifier.align(Alignment.BottomStart), // Alinear en el centro
                    textAlign = TextAlign.Left
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = colorBarra)
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarraRetorno(titulo: String, colorBarra: Color, onclick: () -> Unit) {
    TopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { onclick() }) {
                    Icon(
                        imageVector = Icons.Default.ChevronLeft,
                        contentDescription = Icons.Default.ChevronLeft.name,
                        tint = Color.White,
                        modifier = Modifier.size(75.dp)
                    )
                }
                Text(
                    text = titulo,
                    style = TextStyle(
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = colorBarra)
    )
}

@Composable
fun BotonFlotante(onclick: () -> Unit) {
    FloatingActionButton(onClick = onclick) {
        Icon(imageVector = Icons.Default.Add, contentDescription = null)
    }
}

@Composable
fun IconButtonGenerico(icono: ImageVector, onclick: () -> Unit) {
    IconButton(onClick = { onclick() }) {
        Icon(
            imageVector = icono,
            contentDescription = icono.name,
            tint = Color.Black,
            modifier = Modifier.size(20.dp)
        )
    }
}

/*@Composable
fun ElementosListaAlumnos(alumno: Alumnos, indice: Int) {

    //variable de estado
    var verDialogo by remember { mutableStateOf(false)}

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(2.5f)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(text = "Nombres: ${alumno.nombre}")
                    Text(text = "Apellidos:${alumno.apellidos}")
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(text = "DNI: ${alumno.DNI}")
                    Text(text = "Curso:${alumno.curso}")
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f), horizontalArrangement = Arrangement.End
            ) {
                BotonGenerico(texto = "", icono = Icons.Default.Delete) {
                    //ELIMINAR
                    verDialogo = true
                }
            }
        }
    }
    HorizontalDivider(modifier = Modifier.padding(2.dp))
    //invocar a la funcion del dialogo
    DialogoPersonalisado(visible = verDialogo, cancelaAction = { verDialogo = false}) {
        //Acepta el usuario
        //Remover el alumno de la lista
        listaAlumnos.removeAt(indice)
        //Ocultar el dialogo
        verDialogo = false
    }
}*/

@Composable
fun DialogoPersonalisado(visible: Boolean, cancelaAction: () -> Unit, aceptaAction: () -> Unit) {
    if (visible) {
        AlertDialog(
            title = { Text(text = "Confirmacion") },
            text = { Text(text = "¿Desea Eliminar?") },
            onDismissRequest = { /*TODO*/ },
            confirmButton = {
                TextButton(onClick = { aceptaAction() }) {
                    Text(text = "Aceptar")
                }
            },
            dismissButton = {
                TextButton(onClick = { cancelaAction() }) {
                    Text(text = "Cancelar", color = Color.Red)
                }
            })
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
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "S/ ${producto.precio}", color = Color(0xFFA44949),
                        fontFamily = FontFamily(typeface = Typeface.MONOSPACE)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    IconButtonGenerico(icono = ImageVector.vectorResource(id = R.drawable.info)) { onDetail() }
                    IconButtonGenerico(icono = ImageVector.vectorResource(id = R.drawable.edit)) { onEdit() }
                    IconButtonGenerico(icono = ImageVector.vectorResource(id = R.drawable.delete)) { onDelete() }
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
fun obtenerIconoPorCategoria(categoria: String): ImageVector {
    return when (categoria.lowercase()) {
        "pastel" -> ImageVector.vectorResource(id = R.drawable.cake)
        "pan" -> ImageVector.vectorResource(id = R.drawable.pan)
        "postre" -> ImageVector.vectorResource(id = R.drawable.icecream)
        else -> Icons.Filled.Help // Icono por defecto para categorías no reconocidas
    }
}

@Composable
fun DialogoEliminar(cancelaAction: () -> Unit, aceptaAction: () -> Unit) {

    AlertDialog(
        title = { Text(text = "Confirmacion") },
        text = { Text(text = "¿Desea Eliminar?") },
        onDismissRequest = { cancelaAction() },
        confirmButton = {
            TextButton(onClick = { aceptaAction() }) {
                Text(text = "Aceptar")
            }
        },
        dismissButton = {
            TextButton(onClick = { cancelaAction() }) {
                Text(text = "Cancelar", color = Color.Red)
            }
        })
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

    val valNum = remember { Regex("^\\d+(\\.\\d+)?\$") }

    AlertDialog(
        title = { Text(text = "Editar Informacion") },
        text = {
            Column(
                modifier = Modifier
                    .padding()
                    .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SpinnerMenu(lista = items, label = "Categoria", productoIn = categoria) { nuevo ->
                    categoria = nuevo
                }
                CajaTextoGenerico(valor = nombre, label = "Nombre") {
                    if (it.matches(valLetra)) {
                        nombre = it
                    }
                }
                CajaTextoGenerico(valor = descripcion, label = "Descripcion") {
                    if (it.matches(valLetra)) {
                        descripcion = it
                    }
                }
                CajaTextoGenerico(valor = precio, label = "Precio") {
                    if (it.matches(valNum)) {
                        precio = it
                    }
                }
                CajaTextoGenerico(valor = stock, label = "Stock") {
                    if (it.matches(valNum)) {
                        stock = it
                    }
                }
            }
        },
        onDismissRequest = { cancelaAction() },
        confirmButton = {
            TextButton(onClick = {
                val elemento =
                    ProductoEntidad(
                        producto.id_producto,
                        nombre,
                        descripcion,
                        categoria,
                        precio.toDouble(),
                        stock.toInt()
                    )
                aceptaAction(elemento)
            }) {
                Text(text = "Aceptar")
            }
        },
        dismissButton = {
            TextButton(onClick = { cancelaAction() }) {
                Text(text = "Cancelar", color = Color.Red)
            }
        })
}

@Composable
fun DialogoDetalles(
    producto: ProductoEntidad,
    closeAction: () -> Unit
) {

    AlertDialog(
        onDismissRequest = {},
        title = { Text(text = "Informacion") },
        text = {
            Column(
                modifier = Modifier
                    .padding()
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Nombre: ", fontWeight = FontWeight.Bold)
                    Text(text = producto.nombre_producto)
                }
                Text(
                    text = "Descripcion: ",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth()
                )
                Text(
                    text = producto.descripcion, modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth()
                )
                Row(
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Precio: ", fontWeight = FontWeight.Bold)
                    Text(text = "S/${producto.precio}")
                }
                Row(
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Stock: ", fontWeight = FontWeight.Bold)
                    Text(text = "${producto.stock} uds.")
                }
            }
        },
        confirmButton = {
            TextButton(onClick = { closeAction() }) {
                Text(text = "OK")
            }
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpinnerMenu2(lista: List<String>, label: String, productoIn: String, onvalue: (String) -> Unit) {
    // Lista de elementos para el dropdown

    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(productoIn) }

    // Caja de menú desplegable
    ExposedDropdownMenuBox(
        modifier = Modifier.fillMaxWidth(),
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }) {
        // Campo de texto que muestra la opción seleccionada
        OutlinedTextField(
            readOnly = true,
            value = selectedOption,
            onValueChange = {},
            label = { Text(label) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF004481),
                focusedLabelColor = Color(0xFF004481),
                unfocusedBorderColor = Color.Gray,
            )
        )

        // Menú desplegable
        DropdownMenu(
            modifier = Modifier.exposedDropdownSize(),
            expanded = expanded,
            onDismissRequest = { expanded = false }  // Cambiado a false para cerrar el menú
        ) {
            lista.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Text(
                            option,
                            color = if (option == selectedOption) Color.White else Color.Unspecified
                        )
                    },
                    onClick = {
                        selectedOption = option  // Actualiza la opción seleccionada
                        expanded = false  // Cierra el menú
                        onvalue(option)
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    modifier = Modifier.background(if (option == selectedOption) Color(0xFF004481) else Color.Transparent)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpinnerMenu(lista: List<String>, label: String, productoIn: String, onvalue: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(productoIn) }

    // Caja de menú desplegable
    ExposedDropdownMenuBox(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(15.dp))
            .padding(vertical = 5.dp, horizontal = 15.dp),
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        // Campo de texto que muestra la opción seleccionada
        TextField(
            value = selectedOption.ifEmpty { label },
            onValueChange = {},
            readOnly = true,
            placeholder = {
                if (selectedOption.isEmpty()) {
                    Text(
                        text = label,
                        color = Color(0xff7B7B7B),
                        style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    )
                }
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            textStyle = TextStyle(
                color = if (selectedOption.isEmpty()) Color(0xff7B7B7B) else Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                cursorColor = Color(0xff7B7B7B),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(15.dp) // Mantiene esquinas redondeadas
        )

        // Menú desplegable
        DropdownMenu(
            modifier = Modifier
                .background(Color.White)
                .exposedDropdownSize(),
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            lista.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = option,
                            color = if (option == selectedOption) Color.White else Color(0xff7B7B7B),
                            style = TextStyle(fontWeight = FontWeight.Bold)
                        )
                    },
                    onClick = {
                        selectedOption = option
                        expanded = false
                        onvalue(option)
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    modifier = Modifier.background(
                        if (option == selectedOption) Color(0xFFEF6C6C) else Color.Transparent
                    )
                )
            }
        }
    }
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
                .padding(5.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
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
                Text(text = "S/${producto.precio}",color = Color.Black)
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

@Composable
fun ElementosDetallePedido(
    detallePedidoEntidad: DetallePedidoEntidad,
    onDelete: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(5.dp)
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
                .background(Color.DarkGray),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .weight(0.15f)
                    .padding(end = 1.dp)
                    .fillMaxHeight()
                    .background(Color.White), contentAlignment = Alignment.Center
            ) {
                Text(
                    text = detallePedidoEntidad.cantidad.toString(),
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace,
                    fontSize = 20.sp,
                    color = Color.Black
                )
            }
            Box(
                modifier = Modifier
                    .weight(0.45f)
                    .padding(start = 1.dp, end = 1.dp)
                    .fillMaxHeight()
                    .background(Color.White), contentAlignment = Alignment.Center
            ) {
                Text(
                    text = detallePedidoEntidad.nombre_producto,
                    modifier = Modifier.padding(5.dp),
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.Black
                )
            }
            Box(
                modifier = Modifier
                    .weight(0.25f)
                    .padding(start = 1.dp, end = 1.dp)
                    .fillMaxHeight()
                    .background(Color.White), contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "S/. ${detallePedidoEntidad.subtotal}",
                    color = Color.Black,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold
                )
            }
            Box(
                modifier = Modifier
                    .weight(0.15f)
                    .padding(start = 1.dp)
                    .fillMaxHeight()
                    .background(Color.White), contentAlignment = Alignment.Center
            ) {
                IconButton(onClick = { onDelete() }) {
                    Icon(imageVector = Icons.Default.Remove, contentDescription = "Eliminar")
                }
            }
            //Box(modifier = Modifier.fillMaxHeight().padding(5.dp).weight(0.05f).width(2.dp).background(
            //    Color.Black).padding(5.dp))

        }
    }
    /*Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.1f)
                    .fillMaxHeight(1f)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Text(text = detallePedidoEntidad.cantidad.toString())
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .background(Color.Blue)
            ) {
                Text(text = detallePedidoEntidad.nombre_producto)
            }
            Card(modifier = Modifier.fillMaxWidth(0.5f)) {
                Text(text = "S/. ${detallePedidoEntidad.subtotal}")
            }
            /*Column(
                modifier = Modifier.fillMaxWidth(0.75f),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = detallePedidoEntidad.nombre_producto,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Text(text = "S/${detallePedidoEntidad.subtotal}")
            }*/
            IconButtonGenerico(icono = Icons.Default.Remove) { onDelete() }
        }
    }*/
    HorizontalDivider(modifier = Modifier.padding(2.dp))
    //invocar a la funcion del dialogo

}

@Composable
fun DialogoAgregar(
    producto: ProductoEntidad,
    listaAction: () -> Unit
) {

    var cantidad by remember { mutableStateOf(1) }
    var subtotal by remember { mutableStateOf(producto.precio) }


    AlertDialog(
        onDismissRequest = {},
        dismissButton = {
            TextButton(onClick = { listaAction() }) {
                Text(text = "Cancelar")
            }
        },
        title = { Text(text = "Agregar") },
        text = {
            Column(
                modifier = Modifier
                    .padding()
                    .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = producto.nombre_producto,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Row(
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "SubTotal", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    Text(text = "S/${subtotal}", fontSize = 15.sp)
                }
                Row(
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    IconButtonGenerico(icono = Icons.Default.Remove) {
                        cantidad -= 1
                        subtotal -= producto.precio
                    }
                    Text(text = cantidad.toString(), fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    IconButtonGenerico(icono = Icons.Default.Add) {
                        cantidad += 1
                        subtotal += producto.precio
                    }
                }
            }
        },
        confirmButton = {

            TextButton(onClick = {
                var DPedido = DetallePedidoEntidad(
                    null,
                    "",
                    producto.id_producto.toString(),
                    producto.nombre_producto,
                    cantidad,
                    producto.precio,
                    subtotal
                )
                var nuevo = true
                ListaDetallePedido.forEach() { element ->
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
            }) {
                Text(text = "OK")
            }
        })
}
