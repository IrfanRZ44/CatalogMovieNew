package id.telkomsel.merchant.utils

object Constant {
    const val instalApp = "Dapatkan update tabligh dari ustadz favoritmu hanya di Balligh. " +
            "Install Aplikasinya sekarang juga </downloadlink>"
    const val appName = "Merchant"
    const val reffUser = "user"
    const val reffMerchant = "merchant"
    const val reffProduk = "produk"
    const val reffLoginMerchantPhone = "loginMerchantPhone"
    const val reffCreateMerchant = "createMerchant"
    const val reffCreateProduk = "createProduk"
    const val reffEditProduk = "editProduk"
    const val reffEditFotoProfilProduk = "editFotoProfilProduk"
    const val reffCreateFotoProduk = "createFotoProduk"
    const val reffDeleteFotoProduk = "deleteFotoProduk"
    const val reffGetDaftarFotoProduk = "getDaftarFotoProduk"
    const val reffUpdateMerchant = "updateMerchant"
    const val reffUpdateAdmin = "updateAdmin"
    const val reffValidateNewMerchant = "validateNewMerchant"
    const val reffValidateNewMerchantPhone = "validateNewMerchantPhone"
    const val reffUpdatePassword = "updatePasswordMerchant"
    const val reffLogoutMerchant = "logoutMerchant"
    const val reffGetDataMerchant = "getDataMerchant"
    const val reffGetDataMerchantById = "getDataMerchantById"
    const val reffForgetPasswordMerchantUsername = "forgetPasswordMerchantUsername"
    const val reffForgetPasswordMerchantPhone = "forgetPasswordMerchantPhone"
    const val reffDaftarMerchantByAdmin = "getDaftarMerchantByAdmin"
    const val reffDaftarProdukByMerchant = "getDaftarProdukByMerchant"
    const val reffDaftarPickMerchant = "getPickMerchant"
    const val reffDaftarKategori = "getDaftarKategori"
    const val reffDaftarTopSubKategori = "getTopSubKategori"
    const val reffDataKategori = "getDataKategori"
    const val reffDaftarSubKategoriByMerchant = "getDaftarSubKategoriByMerchant"
    const val reffDaftarSubKategori = "getDaftarSubKategori"
    const val reffDaftarSubKategoriFilterKategori = "getDaftarSubKategoriFilterKategori"
    const val reffUpdateStatusMerchant = "updateStatusMerchant"
    const val reffUpdateStatusProduk = "updateStatusProduk"

    const val reffProvinsi = "getProvinsi"
    const val reffKabupaten = "getKabupatenByProvinsi"
    const val reffKecamatan = "getKecamatanByKabupaten"
    const val reffKelurahan = "getKelurahanByKecamatan"
    const val reffInfoApps = "getInfoApps"
    const val reffCheckToken = "checkTokenMerchant"

    const val reffDataModel = "dataModel"
    const val reffId = "id"
    const val reffFolder = "folder"
    const val reffName = "name"

    //    const val reffURL = "https://admin-tokobonus.com"
    const val reffURL = "http://192.168.1.4:8000"
//    const val reffURL = "http://192.168.43.186:8000"
    const val reffBaseURL = "$reffURL/api/"
    const val reffBaseURLUploadFoto = "$reffURL/api/uploadFoto"

    const val defaultLatitude = -5.1365727
    const val defaultLongitude = 119.4394061

    const val reffSuccess = "Success"
    const val reffSuccessRegister = "Berhasil mendaftar, mohon tunggu proses verifikasi dalam waktu 1x24 jam"
    const val reffDataCanBeUsed = "Data bisa digunakan untuk mendaftar!"

    const val codeRequestFoto = 100
    const val codeRequestGallery = 1
    const val codeRequestScanner = 100
    const val codeRequestLocation = 101
    const val codeRequestPhone = 1
    const val codeRequestLocationUpdate = 10
    const val codeRequestResultCrop = 400

    const val defaultTempFoto = "https://www.google.co.id/images/branding/googlelogo/2x/googlelogo_color_160x56dp.png"
    const val timeFormat = "HH:mm"
    const val dateFormat1 = "dd-M-yyyy"
    const val dateFormat2 = "dd-MMM-yyyy"
    const val dateFormat3 = "dd MMM yyyy"
    const val dateFormat4 = "yyyy-MM-dd"
    const val emailFormat = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

