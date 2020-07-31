package dcwiek.noisemeasurmentapp.service.media

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

    companion object {
        private const val AUDIO_CHANNELS = 2
        const val RECORD_DURATION = 10000
        private const val AUDIO_ENCODING_BIT_RATE =  256 * 1024
        private const val AUDIO_SAMPLING_RATE =  48 * 1024
    }

    init {
        initializeProbeRecorder(context)
    }

    private fun initializeProbeRecorder(context: Context) : ProbeRecorder {
        Log.i(TAG, "ProbeRecorder.initalizeProbeRecorder called")
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
        this.mediaRecorder.setAudioChannels(AUDIO_CHANNELS)
        this.mediaRecorder.setMaxDuration(RECORD_DURATION)
        this.mediaRecorder.setAudioEncodingBitRate(AUDIO_ENCODING_BIT_RATE)
        this.mediaRecorder.setAudioSamplingRate(AUDIO_SAMPLING_RATE)
        this.mediaRecorder.setOutputFile(output)


        return this
    }

    fun changeOutputFileName(fileName: String) {
        mediaRecorder.setOutputFile(getProbesFolder(context) + fileName)
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

    private fun getProbesFolder(context: Context): String {
        return context.getExternalFilesDir(null)?.absolutePath + "/" + PROBES_FOLDER
    }

}