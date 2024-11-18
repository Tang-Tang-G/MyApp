package com.example.myapp.database

class Area(val name: String) {
    private val _devices: MutableList<Device> = mutableListOf()
    val devices: List<Device> get() = _devices

    fun addDevice(device: Device) {
        _devices.add(device)
    }

    fun removeDevice(device: Device) {
        _devices.remove(device)
    }
}

