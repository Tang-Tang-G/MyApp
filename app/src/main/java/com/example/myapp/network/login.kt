package com.example.myapp.network

import android.util.Log
import com.example.myapp.model.LoginInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

object OkHttpSingleton {
    val client = OkHttpClient()
}

object AccountManager

suspend fun AccountManager.login(username: String, password: String): LoginInfo? {
    return withContext(Dispatchers.IO) {
        val body = JSONObject()
        body.put("username", username)
        body.put("password", password)
        val request = Request.Builder()
            .url("http://47.108.27.238/api/login")
            .post(body.toString().toRequestBody("application/json".toMediaType()))
            .build()

        try {
            val response = OkHttpSingleton.client.newCall(request).execute()
            response.body?.let {
                val json = it.string()
                Log.d("network", "json: $json")
                val obj = JSONObject(json)
                // Code == 200 ?
                val data = obj["data"] as JSONObject
                LoginInfo(username, data["token"].toString())
            }
        } catch (e: Exception) {
            Log.d(this.javaClass.name, "no data", e)
            null
        }
    }
}

suspend fun AccountManager.auth(token: String): Boolean {
    return withContext(Dispatchers.IO) {
        val request = Request.Builder()
            .url("http://47.108.27.238/api/auth")
            .addHeader("Authorization", "Bearer $token")
            .build()

        val response = OkHttpSingleton.client.newCall(request).execute()
        response.body?.let {
            val json = it.string()
            Log.d("Auth", json)
            try {
                val obj = JSONObject(json)
                val code = obj["code"] as Int
                code == 200
            } catch (e: Exception) {
                false
            }
        } ?: false
    }
}

suspend fun AccountManager.signup(username: String, password: String): LoginInfo? {
    return withContext(Dispatchers.IO) {
        val body = JSONObject()
        body.put("username", username)
        body.put("password", password)
        val request = Request.Builder()
            .url("http://47.108.27.238/api/signup")
            .post(body.toString().toRequestBody("application/json".toMediaType()))
            .build()

        try {
            val response = OkHttpSingleton.client.newCall(request).execute()
            response.body?.let {
                val json = it.string()
                Log.d("network", "json: $json")
                val obj = JSONObject(json)
                if (obj["code"] != 200) {
                    return@let null
                }
                val data = obj["data"] as JSONObject
                LoginInfo(username, data["token"].toString())
            }
        } catch (e: Exception) {
            Log.d(this.javaClass.name, "no data", e)
            null
        }
    }
}

suspend fun AccountManager.fetchData(token: String): JSONObject? {
    return withContext(Dispatchers.IO) {
        val request = Request.Builder()
            .url("http://47.108.27.238/api/my/device")
            .get()
            .addHeader("Authorization", "Bearer $token")
            .build()

        val response = OkHttpSingleton.client.newCall(request).execute()
        response.body?.let {
            val json = it.string()
            Log.d("fetchData", json)
            try {
                val obj = JSONObject(json)
                val data = obj["data"] as JSONObject
                data
            } catch (e: Exception) {
                Log.d("fetchData", "error", e)
                null
            }
        }
    }
}