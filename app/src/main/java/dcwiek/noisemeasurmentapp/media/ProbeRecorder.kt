package dcwiek.noisemeasurmentapp.media

import android.content.Context
import android.media.MediaRecorder
import android.util.Log
import dcwiek.noisemeasurmentapp.ui.constants.ProbesConstants.Companion.CUSTOM_PROBE_NAME
import dcwiek.noisemeasurmentapp.ui.constants.ProbesConstants.Companion.PROBES_FOLDER
import java.io.File

class ProbeRecorder constructor(val context: Context) {

    private lateinit var mediaRecorder: MediaRecorder
    private var state = false
    private val TAG: String = ProbeRecorder::class.java.name

    init {
        initializeProbeRecorder(context)
    }

    fun initializeProbeRecorder(context: Context) : ProbeRecorder {
        val folderPath = getProbesFolder(context)
        val output = folderPath + CUSTOM_PROBE_NAME

        val probesDirectory = File(folderPath)
        if (!probesDirectory.exists()) {
            probesDirectory.mkdirs()
        }

        val recordedProbe = File(output)
        if (recordedProbe.exists()) {
            recordedProbe.delete()
        }

        Log.i(TAG, "Output file location: $output")

        this.mediaRecorder = MediaRecorder()

        this.mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC)
        this.mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
        this.mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
        this.mediaRecorder.setAudioChannels(2)
        this.mediaRecorder.setMaxDuration(20000)
        this.mediaRecorder.setAudioEncodingBitRate(256 * 1024)
        this.mediaRecorder.setAudioSamplingRate(48 * 1024)
        this.mediaRecorder.setOutputFile(output)

        Log.i(TAG, "initalizeProbeRecorder called")

        return this
    }

    private fun getProbesFolder(context: Context): String {
        return context.getExternalFilesDir(null)?.absolutePath + "/" + PROBES_FOLDER
    }


    fun startRecording() {
        this.mediaRecorder.prepare()
        this.mediaRecorder.start()
        this.state = true
    }

    fun stopRecording() {
        if (state) {
            this.mediaRecorder.stop()
            this.mediaRecorder.release()
            this.state = false
        }
    }

    fun changeOutputFileName(fileName: String) {
        mediaRecorder.setOutputFile(getProbesFolder(context) + fileName)
    }


}