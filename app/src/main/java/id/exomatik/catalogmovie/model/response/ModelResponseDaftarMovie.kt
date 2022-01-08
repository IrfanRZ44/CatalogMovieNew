package id.exomatik.catalogmovie.model.response

import android.os.Parcelable
import id.exomatik.catalogmovie.model.ModelMovie
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ModelResponseDaftarMovie(
    var results: List<ModelMovie> = emptyList(),
    var total_pages: Int = 0
    ) : Parcelable