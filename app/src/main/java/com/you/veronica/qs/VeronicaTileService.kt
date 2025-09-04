package com.you.veronica.qs
import android.content.Intent; import android.service.quicksettings.TileService; import com.you.veronica.overlay.VeronicaOverlayService
class VeronicaTileService : TileService() { override fun onClick(){ startForegroundService(Intent(this,VeronicaOverlayService::class.java).setAction("SHOW")) } }
