package com.example.savefoodfreshv10.roomcodes

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.savefoodfreshv10.roomcodes.Producto
import com.example.savefoodfreshv10.databinding.ItemRvProductoBinding
import java.text.SimpleDateFormat
import java.util.Locale

class AdaptadorProductos(
    private val listaProductos: MutableList<Producto>,
    private val listener: AdaptadorListener
) : RecyclerView.Adapter<AdaptadorProductos.ViewHolder>(), Filterable {

    private var listaProductosFiltrada: MutableList<Producto> = listaProductos.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRvProductoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val producto = listaProductosFiltrada[position]

        holder.bind(producto)

        holder.binding.btnEditar.setOnClickListener {
            listener.onEditItemClick(producto)
        }

        holder.binding.btnBorrar.setOnClickListener {
            listener.onDeleteItemClick(producto)
        }
    }

    override fun getItemCount(): Int {
        return listaProductosFiltrada.size
    }

    inner class ViewHolder(val binding: ItemRvProductoBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(producto: Producto) {
            binding.tvNombre.text = producto.nombre
            binding.tvFechaVencimiento.text =
                producto.fechaVencimiento?.let { SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(it) }
            binding.tvCantidad.text = producto.cantidad.toString()
            binding.tvPrecio.text = producto.precio.toString()
            binding.tvCategoria.text = producto.categoria
            binding.tvDescripcion.text = producto.descripcion
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val resultadosFiltrados = mutableListOf<Producto>()

                if (constraint.isNullOrBlank()) {
                    resultadosFiltrados.addAll(listaProductos)
                } else {
                    val filtro = constraint.toString().toLowerCase(Locale.getDefault())
                    for (producto in listaProductos) {
                        if (producto.nombre.toLowerCase(Locale.getDefault()).contains(filtro) ||
                            producto.categoria.toLowerCase(Locale.getDefault()).contains(filtro)) {
                            resultadosFiltrados.add(producto)
                        }
                    }
                }

                val resultados = FilterResults()
                resultados.values = resultadosFiltrados
                return resultados
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                listaProductosFiltrada.clear()
                listaProductosFiltrada.addAll(results?.values as MutableList<Producto>)
                notifyDataSetChanged()
            }
        }
    }
}