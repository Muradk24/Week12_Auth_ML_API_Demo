package edu.farmingdale.alrajab.week12_auth_ml_api_demo

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import com.google.firebase.auth.FirebaseAuth
import edu.farmingdale.alrajab.week12_auth_ml_api_demo.databinding.ActivityLandingBinding
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class LandingActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    lateinit var binding: ActivityLandingBinding

    var photoUri: Uri?=null
    private val takePicture = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success: Boolean ->
        if (success) {
            binding.imageHolder.setImageURI(photoUri)
            binding.imageUrlField.setText(photoUri.toString())
            Toast.makeText(
                this, "Saved photo", Toast.LENGTH_SHORT
            ).show()
        } else {
            Toast.makeText(
                this, "Did not save photo", Toast.LENGTH_SHORT
            ).show()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLandingBinding.inflate(layoutInflater)
        setContentView(binding.root)
binding.loadIamgeBtn.setOnClickListener { // Create the File for saving the photo
    val photoFile = createImageFile()

    // Create a content URI to grant camera app write permission to photoFile
    photoUri= FileProvider.getUriForFile(
        this, "com.zybooks.camerademo.fileprovider", photoFile)
    // Start camera app
    takePicture.launch(photoUri) }
        binding.logoutBtn.setOnClickListener { logout() }


        firebaseAuth = FirebaseAuth.getInstance()

    }
    private fun createImageFile(): File {

        // Create a unique filename
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val imageFilename = "photo_$timeStamp.jpg"

        // Create the file in the Pictures directory on external storage
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File(storageDir, imageFilename)
    }

    private fun logout() {
        firebaseAuth.signOut()
            startActivity(Intent(this@LandingActivity,LoginActivity::class.java))

    }
    fun recognizeText(view : View){
        Toast.makeText(this,"Please check",Toast.LENGTH_SHORT).show()
    }
}


