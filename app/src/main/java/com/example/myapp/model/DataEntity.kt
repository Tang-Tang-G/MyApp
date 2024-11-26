package com.example.myapp.model

import android.R
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class AccountInfo(
    @SerialName("account_id") val accountId: Int,
    val username: String
)

@Serializable
data class HouseInfo(
    @SerialName("house_id") val houseId: Int,
    @SerialName("house_name") val houseName: String
)

@Serializable
data class AreaInfo(
    @SerialName("area_id") val areaId: Int,
    @SerialName("area_name") val areaName: String
)

@Serializable
data class DeviceType(
    @SerialName("type_id") val typeId: Int,
    @SerialName("type_name") val typeName: String
)

@Serializable
data class DeviceInfo(
    @SerialName("device_id") val deviceId: Int,
    @SerialName("device_name") val deviceName: String,
    @SerialName("efuse_mac") val eFuseMac: String,
    @SerialName("model_id") val modelId: Int,
    @SerialName("model_name") val modelName: String,
    @SerialName("device_type") val deviceType: DeviceType,
    val service: List<JsonObject>
)

@Serializable
data class AreaDevices(
    @SerialName("area_info") val areaInfo: AreaInfo,
    val devices: List<DeviceInfo>
)

@Serializable
data class HouseDevices(
    @SerialName("house_info") val houseInfo: HouseInfo,
    @SerialName("areas_devices") val areasDevices: List<AreaDevices>
)

@Serializable
data class AccountDevices(
    @SerialName("account_info") val accountInfo: AccountInfo,
    @SerialName("houses_devices") val housesDevices: List<HouseDevices>
)

@Serializable
data class ApiResponse<T>(val code: Int, val message: String, val timestamp: Int, val data: T)

@Serializable
data class UserInfo(val name: String, val age: Int, val email: String, val gender :String,val city: String)
data class MutableUserInfo(var name: String, var gender: String, var age: Int, var city: String, var email: String)