package id.exomatik.catalogmovie.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ModelKategori(
    var id: Int = 0,
    var isSelected: Boolean = false,
    var nama_kategori: String = "",
) : Parcelable