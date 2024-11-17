package com.example.nestarez.LogicaNegocio.ManejadorF

import com.example.nestarez.LogicaNegocio.Entidades.DetallePedidoEntidad
import com.example.nestarez.LogicaNegocio.Entidades.PedidoEntidad
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class ManejadorPedidos {
    /*private val dbPedidos = FirebaseFirestore.getInstance().collection("Pedidos")

    fun agregarPedido(pedido: PedidoEntidad) {
        val key = dbPedidos.document().id
        pedido.id_pedido = key
        dbPedidos.document(key).set(pedido)
    }

    fun obtenerPedidos(): Flow<List<PedidoEntidad>> {
        val flujo = callbackFlow {
            val listener = dbPedidos.addSnapshotListener { snapshot, e ->
                if (e != null) {
                    close(e)
                    return@addSnapshotListener
                }
                val lista = snapshot?.documents?.mapNotNull { doc ->
                    doc.toObject(PedidoEntidad::class.java)?.copy(id_pedido = doc.id)
                } ?: listOf()
                trySend(lista).isSuccess
            }
            awaitClose { listener.remove() }
        }
        return flujo
    }

    fun actualizarPedido(pedido: PedidoEntidad) {
        pedido.id_pedido?.let {
            dbPedidos.document(it).set(pedido)
        }
    }

    fun eliminarPedido(id_pedido: String) {
        dbPedidos.document(id_pedido).delete()
    }*/
    private val dbPedidos = FirebaseFirestore.getInstance().collection("Pedidos")
    private val dbDetallePedidos = FirebaseFirestore.getInstance().collection("DetallePedidos")

    // Agregar un nuevo pedido junto con los detalles
    //private val dbPedidos = FirebaseFirestore.getInstance().collection("Pedidos")

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
            detalle.id_pedido = keyPedido // Relacionar el detalle con el pedido
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

    // Obtener lista de pedidos
    fun obtenerPedidos(): Flow<List<PedidoEntidad>> {
        val flujo = callbackFlow {
            val listener = dbPedidos.addSnapshotListener { snapshot, e ->
                if (e != null) {
                    close(e)
                    return@addSnapshotListener
                }
                val lista = snapshot?.documents?.mapNotNull { doc ->
                    doc.toObject(PedidoEntidad::class.java)?.copy(id_pedido = doc.id)
                } ?: listOf()
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

    fun obtenerListaPedidos(
        onSuccess: (List<PedidoEntidad>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        dbPedidos.get()
            .addOnSuccessListener { snapshot ->
                val pedidos = snapshot.documents.mapNotNull { doc ->
                    doc.toObject(PedidoEntidad::class.java)?.apply {
                        id_pedido = doc.id  // Asignar el ID del documento al pedido
                    }
                }
                onSuccess(pedidos)
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

