package com.example.myapp.network

import android.util.Log
import com.example.myapp.model.AccountDevices
import com.example.myapp.model.AccountRequest
import com.example.myapp.model.Jwt

object AccountManager {
    var token: String? = null
}

suspend fun AccountManager.login(username: String, password: String): Jwt? {
    try {
        val response = api.login(AccountRequest(username, password))
        if (response.code != 200) {
            return null
        }
        token = response.data?.token
        return response.data
    } catch (e: Exception) {
        Log.e("Login", "failed", e)
        return null
    }
}

suspend fun AccountManager.auth(token: String): Boolean {
    try {
        this.token = token
        return apiWithToken.auth().code == 200
    } catch (e: Exception) {
        Log.e("Auth", "http error", e)
        this.token = null
        return false
    }
}

suspend fun AccountManager.signup(username: String, password: String): Boolean {
    try {
        return api.signup(AccountRequest(username, password)).code == 200
    } catch (e: Exception) {
        return false
    }
}

suspend fun AccountManager.fetchData(token: String): AccountDevices? {
    try {
        val response = apiWithToken.getAllAccountDevices()
        if (response.code != 200) {
            return null
        }
        return response.data
    } catch (e: Exception) {
        Log.e("Fetch Data", e.message, e)
        return null
    }
}