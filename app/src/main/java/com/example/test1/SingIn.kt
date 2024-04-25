package com.example.test1

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


class SingIn : AppCompatActivity() {
    private lateinit var googleSignInClient:GoogleSignInClient
    private lateinit var do_not_have_account:TextView
    private lateinit var LogInBtn:Button
    private lateinit var actionSI:FloatingActionButton
//    private lateinit var remember:CheckBox
    private lateinit var emailSignIn:EditText
    private lateinit var googleImg:ImageView
    private lateinit var passwordSignIn:EditText
    private lateinit var resetPassword:TextView
    var mAuth:FirebaseAuth?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sing_in)

        mAuth=FirebaseAuth.getInstance()

        initialization()

        val gso=GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient=GoogleSignIn.getClient(this,gso)


        do_not_have_account.setOnClickListener {
            val intent=Intent(this,SignUp::class.java)
            startActivity(intent)
        }

        actionSI.setOnClickListener {
            val intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

        LogInBtn.setOnClickListener {
            login()
        }

        resetPassword.setOnClickListener {
            val intent=Intent(this,ResetPassword::class.java)
            startActivity(intent)
        }
        googleImg.setOnClickListener {
            signInWithGoogle()
        }
    }

    private fun signInWithGoogle(){
        val signInIntent=googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }

    private val launcher=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result->
        if (result.resultCode==Activity.RESULT_OK){
            val task=GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleResult(task)
        }
    }

    private fun handleResult(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful){
            val account:GoogleSignInAccount?=task.result
            if (account!=null){
                updateUI(account)
            }
        }
        else{
            Toast.makeText(applicationContext, "SignIn Failed , Try again later!", Toast.LENGTH_LONG).show()
        }
    }

    private fun updateUI(account: GoogleSignInAccount) {
        val credential=GoogleAuthProvider.getCredential(account.idToken,null)
        mAuth!!.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful){
                val intent=Intent(this,FirstScreen::class.java)
                startActivity(intent)
            } else{
                Toast.makeText(applicationContext, "Can't LogIn!", Toast.LENGTH_LONG).show()
            }
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
        actionSI=findViewById(R.id.actionSI)
        LogInBtn=findViewById(R.id.loginButton)
        googleImg=findViewById(R.id.googleImg)
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