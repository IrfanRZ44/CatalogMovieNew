package id.exomatik.catalogmovie.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ModelAuthor(
    var name: String = "",
    var username: String = "",
    var avatar_path: String = "",
    ) : Parcelable