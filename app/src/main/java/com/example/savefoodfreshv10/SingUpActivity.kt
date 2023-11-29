package com.example.savefoodfreshv10

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

data class Cuenta(val nombre: String, val email: String, val password1: String)

class SingUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sing_up)

        val editTextNombre = findViewById<EditText>(R.id.editTextNombre)
        val editTextEmail = findViewById<EditText>(R.id.editTextEmail)
        val editTextPassword1 = findViewById<EditText>(R.id.editTextPassword1)
        val editTextPassword2 = findViewById<EditText>(R.id.editTextPassword2)
        val buttonSingUp = findViewById<Button>(R.id.buttonSingUp)

        buttonSingUp.setOnClickListener {
            // Obtener los valores de los EditText
            val nombre = editTextNombre.text.toString()
            val email = editTextEmail.text.toString()
            val password1 = editTextPassword1.text.toString()
            val password2 = editTextPassword2.text.toString()

            if (password1 == password2) {
                // Obtener la lista actual de cuentas desde SharedPreferences
                val sharedPreferences = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE)
                val cuentasJson = sharedPreferences.getString("cuentas", "[]")

                // Convertir la lista JSON a una lista de objetos Cuenta
                val cuentas: MutableList<Cuenta> = Gson().fromJson(cuentasJson, object : TypeToken<MutableList<Cuenta>>() {}.type)

                // Verificar si el correo electrónico ya se ha utilizado
                val correoYaUtilizado = cuentas.any { it.email == email }

                if (!correoYaUtilizado) {
                    // Agregar la nueva cuenta a la lista
                    cuentas.add(Cuenta(nombre, email, password1))

                    // Convertir la lista de nuevo a JSON y guardarla en SharedPreferences
                    val editor = sharedPreferences.edit()
                    editor.putString("cuentas", Gson().toJson(cuentas))
                    editor.apply()

                    // Mostrar un mensaje o realizar otras acciones después de guardar
                    Toast.makeText(this,
                        getString(R.string.cuenta_creada_correctamente), Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this,
                        getString(R.string.el_correo_electr_nico_ya_est_en_uso), Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, getString(R.string.verifique_la_contrase_a), Toast.LENGTH_SHORT).show()
            }
        }

    }
}
