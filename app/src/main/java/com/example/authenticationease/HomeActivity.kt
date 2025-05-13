// HomeActivity.kt
package com.example.authenticationease

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.authenticationease.ui.theme.AuthenticationEaseTheme

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AuthenticationEaseTheme {
                HomeScreen()
            }
        }
    }
}

@SuppressLint("DiscouragedApi")
@Composable
fun HomeScreen() {
    val context = LocalContext.current
    val profileRes = context.resources.getIdentifier("profile", "drawable", context.packageName)
    val profileImage = painterResource(id = if (profileRes != 0) profileRes else android.R.drawable.sym_def_app_icon)

    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(Icons.Default.Menu, contentDescription = "메뉴")
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("메뉴 1") },
                        onClick = { expanded = false }
                    )
                    DropdownMenuItem(
                        text = { Text("메뉴 2") },
                        onClick = { expanded = false }
                    )
                }
            }

            Image(
                painter = profileImage,
                contentDescription = "프로필",
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .clickable {
                        // 프로필 클릭 시 행동
                    }
            )
        }
    }
}

