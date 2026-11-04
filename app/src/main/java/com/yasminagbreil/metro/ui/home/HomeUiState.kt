package com.yasminagbreil.metro.ui.home

data class HomeUiState(
    val stations:List<String> =emptyList(),
    val from:String="",
    val to:String="",
    val fromLines: List<Int> = emptyList(),
    val toLines: List<Int> = emptyList(),
    val sameStationError: Boolean=false
)
