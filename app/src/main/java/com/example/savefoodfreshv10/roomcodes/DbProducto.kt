package com.example.savefoodfreshv10.roomcodes

import androidx.room.Database
import androidx.room.InvalidationTracker
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters


@Database(
    entities = [Producto::class],
    version = 1
)
@TypeConverters(Converters::class)

abstract class DbProducto: RoomDatabase() {
    abstract fun daoProducto(): DaoProducto

}