package com.example.employee_access_control_system

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import android.graphics.Bitmap
import android.graphics.Color
import com.google.firebase.auth.FirebaseAuth

class QrcodeActivity : AppCompatActivity() {
    private var im: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrcode)

        im = findViewById(R.id.imageView5)
        val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid
        currentUserUid?.let { generateQrCode(it) }
    }

    private fun generateQrCode(uid: String) {
        val multiFormatWriter = MultiFormatWriter()
        try {
            val bitMatrix: BitMatrix = multiFormatWriter.encode(uid, BarcodeFormat.QR_CODE, 500, 500)
            val width: Int = bitMatrix.width
            val height: Int = bitMatrix.height
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
            for (x in 0 until width) {
                for (y in 0 until height) {
                    bitmap.setPixel(x, y, if (bitMatrix.get(x, y)) Color.BLACK else Color.WHITE)
                }
            }
            im?.setImageBitmap(bitmap)
        } catch (e: WriterException) {
            e.printStackTrace()
        }
    }

    // Обработка нажатия кнопки "Назад"
    fun goBack() {
        finish()
    }
}