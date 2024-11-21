package com.example.nestarez.views.Pedido

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.nestarez.LogicaNegocio.Entidades.DetallePedidoEntidad
import com.example.nestarez.LogicaNegocio.ManejadorF.ManejadorPedidos
import com.example.nestarez.LogicaNegocio.ManejadorF.ManejadorProductos
import com.example.nestarez.R
import com.example.nestarez.componentesUI.NIPedido
import com.example.nestarez.componentesUI.NIPedidoNew
import com.example.nestarez.componentesUI.NIProductos
import com.example.nestarez.componentesUI.TopBarraRetorno
import com.example.nestarez.fonts.fontInria
import com.example.nestarez.navegacion.PedidoNew.ANIPedidoNew
import com.example.nestarez.navegacion.Pedidos.ANIPedido
import com.example.nestarez.navegacion.Productos.ANIProductos


@Composable
fun PrincipalPedidos(navController: NavController) {
    val navControlador = rememberNavController()
    Scaffold(
        topBar = {
            TopBarraRetorno(titulo = "Pedidos", colorBarra = Color(0xFFFF683F)) {
                navController.popBackStack()
            }
        },
        bottomBar = { NIPedido(navControlador = navControlador) }
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
                ANIPedido(navControlador = navControlador)
            }
        }
    }
}
