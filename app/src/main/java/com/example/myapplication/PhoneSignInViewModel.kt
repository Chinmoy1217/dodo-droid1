package com.example.myapplication

import android.app.Activity
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class PhoneSignInViewModel : ViewModel() {
    var phoneNumber by mutableStateOf("")
    var verificationCode by mutableStateOf("")
    var verificationId by mutableStateOf("")
    var isCodeSent by mutableStateOf(false)
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)

    private val auth = FirebaseAuth.getInstance()

    fun sendVerificationCode(activity: Activity) {
        isLoading = true
        errorMessage = null
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    signInWithCredential(credential) {}
                }

                override fun onVerificationFailed(e: FirebaseException) {
                    isLoading = false
                    errorMessage = e.message
                    Log.e("PhoneAuth", "Verification Failed", e)
                }

                override fun onCodeSent(
                    verificationId: String,
                    token: PhoneAuthProvider.ForceResendingToken
                ) {
                    this@PhoneSignInViewModel.verificationId = verificationId
                    isCodeSent = true
                    isLoading = false
                    Log.d("PhoneAuth", "Code Sent: $verificationId")
                }
            })
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    fun verifyCodeAndSignIn(onSuccess: () -> Unit) {
        isLoading = true
        errorMessage = null
        val credential = PhoneAuthProvider.getCredential(verificationId, verificationCode)
        signInWithCredential(credential, onSuccess)
    }

    private fun signInWithCredential(credential: PhoneAuthCredential, onSuccess: () -> Unit) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                isLoading = false
                if (task.isSuccessful) {
                    onSuccess()
                } else {
                    errorMessage = task.exception?.message
                    Log.e("PhoneAuth", "Sign In Failed", task.exception)
                }
            }
    }
}
