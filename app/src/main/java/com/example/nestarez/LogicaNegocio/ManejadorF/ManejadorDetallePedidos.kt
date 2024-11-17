package com.example.nestarez.LogicaNegocio.ManejadorF

import com.example.nestarez.LogicaNegocio.Entidades.DetallePedidoEntidad
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class ManejadorDetallePedidos {
    private val dbDetallePedidos = FirebaseFirestore.getInstance().collection("DetallePedidos")

    fun agregarDetallePedido(detalle: DetallePedidoEntidad) {
        val key = dbDetallePedidos.document().id
        detalle.id_detalle = key
        dbDetallePedidos.document(key).set(detalle)
    }

    fun obtenerDetallesPedido(id_pedido: String): Flow<List<DetallePedidoEntidad>> {
        val flujo = callbackFlow {
            val listener = dbDetallePedidos.whereEqualTo("id_pedido", id_pedido)
                .addSnapshotListener { snapshot, e ->
                    if (e != null) {
                        close(e)
                        return@addSnapshotListener
                    }
                    val lista = snapshot?.documents?.mapNotNull { doc ->
                        doc.toObject(DetallePedidoEntidad::class.java)?.copy(id_detalle = doc.id)
                    } ?: listOf()
                    trySend(lista).isSuccess
                }
            awaitClose { listener.remove() }
        }
        return flujo
    }

    fun actualizarDetallePedido(detalle: DetallePedidoEntidad) {
        detalle.id_detalle?.let {
            dbDetallePedidos.document(it).set(detalle)
        }
    }

    fun eliminarDetallePedido(id_detalle: String) {
        dbDetallePedidos.document(id_detalle).delete()
    }
}
