package com.example.test1

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class FirstScreen : AppCompatActivity() {

    var mAuth: FirebaseAuth?=null
    private lateinit var logout:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_first_screen)

        mAuth=FirebaseAuth.getInstance()

        init()

        logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

    }

    private fun init(){
        logout=findViewById(R.id.logout_tv)
    }

}