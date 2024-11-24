package com.example.nestarez.LogicaNegocio.ManejadorF

import com.example.nestarez.LogicaNegocio.Entidades.DetallePedidoEntidad
import com.example.nestarez.LogicaNegocio.Entidades.PedidoEntidad
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.util.Calendar

class ManejadorPedidos {

    private val dbPedidos = FirebaseFirestore.getInstance().collection("Pedidos")

    fun agregarPedidoConDetallesAnidados(
        pedido: PedidoEntidad,
        detalles: List<DetallePedidoEntidad>,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val batch = FirebaseFirestore.getInstance().batch()
        val keyPedido = dbPedidos.document().id // Generar ID del pedido
        pedido.id_pedido = keyPedido

        // Referencia al documento del pedido
        val pedidoRef = dbPedidos.document(keyPedido)
        batch.set(pedidoRef, pedido)

        // Crear la subcolección "DetallePedidos" dentro del documento del pedido
        detalles.forEach { detalle ->
            val keyDetalle = pedidoRef.collection("DetallePedidos").document().id
            detalle.id_detalle = keyDetalle
            val detalleRef = pedidoRef.collection("DetallePedidos").document(keyDetalle)
            batch.set(detalleRef, detalle)
        }

        // Ejecutar la operación en batch
        batch.commit()
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { e ->
                onFailure(e)
            }
    }

    fun buscarPedidosPorNombre(query: String, estado:String): Flow<List<PedidoEntidad>> {
        val flujo = callbackFlow {
            // Configurar la consulta
            val consulta = if (query.isBlank()) {
                dbPedidos
                    .whereEqualTo("estado", estado)
                    .orderBy("fecha_pedido")
            } else {
                dbPedidos
                    .whereEqualTo("estado", estado)
                    .whereGreaterThanOrEqualTo(
                        "nombre_cliente",
                        query.lowercase()
                    ) // Buscar coincidencias en minúsculas
                    .whereLessThanOrEqualTo(
                        "nombre_cliente",
                        query.lowercase() + "\uf8ff"
                    )
                    .orderBy("nombre_cliente") // Es necesario para las consultas con rango
                    .orderBy("fecha_pedido")
            }

            // Escuchar los cambios en la base de datos
            val listener = consulta.addSnapshotListener { snapshot, e ->
                if (e != null) {
                    close(e)
                    return@addSnapshotListener
                }

                val lista = snapshot?.documents?.mapNotNull { doc ->
                    doc.toObject(PedidoEntidad::class.java)?.copy(id_pedido = doc.id)
                } ?: listOf()

                // Enviar la lista al flujo
                trySend(lista).isSuccess
            }

            awaitClose { listener.remove() }
        }
        return flujo
    }

    fun buscarPedidosPorNombre(
        query: String,
        estado: String,
        fechaEspecifica: Timestamp? // Agregar parámetro de fecha
    ): Flow<List<PedidoEntidad>> {
        val flujo = callbackFlow {
            val consulta = when {
                // Si ambos parámetros están vacíos, mostramos todos los pedidos
                query.isBlank() && fechaEspecifica == null -> {
                    dbPedidos
                        .whereEqualTo("estado", estado)
                        .orderBy("fecha_pedido")
                }

                // Si solo la fecha es proporcionada, buscamos solo por fecha
                fechaEspecifica != null && query.isBlank() -> {
                    val fechaInicio = Calendar.getInstance().apply {
                        time = fechaEspecifica.toDate()
                        set(Calendar.HOUR_OF_DAY, 0)
                        set(Calendar.MINUTE, 0)
                        set(Calendar.SECOND, 0)
                        set(Calendar.MILLISECOND, 0)
                    }.time

                    val fechaFin = Calendar.getInstance().apply {
                        time = fechaEspecifica.toDate()
                        set(Calendar.HOUR_OF_DAY, 23)
                        set(Calendar.MINUTE, 59)
                        set(Calendar.SECOND, 59)
                        set(Calendar.MILLISECOND, 999)
                    }.time

                    dbPedidos
                        .whereEqualTo("estado", estado)
                        .whereGreaterThanOrEqualTo("fecha_pedido", Timestamp(fechaInicio))
                        .whereLessThanOrEqualTo("fecha_pedido", Timestamp(fechaFin))
                        .orderBy("fecha_pedido")
                }

                // Si solo el query es proporcionado, buscamos solo por nombre
                query.isNotBlank() && fechaEspecifica == null -> {
                    dbPedidos
                        .whereEqualTo("estado", estado)
                        .whereGreaterThanOrEqualTo("nombre_cliente", query.lowercase())
                        .whereLessThanOrEqualTo("nombre_cliente", query.lowercase() + "\uf8ff")
                        .orderBy("nombre_cliente")
                        .orderBy("fecha_pedido")
                }

                // Si ambos parámetros son proporcionados, buscamos por nombre y fecha
                else -> {
                    val fechaInicio = Calendar.getInstance().apply {
                        if (fechaEspecifica != null) {
                            time = fechaEspecifica.toDate()
                        }
                        set(Calendar.HOUR_OF_DAY, 0)
                        set(Calendar.MINUTE, 0)
                        set(Calendar.SECOND, 0)
                        set(Calendar.MILLISECOND, 0)
                    }.time

                    val fechaFin = Calendar.getInstance().apply {
                        if (fechaEspecifica != null) {
                            time = fechaEspecifica.toDate()
                        }
                        set(Calendar.HOUR_OF_DAY, 23)
                        set(Calendar.MINUTE, 59)
                        set(Calendar.SECOND, 59)
                        set(Calendar.MILLISECOND, 999)
                    }.time

                    dbPedidos
                        .whereEqualTo("estado", estado)
                        .whereGreaterThanOrEqualTo("nombre_cliente", query.lowercase())
                        .whereLessThanOrEqualTo("nombre_cliente", query.lowercase() + "\uf8ff")
                        .whereGreaterThanOrEqualTo("fecha_pedido", Timestamp(fechaInicio))
                        .whereLessThanOrEqualTo("fecha_pedido", Timestamp(fechaFin))
                        .orderBy("nombre_cliente")
                        .orderBy("fecha_pedido")
                }
            }

            // Escuchar los cambios en la base de datos
            val listener = consulta.addSnapshotListener { snapshot, e ->
                if (e != null) {
                    close(e)
                    return@addSnapshotListener
                }

                val lista = snapshot?.documents?.mapNotNull { doc ->
                    doc.toObject(PedidoEntidad::class.java)?.copy(id_pedido = doc.id)
                } ?: listOf()

                // Enviar la lista al flujo
                trySend(lista).isSuccess
            }

            awaitClose { listener.remove() }
        }
        return flujo
    }

    fun obtenerDetallesPedido(
        idPedido: String,
        onSuccess: (List<DetallePedidoEntidad>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        dbPedidos.document(idPedido).collection("DetallePedidos").get()
            .addOnSuccessListener { snapshot ->
                val detalles = snapshot.documents.mapNotNull { doc ->
                    doc.toObject(DetallePedidoEntidad::class.java)?.apply {
                        id_detalle = doc.id // Asignar el ID del documento al detalle
                    }
                }
                onSuccess(detalles)
            }
            .addOnFailureListener { e ->
                onFailure(e)
            }
    }

    // Actualizar un pedido
    fun actualizarPedido(pedido: PedidoEntidad) {
        pedido.id_pedido?.let {
            dbPedidos.document(it).set(pedido)
        }
    }

    // Eliminar un pedido
    fun eliminarPedido(id_pedido: String) {
        dbPedidos.document(id_pedido).delete()
    }
}

