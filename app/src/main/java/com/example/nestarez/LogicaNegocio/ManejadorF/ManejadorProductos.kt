package com.example.nestarez.LogicaNegocio.ManejadorF

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

    fun obtenerProductos(): Flow<List<ProductoEntidad>> {
        val flujo = callbackFlow {
            val listener = dbProductos.addSnapshotListener { snapshot, e ->
                if (e != null) {
                    close(e)
                    return@addSnapshotListener
                }
                val lista = snapshot?.documents?.mapNotNull { doc ->
                    doc.toObject(ProductoEntidad::class.java)?.copy(id_producto = doc.id)
                } ?: listOf()
                trySend(lista).isSuccess
            }
            awaitClose { listener.remove() }
        }
        return flujo
    }

    fun actualizarProducto(producto: ProductoEntidad) {
        producto.id_producto?.let {
            dbProductos.document(it).set(producto)
        }
    }

    fun eliminarProducto(id_producto: String) {
        dbProductos.document(id_producto).delete()
    }

    fun buscarProductosPorNombreAD(query: String): Flow<List<ProductoEntidad>> {
        val flujo = callbackFlow {
            val listener = dbProductos.addSnapshotListener { snapshot, e ->
                if (e != null) {
                    close(e)
                    return@addSnapshotListener
                }
                val lista = snapshot?.documents?.mapNotNull { doc ->
                    doc.toObject(ProductoEntidad::class.java)?.copy(id_producto = doc.id)
                }?.filter { producto ->
                    producto.nombre_producto.contains(
                        query,
                        ignoreCase = true
                    ) // Filtrado por '%query%'
                } ?: listOf()
                trySend(lista).isSuccess
            }
            awaitClose { listener.remove() }
        }
        return flujo
    }

    fun buscarProductosPorNombre2(query: String): Flow<List<ProductoEntidad>> {
        val flujo = callbackFlow {
            // Generar el rango para la búsqueda
            val finalQuery = query + "\uf8ff"  // Sufijo para limitar el rango
            val listener = dbProductos
                .whereGreaterThanOrEqualTo("nombre", query)
                .whereLessThanOrEqualTo("nombre", finalQuery)
                .addSnapshotListener { snapshot, e ->
                    if (e != null) {
                        close(e)
                        return@addSnapshotListener
                    }
                    val lista = snapshot?.documents?.mapNotNull { doc ->
                        doc.toObject(ProductoEntidad::class.java)?.copy(id_producto = doc.id)
                    } ?: listOf()
                    trySend(lista).isSuccess
                }
            awaitClose { listener.remove() }
        }
        return flujo
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
