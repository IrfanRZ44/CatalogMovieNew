package id.exomatik.catalogmovie.ui.movie.detailMovie

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.exomatik.catalogmovie.R
import id.exomatik.catalogmovie.model.ModelGenre
import kotlinx.android.synthetic.main.item_kategori.view.*

class AdapterGenre(
    private val listItem: ArrayList<ModelGenre>
) : RecyclerView.Adapter<AdapterGenre.AfiliasiHolder>(){
    inner class AfiliasiHolder(private val viewItem : View) : RecyclerView.ViewHolder(viewItem){
        @SuppressLint("SetTextI18n")
        fun bindAfiliasi(item: ModelGenre) {
            viewItem.textNama.text = item.name

            viewItem.cardView.setCardBackgroundColor(Color.RED)
            viewItem.textNama.setTextColor(Color.WHITE)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AfiliasiHolder {
        return AfiliasiHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_kategori, parent, false))
    }

    override fun getItemCount(): Int = listItem.size
    override fun onBindViewHolder(holder: AfiliasiHolder, position: Int) {
        holder.bindAfiliasi(listItem[position])
    }
}
