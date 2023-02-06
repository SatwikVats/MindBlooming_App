package com.example.yourfitnessidol.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yourfitnessidol.R
import com.example.yourfitnessidol.adapter.CCRecyclerAdapter
import com.example.yourfitnessidol.model.food

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CalorieContentFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CalorieContentFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var recycler_calorie_content: RecyclerView



    lateinit var layoutManager: RecyclerView.LayoutManager



    val foodList= arrayListOf("Hamburger","French fries","Medium pizza","Choco shake","Maggi","Dairy Milk","Lotte chocopie","Oreo biscuit",
        "Noodles","Ice-cream","Coke")



    val foodInfoList= arrayListOf<food>(
        food("Hamburger","Quantity:100 grams ","Calories:270 ","Fats:8g   ","Source:McDonald's     ",R.drawable.burger),
        food("French fries","Quantity:100 grams ","Calories:323 ","Fats:15g  ","Source:Burger King    ",R.drawable.frenchfries),
        food("Medium pizza","Quantity:1 slice   ","Calories:230 ","Fats: --  ","Source:Dominoes       ",R.drawable.pizza),
        food("Choco shake","Quantity:100 mL    ","Calories:101 ","Fats: --  ","Source:Cafe Coffee Day",R.drawable.milkshake),
        food("Maggi","Quantity:1 pack    ","Calories:310 ","Fats:12g  ","Source:Nestle         ",R.drawable.maggi),
        food("Dairy Milk","Quantity:200g block","Calories:1000","Fats:60g  ","Source:Cadbury        ",R.drawable.dairymilk),
        food("Lotte chocopie","Quantity:1 pack    ","Calories:125 ","Fats:6g   ","Source:Lotte          ",R.drawable.chocopie),
        food("Oreo biscuit","Quantity:1 biscuit ","Calories:40  ","Fats:2g   ","Source:Cadbury        ",R.drawable.oreo),
        food("Noodles","Quantity:1 pack    ","Calories:370 ","Fats:15g  ","Source:Top Ramen      ",R.drawable.noodles),
        food("Vanilla scoop","Quantity:1 scoop   ","Calories:150 ","Fats:10g  ","Source:Baskin-Robbins ",R.drawable.ice_cream),
        food("Coke","Quantity:1 can     ","Calories:140 ","Fats:0g   ","Source:Coca-cola      ",R.drawable.coke)



    )

   lateinit var recyclerAdapter: CCRecyclerAdapter

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
        val view= inflater.inflate(R.layout.fragment_calorie_content, container, false)

        recycler_calorie_content= view.findViewById(R.id.recycler_calorie_content)

        layoutManager=LinearLayoutManager(activity)

        recyclerAdapter= CCRecyclerAdapter(activity as Context ,foodInfoList  )

        recycler_calorie_content.adapter = recyclerAdapter

        recycler_calorie_content.layoutManager= layoutManager


        recycler_calorie_content.addItemDecoration(DividerItemDecoration
            (recycler_calorie_content.context,(layoutManager as LinearLayoutManager).orientation))









                return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CalorieContentFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CalorieContentFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}