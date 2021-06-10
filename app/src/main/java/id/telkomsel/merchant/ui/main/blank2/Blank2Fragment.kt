package id.telkomsel.merchant.ui.main.blank2

import id.telkomsel.merchant.R
import id.telkomsel.merchant.base.BaseFragmentBind
import id.telkomsel.merchant.databinding.FragmentBlank2Binding

class Blank2Fragment : BaseFragmentBind<FragmentBlank2Binding>() {
    private lateinit var viewModel: Blank2ViewModel

    override fun getLayoutResource(): Int = R.layout.fragment_blank2

    override fun myCodeHere() {
        init()
    }

    private fun init() {
        bind.lifecycleOwner = this
        viewModel = Blank2ViewModel()
        bind.viewModel = viewModel
    }

}

