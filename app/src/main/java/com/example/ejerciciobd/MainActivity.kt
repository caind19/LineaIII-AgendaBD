package com.example.ejerciciobd

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.ejerciciobd.DAO.UserDao
import com.example.ejerciciobd.Database.UserDatabase
import com.example.ejerciciobd.Repository.UserRepository
import com.example.ejerciciobd.Screen.UserApp
import com.example.ejerciciobd.ui.theme.EjercicioBDTheme

class MainActivity : ComponentActivity() {
    private lateinit var userDao :UserDao
    private lateinit var userRepository :UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = UserDatabase.getDatabase(applicationContext)
        userDao = db.userDao()
        userRepository = UserRepository(userDao)
        enableEdgeToEdge()
        setContent{
            UserApp(userRepository)
        }
    }
}

