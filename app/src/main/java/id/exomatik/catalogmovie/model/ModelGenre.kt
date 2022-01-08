package id.exomatik.catalogmovie.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ModelGenre(
    var id: Int = 0,
    var name: String = ""
    ) : Parcelable