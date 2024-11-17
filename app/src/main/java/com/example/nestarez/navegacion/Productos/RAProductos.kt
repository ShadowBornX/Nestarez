package com.example.nestarez.navegacion.Productos

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun RAProductos(navControlador: NavHostController): String? =
    navControlador.currentBackStackEntryAsState().value?.destination?.route