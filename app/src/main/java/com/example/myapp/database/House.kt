package com.example.myapp.database

class House(val name: String) {
    private val _areas: MutableList<Area> = mutableListOf()
    val areas: List<Area> get() = _areas

    fun addArea(area: Area) {
        _areas.add(area)
    }

    fun removeArea(area: Area) {
        _areas.remove(area)
    }
}
