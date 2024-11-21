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
                //insertarProductosEnDb()
                //CustomScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomScreen() {
    // Top bar color
    val topBarColor = Color(0xFFFF683F) // Naranja

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Título") },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = topBarColor),
                //modifier = Modifier.background(topBarColor)
            )
        },
        content = { padding ->
            // Contenido con un fondo naranja y esquinas redondeadas
            Box(modifier = Modifier.fillMaxSize()) {
                // La imagen ocupa toda la pantalla
                Image(
                    painter = painterResource(id = R.drawable.fondo_ui),
                    contentDescription = "Logo",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop // Ajusta la imagen para llenar todo el espacio
                )

                Column(
                    modifier = Modifier.fillMaxSize().padding(padding),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .padding(40.dp).background(Color.White, shape = RoundedCornerShape(16.dp))) {
                        Text(
                            text = "Cargando...",
                            modifier = Modifier // Alinear en la parte inferior del centro
                                .padding(bottom = 32.dp), // Espaciado inferior
                            color = Color.White, // Color del texto
                            style = MaterialTheme.typography.headlineLarge // Estilo del texto
                        )
                    }


                    // Texto

                }
            }
        }
    )
}

@Composable
fun MyApp() {
    var startMainScreen by remember { mutableStateOf(false) }

    if (startMainScreen) {
        ManejadorNavegacionExt() // Aquí llamarías a tu pantalla principal
    } else {
        SplashScreen { startMainScreen = true }
    }
}

@Composable
fun SplashScreen(onTimeout: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        // La imagen ocupa toda la pantalla
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
            // Indicador de carga circular
            CircularProgressIndicator(
                modifier = Modifier // Centrar el indicador de carga
                    .padding(16.dp),
                color = Color.White // Cambiar el color del indicador si es necesario
            )

            // Texto
            Text(
                text = "Cargando...",
                modifier = Modifier // Alinear en la parte inferior del centro
                    .padding(bottom = 32.dp), // Espaciado inferior
                color = Color.White, // Color del texto
                style = MaterialTheme.typography.headlineLarge // Estilo del texto
            )
        }
    }

    // Usamos LaunchedEffect para esperar 5 segundos antes de navegar
    LaunchedEffect(Unit) {
        delay(500) // 5000 ms = 5 segundos
        onTimeout()
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NestarezTheme {
        Greeting("Android")
    }
}

