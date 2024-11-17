package com.example.nestarez.navegacion.PedidoNew

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.nestarez.navegacion.ElementoNav
import com.example.nestarez.views.PedidoNew.InicioPedidoNew
import com.example.nestarez.views.PedidoNew.ListaPedidoNew

@Composable
fun ANIPedidoNew(navControlador: NavHostController){
    NavHost(navController = navControlador, startDestination = ElementoNav.PedidosNewPrincipal.ruta){
        composable(route = ElementoNav.PedidosNewPrincipal.ruta){
            // Invocar a la UI Destino
            InicioPedidoNew()
        }
        composable(route = ElementoNav.PedidosNewLista.ruta){
            // Invocar a la UI Destino
            ListaPedidoNew()
        }
    }
}