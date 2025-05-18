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
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.authenticationease.ui.theme.AuthenticationEaseTheme
import androidx.compose.ui.graphics.vector.ImageVector


class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AuthenticationEaseTheme {
                MainScreenWithBottomBar()
            }
        }
    }
}

enum class BottomTab(val icon: ImageVector, val label: String) {
    Home(Icons.Default.Home, "홈"),
    Map(Icons.Default.Map, "지도"),
    Chat(Icons.Default.Chat, "채팅"),
    Notifications(Icons.Default.Notifications, "알림"),
    Profile(Icons.Default.Person, "프로필")
}

@Composable
fun BottomBar(
    selectedTab: BottomTab,
    onTabSelected: (BottomTab) -> Unit
) {
    NavigationBar {
        BottomTab.values().forEach { tab ->
            NavigationBarItem(
                icon = { Icon(tab.icon, contentDescription = tab.label) },
                label = { Text(tab.label) },
                selected = selectedTab == tab,
                onClick = { onTabSelected(tab) }
            )
        }
    }
}

@Composable
fun MainScreenWithBottomBar() {
    var selectedTab by remember { mutableStateOf(BottomTab.Home) }

    Scaffold(
        bottomBar = {
            BottomBar(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
        ) {
            when (selectedTab) {
                BottomTab.Home -> HomeScreen()
                BottomTab.Map -> Text("지도 화면")
                BottomTab.Chat -> Text("채팅 화면")
                BottomTab.Notifications -> Text("알림 화면")
                BottomTab.Profile -> Text("프로필 화면")
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

    // 사용자 정보
    val userName = "홍길동"
    val userRegion = "서울특별시 은평구"
    val userGender = "남성"
    val userAge = 75

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.weight(1f),
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = userName,
                color = Color(0xff324F5E),
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = "$userRegion · $userGender · ${userAge}세",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
        Image(
            painter = profileImage,
            contentDescription = "프로필",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .clickable {
                    // 프로필 클릭 시 행동
                }
        )
    }
}
