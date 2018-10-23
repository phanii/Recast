package phani.recast.com.webservices

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ApiBuilder_ODSP {
    companion object {
        //val BASE_URL = "http://153.58.137.56:3000/"
        val BASE_URL = "https://nep-gateway.swenglabs.ncr.com/"

        fun create(): APIinterface {
            val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client1)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            return retrofit.create(APIinterface::class.java)
        }

        var client = OkHttpClient.Builder()
                .addInterceptor(BasicAuthInterceptor("admin", "admin@12345"))
                .build()
/*
        var client = OkHttpClient.Builder().addInterceptor { chain ->
            val newRequest = chain.request().newBuilder()
                    .addHeader("Authorization", "Token  $token")
                    .build()
            chain.proceed(newRequest)
        }.build()!!*/


        val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }
        val client1: OkHttpClient = OkHttpClient.Builder().apply {
            this.addInterceptor(interceptor)
            this.addInterceptor(BasicAuthInterceptor("admin", "admin@12345"))


        }.build()

    }

}

