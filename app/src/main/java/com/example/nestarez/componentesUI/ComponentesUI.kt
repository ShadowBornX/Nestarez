package com.example.nestarez.componentesUI

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.widget.DatePicker
import android.widget.Space
import android.widget.Toast
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.BakeryDining
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Icecream
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.RemoveCircle
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
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.nestarez.LogicaNegocio.Entidades.DetallePedidoEntidad
import com.example.nestarez.LogicaNegocio.Entidades.ProductoEntidad
import com.example.nestarez.R
import com.example.nestarez.fonts.fontInria
import com.example.nestarez.navegacion.PedidoNew.IBIPedidoNew
import com.example.nestarez.navegacion.PedidoNew.RAPedidoNew
import com.example.nestarez.navegacion.Productos.IBIProductos
import com.example.nestarez.navegacion.Productos.RAProductos
import com.google.firebase.Timestamp

import kotlinx.coroutines.channels.ticker
import org.w3c.dom.Text
import java.util.Calendar

fun MensajeToast(context: Context, mensaje: String) {
    Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show()
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
    isNum: Boolean,
    size: Float,
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
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = size.sp)
                )
            }
        },
        textStyle = TextStyle(
            color = Color.Black,
            fontSize = size.sp,
            fontWeight = FontWeight.Bold
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = if (isNum) KeyboardType.Number else KeyboardType.Text
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
fun CajaTextoGenericoEdit(
    valor: String,
    label: String,
    isNum: Boolean,
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
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        fontFamily = fontInria
                    )
                )
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = if (isNum) KeyboardType.Number else KeyboardType.Text),
        textStyle = TextStyle(
            color = Color.Black,
            fontSize = 16.sp,
            fontFamily = fontInria,
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
    with: Float,
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
            .fillMaxWidth(with)
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

@Composable
fun BotonGenerico(texto: String, icono: ImageVector, onclick: () -> Unit) {
    Button(onClick = onclick, colors = ButtonDefaults.buttonColors(Color(0xFFEF6C6C))) {
        Icon(
            imageVector = icono,
            contentDescription = null,
            modifier = Modifier.size(35.dp),
            tint = Color.White
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = texto, fontSize = 25.sp, color = Color.White)
    }
}

@Composable
fun BotonGenericoLista(texto: String, icono: ImageVector, onclick: () -> Unit) {
    Button(onClick = onclick, colors = ButtonDefaults.buttonColors(Color(0xFFD84315))) {
        Icon(
            imageVector = icono,
            contentDescription = null,
            modifier = Modifier.size(30.dp),
            tint = Color.White
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = texto, fontSize = 20.sp, color = Color.White)
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
fun IconButtonGenerico(icono: ImageVector, color: Color, onclick: () -> Unit) {
    IconButton(onClick = { onclick() }) {
        Icon(
            imageVector = icono,
            contentDescription = icono.name,
            tint = color,
            modifier = Modifier.size(40.dp)
        )
    }
}

@Composable
fun IconButtonGenerico(icono: ImageVector, onclick: () -> Unit) {
    IconButton(onClick = { onclick() }) {
        Icon(
            imageVector = icono,
            contentDescription = icono.name,
            tint = Color.Black,
            modifier = Modifier.size(25.dp)
        )
    }
}


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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpinnerMenu2(
    lista: List<String>,
    label: String,
    productoIn: String,
    onvalue: (String) -> Unit
) {
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
fun SpinnerMenu(
    lista: List<String>,
    label: String,
    productoIn: String,
    fonsize: Float,
    onvalue: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

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
            value = productoIn.ifEmpty { label },
            onValueChange = {},
            readOnly = true,
            placeholder = {
                if (productoIn.isEmpty()) {
                    Text(
                        text = label,
                        color = Color(0xff7B7B7B),
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = fonsize.sp,
                            fontFamily = fontInria
                        )
                    )
                }
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            textStyle = TextStyle(
                color = if (productoIn.isEmpty()) Color(0xff7B7B7B) else Color.Black,
                fontSize = fonsize.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = fontInria
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
                            color = if (option == productoIn) Color.White else Color(0xff7B7B7B),
                            fontFamily = fontInria,
                            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = fonsize.sp)
                        )
                    },
                    onClick = {
                        expanded = false
                        onvalue(option)
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    modifier = Modifier.background(
                        if (option == productoIn) Color(0xFFEF6C6C) else Color.Transparent
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




@Composable
fun DatePickerIcon(
    onDateSelected: (Timestamp) -> Unit // Callback para devolver la fecha como Timestamp
) {
    val context = LocalContext.current

    // Obtener la fecha actual
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    // Crear DatePickerDialog
    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            // Formatear la fecha seleccionada como Timestamp
            //selectedDate = "$dayOfMonth/${month + 1}/$year" // Mostrar la fecha como texto
            calendar.set(year, month, dayOfMonth)
            val selectedTimestamp = Timestamp(calendar.time) // Convertir a Timestamp
            Log.d("DatePicker", "Fecha seleccionada: $selectedTimestamp")
            onDateSelected(selectedTimestamp)
        }, year, month, day
    )

    datePickerDialog.datePicker.maxDate = calendar.timeInMillis

    IconButton(onClick = { datePickerDialog.show() }) {
        Icon(
            imageVector = Icons.Default.CalendarMonth,
            contentDescription = "Selecciona una fecha",
            modifier = Modifier.size(40.dp),
            tint = Color(0xFF7B7B7B)
        )
    }

}

