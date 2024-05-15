package com.example.employee_access_control_system

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions


class GenerationScanner : AppCompatActivity() {

    private val barcodeLauncher: ActivityResultLauncher<ScanOptions> = registerForActivityResult(
        ScanContract()
    ) { result ->
        if (result.contents == null) {
            Toast.makeText(this@GenerationScanner, "Cancelled", Toast.LENGTH_LONG).show()
        } else {
            val scannedUid = result.contents
            val intent = Intent(this@GenerationScanner, ScanResultHandler::class.java)
            intent.putExtra("scannedUid", scannedUid)
            startActivity(intent)
        }
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scanQRCode()
    }

    private fun scanQRCode() {
        val options = ScanOptions()
        options.setOrientationLocked(false)
        barcodeLauncher.launch(options)
    }
}
