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

class MainActivity : AppCompatActivity() {



    private lateinit var binding: ActivityMainBinding
    private var navController: NavController? = null

    private lateinit var guideContainer: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

        //guideContainer = findViewById(R.id.guideContainer)
        binding.includeguia.root.bringToFront()
         guideContainer = binding.includeguia.guideContainer

        guideContainer.setOnClickListener {
            // No hace nada, pero captura el clic para que no llegue a lo que hay debajo
        }

        guideContainer.isClickable = true
        guideContainer.isFocusable = true



        guideContainer.bringToFront()

        //guideContainer.visibility = View.GONE

//        val prefs = getSharedPreferences("guide", MODE_PRIVATE)
//        val shown = prefs.getBoolean("shown", false)

     //   if (!shown) {
           showGuide(1)
      //  }






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

        if (step <= 4) {
            binding.navView.alpha = 0.5f // Indicador visual de bloqueo

            // Esto desactiva la capacidad de hacer clic en cada botón individualmente
            for (i in 0 until binding.navView.menu.size()) {
                binding.navView.menu.getItem(i).isEnabled = false
            }

            // Opcional: Ocultar la Toolbar si lo prefieres
            supportActionBar?.hide()
        } else {
            // Restaurar en los pasos finales
            binding.navView.alpha = 1.0f
            for (i in 0 until binding.navView.menu.size()) {
                binding.navView.menu.getItem(i).isEnabled = true
            }
            supportActionBar?.show()
        }



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

        val nextBtn = guideView.findViewById<View>(R.id.btnNext) ?: guideView.findViewById<View>(R.id.btnStart)

        nextBtn?.setOnClickListener {
            playClickSound()
            if (step < 6) {
                when (step) {
                    1 -> navController?.navigate(R.id.navigation_characters)
                    2 -> navController?.navigate(R.id.navigation_worlds)
                    3 -> navController?.navigate(R.id.navigation_collectibles)
                }

                guideContainer.removeAllViews()

                showGuide(step + 1)
            } else {


                finishGuide()
            }
        }

        guideView.findViewById<View>(R.id.btnFinGuia)?.setOnClickListener {

            finishGuide()
        }

        guideView.startAnimation(
            AnimationUtils.loadAnimation(this, R.anim.scale_in)
        )


        if (step == 1 ||step == 2 || step == 3 || step == 4) {
            val circle = guideView.findViewById<View>(R.id.focus_circle)
            val focusAnim = AnimationUtils.loadAnimation(this, R.anim.focus_in)
            circle?.startAnimation(focusAnim)

            // El texto también tiene una animación suave
            val tv = guideView.findViewById<View>(R.id.tvGuide)
            val bocadillo = guideView.findViewById<View>(R.id.bocadillo)
            val finguia = guideView.findViewById<View>(R.id.btnFinGuia)

finguia?.startAnimation(AnimationUtils.loadAnimation(this, R.anim.alpha))



            tv?.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in))

            bocadillo?.startAnimation(AnimationUtils.loadAnimation(this, R.anim.jump))





            val botonAnimado = guideView.findViewById<View>(R.id.btnNext)
            botonAnimado?.startAnimation(AnimationUtils.loadAnimation(this, R.anim.jump))

            val botonStartAnimado = guideView.findViewById<View>(R.id.btnStart)
            botonStartAnimado?.startAnimation(AnimationUtils.loadAnimation(this, R.anim.jump))

            val txtAnim = guideView.findViewById<View>(R.id.tvWelcome)
            txtAnim?.startAnimation(AnimationUtils.loadAnimation(this, R.anim.jump))

            val logoAnimado = guideView.findViewById<View>(R.id.ivLogo)
            logoAnimado?.startAnimation(AnimationUtils.loadAnimation(this, R.anim.giro))


        }
        if (step == 5 ||step == 6) {

            supportActionBar?.show()
            binding.navView.visibility = View.VISIBLE
            binding.navView.isEnabled = true
        }



        }

    private fun finishGuide() {
        guideContainer.removeAllViews()
        guideContainer.visibility = View.GONE

        // Devolvemos la UI a la normalidad
        supportActionBar?.show()
        binding.navView.visibility = View.VISIBLE
        binding.navView.isEnabled = true

        for (i in 0 until binding.navView.menu.size()) {
            binding.navView.menu.getItem(i).isEnabled = true
        }

        // Guardamos en preferencias
        val prefs = getSharedPreferences("guide", MODE_PRIVATE)
        prefs.edit().putBoolean("shown", true).apply()
    }

    private fun playClickSound() {
        try {
            val mediaPlayer = android.media.MediaPlayer.create(this, R.raw.spyro_click)
            mediaPlayer.setOnCompletionListener { it.release() }
            mediaPlayer.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun playSecretVideo() {
        // 1. Crear el VideoView dinámicamente para que ocupe toda la pantalla
        val videoView = android.widget.VideoView(this)
        val layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )
        videoView.layoutParams = layoutParams

        // 2. Añadirlo al guideContainer (que ya sabemos que está por encima de todo)
        guideContainer.removeAllViews()
        guideContainer.visibility = View.VISIBLE
        guideContainer.addView(videoView)
        guideContainer.elevation = 200f // Máxima prioridad visual

        // 3. Configurar la ruta del video
        val videoPath = "android.resource://" + packageName + "/" + R.raw.videoeasternegg
        videoView.setVideoPath(videoPath)

        // 4. Qué hacer cuando termine el video
        videoView.setOnCompletionListener {
            guideContainer.removeAllViews()
            guideContainer.visibility = View.GONE

            // Redirigir a la pestaña de mundos (por si acaso se movió)
            navController?.navigate(R.id.navigation_worlds)
        }

        // 5. Iniciar la reproducción
        videoView.start()
    }


}


