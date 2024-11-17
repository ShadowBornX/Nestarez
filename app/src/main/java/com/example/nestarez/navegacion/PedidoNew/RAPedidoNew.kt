package com.example.nestarez.navegacion.PedidoNew

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun RAPedidoNew(navControlador: NavHostController): String? =
    navControlador.currentBackStackEntryAsState().value?.destination?.route