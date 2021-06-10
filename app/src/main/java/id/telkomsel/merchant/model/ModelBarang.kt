package id.telkomsel.merchant.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ModelBarang(
    var id: Int = 0,
    var nama: String = "",
    var jenis: String = "",
    var harga: Long = 0,
    var created_at: String = "",
    var updated_at: String = ""
    ) : Parcelable