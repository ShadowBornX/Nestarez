package com.example.nestarez.navegacion

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nestarez.views.Pedido.PrincipalPedidos
import com.example.nestarez.views.Productos.PrincipalProductos
import com.example.nestarez.views.PedidoNew.PrincipalPedidoNew
import com.example.nestarez.views.PrincipalView


@Composable
fun ManejadorNavegacionExt(){
    //Definir un navController
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ElementoNav.Principal.ruta){
        //Definir rutas de navegacion en la app
        composable(route = ElementoNav.Principal.ruta){
            // Invocar a la UI Destino
            PrincipalView(navController = navController)
        }
        composable(route = ElementoNav.PedidosNewPrincipal.ruta){
            // Invocar a la UI Destino
            PrincipalPedidoNew(navController = navController)
        }
        composable(route = ElementoNav.ProductosPrincipal.ruta){
            // Invocar a la UI Destino
            PrincipalProductos(navController = navController)
        }
        composable(route = ElementoNav.PedidoPrincipal.ruta){
            // Invocar a la UI Destino
            PrincipalPedidos(navController = navController)
        }

    }
}
