package com.example.nestarez.views.PedidoNew

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.nestarez.R
import com.example.nestarez.componentesUI.NIPedidoNew
import com.example.nestarez.navegacion.PedidoNew.ANIPedidoNew
import com.example.nestarez.navegacion.PedidoNew.IBIPedidoNew
import com.example.nestarez.componentesUI.TopBarra
import com.example.nestarez.componentesUI.IconButtonGenerico
import com.example.nestarez.componentesUI.TopBarraRetorno
import com.example.nestarez.navegacion.ElementoNav

@Composable
fun PrincipalPedidoNew(navController: NavController) {
    val navControlador = rememberNavController()
    Scaffold(
        topBar = {
            TopBarraRetorno(titulo = "Nuevo Pedido", colorBarra = Color(0xFFFF683F)) {
                navController.popBackStack()
            }
        },
        bottomBar = { NIPedidoNew(navControlador = navControlador) }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.fondo_ui),
                contentDescription = "Logo",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop // Ajusta la imagen para llenar todo el espacio
            )
            Column(modifier = Modifier.padding(innerPadding)) {
                ANIPedidoNew(navControlador = navControlador)
            }
        }

    }
}