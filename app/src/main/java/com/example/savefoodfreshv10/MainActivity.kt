package com.example.savefoodfreshv10

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.savefoodfreshv10.databinding.ActivityMainBinding
import com.example.savefoodfreshv10.roomcodes.AdaptadorListener
import com.example.savefoodfreshv10.roomcodes.AdaptadorProductos
import com.example.savefoodfreshv10.roomcodes.DbProducto
import com.example.savefoodfreshv10.roomcodes.Producto
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import android.view.View


class MainActivity : AppCompatActivity(), AdaptadorListener {

    lateinit var binding: ActivityMainBinding

    var listaProductos: MutableList<Producto> = mutableListOf()
    lateinit var adaptador: AdaptadorProductos
    lateinit var room: DbProducto
    lateinit var producto: Producto

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvProductos.layoutManager = LinearLayoutManager(this)

        room = Room.databaseBuilder(this, DbProducto::class.java, "dbProductos").allowMainThreadQueries().fallbackToDestructiveMigration().build()

        obtenerProductos(room)



        binding.btnAddUpdate.setOnClickListener {
            if(binding.etNombre.text.isNullOrEmpty() || binding.etCategoria.text.isNullOrEmpty()) {
                Toast.makeText(this,
                    getString(R.string.debes_llenar_todos_los_camposxml), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (binding.btnAddUpdate.text == getString(R.string.agregarxml)) {
                producto = Producto(
                    nombre = binding.etNombre.text.toString().trim(),
                    fechaVencimiento = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(binding.etFechaVencimiento.text.toString()),
                    cantidad = binding.etCantidad.text.toString().toInt(),
                    precio = binding.etPrecio.text.toString().toInt(),
                    categoria = binding.etCategoria.text.toString().trim(),
                    descripcion = binding.etDescripcion.text.toString().trim()
                )

                agregarProducto(room, producto)
            } else if(binding.btnAddUpdate.text == getString(R.string.actualizarxml)) {
                producto.nombre = binding.etNombre.text.toString().trim()
                producto.fechaVencimiento = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(binding.etFechaVencimiento.text.toString())
                producto.cantidad = binding.etCantidad.text.toString().toInt()
                producto.precio = binding.etPrecio.text.toString().toInt()
                producto.categoria = binding.etCategoria.text.toString().trim()
                producto.descripcion = binding.etDescripcion.text.toString().trim()

                actualizarProducto(room, producto)
            }
        }
    }

    fun onClickScheduledDate(v: View?){
        val etScheduledDate = findViewById<EditText>(R.id.etFechaVencimiento)
        val selectedCalendar = Calendar.getInstance()
        val año = selectedCalendar.get(Calendar.YEAR)
        val mes = selectedCalendar.get(Calendar.MONTH)
        val dia = selectedCalendar.get(Calendar.DAY_OF_MONTH)
        val listener = DatePickerDialog.OnDateSetListener{datePicker, a, m, d ->
            etScheduledDate.setText("$a-$m-$a")
        }

        DatePickerDialog(this,listener,año,mes,dia).show()

    }

    fun obtenerProductos(room: DbProducto) {
        lifecycleScope.launch {
            listaProductos = room.daoProducto().obtenerProductos()
            adaptador = AdaptadorProductos(listaProductos, this@MainActivity)
            binding.rvProductos.adapter = adaptador
        }
    }

    fun agregarProducto(room: DbProducto, producto: Producto) {
        lifecycleScope.launch {
            room.daoProducto().agregarProducto(producto)
            obtenerProductos(room)
            limpiarCampos()
        }
    }

    fun actualizarProducto(room: DbProducto, producto: Producto) {
        lifecycleScope.launch {
            room.daoProducto().actualizarProducto(producto.producto, producto.nombre, producto.fechaVencimiento, producto.cantidad, producto.precio, producto.categoria ,producto.descripcion)
            obtenerProductos(room)
            limpiarCampos()
        }
    }

    fun limpiarCampos() {

        producto.nombre = ""
        producto.fechaVencimiento = Date()
        producto.cantidad = 0
        producto.precio = 0
        producto.categoria = ""
        producto.descripcion = ""

        binding.etNombre.setText("")
        binding.etFechaVencimiento.setText("")
        binding.etCantidad.setText("0")
        binding.etPrecio.setText("0")
        binding.etCategoria.setText("")
        binding.etDescripcion.setText("")


        if (binding.btnAddUpdate.text.equals(getString(R.string.actualizarxml))){
            binding.btnAddUpdate.setText(getString(R.string.agregarxml))
            binding.etNombre.isEnabled=true
            binding.etFechaVencimiento.isEnabled=true
            binding.etCantidad.isEnabled=true
            binding.etPrecio.isEnabled=true
            binding.etDescripcion.isEnabled=true
        }


    }

    override fun onEditItemClick(producto: Producto) {
        binding.btnAddUpdate.setText(getString(R.string.actualizarxml))
        binding.etNombre.isEnabled = false
        this.producto = producto
        binding.etNombre.setText(this.producto.nombre)
        binding.etFechaVencimiento.setText(SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(this.producto.fechaVencimiento))
        binding.etCantidad.setText(this.producto.cantidad.toString())
        binding.etPrecio.setText(this.producto.precio.toString())
        binding.etCategoria.setText(this.producto.categoria)
        binding.etDescripcion.setText(this.producto.descripcion)

    }

    override fun onDeleteItemClick(producto: Producto) {

        lifecycleScope.launch {
            room.daoProducto().borrarProducto(producto.producto)
            adaptador.notifyDataSetChanged()
            obtenerProductos(room)
        }

    }
}