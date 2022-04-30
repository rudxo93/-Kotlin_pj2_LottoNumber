package com.example.lottonumber

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible

class MainActivity : AppCompatActivity() {

    private val btnReset: Button by lazy { // 사용하기 직전에 할당된다.
        findViewById<Button>(R.id.btn_reset)
    }

    private val btnAddNum : Button by lazy {
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

    private var didRun = false // 자동 생성 버튼을 최정적으로 누를경우 true

    private val pickNumberSet = hashSetOf<Int>() // 사용자가 선택한 번호 set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // numberPicker 1~45까지
        numberPicker.minValue = 1
        numberPicker.maxValue = 45

        initStartButton() // 자동 생성 시작 함수
        initAddButton() // 번호 추가하기 버튼 함수
        initResetButton() // Reset 버튼 함수
    }

    // Reset 버튼 클릭 초기화하기
    private fun initResetButton() {
        btnReset.setOnClickListener {
            pickNumberSet.clear()
            numberTextViewList.forEach {
                it.isVisible = false
            }
            didRun = false
        }
    }

    // 번호 추가하기 버튼 초기화하기
    private fun initAddButton() {
        btnAddNum.setOnClickListener {

            // 이미 자동생성버튼을 누른경우 번호가 추가되면 안되기 때문에 -> 초기화 후에 번호를 선택할 수 있게
            if(didRun){
                Toast.makeText(this, "초기화 후에 시도해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 최대 5개까지만 번호를 선택
            if(pickNumberSet.size >= 5) {
                Toast.makeText(this, "번호는 5개까지만 선택할 수 있습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 중복으로 번호를 선택하지 못하게
            if (pickNumberSet.contains(numberPicker.value)) {
                Toast.makeText(this, "이미 선택한 번호입니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val textView = numberTextViewList[pickNumberSet.size] // 다음으로 추가될 텍스트뷰
            textView.isVisible = true
            textView.text = numberPicker.value.toString()

            // 코틀린이라 setBackground 사용 말고 이렇게 사용가능
            // drawable 이 안드로이드 앱에 저장되는 것이기 때문에 Context에서 가져오는 것이 필요
            textView.background = ContextCompat.getDrawable(this, R.drawable.circle_blue)

            setNumberBackground(numberPicker.value, textView) // 번호에 맞는 공색깔을 set해준다.

            pickNumberSet.add(numberPicker.value) // 선택한 번호 set에 추가
        }
   }

    // 자동 생성 시작 버튼 초기화하기
    private fun initStartButton() {
        btnStart.setOnClickListener {
            didRun = true
            val list = getRandomNumber() // 사용자가 선택한 번호를 포함한 6개의 로또 번호 가져오기

            // 인덱스와 값을 사용하기 위해 forEachIndexed
            list.forEachIndexed { index, number ->
                val textView = numberTextViewList[index]

                textView.text = number.toString()
                textView.isVisible = true // 공 번호가 있는 textview가 보인다.

                setNumberBackground(number, textView) // 번호에 맞는 공 색깔 set
            }
        }
    }

    private fun getRandomNumber(): List<Int> {
        val numberList = mutableListOf<Int>().apply {
            for (i in 1..45) { // 1~45까지 for문이 돈다.
                // 이미 선택된 번호일 경우 skip
                if(pickNumberSet.contains(i)){
                    continue
                }
                this.add(i)
            }
            shuffle() // 랜덤으로 셔플한다.
        }
        // 선택한 set + 랜덤 번호가 합쳐서 6개가 되도록한다.
        val newList = pickNumberSet.toList() + numberList.subList(0, 6 - pickNumberSet.size)

        return newList.sorted() // 오름차순으로 정렬해서 반환한다.
    }

    // 중복을 피하기 위해 함수를 따로 빼서 구성한다.
    private fun setNumberBackground(number: Int, textView: TextView) {
        // 로또번호에 해당하는 배경설정
        when(number){
            in 1..10 ->
                textView.background = ContextCompat.getDrawable(this, R.drawable.circle_yellow)
            in 11..20 ->
                textView.background = ContextCompat.getDrawable(this, R.drawable.circle_blue)
            in 21..30 ->
                textView.background = ContextCompat.getDrawable(this, R.drawable.circle_red)
            in 31..40 ->
                textView.background = ContextCompat.getDrawable(this, R.drawable.circle_gray)
            in 41..45 ->
                textView.background = ContextCompat.getDrawable(this, R.drawable.circle_green)
        }
    }


}