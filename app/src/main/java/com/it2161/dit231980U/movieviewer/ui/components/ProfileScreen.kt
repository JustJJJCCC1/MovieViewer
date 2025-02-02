package com.it2161.dit231980U.movieviewer.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.it2161.dit231980U.movieviewer.MovieRaterApplication
import com.it2161.dit231980U.movieviewer.R
import com.it2161.dit231980U.movieviewer.data.UserProfile

@Composable
fun ProfileScreen(navController: NavController) {
    val scrollState = rememberScrollState()

    var isPasswordVisible by remember { mutableStateOf(false) }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

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
    ) {

        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .background(Color.LightGray)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = { navController.navigate("landing_screen/Popular") }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back Icon",
                    modifier = Modifier.size(32.dp),
                    tint = Color.Black
                )
            }
            Text(
                text ="Profile",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.width(32.dp)) // Spacer for the alignment
        }

        Column(modifier = Modifier.fillMaxSize()) {
            // Username Field
            OutlinedTextField(
                value = username,
                onValueChange = {},
                label = { Text("Username") },
                enabled = false, // Disable editing
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp, start = 16.dp, end = 16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    disabledTextColor = Color.Black,
                    disabledBorderColor = Color.Black,
                    disabledLabelColor = Color.Black,
                    disabledLeadingIconColor = Color.Black,
                    disabledTrailingIconColor = Color.Black,
                    disabledPlaceholderColor = Color.Black
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Password Field
            OutlinedTextField(
                value = password,
                onValueChange = {},
                label = { Text("Password") },
                enabled = false, // Disable editing
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
                modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    disabledTextColor = Color.Black,
                    disabledBorderColor = Color.Black,
                    disabledLabelColor = Color.Black,
                    disabledLeadingIconColor = Color.Black,
                    disabledTrailingIconColor = Color.Black,
                    disabledPlaceholderColor = Color.Black
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Email Field
            OutlinedTextField(
                value = email,
                onValueChange = {},
                label = { Text("Email") },
                enabled = false, // Disable editing
                modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    disabledTextColor = Color.Black,
                    disabledBorderColor = Color.Black,
                    disabledLabelColor = Color.Black,
                    disabledLeadingIconColor = Color.Black,
                    disabledTrailingIconColor = Color.Black,
                    disabledPlaceholderColor = Color.Black
                )
            )

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(navController = rememberNavController())
}
