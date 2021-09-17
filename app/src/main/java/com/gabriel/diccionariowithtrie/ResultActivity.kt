package com.gabriel.diccionariowithtrie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        Handler(Looper.getMainLooper()).postDelayed(
            {
                val intent = Intent(this, NoResultsActivity::class.java)
                startActivity(intent)
                finish()
            }, 2000
        )
    }
}