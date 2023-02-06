package com.example.yourfitnessidol.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.yourfitnessidol.*
import com.example.yourfitnessidol.fragment.HomeFragment
import com.example.yourfitnessidol.fragment.*
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_login2.*
import java.util.*

class MainActivity : AppCompatActivity() {



    lateinit var drawerLayout: DrawerLayout
    lateinit var coordinatorLayout: CoordinatorLayout
    lateinit var toolbar: Toolbar
    lateinit var frameLayout: FrameLayout
    lateinit var navigationView: NavigationView

    lateinit var loginpage_progresslayoutii: RelativeLayout
    lateinit var loginpage_progressbarii: ProgressBar

    lateinit var fmsg:FirebaseMessaging
    lateinit var fauth:FirebaseAuth
    lateinit var fstore:FirebaseFirestore

    private lateinit var userid: String






    var previousmenuitem: MenuItem?=null





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loginpage_progresslayoutii=findViewById(R.id.loginpage_progresslayoutii)
        loginpage_progressbarii=findViewById(R.id.loginpage_progressbarii)

        loginpage_progresslayoutii.visibility=View.GONE


        fmsg= FirebaseMessaging.getInstance()
        fauth= FirebaseAuth.getInstance()
        fstore= FirebaseFirestore.getInstance()

        val user = fauth.currentUser
        userid = user!!.uid














        drawerLayout = findViewById(R.id.drawerLayout)
        coordinatorLayout = findViewById(R.id.coordinatorLayout)
        toolbar = findViewById(R.id.toolbar)
        frameLayout = findViewById(R.id.frameLayout)
        navigationView = findViewById(R.id.navigationView)
        setUpToolbar()

        openHome()

        val actionBarDrawerToggle = ActionBarDrawerToggle(
            this@MainActivity,
            drawerLayout,
            R.string.open_drawer,
            R.string.close_drawer
        )
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        navigationView.setNavigationItemSelectedListener {

            if(previousmenuitem!=null){
                previousmenuitem?.isChecked=false
            }

            it.isCheckable=true
            it.isChecked=true
            previousmenuitem=it





            when (it.itemId) {


                R.id.home_menu -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.frameLayout,
                        HomeFragment()
                    ).commit()
                    drawerLayout.closeDrawers()

                    supportActionBar?.title="Home"
                }

                R.id.goal_analyzer -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.frameLayout,
                        FitnessGoalsFragment()
                    ).commit()
                    drawerLayout.closeDrawers()

                    supportActionBar?.title="Fitness goals"
                }

                R.id.calorie_content -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.frameLayout,
                        CalorieContentFragment()
                    ).commit()
                    drawerLayout.closeDrawers()

                    supportActionBar?.title="Calorie content"
                }

                R.id.biometrics -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.frameLayout,
                        BiometricsFragment()
                    ).commit()
                    drawerLayout.closeDrawers()

                    supportActionBar?.title="Biometrics"
                }

                R.id.your_profile -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.frameLayout,
                        ProfileFragment()
                    ).commit()
                    drawerLayout.closeDrawers()

                    supportActionBar?.title="Profile"
                }

                R.id.about_app -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.frameLayout,
                        AboutAppFragment()
                    ).commit()
                    drawerLayout.closeDrawers()

                    supportActionBar?.title="About app"
                }

            }
            return@setNavigationItemSelectedListener true
        }
    }

    fun setUpToolbar(){
        setSupportActionBar(toolbar)
        supportActionBar?.title ="Toolbar title"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {


        val id= item.itemId
        if (id==android.R.id.home){
            drawerLayout.openDrawer(GravityCompat.START)}
        return super.onOptionsItemSelected(item)
    }

    fun openHome (){
        val fragment= HomeFragment()
        val transaction= supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout,fragment)
        transaction.commit()

        supportActionBar?.title="Home"

        navigationView.setCheckedItem(R.id.home_menu)

    }

    override fun onBackPressed() {
        val frag= supportFragmentManager.findFragmentById(R.id.frameLayout)

        when(frag)
        {
            !is HomeFragment ->openHome()

            else->{
                super.onBackPressed()
                loginpage_progresslayout.visibility=View.GONE
            }
        }
    }
}