package com.anomdev.qrscanner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.anomdev.qrscanner.databinding.ActivityMainBinding
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnScanner.setOnClickListener { initScanner() }

    }
    // Con este método la app abrirá la cámara y escaneará el código.
    private fun initScanner() {
       val integrator = IntentIntegrator(this)
        //Decidimo qué tipo de código queremos que pueda escanear (QR, código de barras, etc)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)

        //Aparece una indicación debajo del recuadro de escaneo
        integrator.setPrompt("Enfoca el código que quieres escanear")

        //Activa o no la linterna
        integrator.setTorchEnabled(false)

        //Emite un sonido al escanear correctamente
        integrator.setBeepEnabled(true)

        // Inicia el escaneo
        integrator.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        val result : IntentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data)
        if (result != null){
            if(result.contents == null){
                Toast.makeText(this,"Cancelado", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this,"El valor escaneado es: ${result.contents}", Toast.LENGTH_SHORT).show()

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}