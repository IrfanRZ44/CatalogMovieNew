package id.exomatik.catalogmovie.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import id.exomatik.catalogmovie.utils.DataSave


abstract class BaseActivity : AppCompatActivity(){
    protected abstract fun myCodeHere()
    protected abstract fun getLayoutResource(): Int
    protected lateinit var savedData : DataSave

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(getLayoutResource())
        savedData = DataSave(this)

        myCodeHere()
    }
}