package id.exomatik.catalogmovie.ui.movie.listMovie

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.request.CachePolicy
import id.exomatik.catalogmovie.R
import id.exomatik.catalogmovie.model.ModelMovie
import id.exomatik.catalogmovie.utils.Constant
import kotlinx.android.synthetic.main.item_movie.view.*

class AdapterMovie(
    private val listKelas: ArrayList<ModelMovie>,
    private val onClick: (ModelMovie) -> Unit
) : RecyclerView.Adapter<AdapterMovie.AfiliasiHolder>(){

    inner class AfiliasiHolder(private val viewItem : View) : RecyclerView.ViewHolder(viewItem){
        @SuppressLint("SetTextI18n")
        fun bindAfiliasi(item: ModelMovie){
            viewItem.textNama.text = item.title
            viewItem.textRating.text = "${item.vote_average} Rating"

            viewItem.setOnClickListener {
                onClick(item)
            }

            viewItem.imgFoto.load("${Constant.reffBaseURLPosterPath}${item.poster_path}") {
                crossfade(true)
                placeholder(R.drawable.ic_image_gray)
                error(R.drawable.ic_image_gray)
                fallback(R.drawable.ic_image_gray)
                memoryCachePolicy(CachePolicy.ENABLED)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AfiliasiHolder {
        return AfiliasiHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false))
    }
    override fun getItemCount(): Int = listKelas.size
    override fun onBindViewHolder(holder: AfiliasiHolder, position: Int) {
        holder.bindAfiliasi(listKelas[position])
    }
}
