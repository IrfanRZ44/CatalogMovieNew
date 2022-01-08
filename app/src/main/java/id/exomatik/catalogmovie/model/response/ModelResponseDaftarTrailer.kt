package id.exomatik.catalogmovie.model.response

import android.os.Parcelable
import id.exomatik.catalogmovie.model.ModelMovie
import id.exomatik.catalogmovie.model.ModelReview
import id.exomatik.catalogmovie.model.ModelTrailer
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ModelResponseDaftarTrailer(
    var id: Int = 0,
    var results: List<ModelTrailer> = emptyList(),
    ) : Parcelable