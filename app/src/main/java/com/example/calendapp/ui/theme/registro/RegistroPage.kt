package com.example.calendapp.ui.theme.registro

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calendapp.ui.theme.CalendappTheme
import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.calendapp.MainActivity

data class User(
    val username: String,
    val email: String,
    val password: String
) {
    override fun toString(): String {
        return "User(username='$username', email='$email')"
    }
}

class RegistroActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalendappTheme {
                RegistroPage()
            }
        }
    }

    @Composable
    fun RegistroPage() {
        var username by remember { mutableStateOf(TextFieldValue("")) }
        var email by remember { mutableStateOf(TextFieldValue("")) }
        var password by remember { mutableStateOf(TextFieldValue("")) }
        var confirmPassword by remember { mutableStateOf(TextFieldValue("")) }

        var usernameError by remember { mutableStateOf("") }
        var emailError by remember { mutableStateOf("") }
        var passwordError by remember { mutableStateOf("") }
        var confirmPasswordError by remember { mutableStateOf("") }

        val users = remember { mutableStateListOf<User>() }  // Inicializa la lista de usuarios
        val scope = rememberCoroutineScope()  // Englobamos la clase dentro del scope
        var showDialog by remember { mutableStateOf(false) }  // Estado para mostrar el Dialog

        //agregamos Trim para evitar espacios en blanco
        val isFormValid by remember {
            derivedStateOf {
                username.text.trim().isNotBlank() &&
                        email.text.trim().isNotBlank() &&
                        password.text.trim().isNotBlank() &&
                        confirmPassword.text.trim().isNotBlank() &&
                        password.text.trim() == confirmPassword.text.trim() &&
                        isValidEmail(email.text.trim())
            }
        }

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(30.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Título
                Text(
                    text = "R e g i s t r o   d e   u s u a r i o",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Username Input
                BasicTextField(
                    value = username,
                    onValueChange = {
                        username = it.copy(text = it.text.trim())
                        usernameError = if (username.text.isBlank()) "Nombre de Usuario no puede estar vacío" else ""
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                                .padding(16.dp)
                        ) {
                            if (username.text.isEmpty()) {
                                Text(
                                    text = "N o m b r e   d e   U s u a r i o",
                                    color = Color.Black
                                )
                            }
                            innerTextField()
                        }
                    },
                    textStyle = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp)
                )

                if (usernameError.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = usernameError,
                        color = Color.Red,
                        fontSize = 14.sp,
                        modifier = Modifier.align(Alignment.Start)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Email Input
                BasicTextField(
                    value = email,
                    onValueChange = {
                        email = it.copy(text = it.text.trim())
                        emailError = if (email.text.isBlank()) "El correo electrónico no puede estar vacío"
                        else if (!isValidEmail(email.text)) "Por favor ingresar un correo válido" else ""
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                                .padding(16.dp)
                        ) {
                            if (email.text.isEmpty()) {
                                Text(
                                    text = "C o r r e o   E l e c t r ó n i c o",
                                    color = Color.Black
                                )
                            }
                            innerTextField()
                        }
                    },
                    textStyle = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp)
                )

                if (emailError.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = emailError,
                        color = Color.Red,
                        fontSize = 14.sp,
                        modifier = Modifier.align(Alignment.Start)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Contraseña Input
                BasicTextField(
                    value = password,
                    onValueChange = {
                        password = it.copy(text = it.text.trim())
                        passwordError = if (password.text.isBlank()) "La contraseña no puede estar vacía" else ""
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation(),
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                                .padding(16.dp)
                        ) {
                            if (password.text.isEmpty()) {
                                Text(
                                    text = "C o n t r a s e ñ a",
                                    color = Color.Black
                                )
                            }
                            innerTextField()
                        }
                    },
                    textStyle = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp)
                )

                if (passwordError.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = passwordError,
                        color = Color.Red,
                        fontSize = 14.sp,
                        modifier = Modifier.align(Alignment.Start)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Confirmar Contraseña Input
                BasicTextField(
                    value = confirmPassword,
                    onValueChange = {
                        confirmPassword = it.copy(text = it.text.trim())
                        confirmPasswordError = if (confirmPassword.text.isBlank()) "Confirmar contraseña no puede estar vacío"
                        else if (confirmPassword.text != password.text) "Las contraseñas no coinciden" else ""
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation(),
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                                .padding(16.dp)
                        ) {
                            if (confirmPassword.text.isEmpty()) {
                                Text(
                                    text = "C o n f i r m a r   C o n t r a s e ñ a ",
                                    color = Color.Black
                                )
                            }
                            innerTextField()
                        }
                    },
                    textStyle = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp)
                )

                if (confirmPasswordError.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = confirmPasswordError,
                        color = Color.Red,
                        fontSize = 14.sp,
                        modifier = Modifier.align(Alignment.Start)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Register Button
                Button(
                    onClick = {
                        if (isFormValid && users.size < 5) {
                            val newUser = User(
                                username = username.text,
                                email = email.text,
                                password = password.text
                            )
                            users.add(newUser)

                            // Almacenamos la data del usuario
                            val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                            val editor = sharedPreferences.edit()
                            editor.putString("username_${newUser.email}", newUser.username)
                            editor.putString("password_${newUser.email}", newUser.password)
                            editor.apply()

                            // Log para ver cada usuario de la lista
                            users.forEach { user ->
                                Log.d("RegistroActivity", "Registered User - Username: ${user.username}, Email: ${user.email}")
                            }

                            // Mensaje de éxito y redirige a la página de login
                            showDialog = true
                            scope.launch {
                                delay(2000) // 2 seg de pausa
                                showDialog = false

                                // Redirige al Login Page
                                val intent = Intent(this@RegistroActivity, MainActivity::class.java)
                                startActivity(intent)
                                finish() // Cierra la página de registro
                            }
                        } else {
                            //limite para almacenar solo 5 usuarios
                            if (users.size >= 5) {
                                // Log cuando ya tenemos los 5 usuarios
                                Log.d("RegistroActivity", "Se alcanzo el limite de: 5 usuarios registrados")
                            }

                            if (username.text.isBlank()) usernameError = "El Nombre de Usuario no puede estar vacío"
                            if (email.text.isBlank()) emailError = "El Correo Electrónico no puede estar vacío"
                            if (!isValidEmail(email.text)) emailError = "Por favor ingresar un correo válido"
                            if (password.text.isBlank()) passwordError = "La Contraseña no puede estar vacía"
                            if (confirmPassword.text.isBlank()) confirmPasswordError = "Confirmar contraseña no puede estar vacío"
                            if (confirmPassword.text != password.text) confirmPasswordError = "Las Contraseñas no coinciden"
                        }
                    },
                    enabled = isFormValid && users.size < 5,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isFormValid) Color.Black else Color.Gray,
                        contentColor = Color.White
                    ),
                    modifier = Modifier.width(200.dp)
                ) {
                    Text(
                        text = "R e g i s t r a r",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                //volver
                Button(
                    onClick = {
                        finish() // Volver a la pantalla principal
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .width(200.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack, // Agregar la flecha de vuelta
                        contentDescription = "Volver",
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(8.dp)) // Espacio entre la flecha y el texto
                    Text(
                        text = "V o l v e r",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // Muestra mensaje de éxito en la creación de usuario
        if (showDialog) {
            CreacionUsuarioDialog {
                showDialog = false
            }
        }
    }

    @Composable
    fun CreacionUsuarioDialog(onDismiss: () -> Unit) {
        Dialog(
            onDismissRequest = { onDismiss() },
            properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
        ) {
            Surface(
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Usuario creado exitosamente!",
                        fontSize = 18.sp,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // El modal desparece después de un rato
                    LaunchedEffect(Unit) {
                        delay(2000) // 2 seg pausa
                        onDismiss()
                    }
                }
            }
        }
    }

    // Validación correo electrónico
    private fun isValidEmail(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return email.matches(emailPattern.toRegex())
    }
}
