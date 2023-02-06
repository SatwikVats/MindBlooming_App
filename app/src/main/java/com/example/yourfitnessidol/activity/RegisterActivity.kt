package com.example.yourfitnessidol.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.yourfitnessidol.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.model.ObjectValue
import java.net.IDN
import java.util.*
import kotlin.collections.HashMap


class RegisterActivity : AppCompatActivity() {

    private lateinit var fAuth: FirebaseAuth
    private lateinit var fstore: FirebaseFirestore
    private lateinit var userID: String










    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        fAuth = FirebaseAuth.getInstance()
        fstore= FirebaseFirestore.getInstance()

        val musername: EditText = findViewById(R.id.usernamenew)
        val mpassword: EditText = findViewById(R.id.passwordnew)
        val memail: EditText = findViewById(R.id.emailnew)
        val mpassword2: EditText= findViewById(R.id.passwordnew2)

        val already_regis:TextView=findViewById(R.id.already_regis)

        val mregister: Button = findViewById(R.id.registerbutton)


        already_regis.setOnClickListener(){

            startActivity(Intent(this,LoginActivity2::class.java))
            finish()

        }








        mregister.setOnClickListener() {

            val user_name=musername.text.toString().trim()

            /* var email = memail.text.toString().trim()
            var username = musername.text.toString().trim()
            var password = mpassword.text.toString().trim()
            var plength = password.length*/

            if (musername.text.toString().trim().isEmpty()) {
                musername.setError("Username is required.")
                musername.requestFocus()
                return@setOnClickListener

            }

            if (memail.text.toString().trim().isEmpty()) {
                memail.setError("Email is required.")
                memail.requestFocus()
                return@setOnClickListener

            }

            if (!Patterns.EMAIL_ADDRESS.matcher(memail.text.toString().trim()).matches()) {
                memail.error = "Please enter valid email address."
                memail.requestFocus()
                return@setOnClickListener


            }



            if (mpassword.text.toString().trim().isEmpty()) {
                mpassword.setError("Password is required.")
                mpassword.requestFocus()
                return@setOnClickListener

            }

            if (mpassword.text.toString().trim().length < 6) {
                mpassword.setError("Password must have atleast six characters.")
                mpassword.requestFocus()
                return@setOnClickListener

            }

            if (!(mpassword.text.toString().trim().equals(mpassword2.text.toString().trim()))) {
                mpassword.setError("The two passwords do not match.")
                mpassword2.setError("The two passwords do not match.")
                mpassword2.requestFocus()
                return@setOnClickListener

            }







            fAuth.createUserWithEmailAndPassword(
                memail.text.toString().trim(),
                mpassword.text.toString().trim()
            ).addOnCompleteListener(this)
            {

                    task ->
                if (task.isSuccessful) {

                    val user = fAuth.currentUser

                    user!!.sendEmailVerification()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {

                                Toast.makeText(
                                    baseContext, "Please verify your email. A conformation mail has been sent to ${memail.text.toString().trim()}",
                                    Toast.LENGTH_LONG
                                ).show()

                                userID= fAuth.currentUser!!.uid
                                val documentreference: DocumentReference= fstore.collection("users").document(userID)
                                var userf=HashMap<String,String>()
                                userf.put("userid",userID)
                                userf.put("uname",user_name)
                                documentreference.set(userf)


                                startActivity(Intent(this, LoginActivity2::class.java))
                                finish()
                            }
                        }

                    //Sign in success , update user's UI with signed in user's information//

                    /* Log.d(TAG,"createUserWithEmail:success")
                    val user=fAuth.currentUser
                    updateUI(user)*/
                } else {
                    /*If sign in fails , display a msg to the user
                    Log.w(TAG,"createUserWithEmail:failure",task.exception)*/
                    Toast.makeText(
                        baseContext, "Authentication failed. Try again after some time.",
                        Toast.LENGTH_SHORT
                    ).show()

                }

            }



        }


    }

    }


