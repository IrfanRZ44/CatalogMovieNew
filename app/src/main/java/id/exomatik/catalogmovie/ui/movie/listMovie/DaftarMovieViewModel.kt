package id.exomatik.catalogmovie.ui.movie.listMovie

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.exomatik.catalogmovie.R
import id.exomatik.catalogmovie.base.BaseViewModel
import id.exomatik.catalogmovie.model.ModelKategori
import id.exomatik.catalogmovie.model.ModelMovie
import id.exomatik.catalogmovie.model.response.ModelResponseDaftarMovie
import id.exomatik.catalogmovie.ui.movie.detailMovie.DetailMovieFragment
import id.exomatik.catalogmovie.utils.Constant
import id.exomatik.catalogmovie.utils.RetrofitUtils
import id.exomatik.catalogmovie.utils.adapter.showLog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@SuppressLint("StaticFieldLeak")
class DaftarMovieViewModel(
    private val navController: NavController,
    private val activity: Activity?,
    private val rcKategori: RecyclerView,
    private val rcMovie: RecyclerView,
) : BaseViewModel() {
    private val listKategori = ArrayList<ModelKategori>()
    val listMovie = ArrayList<ModelMovie>()
    private lateinit var adapterKategori: AdapterKategoriMovie
    lateinit var adapterMovie: AdapterMovie
    var startPage = 1
    var idSubKategori = 1

    fun initAdapterKategoriMovie() {
        rcKategori.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        listKategori.add(ModelKategori(1, true, "The Marvel Universe"))
        listKategori.add(ModelKategori(2, false, "2012 Oscar Nominations"))
        listKategori.add(ModelKategori(3, false, "DC Comics Universe"))
        listKategori.add(ModelKategori(5, false, "The Avengers"))
        listKategori.add(ModelKategori(6, false, "Films déjà vus"))
        listKategori.add(ModelKategori(7, false, "Films à voir"))
        listKategori.add(ModelKategori(8, false, "2010 Oscar Nominations"))

        adapterKategori = AdapterKategoriMovie(
            listKategori
        ) { item: ModelKategori -> onClickItemKategori(item) }
        rcKategori.adapter = adapterKategori
    }

    fun initAdapterMovie() {
        val layoutManager = GridLayoutManager(activity, 3)
        rcMovie.layoutManager = layoutManager
        adapterMovie = AdapterMovie(
            listMovie
        ) { item: ModelMovie -> onClickItemMovie(item) }
        rcMovie.adapter = adapterMovie
    }

    fun getDaftarMovie() {
        isShowLoading.value = true

        showLog("Get Daftar")
        RetrofitUtils.getDaftarMovie("${idSubKategori}?page=${startPage}&api_key=${Constant.reffApiKey}",
            object : Callback<ModelResponseDaftarMovie> {
                override fun onResponse(
                    call: Call<ModelResponseDaftarMovie>,
                    response: Response<ModelResponseDaftarMovie>
                ) {
                    isShowLoading.value = false
                    val result = response.body()?.results

                    if (!result.isNullOrEmpty()) {
                        if (result.isNotEmpty()){
                            listMovie.addAll(result)
                            adapterMovie.notifyDataSetChanged()

                            startPage += 1

                            if (startPage >= response.body()?.total_pages?:0){
                                startPage = 1
                                idSubKategori += 1
                            }

                            message.value = ""
                        }
                        else{
                            if (startPage > 1) {
                                status.value = "Maaf, sudah tidak ada lagi data"
                            } else {
                                message.value = "Maaf, tidak ditemukan daftar film dengan kategori tersebut"
                            }
                        }
                    } else {
                        if (startPage > 1) {
                            status.value = "Maaf, sudah tidak ada lagi data"
                        } else {
                            message.value = "Maaf, tidak ditemukan daftar film dengan kategori tersebut"
                        }
                    }
                }

                override fun onFailure(
                    call: Call<ModelResponseDaftarMovie>,
                    t: Throwable
                ) {
                    isShowError.value = true
                    isShowLoading.value = false
                    status.value = t.message
                }
            })
    }

    private fun onClickItemKategori(item: ModelKategori){
        idSubKategori = item.id
        startPage = 1
        listMovie.clear()
        adapterMovie.notifyDataSetChanged()
        getDaftarMovie()
    }

    private fun onClickItemMovie(item: ModelMovie){
        val bundle = Bundle()
        val fragmentTujuan = DetailMovieFragment()
        bundle.putInt(Constant.reffMovie, item.id)
        fragmentTujuan.arguments = bundle
        navController.navigate(R.id.detailMovieFragment, bundle)
    }

    fun onClickError(){
        startPage = 1
        listMovie.clear()
        adapterMovie.notifyDataSetChanged()
        getDaftarMovie()
        isShowError.value = false
    }
}