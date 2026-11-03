package com.yasminagbreil.metro.ui.home

data class HomeUiState(
    val stations:List<String> =emptyList(),
    val from:String="",
    val to:String="",
    val sameStationError: Boolean=false
)
