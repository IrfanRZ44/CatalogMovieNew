package id.exomatik.catalogmovie.model.response

import android.os.Parcelable
import id.exomatik.catalogmovie.model.ModelMovie
import id.exomatik.catalogmovie.model.ModelReview
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ModelResponseDaftarReview(
    var id: Int = 0,
    var total_pages: Int = 0,
    var results: List<ModelReview> = emptyList(),
    ) : Parcelable