@Composable
fun insertarProductosEnDb() {
    val manejadorClientes = ManejadorClientes()
    val manejadorProductos = ManejadorProductos()
    val manejadorPedidos = ManejadorPedidos()

// Agregar un cliente
   /* val nuevoCliente = ClienteEntidad(nombre = "Juan Perez", direccion = "Calle 123", telefono = "999999999", email = "juan@gmail.com")
    manejadorClientes.agregarCliente(nuevoCliente)


// Obtener lista de clientes
    val listaClientesFlow = manejadorClientes.obtenerClientes()

// Agregar un producto
    val nuevoProducto = ProductoEntidad(nombre_producto = "Producto A", descripcion = "Descripción del Producto A", precio = 10.0, stock = 100)
    manejadorProductos.agregarProducto(nuevoProducto)*/

    val productos = listOf(
        ProductoEntidad(nombre_producto = "Torta de Chocolate", nombre_lower = "torta de chocolate", descripcion = "Deliciosa torta con múltiples capas de chocolate.", precio = 20.0, stock = 50, categoria = "Pastel"),
        ProductoEntidad(nombre_producto = "Pastel de Fresas", nombre_lower = "pastel de fresas", descripcion = "Esponjoso pastel cubierto de fresas frescas.", precio = 18.0, stock = 40, categoria = "Pastel"),
        ProductoEntidad(nombre_producto = "Donuts Glaseadas", nombre_lower = "donuts glaseadas", descripcion = "Donuts suaves con un glaseado dulce.", precio = 5.0, stock = 100, categoria = "Postre"),
        ProductoEntidad(nombre_producto = "Brownie de Nuez", nombre_lower = "brownie de nuez", descripcion = "Brownies crujientes con trozos de nuez.", precio = 12.0, stock = 60, categoria = "Postre"),
        ProductoEntidad(nombre_producto = "Cupcakes de Vainilla", nombre_lower = "cupcakes de vainilla", descripcion = "Cupcakes suaves con crema de vainilla.", precio = 7.0, stock = 80, categoria = "Pastel"),
        ProductoEntidad(nombre_producto = "Tarta de Limón", nombre_lower = "tarta de limón", descripcion = "Tarta con crema de limón y merengue.", precio = 16.0, stock = 30, categoria = "Pastel"),
        ProductoEntidad(nombre_producto = "Galletas de Chispas de Chocolate", nombre_lower = "galletas de chispas de chocolate", descripcion = "Galletas rellenas de chispas de chocolate.", precio = 4.0, stock = 150, categoria = "Postre"),
        ProductoEntidad(nombre_producto = "Mousse de Fresa", nombre_lower = "mousse de fresa", descripcion = "Mousse suave de fresa con crema batida.", precio = 10.0, stock = 45, categoria = "Postre"),
        ProductoEntidad(nombre_producto = "Tiramisú", nombre_lower = "tiramisú", descripcion = "Clásico postre italiano con café y cacao.", precio = 22.0, stock = 35, categoria = "Postre"),
        ProductoEntidad(nombre_producto = "Rollitos de Canela", nombre_lower = "rollitos de canela", descripcion = "Rollos de canela suaves con glaseado.", precio = 8.0, stock = 70, categoria = "Pan"),
        ProductoEntidad(nombre_producto = "Milhojas de Crema", nombre_lower = "milhojas de crema", descripcion = "Postre crujiente con capas de crema pastelera.", precio = 15.0, stock = 55, categoria = "Postre"),
        ProductoEntidad(nombre_producto = "Flan de Caramelo", nombre_lower = "flan de caramelo", descripcion = "Flan cremoso bañado en caramelo.", precio = 14.0, stock = 40, categoria = "Postre"),
        ProductoEntidad(nombre_producto = "Tarta de Manzana", nombre_lower = "tarta de manzana", descripcion = "Tarta tradicional con relleno de manzana.", precio = 18.0, stock = 25, categoria = "Pastel"),
        ProductoEntidad(nombre_producto = "Panettone Tradicional", nombre_lower = "panettone tradicional", descripcion = "Pan dulce italiano con frutas secas.", precio = 25.0, stock = 20, categoria = "Pan"),
        ProductoEntidad(nombre_producto = "Trufas de Chocolate", nombre_lower = "trufas de chocolate", descripcion = "Trufas de chocolate amargo y leche.", precio = 6.0, stock = 90, categoria = "Postre"),
        ProductoEntidad(nombre_producto = "Cheesecake de Maracuyá", nombre_lower = "cheesecake de maracuyá", descripcion = "Cheesecake suave con maracuyá.", precio = 24.0, stock = 30, categoria = "Postre"),
        ProductoEntidad(nombre_producto = "Macarons de Colores", nombre_lower = "macarons de colores", descripcion = "Delicados macarons de almendra en varios sabores.", precio = 12.0, stock = 60, categoria = "Postre"),
        ProductoEntidad(nombre_producto = "Tarta de Queso y Frutos Rojos", nombre_lower = "tarta de queso y frutos rojos", descripcion = "Tarta de queso con frutas frescas.", precio = 21.0, stock = 25, categoria = "Pastel"),
        ProductoEntidad(nombre_producto = "Helado Artesanal de Vainilla", nombre_lower = "helado artesanal de vainilla", descripcion = "Helado cremoso de vainilla.", precio = 15.0, stock = 40, categoria = "Postre"),
        ProductoEntidad(nombre_producto = "Croissants de Mantequilla", nombre_lower = "croissants de mantequilla", descripcion = "Croissants suaves con mantequilla.", precio = 9.0, stock = 80, categoria = "Pan"),
        ProductoEntidad(nombre_producto = "Eclairs de Chocolate", nombre_lower = "eclairs de chocolate", descripcion = "Eclairs rellenos de crema de chocolate.", precio = 14.0, stock = 50, categoria = "Postre"),
        ProductoEntidad(nombre_producto = "Bizcocho de Naranja", nombre_lower = "bizcocho de naranja", descripcion = "Bizcocho esponjoso con sabor a naranja.", precio = 12.0, stock = 65, categoria = "Pastel"),
        ProductoEntidad(nombre_producto = "Pastelitos de Coco", nombre_lower = "pastelitos de coco", descripcion = "Pastelitos dulces con coco rallado.", precio = 10.0, stock = 70, categoria = "Pastel"),
        ProductoEntidad(nombre_producto = "Tarta de Durazno", nombre_lower = "tarta de durazno", descripcion = "Tarta de masa crujiente con duraznos frescos.", precio = 19.0, stock = 30, categoria = "Pastel"),
        ProductoEntidad(nombre_producto = "Helado de Fresa", nombre_lower = "helado de fresa", descripcion = "Helado de fresas frescas.", precio = 13.0, stock = 50, categoria = "Postre"),
        ProductoEntidad(nombre_producto = "Panque de Plátano", nombre_lower = "panque de plátano", descripcion = "Panque suave con plátano maduro.", precio = 11.0, stock = 60, categoria = "Pan"),
        ProductoEntidad(nombre_producto = "Bombas de Crema", nombre_lower = "bombas de crema", descripcion = "Bombas rellenas de crema pastelera.", precio = 5.0, stock = 100, categoria = "Postre"),
        ProductoEntidad(nombre_producto = "Torta de Red Velvet", nombre_lower = "torta de red velvet", descripcion = "Torta de terciopelo rojo con crema de queso.", precio = 20.0, stock = 40, categoria = "Pastel"),
        ProductoEntidad(nombre_producto = "Quesadilla de Nata", nombre_lower = "quesadilla de nata", descripcion = "Quesadilla rellena de nata fresca.", precio = 14.0, stock = 45, categoria = "Pan"),
        ProductoEntidad(nombre_producto = "Pastel de Zanahoria", nombre_lower = "pastel de zanahoria", descripcion = "Pastel saludable con zanahorias ralladas y nueces.", precio = 18.0, stock = 35, categoria = "Pastel")
    )



    productos.forEach { producto ->
        manejadorProductos.agregarProducto(producto)
    }

}

