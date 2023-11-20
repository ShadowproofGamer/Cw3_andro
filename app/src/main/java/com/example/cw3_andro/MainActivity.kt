package com.example.cw3_andro

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toolbar
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
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var navHostFragment:NavHostFragment
    lateinit var navController: NavController
    lateinit var bottomNav: BottomNavigationView
    lateinit var toolbar1: androidx.appcompat.widget.Toolbar

    //RF vars
    private var myAM: ActionMode? = null
    lateinit var conColor1: TextView
    lateinit var conPref1: TextView
    lateinit var conBirth: TextView
    lateinit var conBirthDate: TextView




    fun applyTheme() {
        var data: SharedPreferences = getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        var themeNum = data.getInt("theme_color", 0)
        when (themeNum) {
            1 -> setTheme(R.style.AppThemeRed)
            2 -> setTheme(R.style.AppThemeGreen)
            3 -> setTheme(R.style.AppThemeGrey)
            else -> setTheme(R.style.Theme_Cw3_andro)
        }
        //theme.applyStyle(R.style.Theme_L2_andro, true)
    }

    fun setPrefs(themeNum: Int) {
        val data: SharedPreferences = getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        val editor = data.edit()
        editor.putInt("theme_color", themeNum)
        editor.apply()
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.app_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //Toast.makeText(this, "Clicked!", Toast.LENGTH_SHORT).show()
        return when (item.itemId) {
            R.id.mi0 -> {
                setPrefs(0)
                recreate()
                true
            }
            R.id.miRes -> {
                /*
                tsize = 14F
                tface = Typeface.NORMAL
                checkedBold = false
                checkedItalic = false
                recreate()

                 */
                true
            }
            R.id.mi1 -> {
                setPrefs(1)
                recreate()
                true
            }
            R.id.mi2 -> {
                setPrefs(2)
                recreate()
                true
            }
            R.id.mi3 -> {
                setPrefs(3)
                recreate()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    //for right activity
    fun applyBGColor() {
        val data: SharedPreferences = getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        var colorNum = data.getInt("bg_color", Color.WHITE)
        conColor1.setBackgroundColor(colorNum)
    }

    fun setPrefColor(colorNum: Int) {
        val data: SharedPreferences = getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        val editor = data.edit()
        editor.putInt("bg_color", colorNum)
        editor.apply()
    }

    fun setPrefDate(dateText: String) {
        val data: SharedPreferences = getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        val editor = data.edit()
        editor.putString("con_date", dateText)
        editor.apply()
    }
    fun applyDateText() {
        val data: SharedPreferences = getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        var date = data.getString("con_date", null)
        conBirthDate.text = date
    }

    val myAMCallback: ActionMode.Callback = object: ActionMode.Callback {
        override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            menuInflater.inflate(R.menu.cam_view, menu)

            return true
        }

        override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            //nic nie robimy
            return true
        }

        override fun onDestroyActionMode(mode: ActionMode?) {
            myAM = null

        }

        override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
            return when(item.itemId){
                R.id.con_act1 -> {
                    //val color = resources.getColor(R.color.md_theme_red_light_onPrimary, theme)
                    setPrefColor(Color.RED)
                    conColor1.setBackgroundColor(Color.RED)
                    mode.finish()
                    true
                }
                R.id.con_act2 -> {
                    //val color = resources.getColor(R.color.md_theme_green_light_onPrimary, theme)
                    setPrefColor(Color.GREEN)
                    conColor1.setBackgroundColor(Color.GREEN)
                    mode.finish()
                    true
                }
                R.id.con_act3 -> {
                    //val color = resources.getColor(R.color.md_theme_grey_light_onPrimary, theme)
                    setPrefColor(Color.parseColor("#745B00"))
                    conColor1.setBackgroundColor(Color.parseColor("#745B00"))
                    mode.finish()
                    true
                }
                else -> {
                    mode.finish()
                    false
                }
            }
        }
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        applyTheme()

        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        navHostFragment = supportFragmentManager.findFragmentById(R.id.sfcontainer) as NavHostFragment
        navController =navHostFragment.navController
        bottomNav =binding.bottomMenu

/*
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

 */


        toolbar1 = binding.toolbar1
        setSupportActionBar(toolbar1)



        val appBarConfig : AppBarConfiguration =
            AppBarConfiguration(setOf(R.id.left_frag,R.id.center_frag,R.id.right_frag))
        setupActionBarWithNavController(navController,appBarConfig)
        bottomNav.setupWithNavController(navController)



        //for RF *ERRORS*
        /*
        conColor1 = findViewById(R.id.con_color1)
        conPref1 = findViewById(R.id.con_pref1)

        conColor1.setOnLongClickListener(View.OnLongClickListener { view ->
            if (myAM != null) {
                return@OnLongClickListener false
            }
            myAM = startActionMode(myAMCallback)
            view.isSelected = true
            true
        })

        applyBGColor()

        conBirth = findViewById(R.id.con_br)
        conBirthDate = findViewById(R.id.con_br_date)
        applyDateText()
        conBirthDate.setOnClickListener { _ ->
            val cal = Calendar.getInstance()
            val year = cal.get(Calendar.YEAR)
            val month = cal.get(Calendar.MONTH)
            val day = cal.get(Calendar.DAY_OF_MONTH)
            val dateDialog = DatePickerDialog(
                this,
                {view, year, monthOfYear, dayOfMonth ->
                    val currCal = Calendar.getInstance()
                    currCal.set(year, monthOfYear, dayOfMonth)
                    var tempDate = (dayOfMonth.toString() + "-" + (monthOfYear+1) + "-" + year)
                    conBirthDate.text = tempDate
                    setPrefDate(tempDate)
                },
                year,
                month,
                day
            ).show()
        }

        val backButton:Button? = findViewById(R.id.back_button)
        backButton!!.setOnClickListener { _ ->
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder
                .setTitle("Go Back Dialog")
                .setMessage("Are you sure at 100% ?")
                //.setSingleChoiceItems()
                .setPositiveButton("Accept") { dialog, which ->
                    onBackPressed()
                }.setNegativeButton("Cancel") { dialog, which ->
                    dialog.cancel()
                }.create().show()
        }

         */

    }
}