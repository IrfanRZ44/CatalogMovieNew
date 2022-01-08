package id.exomatik.catalogmovie.ui.movie.listMovie

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import id.exomatik.catalogmovie.R
import id.exomatik.catalogmovie.databinding.FragmentDaftarMovieBinding
import id.exomatik.catalogmovie.base.BaseFragmentBind
import id.exomatik.catalogmovie.utils.Constant

class DaftarMovieFragment : BaseFragmentBind<FragmentDaftarMovieBinding>() {
    override fun getLayoutResource(): Int = R.layout.fragment_daftar_movie
    lateinit var viewModel: DaftarMovieViewModel

    override fun myCodeHere() {
        supportActionBar?.show()
        supportActionBar?.title = Constant.menuBeranda
        init()
    }

    private fun init(){
        bind.lifecycleOwner = this
        viewModel = DaftarMovieViewModel(
            findNavController(), activity, bind.rcKategori, bind.rcMovie
        )
        bind.viewModel = viewModel

        viewModel.initAdapterKategoriMovie()
        viewModel.initAdapterMovie()

        viewModel.getDaftarMovie()

        bind.swipeRefresh.setOnRefreshListener {
            viewModel.startPage = 1
            viewModel.listMovie.clear()
            viewModel.adapterMovie.notifyDataSetChanged()
            bind.swipeRefresh.isRefreshing = false
            viewModel.getDaftarMovie()
        }

        bind.rcMovie.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE && viewModel.isShowLoading.value == false) {
                    viewModel.isShowLoading.value = true
                    viewModel.getDaftarMovie()
                }
            }
        })
    }
}