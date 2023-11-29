package com.example.savefoodfreshv10



import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val editTextEmail = findViewById<EditText>(R.id.editTextEmail)
        val editTextPassword = findViewById<EditText>(R.id.editTextPassword)
        val buttonLogin = findViewById<Button>(R.id.buttonLogin)
        val buttonGoToSignUp = findViewById<Button>(R.id.buttonGoToSingUp)

        buttonLogin.setOnClickListener {
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()

            // Obtener la lista de cuentas desde SharedPreferences
            val sharedPreferences = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE)
            val cuentasJson = sharedPreferences.getString("cuentas", "[]")
            val cuentas: MutableList<Cuenta> =
                Gson().fromJson(cuentasJson, object : TypeToken<MutableList<Cuenta>>() {}.type)

            // Verificar si el correo y la contraseña coinciden con alguna cuenta
            val cuentaExistente = cuentas.find { it.email == email && it.password1 == password }

            if (cuentaExistente != null) {
                // Inicio de sesión exitoso, puedes realizar acciones adicionales aquí
                Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show()
            }
        }

        buttonGoToSignUp.setOnClickListener {
            // Ir a la actividad de registro (SingUpActivity)
            val intent = Intent(this, SingUpActivity::class.java)
            startActivity(intent)
        }
    }
}