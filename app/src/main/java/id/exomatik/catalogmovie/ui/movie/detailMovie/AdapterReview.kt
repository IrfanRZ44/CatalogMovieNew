package id.exomatik.catalogmovie.ui.movie.detailMovie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.request.CachePolicy
import coil.transform.CircleCropTransformation
import id.exomatik.catalogmovie.R
import id.exomatik.catalogmovie.model.ModelReview
import id.exomatik.catalogmovie.utils.adapter.showLog
import kotlinx.android.synthetic.main.item_review.view.*

class AdapterReview(
    private val listKelas: ArrayList<ModelReview>,
    private val onClick: (ModelReview) -> Unit
) : RecyclerView.Adapter<AdapterReview.AfiliasiHolder>(){

    inner class AfiliasiHolder(private val viewItem : View) : RecyclerView.ViewHolder(viewItem){
        fun bindAfiliasi(item: ModelReview){
            viewItem.textNama.text = item.author_details?.username?:"-"
            viewItem.textReview.text = item.content
            viewItem.textDate.text = item.created_at.take(10)

            viewItem.textSelengkapnya.setOnClickListener {
                onClick(item)
            }

            val fotoPath = item.author_details?.avatar_path?.replaceFirst("/", "")
            showLog(fotoPath)

            viewItem.imgFoto.load(fotoPath) {
                crossfade(true)
                placeholder(R.drawable.ic_image_gray)
                transformations(CircleCropTransformation())
                error(R.drawable.ic_image_gray)
                fallback(R.drawable.ic_image_gray)
                memoryCachePolicy(CachePolicy.ENABLED)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AfiliasiHolder {
        return AfiliasiHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_review, parent, false))
    }
    override fun getItemCount(): Int = listKelas.size
    override fun onBindViewHolder(holder: AfiliasiHolder, position: Int) {
        holder.bindAfiliasi(listKelas[position])
    }
}
