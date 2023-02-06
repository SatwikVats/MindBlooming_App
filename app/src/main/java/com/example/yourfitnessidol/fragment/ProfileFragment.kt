package com.example.yourfitnessidol.fragment

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.yourfitnessidol.R
import com.example.yourfitnessidol.activity.Change_password
import com.example.yourfitnessidol.activity.LoginActivity2
import com.example.yourfitnessidol.activity.RegisterActivity
import com.example.yourfitnessidol.model.goaldetail
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_profile.*
import java.lang.Exception

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var auth: FirebaseAuth
    private lateinit var fstore: FirebaseFirestore
    private lateinit var userID: String

    lateinit var change_password: Button
    lateinit var btn_logout: Button
    lateinit var last_updated_timedate: TextView
    lateinit var heyy_user: TextView
    lateinit var btn_del_acc: Button




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
        val view= inflater.inflate(R.layout.fragment_profile, container, false)

        auth = FirebaseAuth.getInstance()
        fstore= FirebaseFirestore.getInstance()
        val user=auth.currentUser
        userID=user!!.uid




        change_password=view.findViewById(R.id.change_password)
        btn_logout=view.findViewById(R.id.btn_logout)
        last_updated_timedate=view.findViewById(R.id.last_updated_timedate)
        btn_del_acc=view.findViewById(R.id.btn_del_acc)

        heyy_user=view.findViewById(R.id.heyy_user)


        var docname:String
        var docnamei:String
        var docnameii:String




        if (user!=null) {
            fstore.collection("users").get().addOnSuccessListener { result ->
                for (document in result) {
                    if (document.id == userID) {

                        val heyyusername = document.data["uname"].toString()
                        heyy_user.setText("Hey $heyyusername")
                    }

                }
            }
        }


        if (user!=null) {
            fstore.collection("weight_height").get().addOnSuccessListener { result ->
                for (document in result) {
                    if (document.id == userID) {

                        val last_updated = document.data["biometrics_last_updated"].toString()
                        last_updated_timedate.setText(last_updated)
                    }

                }
            }
        }


        change_password.setOnClickListener(){
            val intent = Intent (getActivity(), Change_password::class.java)
           /* getActivity().*/ startActivity(intent)
           // startActivity(Intent(this as Context,Change_password::class.java))//
            activity?.finish()

        }

        btn_logout.setOnClickListener(){
            auth.signOut()
            val intent = Intent (getActivity(), LoginActivity2::class.java)
            startActivity(intent)
            activity?.finish()

        }

        btn_del_acc.setOnClickListener() {

            if (user != null) {

                val acc_del_dialog = AlertDialog.Builder(activity as Context)
                acc_del_dialog.setTitle("Are you sure?")
                acc_del_dialog.setMessage("Deleting this account will result in completely removing your account from the system and you won't be able to access the app.")

                acc_del_dialog.setPositiveButton("Ok") { text, listener ->

                    fstore.collection("to_do_list").get()
                        .addOnSuccessListener { result ->
                            for (document in result) {
                                if ((document.id).startsWith(userID)) {

                                    docname = document.id

                                    val updates = hashMapOf<String, Any>(
                                        "exercise_timing" to FieldValue.delete(),
                                        "frequency" to FieldValue.delete(),
                                        "name" to FieldValue.delete(),
                                        "repetitions" to FieldValue.delete()
                                    )
                                    fstore.collection("to_do_list").document(docname).update(updates)

                                    fstore.collection("to_do_list").document(docname)
                                        .delete()
                                    //recyclerAdapter.notifyDataSetChanged()
                                }
                            }
                        }

                    fstore.collection("users").get().addOnSuccessListener { result ->
                        for (documenti in result) {
                            if (userID in documenti.id) {

                                docnamei = documenti.id

                                val updatesi = hashMapOf<String, Any>(
                                    "uname" to FieldValue.delete(),
                                    "userid" to FieldValue.delete()
                                )
                                fstore.collection("users").document(docnamei).update(updatesi)

                                fstore.collection("users").document(docnamei).delete()
                                //recyclerAdapter.notifyDataSetChanged()
                            }
                        }
                    }



                    fstore.collection("weight_height").get()
                        .addOnSuccessListener { result ->
                            for (documentii in result) {
                                if (documentii.id.startsWith(userID)) {


                                    docnameii = documentii.id

                                    val updatesii = hashMapOf<String, Any>(
                                        "biometrics_last_updated" to FieldValue.delete(),
                                        "height" to FieldValue.delete(),
                                        "weight" to FieldValue.delete()
                                    )
                                    fstore.collection("weight_height").document(docnameii).update(updatesii)
                                    fstore.collection("weight_height").document(docnameii).delete()
                                    //recyclerAdapter.notifyDataSetChanged()
                                }
                            }
                        }



                        auth.signOut()

                        val intenti = Intent (getActivity(), LoginActivity2::class.java)
                        startActivity(intenti)
                        activity?.finish()

                    user.delete().addOnCompleteListener(){
                        Toast.makeText(this.context,"Account deleted successfully.",Toast.LENGTH_LONG).show()
                    }









                }




                acc_del_dialog.setNegativeButton("Cancel") { text, listener ->
                    return@setNegativeButton
                }
                acc_del_dialog.create()
                acc_del_dialog.show()


            }
        }







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
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}