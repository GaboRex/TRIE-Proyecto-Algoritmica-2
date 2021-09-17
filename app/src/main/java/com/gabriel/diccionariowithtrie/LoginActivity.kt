package com.gabriel.diccionariowithtrie

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setup()
    }
    private fun setup() {
        title = "Autenticacion"

        buttonBuscar.setOnClickListener {
            if (editTextUsuario.text.isNotEmpty() && editTextPassword.text.isNotEmpty()) {
                FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(
                        editTextUsuario.text.toString(), editTextPassword.text.toString()
                    ).addOnCompleteListener {

                        if (it.isSuccessful) {
                            showSearch(it.result?.user?.email ?: "" )
                        } else {
                            showAlertRegister()
                        }
                    }
            }
        }
        buttonIniciarSesion.setOnClickListener {
            if (editTextUsuario.text.isNotEmpty() && editTextPassword.text.isNotEmpty())
                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(
                        editTextUsuario.text.toString(),editTextPassword.text.toString()
                    ).addOnCompleteListener {
                        if (it.isSuccessful) {
                            showSearch(it.result?.user?.email ?: "")
                        } else {
                            showAlertLogin()
                        }
                    }
        }
    }

    private fun showAlertLogin() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("El usuario no se encuentra registrado.")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showAlertRegister() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("El usuario ya se encuentra registrado.")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showSearch(email: String) {
        val intent = Intent(this, SearchActivity::class.java).apply {
            putExtra("username", email)
            putExtra("email", email)
        }
        startActivity(intent)
    }
}
