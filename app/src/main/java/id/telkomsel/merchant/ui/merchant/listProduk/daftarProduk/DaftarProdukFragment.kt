package id.telkomsel.merchant.ui.merchant.listProduk.daftarProduk

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import id.telkomsel.merchant.R
import id.telkomsel.merchant.databinding.FragmentDaftarProdukBinding
import id.telkomsel.merchant.base.BaseFragmentBind
import id.telkomsel.merchant.listener.ListenerFotoIklan
import id.telkomsel.merchant.model.ModelFotoIklan
import id.telkomsel.merchant.utils.adapter.onClickFoto
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.format
import id.zelory.compressor.constraint.quality
import id.zelory.compressor.constraint.resolution
import id.zelory.compressor.constraint.size
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class DaftarProdukFragment(private val statusRequest: String,
                           private val stok: Int,
                           private val isKadaluarsa: Boolean
) : BaseFragmentBind<FragmentDaftarProdukBinding>(), ListenerFotoIklan {
    override fun getLayoutResource(): Int = R.layout.fragment_daftar_produk
    lateinit var viewModel: DaftarProdukViewModel
    private var dataFotoIklan: ModelFotoIklan? = null

    override fun myCodeHere() {
        supportActionBar?.show()
        init()
    }

    fun init(){
        bind.lifecycleOwner = this
        viewModel = DaftarProdukViewModel(
            findNavController(), activity, context, bind.rcKategori, bind.rcProduk,
            statusRequest, stok, isKadaluarsa, savedData, bind.viewPager, bind.dotsIndicator,
            this
        )
        bind.viewModel = viewModel

        viewModel.initHeader(bind.cardheader)
        viewModel.initAdapterKategori()
        viewModel.initAdapterProduk()
        viewModel.showDialogFilter(bind.root, layoutInflater)

        viewModel.getDataKategori()
        viewModel.checkCluster()

        bind.swipeRefresh.setOnRefreshListener {
            viewModel.startPage = 0
            viewModel.listProduk.clear()
            viewModel.adapterProduk.notifyDataSetChanged()
            bind.swipeRefresh.isRefreshing = false
            viewModel.checkCluster()
        }

        bind.rcProduk.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE && viewModel.isShowLoading.value == false) {
                    viewModel.isShowLoading.value = true
                    viewModel.checkCluster()
                }
            }
        })
    }

    override fun clickUploadIklan(position: Int, rows: ModelFotoIklan) {
        super.clickUploadIklan(position, rows)

        dataFotoIklan = rows
        context?.let {
            CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAllowFlipping(true)
                .setAllowRotation(true)
                .setAspectRatio(2, 1)
                .start(it, this)
        }
    }

    override fun clickFotoIklan(rows: ModelFotoIklan) {
        super.clickFotoIklan(rows)

        onClickFoto(rows.url_foto, findNavController())
    }

    @Suppress("DEPRECATION")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            val imagePath = result.uri.path
            val lvl = savedData.getDataMerchant()?.level

            if (resultCode == Activity.RESULT_OK && !imagePath.isNullOrEmpty() && !lvl.isNullOrEmpty()){
                activity?.let { compressImage(it, imagePath) }
                dataFotoIklan = null
            }
        }
    }

    private fun compressImage(act: Activity, realFoto: String){
        val job = Job()
        val uiScope = CoroutineScope(Dispatchers.IO + job)
        uiScope.launch {
            val compressedImageFile = Compressor.compress(act, File(realFoto)) {
                resolution(256, 256)
                quality(70)
                format(Bitmap.CompressFormat.JPEG)
                size(124_000) // 124 KB
            }
            val resultUri = Uri.fromFile(compressedImageFile)

            act.runOnUiThread {
                resultUri?.let {
                    val tempPath = it.path

                    if(!tempPath.isNullOrEmpty()){
                        val fileProduk = File(tempPath)
                        val urlFoto = MultipartBody.Part.createFormData("url_foto", fileProduk.name, RequestBody.create(
                            MediaType.get("image/*"), fileProduk))
                        val idFoto = dataFotoIklan?.id
                        if (idFoto != 0){
                            val id = RequestBody.create(MediaType.get("text/plain"), idFoto.toString())
                            viewModel.updateFotoIklan(id, urlFoto)
                        }
                        else{
                            viewModel.createFotoIklan(urlFoto)
                        }
                    }
                    else{
                        val fileProduk = File(realFoto)
                        val urlFoto = MultipartBody.Part.createFormData("url_foto", fileProduk.name, RequestBody.create(
                            MediaType.get("image/*"), fileProduk))
                        val idFoto = dataFotoIklan?.id
                        if (idFoto != 0){
                            val id = RequestBody.create(MediaType.get("text/plain"), idFoto.toString())
                            viewModel.updateFotoIklan(id, urlFoto)
                        }
                        else{
                            viewModel.createFotoIklan(urlFoto)
                        }
                    }
                }
            }
        }
    }
}