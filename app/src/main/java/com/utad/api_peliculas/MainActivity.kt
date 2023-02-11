package com.utad.api_peliculas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.utad.api_peliculas.fragments.VistaGeneros

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        supportFragmentManager?.beginTransaction()
            ?.replace(R.id.mainContainer, VistaGeneros())?.addToBackStack(null)?.commit()
        // Mostrar como cuadricula



    }



}