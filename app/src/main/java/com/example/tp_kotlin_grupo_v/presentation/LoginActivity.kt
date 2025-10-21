package com.example.tp_kotlin_grupo_v.presentation

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.tp_kotlin_grupo_v.R
import com.example.tp_kotlin_grupo_v.domain.UserDao
import com.example.tp_kotlin_grupo_v.repository.AppDatabase
import com.example.tp_kotlin_grupo_v.util.HashUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var userDao: UserDao
    lateinit var btnIniciarSesion: Button
    lateinit var cbRecordar: CheckBox

    private val CHANNEL_ID = "recordar_usuario_channel"
    private val NOTIFICATION_ID = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        userDao = AppDatabase.Companion.getDatabase(applicationContext).userDao()

        createNotificationChannel()
        requestNotificationPermission()

        val tvRegister = findViewById<TextView>(R.id.tv_register)
        tvRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        val etEmail = findViewById<EditText>(R.id.et_email)
        val etPassword = findViewById<EditText>(R.id.et_password)
        cbRecordar = findViewById(R.id.cbRecordar)

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

            CoroutineScope(Dispatchers.IO).launch {
                val user = userDao.getUserByEmail(email)
                val hashedPassword = HashUtils.sha256(password)

                runOnUiThread {
                    if (user != null && user.password == hashedPassword) {
                        val editor = preferencias.edit()
                        if (cbRecordar.isChecked) {
                            editor.putString(resources.getString(R.string.email), email)
                            editor.putString(resources.getString(R.string.password), password)
                            showNotification()
                        } else {
                            editor.clear()
                        }
                        editor.apply()

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

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Recordar Usuario"
            val descriptionText = "Notificaciones cuando se activa la opción de recordar usuario"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), 1)
            }
        }
    }

    private fun showNotification() {
        val notificationText = "Tus datos de inicio de sesión han sido guardados para la próxima vez."

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_sports_esports_24)
            .setContentTitle("Credenciales Guardadas")
            .setContentText(notificationText)
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText(notificationText))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(this)) {
            if (ActivityCompat.checkSelfPermission(this@LoginActivity, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                notify(NOTIFICATION_ID, builder.build())
            }
        }
    }
}
