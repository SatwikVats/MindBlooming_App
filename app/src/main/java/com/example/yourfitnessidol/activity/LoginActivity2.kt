package com.example.yourfitnessidol.activity

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import com.example.yourfitnessidol.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_change_password.*
import kotlinx.android.synthetic.main.activity_login2.*

class LoginActivity2 : AppCompatActivity() {

    private lateinit var fAuth: FirebaseAuth

    private lateinit var username:EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login2)

        fAuth= FirebaseAuth.getInstance()

        /*var emailid: EditText =findViewById(R.id.emailid)
        var password: EditText =findViewById(R.id.password)
        val loginbutton: Button =findViewById(R.id.loginbutton)
        val registerlink: TextView =findViewById(R.id.registerlink)*/
        lateinit var loginpage_progresslayout: RelativeLayout
        lateinit var loginpage_progressbar: ProgressBar

        loginpage_progresslayout=findViewById(R.id.loginpage_progresslayout)
        loginpage_progressbar=findViewById(R.id.loginpage_progressbar)

        loginpage_progresslayout.visibility=View.GONE










        registerlink.setOnClickListener(){

            startActivity(Intent(this,RegisterActivity::class.java))
            finish()
        }

        forgotpasswordlink.setOnClickListener(){

             val builder= AlertDialog.Builder(this)
            builder.setTitle("Forgot Password")
            val view=layoutInflater.inflate(R.layout.forgotpassworddialogbox,null)
            username=view.findViewById(R.id.et_username)

            builder.setView(view)
            builder.setPositiveButton("Reset",DialogInterface.OnClickListener{ _, _ ->
                if (username.text.toString().trim().isEmpty()) {
                    return@OnClickListener
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(username.text.toString().trim()).matches()) {
                    return@OnClickListener
                }

                fAuth.sendPasswordResetEmail(username.text.toString())
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(baseContext, "Email sent.",
                                Toast.LENGTH_SHORT).show()
                        }
                    }
            })
            builder.setNegativeButton("Close",DialogInterface.OnClickListener{ _, _ -> })
            builder.show()

        }

        loginbutton.setOnClickListener(){


            if (emailid.text.toString().trim().isEmpty()) {
                emailid.setError("Email is required")
                emailid.requestFocus()
                return@setOnClickListener

            }

            if (!Patterns.EMAIL_ADDRESS.matcher(emailid.text.toString().trim()).matches()) {
                emailid.error = "Please enter valid email address"
                emailid.requestFocus()
                return@setOnClickListener


            }



            if (password.text.toString().trim().isEmpty()) {
                password.setError("Password is required")
                password.requestFocus()
                return@setOnClickListener

            }

            fAuth.signInWithEmailAndPassword(emailid.text.toString().trim(),password.text.toString().trim())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        /* Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success")*/
                        val user = fAuth.currentUser
                        if (user!!.isEmailVerified)
                        {
                            loginpage_progresslayout.visibility=View.VISIBLE
                        startActivity(Intent(this ,MainActivity::class.java))
                        password.text.clear()

                        }
                        else{
                            Toast.makeText(baseContext, "Login failed.",
                                Toast.LENGTH_LONG).show() }
                        //updateUI(user)//
                    } else {
                        // If sign in fails, display a message to the user.
                        //Log.w(TAG, "signInWithEmail:failure", task.exception)//
                        /* Toast.makeText(baseContext, "Login failed.",
                             Toast.LENGTH_SHORT).show()
                        updateUI(null)
                        */
                        Toast.makeText(baseContext, "Login failed.",
                            Toast.LENGTH_SHORT).show()
                    }
        }



    }






                // ...

    }

   /* private fun forgotpassword(username:EditText){

        if (username.text.toString().trim().isEmpty()) {
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(username.text.toString().trim()).matches()) {
            return
        }

        fAuth.sendPasswordResetEmail(username.text.toString())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(baseContext, "Email sent.",
                        Toast.LENGTH_SHORT).show()
                }
            }


    }*/



   /* public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = fAuth.currentUser
        //updateUI(currentUser)//
        if(currentUser!=null)
        {
            startActivity(Intent(this as Context,MainActivity::class.java))
        }
        else{
            Toast.makeText(baseContext, "Login failed.",
                Toast.LENGTH_SHORT).show()

        }
    }*/

    /*private fun updateUI(currentUser: FirebaseUser?){

        if(currentUser!=null)
        {
            startActivity(Intent(this as Context,MainActivity::class.java))
        }
        else{
            Toast.makeText(baseContext, "Login failed.",
                Toast.LENGTH_SHORT).show()

        }


    }*/





}