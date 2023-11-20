package com.example.cw3_andro

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentCenter.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentCenter : Fragment() , RadioGroup.OnCheckedChangeListener{
    lateinit var frag1: Fragment1
    lateinit var frag2: Fragment2
    lateinit var myTrans: FragmentTransaction

    private val TAG_F1 = "Fragment1"
    private val TAG_F2 = "Fragment2"


    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        if (savedInstanceState==null){
            frag1=Fragment1.newInstance("null", "null")
            frag2=Fragment2.newInstance("null", "null")
            myTrans=childFragmentManager.beginTransaction()
            myTrans!!.add(R.id.nested_container, frag1, this.TAG_F1)
            myTrans!!.detach(frag1!!)
            myTrans!!.add(R.id.nested_container, frag2, this.TAG_F2)
            myTrans!!.detach(frag2!!)
            myTrans!!.commit()
        }
        //remember to check params

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_center, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentCenter.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentCenter().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        myTrans = childFragmentManager.beginTransaction()
        when (checkedId){
            R.id.radio1 -> {
                myTrans.detach(frag2)
                myTrans.attach(frag1)
            }
            R.id.radio2 -> {
                myTrans.detach(frag1)
                myTrans.attach(frag2)
            }
        }
        //crash przy drugim commicie
        //myTrans.addToBackStack(null)
        myTrans.commit()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity().findViewById(R.id.RadioGroup1) as RadioGroup).setOnCheckedChangeListener(this)
        if (savedInstanceState != null) {
            frag1 = childFragmentManager.findFragmentByTag(this.TAG_F1) as Fragment1
            frag2 = childFragmentManager.findFragmentByTag(this.TAG_F2) as Fragment2
        }
        childFragmentManager.setFragmentResultListener("msgfromchild", viewLifecycleOwner)
        { key, bundle ->
            val result = bundle.getString("msg1")
            (requireActivity().findViewById<View>(R.id.result_txt) as TextView).setText(result)
        }
    }
}