package com.example.nestarez.views.Productos

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nestarez.LogicaNegocio.Entidades.ProductoEntidad
import com.example.nestarez.LogicaNegocio.ManejadorF.ManejadorProductos
import com.example.nestarez.R
import com.example.nestarez.componentesUI.BotonGenerico
import com.example.nestarez.componentesUI.CajaTextoGenerico
import com.example.nestarez.componentesUI.CajaTextoGenericoDes
import com.example.nestarez.componentesUI.MensajeToast
import com.example.nestarez.componentesUI.SpinnerMenu
import com.example.nestarez.fonts.fontFredoka


@Composable
fun ProductosNew() {
    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var stock by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }

    val contexto = LocalContext.current

    val FRproducto = ManejadorProductos()

    val valLetra = remember { Regex("[A-Za-z\\s]*") }

    val valNum = remember { Regex("^\\d+(\\.\\d+)?\$|^\\d+\\.\$") }
    val valNumInt = remember { Regex("^\\d+$") }
    val items = listOf("Pastel", "Postre", "Pan")

    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp, start = 20.dp, end = 20.dp)
            .shadow(50.dp, shape = RoundedCornerShape(16.dp))
            .background(
                Color(0xFFFFC6B7),
                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
            )
    ) {
        Column(
            modifier = Modifier
                .padding(15.dp)
                .fillMaxWidth()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Agregue un nuevo producto",
                modifier = Modifier.fillMaxWidth(),
                style = TextStyle(
                    textAlign = TextAlign.Center,
                    fontSize = 35.sp,
                    color = Color(0xFF842C00),
                    fontFamily = fontFredoka
                )
            )
            Spacer(modifier = Modifier.height(20.dp))
            SpinnerMenu(
                lista = items,
                label = "Categoria",
                productoIn = categoria,
                fonsize = 20f
            ) { nuevo ->
                categoria = nuevo
            }
            Spacer(modifier = Modifier.height(15.dp))
            CajaTextoGenerico(valor = nombre, label = "Nombre", isNum = false,
                size = 20f) {
                if (it.matches(valLetra)) {
                    nombre = it
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            CajaTextoGenericoDes(valor = descripcion, label = "Descripcion") {
                descripcion = it
            }
            Spacer(modifier = Modifier.height(10.dp))
            CajaTextoGenerico(valor = precio, label = "Precio", isNum = true,
                size = 20f) {
                if (it.matches(valNum)||it.isEmpty()) {
                    precio = it
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            CajaTextoGenerico(valor = stock, label = "Stock", isNum = true,
                size = 20f) {
                if (it.matches(valNumInt)||it.isEmpty()) {
                    stock = it
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            BotonGenerico(texto = "Guardar", icono = Icons.Default.Save) {
                if (nombre.isNotBlank() || descripcion.isNotBlank() || precio.isNotBlank() || stock.isNotBlank() || categoria.isNotBlank()) {

                    FRproducto.agregarProducto(
                        ProductoEntidad(
                            nombre_producto = nombre.toUpperCase(),
                            descripcion = descripcion,
                            categoria = categoria,
                            precio = precio.toDouble(),
                            stock = stock.toInt()
                        )
                    )
                    nombre = "";descripcion = "";precio = "";stock = "";categoria = ""
                } else {
                    MensajeToast(contexto, "Complete todos los campos")
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Image(
                painter = painterResource(id = R.drawable.logo_nestarez),
                contentDescription = null
            )
        }
    }

}