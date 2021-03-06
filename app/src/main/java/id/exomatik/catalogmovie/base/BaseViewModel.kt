package id.exomatik.catalogmovie.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel (){
    val isShowLoading = MutableLiveData<Boolean>()
    val isShowError = MutableLiveData<Boolean>()
    val message = MutableLiveData<String>()
    val status = MutableLiveData<String>()
}