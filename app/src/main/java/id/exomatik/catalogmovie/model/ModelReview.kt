package id.exomatik.catalogmovie.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ModelReview(
    var id: String = "",
    var author: String = "",
    var content: String = "",
    var created_at: String = "",
    var url: String = "",
    var author_details: ModelAuthor? = null,
    ) : Parcelable