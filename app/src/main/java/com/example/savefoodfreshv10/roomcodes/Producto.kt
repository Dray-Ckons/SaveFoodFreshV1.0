package com.example.savefoodfreshv10.roomcodes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.util.Date

@Entity(tableName = "productos")
data class Producto(
    @PrimaryKey(autoGenerate = true) var producto: Int = 0,
    @ColumnInfo(name = "nombre") var nombre: String,
    @ColumnInfo(name = "fechaVencimiento")var fechaVencimiento: Date,
    @ColumnInfo(name = "cantidad")var cantidad: Int,
    @ColumnInfo(name = "precio")var precio: Int,
    @ColumnInfo(name = "categoria")var categoria: String,
    @ColumnInfo(name = "descripcion")var descripcion: String
)

