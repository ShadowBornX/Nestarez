package com.example.nestarez.componentesUI

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.nestarez.navegacion.PedidoNew.IBIPedidoNew
import com.example.nestarez.navegacion.PedidoNew.RAPedidoNew
import com.example.nestarez.navegacion.Pedidos.IBIPedido
import com.example.nestarez.navegacion.Pedidos.RAPedido
import com.example.nestarez.navegacion.Productos.IBIProductos
import com.example.nestarez.navegacion.Productos.RAProductos


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
                                modifier = Modifier.size(35.dp)
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
                                modifier = Modifier.size(35.dp)
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

val PItems = listOf(
    IBIPedido.ItemInicio,
    IBIPedido.ItemLista,
)

@Composable
fun NIPedido(navControlador: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
    ) {
        BottomAppBar(containerColor = Color(0xFFFE6565)) {
            NavigationBar(containerColor = Color(0xFFFE6565)) {
                PItems.forEach { elemento ->
                    //Obtener ruta actual
                    val rutaActual = RAPedido(navControlador = navControlador) == elemento.ruta
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
                                modifier = Modifier.size(35.dp)
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