package com.example.hhru

import MyData
import VacancyData
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialog


var dataList = mutableListOf<MyData>()
var dataListVacancy = mutableListOf<VacancyData>()
var dataListVacancy_fav = mutableListOf<VacancyData>()
var limit = 3

lateinit var favorite_count_image: TextView
var favorite_count_heart = 0
var button_pos = 1

fun show_modal_otklik(pos: Int, cotnext: Context)
{
    val bottomSheetDialog = BottomSheetDialog(cotnext)
    val view = LayoutInflater.from(cotnext).inflate(R.layout.modal_window, null)
    bottomSheetDialog.setContentView(view)

    val textView = view.findViewById<TextView>(R.id.vacancy_name)
    if (button_pos == 1) {
        textView.text = dataListVacancy[pos].dataList.title
    }
    else
    {
        textView.text = dataListVacancy_fav[pos].dataList.title
    }

    val edit_text_letter = view.findViewById<EditText>(R.id.edit_text_letter)

    val add_letter = view.findViewById<TextView>(R.id.add_letter)
    add_letter.setOnClickListener{
        edit_text_letter.visibility = View.VISIBLE
        add_letter.visibility = View.GONE
    }

    val button_otklik_close = view.findViewById<Button>(R.id.button_otklik_close)
    button_otklik_close.setOnClickListener{
        bottomSheetDialog.dismiss()
    }

    bottomSheetDialog.show()
}

