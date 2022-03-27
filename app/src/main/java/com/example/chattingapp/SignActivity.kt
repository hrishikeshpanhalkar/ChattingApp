package com.example.chattingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignActivity : AppCompatActivity() {

    private lateinit var editName: TextInputLayout
    private lateinit var editPassword: TextInputLayout
    private lateinit var editEmail: TextInputLayout
    private lateinit var buttonSignUp: Button

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDatabaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign)

        supportActionBar?.hide()

        editName = findViewById(R.id.etd_name)
        editEmail = findViewById(R.id.etd_email)
        editPassword = findViewById(R.id.etd_password)
        buttonSignUp = findViewById(R.id.btn_signup)

        mAuth = FirebaseAuth.getInstance()


        buttonSignUp.setOnClickListener {
            val name = editName.editText?.text.toString()
            val email = editEmail.editText?.text.toString()
            val password = editPassword.editText?.text.toString()
            if (name == "") {
                Toast.makeText(this@SignActivity, "Please Enter Name Address", Toast.LENGTH_SHORT).show()
            } else if (email == "") {
                Toast.makeText(this@SignActivity, "Please Enter Email Address", Toast.LENGTH_SHORT).show()
            } else if (password == "") {
                Toast.makeText(this@SignActivity, "Please Enter Password Address", Toast.LENGTH_SHORT).show()
            } else {
                signup(name, email, password)
            }

        }
    }

    private fun signup(name: String, email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    addUserToDatabase(name, email, mAuth.currentUser?.uid!!)
                } else {
                    Toast.makeText(this@SignActivity, "Some error occurred", Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }

    private fun addUserToDatabase(name: String, email: String, uid: String) {
        mDatabaseReference = FirebaseDatabase.getInstance().getReference()

        mDatabaseReference.child("user").child(uid).setValue(User(name, email, uid))
        Toast.makeText(this@SignActivity, "User Created Successfully!!", Toast.LENGTH_SHORT)
            .show()
        val intent = Intent(this@SignActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}