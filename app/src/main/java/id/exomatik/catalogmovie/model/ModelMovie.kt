package id.exomatik.catalogmovie.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ModelMovie(
    var success: Boolean = true,
    var status_message: String = "",
    var id: Int = 0,
    var title: String = "",
    var poster_path: String = "",
    var backdrop_path: String = "",
    var vote_average: String = "",
    var adult: String = "",
    var original_title: String = "",
    var overview: String = "",
    var release_date: String = "",
    var companies: String = "",
    var genres: List<ModelGenre> = emptyList(),
    var production_companies: List<ModelCompanies> = emptyList(),
) : Parcelable