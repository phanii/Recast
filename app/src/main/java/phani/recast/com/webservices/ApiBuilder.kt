package phani.recast.com.webservices

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import phani.recast.com.webservices.Utilities.Values.token
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiBuilder {
    companion object {
        val BASE_URL = "https://api.recast.ai/"
        fun create(): APIinterface {
            val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client1)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            return retrofit.create(APIinterface::class.java)
        }

        var client = OkHttpClient.Builder().addInterceptor { chain ->
            val newRequest = chain.request().newBuilder()
                    .addHeader("Authorization", "Token  $token")
                    .build()
            chain.proceed(newRequest)
        }.build()!!


        val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }
        val client1: OkHttpClient = OkHttpClient.Builder().apply {
            this.addInterceptor(interceptor)


        }.build()

    }

}

