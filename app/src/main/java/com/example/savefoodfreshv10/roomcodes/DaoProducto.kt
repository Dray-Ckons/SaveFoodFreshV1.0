package com.example.savefoodfreshv10.roomcodes

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import java.util.Date

@Dao
interface DaoProducto {

    @Query("SELECT * FROM productos")
    fun obtenerProductos(): MutableList<Producto>

    @Insert
    fun agregarProducto(producto: Producto)

    @Query("UPDATE productos set nombre=:nombre, fechaVencimiento=:fechaVencimiento,  cantidad=:cantidad, precio=:precio, categoria=:categoria, descripcion=:descripcion WHERE producto=:producto ")
    fun actualizarProducto(producto: Int, nombre: String?, fechaVencimiento: Date?, cantidad: Int, precio: Int, categoria: String?, descripcion: String?)

    @Query("DELETE FROM productos WHERE producto=:producto")
    fun borrarProducto(producto: Int)
}

