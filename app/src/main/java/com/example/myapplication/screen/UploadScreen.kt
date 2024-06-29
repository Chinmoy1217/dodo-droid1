package com.example.myapplication.screen
// UploadScreen.kt
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID

@Composable
fun UploadScreen(onUploadSuccess: () -> Unit) {
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }
    var uploadSuccess by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    // Load image when imageUri is updated
    LaunchedEffect(imageUri) {
        imageUri?.let {
            val result = loadImageBitmap(context, it)
            result.onSuccess { bitmap ->
                imageBitmap = bitmap
            }.onFailure { exception ->
                Log.e("UploadScreen", "Error loading image", exception)
                Toast.makeText(context, "Error loading image", Toast.LENGTH_SHORT).show()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (imageBitmap != null) {
            Image(
                bitmap = imageBitmap!!,
                contentDescription = null,
                modifier = Modifier.size(200.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { imagePicker.launch("image/*") }) {
            Text(text = "Pick Image")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            imageUri?.let { uri ->
                isLoading = true
                uploadImageToFirebase(uri) { success ->
                    isLoading = false
                    uploadSuccess = success
                    if (success) {
                        Toast.makeText(context, "Upload Successful", Toast.LENGTH_SHORT).show()
                        onUploadSuccess()
                    } else {
                        Toast.makeText(context, "Upload Failed", Toast.LENGTH_SHORT).show()
                    }
                }
            } ?: Toast.makeText(context, "No image selected", Toast.LENGTH_SHORT).show()
        }) {
            Text(text = "Upload Image")
        }

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp))
        } else if (uploadSuccess) {
            Text(text = "Upload Successful", color = MaterialTheme.colorScheme.primary)
        }
    }
}

private suspend fun loadImageBitmap(
    context: android.content.Context,
    uri: Uri
): Result<ImageBitmap?> {
    return withContext(Dispatchers.IO) {
        try {
            val imageLoader = ImageLoader(context)
            val request = ImageRequest.Builder(context)
                .data(uri)
                .build()

            val result = (imageLoader.execute(request) as? SuccessResult)?.drawable
            Result.success(result?.toBitmap()?.asImageBitmap())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

private fun uploadImageToFirebase(uri: Uri, onResult: (Boolean) -> Unit) {
    val storageReference = FirebaseStorage.getInstance().reference
    val imageRef = storageReference.child("images/${UUID.randomUUID()}.jpg")

    val uploadTask = imageRef.putFile(uri)

    uploadTask.addOnSuccessListener {
        onResult(true)
    }.addOnFailureListener { exception ->
        Log.e("UploadScreen", "Error uploading image", exception)
        onResult(false)
    }
}