package id.telkomsel.merchant.ui.merchant.addMerchant

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import id.telkomsel.merchant.R
import id.telkomsel.merchant.base.BaseFragmentBind
import id.telkomsel.merchant.databinding.FragmentAddMerchantBinding
import id.telkomsel.merchant.utils.Constant
import id.telkomsel.merchant.utils.adapter.dismissKeyboard

class AddMerchantFragment : BaseFragmentBind<FragmentAddMerchantBinding>(){
    override fun getLayoutResource(): Int = R.layout.fragment_add_merchant
    lateinit var viewModel: AddMerchantViewModel
    private val mapBundelKey = "MapViewBundleKey"
    private var gmap: GoogleMap? = null
    private var marker : Marker? = null
    private lateinit var place : MarkerOptions
    private lateinit var btmSheet : BottomSheetDialog
    private lateinit var mapView : MapView

    override fun myCodeHere() {
        supportActionBar?.title = "Tambah Merchant"
        supportActionBar?.show()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        init(savedInstanceState)
        onClick()
    }

    private fun init(savedInstanceState: Bundle?){
        bind.lifecycleOwner = this
        bind.etTitikLokasi.editText?.keyListener = null
        bind.etTglLahir.editText?.keyListener = null
        bind.etTglPeresmian.editText?.keyListener = null

        viewModel = AddMerchantViewModel(activity, findNavController(), bind.spinnerKategori,
            bind.spinnerProvinsi, bind.spinnerKabupaten, bind.spinnerKecamatan, bind.spinnerKelurahan,
            bind.etNamaMerchant, bind.etAlamatMerchant, bind.etTitikLokasi, bind.etTglPeresmian,
            bind.etCluster, bind.etNoHpMerchant, bind.etNoWaMerchant, bind.etEmail,
            bind.etUsername, bind.etPassword, bind.etConfirmPassword,
            bind.etNamaLengkap, bind.etTglLahir, bind.etNoHpPemilik, bind.etNoWaPemilik,
            )
        bind.viewModel = viewModel
        initPickMap(bind.root, savedInstanceState)
        viewModel.setAdapterKategori()
        viewModel.setAdapterProvinsi()
        viewModel.setAdapterKabupaten()
        viewModel.setAdapterKecamatan()
        viewModel.setAdapterKelurahan()

        viewModel.dataMerchant = this.arguments?.getParcelable(Constant.reffMerchant)
        if (viewModel.dataMerchant != null){
            viewModel.setDataMerchant(
                this.arguments?.getParcelable(Constant.dataModelFotoProfil)
            )
        }

        viewModel.getDaftarProvinsi()
        viewModel.getDaftarKategori()
    }

