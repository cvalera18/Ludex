package com.cvalera.ludex.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.GravityCompat
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.cvalera.ludex.R
import com.cvalera.ludex.databinding.ActivityMainBinding
import com.cvalera.ludex.presentation.auth.signin.SignInActivity
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.AndroidEntryPoint

enum class ProviderType {
    BASIC
}
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    companion object {
        fun create(context: Context): Intent =
            Intent(context, MainActivity::class.java)
    }

    private lateinit var binding: ActivityMainBinding
    private val sharedViewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNavigationView = binding.navMenu
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.frameContainer) as NavHostFragment
        bottomNavigationView.setupWithNavController(navHostFragment.navController)

        // Analytics Event
        val analytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString("message", "Firebase Integration complete")
        analytics.logEvent("InitScreen", bundle)

        binding.etSearch.addTextChangedListener { text ->
            sharedViewModel.setSearchQuery(text.toString())
        }
        setupNavigationDrawer()
    }

    private fun setupNavigationDrawer() {
        binding.ivUser.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }

        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.inbox_item -> {
                    // Acción para Inbox
                }
                R.id.outbox_item -> {
                    // Acción para Outbox
                }
                R.id.favourites_item -> {
                    // Acción para Favoritos
                }
                R.id.label_one -> {
                    // Acción para Label One
                }
                R.id.label_two -> {
                    // Acción para Label Two
                }
            }
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }
}