package com.example.calendapp.ui.theme.recuperarpass

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.calendapp.ui.theme.CalendappTheme
import com.example.calendapp.R
import kotlinx.coroutines.delay
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.res.painterResource

class RecuperarPasswordActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalendappTheme {
                RecuperarPassPage()
            }
        }
    }

    @Composable
    fun RecuperarPassPage() {
        var email by remember { mutableStateOf(TextFieldValue("")) }
        var emailError by remember { mutableStateOf("") }
        var showDialog by remember { mutableStateOf(false) }

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
                    text = "R e c u p e r a r    C o n t r a s e ñ a",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Email Input
                BasicTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        emailError = "" // Limpia mensaje de error cuando el usuario escribe en el campo
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

                // Mensaje de error Email
                if (emailError.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = emailError,
                        color = Color.Red,
                        fontSize = 14.sp,
                        modifier = Modifier.align(Alignment.Start)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Botón Recuperar
                Button(
                    onClick = {
                        if (isValidEmail(email.text)) {
                            showDialog = true
                        } else {
                            emailError = "Por favor ingresar un correo válido"
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .width(200.dp)
                ) {
                    Text(
                        text = "R e c u p e r a r",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                //Botón volver
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

            // Muestra el modal
            if (showDialog) {
                RecuperarInstruccionesDialog(email.text) {
                    showDialog = false
                    finish() // Cierra el modal y se devuelve a la págiona principal
                }
            }
        }
    }

    // Validación formato correo
    private fun isValidEmail(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return email.matches(emailPattern.toRegex())
    }

    @Composable
    fun RecuperarInstruccionesDialog(email: String, onDismiss: () -> Unit) {
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
                        text = "Las instrucciones fueron enviadas al correo:",
                        fontSize = 18.sp,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = email,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // El modal desparece después de un rato
                    LaunchedEffect(Unit) {
                        delay(5000) // 5 seg de pausa
                        onDismiss()
                    }
                }
            }
        }
    }
}
