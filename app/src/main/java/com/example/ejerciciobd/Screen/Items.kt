package com.example.ejerciciobd.Screen

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.text.font.FontWeight
import com.example.ejerciciobd.Model.User
import com.example.ejerciciobd.Repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun UserApp(userRepository: UserRepository) {
    // Usamos rememberSaveable para preservar el estado durante la rotación
    var nombre by rememberSaveable { mutableStateOf("") }
    var apellido by rememberSaveable { mutableStateOf("") }
    var edad by rememberSaveable { mutableStateOf("") }
    var idToEdit by rememberSaveable { mutableStateOf("") }
    var idToDelete by rememberSaveable { mutableStateOf("") }
    var isEditing by rememberSaveable { mutableStateOf(false) }
    var users by rememberSaveable { mutableStateOf(listOf<User>()) }

    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 16.dp), // Márgenes laterales aumentados
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            // Título
            Text(
                text = "Gestión de Usuarios",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 24.dp)
            )
        }

        item {
            // Campo para el ID de edición, solo visible en modo edición
            if (isEditing) {
                TextField(
                    value = idToEdit,
                    onValueChange = { idToEdit = it },
                    label = { Text("Ingrese ID del usuario a editar") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp) // Espaciado vertical
                )
            }
        }

        item {
            // Campos para Nombre, Apellido y Edad
            TextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp) // Espaciado vertical
            )

            TextField(
                value = apellido,
                onValueChange = { apellido = it },
                label = { Text("Apellido") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp) // Espaciado vertical
            )

            TextField(
                value = edad,
                onValueChange = { edad = it },
                label = { Text("Edad") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp) // Espaciado vertical
            )
        }

        item {
            // Botón para registrar o editar el usuario
            Button(
                onClick = {
                    val user = User(
                        nombre = nombre,
                        apellido = apellido,
                        edad = edad.toIntOrNull() ?: 0
                    )
                    scope.launch {
                        if (isEditing) {
                            val id = idToEdit.toIntOrNull()
                            if (id != null) {
                                val updatedCount = withContext(Dispatchers.IO) {
                                    userRepository.updateUser(id, nombre, apellido, edad.toInt())
                                }
                                if (updatedCount > 0) {
                                    Toast.makeText(context, "Usuario actualizado", Toast.LENGTH_SHORT).show()
                                } else {
                                    Toast.makeText(context, "Usuario no encontrado", Toast.LENGTH_SHORT).show()
                                }
                            }
                            isEditing = false
                        } else {
                            withContext(Dispatchers.IO) {
                                userRepository.insert(user)
                            }
                            Toast.makeText(context, "Usuario registrado", Toast.LENGTH_SHORT).show()
                        }
                        // Limpiar campos
                        nombre = ""
                        apellido = ""
                        edad = ""
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp) // Espaciado vertical
            ) {
                Text(if (isEditing) "Guardar cambios" else "Registrar")
            }
        }

        item {
            // Botón para listar usuarios
            Button(
                onClick = {
                    scope.launch {
                        users = withContext(Dispatchers.IO) {
                            userRepository.getAllUsers()
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp) // Espaciado vertical
            ) {
                Text("Listar Usuarios")
            }
        }

        item {
            // Campo y botón para eliminar usuario
            TextField(
                value = idToDelete,
                onValueChange = { idToDelete = it },
                label = { Text("ID del usuario a eliminar") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp) // Espaciado vertical
            )

            Button(
                onClick = {
                    val id = idToDelete.toIntOrNull()
                    if (id != null) {
                        scope.launch {
                            val deletedCount = withContext(Dispatchers.IO) {
                                userRepository.deleteById(id)
                            }
                            if (deletedCount > 0) {
                                Toast.makeText(context, "Usuario eliminado", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(context, "Usuario no encontrado", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        Toast.makeText(context, "ID no válido", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp) // Espaciado vertical
            ) {
                Text("Eliminar Usuario")
            }
        }

        // Mostrar lista de usuarios
        items(users) { user ->
            Text("${user.id}: ${user.nombre} ${user.apellido}, Edad: ${user.edad}")
            Spacer(modifier = Modifier.height(4.dp))
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
            // Botón para activar el modo edición
            Button(
                onClick = { isEditing = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp) // Espaciado vertical
            ) {
                Text("Editar Usuario")
            }
        }
    }
}