/*@Composable
fun ClienteListView(viewModel: ClienteViewModel) {
    val clientes = viewModel.obtenerListaClientes().observeAsState(listOf())

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Lista de Clientes") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /* Navegar al formulario para agregar cliente */ }) {
                Icon(Icons.Filled.Add, contentDescription = "Agregar Cliente")
            }
        }
    ) {
        LazyColumn {
            items(clientes.value) { cliente ->
                ClienteItem(cliente = cliente)
            }
        }
    }
}

@Composable
fun ClienteItem(cliente: ClienteEntidad) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "${cliente.nombre} ${cliente.apellidos}", style = MaterialTheme.typography.h6)
            Text(text = cliente.email)
            Text(text = cliente.telefono)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClienteFormView(viewModel: ClienteViewModel, cliente: ClienteEntidad?) {
    var nombre by remember { mutableStateOf(cliente?.nombre ?: "") }
    var apellidos by remember { mutableStateOf(cliente?.apellidos ?: "") }
    var email by remember { mutableStateOf(cliente?.email ?: "") }
    var telefono by remember { mutableStateOf(cliente?.telefono ?: "") }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(if (cliente == null) "Agregar Cliente" else "Editar Cliente") })
        }
    ) {inne->
        Column(modifier = Modifier.padding(16.dp)) {
            TextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre") }
            )
            TextField(
                value = apellidos,
                onValueChange = { apellidos = it },
                label = { Text("Apellidos") }
            )
            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") }
            )
            TextField(
                value = telefono,
                onValueChange = { telefono = it },
                label = { Text("Teléfono") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                if (cliente == null) {
                    viewModel.guardarCliente(ClienteEntidad(nombre = nombre, apellidos = apellidos, email = email, telefono = telefono))
                } else {
                    viewModel.actualizarCliente(ClienteEntidad(id_cliente = cliente.id_cliente, nombre = nombre, apellidos = apellidos, email = email, telefono = telefono))
                }
            }) {
                Text("Guardar")
            }
        }
    }
}

