package com.example.lottonumber

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private val btnReset: Button by lazy { // 사용하기 직전에 할당된다.
        findViewById<Button>(R.id.btn_reset)
    }

    private val btnAddNym : Button by lazy {
        findViewById<Button>(R.id.btn_addNum)
    }

    private val btnStart : Button by lazy {
        findViewById<Button>(R.id.btn_start)
    }

    private val numberPicker : NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.numberPicker)
    }

    // 로또번호 6개 나타낼 textview 6개
    private val numberTextViewList : List<TextView> by lazy {
        listOf<TextView>(
            findViewById(R.id.tv_num1),
            findViewById(R.id.tv_num2),
            findViewById(R.id.tv_num3),
            findViewById(R.id.tv_num4),
            findViewById(R.id.tv_num5),
            findViewById(R.id.tv_num6)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // numberPicker 1~45까지
        numberPicker.minValue = 1
        numberPicker.maxValue = 45
    }


}