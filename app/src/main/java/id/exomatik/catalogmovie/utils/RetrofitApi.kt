package id.exomatik.catalogmovie.utils

import id.exomatik.catalogmovie.model.ModelMovie
import id.exomatik.catalogmovie.model.response.*
import retrofit2.Call
import retrofit2.http.*


/**
 * Created by IrfanRZ on 02/08/2019.
 */
interface RetrofitApi {
    @Headers("Accept:application/json")
    @GET
    fun getDaftarMovie(@Url url: String?): Call<ModelResponseDaftarMovie>

    @Headers("Accept:application/json")
    @GET
    fun getDetailMovie(@Url url: String?): Call<ModelMovie>

    @Headers("Accept:application/json")
    @GET
    fun getUserReview(@Url url: String?): Call<ModelResponseDaftarReview>

    @Headers("Accept:application/json")
    @GET
    fun getTrailer(@Url url: String?): Call<ModelResponseDaftarTrailer>
}