@Composable
fun ProductoListView(viewModel: ProductoViewModel) {
    val productos = viewModel.obtenerListaProductos().observeAsState(listOf())

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Lista de Productos") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /* Navegar al formulario para agregar producto */ }) {
                Icon(Icons.Filled.Add, contentDescription = "Agregar Producto")
            }
        }
    ) {
        LazyColumn {
            items(productos.value) { producto ->
                ProductoItem(producto = producto)
            }
        }
    }
}

@Composable
fun ProductoItem(producto: ProductoEntidad) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = producto.nombre_producto, style = MaterialTheme.typography.h6)
            Text(text = "Precio: S/.${producto.precio}")
            Text(text = "Stock: ${producto.stock}")
        }
    }
}

@Composable
fun ProductoFormView(viewModel: ProductoViewModel, producto: ProductoEntidad?) {
    var nombreProducto by remember { mutableStateOf(producto?.nombre_producto ?: "") }
    var descripcion by remember { mutableStateOf(producto?.descripcion ?: "") }
    var precio by remember { mutableStateOf(producto?.precio?.toString() ?: "") }
    var stock by remember { mutableStateOf(producto?.stock?.toString() ?: "") }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(if (producto == null) "Agregar Producto" else "Editar Producto") })
        }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            TextField(
                value = nombreProducto,
                onValueChange = { nombreProducto = it },
                label = { Text("Nombre del Producto") }
            )
            TextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = { Text("Descripción") }
            )
            TextField(
                value = precio,
                onValueChange = { precio = it },
                label = { Text("Precio") }
            )
            TextField(
                value = stock,
                onValueChange = { stock = it },
                label = { Text("Stock") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                if (producto == null) {
                    viewModel.guardarProducto(ProductoEntidad(nombre_producto = nombreProducto, descripcion = descripcion, precio = precio.toDouble(), stock = stock.toInt()))
                } else {
                    viewModel.actualizarProducto(ProductoEntidad(id_producto = producto.id_producto, nombre_producto = nombreProducto, descripcion = descripcion, precio = precio.toDouble(), stock = stock.toInt()))
                }
            }) {
                Text("Guardar")
            }
        }
    }
}

@Composable
fun PedidoListView(viewModel: PedidoViewModel) {
    val pedidos = viewModel.obtenerListaPedidos().observeAsState(listOf())

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Lista de Pedidos") })
        }
    ) {
        LazyColumn {
            items(pedidos.value) { pedido ->
                PedidoItem(pedido = pedido)
            }
        }
    }
}

@Composable
fun PedidoItem(pedido: PedidoEntidad) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "ID Pedido: ${pedido.id_pedido}", style = MaterialTheme.typography.h6)
            Text(text = "Cliente ID: ${pedido.id_cliente}")
            Text(text = "Fecha: ${pedido.fecha_pedido}")
            Text(text = "Total: S/.${pedido.total}")
        }
    }
}

@Composable
fun DetallePedidoListView(viewModel: DetallePedidoViewModel, idPedido: Int) {
    val detalles = viewModel.obtenerDetallesPedido(idPedido).observeAsState(listOf())

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Detalle del Pedido #$idPedido") })
        }
    ) {
        LazyColumn {
            items(detalles.value) { detalle ->
                DetallePedidoItem(detalle = detalle)
            }
        }
    }
}

@Composable
fun DetallePedidoItem(detalle: DetallePedidoEntidad) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Producto ID: ${detalle.id_producto}", style = MaterialTheme.typography.h6)
            Text(text = "Cantidad: ${detalle.cantidad}")
            Text(text = "Precio Unitario: S/.${detalle.precio_unitario}")
            Text(text = "Subtotal: S/.${detalle.subtotal}")
        }
    }
}*/
