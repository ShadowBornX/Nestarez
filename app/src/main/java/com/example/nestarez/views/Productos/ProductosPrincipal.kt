package com.example.nestarez.views.Productos

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.nestarez.R
import com.example.nestarez.componentesUI.NIPedidoNew
import com.example.nestarez.componentesUI.NIProductos
import com.example.nestarez.componentesUI.TopBarraRetorno
import com.example.nestarez.navegacion.PedidoNew.ANIPedidoNew
import com.example.nestarez.navegacion.Productos.ANIProductos

@Composable
fun PrincipalProductos(navController: NavController) {
    val navControlador = rememberNavController()
    Scaffold(
        topBar = {
            TopBarraRetorno(titulo = "Productos", colorBarra = Color(0xFFFF683F)) {
                navController.popBackStack()
            }
        },
        bottomBar = { NIProductos(navControlador = navControlador) }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            // La imagen ocupa toda la pantalla
            Image(
                painter = painterResource(id = R.drawable.fondo_ui),
                contentDescription = "Logo",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop // Ajusta la imagen para llenar todo el espacio
            )
            Column(modifier = Modifier.padding(innerPadding)) {
                //pintado de las vistas
                ANIProductos(navControlador = navControlador)
            }

        }
    }
}