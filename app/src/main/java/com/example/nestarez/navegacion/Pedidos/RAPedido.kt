package com.example.nestarez.navegacion.Pedidos

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun RAPedido(navControlador: NavHostController): String? =
    navControlador.currentBackStackEntryAsState().value?.destination?.route