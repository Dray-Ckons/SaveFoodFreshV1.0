package com.example.savefoodfreshv10.roomcodes

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.savefoodfreshv10.R
import java.text.SimpleDateFormat
import java.util.Locale

class AdaptadorProductos (
    val listaProductos: MutableList<Producto>,
    val listener: AdaptadorListener
): RecyclerView.Adapter<AdaptadorProductos.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_rv_producto,parent, false)
        return ViewHolder(vista)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val producto= listaProductos[position]

        holder.tvNombre.text = producto.nombre
        holder.tvFechaVencimiento.text = producto.fechaVencimiento?.let { SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(it) }
        holder.tvCantidad.text = producto.cantidad.toString()
        holder.tvPrecio.text = producto.precio.toString()
        holder.tvCategoria.text = producto.categoria
        holder.tvDescripcion.text = producto.descripcion

        holder.btnEditar.setOnClickListener{
            listener.onEditItemClick(producto)
        }

        holder.btnBorrar.setOnClickListener{
            listener.onDeleteItemClick(producto)
        }

    }

    override fun getItemCount(): Int {
        return listaProductos.size
    }

    inner class ViewHolder(ItemView: View): RecyclerView.ViewHolder(ItemView) {
        val cvProducto = itemView.findViewById<CardView>(R.id.cvProducto)
        val tvNombre = itemView.findViewById<TextView>(R.id.tvNombre)
        val tvFechaVencimiento = itemView.findViewById<TextView>(R.id.tvFechaVencimiento)
        val tvCantidad = itemView.findViewById<TextView>(R.id.tvCantidad)
        val tvPrecio = itemView.findViewById<TextView>(R.id.tvPrecio)
        val tvCategoria = itemView.findViewById<TextView>(R.id.tvCategoria)
        val tvDescripcion = itemView.findViewById<TextView>(R.id.tvDescripcion)
        val btnBorrar = itemView.findViewById<Button>(R.id.btnBorrar)
        val btnEditar = itemView.findViewById<Button>(R.id.btnEditar)


    }


}