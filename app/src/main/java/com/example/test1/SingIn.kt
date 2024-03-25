package com.example.test1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth


class SingIn : AppCompatActivity() {

    private lateinit var do_not_have_account:TextView
    private lateinit var Loginbtn:Button
//    private lateinit var remember:CheckBox
    private lateinit var emailSignIn:EditText
    private lateinit var passwordSignIn:EditText
    private lateinit var resetPassword:TextView
    var mAuth:FirebaseAuth?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sing_in)

        mAuth=FirebaseAuth.getInstance()

        initialization()
        do_not_have_account.setOnClickListener {
            val intent=Intent(this,SignUp::class.java)
            startActivity(intent)
        }

        Loginbtn.setOnClickListener {
            login()
        }

        resetPassword.setOnClickListener {
            val intent=Intent(this,ResetPassword::class.java)
            startActivity(intent)
        }


    }

    private fun login(){
        val email1=emailSignIn.text.toString()
        val password1=passwordSignIn.text.toString()
        mAuth?.signInWithEmailAndPassword(email1,password1)?.addOnCompleteListener {
            if (it.isSuccessful) {
                verifyEmailAddress()
            } else {
                Toast.makeText(applicationContext, "Can't find your Email", Toast.LENGTH_LONG).show()
                val intent=Intent(this,SignUp::class.java)
                startActivity(intent)
            }
        }

    }

    private fun initialization(){
        do_not_have_account=findViewById(R.id.do_not_have_account_TV)
        Loginbtn=findViewById(R.id.loginButton)
//        remember=findViewById(R.id.remember)
        emailSignIn=findViewById(R.id.email_ed)
        resetPassword=findViewById(R.id.forgetPasswordSingIn)
        passwordSignIn=findViewById(R.id.password_ed)
    }

    private fun verifyEmailAddress(){
        val user=mAuth?.currentUser
        if (user!!.isEmailVerified) {
            Toast.makeText(applicationContext, "login successfully", Toast.LENGTH_LONG).show()
            val intent=Intent(this,FirstScreen::class.java)
            startActivity(intent)
        } else {
            Toast.makeText(applicationContext, "Your Account is not verified", Toast.LENGTH_LONG).show()
        }
    }

}