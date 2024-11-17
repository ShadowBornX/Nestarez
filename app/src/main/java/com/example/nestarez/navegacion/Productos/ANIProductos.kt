package com.example.nestarez.navegacion.Productos

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.nestarez.navegacion.ElementoNav
import com.example.nestarez.views.Productos.InicioProductos
import com.example.nestarez.views.Productos.ProductosNew

@Composable
fun ANIProductos(navControlador: NavHostController){
    NavHost(navController = navControlador, startDestination = ElementoNav.ProductosPrincipal.ruta){
        composable(route = ElementoNav.ProductosPrincipal.ruta){
            // Invocar a la UI Destino
            InicioProductos()
        }
        composable(route = ElementoNav.ProductosNew.ruta){
            // Invocar a la UI Destino
            ProductosNew()
        }
    }
}