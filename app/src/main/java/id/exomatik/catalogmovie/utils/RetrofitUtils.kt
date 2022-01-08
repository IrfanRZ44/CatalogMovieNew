package id.exomatik.catalogmovie.utils

import id.exomatik.catalogmovie.model.ModelMovie
import id.exomatik.catalogmovie.model.response.ModelResponseDaftarMovie
import id.exomatik.catalogmovie.model.response.ModelResponseDaftarReview
import id.exomatik.catalogmovie.model.response.ModelResponseDaftarTrailer
import okhttp3.ResponseBody
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitUtils{
    private val retrofitDaftarMovie = Retrofit.Builder()
        .baseUrl(Constant.reffURL + Constant.reffDaftarMovie)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val apiDaftarMovie = retrofitDaftarMovie.create(RetrofitApi::class.java)

    fun getDaftarMovie(uri: String, callback: Callback<ModelResponseDaftarMovie>){
        val call = apiDaftarMovie.getDaftarMovie(uri)
        call.enqueue(callback)
    }

    private val retrofitDetailMovie = Retrofit.Builder()
        .baseUrl(Constant.reffURL + Constant.reffDetailMovie)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val apiDetailMovie = retrofitDetailMovie.create(RetrofitApi::class.java)

    fun getDetailMovie(uri: String, callback: Callback<ModelMovie>){
        val call = apiDetailMovie.getDetailMovie(uri)
        call.enqueue(callback)
    }

    fun getUserReview(uri: String, callback: Callback<ModelResponseDaftarReview>){
        val call = apiDetailMovie.getUserReview(uri)
        call.enqueue(callback)
    }

    fun getTrailer(uri: String, callback: Callback<ModelResponseDaftarTrailer>){
        val call = apiDetailMovie.getTrailer(uri)
        call.enqueue(callback)
    }
}