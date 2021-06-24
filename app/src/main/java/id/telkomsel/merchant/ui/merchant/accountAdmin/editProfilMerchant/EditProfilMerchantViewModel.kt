package id.telkomsel.merchant.ui.merchant.accountAdmin.editProfilMerchant

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.net.Uri
import androidx.appcompat.widget.AppCompatSpinner
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import com.google.android.material.textfield.TextInputLayout
import id.telkomsel.merchant.base.BaseViewModel
import id.telkomsel.merchant.model.ModelKategori
import id.telkomsel.merchant.model.ModelMerchant
import id.telkomsel.merchant.model.ModelWilayah
import id.telkomsel.merchant.model.response.ModelResponse
import id.telkomsel.merchant.model.response.ModelResponseDaftarKategori
import id.telkomsel.merchant.model.response.ModelResponseDataKategori
import id.telkomsel.merchant.model.response.ModelResponseWilayah
import id.telkomsel.merchant.utils.Constant
import id.telkomsel.merchant.utils.DataSave
import id.telkomsel.merchant.utils.RetrofitUtils
import id.telkomsel.merchant.utils.adapter.SpinnerKategoriAdapter
import id.telkomsel.merchant.utils.adapter.SpinnerWilayahAdapter
import id.telkomsel.merchant.utils.adapter.dismissKeyboard
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

