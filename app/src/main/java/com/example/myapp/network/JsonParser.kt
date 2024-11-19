package com.example.myapp.network

import android.util.Log
import com.example.myapp.database.Area
import com.example.myapp.database.Device
import com.example.myapp.database.House
import org.json.JSONException
import org.json.JSONObject

fun ParseJson(data: JSONObject): List<House> {
        val houseList = mutableListOf<House>()
        try {
            val housesDevices = data.getJSONArray("houses_devices")
            for (i in 0 until housesDevices.length()) {
                val house = housesDevices.getJSONObject(i)
                val houseInfo = house.getJSONObject("house_info")
                val houseName = houseInfo.getString("house_name")
                Log.e("HouseName", "houseName: $houseName")

                val newHouse = House(houseName)  // 创建新房屋对象
                val areasDevices = house.getJSONArray("areas_devices")
                for (j in 0 until areasDevices.length()) {
                    val area = areasDevices.getJSONObject(j)
                    val areaInfo = area.getJSONObject("area_info")
                    val areaName = areaInfo.getString("area_name")

                    val newArea = Area(areaName)  // 创建新区域对象

                    val devices = area.getJSONArray("devices")
                    for (k in 0 until devices.length()) {
                        val device = devices.getJSONObject(k)
                        val deviceName = device.getString("device_name")
                        val deviceType = device.getJSONObject("device_type").getString("type_name")
                        val deviceID = device.getInt("device_id")
                        // 创建设备对象并添加到区域
                        val deviceObj = Device(deviceID, deviceName, deviceType)
                        newArea.addDevice(deviceObj)
                    }
                    // 将区域添加到房屋
                    newHouse.addArea(newArea)
                }
                // 将房屋添加到房屋列表
                houseList.add(newHouse)
            }
        } catch (e: JSONException) {
            Log.e("JsonParser", "JSON parsing error: ${e.message}")
        }
        return houseList
    }
