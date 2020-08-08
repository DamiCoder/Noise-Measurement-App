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
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.InputStream
import java.security.KeyStore
import java.security.SecureRandom
import java.util.concurrent.TimeUnit
import javax.net.ssl.KeyManagerFactory
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager


class NoiseMeasurementServerApi(private val context: Context) {

    companion object {
        const val LOGIN_RELATIVE_URL = "/public/api/user/login"
        const val REGISTER_RELATIVE_URL = "/public/api/user/register"
        const val PLACES_RETRIEVAL_RELATIVE_URL = "/api/place/retrieveAll"
        const val REGULATIONS_RETRIEVAL_RELATIVE_URL = "/api/regulation/retrieveAll"
        const val STANDARDS_RETRIEVAL_RELATIVE_URL = "/api/standard/retrieveAll"
        const val PROBES_RETRIEVAL_RELATIVE_URL = "/api/probe/retrieveAll"
        const val PROBE_UPLOAD_RELATIVE_URL = "/api/probe/upload"

        val JSON = "application/json; charset=utf-8".toMediaTypeOrNull()

        val TAG = NoiseMeasurementServerApi::class.java.simpleName
        private lateinit var loggedUserName: String
        private lateinit var loggedUserPassword: String
    }

    private val client: OkHttpClient

    init {
        val sslContext = SSLContext.getInstance("SSL")
        val trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
        val keyStore: KeyStore = readKeystore()
        trustManagerFactory.init(keyStore)
        val keyManagerFactory =
            KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm())
        val trustManagers = trustManagerFactory.trustManagers
        val trustManager = trustManagers[0] as X509TrustManager
        keyManagerFactory.init(keyStore, "secret".toCharArray())
        sslContext.init(keyManagerFactory.keyManagers, arrayOf(trustManager), SecureRandom())

        val cookieJar: ClearableCookieJar = PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(context))

        client = OkHttpClient()
            .newBuilder()
            .cookieJar(cookieJar)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .sslSocketFactory(sslContext.socketFactory, trustManager)
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
        loggedUserName = username
        loggedUserPassword = password
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
//            .addHeader("Authorization", Credentials.basic(loggedUserName, loggedUserPassword))
            .build()
    }

    fun prepareRegulationsRetrievalRequest(): Request {
        return Request.Builder()
            .url(EndpointConstants.NOISE_MEASUREMENT_SERVER_URL + REGULATIONS_RETRIEVAL_RELATIVE_URL)
//            .addHeader("Authorization", Credentials.basic(loggedUserName, loggedUserPassword))
            .build()
    }

    fun prepareStandardsRetrievalRequest(): Request {
        return Request.Builder()
            .url(EndpointConstants.NOISE_MEASUREMENT_SERVER_URL + STANDARDS_RETRIEVAL_RELATIVE_URL)
//            .addHeader("Authorization", Credentials.basic(loggedUserName, loggedUserPassword))
            .build()
    }

    fun prepareProbesRetrievalRequest(): Request {
        return Request.Builder()
            .url(EndpointConstants.NOISE_MEASUREMENT_SERVER_URL + PROBES_RETRIEVAL_RELATIVE_URL)
//            .addHeader("Authorization", Credentials.basic(loggedUserName, loggedUserPassword))
            .addHeader("Content-Type", "application/json")
            .post(Gson().toJson(ProbeRetrievalForm(null, null)).toRequestBody())
            .build()
    }

    fun prepareProbeCreationRequest(location: String, place: Place, regulation: Regulation, result: Int,
                                    standardIds: List<Int>, comment: String, userRating: Int): Request {

        val probeCreationForm = ProbeCreationForm(location, place.id, regulation.id, result, standardIds,
            comment, userRating)

        val body = Gson().toJson(probeCreationForm)
        Log.v(TAG, "probeCreationForm request body: ${body})")

        return Request.Builder()
            .url(EndpointConstants.NOISE_MEASUREMENT_SERVER_URL + PROBE_UPLOAD_RELATIVE_URL)
//            .addHeader("Authorization", Credentials.basic(loggedUserName, loggedUserPassword, StandardCharsets.UTF_8))
            .addHeader("Content-Type", "application/json")
            .post(body.toRequestBody())
            .build()
    }

    private class UserRegistrationForm(val username: String, val password: String)

    private class ProbeRetrievalForm(val number: Int?, val pageSize: Int?)

    private class ProbeCreationForm(val location: String, val placeId: Int, val regulationId: Int,
                                    val result: Int, val standardIds: List<Int>, val comment: String,
                                    val userRating: Int)
}