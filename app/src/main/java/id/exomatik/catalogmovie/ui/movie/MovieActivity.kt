package id.exomatik.catalogmovie.ui.movie

import android.view.View
import androidx.navigation.fragment.NavHostFragment
import id.exomatik.catalogmovie.R
import id.exomatik.catalogmovie.base.BaseActivity
import kotlinx.android.synthetic.main.activity_movie.*

class MovieActivity : BaseActivity() {
    override fun getLayoutResource(): Int = R.layout.activity_movie

    @Suppress("DEPRECATION")
    override fun myCodeHere() {
        NavHostFragment.create(R.navigation.movie_nav)
        viewParent.systemUiVisibility = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}