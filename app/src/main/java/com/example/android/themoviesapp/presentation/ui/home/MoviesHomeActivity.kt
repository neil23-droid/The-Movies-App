package com.example.android.themoviesapp.presentation.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.android.themoviesapp.R
import com.example.android.themoviesapp.databinding.ActivityMoviesHomeBinding
import com.example.android.themoviesapp.presentation.models.DrawerItem
import com.example.android.themoviesapp.presentation.ui.drawer.NavigationDrawerFragment
import com.example.android.themoviesapp.presentation.ui.movie_details.MovieDetailsFragmentArgs
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MoviesHomeActivity : AppCompatActivity(), NavigationDrawerFragment.FragmentNavigationDrawerListener {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMoviesHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMoviesHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // 1. Manually set the status bar color (Edge-to-edge makes it transparent by default)
       /* ViewCompat.setOnApplyWindowInsetsListener(binding.toolbar) { view, insets ->
            val statusBarHeight = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top
            view.setPadding(0, statusBarHeight, 0, 0)
            insets
        }*/
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            binding.root.setPadding(
                systemBars.left,   // handles side nav bar in landscape
                0,
                systemBars.right,  // handles side nav bar in landscape
                systemBars.bottom
            )
            binding.toolbar.setPadding(
                0,
                systemBars.top,
                0,
                0
            )
            insets
        }

        initializeDrawerLayout() //  Set up drawer + listeners + lock mode
        initializeNavHost()          //  Get NavController from NavHostFragment
        initializeNavigationListener() //  Listen for destination changes to update toolbar
        initializeToolBar()            //  Set up click that opens/closes the drawer
        initializeOnBackPressedDispatcher() // 3. Set up back press that checks drawer state



    }

    private fun initializeToolBar(){
        // 3. Handle the Click manually since the drawer is locked
        binding.toolbar.setNavigationOnClickListener {
            val currentDestination = navController.currentDestination?.id
            if (currentDestination != navController.graph.startDestinationId) {
                // On Details: Go Back
                navController.navigateUp()
            } else {
                // On Home/Other: Toggle Drawer
                if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                } else {
                    binding.drawerLayout.openDrawer(GravityCompat.START)
                }
            }
        }

    }

    private fun initializeDrawerLayout(){
        // Disable swiping from the edge to open
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.nav_open,
            R.string.nav_close
        )
        toggle.isDrawerIndicatorEnabled = false
        // 2. Add the toggle as a listener so it knows when to start morphing
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        // 4. Re-lock it when it finishes closing
        binding.drawerLayout.addDrawerListener(object : DrawerLayout.SimpleDrawerListener() {
            override fun onDrawerClosed(drawerView: View) {
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            }
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                // slideOffset: 0.0 (closed) to 1.0 (open)
                if (slideOffset > 0.5f) {
                    binding.toolbar.setNavigationIcon(R.drawable.ic_back_button)
                } else {
                    binding.toolbar.setNavigationIcon(R.drawable.ic_hamburger_icon)
                }
            }
        })

    }

    private fun initializeOnBackPressedDispatcher(){
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                } else {
                    isEnabled = false // Disable this callback and let the system handle the back press
                    onBackPressedDispatcher.onBackPressed()
                }
            }
        })
    }

    private fun initializeNavHost(){
        // Get the NavHostFragment directly first
        val navHostFragment = supportFragmentManager
            .findFragmentById(binding.homeMoviesNavHostFragment.id) as NavHostFragment
        // Then get NavController from it
        navController = navHostFragment.navController


    }

    private fun initializeNavigationListener(){
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.moviesListingFragment -> { // Replace with your actual home fragment ID
                    // 1. Set Default App Title
                    binding.toolbarTitle.text = destination.label

                    // 2. Set Hamburger Icon
                    binding.toolbar.setNavigationIcon(R.drawable.ic_hamburger_icon)
                }
                R.id.movieDetailsFragment -> {
                    // Use the generated Args class to parse the bundle safely
                    val args = MovieDetailsFragmentArgs.fromBundle(arguments ?: Bundle.EMPTY)
                    binding.toolbarTitle.text = args.movieName
                    binding.toolbar.setNavigationIcon(R.drawable.ic_back_button)
                }

                R.id.locationDetailsFragment ->{
                    binding.toolbarTitle.text = destination.label
                    binding.toolbar.setNavigationIcon(R.drawable.ic_back_button)
                }

            }
        }
    }

    override fun onDrawerMenuClicked(item: DrawerItem.MenuItem) {
        handleNavigation(item)
    }

    private fun handleNavigation(item: DrawerItem.MenuItem) {
        when (item.title) {
            "Booked Ticket History" -> {
           /*     binding.drawerLayout.addDrawerListener(object : DrawerLayout.SimpleDrawerListener() {
                    override fun onDrawerClosed(drawerView: View) {
                        binding.drawerLayout.removeDrawerListener(this)
                        val intent =
                            Intent(this@MoviesHomeActivity, BookedTicketHistoryActivity::class.java)
                        startActivity(intent)
                    }
                })*/
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            }

            else -> {}
        }

        // IMPORTANT: Close the drawer after navigation
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }
    }

}