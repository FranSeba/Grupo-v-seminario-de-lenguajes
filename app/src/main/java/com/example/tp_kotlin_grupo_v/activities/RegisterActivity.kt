package com.example.tp_kotlin_grupo_v.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tp_kotlin_grupo_v.model.AppDatabase
import com.example.tp_kotlin_grupo_v.HashUtils
import com.example.tp_kotlin_grupo_v.R
import com.example.tp_kotlin_grupo_v.User
import com.example.tp_kotlin_grupo_v.UserDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {
    private lateinit var userDao: UserDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        userDao = AppDatabase.Companion.getDatabase(applicationContext).userDao()

        val backArrow = findViewById<ImageView>(R.id.register_back_arrow)
        backArrow.setOnClickListener {
            finish()
        }

        val tvLoginLink = findViewById<TextView>(R.id.tv_login_link)
        tvLoginLink.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }

        val etUsername = findViewById<EditText>(R.id.et_username_register)
        val etEmail = findViewById<EditText>(R.id.et_email_register)
        val etPassword = findViewById<EditText>(R.id.et_password_register)
        val etConfirmPassword = findViewById<EditText>(R.id.et_confirm_password_register)
        val btnRegister = findViewById<Button>(R.id.btn_register)

        btnRegister.setOnClickListener {
            val username = etUsername.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()
            val confirmPassword = etConfirmPassword.text.toString().trim()

            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            CoroutineScope(Dispatchers.IO).launch {
                val existingUserByUsername = userDao.getUserByUsername(username)
                val existingUserByEmail = userDao.getUserByEmail(email)

                if (existingUserByUsername != null) {
                    runOnUiThread {
                        Toast.makeText(this@RegisterActivity, "El nombre de usuario ya existe", Toast.LENGTH_SHORT).show()
                    }
                    return@launch
                }

                if (existingUserByEmail != null) {
                    runOnUiThread {
                        Toast.makeText(this@RegisterActivity, "El correo electrónico ya está registrado", Toast.LENGTH_SHORT).show()
                    }
                    return@launch
                }

                val hashedPassword = HashUtils.sha256(password)
                val newUser = User(username = username, email = email, password = hashedPassword)
                userDao.insertUser(newUser)

                runOnUiThread {
                    Toast.makeText(this@RegisterActivity, "Usuario registrado con éxito", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
}