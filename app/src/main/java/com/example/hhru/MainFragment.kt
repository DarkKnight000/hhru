package com.example.hhru

import MyData
import RecommendationAdapter
import VacancyAdapter
import VacancyData
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hhru.R.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

lateinit var recyclerViewVacancy: RecyclerView
lateinit var adapterVacancy: VacancyAdapter

/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecommendationAdapter
    private lateinit var button_all_vacancies: Button
    private lateinit var back_image: ImageView
    private lateinit var linear_vacancy_sort: LinearLayout
    private lateinit var vacancies_for_you: TextView
    private lateinit var all_vacancy_count: TextView
    lateinit var progress_loading: ProgressBar
    lateinit var fragment_layout: LinearLayout

    val validation = Validation()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val v: View = inflater.inflate(layout.fragment_main, null)

        limit = 3

        button_all_vacancies = v.findViewById(R.id.button_all_vacancies)
        all_vacancy_count = v.findViewById(R.id.all_vacancy_count)
        vacancies_for_you = v.findViewById(R.id.vacancies_for_you)
        linear_vacancy_sort = v.findViewById(R.id.linear_vacancy_sort)
        back_image = v.findViewById(R.id.back_image)
        progress_loading = v.findViewById(R.id.progress_loading)
        fragment_layout = v.findViewById(R.id.fragment_layout)

        button_all_vacancies.setOnClickListener {
            limit = dataListVacancy.size
            adapterVacancy = VacancyAdapter(dataListVacancy)
            recyclerViewVacancy.adapter = adapterVacancy
            back_image.setImageResource(drawable.back)
            recyclerView.visibility = View.GONE
            button_all_vacancies.visibility = View.GONE
            vacancies_for_you.visibility = View.GONE
            linear_vacancy_sort.visibility = View.VISIBLE
            all_vacancy_count.text = validation.formatVacancies(dataListVacancy.size)
        }

        back_image.setOnClickListener {
            limit = 3
            adapterVacancy = VacancyAdapter(dataListVacancy)
            recyclerViewVacancy.adapter = adapterVacancy
            back_image.setImageResource(drawable.search1)
            recyclerView.visibility = View.VISIBLE
            button_all_vacancies.visibility = View.VISIBLE
            vacancies_for_you.visibility = View.VISIBLE
            linear_vacancy_sort.visibility = View.GONE
        }

        val api = RetrofitInstance.api
        recyclerView = v.findViewById(R.id.RVRec)
        recyclerViewVacancy = v.findViewById(R.id.RVVacancy)

        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        adapter = RecommendationAdapter(dataList)
        recyclerView.adapter = adapter

        recyclerViewVacancy.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        adapterVacancy = VacancyAdapter(dataListVacancy)
        recyclerViewVacancy.adapter = adapterVacancy

        if (favorite_count_heart == 0)
            favorite_count_image.visibility = View.GONE
        else
            favorite_count_image.visibility = View.VISIBLE

        val formatedVacancies = validation.formatVacancies(dataListVacancy.size)
        button_all_vacancies.text = "Ещё " + formatedVacancies


        if (dataListVacancy.size == 0) {
            favorite_count_heart = 0

            progress_loading.visibility = View.VISIBLE
            fragment_layout.visibility = View.INVISIBLE

            api.getOfferVacancy().enqueue(object : Callback<OfferVacancyResponse> {
                override fun onResponse(
                    call: Call<OfferVacancyResponse>,
                    response: Response<OfferVacancyResponse>
                ) {
                    if (response.isSuccessful) {
                        val offerVacancyResponse = response.body()

                        offerVacancyResponse?.let {

                            it.offers.forEach { offer ->
                                dataList.add(MyData(offer))
                            }

                            it.vacancies.forEach { vacancy ->
                                dataListVacancy.add(VacancyData(vacancy))
                                if (vacancy.isFavorite)
                                {
                                    favorite_count_heart++
                                    favorite_count_image.visibility = View.VISIBLE
                                    favorite_count_image.text = favorite_count_heart.toString()
                                }
                            }

                            val formatedVacancies = validation.formatVacancies(it.vacancies.size)
                            button_all_vacancies.text = "Ещё " + formatedVacancies

                            adapter = RecommendationAdapter(dataList)
                            recyclerView.adapter = adapter

                            adapterVacancy = VacancyAdapter(dataListVacancy)
                            recyclerViewVacancy.adapter = adapterVacancy
                        }


                    } else {
                        Log.e("Error", "Failed to retrieve data")
                    }

                    progress_loading.visibility = View.INVISIBLE
                    fragment_layout.visibility = View.VISIBLE
                }

                override fun onFailure(call: Call<OfferVacancyResponse>, t: Throwable) {
                    Log.e("Error", "Failed to retrieve data", t)
                }
            })

        }

        return v
    }

}

object RetrofitInstance {
    private const val BASE_URL = "https://drive.usercontent.google.com/"

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: OfferVacancyApi by lazy {
        retrofit.create(OfferVacancyApi::class.java)
    }
}
