package com.example.hhru

import QuectionsAdapter
import VacancyAdapter
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

val validation = Validation()

class VacancyActivity : AppCompatActivity() {

    private lateinit var vacancy_name: TextView
    private lateinit var vacancy_salary: TextView
    private lateinit var vacancy_experience: TextView
    private lateinit var vacancy_schedules: TextView
    private lateinit var vacancy_otklik: TextView
    private lateinit var vacancy_looking: TextView
    private lateinit var vacancy_company: TextView
    private lateinit var vacancy_address: TextView
    private lateinit var vacancy_description: TextView
    private lateinit var vacancy_responsibilities: TextView
    private lateinit var heart_image: ImageView
    private lateinit var card_otklik: CardView
    private lateinit var card_looking: CardView
    private lateinit var button_otklik: Button


    lateinit var recyclerViewQuestions: RecyclerView
    lateinit var adapterQuestions: QuectionsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_vacancy)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val vacancyPos = intent.getIntExtra("vacancyPos", -1)
        var data_item = dataListVacancy[vacancyPos].dataList
        if (button_pos == 1) {
            data_item = dataListVacancy[vacancyPos].dataList
        }
        else if (button_pos == 2)
        {
            data_item = dataListVacancy_fav[vacancyPos].dataList
        }

        vacancy_name = findViewById(R.id.vacancy_name)
        vacancy_salary = findViewById(R.id.vacancy_salary)
        vacancy_experience = findViewById(R.id.vacancy_experience)
        vacancy_schedules = findViewById(R.id.vacancy_schedules)
        vacancy_otklik = findViewById(R.id.vacancy_otklik)
        vacancy_looking = findViewById(R.id.vacancy_looking)
        vacancy_company = findViewById(R.id.vacancy_company)
        vacancy_address = findViewById(R.id.vacancy_address)
        vacancy_description = findViewById(R.id.vacancy_description)
        vacancy_responsibilities = findViewById(R.id.vacancy_responsibilities)
        heart_image = findViewById(R.id.heart_image)
        card_otklik = findViewById(R.id.card_otklik)
        card_looking = findViewById(R.id.card_looking)
        recyclerViewQuestions = findViewById(R.id.RVQuestions)
        button_otklik = findViewById(R.id.button_otklik)

        val itemList = mutableListOf<String>()

        recyclerViewQuestions.layoutManager = LinearLayoutManager(this)
        adapterQuestions = QuectionsAdapter(itemList)
        recyclerViewQuestions.adapter = adapterQuestions

        for (i in 0..<data_item.questions.size) {
            adapterQuestions.addItem(data_item.questions[i])
        }

        heart_image.setOnClickListener {
            if (data_item.isFavorite == true)
            {
                heart_image.setImageResource(R.drawable.heart1)
                data_item.isFavorite = false
                dataListVacancy[vacancyPos].dataList.isFavorite = false

                favorite_count_heart--
                if (favorite_count_heart == 0)
                {
                    favorite_count_image.visibility = View.GONE
                }
                favorite_count_image.text = favorite_count_heart.toString()
            }
            else
            {
                heart_image.setImageResource(R.drawable.heart2)
                data_item.isFavorite = true
                dataListVacancy[vacancyPos].dataList.isFavorite = true

                favorite_count_heart++
                favorite_count_image.text = favorite_count_heart.toString()

            }

            recyclerViewVacancy.layoutManager = LinearLayoutManager(this)
            adapterVacancy = VacancyAdapter(dataListVacancy)
            recyclerViewVacancy.adapter = adapterVacancy
        }

        button_otklik.setOnClickListener {
            show_modal_otklik(vacancyPos, this)
        }

        vacancy_name.text = data_item.title
        vacancy_salary.text = data_item.salary.full
        vacancy_experience.text = "Требуемый опыт: " + data_item.experience.text
        vacancy_schedules.text = data_item.schedules.joinToString(", ") { it}

        if (data_item.appliedNumber == null)
        {
            card_otklik.visibility = View.GONE
        }
        else
        {
            vacancy_otklik.text = data_item.appliedNumber?.let { validation.formatLookingNumber(it) } + " уже откликнулись"
        }

        if (data_item.lookingNumber == 0)
        {
            card_looking.visibility = View.GONE
        }
        else
        {
            vacancy_looking.text = data_item.lookingNumber?.let { validation.formatLookingNumber(it) } + " сейчас смотрят"
        }
        vacancy_company.text = data_item.company
        vacancy_address.text = data_item?.address?.town + ", " + data_item?.address?.street + ", " + data_item.address.house
        vacancy_description.text = data_item.description
        vacancy_responsibilities.text = data_item.responsibilities

        if (data_item.isFavorite)
        {
            heart_image.setImageResource(R.drawable.heart2)
        }
    }
}