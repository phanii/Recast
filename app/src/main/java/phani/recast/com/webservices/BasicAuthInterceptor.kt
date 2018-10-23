package phani.recast.com.webservices

import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException


class BasicAuthInterceptor(user: String, password: String) : Interceptor {
    private val credentials: String = Credentials.basic(user, password)

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val authenticatedRequest = request.newBuilder()
                .header("Authorization", credentials)
                .header("Accept-Language", "en-US")
                .header("nep-organization", "/ams/catalog-storeline")
                .header("Content-Type", "application/json")
                .header("nep-application-key", "8a808f295f2545b5015fc94bec2e040e")
                .build()
        return chain.proceed(authenticatedRequest)
    }
}