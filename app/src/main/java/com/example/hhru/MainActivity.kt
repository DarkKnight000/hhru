package com.example.hhru

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {
    private lateinit var image_search: ImageView
    private lateinit var image_heart: ImageView
//    private lateinit var progress_loading: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        image_search = findViewById(R.id.image_search)
        image_heart = findViewById(R.id.image_heart)
        favorite_count_image = findViewById(R.id.favorite_count_heart)
//        progress_loading = findViewById(R.id.progress_loading)

        val mainFragment = MainFragment()
        setNewFragment(mainFragment)
    }


    private fun setNewFragment(fragment: Fragment) {

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    fun open_main(view: View)
    {
        if (button_pos != 1) {
            val mainFragment = MainFragment()
            setNewFragment(mainFragment)
            image_search.setImageResource(R.drawable.search2)
            button_pos = 1
        }

    }

    fun open_fav(view: View)
    {
        if (button_pos != 2) {
            val favFragment = FavoriteFragment()
            setNewFragment(favFragment)
            image_search.setImageResource(R.drawable.search1)
            button_pos = 2
        }
    }
}