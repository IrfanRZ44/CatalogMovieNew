package id.exomatik.catalogmovie.ui.auth.splash

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.CountDownTimer
import id.exomatik.catalogmovie.base.BaseViewModel
import id.exomatik.catalogmovie.ui.movie.MovieActivity

@SuppressLint("StaticFieldLeak")
class SplashViewModel(
    private val activity: Activity?
) : BaseViewModel() {

    fun moveMainMenu(){
        isShowLoading.value = true
        object : CountDownTimer(2000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                isShowLoading.value = false
                val intent = Intent(activity, MovieActivity::class.java)
                activity?.startActivity(intent)
                activity?.finish()
            }
        }.start()
    }
}