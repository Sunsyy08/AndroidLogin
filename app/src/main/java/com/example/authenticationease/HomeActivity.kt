package com.example.authenticationease

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.example.authenticationease.ui.theme.AuthenticationEaseTheme

class HomeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AuthenticationEaseTheme {
                MainScreenWithBottomBar()
            }
        }
    }

    // 카메라 시작 함수는 Composable 내에서 LifecycleOwner, Context를 받아 실행하도록 변경
    companion object {
        fun startCamera(
            lifecycleOwner: LifecycleOwner,
            previewView: PreviewView,
            context: android.content.Context
        ) {
            val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
            cameraProviderFuture.addListener({
                val cameraProvider = cameraProviderFuture.get()
                val preview = Preview.Builder().build().also {
                    it.setSurfaceProvider(previewView.surfaceProvider)
                }
                val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

                try {
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        lifecycleOwner,
                        cameraSelector,
                        preview
                    )
                } catch (exc: Exception) {
                    exc.printStackTrace()
                }
            }, ContextCompat.getMainExecutor(context))
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
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            when (selectedTab) {
                BottomTab.Home -> HomeScreen()
                BottomTab.Map -> Text("지도 화면", modifier = Modifier.fillMaxSize(), style = MaterialTheme.typography.headlineMedium)
                BottomTab.Chat -> Text("채팅 화면", modifier = Modifier.fillMaxSize(), style = MaterialTheme.typography.headlineMedium)
                BottomTab.Notifications -> Text("알림 화면", modifier = Modifier.fillMaxSize(), style = MaterialTheme.typography.headlineMedium)
                BottomTab.Profile -> Text("프로필 화면", modifier = Modifier.fillMaxSize(), style = MaterialTheme.typography.headlineMedium)
            }
        }
    }
}

@SuppressLint("DiscouragedApi")
@Composable
fun HomeScreen() {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val profileRes = context.resources.getIdentifier("profile", "drawable", context.packageName)
    val profileImage = painterResource(id = if (profileRes != 0) profileRes else android.R.drawable.sym_def_app_icon)

    val userName = "홍길동"
    val userRegion = "서울특별시 은평구"
    val userGender = "남성"
    val userAge = 75

    var showCamera by remember { mutableStateOf(false) }
    var permissionGranted by remember { mutableStateOf(false) }

    // previewView는 Composable 내에서 remember로 관리
    val previewView = remember {
        PreviewView(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }
    }

    // 권한 요청 launcher
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        permissionGranted = granted
        if (granted) {
            showCamera = true
        }
    }

    // 권한 체크 및 요청 함수
    fun checkPermissionAndShowCamera() {
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) -> {
                permissionGranted = true
                showCamera = true
            }
            else -> {
                permissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column {
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

            if (showCamera && permissionGranted) {
                CameraPreview(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(3f / 4f),
                    previewView = previewView
                )

                // 카메라 시작은 권한이 있을 때만 실행
                LaunchedEffect(previewView, lifecycleOwner) {
                    HomeActivity.startCamera(lifecycleOwner, previewView, context)
                }
            }
        }

        FloatingActionButton(
            onClick = { checkPermissionAndShowCamera() },
            containerColor = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp)
        ) {
            Icon(imageVector = Icons.Default.CameraAlt, contentDescription = "카메라")
        }
    }
}

@Composable
fun CameraPreview(
    modifier: Modifier = Modifier,
    previewView: PreviewView
) {
    AndroidView(
        factory = { previewView },
        modifier = modifier
    )
}

