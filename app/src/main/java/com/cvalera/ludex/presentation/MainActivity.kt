package com.cvalera.ludex.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.cvalera.ludex.R
import com.cvalera.ludex.databinding.ActivityMainBinding
import com.cvalera.ludex.presentation.auth.intro.IntroductionActivity
import com.cvalera.ludex.presentation.detail.OnDetailFragmentInteractionListener
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnDetailFragmentInteractionListener {

    companion object {
        fun create(context: Context): Intent =
            Intent(context, MainActivity::class.java)
    }

    private lateinit var binding: ActivityMainBinding
    private val sharedViewModel: SharedViewModel by viewModels()
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        val bottomNavigationView = binding.navMenu
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.frameContainer) as NavHostFragment
        bottomNavigationView.setupWithNavController(navHostFragment.navController)

        // Analytics Event
        val analytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString("message", "Firebase Integration complete")
        analytics.logEvent("InitScreen", bundle)
        // ---------------------------------

        binding.etSearch.addTextChangedListener { text ->
            sharedViewModel.setSearchQuery(text.toString())
        }
        setupNavigationDrawer()
        updateNavHeader()
    }

    private fun setupNavigationDrawer() {
        binding.ivUser.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.END)
        }

        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.buttonLogOut -> {
                    FirebaseAuth.getInstance().signOut()
                    goToIntroActivity()
                }
            }
            binding.drawerLayout.closeDrawer(GravityCompat.END)
            true
        }
    }

    override fun onDetailFragmentOpened() {
        binding.navMenu.visibility = View.GONE
        binding.etSearch.visibility = View.GONE
        binding.ivUser.visibility = View.GONE
    }

    override fun onDetailFragmentClosed() {
        binding.navMenu.visibility = View.VISIBLE
        binding.etSearch.visibility = View.VISIBLE
        binding.ivUser.visibility = View.VISIBLE
    }

    private fun goToIntroActivity() {
        val intent = Intent(this, IntroductionActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun updateNavHeader() {
        val headerView = binding.navigationView.getHeaderView(0)
        val tvUserName = headerView.findViewById<TextView>(R.id.tvUserName)
        val tvUserEmail = headerView.findViewById<TextView>(R.id.tvUserEmail)
        val imageViewUser = headerView.findViewById<ImageView>(R.id.imageViewUser)

        val currentUser: FirebaseUser? = auth.currentUser
        currentUser?.let {
            tvUserName.text = it.displayName ?: "Nombre no disponible"
            tvUserEmail.text = it.email ?: "Email no disponible"

            // Cargar la foto de perfil usando Glide
            it.photoUrl?.let { uri ->
                Glide.with(this)
                    .load(uri)
                    .placeholder(R.drawable.icon_user_placeholder) // Imagen de placeholder
                    .circleCrop()
                    .into(imageViewUser)

                // Cargar la misma foto en ivUser
                Glide.with(this)
                    .load(uri)
                    .placeholder(R.drawable.baseline_account_circle) // Imagen de placeholder
                    .circleCrop()
                    .into(binding.ivUser)
            }
        }
    }
}