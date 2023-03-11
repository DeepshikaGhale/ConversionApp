package com.example.conversionapp

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.get
import com.example.conversionapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var binding: ActivityMainBinding
    var userInput = 0.0
    var conversionResult = ""
    var selectedItem = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater) //
        setContentView(binding.root)

        //adapter for spinner, the create from resources method is used to create an dropdown using the array of created in the resource file
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.units,
            android.R.layout.simple_spinner_item
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        //assigning the adapter we created to the spinner's adapter
        binding.conversionSpinner.adapter = adapter
        binding.conversionSpinner.onItemSelectedListener = this

        //convert
        binding.convertBtn.setOnClickListener(){

            if(binding.inputNumber.text.isNotEmpty()){
                checkUnit()
            }else{
                Toast.makeText(this, "Please enter a number!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
         selectedItem = binding.conversionSpinner.selectedItem.toString()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        Toast.makeText(this, "$p0", Toast.LENGTH_SHORT).show()
    }

    fun setColorForSpinner(){

    }

    //check if the unit field is empty or not
    private fun checkUnit(){
        if (selectedItem.isNotBlank() && selectedItem != "Select unit"){
            //store user input
            userInput = binding.inputNumber.text.toString().toDouble()
            convert(selectedItem, userInput)
            binding.answer.text = conversionResult

            //to make the answer title visible
            binding.answerTitle.visibility = View.VISIBLE
            binding.answer.visibility = View.VISIBLE
            Toast.makeText(this, "Conversion successful", Toast.LENGTH_SHORT).show()
//            Toast.makeText(this, "Converted Successfully!", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this, "Please select a unit to convert!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun convert(unitOfMeasurement: String, inputNumber: Double){
        print(unitOfMeasurement)
        //setting value for the conversion result
        conversionResult = when (unitOfMeasurement){
            "km" -> "${inputNumber * 0.62}mi"
            "mi" -> "${inputNumber * 1.61}km"
            "cm" -> "${inputNumber * 0.39}in"
            "in" -> "${inputNumber * 2.54}cm"
            "kg" -> "${inputNumber * 2.2}lb"
            "lb" -> "${inputNumber * 0.45}kg"
            "g" -> "${inputNumber * 0.04}oz"
            "oz" -> "${inputNumber * 28.35}g"
            "C" -> "${(inputNumber * (9.0/5))+32}F"
            "L" -> "${inputNumber * 4.17}cups"
            "cup" -> "${inputNumber * 0.24}L"
            else -> "not found" //return not found if the measurement unit is not available
        }
    }
}