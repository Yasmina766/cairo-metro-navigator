package com.yasminagbreil.metro.domain

data class Step(
    val station: String,
    val line: Int
)

data class Route(
    val steps: List<Step>,
    val stops: Int,
    val transfers: Int
)

data class RouteResult(
    val route: Route,
    val fare: Int,
    val minutes: Int
)