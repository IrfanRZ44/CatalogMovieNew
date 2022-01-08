package id.exomatik.catalogmovie.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ModelTrailer(
    var id: String = "",
    var key: String = "",
    var name: String = ""
    ) : Parcelable