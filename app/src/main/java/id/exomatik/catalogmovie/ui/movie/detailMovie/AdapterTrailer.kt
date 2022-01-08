package id.exomatik.catalogmovie.ui.movie.detailMovie

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.request.CachePolicy
import id.exomatik.catalogmovie.R
import id.exomatik.catalogmovie.model.ModelTrailer
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubeStandalonePlayer
import com.google.android.youtube.player.YouTubeThumbnailLoader
import com.google.android.youtube.player.YouTubeThumbnailLoader.OnThumbnailLoadedListener
import com.google.android.youtube.player.YouTubeThumbnailView

class AdapterTrailer(context: Context, var dataVideo: List<ModelTrailer>) : RecyclerView.Adapter<AdapterTrailer.VideoInfoHolder>() {
    var ctx: Context = context
    private val videoCode = "XfP31eWXli4"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoInfoHolder {
        val itemView: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_video_youtube, parent, false)
        return VideoInfoHolder(itemView)
    }

    override fun onBindViewHolder(holder: VideoInfoHolder, position: Int) {
        val onThumbnailLoadedListener: OnThumbnailLoadedListener =
            object : OnThumbnailLoadedListener {
                override fun onThumbnailError(
                    youTubeThumbnailView: YouTubeThumbnailView,
                    errorReason: YouTubeThumbnailLoader.ErrorReason) {

                }

                override fun onThumbnailLoaded(
                    youTubeThumbnailView: YouTubeThumbnailView,
                    s: String
                ) {
                    youTubeThumbnailView.visibility = View.VISIBLE
                    holder.relativeLayoutOverYouTubeThumbnailView.visibility = View.VISIBLE
                }
            }
        holder.textTitle.text = dataVideo[position].name
        holder.imgThumbnail.load("https://img.youtube.com/vi/${dataVideo[position].key}/0.jpg") {
            crossfade(true)
            placeholder(R.drawable.ic_camera_white)
            error(R.drawable.ic_camera_white)
            fallback(R.drawable.ic_camera_white)
            memoryCachePolicy(CachePolicy.ENABLED)
        }

//        holder.youTubeThumbnailView.initialize(
//            videoCode,
//            object : YouTubeThumbnailView.OnInitializedListener {
//                override fun onInitializationSuccess(
//                    youTubeThumbnailView: YouTubeThumbnailView,
//                    youTubeThumbnailLoader: YouTubeThumbnailLoader
//                ) {
//                    youTubeThumbnailLoader.setVideo(dataVideo[position].key)
//                    youTubeThumbnailLoader.setOnThumbnailLoadedListener(onThumbnailLoadedListener)
//                }
//
//                override fun onInitializationFailure(
//                    youTubeThumbnailView: YouTubeThumbnailView,
//                    youTubeInitializationResult: YouTubeInitializationResult
//                ) {
//                    //write something for failure
//                }
//            })
    }

    override fun getItemCount(): Int {
        return dataVideo.size
    }

    inner class VideoInfoHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var relativeLayoutOverYouTubeThumbnailView: RelativeLayout
        var imgThumbnail: AppCompatImageView
        var textTitle: AppCompatTextView = itemView.findViewById<View>(R.id.text_title) as AppCompatTextView
        private var playButton: ImageView = itemView.findViewById<View>(R.id.btnYoutube_player) as ImageView
        override fun onClick(v: View) {
            val intent = YouTubeStandalonePlayer.createVideoIntent(
                ctx as Activity,
                videoCode,
                dataVideo[layoutPosition].key
            )
            ctx.startActivity(intent)
        }

        init {
            playButton.setOnClickListener(this)
            relativeLayoutOverYouTubeThumbnailView =
                itemView.findViewById<View>(R.id.relativeLayout_over_youtube_thumbnail) as RelativeLayout
            imgThumbnail = itemView.findViewById<View>(R.id.imgThumbnail) as AppCompatImageView
        }
    }

}