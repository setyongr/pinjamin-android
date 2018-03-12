package com.setyongr.pinjamin.data

import android.util.Log
import com.setyongr.pinjamin.data.models.RequestModel
import com.setyongr.pinjamin.data.models.ResponseModel
import io.reactivex.Completable
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

interface PinjaminService {

    @POST("auth/jwt/create/")
    fun login(@Body req: RequestModel.Login): Observable<ResponseModel.Token>

    @POST("auth/users/create/")
    fun register(@Body req: RequestModel.Register): Observable<ResponseModel.Register>

    @GET("pinjaman/")
    fun getPinjaman(): Observable<List<ResponseModel.Pinjaman>>

    @GET("pinjaman/{id}/")
    fun getPinjamanById(@Path("id") id: Int): Observable<ResponseModel.Pinjaman>

    @GET("order/")
    fun getOrder(): Observable<List<ResponseModel.Order>>

    @GET("order/{id}/")
    fun getOrderById(@Path("id") id: Int): Observable<ResponseModel.Order>

    @POST("order/")
    fun placeOrder(@Body req: RequestModel.Order): Observable<ResponseModel.Order>

    @GET("ordertome/")
    fun getOrderToMe(): Observable<List<ResponseModel.Order>>


    @GET("ordertome/{id}/")
    fun getOrderToMeById(@Path("id") id: Int): Observable<ResponseModel.Order>

    @PATCH("ordertome/{id}/")
    fun updateOrderToMe(@Path("id") id: Int, @Body req: RequestModel.OrderToMe): Observable<ResponseModel.Order>

    @Multipart
    @POST("pinjaman/")
    fun savePinjaman(
            @Part("name") name: String,
            @Part("deskripsi") deskripsi: String,
            @Part part: MultipartBody.Part? = null): Observable<ResponseModel.Pinjaman>

    @GET("auth/me/")
    fun me(): Observable<ResponseModel.User>

    @PATCH("auth/me/")
    fun updateMe(@Body req: RequestModel.User): Observable<ResponseModel.User>

    @Multipart
    @PATCH("auth/me/")
    fun updateMeImage(@Part part: MultipartBody.Part): Observable<ResponseModel.User>

    @GET("me/pinjaman/")
    fun getMyPinjaman(): Observable<List<ResponseModel.Pinjaman>>

    @DELETE("me/pinjaman/{id}/")
    fun deleteMyPinjaman(@Path("id") id: Int): Completable

    companion object {
        private const val ENDPOINT = "https://pinjamin-backend.herokuapp.com/"
        fun create(tokenProvider: TokenProvider): PinjaminService {
            val httpClient = OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .addInterceptor {
                    chain ->
                    val original = chain.request()
                    val requestBuilder = original.newBuilder().method(original.method(), original.body())

                    tokenProvider.provideToken()?.let {
                        requestBuilder.addHeader("Authorization", "JWT $it")

                    }

                    val request = requestBuilder.build()
                    chain.proceed(request)
                }

            val retrofit = Retrofit.Builder()
                    .baseUrl(PinjaminService.ENDPOINT)
                    .client(httpClient.build())
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
            return retrofit.create(PinjaminService::class.java)
        }
    }
}