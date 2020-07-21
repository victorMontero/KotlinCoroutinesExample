package com.android.kotlincoroutinesexample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private val RESULT_TWO = "result # 2"
    private val RESULT_ONE = "result # 1"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {

            CoroutineScope(IO).launch {
                fakeApiRequest()
            }
        }
    }

    private fun setNewText(input: String){
        val newText = text.text.toString() + "\n$input"
        text.text = newText
    }

    private suspend fun setTextOnMainThread(input: String){
        withContext(Main){
            setNewText(input)
        }
    }

    private suspend fun fakeApiRequest() {
        val result1 = getResultOneFromApi()
        println("debug: $result1")
        setTextOnMainThread(result1)

        val result2 = getResultTwoFromApi()
        setTextOnMainThread(result2)
    }

    private suspend fun getResultOneFromApi(): String {
        logThread("getResultOneFromApi")
        delay(1000)
        return RESULT_ONE
    }

    private suspend fun getResultTwoFromApi(): String {
        logThread("getResultOneFromApi")
        delay(1000)
        return RESULT_TWO
    }

    private fun logThread(methodName: String) {
        println("debug: ${methodName}: ${Thread.currentThread().name}")
    }
}