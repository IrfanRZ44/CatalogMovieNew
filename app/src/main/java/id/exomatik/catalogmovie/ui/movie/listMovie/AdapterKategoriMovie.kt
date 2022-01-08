package id.exomatik.catalogmovie.ui.movie.listMovie

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.exomatik.catalogmovie.R
import id.exomatik.catalogmovie.model.ModelKategori
import kotlinx.android.synthetic.main.item_kategori.view.*

class AdapterKategoriMovie(
    private val listItem: ArrayList<ModelKategori>,
    private val onClickItem: (ModelKategori) -> Unit
) : RecyclerView.Adapter<AdapterKategoriMovie.AfiliasiHolder>(){
    inner class AfiliasiHolder(private val viewItem : View) : RecyclerView.ViewHolder(viewItem){
        @SuppressLint("SetTextI18n")
        fun bindAfiliasi(item: ModelKategori) {
            viewItem.textNama.text = item.nama_kategori

            if (item.isSelected){
                viewItem.cardView.setCardBackgroundColor(Color.RED)
                viewItem.textNama.setTextColor(Color.WHITE)
            }
            else{
                viewItem.cardView.setCardBackgroundColor(Color.WHITE)
                viewItem.textNama.setTextColor(Color.BLACK)
            }

            viewItem.setOnClickListener {
                onClickItem(item)
                for (i in listItem.indices){
                    listItem[i].isSelected = false
                }
                item.isSelected = true
                notifyDataSetChanged()
            }
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
