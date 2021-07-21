package id.telkomsel.merchant.utils

import android.app.Activity
import id.telkomsel.merchant.model.ModelMerchant
import id.telkomsel.merchant.model.ModelPelanggan
import id.telkomsel.merchant.model.response.*
import net.gotev.uploadservice.protocols.multipart.MultipartUploadRequest
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Part
import java.io.File

object RetrofitUtils{
    private val retrofit = Retrofit.Builder()
        .baseUrl(RetrofitApi.baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val api = retrofit.create(RetrofitApi::class.java)

    fun getDaftarProvinsi(callback: Callback<ModelResponseWilayah>){
        val call = api.getDaftarProvinsi()
        call.enqueue(callback)
    }

    fun getDaftarKabupaten(idProvinsi: Long, callback: Callback<ModelResponseWilayah>){
        val call = api.getDaftarKabupaten(idProvinsi)
        call.enqueue(callback)
    }

    fun getDaftarKecamatan(idKabupaten: Long, callback: Callback<ModelResponseWilayah>){
        val call = api.getDaftarKecamatan(idKabupaten)
        call.enqueue(callback)
    }

    fun getDaftarKelurahan(idKecamatan: Long, callback: Callback<ModelResponseWilayah>){
        val call = api.getDaftarKelurahan(idKecamatan)
        call.enqueue(callback)
    }

    fun getInfoApps(callback: Callback<ModelResponseInfoApps>){
        val call = api.getInfoApps()
        call.enqueue(callback)
    }

    fun checkToken(username: String, token: String, callback: Callback<ModelResponseMerchant>){
        val call = api.checkToken(username, token)
        call.enqueue(callback)
    }

    fun uploadFoto(dataModel: String, folder: String, fileName: String, id: String, filePath: String, activity: Activity){
        val uploadRequest = MultipartUploadRequest(activity, Constant.reffBaseURLUploadFoto)
        uploadRequest.setMethod("POST")
        uploadRequest.addFileToUpload(
            filePath = filePath,
            parameterName = "image"
        )
        uploadRequest.addParameter(Constant.reffId, id)
        uploadRequest.addParameter(Constant.reffDataModel, dataModel)
        uploadRequest.addParameter(Constant.reffFolder, folder)
        uploadRequest.addParameter(Constant.reffName, fileName)
        uploadRequest.startUpload()
    }

    fun loginMerchantUsername(username: String, password: String, token: String, callback: Callback<ModelResponseMerchant>){
        val call = api.loginMerchantUsername(username, password, token)
        call.enqueue(callback)
    }

    fun loginMerchantPhone(phone: String, password: String, token: String, callback: Callback<ModelResponseMerchant>){
        val call = api.loginMerchantPhone(phone, token, password)
        call.enqueue(callback)
    }

    fun loginPelangganUsername(username: String, password: String, token: String, callback: Callback<ModelResponsePelanggan>){
        val call = api.loginPelangganUsername(username, password, token)
        call.enqueue(callback)
    }

    fun loginPelangganPhone(phone: String, password: String, token: String, callback: Callback<ModelResponsePelanggan>){
        val call = api.loginPelangganPhone(phone, token, password)
        call.enqueue(callback)
    }

    fun createMerchant(dataMerchant: ModelMerchant, callback: Callback<ModelResponse>){
        val call = api.createMerchant(dataMerchant.username, dataMerchant.kategori_id, dataMerchant.nama_merchant,
            dataMerchant.alamat_merchant, dataMerchant.latitude, dataMerchant.longitude, dataMerchant.tgl_peresmian_merchant,
            dataMerchant.provinsi, dataMerchant.kabupaten, dataMerchant.kecamatan, dataMerchant.kelurahan,
            dataMerchant.regional, dataMerchant.branch, dataMerchant.cluster,
            dataMerchant.no_hp_merchant, dataMerchant.no_wa_merchant, dataMerchant.verified_phone,
            dataMerchant.email_merchant, dataMerchant.password, dataMerchant.nama_lengkap, dataMerchant.tgl_lahir,
            dataMerchant.no_hp_pemilik, dataMerchant.no_wa_pemilik,
        )
        call.enqueue(callback)
    }

    fun createPelanggan(dataPelanggan: ModelPelanggan, urlFoto: String, callback: Callback<ModelResponsePelanggan>){
        val fileProduk = File(urlFoto)
        val foto = MultipartBody.Part.createFormData("url_foto", fileProduk.name, RequestBody.create(
            MediaType.get("image/*"), fileProduk))
        val username = RequestBody.create(MediaType.get("text/plain"), dataPelanggan.username)
        val nama = RequestBody.create(MediaType.get("text/plain"), dataPelanggan.nama)
        val token = RequestBody.create(MediaType.get("text/plain"), dataPelanggan.token)
        val alamat = RequestBody.create(MediaType.get("text/plain"), dataPelanggan.alamat)
        val nomorHP = RequestBody.create(MediaType.get("text/plain"), dataPelanggan.nomor_hp)
        val nomorWA = RequestBody.create(MediaType.get("text/plain"), dataPelanggan.nomor_wa)
        val verifiedPhone = RequestBody.create(MediaType.get("text/plain"), dataPelanggan.verified_phone)
        val password = RequestBody.create(MediaType.get("text/plain"), dataPelanggan.password)
        val tglLahir = RequestBody.create(MediaType.get("text/plain"), dataPelanggan.tgl_lahir)

        val call = api.createPelanggan(username, nama, token, alamat,
            nomorHP, nomorWA, verifiedPhone, password, tglLahir, foto
        )
        call.enqueue(callback)
    }

    fun updateMerchant(dataMerchant: ModelMerchant, callback: Callback<ModelResponse>){
        val call = api.updateMerchant(dataMerchant.id, dataMerchant.nama_merchant, dataMerchant.kategori_id,
            dataMerchant.alamat_merchant,
            dataMerchant.status_merchant, dataMerchant.latitude, dataMerchant.longitude, dataMerchant.tgl_peresmian_merchant,
            dataMerchant.provinsi, dataMerchant.kabupaten, dataMerchant.kecamatan, dataMerchant.kelurahan,
            dataMerchant.regional, dataMerchant.branch, dataMerchant.cluster, dataMerchant.no_hp_merchant,
            dataMerchant.no_wa_merchant, dataMerchant.email_merchant,
            dataMerchant.nama_lengkap, dataMerchant.tgl_lahir,
            dataMerchant.no_hp_pemilik, dataMerchant.no_wa_pemilik,
            dataMerchant.status_merchant
        )
        call.enqueue(callback)
    }

    fun updateAdmin(dataMerchant: ModelMerchant, callback: Callback<ModelResponse>){
        val call = api.updateAdmin(dataMerchant.id, dataMerchant.nama_merchant,
            dataMerchant.alamat_merchant,
            dataMerchant.status_merchant, dataMerchant.latitude, dataMerchant.longitude,
            dataMerchant.provinsi, dataMerchant.kabupaten, dataMerchant.kecamatan, dataMerchant.kelurahan,
            dataMerchant.regional, dataMerchant.branch, dataMerchant.cluster, dataMerchant.no_hp_merchant,
            dataMerchant.no_wa_merchant, dataMerchant.email_merchant,
            dataMerchant.nama_lengkap, dataMerchant.tgl_lahir,
            dataMerchant.no_hp_pemilik, dataMerchant.no_wa_pemilik
        )
        call.enqueue(callback)
    }

    fun validateNewMerchant(dataMerchant: ModelMerchant, callback: Callback<ModelResponse>){
        val call = api.validateNewMerchant(dataMerchant.username, dataMerchant.no_hp_merchant)
        call.enqueue(callback)
    }

    fun validateNewPelanggan(dataPelanggan: ModelPelanggan, callback: Callback<ModelResponse>){
        val call = api.validateNewPelanggan(dataPelanggan.username, dataPelanggan.nomor_hp)
        call.enqueue(callback)
    }

    fun validateNewMerchantPhone(dataMerchant: ModelMerchant, callback: Callback<ModelResponse>){
        val call = api.validateNewMerchantPhone(dataMerchant.no_hp_merchant)
        call.enqueue(callback)
    }

    fun logoutMerchant(username: String, callback: Callback<ModelResponse>){
        val call = api.logoutMerchant(username)
        call.enqueue(callback)
    }

    fun logoutPelanggan(username: String, callback: Callback<ModelResponse>){
        val call = api.logoutPelanggan(username)
        call.enqueue(callback)
    }

    fun updatePassword(id: Int,
                       passwordNew: String,
                       callback: Callback<ModelResponse>){
        val call = api.updatePassword(id, passwordNew)
        call.enqueue(callback)
    }

    fun getDataMerchant(username: String, callback: Callback<ModelResponseMerchant>){
        val call = api.getDataMerchant(username)
        call.enqueue(callback)
    }

    fun getDataMerchantById(merchant_id: String, callback: Callback<ModelResponseMerchant>){
        val call = api.getDataMerchantById(merchant_id)
        call.enqueue(callback)
    }

    fun forgetPasswordMerchantUsername(username: String, callback: Callback<ModelResponseMerchant>){
        val call = api.forgetPasswordMerchantUsername(username)
        call.enqueue(callback)
    }

    fun forgetPasswordMerchantPhone(phone: String, callback: Callback<ModelResponseMerchant>){
        val call = api.forgetPasswordMerchantPhone(phone)
        call.enqueue(callback)
    }

    fun getDaftarMerchantByAdmin(cluster: String, userRequest: String, startPage: Int, status: String, search: String?, callback: Callback<ModelResponseDaftarMerchant>){
        val call = api.getDaftarMerchantByAdmin(cluster, userRequest, startPage, status, search)
        call.enqueue(callback)
    }

    fun getDaftarProdukByMerchant(merchant_id: String?, cluster: String?, level: String, startPage: Int, status: String,
                                  search: String?, sub_kategori_id: String?, stok: Int,
                                  isKadaluarsa: Boolean, callback: Callback<ModelResponseDaftarProduk>){
        val call = api.getDaftarProdukByMerchant(merchant_id, cluster, level, startPage, status, search,
            sub_kategori_id, stok, isKadaluarsa)
        call.enqueue(callback)
    }

    fun getDaftarProdukByPelanggan(startPage: Int, search: String?, sub_kategori_id: String?,
                                   callback: Callback<ModelResponseDaftarProduk>){
        val call = api.getDaftarProdukByPelanggan(startPage, search, sub_kategori_id)
        call.enqueue(callback)
    }

    fun getPickMerchant(cluster: String, userRequest: String, startPage: Int, search: String?, callback: Callback<ModelResponseDaftarMerchant>){
        val call = api.getPickMerchant(cluster, userRequest, startPage, search)
        call.enqueue(callback)
    }

    fun getDaftarKategori(callback: Callback<ModelResponseDaftarKategori>){
        val call = api.getDaftarKategori()
        call.enqueue(callback)
    }

    fun getDaftarSubKategoriByMerchant(kategori_id: Int, callback: Callback<ModelResponseDaftarKategori>){
        val call = api.getDaftarSubKategoriByMerchant(kategori_id)
        call.enqueue(callback)
    }

    fun getTopSubKategori(callback: Callback<ModelResponseDaftarKategori>){
        val call = api.getTopSubKategori()
        call.enqueue(callback)
    }

    fun getDataKategori(kategori_id: Int, callback: Callback<ModelResponseDataKategori>){
        val call = api.getDataKategori(kategori_id)
        call.enqueue(callback)
    }

    fun getDaftarSubKategori(callback: Callback<ModelResponseDaftarKategori>){
        val call = api.getDaftarSubKategori()
        call.enqueue(callback)
    }

    fun getDaftarSubKategoriFilterKategori(callback: Callback<ModelResponseDaftarKategori>){
        val call = api.getDaftarSubKategoriFilterKategori()
        call.enqueue(callback)
    }

    fun updateStatusMerchant(id: Int, status_merchant: String, comment: String, callback: Callback<ModelResponse>){
        val call = api.updateStatusMerchant(id, status_merchant, comment)
        call.enqueue(callback)
    }

    fun updateStatusProduk(id: Int, status: String, comment: String, callback: Callback<ModelResponse>){
        val call = api.updateStatusProduk(id, status, comment)
        call.enqueue(callback)
    }

    fun createProduk(status: RequestBody, merchant_id: RequestBody, kategori_id: RequestBody,
                     sub_kategori_id: RequestBody, tgl_kadaluarsa: RequestBody,
                     stok: RequestBody, nama: RequestBody, harga: RequestBody,
                     promo: RequestBody, poin: RequestBody,
                     deskripsi: RequestBody, regional: RequestBody, branch: RequestBody,
                     cluster: RequestBody, url_foto: MultipartBody.Part,
                     callback: Callback<ModelResponse>){
        val call = api.createProduk(status, merchant_id, kategori_id, sub_kategori_id, tgl_kadaluarsa,
            stok, nama, harga, promo, poin, deskripsi, regional, branch, cluster, url_foto)
        call.enqueue(callback)
    }

    fun editProduk(status: String, produk_id: String, merchant_id: String, kategori_id: String,
                     sub_kategori_id: String, tgl_kadaluarsa: String,
                     stok: String, nama: String, harga: String,
                     promo: String, poin: String,
                     deskripsi: String, regional: String, branch: String,
                     cluster: String,
                     callback: Callback<ModelResponse>){
        val call = api.editProduk(status, produk_id, merchant_id, kategori_id, sub_kategori_id, tgl_kadaluarsa,
            stok, nama, harga, promo, poin, deskripsi, regional, branch, cluster)
        call.enqueue(callback)
    }

    fun editFotoProfilProduk(produk_id: RequestBody, level: RequestBody,
                         url_foto: MultipartBody.Part,
                         callback: Callback<ModelResponse>){
        val call = api.editFotoProfilProduk(produk_id, level, url_foto)
        call.enqueue(callback)
    }

    fun createFotoProduk(produk_id: RequestBody, level: RequestBody,
                     url_foto: MultipartBody.Part,
                     callback: Callback<ModelResponse>){
        val call = api.createFotoProduk(produk_id, level, url_foto)
        call.enqueue(callback)
    }

    fun deleteFotoProduk(produk_id: Int, callback: Callback<ModelResponse>){
        val call = api.deleteFotoProduk(produk_id)
        call.enqueue(callback)
    }

    fun getDaftarFotoProduk(produk_id: Int, callback: Callback<ModelResponseDaftarFotoProduk>){
        val call = api.getDaftarFotoProduk(produk_id)
        call.enqueue(callback)
    }
}