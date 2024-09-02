import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hhru.R
import com.example.hhru.Vacancy
import com.example.hhru.VacancyActivity
import com.example.hhru.Validation
import com.example.hhru.button_pos
import com.example.hhru.dataListVacancy
import com.example.hhru.dataListVacancy_fav
import com.example.hhru.favorite_count_heart
import com.example.hhru.favorite_count_image
import com.example.hhru.limit
import com.example.hhru.show_modal_otklik
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

val validation = Validation()

class VacancyAdapter(private val data: List<VacancyData>) : RecyclerView.Adapter<VacancyAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.vacancy_item, parent, false)
        return ViewHolder(view, data, parent.context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.vacancy_name.text = item.dataList.title

        if(item.dataList.salary.short == null)
        {
            holder.vacancy_salary.visibility = View.GONE
        }
        else
        {
            holder.vacancy_salary.text = item.dataList.salary.short
        }
        holder.vacancy_city.text = item.dataList.address.town
        holder.vacancy_company.text = item.dataList.company
        holder.vacancy_experience.text = item.dataList.experience.previewText

        val localDate = LocalDate.parse(item.dataList.publishedDate)
        val formatter = DateTimeFormatter.ofPattern("d MMMM", Locale("ru", "RU"))
        val formattedDate = localDate.format(formatter)
        holder.vacancy_date.text = formattedDate

        val formattedString = validation.formatLookingNumber(item.dataList.lookingNumber)
        holder.looking_number.text = formattedString

        if(!item.dataList.isFavorite)
        {
            holder.vacancy_heart.setImageResource(R.drawable.heart1)
        }
        else
        {
            holder.vacancy_heart.setImageResource(R.drawable.heart2)
        }
    }

    override fun getItemCount(): Int {
        return if (data.size > limit) limit else data.size
    }

    class ViewHolder(itemView: View, private val data: List<VacancyData>, private val context: Context) : RecyclerView.ViewHolder(itemView) {
        val vacancy_name: TextView = itemView.findViewById(R.id.vacancy_name)
        val vacancy_heart: ImageView = itemView.findViewById(R.id.heartImage)
        val looking_number: TextView = itemView.findViewById(R.id.vacancy_count)
        val vacancy_salary: TextView = itemView.findViewById(R.id.vacancy_salary)
        val vacancy_city: TextView = itemView.findViewById(R.id.vacancy_city)
        val vacancy_company: TextView = itemView.findViewById(R.id.vacancy_company)
        val vacancy_experience: TextView = itemView.findViewById(R.id.vacancy_experience)
        val vacancy_date: TextView = itemView.findViewById(R.id.vacancy_date)
        val button_otklik: Button = itemView.findViewById(R.id.button_otklik)

        var isLiked: Boolean = false
        init {
            vacancy_heart.setOnClickListener {

                var clickedItem = dataListVacancy[adapterPosition].dataList
                if (button_pos == 1) {
                    clickedItem = dataListVacancy[adapterPosition].dataList
                }
                else if (button_pos == 2)
                {
                    clickedItem = dataListVacancy_fav[adapterPosition].dataList

                }

                isLiked = clickedItem.isFavorite
                isLiked = !isLiked
                if (isLiked)
                {
                    vacancy_heart.setImageResource(R.drawable.heart2)
                    clickedItem.isFavorite = true
                    favorite_count_heart++
                    favorite_count_image.text = favorite_count_heart.toString()
                }
                else
                {
                    vacancy_heart.setImageResource(R.drawable.heart1)
                    clickedItem.isFavorite = false
                    favorite_count_heart--
                    if (favorite_count_heart == 0)
                    {
                        favorite_count_image.visibility = View.GONE
                    }
                    favorite_count_image.text = favorite_count_heart.toString()
                }
            }

            button_otklik.setOnClickListener {
                show_modal_otklik(adapterPosition, context)
            }

            itemView.setOnClickListener {
                val context = itemView.context
                val intent = Intent(context, VacancyActivity::class.java)

                intent.putExtra("vacancyPos", adapterPosition)
                context.startActivity(intent)

            }
        }
    }

}


data class VacancyData(val dataList: Vacancy)