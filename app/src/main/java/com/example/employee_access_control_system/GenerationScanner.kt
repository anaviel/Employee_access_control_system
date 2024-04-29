package com.example.employee_access_control_system

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions

class GenerationScanner : AppCompatActivity() {
    private var bScanner: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanner)
        bScanner = findViewById(R.id.bScan)
        bScanner?.setOnClickListener {
            val options = ScanOptions()
            options.setOrientationLocked(false)
            barcodeLauncher.launch(options)
        }
    }

    private val barcodeLauncher: ActivityResultLauncher<ScanOptions> = registerForActivityResult(
        ScanContract()
    ) { result ->
        if (result.contents == null) {
            Toast.makeText(this@GenerationScanner, "Cancelled", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this@GenerationScanner, "Scanned: " + result.contents, Toast.LENGTH_LONG)
                .show()
        }
    }
}