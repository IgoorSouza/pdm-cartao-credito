package com.igor.cartaocredito

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val titularInput = findViewById<EditText>(R.id.titular_input)
        val numeroInput = findViewById<EditText>(R.id.numero_input)
        val dataInput = findViewById<EditText>(R.id.data_input)
        val cvvInput = findViewById<EditText>(R.id.cvv_input)

        titularInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s.toString().length < 3) {
                    titularInput.error = "O nome do titular deve ter pelo menos 3 caracteres."
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        numeroInput.addTextChangedListener(object : TextWatcher {
            private var isUpdating = false

            override fun afterTextChanged(s: Editable?) {
                if (isUpdating) return
                isUpdating = true

                var digits = s.toString().replace(" ", "")
                if (digits.length > 16) digits = digits.substring(0, 16)

                val formatted = digits.chunked(4).joinToString(" ")
                numeroInput.setText(formatted)
                numeroInput.setSelection(formatted.length)

                if (digits.length < 16) {
                    numeroInput.error = "O número deve ter 16 dígitos."
                } else {
                    numeroInput.error = null
                }

                isUpdating = false
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        dataInput.addTextChangedListener(object : TextWatcher {
            private var isUpdating = false

            override fun afterTextChanged(s: Editable?) {
                if (isUpdating) return
                isUpdating = true

                var input = s.toString().replace("/", "")

                if (input.length > 4) input = input.substring(0, 4)

                if (input.length >= 3) {
                    input = input.substring(0, 2) + "/" + input.substring(2)
                }

                if (input.length >= 2) {
                    val month = input.substring(0, 2).toIntOrNull()
                    if (month == null || month < 1 || month > 12) {
                        dataInput.error = "O mês deve ser um valor entre 1 e 12."
                    }
                }

                dataInput.setText(input)
                dataInput.setSelection(input.length)
                isUpdating = false
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        cvvInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val input = s.toString();

                if (input.length > 3) {
                    val digits = input.substring(0, 3)
                    cvvInput.setText(digits)
                    cvvInput.setSelection(digits.length)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}