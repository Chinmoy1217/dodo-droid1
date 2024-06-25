package com.example.myapplication

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.validatePhoneNumber

@Composable
fun PhoneSignInScreen(
    viewModel: PhoneSignInViewModel = viewModel(),
    onPhoneSignInSuccess: () -> Unit,
    onBack: () -> Unit
) {
    val context = LocalContext.current as Activity

    var phoneNumber by remember { mutableStateOf(viewModel.phoneNumber) }
    var verificationCode by remember { mutableStateOf(viewModel.verificationCode) }
    val isLoading by remember { mutableStateOf(viewModel.isLoading) }
    val errorMessage by remember { mutableStateOf(viewModel.errorMessage) }
    val isCodeSent by remember { mutableStateOf(viewModel.isCodeSent) }
    var isPhoneNumberValid by remember { mutableStateOf(true) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (!isCodeSent) {
                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = {
                        phoneNumber = it
                        viewModel.phoneNumber = it
                        isPhoneNumberValid = validatePhoneNumber(it)
                    },
                    label = { Text("Phone Number") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = !isPhoneNumberValid
                )
                if (!isPhoneNumberValid) {
                    Text(
                        text = "Invalid phone number",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { viewModel.sendVerificationCode(context) },
                    enabled = isPhoneNumberValid && !isLoading
                ) {
                    Text("Send Verification Code")
                }
            } else {
                // Show OTP input when isCodeSent is true
                OutlinedTextField(
                    value = verificationCode,
                    onValueChange = {
                        verificationCode = it
                        viewModel.verificationCode = it
                    },
                    label = { Text("Verification Code") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { viewModel.verifyCodeAndSignIn(onPhoneSignInSuccess) },
                    enabled = !isLoading
                ) {
                    Text("Verify and Sign In")
                }
            }

            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp))
            }

            errorMessage?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onBack) {
                Text("Back")
            }
        }
    }
}