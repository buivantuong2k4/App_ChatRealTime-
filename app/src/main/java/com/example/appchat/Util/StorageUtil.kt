package com.example.appchat.Util

import android.app.ProgressDialog
import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await

class StorageUtil{


    companion object {
        private  var storage = Firebase.storage
private    lateinit var pd: ProgressDialog
        fun uploadToStorage(uri: Uri?, context: Context, type: String,unique_image_name:String) {
            pd=ProgressDialog(context)
            if (uri!=null){

            // Create a storage reference from our app
            var storageRef = storage.reference

            var spaceRef: StorageReference

            if (type == "image"){
                spaceRef = storageRef.child("images/$unique_image_name.jpg")
            }else{
                spaceRef = storageRef.child("videos/$unique_image_name.mp4")
            }

            val byteArray: ByteArray? = context.contentResolver
                .openInputStream(uri)
                ?.use { it.readBytes() }

            byteArray?.let{

                var uploadTask = spaceRef.putBytes(byteArray)
                uploadTask.addOnFailureListener {
                    Toast.makeText(
                        context,
                        "upload failed",
                        Toast.LENGTH_SHORT
                    ).show()
                    // Handle unsuccessful uploads
                }.addOnSuccessListener { taskSnapshot ->
                    // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
                    // ...
                    Toast.makeText(
                        context,
                        "upload successed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }}
        }

        suspend fun getUriImage(Imagename: String): Uri? {
            Log.e("loi","hhh  $Imagename")
            val imageFileName = "${Imagename}.jpg"
            val imageRef = storage.reference.child("images").child(imageFileName)
       imageRef.let {
            return try {
                imageRef.downloadUrl.await()
            } catch (e: Exception) {
                // Log the error and rethrow the exception
                Log.e("SanphamItem", "Error fetching image URI: ${e.message}")
                return null
            }}

           }




    }}