package com.example.chattingapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var editEmail: TextInputLayout
    private lateinit var editPassword: TextInputLayout
    private lateinit var buttonLogin: Button
    private lateinit var buttonSignUp: Button

    private lateinit var mAuth: FirebaseAuth
    lateinit var editor: SharedPreferences.Editor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()

        editEmail = findViewById(R.id.etd_email)
        editPassword = findViewById(R.id.etd_password)
        buttonLogin = findViewById(R.id.btn_login)
        buttonSignUp = findViewById(R.id.btn_signup)
        val sharedPreference =  getSharedPreferences("LOGIN_DETAILS", Context.MODE_PRIVATE)
        editor = sharedPreference.edit()
        mAuth = FirebaseAuth.getInstance()

        buttonSignUp.setOnClickListener {
            val intent = Intent(this, SignActivity::class.java)
            startActivity(intent)
            finish()
        }

        buttonLogin.setOnClickListener {
            val email = editEmail.editText?.text.toString()
            val password = editPassword.editText?.text.toString()
            if(email == ""){
                Toast.makeText(this@LoginActivity, "Please Enter Email Address", Toast.LENGTH_SHORT).show()
            }else if(password == ""){
                Toast.makeText(this@LoginActivity, "Please Enter Password Address", Toast.LENGTH_SHORT).show()
            }else{
                login(email, password)
            }
        }
    }

    private fun login(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){task ->
                if(task.isSuccessful){
                    editor.putString("email",email)
                    editor.putString("password",password)
                    editor.putBoolean("Logged_In", true)
                    editor.commit()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    Toast.makeText(this@LoginActivity, "user does not exist", Toast.LENGTH_SHORT).show()
                }
            }
    }
}