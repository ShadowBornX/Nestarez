package com.example.nestarez.navegacion.Pedidos

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.nestarez.navegacion.ElementoNav
import com.example.nestarez.views.Pedido.PedidoInicio
import com.example.nestarez.views.Pedido.PedidoLista
import com.example.nestarez.views.PedidoNew.InicioPedidoNew
import com.example.nestarez.views.PedidoNew.ListaPedidoNew

@Composable
fun ANIPedido(navControlador: NavHostController){
    NavHost(navController = navControlador, startDestination = ElementoNav.PedidoPrincipal.ruta){
        composable(route = ElementoNav.PedidoPrincipal.ruta){
            // Invocar a la UI Destino
            PedidoInicio()
        }
        composable(route = ElementoNav.PedidoLista.ruta){
            // Invocar a la UI Destino
            PedidoLista()
        }
    }
}