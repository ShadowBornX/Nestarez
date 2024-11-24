package com.example.nestarez.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AllInbox
import androidx.compose.material.icons.filled.BreakfastDining
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.FreeBreakfast
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.nestarez.R
import com.example.nestarez.navegacion.ElementoNav
import com.example.nestarez.componentesUI.BotonNav
import com.example.nestarez.componentesUI.TopBarra

@Composable
fun PrincipalView(navController: NavController) {

    Scaffold(modifier = Modifier
        .fillMaxSize(),
        topBar = { TopBarra(titulo = "¡Bienvenido!", colorBarra = Color(0xFFFF683F)) }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.fondo_ui),
                contentDescription = "Logo",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Nestarez",style = TextStyle(fontSize = 40.sp, fontWeight = FontWeight.Bold), color = Color.White)
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(40.dp)
                        .shadow(50.dp, shape = RoundedCornerShape(16.dp))
                        .background(Color.White, shape = RoundedCornerShape(16.dp))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceAround
                    ) {
                        Row(modifier = Modifier.height(65.dp)) {
                        }
                        BotonNav(
                            icono = ImageVector.vectorResource(id = R.drawable.add),
                            texto = "Nuevo\nPedido",
                            1f,
                            Color(0xFFFE6565)
                        ) {
                            navController.navigate(ElementoNav.PedidosNewPrincipal.ruta)
                        }
                        BotonNav(
                            icono = ImageVector.vectorResource(id = R.drawable.cake),
                            texto = "Productos",
                            1f,
                            Color(0xFFFE6565)
                        ) {
                            navController.navigate(ElementoNav.ProductosPrincipal.ruta)
                        }
                        BotonNav(
                            icono = ImageVector.vectorResource(id = R.drawable.list),
                            texto = "Lista de\nPedidos",
                            1f,
                            Color(0xFFFE6565)
                        ) {
                            navController.navigate(ElementoNav.PedidoPrincipal.ruta)
                        }

                    }

                }
            }
            Row(modifier = Modifier.padding(innerPadding).padding(top = 30.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                Image(
                    painter = painterResource(id = R.drawable.pa),
                    contentDescription = "", modifier = Modifier.size(150.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.pastel),
                    contentDescription = "", modifier = Modifier.size(150.dp)
                )
            }
        }
    }
}