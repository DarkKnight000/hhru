import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.hhru.Offer
import com.example.hhru.R

class RecommendationAdapter(private val data: List<MyData>) : RecyclerView.Adapter<RecommendationAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recomendation_item, parent, false)
        val holder = ViewHolder(view)
        holder.itemView.setOnClickListener {
            val position = holder.adapterPosition
            val item = data[position]
            // Обработка нажатия на весь item
            Log.d("itemView", "Item clicked: $item")
            // Открываем ссылку в браузере
            val context = holder.itemView.context
            if (Uri.parse(item.dataList.link).scheme != null && Uri.parse(item.dataList.link).host != null) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.dataList.link))
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(intent)
            } else {
                Toast.makeText(context, "Invalid link", Toast.LENGTH_SHORT).show()
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]

        if (item.dataList.id == "near_vacancies")
        {
            holder.imageIcon.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#00427D"))
        }
        else if (item.dataList.id == "level_up_resume")
        {
            holder.imageIcon.setImageResource(R.drawable.star)
            holder.imageIcon.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#015905"))
        }
        else if (item.dataList.id == "temporary_job")
        {
            holder.imageIcon.setImageResource(R.drawable.temporary_job)
            holder.imageIcon.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#015905"))
        }
        else
        {
            holder.imageIcon.backgroundTintList = ColorStateList.valueOf(Color.TRANSPARENT)
        }

        holder.textTitle1.text = item.dataList.title.trim()

        if (holder.buttonText.text != "null") {
            holder.buttonText.text = item.dataList.button?.text
            holder.buttonText.visibility = View.VISIBLE
            holder.textTitle1.maxLines = 2 // ограничить до 2 строк, если текст кнопки присутствует
        } else {
            holder.buttonText.visibility = View.GONE
            holder.textTitle1.maxLines = 3 // разрешить 3 строки, если текст кнопки отсутствует
        }

    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageIcon: ImageView = itemView.findViewById(R.id.imageIcon)
        val textTitle1: TextView = itemView.findViewById(R.id.title1)
        val buttonText: TextView = itemView.findViewById(R.id.title2)
    }
}

data class MyData(val dataList: Offer)
