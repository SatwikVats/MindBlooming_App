package com.example.yourfitnessidol.fragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.yourfitnessidol.R
import com.google.android.material.card.MaterialCardView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_biometrics.*
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BiometricsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BiometricsFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var fstore: FirebaseFirestore
    private lateinit var userID: String
    lateinit var btn_update_biometrics: Button
    //lateinit var btn_et_height: Button//
    lateinit var et_weight: EditText
    lateinit var et_height: EditText
    lateinit var weight: TextView
    lateinit var height: TextView
    lateinit var bmi_display:TextView
    lateinit var bmi_analysis:TextView
    lateinit var bmi_analysis_card:MaterialCardView
    var bmi:Float=0.0f


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

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_biometrics, container, false)

        auth = FirebaseAuth.getInstance()
        fstore= FirebaseFirestore.getInstance()
        val user=auth.currentUser
        userID=user!!.uid

        btn_update_biometrics=view.findViewById(R.id.btn_update_biometrics)
        //btn_et_height=view.findViewById(R.id.btn_et_height)//
        et_weight=view.findViewById(R.id.et_weight)
        et_height=view.findViewById(R.id.et_height)
        weight=view.findViewById(R.id.weight)
        height=view.findViewById(R.id.height)
        bmi_display=view.findViewById(R.id.bmi_display)
        bmi_analysis=view.findViewById(R.id.bmi_analysis)
        bmi_analysis_card=view.findViewById(R.id.bmi_analysis_card)

        btn_update_biometrics.setOnClickListener(){

            if ((et_weight.text.toString().trim().isNotEmpty())&&(et_weight.text.toString().trim().isNotEmpty())) {

                val user_weight = et_weight.text.toString().toDouble()
                val user_height = et_height.text.toString().toDouble()

                val user_bmi=(user_weight/((user_height/100)*(user_height/100)))

                //val dateformat=SimpleDateFormat("dd-MM-YYYY", Locale.US)
                val timeformat=SimpleDateFormat("hh:mm a, EEEE, dd-MM-YYYY", Locale.US)
                val now=Calendar.getInstance()

                //val currentdate:String=dateformat.format(now.date)
                val currenttime:String=timeformat.format(now.time)




                val docref: DocumentReference = fstore.collection("weight_height").document(userID)
                var user_biometrics = HashMap<String, Any>()
                user_biometrics.put("weight", user_weight)
                user_biometrics.put("height", user_height)
                user_biometrics.put("biometrics_last_updated", currenttime)
                //user_biometrics.put("bmi", bmi)//
                docref.set(user_biometrics)

                weight.setText("${user_weight.toString()} kg")
                height.setText("${user_height.toString()} cm")

                val dff = DecimalFormat("#.##")
                dff.roundingMode = RoundingMode.CEILING
                val usbmi=dff.format(user_bmi)

                bmi_display.setText("BMI:${usbmi.toString()}")
                btn_update_biometrics.setText("Update")
                et_weight.text.clear()
                et_height.text.clear()

                if (user_bmi>35.0){
                    val difference=((user_height/100.0)*(user_height/100.0))*(user_bmi-25.0)
                    val dif = DecimalFormat("#.##")
                    dif.roundingMode = RoundingMode.CEILING
                    val diff=dif.format(difference)

                    bmi_analysis.setText("Presently, you are extremely obese. You must lose ${diff}kg weight" +
                            " for fetching yourself a healthy Body Mass Index. ")
                    bmi_analysis_card.setCardBackgroundColor(Color.parseColor("#ec1a23"))
                    bmi_analysis_card.strokeColor=Color.parseColor("#690c10")


                }

                else if ((user_bmi>30.0)&&(user_bmi<=35.0)){

                    val difference=((user_height/100.0)*(user_height/100.0))*(user_bmi-25.0)
                    val dif = DecimalFormat("#.##")
                    dif.roundingMode = RoundingMode.CEILING
                    val diff=dif.format(difference)

                    bmi_analysis.setText("Presently, you are obese. You must lose ${diff}kg weight " +
                            "for fetching yourself a healthy Body Mass Index. ")
                    bmi_analysis_card.setCardBackgroundColor(Color.parseColor("#f68121"))
                    bmi_analysis_card.strokeColor=Color.parseColor("#7b4010")

                }
                else if ((user_bmi>25.0)&& (user_bmi<=30.0) ){

                    val difference=((user_height/100.0)*(user_height/100.0))*(user_bmi-25.0)
                    val dif = DecimalFormat("#.##")
                    dif.roundingMode = RoundingMode.CEILING
                    val diff=dif.format(difference)

                    bmi_analysis.setText("Presently, you are slightly overweight. You must lose" +
                            " ${diff}kg weight for fetching yourself a healthy Body Mass Index. ")
                    bmi_analysis_card.setCardBackgroundColor(Color.parseColor("#feca04"))
                    bmi_analysis_card.strokeColor=Color.parseColor("#745c02")

                }
                else if ((user_bmi>=18.5)&& (user_bmi<=25.0) ){

                    bmi_analysis.setText("Presently, you have a healthy Body Mass Index. ")
                    bmi_analysis_card.setCardBackgroundColor(Color.parseColor("#a5ba21"))
                    bmi_analysis_card.strokeColor=Color.parseColor("#4d5710")

                }
                else if ((user_bmi>0)&& (user_bmi<18.5) ){

                    val difference=((user_height/100.0)*(user_height/100.0))*(18.5-user_bmi)
                    val dif = DecimalFormat("#.##")
                    dif.roundingMode = RoundingMode.CEILING
                    val diff=dif.format(difference)

                    bmi_analysis.setText("Presently, you are underweight. You must gain" +
                            " ${diff}kg weight for fetching yourself a healthy Body Mass Index. ")
                    bmi_analysis_card.setCardBackgroundColor(Color.parseColor("#32b9f1"))
                    bmi_analysis_card.strokeColor=Color.parseColor("#16536c")



                }
                else{}


            }

            else{
                et_weight.setError("Please enter your biometrics")
                et_weight.requestFocus()
            }





        }

        if (user!=null) {
            fstore.collection("weight_height").get().addOnSuccessListener { result ->
                for (document in result) {
                    if (document.id==userID){

                        val uweight=document.data["weight"].toString()

                        val user_weight=document.data["weight"].toString().toFloat()

                        val uheight=document.data["height"].toString()

                        val user_height=document.data["height"].toString().toFloat()



                        val user_bmi=user_weight/((user_height/100.0)*(user_height/100.0))
                        val df = DecimalFormat("#.##")
                        df.roundingMode = RoundingMode.CEILING
                        val ubmi=df.format(user_bmi)

                        weight.setText("$uweight kg")
                        height.setText("$uheight cm")
                        bmi_display.setText("BMI:${ubmi.toString()}")

                        if (user_bmi>35.0){
                            val difference=((user_height/100.0)*(user_height/100.0))*(user_bmi-25.0)
                            val dif = DecimalFormat("#.##")
                            dif.roundingMode = RoundingMode.CEILING
                            val diff=dif.format(difference)

                            bmi_analysis.setText("Presently, you are extremely obese. You must lose ${diff}kg weight" +
                                    " for fetching yourself a healthy Body Mass Index. ")
                            bmi_analysis_card.setCardBackgroundColor(Color.parseColor("#ec1a23"))
                            bmi_analysis_card.strokeColor=Color.parseColor("#690c10")


                        }

                        else if ((user_bmi>30.0)&&(user_bmi<=35.0)){

                         val difference=((user_height/100.0)*(user_height/100.0))*(user_bmi-25.0)
                            val dif = DecimalFormat("#.##")
                            dif.roundingMode = RoundingMode.CEILING
                            val diff=dif.format(difference)

                            bmi_analysis.setText("Presently, you are obese. You must lose ${diff}kg weight " +
                                    "for fetching yourself a healthy Body Mass Index. ")
                            bmi_analysis_card.setCardBackgroundColor(Color.parseColor("#f68121"))
                            bmi_analysis_card.strokeColor=Color.parseColor("#7b4010")

                        }
                        else if ((user_bmi>25.0)&& (user_bmi<=30.0) ){

                            val difference=((user_height/100.0)*(user_height/100.0))*(user_bmi-25.0)
                            val dif = DecimalFormat("#.##")
                            dif.roundingMode = RoundingMode.CEILING
                            val diff=dif.format(difference)

                            bmi_analysis.setText("Presently, you are slightly overweight. You must lose" +
                                    " ${diff}kg weight for fetching yourself a healthy Body Mass Index. ")
                            bmi_analysis_card.setCardBackgroundColor(Color.parseColor("#feca04"))
                            bmi_analysis_card.strokeColor=Color.parseColor("#745c02")

                        }
                        else if ((user_bmi>=18.5)&& (user_bmi<=25.0) ){

                            bmi_analysis.setText("Presently, you have a healthy Body Mass Index. ")
                            bmi_analysis_card.setCardBackgroundColor(Color.parseColor("#a5ba21"))
                            bmi_analysis_card.strokeColor=Color.parseColor("#4d5710")

                        }
                        else if ((user_bmi>0)&& (user_bmi<18.5) ){

                            val difference=((user_height/100.0)*(user_height/100.0))*(18.5-user_bmi)
                            val dif = DecimalFormat("#.##")
                            dif.roundingMode = RoundingMode.CEILING
                            val diff=dif.format(difference)

                            bmi_analysis.setText("Presently, you are underweight. You must gain" +
                                    " ${diff}kg weight for fetching yourself a healthy Body Mass Index. ")
                            bmi_analysis_card.setCardBackgroundColor(Color.parseColor("#32b9f1"))
                            bmi_analysis_card.strokeColor=Color.parseColor("#16536c")



                        }
                        else{}

                    }

                }
            }
                .addOnFailureListener { exception ->

                }
        }


       /* btn_et_height.setOnClickListener(){
        }*/



        // Inflate the layout for this fragment
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BiometricsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BiometricsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}