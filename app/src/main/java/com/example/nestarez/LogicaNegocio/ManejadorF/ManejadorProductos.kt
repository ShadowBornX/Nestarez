package com.example.nestarez.LogicaNegocio.ManejadorF

import android.util.Log
import com.example.nestarez.LogicaNegocio.Entidades.DetallePedidoEntidad
import com.example.nestarez.LogicaNegocio.Entidades.ProductoEntidad
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class ManejadorProductos {
    private val dbProductos = FirebaseFirestore.getInstance().collection("Productos")

    fun agregarProducto(producto: ProductoEntidad) {
        val key = dbProductos.document().id
        producto.id_producto = key
        dbProductos.document(key).set(producto)
    }

    fun actualizarProducto(producto: ProductoEntidad) {
        producto.id_producto?.let {
            dbProductos.document(it).set(producto)
        }
    }

    fun eliminarProducto(id_producto: String) {
        dbProductos.document(id_producto).delete()
    }

    // Actualizar el stock de un producto
    fun actualizarStock(idProducto: String, nuevoStock: Int) {
        dbProductos.document(idProducto).update("stock", nuevoStock)
            .addOnSuccessListener {
                Log.d("ManejadorProductos", "Stock actualizado correctamente para $idProducto")
            }
            .addOnFailureListener { e ->
                Log.e("ManejadorProductos", "Error al actualizar el stock de $idProducto", e)
            }
    }

    // Obtener el stock actual de un producto
    fun obtenerStockActual(idProducto: String, onResult: (Int?) -> Unit) {
        dbProductos.document(idProducto).get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val stockActual = document.getLong("stock")?.toInt() ?: 0
                    onResult(stockActual)
                } else {
                    Log.e("ObtenerStock", "Producto no encontrado: $idProducto")
                    onResult(null)
                }
            }
            .addOnFailureListener { e ->
                Log.e("ObtenerStock", "Error al obtener stock para $idProducto", e)
                onResult(null)
            }
    }

    // Actualizar stock en base a una lista de detalles de pedido
    fun actualizarStockPorDetalles(listaDetalles: List<DetallePedidoEntidad>) {
        listaDetalles.forEach { detalle ->
            obtenerStockActual(detalle.id_producto) { stockActual ->
                if (stockActual != null) {
                    val stockNuevo = stockActual - detalle.cantidad
                    if (stockNuevo >= 0) {
                        actualizarStock(detalle.id_producto, stockNuevo)
                    } else {
                        Log.e(
                            "ActualizarStock",
                            "Stock insuficiente para el producto ${detalle.id_producto}"
                        )
                    }
                } else {
                    Log.e(
                        "ActualizarStock",
                        "No se pudo obtener el stock para ${detalle.id_producto}"
                    )
                }
            }
        }
    }

    fun buscarProductosPorNombreI(query: String): Flow<List<ProductoEntidad>> {
        val flujo = callbackFlow {
            // Si el query está vacío, devolvemos todos los productos
            val consulta = if (query.isBlank()) {
                dbProductos
            } else {
                // Generar el rango para la búsqueda con LIKE '%query%'
                dbProductos
                    .whereGreaterThanOrEqualTo(
                        "nombre_lower",
                        query.lowercase()
                    ) // Buscar en minúsculas
                    .whereLessThanOrEqualTo(
                        "nombre_lower",
                        query.lowercase() + "\uf8ff"
                    ) // Completar búsqueda
            }

            // Escuchar los cambios en la base de datos
            val listener = consulta.addSnapshotListener { snapshot, e ->
                if (e != null) {
                    close(e)
                    return@addSnapshotListener
                }

                val lista = snapshot?.documents?.mapNotNull { doc ->
                    doc.toObject(ProductoEntidad::class.java)?.copy(id_producto = doc.id)
                } ?: listOf()

                // Enviar la lista al flujo
                trySend(lista).isSuccess
            }

            awaitClose { listener.remove() }
        }
        return flujo
    }

}
