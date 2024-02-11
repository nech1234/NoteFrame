import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.noteframe.databinding.ItemImageBinding

class NoteRVAdapter(private val images: List<Int>) : RecyclerView.Adapter<NoteRVAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): NoteRVAdapter.ImageViewHolder {
        val binding : ItemImageBinding = ItemImageBinding.inflate(LayoutInflater.from(viewGroup.context),viewGroup,false)
        return ImageViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(images[position])
    }

    inner class ImageViewHolder(private val binding : ItemImageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(imageResId: Int) {
            binding.itemNoteIv.setImageResource(imageResId)
        }
    }
}
