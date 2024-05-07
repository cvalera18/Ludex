package com.cvalera.ludex.presentation.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.cvalera.ludex.databinding.ActivityAuthBinding
import com.cvalera.ludex.presentation.MainActivity
import com.cvalera.ludex.presentation.ProviderType
import dagger.hilt.android.AndroidEntryPoint

//@AndroidEntryPoint
//class AuthActivity : AppCompatActivity() {
//    private lateinit var binding: ActivityAuthBinding
//    private val viewModel: AuthViewModel by viewModels()
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityAuthBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//        setup()
//    }
//
//    private fun setup() {
//        binding.signUpButton.setOnClickListener {
//            val email = binding.etTextEmail.text.toString()
//            val password = binding.etTextPassword.text.toString()
//            viewModel.createUser(email, password, ::showHome, ::showAlert)
//        }
//
//        binding.signInButton.setOnClickListener {
//            val email = binding.etTextEmail.text.toString()
//            val password = binding.etTextPassword.text.toString()
//            viewModel.signInUser(email, password, ::showHome, ::showAlert)
//        }
//    }
//
//    private fun showAlert() {
//        val builder = AlertDialog.Builder(this)
//        builder.setTitle("Error")
//        builder.setMessage("Se ha producido un error autenticando al usuario")
//        builder.setPositiveButton("Aceptar", null)
//        val dialog: AlertDialog = builder.create()
//        dialog.show()
//    }
//
//    private fun showHome(email: String) {
//        val homeIntent = Intent(this, MainActivity::class.java).apply {
//            putExtra("email", email)
//            putExtra("provider", ProviderType.BASIC.name) // Asumiendo siempre BASIC por ahora
//        }
//        startActivity(homeIntent)
//    }
//
//}