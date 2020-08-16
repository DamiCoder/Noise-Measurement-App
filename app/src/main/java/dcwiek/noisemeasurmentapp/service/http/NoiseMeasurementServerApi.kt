package dcwiek.noisemeasurmentapp.service.http

import android.content.Context
import android.util.Log
import com.franmontiel.persistentcookiejar.ClearableCookieJar
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.google.gson.Gson
import dcwiek.noisemeasurmentapp.R
import dcwiek.noisemeasurmentapp.domain.model.Place
import dcwiek.noisemeasurmentapp.domain.model.Regulation
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.InputStream
import java.security.KeyStore
import java.util.concurrent.TimeUnit


class NoiseMeasurementServerApi(private val context: Context) {

    companion object {
        const val LOGIN_RELATIVE_URL = "/public/api/user/login"
        const val REGISTER_RELATIVE_URL = "/public/api/user/register"
        const val PLACES_RETRIEVAL_RELATIVE_URL = "/api/place/retrieveAll"
        const val REGULATIONS_RETRIEVAL_RELATIVE_URL = "/api/regulation/retrieveAll"
        const val STANDARDS_RETRIEVAL_RELATIVE_URL = "/api/standard/retrieveAll"
        const val PROBES_RETRIEVAL_RELATIVE_URL = "/api/probe/retrieveAll"
        const val PROBE_PROCESSING_RELATIVE_URL = "/api/probe/process/dbLevel"
        const val PROBE_UPLOAD_RELATIVE_URL = "/api/probe/upload"

        val JSON = "application/json; charset=utf-8".toMediaTypeOrNull()

        val TAG = NoiseMeasurementServerApi::class.java.simpleName
    }

    private val client: OkHttpClient

    init {
        val cookieJar: ClearableCookieJar = PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(context))

        client = OkHttpClient()
            .newBuilder()
            .cookieJar(cookieJar)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    private fun readKeystore(): KeyStore {
        val ks = KeyStore.getInstance("PKCS12")
        val password: CharArray = "secret".toCharArray()

        var fis: InputStream? = null
        try {
            fis = context.resources.openRawResource(R.raw.noisemeasurementserver)
            ks.load(fis, password)
        } finally {
            fis?.close()
        }
        return ks
    }

    fun execute(request: Request): Response {
        return client.newCall(request).execute()
    }

    fun prepareLoginRequest(username: String, password: String): Request {
        val builder = FormBody.Builder()
        val formBody = builder.build()
        return Request.Builder()
            .url(EndpointConstants.NOISE_MEASUREMENT_SERVER_URL + LOGIN_RELATIVE_URL)
            .addHeader("Authorization", Credentials.basic(username, password))
            .post(formBody)
            .build()
    }

    fun prepareRegisterRequest(username: String, password: String): Request {
        return Request.Builder()
            .url(EndpointConstants.NOISE_MEASUREMENT_SERVER_URL + REGISTER_RELATIVE_URL)
            .post(Gson().toJson(UserRegistrationForm(username, password)).toRequestBody(JSON))
            .build()
    }

    fun preparePlacesRetrievalRequest(): Request {
        return Request.Builder()
            .url(EndpointConstants.NOISE_MEASUREMENT_SERVER_URL + PLACES_RETRIEVAL_RELATIVE_URL)
            .build()
    }

    fun prepareRegulationsRetrievalRequest(): Request {
        return Request.Builder()
            .url(EndpointConstants.NOISE_MEASUREMENT_SERVER_URL + REGULATIONS_RETRIEVAL_RELATIVE_URL)
            .build()
    }

    fun prepareStandardsRetrievalRequest(): Request {
        return Request.Builder()
            .url(EndpointConstants.NOISE_MEASUREMENT_SERVER_URL + STANDARDS_RETRIEVAL_RELATIVE_URL)
            .build()
    }

    fun prepareProbesRetrievalRequest(): Request {
        return Request.Builder()
            .url(EndpointConstants.NOISE_MEASUREMENT_SERVER_URL + PROBES_RETRIEVAL_RELATIVE_URL)
            .addHeader("Content-Type", "application/json")
            .post(Gson().toJson(ProbeRetrievalForm(null, null)).toRequestBody())
            .build()
    }

    fun prepareProbeProcessingRequest(probe: File, amplitudeReferenceValue: Double): Request {

        val formBody: RequestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("probe", probe.name, probe.asRequestBody("audio/mpeg".toMediaTypeOrNull()))
            .addFormDataPart("referenceValue", amplitudeReferenceValue.toString())
            .build()

        return Request.Builder()
            .url(EndpointConstants.NOISE_MEASUREMENT_SERVER_URL + PROBE_PROCESSING_RELATIVE_URL)
            .addHeader("Content-Type", "multipart/form-data")
            .post(formBody)
            .build()
    }

    fun prepareProbeCreationRequest(location: String, place: Place, regulation: Regulation, result: Int,
                                    standardIds: List<Int>, comment: String, userRating: Int?): Request {

        val probeCreationForm = ProbeCreationForm(location, place.id, regulation.id, result, standardIds,
            comment, userRating)

        val body = Gson().toJson(probeCreationForm)
        Log.v(TAG, "probeCreationForm request body: ${body})")

        return Request.Builder()
            .url(EndpointConstants.NOISE_MEASUREMENT_SERVER_URL + PROBE_UPLOAD_RELATIVE_URL)
            .addHeader("Content-Type", "application/json")
            .post(body.toRequestBody())
            .build()
    }

    private class UserRegistrationForm(val username: String, val password: String)

    private class ProbeRetrievalForm(val number: Int?, val pageSize: Int?)

    private class ProbeCreationForm(val location: String, val placeId: Int, val regulationId: Int,
                                    val result: Int, val standardIds: List<Int>, val comment: String,
                                    val userRating: Int?)
}