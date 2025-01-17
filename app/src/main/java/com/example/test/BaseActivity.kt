package com.example.test
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning

open class BaseActivity : AppCompatActivity() {

    protected fun startBarcodeScan() {
        val options = GmsBarcodeScannerOptions.Builder()
            .setBarcodeFormats(
                Barcode.FORMAT_EAN_13,
                Barcode.FORMAT_EAN_8,
                Barcode.FORMAT_UPC_A,
                Barcode.FORMAT_UPC_E
            )
            .enableAutoZoom()
            .build()

        val scanner = GmsBarcodeScanning.getClient(this, options)

        scanner.startScan()
            .addOnSuccessListener { barcode ->
                val rawValue: String? = barcode.rawValue
                if (rawValue != null) {
                    val intent = Intent(this, MainActivity2::class.java)
                    intent.putExtra("barcode_value", rawValue)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Scan failed.", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnCanceledListener {
                Toast.makeText(this, "Scan canceled.", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Scan error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
