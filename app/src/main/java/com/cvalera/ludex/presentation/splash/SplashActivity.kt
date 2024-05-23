package com.cvalera.ludex.presentation.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cvalera.ludex.R
import com.cvalera.ludex.presentation.MainActivity
import com.cvalera.ludex.presentation.auth.intro.IntroductionActivity
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            // El usuario está autenticado, ir a la pantalla principal
            val intent = MainActivity.create(this)
            startActivity(intent)
        } else {
            // El usuario no está autenticado, ir a la pantalla de inicio de sesión
            val intent = IntroductionActivity.create(this)
            startActivity(intent)
        }
        finish()
    }
}