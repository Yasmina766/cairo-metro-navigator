package com.yasminagbreil.metro.data

import com.yasminagbreil.metro.domain.MetroRepository
import com.yasminagbreil.metro.domain.Route
import com.yasminagbreil.metro.domain.RouteResult
import com.yasminagbreil.metro.domain.Step
import javax.inject.Inject

class FakeMetroRepository @Inject constructor() : MetroRepository {

    private val stations = listOf(
        "Helwan", "Maadi", "Sadat", "Shohadaa", "El Marg",
        "Attaba", "Cairo University", "Giza", "Shubra El Kheima", "Dokki"
    )

    override fun stationNames(): List<String> = stations

    override fun lineColorHex(lineId: Int): String = when (lineId) {
        1 -> "#1E88E5"
        2 -> "#E53935"
        3 -> "#43A047"
        else -> "#9E9E9E"
    }

    override fun findRoute(
        from: String,
        to: String
    ): RouteResult? {
        if (from !in stations || to !in stations) return null
        if (from == to) return null
        val route = Route(
            steps = listOf(
                Step("Helwan", 1),
                Step("Maadi", 1),
                Step("Sadat", 1),
                Step("Sadat", 2),
                Step("Giza", 2),
                Step("Cairo University", 2)
            ),
            stops = 5,
            transfers = 1
        )
        return RouteResult(route = route, fare = 10, minutes = 25)
    }

}