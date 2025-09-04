package com.you.veronica.overlay
import android.app.*; import android.content.*; import android.graphics.PixelFormat; import android.os.Build; import android.os.IBinder
import android.view.*; import android.widget.ImageButton; import android.widget.TextView; import com.you.veronica.R
class VeronicaOverlayService : Service() {
  private lateinit var wm: WindowManager; private var popup: View? = null; private val channelId = "veronica_channel"
  override fun onCreate() { super.onCreate(); wm = getSystemService(WINDOW_SERVICE) as WindowManager; createChannel(); startForeground(1, notif("Veronica is standing by")) }
  override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int { if (intent?.action=="SHOW") showPopup(); if (intent?.action=="HIDE") hidePopup(); return START_STICKY }
  private fun showPopup() {
    if (popup!=null) return
    val type = if (Build.VERSION.SDK_INT>=26) WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY else WindowManager.LayoutParams.TYPE_PHONE
    val params = WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT,type,WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,PixelFormat.TRANSLUCENT).apply { gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL; y=120 }
    popup = LayoutInflater.from(this).inflate(R.layout.view_popup,null).apply {
      findViewById<TextView>(R.id.tvText).text = "Good day, Sir. Veronica at your service."
      findViewById<ImageButton>(R.id.btnClose).setOnClickListener{ hidePopup() }
      setOnClickListener{ hidePopup() }
    }; wm.addView(popup,params)
  }
  private fun hidePopup(){ popup?.let{ wm.removeView(it)}; popup=null }
  private fun createChannel(){ if (Build.VERSION.SDK_INT>=26){ val nm=getSystemService(NotificationManager::class.java); nm.createNotificationChannel(NotificationChannel(channelId,"Veronica",NotificationManager.IMPORTANCE_MIN)) } }
  private fun notif(text:String):Notification{ val b=if(Build.VERSION.SDK_INT>=26) Notification.Builder(this,channelId) else Notification.Builder(this); return b.setContentTitle("Veronica").setContentText(text).setSmallIcon(android.R.drawable.star_on).build() }
  override fun onBind(intent: Intent?): IBinder? = null
}
