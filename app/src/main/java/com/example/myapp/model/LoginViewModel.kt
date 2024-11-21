package com.example.myapp.model

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


@Composable
inline fun <reified T : ViewModel> activityViewModel(): T {
    val activity = LocalContext.current as ComponentActivity
    return ViewModelProvider(activity)[T::class.java]
}

// TODO: gson
//data class ApiResponse<T>(val code: Int, val message: String, val timestamp: Int, val data: T)


data class LoginInfo(val username: String, val token: String)

class LoginViewModel : ViewModel() {
    private val _token = MutableLiveData<String>()
    val token: LiveData<String> = _token

    private val _username = MutableLiveData<String>()
    val username: LiveData<String> = _username

    fun setLoginInfo(username: String, token: String) {
        Log.d("viewmodel", "save $username, $token")
        _username.value = username
        _token.value = token
    }
}
