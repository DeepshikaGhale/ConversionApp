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
import java.text.DecimalFormat

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
            R.layout.spinner_item
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        //assigning the adapter we created to the spinner's adapter
        binding.conversionSpinner.adapter = adapter
        binding.conversionSpinner.onItemSelectedListener = this

        //convert button
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
        }else{
            Toast.makeText(this, "Please select a unit to convert!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun convert(unitOfMeasurement: String, inputNumber: Double){
        val inputData = "$inputNumber $unitOfMeasurement"

        //setting value for the conversion result
        conversionResult = when (unitOfMeasurement){
            "km" -> "$inputData =  ${roundOffAnswer(inputNumber * 0.62)} mi"
            "mi" -> "$inputData = ${roundOffAnswer(inputNumber * 1.61)} km"
            "cm" -> "$inputData = ${roundOffAnswer(inputNumber * 0.39)} in"
            "in" -> "$inputData = ${roundOffAnswer(inputNumber * 2.54)} cm"
            "kg" -> "$inputData = ${roundOffAnswer(inputNumber * 2.2)} lb"
            "lb" -> "$inputData = ${roundOffAnswer(inputNumber * 0.45)} kg"
            else -> "not found" //return not found if the measurement unit is not available
        }
    }

    //round off the answers from measurement
    private fun roundOffAnswer(measurement: Double) : String {
        val df = DecimalFormat("#.##")
        return  df.format(measurement)
    }
}