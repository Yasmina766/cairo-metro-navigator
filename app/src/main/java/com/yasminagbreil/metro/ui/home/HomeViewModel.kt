package com.yasminagbreil.metro.ui.home

import androidx.lifecycle.ViewModel
import com.yasminagbreil.metro.domain.MetroRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MetroRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        val stations = repository.stationNames()
        val from = stations.getOrElse(0) { "" }
        val to = stations.getOrElse(2) { "" }
        _uiState.value = HomeUiState(
            stations = stations,
            from = from,
            to = to,
            fromLines = repository.linesOf(from),
            toLines = repository.linesOf(to)
        )
    }

    fun onFromSelected(station: String) {
        _uiState.update {
            it.copy(
                from = station,
                fromLines = repository.linesOf(station),
                sameStationError = false
            )
        }
    }

    fun onToSelected(station: String) {
        _uiState.update {
            it.copy(
                to = station,
                toLines = repository.linesOf(station),
                sameStationError = false
            )
        }
    }

    fun onSwapClicked() {
        _uiState.update {
            it.copy(
                from = it.to, to = it.from,
                fromLines = it.toLines,
                toLines = it.fromLines,
                sameStationError = false
            )
        }
    }

    fun onFindRouteClicked(): Boolean {
        val state = _uiState.value
        val isSame = state.from == state.to
        _uiState.update { it.copy(sameStationError = isSame) }
        return !isSame
    }
}