package com.hadi.jweexample

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.hadi.jweexample.model.LoginRequest
import com.hadi.jweexample.network.JWEUtils
import com.hadi.jweexample.network.RetrofitClient
import kotlinx.coroutines.launch
import okio.IOException
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.await

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

        showDecryptedValue()
        getApiKey()
        login.setOnClickListener { login() }
    }

    private fun showDecryptedValue() {
        // this is the encrypted value for username=+201000652173 and password=6521
        val value =
            "eyJlbmMiOiJBMTI4Q0JDLUhTMjU2IiwiYWxnIjoiZGlyIn0..WHjjVH3UGbYq1Jzwu2Eopg.CubF8zXiA058gBLGDvO0j_27vFZh25pZ9wuLZG5J62ZQ0nHT1N7i-KJOcdgEU05E.MvkJDho7DXLPS6EAM5gPsg"
        // this print in the log Test
        addLog("Decrypted Hard Coded Value: ${JWEUtils.makeDecryptionOfJson(value)}")
    }

    private fun getApiKey() {
        lifecycleScope.launchWhenCreated {
            try {
                addLog("Getting Api Key...")
                val response = RetrofitClient.retrofitClient().getApiKey()
                val responseJsonReader = JSONObject(response.string())
                if (responseJsonReader.getBoolean("Success")) {
                    val data = responseJsonReader.getJSONObject("data")
                    val keyId: String = data.getString("KeyId")
                    val secretKey: String = data.getString("SecretKey")
                    // setting constants for one session
                    Constants.KEY_ID = keyId
                    Constants.SECRET_KEY = secretKey
                    // results on text view
                    addLog("Key Id: $keyId")
                    addLog("Secret Key $secretKey")
                    addLog("-------------------------------------------------------")
                }
            } catch (ioException: IOException) {
                addLog(ioException.message)
            } catch (exception: HttpException) {
                addLog(exception.response()?.errorBody()?.toString())
            }
        }
    }

    private fun login() {
        addLog("Loading")
        lifecycleScope.launch {
            try {
                val request = JSONObject(
                    Gson().toJson(
                        LoginRequest(
                            "+2${usernameEt.text}",
                            passwordEt.text.toString()
                        )
                    )
                )
                val response = RetrofitClient.retrofitClient(Constants.KEY_ID)
                    .login(JWEUtils.makeEncryptionOfJson(request)!!).await()
                addLog("Encrypted Login Success\nHello, ${JSONObject(response.string()).getJSONObject("data").getString("Name")}")
            } catch (ioException: IOException) {
                addLog(ioException.message)
            } catch (exception: HttpException) {
                addLog(exception.response()?.toString())
            }
        }
    }

    private fun addLog(log: String?) {
        resultTextView.append("\n")
        resultTextView.append(log)
        resultTextView.append("\n")
    }
}