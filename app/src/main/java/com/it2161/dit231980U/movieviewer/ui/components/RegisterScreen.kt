package com.it2161.dit231980U.movieviewer.ui.components

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
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
fun RegisterUserScreen(
    navController: NavController,
    onRegisterClick: () -> Unit = {}
) {
    val context = LocalContext.current

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    var usernameError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var confirmPasswordError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }

    val passwordRegex = Regex("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@\$!%*#?&])[A-Za-z\\d@\$!%*#?&]{8,}$")
    val emailRegex = android.util.Patterns.EMAIL_ADDRESS

    var isPasswordVisible by remember { mutableStateOf(false) }
    var isConfirmPasswordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Sign Up",
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = "Create an account to continue!",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )

        // Username field
        OutlinedTextField(
            value = username,
            onValueChange = {
                username = it
                usernameError = when {
                    username.isEmpty() -> "Username is required"
                    username.length < 3 -> "Username must be at least 3 characters"
                    username.length > 30 -> "Username cannot exceed 30 characters"
                    else -> null
                }
            },
            label = { Text("Username") },
            isError = usernameError != null,
            supportingText = { Text(usernameError ?: "Enter your username") },
            modifier = Modifier.fillMaxWidth()
        )

        // Password field
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                passwordError = when {
                    password.isEmpty() -> "Password is required"
                    !password.matches(passwordRegex) -> "Password must be at least 8 characters, with one number, one special character, and one letter"
                    else -> null
                }
            },
            label = { Text("Password") },
            isError = passwordError != null,
            supportingText = { Text(passwordError ?: "Enter your password") },
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    Icon(
                        painterResource(id = if (isPasswordVisible) R.drawable.visibility else R.drawable.visibility_off),
                        contentDescription = if (isPasswordVisible) "Hide password" else "Show password",
                        modifier = Modifier.size(24.dp)
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        // Confirm Password field
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = {
                confirmPassword = it
                confirmPasswordError = when {
                    confirmPassword.isEmpty() -> "Confirm Password is required"
                    confirmPassword != password -> "Passwords do not match"
                    else -> null
                }
            },
            label = { Text("Confirm Password") },
            isError = confirmPasswordError != null,
            supportingText = { Text(confirmPasswordError ?: "Re-enter your password") },
            visualTransformation = if (isConfirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { isConfirmPasswordVisible = !isConfirmPasswordVisible }) {
                    Icon(
                        painterResource(id = if (isConfirmPasswordVisible) R.drawable.visibility else R.drawable.visibility_off),
                        contentDescription = if (isConfirmPasswordVisible) "Hide password" else "Show password",
                        modifier = Modifier.size(24.dp)
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        // Email field
        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                emailError = when {
                    email.isEmpty() -> "Email is required"
                    !emailRegex.matcher(email).matches() -> "Enter a valid email address"
                    else -> null
                }
            },
            label = { Text("Email") },
            isError = emailError != null,
            supportingText = { Text(emailError ?: "Enter your email address") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )

        // Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = { navController.popBackStack() }) {
                Text("Cancel")
            }
            Button(
                onClick = {
                    if (usernameError == null && passwordError == null && confirmPasswordError == null && emailError == null) {
                        // Save profile using MovieRaterApplication
                        val app = MovieRaterApplication.instance
                        app.userProfile = UserProfile(
                            userName = username,
                            password = password,
                            email = email
                        )
                        onRegisterClick()
                        Toast.makeText(context, "Registration successful", Toast.LENGTH_SHORT).show()
                        navController.popBackStack()
                    } else {
                        Toast.makeText(context, "Please fix errors before submitting", Toast.LENGTH_SHORT).show()
                    }
                }
            ) {
                Text("Register")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterUserScreenPreview() {
    RegisterUserScreen(navController = rememberNavController())
}
