package com.yasminagbreil.metro.domain

interface MetroRepository {
    fun stationNames(): List<String>
    fun linesOf(station: String): List<Int>
    fun lineColorHex(lineId: Int): String
    fun findRoute(from: String, to: String): RouteResult?
}