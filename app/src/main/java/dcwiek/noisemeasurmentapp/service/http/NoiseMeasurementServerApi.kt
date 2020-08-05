package dcwiek.noisemeasurmentapp.service.http

import android.content.Context
import com.google.gson.Gson
import dcwiek.noisemeasurmentapp.R
import dcwiek.noisemeasurmentapp.domain.DataStorage
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.InputStream
import java.nio.charset.StandardCharsets
import java.security.KeyStore
import java.security.SecureRandom
import javax.net.ssl.KeyManagerFactory
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager


class NoiseMeasurementServerApi(private val dataStorage: DataStorage,
    private val context: Context) {

    companion object {
        const val LOGIN_RELATIVE_URL = "/public/api/user/login"
        const val REGISTER_RELATIVE_URL = "/public/api/user/register"
        val JSON = "application/json; charset=utf-8".toMediaTypeOrNull()
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
        client = OkHttpClient().newBuilder().sslSocketFactory(sslContext.socketFactory, trustManager).build()
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
            .addHeader("Authorization", Credentials.basic(username, password, StandardCharsets.UTF_8))
            .post(formBody)
            .build()
    }

    fun prepareRegisterRequest(username: String, password: String): Request {
        return Request.Builder()
            .url(EndpointConstants.NOISE_MEASUREMENT_SERVER_URL + REGISTER_RELATIVE_URL)
//            .addHeader("Authorization", "Basic ${Credentials.basic(username, password, StandardCharsets.UTF_8)}")
            .post(Gson().toJson(UserRegistrationForm(username, password)).toRequestBody(JSON))
            .build()
    }

    private class UserRegistrationForm(val username: String, val password: String)
}