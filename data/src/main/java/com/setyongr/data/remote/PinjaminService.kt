package com.setyongr.data.remote

import com.setyongr.data.remote.models.RequestModel
import com.setyongr.data.remote.models.ResponseModel
import com.setyongr.domain.interactor.TokenProvider
import io.reactivex.Completable
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

interface PinjaminService {

    @POST("auth/jwt/create/")
    fun login(@Body req: RequestModel.Login): Single<ResponseModel.Token>

    @POST("auth/users/create/")
    fun register(@Body req: RequestModel.Register): Single<ResponseModel.Register>

    @GET("pinjaman/")
    fun getPinjaman(): Single<List<ResponseModel.Pinjaman>>

    @GET("pinjaman/{id}/")
    fun getPinjamanById(@Path("id") id: Int): Single<ResponseModel.Pinjaman>

    @GET("order/")
    fun getOrder(): Single<List<ResponseModel.Order>>

    @GET("order/{id}/")
    fun getOrderById(@Path("id") id: Int): Single<ResponseModel.Order>

    @POST("order/")
    fun placeOrder(@Body req: RequestModel.Order): Single<ResponseModel.Order>

    @GET("ordertome/")
    fun getOrderToMe(): Single<List<ResponseModel.Order>>

    @GET("ordertome/{id}/")
    fun getOrderToMeById(@Path("id") id: Int): Single<ResponseModel.Order>

    @PATCH("ordertome/{id}/")
    fun updateOrderToMe(@Path("id") id: Int, @Body req: RequestModel.OrderToMe): Single<ResponseModel.Order>

    @Multipart
    @POST("pinjaman/")
    fun savePinjaman(
            @Part("name") name: String,
            @Part("deskripsi") deskripsi: String,
            @Part part: MultipartBody.Part? = null): Single<ResponseModel.Pinjaman>

    @Multipart
    @PATCH("pinjaman/{id}/")
    fun updatePinjaman(
            @Path("id") id: Int? = null,
            @Part("name") name: String? = null,
            @Part("deskripsi") deskripsi: String? = null,
            @Part part: MultipartBody.Part? = null): Single<ResponseModel.Pinjaman>

    @GET("auth/me/")
    fun me(): Single<ResponseModel.User>

    @PATCH("auth/me/")
    fun updateMe(@Body req: RequestModel.User): Single<ResponseModel.User>

    @Multipart
    @PATCH("auth/me/")
    fun updateMeImage(@Part part: MultipartBody.Part): Single<ResponseModel.User>

    @GET("me/pinjaman/")
    fun getMyPinjaman(): Single<List<ResponseModel.Pinjaman>>

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
                    .baseUrl(ENDPOINT)
                    .client(httpClient.build())
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
            return retrofit.create(PinjaminService::class.java)
        }
    }
}