@SuppressLint("StaticFieldLeak")
class EditProfilMerchantViewModel(
    private val savedData: DataSave,
    private val activity: Activity?,
    private val navController: NavController,
    private val spinnerKategori: AppCompatSpinner,
    private val spinnerProvinsi: AppCompatSpinner,
    private val spinnerKabupaten: AppCompatSpinner,
    private val spinnerKecamatan: AppCompatSpinner,
    private val spinnerKelurahan: AppCompatSpinner,
    private val editNamaMerchant: TextInputLayout,
    private val editAlamatMerchant: TextInputLayout,
    private val editTitikLokasi: TextInputLayout,
    private val editTglPeresmianMerchant: TextInputLayout,
    private val editCluster: TextInputLayout,
    private val editNoHpMerchant: TextInputLayout,
    private val editNoWaMerchant: TextInputLayout,
    private val editEmail: TextInputLayout,
    private val editNamaLengkap: TextInputLayout,
    private val editTglLahir: TextInputLayout,
    private val editNoHpPemilik: TextInputLayout,
    private val editNoWaPemilik: TextInputLayout
) : BaseViewModel() {
    var dataMerchant: ModelMerchant? = null
    val etNamaMerchant = MutableLiveData<String>()
    val etAlamatMerchant = MutableLiveData<String>()
    val etLatLng = MutableLiveData<String>()
    val latitude = MutableLiveData<String>()
    val longitude = MutableLiveData<String>()
    val etTglPeresmianMerchant = MutableLiveData<String>()
    val etNamaLengkap = MutableLiveData<String>()
    val etTglLahir = MutableLiveData<String>()
    val etFotoProfil = MutableLiveData<Uri>()
    val etPhonePemilik = MutableLiveData<String>()
    val etWAPemilik = MutableLiveData<String>()
    val etHPMerchant = MutableLiveData<String>()
    val etWAMerchant = MutableLiveData<String>()
    val etEmailMerchant = MutableLiveData<String>()
    val listKategori = ArrayList<ModelKategori>()
    val listProvinsi = ArrayList<ModelWilayah>()
    val listKabupaten = ArrayList<ModelWilayah>()
    val listKecamatan = ArrayList<ModelWilayah>()
    val listKelurahan = ArrayList<ModelWilayah>()
    val etCluster = MutableLiveData<String>()
    lateinit var adapterKategori: SpinnerKategoriAdapter
    lateinit var adapterProvinsi: SpinnerWilayahAdapter
    lateinit var adapterKabupaten: SpinnerWilayahAdapter
    lateinit var adapterKecamatan: SpinnerWilayahAdapter
    lateinit var adapterKelurahan: SpinnerWilayahAdapter

    fun setAdapterKategori() {
        listKategori.clear()

        adapterKategori = SpinnerKategoriAdapter(
            activity,
            listKategori, true
        )
        spinnerKategori.adapter = adapterKategori
    }

    fun setAdapterProvinsi() {
        listProvinsi.clear()

        adapterProvinsi = SpinnerWilayahAdapter(
            activity,
            listProvinsi, true
        )
        spinnerProvinsi.adapter = adapterProvinsi
    }

    fun setAdapterKabupaten() {
        listKabupaten.clear()

        adapterKabupaten = SpinnerWilayahAdapter(
            activity,
            listKabupaten, true
        )
        spinnerKabupaten.adapter = adapterKabupaten
    }

    fun setAdapterKecamatan() {
        listKecamatan.clear()

        adapterKecamatan = SpinnerWilayahAdapter(
            activity,
            listKecamatan, true
        )
        spinnerKecamatan.adapter = adapterKecamatan
    }

    fun setAdapterKelurahan() {
        listKelurahan.clear()

        adapterKelurahan = SpinnerWilayahAdapter(
            activity,
            listKelurahan, true
        )
        spinnerKelurahan.adapter = adapterKelurahan
    }

    fun setDataMerchant() {
        etEmailMerchant.value = dataMerchant?.email_merchant
        etNamaMerchant.value = dataMerchant?.nama_merchant
        etAlamatMerchant.value = dataMerchant?.alamat_merchant
        etLatLng.value = dataMerchant?.latitude + " : " + dataMerchant?.longitude
        latitude.value = dataMerchant?.latitude
        longitude.value = dataMerchant?.longitude
        etCluster.value = dataMerchant?.cluster
        etTglLahir.value = dataMerchant?.tgl_lahir
        etTglPeresmianMerchant.value = dataMerchant?.tgl_peresmian_merchant
        etNamaLengkap.value = dataMerchant?.nama_lengkap

        etHPMerchant.value = dataMerchant?.no_hp_merchant?.replaceFirst("+62", "0")
        etWAMerchant.value = dataMerchant?.no_wa_merchant?.replaceFirst("+62", "0")
        etPhonePemilik.value = dataMerchant?.no_hp_pemilik?.replaceFirst("+62", "0")
        etWAPemilik.value = dataMerchant?.no_wa_pemilik?.replaceFirst("+62", "0")

        etFotoProfil.value = Uri.parse(dataMerchant?.foto_profil)
    }

    fun getDateTglLahir() {
        activity?.let { dismissKeyboard(it) }
        val datePickerDialog: DatePickerDialog
        val localCalendar = Calendar.getInstance()

        try {
            datePickerDialog = DatePickerDialog(
                activity ?: throw Exception("Error, mohon mulai ulang aplikasi"),
                { _, paramAnonymousInt1, paramAnonymousInt2, paramAnonymousInt3 ->
                    val dateSelected = Calendar.getInstance()
                    dateSelected[paramAnonymousInt1, paramAnonymousInt2] = paramAnonymousInt3
                    val dateFormatter = SimpleDateFormat(Constant.dateFormat1, Locale.US)
                    etTglLahir.value = dateFormatter.format(dateSelected.time)
                },
                localCalendar[Calendar.YEAR],
                localCalendar[Calendar.MONTH],
                localCalendar[Calendar.DATE]
            )

            datePickerDialog.show()
        } catch (e: java.lang.Exception) {
            message.value = e.message
        }
    }

    fun getDateTglPeresmian() {
        activity?.let { dismissKeyboard(it) }
        val datePickerDialog: DatePickerDialog
        val localCalendar = Calendar.getInstance()

        try {
            datePickerDialog = DatePickerDialog(
                activity ?: throw Exception("Error, mohon mulai ulang aplikasi"),
                { _, paramAnonymousInt1, paramAnonymousInt2, paramAnonymousInt3 ->
                    val dateSelected = Calendar.getInstance()
                    dateSelected[paramAnonymousInt1, paramAnonymousInt2] = paramAnonymousInt3
                    val dateFormatter = SimpleDateFormat(Constant.dateFormat1, Locale.US)
                    etTglPeresmianMerchant.value = dateFormatter.format(dateSelected.time)
                },
                localCalendar[Calendar.YEAR],
                localCalendar[Calendar.MONTH],
                localCalendar[Calendar.DATE]
            )

            datePickerDialog.show()
        } catch (e: java.lang.Exception) {
            message.value = e.message
        }
    }

    fun getDaftarKategori() {
        isShowLoading.value = true

        RetrofitUtils.getDaftarKategori(
            object : Callback<ModelResponseDaftarKategori> {
                override fun onResponse(
                    call: Call<ModelResponseDaftarKategori>,
                    response: Response<ModelResponseDaftarKategori>
                ) {
                    isShowLoading.value = false
                    val result = response.body()

                    if (result?.message == Constant.reffSuccess) {
                        val data = result.data
                        listKategori.clear()
                        listKategori.add(ModelKategori(0, 0, Constant.pilihKategori))

                        for (i in data.indices) {
                            listKategori.add(data[i])
                        }
                        adapterKategori.notifyDataSetChanged()

                        val idKategori = dataMerchant?.kategori_id
                        if (dataMerchant != null) {
                            for (i in listKategori.indices) {
                                if (listKategori[i].id == idKategori) {
                                    spinnerKategori.setSelection(i)
                                }
                            }
                        }
                    } else {
                        listKategori.clear()
                        listKategori.add(ModelKategori(0, 0, Constant.noDataKategori))
                        adapterKategori.notifyDataSetChanged()
                    }
                }

                override fun onFailure(
                    call: Call<ModelResponseDaftarKategori>,
                    t: Throwable
                ) {
                    isShowLoading.value = false
                    listKategori.clear()
                    listKategori.add(ModelKategori(0, 0, Constant.noDataKategori))
                    adapterKategori.notifyDataSetChanged()
                }
            })
    }

    fun getDataKategori(kategori_id: Int){
        isShowLoading.value = true

        RetrofitUtils.getDataKategori(kategori_id,
            object : Callback<ModelResponseDataKategori> {
                override fun onResponse(
                    call: Call<ModelResponseDataKategori>,
                    response: Response<ModelResponseDataKategori>
                ) {
                    isShowLoading.value = false
                    val result = response.body()
                    val dataKategori = result?.data

                    if (result?.message == Constant.reffSuccess && dataKategori != null){
                        listKategori.add(dataKategori)
                        adapterKategori.notifyDataSetChanged()
                    }
                    else{
                        listKategori.add(ModelKategori(0, 0, Constant.noDataKategori))
                        adapterKategori.notifyDataSetChanged()
                    }
                }

                override fun onFailure(
                    call: Call<ModelResponseDataKategori>,
                    t: Throwable
                ) {
                    isShowLoading.value = false
                    listKategori.add(ModelKategori(0, 0, Constant.noDataKategori))
                    adapterKategori.notifyDataSetChanged()
                }
            })
    }

    fun getDaftarProvinsi() {
        isShowLoading.value = true

        RetrofitUtils.getDaftarProvinsi(
            object : Callback<ModelResponseWilayah> {
                override fun onResponse(
                    call: Call<ModelResponseWilayah>,
                    response: Response<ModelResponseWilayah>
                ) {
                    isShowLoading.value = false
                    val result = response.body()

                    if (result?.message == Constant.reffSuccess) {
                        val data = result.data
                        listProvinsi.clear()
                        listProvinsi.add(ModelWilayah(0, Constant.pilihProvinsi))

                        for (i in data.indices) {
                            listProvinsi.add(data[i])
                        }
                        adapterProvinsi.notifyDataSetChanged()

                        val provinsi = dataMerchant?.provinsi
                        if (dataMerchant != null) {
                            for (i in listProvinsi.indices) {
                                if (listProvinsi[i].nama == provinsi) {
                                    spinnerProvinsi.setSelection(i)
                                }
                            }
                        }
                    } else {
                        listProvinsi.clear()
                        listProvinsi.add(ModelWilayah(0, Constant.noDataWilayah))
                        adapterProvinsi.notifyDataSetChanged()
                    }
                }

                override fun onFailure(
                    call: Call<ModelResponseWilayah>,
                    t: Throwable
                ) {
                    isShowLoading.value = false
                    listProvinsi.clear()
                    listProvinsi.add(ModelWilayah(0, Constant.noDataWilayah))
                    adapterProvinsi.notifyDataSetChanged()
                }
            })
    }

    fun getDaftarKabupaten(idProvinsi: Long) {
        isShowLoading.value = true

        RetrofitUtils.getDaftarKabupaten(idProvinsi,
            object : Callback<ModelResponseWilayah> {
                override fun onResponse(
                    call: Call<ModelResponseWilayah>,
                    response: Response<ModelResponseWilayah>
                ) {
                    isShowLoading.value = false
                    val result = response.body()

                    if (result?.message == Constant.reffSuccess) {
                        val data = result.data
                        listKabupaten.clear()
                        listKabupaten.add(ModelWilayah(0, Constant.pilihKabupaten))

                        for (i in data.indices) {
                            listKabupaten.add(data[i])
                        }
                        adapterKabupaten.notifyDataSetChanged()

                        val kabupaten = dataMerchant?.kabupaten
                        if (dataMerchant != null) {
                            for (i in listKabupaten.indices) {
                                if (listKabupaten[i].nama == kabupaten) {
                                    spinnerKabupaten.setSelection(i)
                                }
                            }
                        }
                    } else {
                        listKabupaten.clear()
                        listKabupaten.add(ModelWilayah(0, Constant.noDataWilayah))
                        adapterKabupaten.notifyDataSetChanged()
                    }
                }

                override fun onFailure(
                    call: Call<ModelResponseWilayah>,
                    t: Throwable
                ) {
                    isShowLoading.value = false
                    listKabupaten.clear()
                    listKabupaten.add(ModelWilayah(0, Constant.noDataWilayah))
                    adapterKabupaten.notifyDataSetChanged()
                }
            })
    }

    fun getDaftarKecamatan(idKabupaten: Long) {
        isShowLoading.value = true

        RetrofitUtils.getDaftarKecamatan(idKabupaten,
            object : Callback<ModelResponseWilayah> {
                override fun onResponse(
                    call: Call<ModelResponseWilayah>,
                    response: Response<ModelResponseWilayah>
                ) {
                    isShowLoading.value = false
                    val result = response.body()

                    if (result?.message == Constant.reffSuccess) {
                        val data = result.data
                        listKecamatan.clear()
                        listKecamatan.add(ModelWilayah(0, Constant.pilihKecamatan))

                        for (i in data.indices) {
                            listKecamatan.add(data[i])
                        }
                        adapterKecamatan.notifyDataSetChanged()

                        val kecamatan = dataMerchant?.kecamatan
                        if (dataMerchant != null) {
                            for (i in listKecamatan.indices) {
                                if (listKecamatan[i].nama == kecamatan) {
                                    spinnerKecamatan.setSelection(i)
                                }
                            }
                        }
                    } else {
                        listKecamatan.clear()
                        listKecamatan.add(ModelWilayah(0, Constant.noDataWilayah))
                        adapterKecamatan.notifyDataSetChanged()
                    }
                }

                override fun onFailure(
                    call: Call<ModelResponseWilayah>,
                    t: Throwable
                ) {
                    isShowLoading.value = false
                    listKecamatan.clear()
                    listKecamatan.add(ModelWilayah(0, Constant.noDataWilayah))
                    adapterKecamatan.notifyDataSetChanged()
                }
            })
    }

    fun getDaftarKelurahan(idKecamatan: Long) {
        isShowLoading.value = true

        RetrofitUtils.getDaftarKelurahan(idKecamatan,
            object : Callback<ModelResponseWilayah> {
                override fun onResponse(
                    call: Call<ModelResponseWilayah>,
                    response: Response<ModelResponseWilayah>
                ) {
                    isShowLoading.value = false
                    val result = response.body()

                    if (result?.message == Constant.reffSuccess) {
                        val data = result.data
                        listKelurahan.clear()
                        listKelurahan.add(ModelWilayah(0, Constant.pilihKelurahan))

                        for (i in data.indices) {
                            listKelurahan.add(data[i])
                        }
                        adapterKelurahan.notifyDataSetChanged()

                        val kelurahan = dataMerchant?.kelurahan
                        if (dataMerchant != null) {
                            for (i in listKelurahan.indices) {
                                if (listKelurahan[i].nama == kelurahan) {
                                    spinnerKelurahan.setSelection(i)
                                }
                            }
                        }
                    } else {
                        listKelurahan.clear()
                        listKelurahan.add(ModelWilayah(0, Constant.noDataWilayah))
                        adapterKelurahan.notifyDataSetChanged()
                    }
                }

                override fun onFailure(
                    call: Call<ModelResponseWilayah>,
                    t: Throwable
                ) {
                    isShowLoading.value = false
                    listKelurahan.clear()
                    listKelurahan.add(ModelWilayah(0, Constant.noDataWilayah))
                    adapterKelurahan.notifyDataSetChanged()
                }
            })
    }

    private fun setTextError(msg: String, editText: TextInputLayout) {
        message.value = msg
        editText.error = msg
        editText.requestFocus()
        editText.findFocus()
    }

    private fun setNullError() {
        editNoHpPemilik.error = null
        editNoWaPemilik.error = null
        editNoWaMerchant.error = null
        editEmail.error = null
        editTglPeresmianMerchant.error = null
        editNamaMerchant.error = null
        editAlamatMerchant.error = null
        editTitikLokasi.error = null
        editCluster.error = null
        editNamaLengkap.error = null
        editTglLahir.error = null
    }

    fun onClickRegisterMerchant() {
        setNullError()
        activity?.let { dismissKeyboard(it) }

        val id = dataMerchant?.id
        val namaMerchant = etNamaMerchant.value
        val alamatMerchant = etAlamatMerchant.value
        val lat = latitude.value
        val lng = longitude.value
        val tglPeresmianMerchant = etTglPeresmianMerchant.value
        val provinsi = listProvinsi[spinnerProvinsi.selectedItemPosition].nama
        val kabupaten = listKabupaten[spinnerKabupaten.selectedItemPosition].nama
        val kecamatan = listKecamatan[spinnerKecamatan.selectedItemPosition].nama
        val kelurahan = listKelurahan[spinnerKelurahan.selectedItemPosition].nama
        val cluster = etCluster.value
        val regional = listKelurahan[spinnerKelurahan.selectedItemPosition].REGION_OMS
        val branch = listKelurahan[spinnerKelurahan.selectedItemPosition].BRANCH
        val noHpMerchant = etHPMerchant.value
        val noWaMerchant = etWAMerchant.value
        val emailMerchant = etEmailMerchant.value
        val namaLengkap = etNamaLengkap.value
        val tglLahir = etTglLahir.value
        val noHpPemilik = etPhonePemilik.value
        val noWaPemilik = etWAPemilik.value
        val fotoProfil = etFotoProfil.value?.path
        val dataMerchant = savedData.getDataMerchant()

        if (dataMerchant != null && id != null && !namaMerchant.isNullOrEmpty() && !alamatMerchant.isNullOrEmpty() && !lat.isNullOrEmpty() && !lng.isNullOrEmpty()
            && (!provinsi.isNullOrEmpty() && provinsi != Constant.pilihProvinsi)
            && (!kabupaten.isNullOrEmpty() && kabupaten != Constant.pilihKabupaten)
            && (!kecamatan.isNullOrEmpty() && kecamatan != Constant.pilihKecamatan)
            && (!kelurahan.isNullOrEmpty() && kelurahan != Constant.pilihKelurahan)
            && !cluster.isNullOrEmpty() && !regional.isNullOrEmpty() && !branch.isNullOrEmpty()
            && !noWaMerchant.isNullOrEmpty() && noWaMerchant.take(1) == "0"
            && !noHpMerchant.isNullOrEmpty() && noHpMerchant.take(1) == "0"
            && !emailMerchant.isNullOrEmpty() && emailMerchant.matches(Regex(Constant.emailFormat))
            && !namaLengkap.isNullOrEmpty() && !tglLahir.isNullOrEmpty() && !tglPeresmianMerchant.isNullOrEmpty()
            && !noHpPemilik.isNullOrEmpty() && noHpPemilik.take(1) == "0"
            && !noWaPemilik.isNullOrEmpty() && noWaPemilik.take(1) == "0"
            && !fotoProfil.isNullOrEmpty()
            && (noHpMerchant.length in 10..13) && (noWaMerchant.length in 10..13)
            && (noHpPemilik.length in 10..13) && (noWaPemilik.length in 10..13)
        ) {
            val hpPemilik = noHpPemilik.replaceFirst("0", "+62")
            val waPemilik = noWaPemilik.replaceFirst("0", "+62")
            val hpMerchant = noHpMerchant.replaceFirst("0", "+62")
            val waMerchant = noWaMerchant.replaceFirst("0", "+62")
            isShowLoading.value = true

            dataMerchant.nama_merchant = namaMerchant
            dataMerchant.alamat_merchant = alamatMerchant
            dataMerchant.latitude = lat
            dataMerchant.longitude = lng
            dataMerchant.tgl_peresmian_merchant = tglPeresmianMerchant
            dataMerchant.provinsi = provinsi
            dataMerchant.kabupaten = kabupaten
            dataMerchant.kecamatan = kecamatan
            dataMerchant.kelurahan = kelurahan
            dataMerchant.regional = regional
            dataMerchant.branch = branch
            dataMerchant.cluster = cluster
            dataMerchant.no_hp_merchant = hpMerchant
            dataMerchant.no_wa_merchant = waMerchant
            dataMerchant.email_merchant = emailMerchant
            dataMerchant.nama_lengkap = namaLengkap
            dataMerchant.tgl_lahir = tglLahir
            dataMerchant.no_hp_pemilik = hpPemilik
            dataMerchant.no_wa_pemilik = waPemilik
            dataMerchant.status_merchant = Constant.statusActive

            updateMerchant(dataMerchant)
        } else {
            if (dataMerchant == null) {
                message.value = "Error, terjadi kesalahan saat mengambil Database"
            } else if (id == null) {
                message.value = "Error, terjadi kesalahan saat mengambil ID Merchant"
            } else if (fotoProfil.isNullOrEmpty()) {
                message.value = "Mohon upload foto profil"
            } else if (namaMerchant.isNullOrEmpty()) {
                setTextError("Error, mohon masukkan nama merchant", editNamaMerchant)
            } else if (alamatMerchant.isNullOrEmpty()) {
                setTextError("Error, mohon masukkan alamat merchant", editAlamatMerchant)
            } else if (lat.isNullOrEmpty() || lng.isNullOrEmpty()) {
                message.value = "Error, mohon pilih titik lokasi di maps"
                editAlamatMerchant.clearFocus()
                editTitikLokasi.requestFocus()
                editTitikLokasi.findFocus()
                editTitikLokasi.error = "Error, mohon pilih titik lokasi di maps"
            } else if (tglPeresmianMerchant.isNullOrEmpty()) {
                message.value = "Error, Mohon pilih tanggal peresmian merchant"
                editNamaMerchant.clearFocus()
                editAlamatMerchant.clearFocus()
                editTitikLokasi.clearFocus()
                editTglPeresmianMerchant.requestFocus()
                editTglPeresmianMerchant.findFocus()
                editTglPeresmianMerchant.error = "Error, Mohon pilih tanggal peresmian merchant"
            } else if (provinsi == Constant.pilihProvinsi) {
                message.value = "Mohon memilih salah satu Provinsi yang tersedia"
            } else if (kabupaten == Constant.pilihKabupaten) {
                message.value = "Mohon memilih salah satu Kabupaten yang tersedia"
            } else if (kecamatan == Constant.pilihKecamatan) {
                message.value = "Mohon memilih salah satu Kecamatan yang tersedia"
            } else if (kelurahan == Constant.pilihKelurahan) {
                message.value = "Mohon memilih salah satu Kelurahan yang tersedia"
            } else if (cluster.isNullOrEmpty()) {
                setTextError("Error, mohon memilih wilayah yang memiliki cluster", editCluster)
            } else if (cluster.isNullOrEmpty()) {
                setTextError("Error, mohon memilih wilayah yang memiliki cluster", editCluster)
            } else if (regional.isNullOrEmpty()) {
                setTextError("Error, wilayah yang dipilih tidak memiliki regional", editCluster)
            } else if (branch.isNullOrEmpty()) {
                setTextError("Error, wilayah yang dipilih tidak memiliki branch", editCluster)
            } else if (noHpMerchant.isNullOrEmpty()) {
                setTextError("Error, mohon masukkan nomor HP Merchant yang valid", editNoHpMerchant)
            } else if (noHpMerchant.take(1) != "0") {
                setTextError(
                    "Error, mohon masukkan nomor HP Merchant dengan awalan 0",
                    editNoHpMerchant
                )
            } else if (noHpMerchant.length !in 10..13) {
                setTextError("Error, nomor HP Merchant harus 10-13 digit", editNoHpMerchant)
            } else if (noWaMerchant.isNullOrEmpty()) {
                setTextError("Error, mohon masukkan nomor WA Merchant yang valid", editNoWaMerchant)
            } else if (noWaMerchant.take(1) != "0") {
                setTextError(
                    "Error, mohon masukkan nomor WA Merchant dengan awalan 0",
                    editNoWaMerchant
                )
            } else if (noWaMerchant.length !in 10..13) {
                setTextError("Error, nomor WA Merchant harus 10-13 digit", editNoWaMerchant)
            } else if (emailMerchant.isNullOrEmpty()) {
                setTextError("Error, mohon masukkan email", editEmail)
            } else if (!emailMerchant.matches(Regex(Constant.emailFormat))) {
                setTextError("Error, format email salah", editEmail)
            } else if (namaLengkap.isNullOrEmpty()) {
                setTextError("Error, Mohon masukkan nama lengkap", editNamaLengkap)
            } else if (tglLahir.isNullOrEmpty()) {
                message.value = "Error, Mohon pilih tanggal lahir"
                editNamaLengkap.clearFocus()
                editTglLahir.requestFocus()
                editTglLahir.findFocus()
                editTglLahir.error = "Error, Mohon pilih tanggal lahir"
            } else if (noHpPemilik.isNullOrEmpty()) {
                setTextError("Error, Mohon masukkan nomor HP Pemilik yang valid", editNoHpPemilik)
            } else if (noHpPemilik.take(1) != "0") {
                setTextError(
                    "Error, mohon masukkan nomor HP Pemilik dengan awalan 0",
                    editNoHpPemilik
                )
            } else if (noHpPemilik.length !in 10..13) {
                setTextError("Error, nomor HP Pemilik harus 10-13 digit", editNoHpPemilik)
            } else if (noWaPemilik.isNullOrEmpty()) {
                setTextError("Error, Mohon masukkan nomor WA Pemilik yang valid", editNoWaPemilik)
            } else if (noWaPemilik.take(1) != "0") {
                setTextError(
                    "Error, mohon masukkan nomor WA Pemilik dengan awalan 0",
                    editNoWaPemilik
                )
            } else if (noWaPemilik.length !in 10..13) {
                setTextError("Error, nomor WA Pemilik harus 10-13 digit", editNoWaPemilik)
            }
        }
    }

    private fun updateMerchant(merchant: ModelMerchant) {
        RetrofitUtils.updateMerchant(merchant,
            object : Callback<ModelResponse> {
                override fun onResponse(
                    call: Call<ModelResponse>,
                    response: Response<ModelResponse>
                ) {
                    isShowLoading.value = false

                    val result = response.body()

                    if (result?.message == Constant.reffSuccessRegister) {
                        message.value = "Berhasil edit profil"
                        savedData.setDataObject(merchant, Constant.reffMerchant)
                        navController.popBackStack()
                    } else {
                        if (result?.message == "Nomor HP Merchant sudah digunakan") {
                            setTextError(
                                "Error, nomor HP Merchant sudah digunakan",
                                editNoHpMerchant
                            )
                        } else {
                            message.value = result?.message
                        }
                    }
                }

                override fun onFailure(
                    call: Call<ModelResponse>,
                    t: Throwable
                ) {
                    isShowLoading.value = false
                    message.value = t.message
                }
            })
    }
}