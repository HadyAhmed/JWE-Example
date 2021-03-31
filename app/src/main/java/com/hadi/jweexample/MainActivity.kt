package com.hadi.jweexample

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.hadi.jweexample.model.LoginRequest
import com.hadi.jweexample.network.RetrofitClient
import kotlinx.coroutines.launch
import okio.IOException
import org.json.JSONObject
import retrofit2.HttpException

class MainActivity : AppCompatActivity() {

    private lateinit var resultTextView: TextView
    private lateinit var login: Button
    private lateinit var usernameEt: EditText
    private lateinit var passwordEt: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        login = findViewById(R.id.login_button)
        resultTextView = findViewById(R.id.resultTextView)
        usernameEt = findViewById(R.id.username_et)
        passwordEt = findViewById(R.id.password_et)

        login.setOnClickListener { login() }
    }

    private fun login() {
        resultTextView.text = "Loading"

        lifecycleScope.launch {
            try {
                val response = RetrofitClient.retrofitClient.login(LoginRequest("+2${usernameEt.text}", passwordEt.text.toString()))
                resultTextView.text = response.string()

            } catch (ioException: IOException) {
                resultTextView.text = ioException.message
            } catch (exception: HttpException) {
                resultTextView.text = JSONObject(exception.response()?.errorBody()?.string()).getString("Message")
            }
        }
    }
}