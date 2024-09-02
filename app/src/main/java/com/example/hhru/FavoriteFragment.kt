package com.example.hhru

import VacancyAdapter
import VacancyData
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * A simple [Fragment] subclass.
 * Use the [FavoriteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FavoriteFragment : Fragment() {

    private lateinit var recyclerView_fav: RecyclerView
    private lateinit var adapterVacancy_fav: VacancyAdapter
    lateinit var vacancy_count: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val v: View = inflater.inflate(R.layout.fragment_favorite, null)

        dataListVacancy_fav = mutableListOf<VacancyData>()

        for (item in dataListVacancy)
        {
            if (item.dataList.isFavorite)
            {
                dataListVacancy_fav.add(item)
            }
        }

        vacancy_count = v.findViewById(R.id.vacancy_count)
        vacancy_count.text = validation.formatVacancies(dataListVacancy_fav.size)

        recyclerView_fav = v.findViewById(R.id.RVVacancy2)
        recyclerView_fav.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        adapterVacancy_fav = VacancyAdapter(dataListVacancy_fav)
        recyclerView_fav.adapter = adapterVacancy_fav

        return v
    }

}