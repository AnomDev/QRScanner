package com.anomdev.qrscanner

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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

        val result: IntentResult =
            IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(this, "Este código no se puede leer correctamente", Toast.LENGTH_LONG).show()
            }
            if (Patterns.WEB_URL.matcher(result.contents).matches()) {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(result.contents))
                startActivity(browserIntent)
            } else {
                Toast.makeText(
                    this,
                    "El valor del código escaneado es: ${result.contents}",
                    Toast.LENGTH_LONG
                ).show()

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}