    const val phone = "noHp"
    const val nama = "nama"
    const val token = "token"
    const val username = "username"
    const val provinsi = "provinsi"
    const val noHp = "noHp"

    const val personal = "Personal"

    const val ahlanWaSahlan = "Ahlan Wa Sahlan"
    const val noMessage = "Silahkan mengirim pesan untuk memulai obrolan"
    const val noListMessage = "Afwan, Anda belum mempunyai pesan"
    const val noData = "Afwan, tidak ada data yang ditemukan"
    const val noRequest = "Afwan, belum ada permintaan verifikasi"
    const val alertLogout = "Apakah Anda yakin ingin keluar dari akun?"
    const val alertHapus = "Apakah Anda yakin ingin menghapus data akun anda?"
    const val batalPermintaan = "Batalkan permintaan"
    const val batalAfiliasi = "PERMINTAAN TERKIRIM / BATALKAN"
    const val batalNaib = "PERMINTAAN NAIB TERKIRIM / BATALKAN"
    const val attention = "Perhatian"
    const val yakin = "Yakin"
    const val yakinBatal = "Yakin ingin membatalkan permintaan?"
    const val iya = "Iya"
    const val baik = "Baik"
    const val batal = "Batal"
    const val tolak = "TOLAK"
    const val statusVerifikasi = "statusVerifikasi"
    const val terima = "TERIMA"
    const val tidak = "Tidak"
    const val selesai = "Selesai"
    const val lewati = "Lewati"
    const val keluar = "Keluar"
    const val online = "Online"
    const val offline = "Offline"
    const val wait = "Mohon tunggu..."

    const val terkonfirmasi = "Terkonfirmasi"
    const val belum_terkonfirmasi = "Belum terkonfirmasi"
    const val hapus_pesan = "Hapus pesan?"

    const val manual = "manual"
    const val invalid = "invalid"
    const val status = "status"
    const val active = "active"
    const val pending = "pending"
    const val switch = "switch"
    const val request = "request"

    const val rejected = "rejected"
    const val inactive = "inactive"
    const val read = "read"
    const val unread = "unread"
    const val sended = "sended"
    const val message = "message"

    const val statusRequest = "request"
    const val statusDeclined = "declined"
    const val statusActive = "active"
    const val statusBanned = "banned"

    const val statusDiproses = "Diproses"
    const val statusDisetujui = "Disetujui"
    const val statusDitolak = "Ditolak"
    const val statusPromoSelesai = "Promo Selesai"
    const val statusStokHabis = "Stok Habis"

    const val levelUser = "User"
    const val levelChannel = "Channel"
    const val levelCSO = "CSO"
    const val levelSBP = "SBP"
    const val levelMerchant = "Merchant"

    const val requestMerchant = "Request Merchant"
    const val pendaftaranMerchant = "Request Merchant"
    const val tervalidasiMerchant = "Tervalidasi Merchant"
    const val requestProduk = "Tervalidasi Produk"
    const val tervalidasiProduk = "Request Produk"
    const val akun = "Akun"

    const val dataModelFotoProfil = "FotoProfil"

    const val folderFotoProfil = "/assets/images/merchant/profil/"
    const val pendaftaranBerhasil = "Pendaftaran Berhasil"
    const val berhasil = "Berhasil"

    const val pilihKategori = "Pilih Kategori"
    const val pilihProvinsi = "Pilih Provinsi"
    const val pilihKabupaten = "Pilih Kota/Kabupaten"
    const val pilihProduk = "Pilih Produk"
    const val pilihKecamatan = "Pilih Kecamatan"
    const val pilihKelurahan = "Pilih Kelurahan"

    const val noDataWilayah = "Tidak ada data wilayah"
    const val noDataKategori = "Tidak ada data kategori"
    const val noMerchantRequest = "Semua Merchant telah divalidasi"
    const val noMerchant = "Maaf, belum ada data merchant"
    const val noProdukRequest = "Semua produk telah divalidasi"
    const val noProduk = "Maaf, belum ada data produk"
    const val noKategori = "Maaf, belum ada data kategori"

    const val konfirmasi = "Konfirmasi"


    const val noHpPemilik = "Nomor HP Pemilik"
    const val noHpMerchant = "Nomor HP Merchant"
    const val formSelectNumber = "Mohon pilih nomor HP untuk verifikasi kode OTP"

    const val formUpdateMerchant = "Pendaftaran akun merchant Anda ditolak, dengan alasan"
    const val loginBerhasil = "Login Berhasil"

}