package com.example.ejerciciobd.Screen

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.ejerciciobd.DAO.UserDao
import com.example.ejerciciobd.Model.User
import com.example.ejerciciobd.Repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun UserApp(userRepository: UserRepository) {
    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var edad by remember { mutableStateOf("") }
    var idToEdit by remember { mutableStateOf("") }
    var idToDelete by remember { mutableStateOf("") }
    var isEditing by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var users by remember { mutableStateOf(listOf<User>()) }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // TextField para el ID (cuando se quiera editar)
        if (isEditing) {
            TextField(
                value = idToEdit,
                onValueChange = { idToEdit = it },
                label = { Text("ID del usuario a editar") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        TextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = apellido,
            onValueChange = { apellido = it },
            label = { Text("Apellido") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = edad,
            onValueChange = { edad = it },
            label = { Text("Edad") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

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
            }
        ) {
            Text(if (isEditing) "Guardar cambios" else "Registrar")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para listar usuarios
        Button(
            onClick = {
                scope.launch {
                    users = withContext(Dispatchers.IO) {
                        userRepository.getAllUsers()
                    }
                }
            }
        ) {
            Text("Listar Usuarios")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para eliminar usuario
        TextField(
            value = idToDelete,
            onValueChange = { idToDelete = it },
            label = { Text("ID del usuario a eliminar") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

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
            }
        ) {
            Text("Eliminar Usuario")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Mostrar lista de usuarios
        Column {
            users.forEach { user ->
                Text("${user.id}: ${user.nombre} ${user.apellido}, Edad: ${user.edad}")
                Spacer(modifier = Modifier.height(4.dp))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para activar el modo edición
        Button(onClick = { isEditing = true }) {
            Text("Editar Usuario")
        }
    }
}
