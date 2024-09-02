package com.example.hhru

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LoginActivity2 : AppCompatActivity() {
    lateinit var emailText: TextView

    lateinit var code1: EditText
    lateinit var code2: EditText
    lateinit var code3: EditText
    lateinit var code4: EditText
    lateinit var button_cont: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        code1 = findViewById(R.id.edit_text_code1)
        code2 = findViewById(R.id.edit_text_code2)
        code3 = findViewById(R.id.edit_text_code3)
        code4 = findViewById(R.id.edit_text_code4)
        button_cont = findViewById(R.id.button_cont)

        emailText = findViewById(R.id.emailText)
        val textFromEditText1 = intent.getStringExtra("textEmail")
        emailText.setText("Отправили код на " + textFromEditText1)

        val editTextList = listOf(code1, code2, code3, code4)

        editTextList.forEachIndexed { index, editText ->
            editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    if (s.toString().length == 1) {
                        if (index < editTextList.size - 1) {
                            editTextList[index + 1].requestFocus()
                        }
                    } else if (s.toString().isEmpty() && index > 0) {
                        editTextList[index - 1].requestFocus()
                    }

                    if (code1.text.isNotBlank() && code2.text.isNotBlank() && code3.text.isNotBlank() && code4.text.isNotBlank())
                    {
                        button_cont.setEnabled(true)
                        button_cont.setBackgroundColor(Color.parseColor("#2B7EFE"))
                        button_cont.setTextColor(Color.parseColor("#FFFFFF"))
                    }
                    else
                    {
                        button_cont.setEnabled(false)
                        button_cont.setBackgroundColor(Color.parseColor("#00427D"))
                        button_cont.setTextColor(Color.parseColor("#9F9F9F"))
                    }

                }
            })
        }

    }

    fun check_code(view: View)
    {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

}