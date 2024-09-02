import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hhru.R
import com.example.hhru.VacancyActivity
import com.google.android.material.bottomsheet.BottomSheetDialog


class QuectionsAdapter(private val data: MutableList<String>) : RecyclerView.Adapter<QuectionsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.questions_item, parent, false)
        return ViewHolder(view, data, parent.context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]

        holder.question_button.text = item

    }

    override fun getItemCount(): Int {
        return data.size
    }

    // Метод для добавления нового элемента
    fun addItem(text: String)
    {
        data.add(text)
        notifyItemInserted(data.size)
    }

    class ViewHolder(itemView: View, private val data: List<String>, private val context: Context) : RecyclerView.ViewHolder(itemView) {

        val question_button: Button = itemView.findViewById(R.id.question_button)

        init {
            question_button.setOnClickListener {
                val bottomSheetDialog = BottomSheetDialog(context)
                val view = LayoutInflater.from(context).inflate(R.layout.modal_window, null)
                bottomSheetDialog.setContentView(view)

                val edit_text_letter = view.findViewById<EditText>(R.id.edit_text_letter)
                edit_text_letter.setText(question_button.text.toString()) // Set the text of the edit_text_letter
                edit_text_letter.visibility = View.VISIBLE
                val add_letter = view.findViewById<TextView>(R.id.add_letter)
                add_letter.visibility = View.GONE

                val button_otklik_close = view.findViewById<Button>(R.id.button_otklik_close)
                button_otklik_close.setOnClickListener {
                    bottomSheetDialog.dismiss()
                }

                bottomSheetDialog.show()
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
