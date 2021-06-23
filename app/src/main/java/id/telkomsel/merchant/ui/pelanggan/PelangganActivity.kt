package id.telkomsel.merchant.ui.pelanggan

import android.content.Context
import android.net.ConnectivityManager
import android.os.CountDownTimer
import android.view.View
import android.view.WindowManager
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.ui.AppBarConfiguration
import id.telkomsel.merchant.R
import id.telkomsel.merchant.base.BaseActivity
import kotlinx.android.synthetic.main.activity_pelanggan.*
import androidx.navigation.findNavController
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
//import coil.api.load
import id.telkomsel.merchant.utils.Constant.defaultTempFoto
import id.telkomsel.merchant.utils.adapter.dismissKeyboard
import id.telkomsel.merchant.utils.adapter.onClickFoto
import id.telkomsel.merchant.utils.adapter.showSnackbar
import id.telkomsel.merchant.utils.adapter.showSnackbarIndefinite
import kotlinx.android.synthetic.main.nav_header_main.view.*

class PelangganActivity : BaseActivity() {
    override fun getLayoutResource(): Int = R.layout.activity_pelanggan
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    private lateinit var view: View
    private var timerCekKoneksi: CountDownTimer? = null

    override fun myCodeHere() {
        setTheme(R.style.CustomTheme)
        drawerLayout.systemUiVisibility = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)

        setSupportActionBar(toolbar)
        view = findViewById(android.R.id.content)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_beranda,
                R.id.nav_profile, R.id.nav_setting
            ), drawerLayout
        )
        navController = findNavController(R.id.navMuballighFragment)
        setupActionBarWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(navController)

        runnabelCekKoneksi()
        setData()
    }

    @Suppress("DEPRECATION")
    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        return connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)!!.state == android.net.NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(
            ConnectivityManager.TYPE_WIFI)!!.state == android.net.NetworkInfo.State.CONNECTED
    }

    private fun runnabelCekKoneksi() {
        var snackbarNotShown = false
        timerCekKoneksi = object : CountDownTimer(300000, 5000) {
            override fun onTick(millisUntilFinished: Long) {
                if(!isNetworkAvailable()){
                    if (!snackbarNotShown){
                        showSnackbarIndefinite(view, "Afwan, mohon periksa koneksi internet Anda")
                        snackbarNotShown = true
                    }
                }
                else{
                    if (snackbarNotShown){
                        showSnackbar(view, "Afwan, mohon periksa koneksi internet Anda")
                        snackbarNotShown = false
                    }
                }
            }

            override fun onFinish() {}
        }.start()
    }

    private fun setData() {
        val headerView = navView?.getHeaderView(0)

//        headerView?.foto?.load(defaultTempFoto) {
//            crossfade(true)
//            placeholder(R.drawable.ic_camera_white)
//            transformations(CircleCropTransformation())
//            error(R.drawable.ic_camera_white)
//            fallback(R.drawable.ic_camera_white)
//            memoryCachePolicy(CachePolicy.ENABLED)
//        }

        headerView?.foto?.setOnClickListener {
            drawerLayout?.closeDrawer(GravityCompat.START)
            onClickFoto(defaultTempFoto, navController)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        dismissKeyboard(this)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun showProgress() {
        progress.visibility = View.VISIBLE
        window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    override fun onBackPressed() {
        if (drawerLayout?.isDrawerOpen(GravityCompat.START)!!) {
            drawerLayout?.closeDrawer(GravityCompat.START)
        }
        if (drawerLayout?.isDrawerOpen(GravityCompat.END)!!) {
            drawerLayout?.closeDrawer(GravityCompat.END)
        } else {
            super.onBackPressed()
        }
    }
}