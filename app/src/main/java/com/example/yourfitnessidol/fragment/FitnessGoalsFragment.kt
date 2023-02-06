package com.example.yourfitnessidol.fragment

import android.app.AlertDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yourfitnessidol.R
import com.example.yourfitnessidol.adapter.CCRecyclerAdapter
import com.example.yourfitnessidol.adapter.fg_recycler_adapter
import com.example.yourfitnessidol.model.food
import com.example.yourfitnessidol.model.goaldetail
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_login2.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

/*import com.example.yourfitnessidol.adapter.goalsrecycleradapter
import kotlinx.android.synthetic.main.fragment_fitness_goals*/

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FitnessGoalsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FitnessGoalsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var recycler_fitness_goals: RecyclerView


    lateinit var layoutManagerf: RecyclerView.LayoutManager

    //val goal_list= arrayListOf("Hamburger","French fries","Medium pizza","Choco shake","Maggi","Dairy Milk","Lotte chocopie","Oreo biscuit",
    //  "Noodles","Ice-cream","Coke")


    val goalInfoList = arrayListOf<goaldetail>(


    )


    lateinit var recyclerAdapter: fg_recycler_adapter


    lateinit var exercise: EditText
    lateinit var repetitions: EditText
    lateinit var frequency: EditText

    /*lateinit var goalsrecycler: ListView*/

    /*var count:Int=1*/

    lateinit var auth: FirebaseAuth
    lateinit var fstore: FirebaseFirestore

    private lateinit var userid: String


    lateinit var addbutton: Button

    // lateinit var removebutton: Button

    lateinit var clearbutton: Button

    lateinit var timepicker_for_exercise: Button


    //val goallist = arrayListOf<String>("pushups")


    //var adapter = ArrayAdapter<String>(this as Context,R.layout.goal_list_single_row, goallist)


    /*(this, R.layout.goal_list_single_row, goallist)*/


    /* lateinit var  goallist: MutableList<String>*/


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
        val view = inflater.inflate(R.layout.fragment_fitness_goals, container, false)

        auth = FirebaseAuth.getInstance()
        fstore = FirebaseFirestore.getInstance()

        val user = auth.currentUser
        userid = user!!.uid



        exercise = view.findViewById(R.id.exercise)
        repetitions = view.findViewById(R.id.repetitions)
        frequency = view.findViewById(R.id.frequency)

        timepicker_for_exercise = view.findViewById(R.id.timepicker_for_exercise)





        addbutton = view.findViewById(R.id.add_button)

        // removebutton = view.findViewById(R.id.remove_button)

        clearbutton = view.findViewById(R.id.clear_button)







        recycler_fitness_goals = view.findViewById(R.id.goalsrecycler)

        layoutManagerf = LinearLayoutManager(activity)

        recyclerAdapter = fg_recycler_adapter(activity as Context, goalInfoList)

        recycler_fitness_goals.adapter = recyclerAdapter

        recycler_fitness_goals.layoutManager = layoutManagerf


        recycler_fitness_goals.addItemDecoration(
            DividerItemDecoration
                (
                recycler_fitness_goals.context,
                (layoutManagerf as LinearLayoutManager).orientation
            )
        )


        /*fstore.collection("number_of_goals_count").get().addOnSuccessListener { result ->
            for (document in result) {
                if (document.id == userid) {

                    val count_temp = document.data["Number of goals"].toString().toInt()

                }
            }

           val getcount:DocumentReference= fstore.collection("number_of_goals_count").document("${userid}_count")
            getcount.dat
*/

















        fstore.collection("to_do_list").get().addOnSuccessListener { result ->
            for (document in result) {

                if (document.id.contains(userid)) {

                    goalInfoList.add(
                        goaldetail(
                            document.data["repetitions"].toString(),
                            document.data["name"].toString(),
                            document.data["frequency"].toString(),
                            document.data["exercise_timing"].toString()

                        )
                    )
                    recyclerAdapter.notifyItemInserted(goalInfoList.size)
                }
            }
        }


        timepicker_for_exercise.setOnClickListener() {


            val now = Calendar.getInstance()
            val timeform = SimpleDateFormat("hh:mm a", Locale.US)

            val timepicker = TimePickerDialog(
                this.context, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                    val selectedtime = Calendar.getInstance()
                    selectedtime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    selectedtime.set(Calendar.MINUTE, minute)

                    val time_for_exercise = timeform.format(selectedtime.time)
                    timepicker_for_exercise.text = time_for_exercise


                },
                now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), false
            )
            timepicker.show()


        }










        addbutton.setOnClickListener()
        {

            if ((exercise.toString().isNotEmpty()) && (repetitions.toString().isNotEmpty()) && (frequency.toString().isNotEmpty())) {

                fstore.collection("to_do_list").get().addOnSuccessListener { result ->
                    for (document in result) {
                        if (document.id == "${userid}_${repetitions.text}_${exercise.text}_${frequency.text}_${timepicker_for_exercise.text}") {

                            exercise.error = "Please avoid duplicate entries"
                            exercise.requestFocus()


                        }
                    }
                }


                val docref: DocumentReference =
                    fstore.collection("to_do_list")
                        .document("${userid}_${repetitions.text}_${exercise.text}_${frequency.text}_${timepicker_for_exercise.text}")
                var user_goal = HashMap<String, Any>()
                user_goal.put("repetitions", repetitions.text.toString())
                user_goal.put("name", exercise.text.toString())
                user_goal.put("frequency", frequency.text.toString())
                user_goal.put("exercise_timing", timepicker_for_exercise.text.toString())
                //user_biometrics.put("bmi", bmi)//
                docref.set(user_goal)

                fstore.collection("to_do_list").get().addOnSuccessListener { result ->
                    for (document in result) {
                        if (document.id == "${userid}_${repetitions.text}_${exercise.text}_${frequency.text}_${timepicker_for_exercise.text}") {
                            goalInfoList.add(
                                goaldetail(
                                    document.data["repetitions"].toString(),
                                    document.data["name"].toString(),
                                    document.data["frequency"].toString(),
                                    document.data["exercise_timing"].toString()
                                )
                            )
                            recyclerAdapter.notifyItemInserted(goalInfoList.size)
                            recyclerAdapter.notifyDataSetChanged()
                        }


                    }
                }






                repetitions.text.clear()
                exercise.text.clear()
                frequency.text.clear()
                timepicker_for_exercise.text = "Select Time"
                Toast.makeText(this.context,"Please refresh/reload this page to update the list",Toast.LENGTH_LONG).show()


            }

            else
            {

                Toast.makeText(this.context, "Please enter valid workout/exercise pose details", Toast.LENGTH_SHORT).show()


            }

        }









       /* removebutton.setOnClickListener(){

            Toast.makeText(activity as Context,"Select the items",Toast.LENGTH_SHORT).show()



        }*/



        clearbutton.setOnClickListener() {

            val dialog=AlertDialog.Builder(activity as Context)
            dialog.setMessage("Are you sure that you want to clear all your current goals?")
            dialog.setPositiveButton("Ok"){
                text,listener -> fstore.collection("to_do_list").get().addOnSuccessListener { result ->
                for (document in result) {
                    if (document.id.contains(userid)){
                        fstore.collection("to_do_list").document(document.id).delete()
                        recyclerAdapter.notifyDataSetChanged()
                    }
                }

            }
                Toast.makeText(this.context,"Please refresh/reload this page to update the list",Toast.LENGTH_LONG).show()
            }
            dialog.setNegativeButton("Cancel"){
                    text,listener -> return@setNegativeButton
            }
            dialog.create()
            dialog.show()


        }

                return view
            }





            /*fun addgoal(
                arraylist: MutableList<String>, content: String
            ) {
                arraylist.add((arraylist.size) - 1, content)
            }*/

            companion object {
            /**
             * Use this factory method to create a new instance of
             * this fragment using the provided parameters.
             *
             * @param param1 Parameter 1.
             * @param param2 Parameter 2.
             * @return A new instance of fragment FitnessGoalsFragment.
             */
            // TODO: Rename and change types and number of parameters
            @JvmStatic
            fun newInstance(param1: String, param2: String) =
                FitnessGoalsFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
        }
        }



