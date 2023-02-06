package com.example.yourfitnessidol.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.yourfitnessidol.R
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_change_password.*

class Change_password : AppCompatActivity() {

    private lateinit var fAuth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        fAuth=FirebaseAuth.getInstance()

        btn_changepassword.setOnClickListener(){

            changepassword()
        }



    }

    private fun changepassword(){

        if ((et_current_password.text.trim().isNotEmpty())&&(et_new_password.text.trim().isNotEmpty())&&
            (et_confirm_password.text.trim().isNotEmpty())){

            if (et_new_password.text.trim().toString().equals(et_confirm_password.text.trim().toString())){

                if (et_new_password.text.trim().toString().length>=6){

                    val user=fAuth.currentUser

                    if((user!=null)&&(user.email!=null)){
                        val credential = EmailAuthProvider
                            .getCredential(user.email!!, et_current_password.text.toString())

// Prompt the user to re-provide their sign-in credentials
                        user?.reauthenticate(credential)?.addOnCompleteListener {
                            if (it.isSuccessful){
                                Toast.makeText(this,"Re-authentication successful.",
                                    Toast.LENGTH_SHORT).show()


                                user?.updatePassword(et_new_password.text.toString())
                                    .addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            Toast.makeText(this,"Password changed successfully.",
                                                Toast.LENGTH_SHORT).show()

                                            fAuth.signOut()

                                            startActivity(Intent(this,LoginActivity2::class.java))
                                            finish()
                                        }
                                    }

                            }
                            else{
                                Toast.makeText(this,"Re-authentication failed.",
                                    Toast.LENGTH_SHORT).show()
                            }



                        }



                    }

                    else{
                        startActivity(Intent(this,LoginActivity2::class.java))
                        finish()
                    }



                }
                else{
                    Toast.makeText(this,"Please enter a valid password.",Toast.LENGTH_SHORT).show()
                }

            }
            else{
                Toast.makeText(this,"Password mismatching!",Toast.LENGTH_SHORT).show()
            }

        }
        else{
            Toast.makeText(this,"Please enter all the fields.",Toast.LENGTH_SHORT).show()
        }


    }
}