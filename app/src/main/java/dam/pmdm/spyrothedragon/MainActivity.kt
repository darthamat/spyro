package dam.pmdm.spyrothedragon

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import dam.pmdm.spyrothedragon.databinding.ActivityMainBinding
import androidx.core.content.edit

class MainActivity : AppCompatActivity() {



    private lateinit var binding: ActivityMainBinding
    private var navController: NavController? = null

    private lateinit var guideContainer: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        guideContainer = findViewById(R.id.guideContainer)

        guideContainer.bringToFront()
        guideContainer.requestLayout()
        guideContainer.invalidate()

        val prefs = getSharedPreferences("guide", MODE_PRIVATE)
        val shown = prefs.getBoolean("shown", false)

        if (!shown) {
           showGuide(1)
        }



        val navHostFragment: Fragment? =
            supportFragmentManager.findFragmentById(R.id.navHostFragment)

        navHostFragment?.let {
            navController = NavHostFragment.findNavController(it)
            NavigationUI.setupWithNavController(binding.navView, navController!!)
            NavigationUI.setupActionBarWithNavController(this, navController!!)
        }

        binding.navView.setOnItemSelectedListener { menuItem ->
            selectedBottomMenu(menuItem)
        }

        navController?.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_characters,
                R.id.navigation_worlds,
                R.id.navigation_collectibles -> {
                    // En las pantallas de los tabs no mostramos la flecha atrás
                    supportActionBar?.setDisplayHomeAsUpEnabled(false)
                }
                else -> {
                    // En el resto de pantallas sí
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                }
            }
        }


    }

    private fun selectedBottomMenu(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.nav_characters ->
                navController?.navigate(R.id.navigation_characters)
            R.id.nav_worlds ->
                navController?.navigate(R.id.navigation_worlds)
            else ->
                navController?.navigate(R.id.navigation_collectibles)
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.about_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.action_info) {
            showInfoDialog()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    private fun showInfoDialog() {
        AlertDialog.Builder(this)
            .setTitle(R.string.title_about)
            .setMessage(R.string.text_about)
            .setPositiveButton(R.string.accept, null)
            .show()
    }

    fun showGuide(step: Int) {
        guideContainer.removeAllViews()
        guideContainer.visibility = View.VISIBLE

        val layout = when(step) {
            1 -> R.layout.guia_1_bienvenida
            2 -> R.layout.guia_personajes
            3 -> R.layout.guia_3_mundos
            4 -> R.layout.guia_4_coleccionables
            5 -> R.layout.guia_5_info
            else -> R.layout.guia_6_final
        }

        val guideView = layoutInflater.inflate(layout, guideContainer, false)
        guideContainer.addView(guideView)

        guideView.startAnimation(
            AnimationUtils.loadAnimation(this, R.anim.scale_in)
        )
    }


}


