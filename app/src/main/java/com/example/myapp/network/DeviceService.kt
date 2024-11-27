package com.example.myapp.network

import android.util.Log
import com.example.myapp.model.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

const val DeviceServiceUrl = "http://47.108.27.238/api/my/device"

suspend fun executeDeviceService(
    deviceId: Int,
    serviceName: String,
    method: String,
    body: String?,
    contentType: String?
): Boolean? {
    return withContext(Dispatchers.IO) {
        val url = "$DeviceServiceUrl/$deviceId/service/$serviceName"
        Log.d("URL", url)
        Log.d("Body", body.toString())
        Log.d("Content-Type", contentType?.toMediaTypeOrNull().toString())
        Log.d("Method", method.uppercase())

        val request = Request.Builder()
            .url(url)
            .method(
                method.uppercase(),
                body?.toRequestBody(contentType?.toMediaTypeOrNull())
            )
            .build()

        val response = okhttp.newCall(request).execute()
        response.body?.let {
            val data = it.string()
            Log.d("Response", data)
            val apiResponse = Json.decodeFromString<ApiResponse<Unit>>(data)
            apiResponse.code == 200
        }
    }
}