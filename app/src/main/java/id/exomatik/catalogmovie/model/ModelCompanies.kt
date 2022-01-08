package id.exomatik.catalogmovie.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ModelCompanies(
    var id: Int = 0,
    var logo_path: String = "",
    var name: String = "",
    var origin_country: String = "",
    ) : Parcelable