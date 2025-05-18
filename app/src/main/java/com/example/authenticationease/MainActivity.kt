package com.example.authenticationease

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.authenticationease.ui.theme.AuthenticationEaseTheme
import kotlin.jvm.java

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AuthenticationEaseTheme {
                MainScreen(
                    onLoginClick = {
                        startActivity(Intent(this, LoginActivity::class.java))
                    },
                    onSignupClick = {
                        startActivity(Intent(this, SignupActivity::class.java))
                    }
                )
            }
        }
    }
}

@Composable
fun MainScreen(
    onLoginClick: () -> Unit,
    onSignupClick: () -> Unit
) {
    val image = painterResource(R.drawable.sky)
    val image1 = painterResource(R.drawable.profile)
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // 배경 이미지
        Image(
            painter = image,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(alpha = 0.5f) // 투명도 설정 (선택)
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
                Text("환영합니다!!!",
                    fontSize = 24.sp,
                    color = Color.Black )
                Spacer(modifier = Modifier.width(12.dp))

                Image(
                    painter = image1,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color.Cyan, CircleShape)
                )
            }
            Spacer(modifier = Modifier.height(24.dp))


            Button(
                onClick = onLoginClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2196F3),
                    contentColor = Color.White
                ),
                modifier = Modifier.width(300.dp)
            ) {
                Text("로그인")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onSignupClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2196F3),
                    contentColor = Color.White
                ),
                modifier = Modifier.width(300.dp)
            ) {
                Text("회원가입")
            }
        }
    }
}
