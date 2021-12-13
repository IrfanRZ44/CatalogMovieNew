package id.telkomsel.merchant.ui.merchant.editProduk

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputLayout
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import id.telkomsel.merchant.R
import id.telkomsel.merchant.base.BaseFragmentBind
import id.telkomsel.merchant.databinding.FragmentEditProdukBinding
import id.telkomsel.merchant.utils.Constant
import id.telkomsel.merchant.utils.adapter.dismissKeyboard
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class EditProdukFragment : BaseFragmentBind<FragmentEditProdukBinding>(){
    override fun getLayoutResource(): Int = R.layout.fragment_edit_produk
    lateinit var viewModel: EditProdukViewModel
    private lateinit var btmSheet : BottomSheetDialog
    private var etSearch: TextInputLayout? = null

    override fun myCodeHere() {
        supportActionBar?.title = "Edit Produk"
        supportActionBar?.show()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        init()
        onClick()
    }

    private fun init() {
        bind.lifecycleOwner = this
        bind.etTglKadaluarsa.editText?.keyListener = null
        bind.etNamaMerchant.editText?.keyListener = null

        viewModel = EditProdukViewModel(
            activity, context, findNavController(), savedData, bind.spinnerKategori,
            bind.etNamaMerchant, bind.etNamaProduk,
            bind.etTglKadaluarsa, bind.etStok, bind.etDesc, bind.etHarga,
            bind.etPromo, bind.etPoin
        )
        bind.viewModel = viewModel
        viewModel.dataProduk.value = this.arguments?.getParcelable(Constant.reffProduk)
        viewModel.setAdapterKategori()
        val kategoriId = savedData.getDataMerchant()?.kategori_id

        if (savedData.getDataMerchant()?.level == Constant.levelMerchant && kategoriId != null){
            bind.rlPickMerchant.visibility = View.GONE
            viewModel.etDataMerchant.value = savedData.getDataMerchant()
            viewModel.getDaftarSubKategoriByMerchant(kategoriId)
        }
        else{
            bind.rlPickMerchant.visibility = View.VISIBLE
            viewModel.etDataMerchant.value = this.arguments?.getParcelable(Constant.reffMerchant)
            initPickMerchant(bind.root)
        }
        viewModel.setData()
    }

    private fun onClick(){
        bind.spinnerKategori.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                activity?.let { dismissKeyboard(it) }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        bind.btnNamaMerchant.setOnClickListener {
            viewModel.startPage = 0
            viewModel.listMerchant.clear()
            viewModel.adapterPickMerchant.notifyDataSetChanged()
            etSearch?.editText?.setText("")
            getDataMerchant("")
            btmSheet.show()
        }

        bind.cardFotoProduk.setOnClickListener {
            context?.let {
                CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAllowFlipping(true)
                    .setAllowRotation(true)
                    .setAspectRatio(1, 1)
                    .start(it, this)
            }
        }
    }

    @SuppressLint("InflateParams")
    private fun initPickMerchant(root: View) {
        btmSheet = BottomSheetDialog(root.context)
        val bottomView = layoutInflater.inflate(R.layout.behavior_pick_merchant, null)

        btmSheet.setContentView(bottomView)
        btmSheet.setCanceledOnTouchOutside(false)
        btmSheet.setCancelable(false)
        btmSheet.behavior.isDraggable = false
        btmSheet.behavior.state = BottomSheetBehavior.STATE_EXPANDED

        viewModel.btmSheet = btmSheet
        etSearch = btmSheet.findViewById(R.id.etSearch)
        val rcRequest = btmSheet.findViewById<RecyclerView>(R.id.rcRequest)
        val btnClose = btmSheet.findViewById<AppCompatImageButton>(R.id.btnClose)

        viewModel.initAdapter(rcRequest)

        rcRequest?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE && viewModel.isShowLoading.value == false) {
                    getDataMerchant("")
                }
            }
        })

        btnClose?.setOnClickListener {
            btmSheet.dismiss()
        }

        etSearch?.editText?.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.startPage = 0
                viewModel.listMerchant.clear()
                viewModel.adapterPickMerchant.notifyDataSetChanged()
                getDataMerchant(etSearch?.editText?.text.toString())

                return@OnEditorActionListener false
            }
            false
        })
    }

    @Suppress("DEPRECATION")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && data != null) {
            val result = CropImage.getActivityResult(data)
            val imagePath = result.uri.path
            val lvl = savedData.getDataMerchant()?.level

            if (resultCode == Activity.RESULT_OK && !imagePath.isNullOrEmpty() && !lvl.isNullOrEmpty()){
                val imageUri = result.uri

                viewModel.etFotoProduk.value = imageUri
                val fileProduk = File(imagePath)
                val urlFoto = MultipartBody.Part.createFormData("url_foto", fileProduk.name, RequestBody.create(
                    MediaType.get("image/*"), fileProduk))
                val produkId = RequestBody.create(MediaType.get("text/plain"), viewModel.dataProduk.value?.id.toString())
                val level = RequestBody.create(MediaType.get("text/plain"), lvl)
                viewModel.editFotoProfilProduk(produkId, level, urlFoto)
            }
        }
    }

    private fun getDataMerchant(search: String?){
        if (savedData.getDataMerchant()?.level == Constant.levelChannel){
            savedData.getDataMerchant()?.cluster?.let {
                viewModel.getDataMerchant(search, it, "regional")
            }
        }
        else{
            savedData.getDataMerchant()?.cluster?.let {
                viewModel.getDataMerchant(search, it, "cluster")
            }
        }
    }
}