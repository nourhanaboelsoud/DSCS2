package com.example.test1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
//import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var SignUpButton:Button

//    var mAuth: FirebaseAuth?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        initialization()
        SignUpButton.setOnClickListener {
            val intent=Intent(this,SignUp::class.java)
            startActivity(intent)
        }

//        mAuth=FirebaseAuth.getInstance()

    }

    private fun initialization(){
        SignUpButton=findViewById(R.id.SignUpButton)
    }

//    override fun onStart() {
//        super.onStart()
//        if (mAuth?.currentUser == null){
//            val intent= Intent(this,SignUp::class.java)
//            startActivity(intent)
//        }
//    }

}