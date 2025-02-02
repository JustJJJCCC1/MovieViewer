package com.it2161.dit231980U.movieviewer.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.it2161.dit231980U.movieviewer.MovieRaterApplication
import com.it2161.dit231980U.movieviewer.R
import com.it2161.dit231980U.movieviewer.data.UserProfile

@Composable
fun ProfileScreen(navController: NavController) {
    val scrollState = rememberScrollState()

    var isPasswordVisible by remember { mutableStateOf(false) }
    var isConfirmPasswordVisible by remember { mutableStateOf(false) }

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    // Error states
    var usernameError by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }
    var confirmPasswordError by remember { mutableStateOf("") }

    // Track if password is changed
    var isPasswordChanged by remember { mutableStateOf(false) }

    // Load the existing profile data
    LaunchedEffect(Unit) {
        val profile = MovieRaterApplication.instance.userProfile
        if (profile != null) {
            username = profile.userName
            password = profile.password
            email = profile.email
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {

        Spacer(modifier = Modifier.height(16.dp))

        Column(modifier = Modifier.fillMaxSize()) {
            // Username Field
            OutlinedTextField(
                value = username,
                onValueChange = { newUsername ->
                    username = newUsername
                    usernameError = when {
                        newUsername.length < 3 -> "Username must be at least 3 characters"
                        newUsername.length > 30 -> "Username must be no more than 30 characters"
                        else -> ""
                    }
                },
                label = { Text("Username") },
                isError = usernameError.isNotEmpty(),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.Black,
                    unfocusedLabelColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    unfocusedSupportingTextColor = Color.Black,
                    focusedBorderColor = Color.Black,
                    focusedLabelColor = Color.Black,
                    focusedTextColor = Color.Black,
                    focusedSupportingTextColor = Color.Black,
                    errorBorderColor = Color.Red,
                    errorLabelColor = Color.Red,
                    errorTextColor = Color.Red,
                )
            )

            if (usernameError.isNotEmpty()) {
                Text(
                    text = usernameError,
                    color = Color.Red,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Password Field
            OutlinedTextField(
                value = password,
                onValueChange = { newPassword ->
                    password = newPassword
                    isPasswordChanged = newPassword != MovieRaterApplication.instance.userProfile?.password
                    passwordError = when {
                        newPassword.length < 8 -> "Password must be at least 8 characters"
                        !newPassword.any { it.isDigit() } -> "Password must contain at least one number"
                        !newPassword.any { !it.isLetterOrDigit() } -> "Password must contain at least one special character"
                        else -> ""
                    }
                },
                label = { Text("Password") },
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                        Icon(
                            painter = painterResource(if (isPasswordVisible) R.drawable.visibility else R.drawable.visibility_off),
                            contentDescription = if (isPasswordVisible) "Hide password" else "Show password",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                isError = passwordError.isNotEmpty(),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.Black,
                    unfocusedLabelColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    unfocusedSupportingTextColor = Color.Black,
                    focusedBorderColor = Color.Black,
                    focusedLabelColor = Color.Black,
                    focusedTextColor = Color.Black,
                    focusedSupportingTextColor = Color.Black,
                    errorBorderColor = Color.Red,
                    errorLabelColor = Color.Red,
                    errorTextColor = Color.Red,
                )
            )

            if (passwordError.isNotEmpty()) {
                Text(
                    text = passwordError,
                    color = Color.Red,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                )
            }

            // Show Confirm Password field only if password is changed
            if (isPasswordChanged) {
                Spacer(modifier = Modifier.height(16.dp))

                // Confirm Password Field
                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { newConfirmPassword ->
                        confirmPassword = newConfirmPassword
                        confirmPasswordError = if (newConfirmPassword != password) {
                            "Passwords do not match"
                        } else {
                            ""
                        }
                    },
                    label = { Text("Confirm Password") },
                    visualTransformation = if (isConfirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { isConfirmPasswordVisible = !isConfirmPasswordVisible }) {
                            Icon(
                                painter = painterResource(if (isConfirmPasswordVisible) R.drawable.visibility else R.drawable.visibility_off),
                                contentDescription = if (isConfirmPasswordVisible) "Hide password" else "Show password",
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    },
                    isError = confirmPasswordError.isNotEmpty(),
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color.Black,
                        unfocusedLabelColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        unfocusedSupportingTextColor = Color.Black,
                        focusedBorderColor = Color.Black,
                        focusedLabelColor = Color.Black,
                        focusedTextColor = Color.Black,
                        focusedSupportingTextColor = Color.Black,
                        errorBorderColor = Color.Red,
                        errorLabelColor = Color.Red,
                        errorTextColor = Color.Red,
                    )
                )

                if (confirmPasswordError.isNotEmpty()) {
                    Text(
                        text = confirmPasswordError,
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Email Field
            OutlinedTextField(
                value = email,
                onValueChange = { newEmail ->
                    email = newEmail
                    emailError = if (android.util.Patterns.EMAIL_ADDRESS.matcher(newEmail).matches()) {
                        ""
                    } else {
                        "Invalid email format"
                    }
                },
                label = { Text("Email") },
                isError = emailError.isNotEmpty(),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.Black,
                    unfocusedLabelColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    unfocusedSupportingTextColor = Color.Black,
                    focusedBorderColor = Color.Black,
                    focusedLabelColor = Color.Black,
                    focusedTextColor = Color.Black,
                    focusedSupportingTextColor = Color.Black,
                    errorBorderColor = Color.Red,
                    errorLabelColor = Color.Red,
                    errorTextColor = Color.Red,
                )
            )

            if (emailError.isNotEmpty()) {
                Text(
                    text = emailError,
                    color = Color.Red,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                )
            }
            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = {
                    navController.navigate("landing_screen")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Back")
            }

            // Save Button
            Button(
                onClick = {
                    // Save the updated profile
                    MovieRaterApplication.instance.userProfile = UserProfile(
                        userName = username,
                        password = password,
                        email = email
                    )
                    // Navigate to the Landing Screen
                    navController.navigate("landing_screen")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Changes")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(navController = rememberNavController())
}
