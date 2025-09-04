package com.you.veronica
import android.content.Intent; import android.net.Uri; import android.os.Build; import android.os.Bundle; import android.provider.Settings
import androidx.activity.ComponentActivity; import androidx.activity.enableEdgeToEdge
import com.you.veronica.overlay.VeronicaOverlayService
class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState); enableEdgeToEdge()
    if (!Settings.canDrawOverlays(this)) {
      val i = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName")); startActivity(i)
    }
    if (Build.VERSION.SDK_INT >= 33) requestPermissions(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 0)
    val svc = Intent(this, VeronicaOverlayService::class.java).setAction("INIT")
    if (Build.VERSION.SDK_INT >= 26) startForegroundService(svc) else startService(svc)
    finish()
  }
}
