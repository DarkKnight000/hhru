package com.example.hhru

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.TypedValue
import android.view.View
import android.widget.EditText
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.regex.Matcher
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() {

    lateinit var emailEditView: EditText
    lateinit var error_text: TextView
    lateinit var button_continue: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }

        emailEditView = findViewById(R.id.edit_text_login)
        error_text = findViewById(R.id.error_text)
        button_continue = findViewById(R.id.button_continue)

        emailEditView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int)
            {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                emailEditView.setBackgroundResource(R.drawable.rounded_corners_background)
                error_text.visibility = View.GONE

                if (emailEditView.text.isEmpty())
                {
                    button_continue.setEnabled(false)
                    button_continue.setBackgroundColor(Color.parseColor("#00427D"))
                    button_continue.setTextColor(Color.parseColor("#9F9F9F"))
                }
                else
                {
                    button_continue.setEnabled(true)
                    button_continue.setBackgroundColor(Color.parseColor("#2B7EFE"))
                    button_continue.setTextColor(Color.parseColor("#FFFFFF"))
                }
            }
        })

        button_continue.setOnClickListener {
            continue_button()
        }


    }

    fun continue_button()
    {

        var flag = true // Изначально считаем, что все верно

        if (!isEmailValid(emailEditView.text.toString()))
        {
            flag = false // Если email невалидный, устанавливаем флаг в false

            val drawable = GradientDrawable()
            drawable.shape = GradientDrawable.RECTANGLE
            val radiusInPx = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                8f,
                resources.displayMetrics
            )
            drawable.cornerRadius = radiusInPx // радиус закругления
            drawable.setColor(Color.parseColor("#313234")) // цвет фона
            drawable.setStroke(1, Color.parseColor("#FF0000")) // цвет и толщина границы

            emailEditView.background = drawable
            error_text.visibility = View.VISIBLE

        }

        if (flag)
        {
            val intent = Intent(this, LoginActivity2::class.java)
            intent.putExtra("textEmail", emailEditView.text.toString()) // Get text from EditText
            startActivity(intent)
        }
    }

    fun isEmailValid(email: String?): Boolean
    {
        val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
        val pattern: Pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher: Matcher = pattern.matcher(email)
        return matcher.matches()
    }


}