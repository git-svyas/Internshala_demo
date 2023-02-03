package com.example.internshala_demo

import android.content.Context
import android.database.Cursor
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.internshala_demo.databinding.FragmentAvailableWorkshopsBinding



interface CellClickListener {
    fun onCellClickListener(data: String,position:Int)
}

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AvailableWorkshopsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AvailableWorkshops : Fragment() ,CellClickListener{
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var binding: FragmentAvailableWorkshopsBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }


    }

    var name = ArrayList<String>()




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
                name.add(str_msg)
                c.moveToNext()
            }
            c.close()
            db.close()
        } catch (e: Exception) {
            Log.e("this not work", "" + e)
        }

        return name
    }

    override fun onCellClickListener(data: String,position: Int) {

        val sharedPreference = this.requireActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)

        if (sharedPreference.contains("Username") && sharedPreference.contains("Password")) {

            val db: MyDBHelper = MyDBHelper(context,null)
            db.updateTask(data,position)

            view?.findNavController()?.navigate(R.id.action_availableWorkshops_to_dashboard)
            Toast.makeText(activity,"Cell clicked"+data, Toast.LENGTH_SHORT).show()
        }
        else{
            view?.findNavController()?.navigate(R.id.action_availableWorkshops_to_signup)
//            startActivity(Intent(context,Signup::class.java))
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(
            true // default to enabled
        ) {
            override fun handleOnBackPressed() {
//                do nothing
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            this,  // LifecycleOwner
            callback
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        name = createList()
        //        name.add("Default")
        Log.d("ArrayList",name.toString())

        binding?.recyclerview?.layoutManager= LinearLayoutManager(this.context)
        val adapter = CustomAdapter(context,name,this)
        binding?.recyclerview?.adapter = adapter

        Log.d("adapter",adapter.toString())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAvailableWorkshopsBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AvailableWorkshopsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AvailableWorkshops().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}