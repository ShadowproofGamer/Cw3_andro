package com.example.cw3_andro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentController
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.cw3_andro.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var navHostFragment:NavHostFragment
    lateinit var navController: NavController
    lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        navHostFragment = supportFragmentManager.findFragmentById(R.id.sfcontainer) as NavHostFragment
        navController =navHostFragment.navController
        bottomNav =binding.bottomMenu


        bottomNav.setOnItemSelectedListener{
            when(it.itemId){
                R.id.left_frag -> {
                    navController.navigate(R.id.action_to_left_fragment)
                    true
                }
                R.id.center_frag -> {
                    navController.navigate(R.id.action_to_center_fragment)
                    true
                }
                R.id.right_frag -> {
                    navController.navigate(R.id.action_to_right_fragment)
                    true
                }
                else -> false
            }
        }


        /*
        val myToolbar = findViewById(R.id.)
        val appBarConfig : AppBarConfiguration =
            AppBarConfiguration(setOf(R.id.left_frag,R.id.center_frag,R.id.right_frag))
        setupActionBarWithNavController(navController,appBarConfig)
        bottomNav.setupWithNavController(navController)

         */




    }
}