package dcwiek.noisemeasurmentapp.service

import android.content.Context
import android.media.RingtoneManager
import android.net.Uri
import android.os.VibrationEffect
import android.os.Vibrator

class NotificationService(val context: Context) {

    fun vibrateAndPlaySound(vibrator: Vibrator) {
        vibrator.vibrate(VibrationEffect.createOneShot(600, VibrationEffect.DEFAULT_AMPLITUDE))
        val notification: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val r = RingtoneManager.getRingtone(context, notification)
        r.play()
    }
}