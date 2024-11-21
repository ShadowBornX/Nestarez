package com.example.nestarez.navegacion

sealed class ElementoNav(val ruta:String) {
    object Principal:ElementoNav(RutasNav.Principal.name)
    object PedidosNewPrincipal:ElementoNav(RutasNav.PedidosNewPrincipal.name)
    object PedidosNewLista:ElementoNav(RutasNav.PedidosNewLista.name)
    object ProductosPrincipal:ElementoNav(RutasNav.ProductosPrincipal.name)
    object ProductosNew:ElementoNav(RutasNav.ProductosNew.name)
    object PedidoPrincipal:ElementoNav(RutasNav.PedidosPrincipal.name)
    object PedidoLista:ElementoNav(RutasNav.PedidosFinish.name)
}