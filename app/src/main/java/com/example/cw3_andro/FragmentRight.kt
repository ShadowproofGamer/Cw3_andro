package com.example.cw3_andro

import android.os.Bundle
import android.view.ActionMode
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

//imports for right
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Typeface
import android.view.ContextMenu
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import android.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.cw3_andro.databinding.FragmentRightBinding
import java.lang.reflect.Array
import java.util.Calendar

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentRight.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentRight : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    //RF vars
    private var myAM: ActionMode? = null
    lateinit var conColor1: TextView
    lateinit var conPref1: TextView
    lateinit var conBirth: TextView
    lateinit var conBirthDate: TextView


    //for right activity
    fun applyBGColor() {
        val data: SharedPreferences = requireActivity().getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        var colorNum = data.getInt("bg_color", Color.WHITE)
        conColor1.setBackgroundColor(colorNum)
    }

    fun setPrefColor(colorNum: Int) {
        val data: SharedPreferences = requireActivity().getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        val editor = data.edit()
        editor.putInt("bg_color", colorNum)
        editor.apply()
    }

    fun setPrefDate(dateText: String) {
        val data: SharedPreferences = requireActivity().getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        val editor = data.edit()
        editor.putString("con_date", dateText)
        editor.apply()
    }
    fun applyDateText() {
        val data: SharedPreferences = requireActivity().getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        var date = data.getString("con_date", null)
        conBirthDate.text = date
    }

    val myAMCallback: ActionMode.Callback = object: ActionMode.Callback {
        override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            requireActivity().menuInflater.inflate(R.menu.cam_view, menu)
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
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_right, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //val binding = FragmentRightBinding.inflate(layoutInflater)
        //requireActivity().setContentView(binding.root)


        conColor1 = (requireActivity().findViewById<View>(R.id.con_color1) as TextView)
        conPref1 = (requireActivity().findViewById<View>(R.id.con_pref1) as TextView)

        conColor1.setOnLongClickListener(View.OnLongClickListener { view ->
            if (myAM != null) {
                return@OnLongClickListener false
            }
            val toolbar = (requireActivity().findViewById<View>(R.id.toolbar1) as androidx.appcompat.widget.Toolbar)
            myAM = toolbar.startActionMode(myAMCallback)
            view.isSelected = true
            true
        })

        applyBGColor()

        conBirth = (requireActivity().findViewById<View>(R.id.con_br) as TextView)
        conBirthDate = (requireActivity().findViewById<View>(R.id.con_br_date) as TextView)
        applyDateText()
        conBirthDate.setOnClickListener { _ ->
            val cal = Calendar.getInstance()
            val year = cal.get(Calendar.YEAR)
            val month = cal.get(Calendar.MONTH)
            val day = cal.get(Calendar.DAY_OF_MONTH)
            val dateDialog = DatePickerDialog(
                requireActivity(),
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

        val backButton: Button = (requireActivity().findViewById<View>(R.id.back_button) as Button)
        backButton.setOnClickListener { _ ->
            val builder: AlertDialog.Builder = AlertDialog.Builder(requireActivity())
            builder
                .setTitle("Go Back Dialog")
                .setMessage("Are you sure at 100% ?")
                //.setSingleChoiceItems()
                .setPositiveButton("Accept") { dialog, which ->
                    requireActivity().onBackPressed()
                }.setNegativeButton("Cancel") { dialog, which ->
                    dialog.cancel()
                }.create().show()
        }


        //mod 1
        val editButton = (requireActivity().findViewById<View>(R.id.editButton) as Button)
        editButton.setOnClickListener { _ ->
            findNavController().navigate(R.id.action_right_frag_to_fragmentExtra)
        }

        //to dodać dane (mod2)
        val titleR = (requireActivity().findViewById<View>(R.id.title1) as TextView)
        parentFragmentManager.setFragmentResultListener("childFragmentData", viewLifecycleOwner){string, bundle ->
            titleR.setText(bundle.getString("title", R.string.pers_data.toString()))
            //requireActivity().recreate()
        }

        //mod3
        setHasOptionsMenu(true)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.right_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.newopt -> {
                Toast.makeText(requireActivity(), "new option clicked!", Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentRight.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentRight().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}