package com.example.nestarez

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nestarez.LogicaNegocio.ManejadorF.ManejadorClientes
import com.example.nestarez.LogicaNegocio.ManejadorF.ManejadorPedidos
import com.example.nestarez.LogicaNegocio.ManejadorF.ManejadorProductos
import com.example.nestarez.LogicaNegocio.Entidades.ClienteEntidad
import com.example.nestarez.LogicaNegocio.Entidades.ProductoEntidad
import com.example.nestarez.navegacion.ManejadorNavegacionExt
import com.example.nestarez.ui.theme.NestarezTheme
import kotlinx.coroutines.delay
import kotlin.math.min

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NestarezTheme {
                MyApp()
            }
        }
    }
}

@Composable
fun MyApp() {
    var startMainScreen by remember { mutableStateOf(false) }

    if (startMainScreen) {
        ManejadorNavegacionExt()
    } else {
        SplashScreen { startMainScreen = true }
    }
}

@Composable
fun SplashScreen(onTimeout: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.fondo_splash),
            contentDescription = "Logo",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop // Ajusta la imagen para llenar todo el espacio
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_nestarez),
                contentDescription = "Logo",
                modifier = Modifier.size(250.dp)
            )
            CircularProgressIndicator(
                modifier = Modifier // Centrar el indicador de carga
                    .padding(16.dp),
                color = Color.White // Cambiar el color del indicador si es necesario
            )

            Text(
                text = "Cargando...",
                modifier = Modifier // Alinear en la parte inferior del centro
                    .padding(bottom = 32.dp), // Espaciado inferior
                color = Color.White, // Color del texto
                style = MaterialTheme.typography.headlineLarge // Estilo del texto
            )
        }
    }

    LaunchedEffect(Unit) {
        delay(5000) // 5000 ms = 5 segundos
        onTimeout()
    }
}