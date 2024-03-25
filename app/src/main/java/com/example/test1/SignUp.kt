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


class SignUp : AppCompatActivity() {

    private lateinit var do_have_account:TextView
    private lateinit var SignUpButton:Button
    private lateinit var remember1: CheckBox
    private lateinit var emailSignUp: EditText
    private lateinit var passwordSignUp: EditText
    private lateinit var resetPassword:TextView
    var mAuth: FirebaseAuth?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)

        initialization()
        do_have_account.setOnClickListener {
            val intent=Intent(this,SingIn::class.java)
            startActivity(intent)
        }

        mAuth=FirebaseAuth.getInstance()

        remember1.setOnClickListener {
            signUp()
        }

        resetPassword.setOnClickListener {
            val intent=Intent(this,ResetPassword::class.java)
            startActivity(intent)
        }

    }


    private fun initialization(){
        do_have_account=findViewById(R.id.do_have_account_TV)
        SignUpButton=findViewById(R.id.SignUpButton)
        remember1=findViewById(R.id.remember1)
        emailSignUp=findViewById(R.id.email_ed1)
        resetPassword=findViewById(R.id.forgetPasswordSingUp)
        passwordSignUp=findViewById(R.id.password_ed1)
    }

    private fun signUp() {
        val email = emailSignUp.text.toString()
        val password = passwordSignUp.text.toString()
        if (email.isNotEmpty() && password.isNotEmpty()) {
            mAuth?.createUserWithEmailAndPassword(email, password)?.addOnCompleteListener {
                if (it.isSuccessful) {
                    sendEmailVerification()
//                    Toast.makeText(applicationContext, "login successfully", Toast.LENGTH_LONG).show()
//                    SignUpButton.setOnClickListener {
//                        val intent=Intent(this,FirstScreen::class.java)
//                        startActivity(intent)
//                    }
                } else {
                    Toast.makeText(applicationContext, it.exception.toString(), Toast.LENGTH_LONG).show()
                    SignUpButton.isEnabled = false
                }
            }
        } else {
            Toast.makeText(applicationContext, "Empty Email or Password", Toast.LENGTH_LONG).show()
            SignUpButton.isEnabled = false
        }
    }

    private fun sendEmailVerification() {
        val user=mAuth?.currentUser
        user?.sendEmailVerification()?.addOnCompleteListener {
            if (it.isSuccessful){
                val intent=Intent(this,SingIn::class.java)
                startActivity(intent)
                Toast.makeText(this,"Please check your Email",Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(applicationContext, it.exception.toString(), Toast.LENGTH_LONG).show()
                SignUpButton.isEnabled = false
            }
        }
    }

}