package com.example.tp_kotlin_grupo_v.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tp_kotlin_grupo_v.model.AppDatabase
import com.example.tp_kotlin_grupo_v.HashUtils
import com.example.tp_kotlin_grupo_v.R
import com.example.tp_kotlin_grupo_v.activities.RegisterActivity
import com.example.tp_kotlin_grupo_v.UserDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var userDao: UserDao
    lateinit var btnIniciarSesion: Button
    lateinit var cbRecordar: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        userDao = AppDatabase.Companion.getDatabase(applicationContext).userDao()

        val tvRegister = findViewById<TextView>(R.id.tv_register)
        tvRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        val etEmail = findViewById<EditText>(R.id.et_email)
        val etPassword = findViewById<EditText>(R.id.et_password)
        val cbRecordar = findViewById<CheckBox>(R.id.cbRecordar)

        var preferencias = getSharedPreferences(resources.getString(R.string.sp_credenciales), MODE_PRIVATE)
        var emailGuardado = preferencias.getString(resources.getString(R.string.email), "")
        var passwordGuardado = preferencias.getString(resources.getString(R.string.password), "")

        if (!emailGuardado.isNullOrEmpty() && !passwordGuardado.isNullOrEmpty()) {
            etEmail.setText(emailGuardado)
            etPassword.setText(passwordGuardado)
            cbRecordar.isChecked = true
        }


        btnIniciarSesion = findViewById(R.id.btn_login)

        btnIniciarSesion.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor, ingresa correo y contraseña", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (cbRecordar.isChecked) {
                var preferencias = getSharedPreferences(resources.getString(R.string.sp_credenciales), MODE_PRIVATE)
                preferencias.edit().putString(resources.getString(R.string.email), email).apply()
                preferencias.edit().putString(resources.getString(R.string.password), password).apply()
            }

            CoroutineScope(Dispatchers.IO).launch {
                val user = userDao.getUserByEmail(email)
                val hashedPassword = HashUtils.sha256(password)

                runOnUiThread {
                    if (user != null && user.password == hashedPassword) {
                        Toast.makeText(this@LoginActivity, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this@LoginActivity, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}