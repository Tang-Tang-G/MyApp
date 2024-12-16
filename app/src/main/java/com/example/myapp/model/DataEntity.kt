package com.example.myapp.model

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
    @SerialName("area_name") var areaName: String
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
data class UserInfo(
    val email: String?,
    val gender: String?,
    val age: Int?,
    val city: String?,
    val name: String?
)

@Serializable
data class AccountUpdate(
    @SerialName("old_password") val oldPassword: String,
    @SerialName("new_password") val newPassword: String?,
    @SerialName("account_name") val userName: String?
)

@Serializable
data class HouseMember(
    @SerialName("house_info") val houseInfo: HouseInfo,
    @SerialName("account") val memberInfo: List<AccountInfo>
)

@Serializable
data class Member(
    @SerialName("houses_member") val houseMember: List<HouseMember>
)

@Serializable
data class DeviceAdd(
    @SerialName("efuse_mac") val eFuseMac: String,
    @SerialName("device_name") val deviceName: String,
    @SerialName("model_id") val modelId: Int,
    @SerialName("area_id") val areaId: Int,
)

@Serializable
data class AreaAdd(
    @SerialName("house_id") val houseId: Int,
    @SerialName("area_name") val areaName: String,
)

@Serializable
data class AreaRename(
    @SerialName("area_name") var areaName: String?
)

@Serializable
data class HouseCreate(
    @SerialName("house_name") val houseName: String
)

@Serializable
data class HouseJoin(
    @SerialName("house_id") val houseId: Int,
    @SerialName("account_id") val accountId: Int,
)

@Serializable
data class AccountRequest(val username: String, val password: String)

@Serializable
data class Jwt(val token: String)

@Serializable
data class ApiResponse<T>(
    val code: Int,
    val message: String,
    val timestamp: Int,
    val data: T? = null
)