    private fun onClick(){
        bind.etNoWaPemilik.editText?.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.onClickRegisterMerchant()
                return@OnEditorActionListener false
            }
            false
        })

        bind.spinnerKategori.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                activity?.let { dismissKeyboard(it) }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        bind.spinnerProvinsi.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                activity?.let { dismissKeyboard(it) }
                viewModel.listKabupaten.clear()
                viewModel.adapterKabupaten.notifyDataSetChanged()
                viewModel.getDaftarKabupaten(viewModel.listProvinsi[position].id)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        bind.spinnerKabupaten.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                activity?.let { dismissKeyboard(it) }
                viewModel.listKecamatan.clear()
                viewModel.adapterKecamatan.notifyDataSetChanged()
                viewModel.getDaftarKecamatan(viewModel.listKabupaten[position].id)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        bind.spinnerKecamatan.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                activity?.let { dismissKeyboard(it) }
                viewModel.listKelurahan.clear()
                viewModel.adapterKelurahan.notifyDataSetChanged()
                viewModel.getDaftarKelurahan(viewModel.listKecamatan[position].id)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        bind.spinnerKelurahan.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                activity?.let { dismissKeyboard(it) }
                viewModel.etCluster.value = viewModel.listKelurahan[bind.spinnerKelurahan.selectedItemPosition].CLUSTER
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        bind.btnLocation.setOnClickListener {
            btmSheet.show()
            mapView.getMapAsync{
                if(marker != null) marker?.remove()
                gmap = it
                when {
                    !viewModel.etLatLng.value.isNullOrEmpty() -> {
                        setMarker(viewModel.etLatLng.value)
                    }
                    else -> {
                        val myLocation = LatLng(Constant.defaultLatitude, Constant.defaultLongitude)
                        gmap?.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 15f))

                        checkPermission()
                    }
                }

                gmap?.setOnMapClickListener { latLng ->
                    val locationPoint = latLng.latitude.toString() + " : " + latLng.longitude.toString()

                    viewModel.etLatLng.value = locationPoint
                    viewModel.latitude.value = latLng.latitude.toString()
                    viewModel.longitude.value = latLng.longitude.toString()
                    if(marker != null) marker?.remove()
                    place = MarkerOptions().position(latLng).title("Titik Lokasi")
                    try {
                        marker = gmap?.addMarker(place)?:throw Exception("Gagal mendapatkan titik lokasi")
                    }catch (e: Exception){
                        viewModel.message.value = e.message
                    }
                }

                val pick = btmSheet.findViewById<FloatingActionButton>(R.id.btnMap)
                pick?.setOnClickListener {
                    if (marker != null) {
                        btmSheet.dismiss()
                        viewModel.message.value = "Berhasil memilih lokasi"
                    } else Toast.makeText(view?.context, "Afwan, Anda belum memilih lokasi", Toast.LENGTH_LONG).show()
                }
            }
        }

        bind.cardFotoProfil.setOnClickListener {
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        var mapViewBundle = outState.getBundle(mapBundelKey)
        if (mapViewBundle == null) {
            mapViewBundle = Bundle()
            outState.putBundle(mapBundelKey, mapViewBundle)
        }
        mapView.onSaveInstanceState(mapViewBundle)
    }

    override fun onResume() {
        super.onResume()
        if (this::mapView.isInitialized) mapView.onResume()
    }

    override fun onStart() {
        super.onStart()
        if (this::mapView.isInitialized) mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        if (this::mapView.isInitialized) mapView.onStop()
    }

    override fun onPause() {
        if (this::mapView.isInitialized) mapView.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        if (this::mapView.isInitialized) mapView.onDestroy()
        super.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        if (this::mapView.isInitialized) mapView.onLowMemory()
    }

    private fun checkPermission() {
        context?.let {
            if (ContextCompat.checkSelfPermission(it, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    it, Manifest.permission.ACCESS_COARSE_LOCATION
                )
                == PackageManager.PERMISSION_GRANTED
            ) {
                val fusedLocationClient = LocationServices.getFusedLocationProviderClient(it)

                fusedLocationClient?.lastLocation?.addOnSuccessListener { location: Location? ->
                    val myLocation = LatLng(location?.latitude?:Constant.defaultLatitude, location?.longitude?:Constant.defaultLongitude)
                    gmap?.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 15f))

                    val place = MarkerOptions().position(myLocation).title("Lokasi Anda")
                    val locationPoint = myLocation.latitude.toString() + " : " + myLocation.longitude.toString()

                    viewModel.etLatLng.value = locationPoint
                    viewModel.latitude.value = myLocation.latitude.toString()
                    viewModel.longitude.value = myLocation.longitude.toString()

                    if (marker != null) marker?.remove()
                    marker = gmap?.addMarker(place)
                }

            }
            else {
                viewModel.message.value = "Anda belum mengizinkan akses lokasi aplikasi ini"

                ActivityCompat.requestPermissions(
                    context as Activity,
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ),
                    Constant.codeRequestLocation
                )
            }
        }

    }

    @SuppressLint("InflateParams")
    private fun initPickMap(root: View, bundle: Bundle?) {
        btmSheet = BottomSheetDialog(root.context)
        val bottomView = layoutInflater.inflate(R.layout.behavior_pick_maps,null)

        var mapViewBundle: Bundle? = null
        if (bundle != null) {
            mapViewBundle = bundle.getBundle(mapBundelKey)
        }

        btmSheet.setContentView(bottomView)
        btmSheet.setCanceledOnTouchOutside(false)
        btmSheet.setCancelable(false)
        btmSheet.behavior.isDraggable = false
        btmSheet.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        mapView = bottomView.findViewById(R.id.mapViewPick)
        mapView.onCreate(mapViewBundle)
    }

    private fun setMarker(titikLokasi : String?){
        val locationPoint = titikLokasi?:"${Constant.defaultLatitude} : ${Constant.defaultLongitude}"

        val data = locationPoint.split(" : ")
        val latitude = data[0].toDouble()
        val longitude = data[1].toDouble()
        val myLocation = LatLng(latitude, longitude)
        gmap?.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 15f))
        val place = MarkerOptions().position(myLocation).title("Titik Lokasi")

        marker = gmap?.addMarker(place)
        viewModel.message.value = "Berhasil memilih lokasi"
    }

    @Suppress("DEPRECATION")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)

            if (resultCode == Activity.RESULT_OK){
                val imageUri = result.uri

                viewModel.etFotoProfil.value = imageUri
            }
        }
    }
}