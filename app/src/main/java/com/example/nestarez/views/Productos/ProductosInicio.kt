package com.example.nestarez.views.Productos

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.nestarez.LogicaNegocio.Entidades.ProductoEntidad
import com.example.nestarez.LogicaNegocio.ManejadorF.ManejadorProductos
import com.example.nestarez.componentesUI.CajaTextoGenericoIcon
import com.example.nestarez.componentesUI.DialogoConfirm
import com.example.nestarez.componentesUI.DialogoDetalles
import com.example.nestarez.componentesUI.DialogoEditar
import com.example.nestarez.componentesUI.ElementosProducto

val ListaProductos = mutableStateListOf<ProductoEntidad>()


@Composable
fun InicioProductos() {

    var productoB by remember { mutableStateOf("") }

    val FRproducto = ManejadorProductos()
    val listaproductos by FRproducto.buscarProductosPorNombreI(productoB.toUpperCase())
        .collectAsState(initial = emptyList())

    val contexto = LocalContext.current

    val valLetra = remember { Regex("[A-Za-z\\s]*") }

    var abrirDialogoEliminar by remember { mutableStateOf(false) }
    var abrirDialogoEditar by remember { mutableStateOf(false) }
    var abrirDialogoDetalles by remember { mutableStateOf(false) }
    var seleccionProducto by remember { mutableStateOf<ProductoEntidad?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp, start = 20.dp, end = 20.dp)
            .shadow(50.dp, shape = RoundedCornerShape(16.dp))
            .background(
                Color(0xFFFFEBEB),
                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(25.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {

            CajaTextoGenericoIcon(
                icon = Icons.Default.Search,
                valor = productoB,
                label = "Buscar Producto",
                with = 1f
            ) {
                if (it.matches(valLetra)) {
                    productoB = it
                }
            }
            Spacer(modifier = Modifier.height(20.dp))

            //Habilitar los dialogos
            if (abrirDialogoEditar && seleccionProducto != null) {
                DialogoEditar(
                    producto = seleccionProducto!!,
                    cancelaAction = { abrirDialogoEditar = false }) {
                    seleccionProducto = seleccionProducto?.copy(
                        nombre_producto = it.nombre_producto,
                        descripcion = it.descripcion,
                        categoria = it.categoria,
                        precio = it.precio,
                        stock = it.stock
                    )
                    seleccionProducto?.let { it1 -> FRproducto.actualizarProducto(it1) }
                    abrirDialogoEditar = false

                }
            }

            if (abrirDialogoEliminar && seleccionProducto != null) {
                DialogoConfirm(
                    "¿Desea eliminar este elemento?",
                    cancelaAction = { abrirDialogoEliminar = false }) {
                    seleccionProducto!!.id_producto?.let { FRproducto.eliminarProducto(it) }
                    abrirDialogoEliminar = false
                }
            }

            if (abrirDialogoDetalles && seleccionProducto != null) {
                DialogoDetalles(producto = seleccionProducto!!) {
                    abrirDialogoDetalles = false
                }
            }

            LazyColumn {
                items(listaproductos) { element ->
                    ElementosProducto(producto = element, onDetail = {
                        seleccionProducto = element
                        abrirDialogoDetalles = true
                    }, onEdit = {
                        seleccionProducto = element
                        abrirDialogoEditar = true
                    }) {
                        seleccionProducto = element
                        abrirDialogoEliminar = true
                    }

                }
            }
        }
    }

}