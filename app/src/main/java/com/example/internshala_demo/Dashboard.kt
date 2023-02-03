package com.example.internshala_demo

import android.content.Context
import android.database.Cursor
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.internshala_demo.databinding.FragmentDashboardBinding


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Dashboard.newInstance] factory method to
 * create an instance of this fragment.
 */
class Dashboard : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)

        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        name = createList()

        val mListView = binding?.list
        arrayAdapter =
            ArrayAdapter(this.requireActivity(), android.R.layout.simple_list_item_1, name)
        mListView?.adapter = arrayAdapter

        binding?.apply?.setOnClickListener {

            view.findNavController()?.navigate(R.id.action_dashboard_to_availableWorkshops)

        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(
            true // default to enabled
        ) {
            override fun handleOnBackPressed() {
                view?.findNavController()?.navigate(R.id.action_dashboard_to_availableWorkshops)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            this,  // LifecycleOwner
            callback
        )
    }


    private fun createList(): ArrayList<String> {
        try {
            val db: MyDBHelper = MyDBHelper(context,null)
            var c: Cursor? = null
            c = db.getName()
            System.out.println(c)
            c!!.moveToFirst()
            while (!c.isAfterLast()) {
                val str_id: String = c.getString(0)
                val str_msg: String = c.getString(1)
                val str_read: String = c.getString(2)
                Log.e("value", str_id + str_msg + str_read)
//                val hm = HashMap<String, String>()
//                hm["name"] = str_msg
//                hm["applied"] = str_read
                if(str_read=="YES"){
                    name.add(str_msg)
                }
                c.moveToNext()
            }
            c.close()
            db.close()
        } catch (e: Exception) {
            Log.e("this not work", "" + e)
        }

        return name
    }

    var name = ArrayList<String>()
    lateinit var arrayAdapter: ArrayAdapter<*>
    private var binding: FragmentDashboardBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding!!.root

    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DashboardFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Dashboard().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}