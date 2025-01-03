package com.example.currencyconverterapp

import android.R.menu
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    var TAG = "ok"

    private val egyptionPound = "Egyption Pound"
    private val americanDollar = "American Dollar"
    private val AED = "AED"
    private val GBP = "GBP"
    private val Euro = "Euro"

    lateinit var convertBtn: Button
    lateinit var amountEt: TextInputEditText
    lateinit var resultEt: TextInputEditText
    lateinit var dropDown1: AutoCompleteTextView
    lateinit var to_Drop_Down_Menu: AutoCompleteTextView
    lateinit var toolbar: Toolbar




    var values = mapOf(
        americanDollar to 1.0,
        egyptionPound to 49.35,
        AED to 3.67,
        GBP to 0.79,
        Euro to 0.94,
    )

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        views()
        menu()
        toolbar.inflateMenu(R.menu.options_menu)
        toolbar.setOnMenuItemClickListener(){  menuItem ->
            when {
                menuItem.itemId == R.id.share_action -> {

                    var message = "${amountEt.text.toString()} ${dropDown1.text.toString()}" +
                            " is equal ${resultEt.text.toString()} ${to_Drop_Down_Menu.text.toString()}"
                    var shareAction = Intent(Intent.ACTION_SEND)
                    shareAction.type = "text/plain"
                    shareAction.putExtra(Intent.EXTRA_TEXT,message)

                    if (shareAction.resolveActivity(packageManager) != null){
                        startActivity(shareAction)
                    }
                    else{
                        Toast.makeText(this, "you have not an app in your device to share the message", Toast.LENGTH_SHORT).show()
                    }

                    true
                }

                else -> false
            }
        }



        amountEt.doAfterTextChanged { calculateResult() }
        dropDown1.setOnClickListener() {
            calculateResult()
        }

        to_Drop_Down_Menu.setOnClickListener() {
            calculateResult()
        }
    }


    fun calculateResult() {
        if (amountEt.text.toString().isNotEmpty()) {
            var amount = amountEt.text.toString().toDouble()
            var to_value = values[to_Drop_Down_Menu.text.toString()]
            var from_value = values[dropDown1.text.toString()]
            var result = amount.times(to_value!!.div(from_value!!))
            var formatedResult = String.format("%.2f", result)
            resultEt.setText(formatedResult)
        } else {
            Toast.makeText(this, "amount field required ", Toast.LENGTH_SHORT).show()
        }
    }

    fun views() {
        convertBtn = findViewById(R.id.button)
        amountEt = findViewById(R.id.amount_edit_text)
        resultEt = findViewById(R.id.result_edit_text)
        dropDown1 = findViewById(R.id.first_autocomplete_textview)
        to_Drop_Down_Menu = findViewById(R.id.to_menu)
        toolbar = findViewById(R.id.toolbar2)
    }

    fun menu() {
        val listofCuntrys = listOf(egyptionPound, americanDollar, AED, GBP, Euro)
        val adapter = ArrayAdapter(this, R.layout.drop_down_menu, listofCuntrys)
        to_Drop_Down_Menu.setAdapter(adapter)
        dropDown1.setAdapter(adapter)
    }
}




