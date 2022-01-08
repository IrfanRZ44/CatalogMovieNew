package id.exomatik.catalogmovie.ui.auth.splash

import id.exomatik.catalogmovie.R
import id.exomatik.catalogmovie.databinding.FragmentSplashBinding
import id.exomatik.catalogmovie.base.BaseFragmentBind

class SplashFragment : BaseFragmentBind<FragmentSplashBinding>() {
    override fun getLayoutResource(): Int = R.layout.fragment_splash
    lateinit var viewModel: SplashViewModel

    override fun myCodeHere() {
        supportActionBar?.hide()
        bind.lifecycleOwner = this
        viewModel = SplashViewModel(activity)
        bind.viewModel = viewModel
        viewModel.moveMainMenu()
    }
}