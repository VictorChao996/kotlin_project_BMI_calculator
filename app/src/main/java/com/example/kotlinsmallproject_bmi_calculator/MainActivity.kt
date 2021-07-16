package com.example.kotlinsmallproject_bmi_calculator

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.kotlinsmallproject_bmi_calculator.databinding.ActivityMainBinding
import kotlin.math.pow

const val KEY = "MainActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var BMI_Result: TextView


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        var finalValue:String? = binding.textFinalValue.text.toString()
        outState.putString(KEY, finalValue)     //儲存textFinalValue(計算結果)中的資料
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        //使用view binding來連結UI component
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //因為binding的關係所以 savedInstanceState資料讀取要寫在這裡
        if(savedInstanceState != null){
            binding.textFinalValue.text = savedInstanceState.getString(KEY, "0")
        }else{
            BMI_Result = binding.textFinalValue

        }


        binding.buttonClear.setOnClickListener{view->
            Log.d("button mes", "clear_button_pressed")
            clearData()
            view.hideKeyBoard()
        }

        binding.buttonCount.setOnClickListener {view ->
            var weight: Double? = binding.EditTextWeight.text.toString().toDoubleOrNull()
            var height:Double? = binding.EditTextHeight.text.toString().toDoubleOrNull()
            Log.d("button mes", "calculate_button_pressed")
            if(weight == null || height == null){

                BMI_Result.text = "輸入的資訊不足，\n請完整輸入身高與體重"
                BMI_Result.setTextSize(TypedValue.COMPLEX_UNIT_SP,14.toFloat())
           }else if(height.toInt() == 0){

                BMI_Result.text = "輸入的測資不合理，\n身高不可為0"
                BMI_Result.setTextSize(TypedValue.COMPLEX_UNIT_SP,14.toFloat())
            }
            else{
                calculateBMI(weight, height)
            }
           view.hideKeyBoard()
        }
    }


    //method for clearButton
    private fun clearData(){
        binding.EditTextHeight.text = null
        binding.EditTextWeight.text = null
    }
    private fun calculateBMI(weight:Double,height:Double ){
        var result:Double = weight/ height.pow(2.0)
        BMI_Result.text = null      //顯示結果前清除版面
        BMI_Result.text = String.format("%.2f", result)
        BMI_Result.setTextSize(TypedValue.COMPLEX_UNIT_SP,30.toFloat())
        Log.d("result value",String.format("%.2f", result))
    }
    private fun View.hideKeyBoard(){
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)


    }


}