package id.exomatik.catalogmovie.ui.movie.detailMovie

import androidx.navigation.fragment.findNavController
import id.exomatik.catalogmovie.R
import id.exomatik.catalogmovie.base.BaseFragmentBind
import id.exomatik.catalogmovie.databinding.FragmentDetailMovieBinding
import id.exomatik.catalogmovie.utils.Constant

class DetailMovieFragment : BaseFragmentBind<FragmentDetailMovieBinding>() {
    override fun getLayoutResource(): Int = R.layout.fragment_detail_movie
    lateinit var viewModel: DetailMovieViewModel

    override fun myCodeHere() {
        supportActionBar?.title = Constant.menuDetail
        supportActionBar?.show()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        init()
    }

    private fun init() {
        bind.lifecycleOwner = this
        viewModel = DetailMovieViewModel(
            activity, findNavController(), bind.rcGenre, bind.rcReview,
            bind.rcTrailer, bind.btnMoreReview, bind.btnMoreTrailer
        )
        bind.viewModel = viewModel
        try {
            viewModel.initAdapterGenre()
            viewModel.initAdapterReview()
            viewModel.initAdapterTrailer(context?:throw Exception("Error, gagal memuat trailer"))
            viewModel.getDetailMovie(this.arguments?.getInt(Constant.reffMovie)?:throw Exception("Error, terjadi kesalahan database"))
        }catch (e: Exception){
            viewModel.message.value = e.message
        }
    }
}