package com.example.test1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class ResetPassword : AppCompatActivity() {

    private lateinit var ResetButton:Button
    private lateinit var emailReset:EditText
    private lateinit var Backutton:Button
    var mAuth: FirebaseAuth?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_raset_password)

        mAuth=FirebaseAuth.getInstance()

        inti()

        ResetButton.setOnClickListener {
            val pass=emailReset.text.toString()
            mAuth!!.sendPasswordResetEmail(pass).addOnSuccessListener {
                Toast.makeText(this,"Please check your Email",Toast.LENGTH_LONG).show()
            } .addOnFailureListener {
                Toast.makeText(applicationContext, it.toString(), Toast.LENGTH_LONG).show()
            }
        }

        Backutton.setOnClickListener {
            val intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun inti() {
        ResetButton=findViewById(R.id.resetButton)
        Backutton=findViewById(R.id.backButton)
        emailReset=findViewById(R.id.email_ed)
    }

}