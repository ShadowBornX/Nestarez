package com.example.nestarez.LogicaNegocio.ManejadorF

import com.example.nestarez.LogicaNegocio.Entidades.ClienteEntidad
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class ManejadorClientes {
    private val dbClientes = FirebaseFirestore.getInstance().collection("Clientes")

    // Agregar un cliente
    fun agregarCliente(cliente: ClienteEntidad) {
        val key = cliente.id_cliente // Generar clave única
        if(key != null){
            dbClientes.document(key).set(cliente)
        } else {
            println("Error al agregar Cliente")
        }

    }

    // Obtener lista de clientes
    fun obtenerClientes(): Flow<List<ClienteEntidad>> {
        val flujo = callbackFlow {
            val listener = dbClientes.addSnapshotListener { snapshot, e ->
                if (e != null) {
                    close(e)
                    return@addSnapshotListener
                }
                val lista = snapshot?.documents?.mapNotNull { doc ->
                    doc.toObject(ClienteEntidad::class.java)?.copy(id_cliente = doc.id)
                } ?: listOf()
                trySend(lista).isSuccess
            }
            awaitClose { listener.remove() }
        }
        return flujo
    }

    // Obtener un cliente por su id_cliente
    fun obtenerClientePorId(id_cliente: String): Flow<ClienteEntidad?> {
        val flujo = callbackFlow {
            val listener = dbClientes.document(id_cliente).addSnapshotListener { snapshot, e ->
                if (e != null) {
                    close(e)
                    return@addSnapshotListener
                }

                // Verifica si el documento existe
                val cliente = snapshot?.toObject(ClienteEntidad::class.java)?.copy(id_cliente = snapshot.id)

                // Enviar el cliente al flujo
                trySend(cliente).isSuccess
            }
            awaitClose { listener.remove() }
        }
        return flujo
    }


    // Actualizar un cliente
    fun actualizarCliente(cliente: ClienteEntidad) {
        cliente.id_cliente?.let {
            dbClientes.document(it).set(cliente)
        }
    }

    // Eliminar un cliente
    fun eliminarCliente(id_cliente: String) {
        dbClientes.document(id_cliente).delete()
    }
}
