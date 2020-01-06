package com.example.hiddenvalley

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.after_login.*
import kotlinx.android.synthetic.main.login.*
import kotlinx.android.synthetic.main.user_registration.*
import kotlinx.android.synthetic.main.user_registration.pass
import kotlinx.android.synthetic.main.user_registration.username

class  MainActivity : AppCompatActivity() {

    lateinit var handler: AccDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        handler = AccDatabase(this)

        registration_button.setOnClickListener {
            setContentView(R.layout.user_registration)
        }

        login_button.setOnClickListener {
            setContentView(R.layout.login)
        }

        back_button.setOnClickListener {
            setContentView(R.layout.activity_main)
        }

        sign_button.setOnClickListener {
            if(username.text.toString().isNotEmpty()){
                if (handler.verifyUsername(username.text.toString())) {
                    if (pass.text.toString().isNotEmpty() && email.text.toString().isNotEmpty() && address.text.toString().isNotEmpty() && pass.text.toString() == password.text.toString()) {
                        handler.insertUserData(username.text.toString(), pass.text.toString(),email.text.toString(), address.text.toString())
                        setContentView(R.layout.activity_main)
                    } else {
                        Toast.makeText(this, "Incorrect Second Password", Toast.LENGTH_SHORT).show()
                        setContentView(R.layout.user_registration)
                    }
                } else
                    Toast.makeText(this, "Username Been Used", Toast.LENGTH_SHORT).show()
            } else
                Toast.makeText(this, "Username cannot be empty", Toast.LENGTH_SHORT).show()
        }

        login.setOnClickListener {
            if (handler.userLogin(login_username.text.toString(), login_pass.text.toString())) {
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                var data = handler.retrieveData(login_username.text.toString())
                setContentView(R.layout.after_login)
                afterUsername.text = ""
                for (i in 0..(data.size - 1)) {
                    afterUsername.append(data.get(i).username)
                }
            } else {
                Toast.makeText(this, "Username Or Password Incorrect", Toast.LENGTH_SHORT).show()
                setContentView(R.layout.activity_main)
            }
        }
    }
}
