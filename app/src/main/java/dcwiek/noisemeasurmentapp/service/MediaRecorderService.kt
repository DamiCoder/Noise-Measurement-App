package dcwiek.noisemeasurmentapp.service

import android.content.Context
import android.media.MediaRecorder
import android.util.Log
import dcwiek.noisemeasurmentapp.view.constants.ProbesConstants.Companion.CUSTOM_PROBE_NAME
import dcwiek.noisemeasurmentapp.view.constants.ProbesConstants.Companion.PROBES_FOLDER
import java.io.File


class MediaRecorderService {


    companion object {
        private lateinit var mediaRecorder: MediaRecorder
        private var state = false
        private lateinit var context: Context
        private const val CLASS_NAME : String = "MediaRecorderService.class"

        fun initializeMediaRecorder(context: Context): MediaRecorder {

            val folderPath = context.getExternalFilesDir(null)?.absolutePath + "/" + PROBES_FOLDER
            val output = folderPath + CUSTOM_PROBE_NAME

            val probesDirectory = File(folderPath)
            if(!probesDirectory.exists()) {
                probesDirectory.mkdirs()
            }

            val recordedProbe = File(output)
            if(recordedProbe.exists()){
                recordedProbe.delete()
            }
            Log.i(CLASS_NAME, "Output file location: $output")

            this.mediaRecorder = MediaRecorder()
            this.context = context

            this.mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC)
            this.mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            this.mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            this.mediaRecorder.setAudioChannels(2)
            this.mediaRecorder.setMaxDuration(20000)
            this.mediaRecorder.setAudioEncodingBitRate(256*1024)
            this.mediaRecorder.setAudioSamplingRate(48*1024)
            this.mediaRecorder.setOutputFile(output)
            return mediaRecorder
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
    }


}