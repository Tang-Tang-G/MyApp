package com.example.myapp.model

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

// TODO: gson
//data class ApiResponse<T>(val code: Int, val message: String, val timestamp: Int, val data: T)


data class LoginInfo(val username: String, val token: String)

object SessionManager {
    private const val PREF_NAME = "user_session"
    private const val KEY_USERNAME = "username"
    private const val KEY_TOKEN = "token"

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveSession(context: Context, username: String, token: String) {
        val editor = getPreferences(context).edit()
        editor.putString(KEY_USERNAME, username)
        editor.putString(KEY_TOKEN, token)
        editor.apply()
    }

    fun getUsername(context: Context): String? {
        return getPreferences(context).getString(KEY_USERNAME, null)
    }

    fun getToken(context: Context): String? {
        return getPreferences(context).getString(KEY_TOKEN, null)
    }

    fun clearSession(context: Context) {
        val editor = getPreferences(context).edit()
        editor.clear()
        editor.apply()
    }

}

object LoginViewModel : ViewModel() {
    private val _token = MutableLiveData<String>("")
    val token: LiveData<String> = _token

    private val _username = MutableLiveData<String>()
    val username: LiveData<String> = _username

    fun setLoginInfo(username: String, token: String) {
        Log.d("viewmodel", "save $username, $token")
        _username.value = username
        _token.value = token
    }
    fun logout() {
        Log.d("viewmodel", "logout")
        _username.value = "Not login"
        _token.value = ""
    }
}
