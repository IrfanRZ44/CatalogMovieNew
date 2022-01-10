package id.exomatik.catalogmovie.ui.movie.detailMovie

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.exomatik.catalogmovie.base.BaseViewModel
import id.exomatik.catalogmovie.model.ModelGenre
import id.exomatik.catalogmovie.model.ModelMovie
import id.exomatik.catalogmovie.model.ModelReview
import id.exomatik.catalogmovie.model.ModelTrailer
import id.exomatik.catalogmovie.model.response.ModelResponseDaftarReview
import id.exomatik.catalogmovie.model.response.ModelResponseDaftarTrailer
import id.exomatik.catalogmovie.utils.Constant
import id.exomatik.catalogmovie.utils.RetrofitUtils
import id.exomatik.catalogmovie.utils.adapter.onClickFoto
import id.exomatik.catalogmovie.utils.adapter.showLog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@SuppressLint("StaticFieldLeak")
class DetailMovieViewModel(
    private val activity: Activity?,
    private val navController: NavController,
    private val rcGenre: RecyclerView,
    private val rcReview: RecyclerView,
    private val rcTrailer: RecyclerView,
    private val btnMoreReview: AppCompatButton,
    private val btnMoreTrailer: AppCompatButton,
) : BaseViewModel() {
    var idMovie = 0
    private val listGenre = ArrayList<ModelGenre>()
    private lateinit var adapterGenre: AdapterGenre
    private val listAllTrailer = ArrayList<ModelTrailer>()
    private val listTrailer = ArrayList<ModelTrailer>()
    private lateinit var adapterTrailer: AdapterTrailer
    private val listAllReview = ArrayList<ModelReview>()
    private val listReview = ArrayList<ModelReview>()
    private lateinit var adapterReview: AdapterReview
    val dataMovie = MutableLiveData<ModelMovie>()
    var indexReview = 0
    var maxReview = 3
    var indexTrailer = 0
    var maxTrailer = 3

    fun initAdapterGenre() {
        rcGenre.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        adapterGenre = AdapterGenre(listGenre)
        rcGenre.adapter = adapterGenre
    }

    fun initAdapterReview() {
        rcReview.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.VERTICAL,
            false
        )

        adapterReview = AdapterReview(
            listReview
        ) { item: ModelReview -> onClickItemReview(item) }
        rcReview.adapter = adapterReview
    }

    fun initAdapterTrailer(ctx: Context) {
        rcTrailer.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.VERTICAL,
            false
        )

        adapterTrailer = AdapterTrailer(ctx,
            listTrailer)
        rcTrailer.adapter = adapterTrailer
    }

    fun clickFoto(){
        dataMovie.value?.backdrop_path?.let { onClickFoto(
            "${Constant.reffBaseURLPosterPath}$it",
            navController
        ) }
    }

    fun clickMoreReview(){
        if (indexReview < listAllReview.size){
            maxReview += 3
            for (i in listAllReview.indices){
                if (listReview.size < maxReview && listReview.size < listAllReview.size){
                    listReview.add(listAllReview[indexReview])
                    adapterReview.notifyDataSetChanged()
                    indexReview += 1
                }
            }
        }
        else{
            btnMoreReview.visibility = View.GONE
        }
    }

    fun clickMoreTrailer(){
        if (indexTrailer < listAllTrailer.size){
            maxTrailer += 3
            for (i in listAllTrailer.indices){
                if (listTrailer.size < maxTrailer && listTrailer.size < listAllTrailer.size){
                    listTrailer.add(listAllTrailer[indexTrailer])
                    adapterTrailer.notifyDataSetChanged()
                    indexTrailer += 1
                }
            }
        }
        else{
            btnMoreTrailer.visibility = View.GONE
        }
    }

    fun getDetailMovie() {
        isShowLoading.value = true

        RetrofitUtils.getDetailMovie("${idMovie}?api_key=${Constant.reffApiKey}",
            object : Callback<ModelMovie> {

                override fun onResponse(
                    call: Call<ModelMovie>,
                    response: Response<ModelMovie>
                ) {
                    isShowLoading.value = false
                    val result = response.body()

                    if (result != null) {
                        result.backdrop_path =
                            "${Constant.reffBaseURLPosterPath}${result.backdrop_path}"
                        result.companies = result.production_companies[0].name
                        result.adult = if (result.adult == "false") "No" else "Yes"

                        if (!result.genres.isNullOrEmpty()) {
                            listGenre.addAll(result.genres)
                            adapterGenre.notifyDataSetChanged()
                        }
                        dataMovie.value = result
                        getUserReview(idMovie)
                        getTrailer(idMovie)
                    } else {
                        status.value = "Error, terjadi kesalahan database"
                        message.value = "Error, terjadi kesalahan database"
                    }
                }

                override fun onFailure(
                    call: Call<ModelMovie>,
                    t: Throwable
                ) {
                    showLog("Me not in")
                    isShowError.value = true
                    isShowLoading.value = false
                    status.value = t.message
                }
            })
    }

    fun getUserReview(idMovie: Int) {
        isShowLoading.value = true

        RetrofitUtils.getUserReview("${idMovie}/reviews?api_key=${Constant.reffApiKey}&language=en-US&page=1",
            object : Callback<ModelResponseDaftarReview> {

                override fun onResponse(
                    call: Call<ModelResponseDaftarReview>,
                    response: Response<ModelResponseDaftarReview>
                ) {
                    isShowLoading.value = false
                    val result = response.body()

                    if (result != null) {
                        if (!result.results.isNullOrEmpty()) {
                            listAllReview.addAll(result.results)

                            for (i in result.results.indices){
                                if (listReview.size < maxReview){
                                    listReview.add(result.results[i])
                                    adapterReview.notifyDataSetChanged()
                                    indexReview = i
                                }
                            }
                            indexReview += 1
                        } else {
                            status.value = "Error, gagal mengambil data review"
                        }
                    } else {
                        status.value = "Error, gagal mengambil data review"
                    }
                }

                override fun onFailure(
                    call: Call<ModelResponseDaftarReview>,
                    t: Throwable
                ) {
                    isShowLoading.value = false
                    status.value = t.message
                }
            })
    }

    fun getTrailer(idMovie: Int) {
        isShowLoading.value = true

        RetrofitUtils.getTrailer("${idMovie}/videos?api_key=${Constant.reffApiKey}&language=en-US",
            object : Callback<ModelResponseDaftarTrailer> {

                override fun onResponse(
                    call: Call<ModelResponseDaftarTrailer>,
                    response: Response<ModelResponseDaftarTrailer>
                ) {
                    isShowLoading.value = false
                    val result = response.body()

                    if (result != null) {
                        if (!result.results.isNullOrEmpty()) {
                            listAllTrailer.addAll(result.results)

                            for (i in result.results.indices){
                                if (listTrailer.size < maxTrailer){
                                    listTrailer.add(result.results[i])
                                    adapterTrailer.notifyDataSetChanged()
                                    indexTrailer = i
                                }
                            }
                            indexTrailer += 1
                        } else {
                            status.value = "Error, gagal mengambil data trailer"
                        }
                    } else {
                        status.value = "Error, gagal mengambil data trailer"
                    }
                }

                override fun onFailure(
                    call: Call<ModelResponseDaftarTrailer>,
                    t: Throwable
                ) {
                    isShowLoading.value = false
                    status.value = t.message
                }
            })
    }

    private fun onClickItemReview(item: ModelReview){
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(item.url)
        activity?.startActivity(i)
    }

    fun onClickError(){
        getDetailMovie()
        isShowError.value = false
    }
}