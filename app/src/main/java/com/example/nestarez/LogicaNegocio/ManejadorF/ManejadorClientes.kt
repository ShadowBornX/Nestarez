package com.example.nestarez.LogicaNegocio.ManejadorF

import com.example.nestarez.LogicaNegocio.Entidades.ClienteEntidad
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class ManejadorClientes {
    private val dbClientes = FirebaseFirestore.getInstance().collection("Clientes")

    fun agregarCliente(cliente: ClienteEntidad) {
        val key = cliente.id_cliente // Generar clave única
        if(key != null){
            dbClientes.document(key).set(cliente)
        } else {
            println("Error al agregar Cliente")
        }
    }

    fun obtenerClientePorId(id_cliente: String): Flow<ClienteEntidad?> {
        val flujo = callbackFlow {
            val listener = dbClientes.document(id_cliente).addSnapshotListener { snapshot, e ->
                if (e != null) {
                    close(e)
                    return@addSnapshotListener
                }
                val cliente = snapshot?.toObject(ClienteEntidad::class.java)?.copy(id_cliente = snapshot.id)
                trySend(cliente).isSuccess
            }
            awaitClose { listener.remove() }
        }
        return flujo
    }
}
