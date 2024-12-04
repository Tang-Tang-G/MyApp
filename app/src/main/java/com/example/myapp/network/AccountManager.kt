package com.example.myapp.network

import android.util.Log
import com.example.myapp.model.AccountDevices
import com.example.myapp.model.AccountRequest
import com.example.myapp.model.AreaInfo
import com.example.myapp.model.Jwt
import com.example.myapp.model.Member
import com.example.myapp.model.UserInfo

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
        Log.e("signup",e.toString())
        return false
    }
}

suspend fun AccountManager.fetchData(): AccountDevices? {
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

suspend fun AccountManager.fetchUserInfo(): UserInfo? {
    try {
        val response = apiWithToken.getUserInfo()
        if (response.code == 200) {
            return response.data
        }
        Log.e("fetchUserInfo", response.message)
        return null
    } catch (e: Exception) {
        Log.e("fetchUserInfo", e.message, e)
        return null
    }
}


suspend fun AccountManager.updateUserInfo(userInfo: UserInfo): Boolean {
    try {
        val response = apiWithToken.updateUserInfo(userInfo)
        if (response.code == 200) {
            return true
        }
        Log.e("updateUserInfo", response.message)
        return false
    } catch (e: Exception) {
        Log.e("updateUserInfo", e.message, e)
        return false
    }
}

suspend fun AccountManager.fetchMemberInfo(): Member? {
    try {
        val response = apiWithToken.getMemberInfo()
        if (response.code == 200) {
            return response.data
        }
        Log.e("fetchMemberInfo", response.message)
        return null
    } catch (e: Exception) {
        Log.e("fetchMemberInfo", e.message, e)
        return null
    }
}

suspend fun AccountManager.fetchAreasInfo(): List<AreaInfo>? {
    try {
        val response = apiWithToken.getAreasInfo()
        if (response.code == 200) {
            return response.data
        }
        Log.e("fetchAreasInfo", response.message)
        return null
    } catch (e: Exception) {
        Log.e("fetchAreasInfo", e.message, e)
        return null
    }
}