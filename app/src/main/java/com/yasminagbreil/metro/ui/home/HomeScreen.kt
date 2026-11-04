package com.yasminagbreil.metro.ui.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yasminagbreil.metro.R
import com.yasminagbreil.metro.ui.theme.CairoMetroNavigatorTheme
import com.yasminagbreil.metro.ui.theme.ErrorRed
import com.yasminagbreil.metro.ui.theme.Line1Blue


private val fakeRecentTrips = listOf(
    RecentTrip("Helwan", "El Marg", "Line 1 Direct • 33 stops"),
    RecentTrip("Shohadaa", "Cairo University", "Line 2 Direct • 6 stops"),
    RecentTrip("Maadi", "Sadat", "Line 1 Direct • 10 stops")
)

@Composable
fun HomeRoute(
    onRouteRequested: (from: String, to: String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreen(
        state = state,
        onFromClick = { },
        onToClick = { },
        onSwapClick = viewModel::onSwapClicked,
        onFindRouteClick = {
            if (viewModel.onFindRouteClicked()) {
                onRouteRequested(state.from, state.to)
            }
        }
    )
}

@Composable
fun HomeScreen(
    state: HomeUiState,
    onFromClick: () -> Unit,
    onToClick: () -> Unit,
    onSwapClick: () -> Unit,
    onFindRouteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
            .padding(horizontal = 16.dp)
    ) {
        HomeTopBar()

        Spacer(Modifier.height(12.dp))

        StationsCard(
            state = state,
            onFromClick = onFromClick,
            onToClick = onToClick,
            onSwapClick = onSwapClick,
            onFindRouteClick = onFindRouteClick
        )

        Spacer(Modifier.height(24.dp))

        RecentTripsHeader()

        Spacer(Modifier.height(8.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(fakeRecentTrips) { trip -> RecentTripItem(trip) }
        }
    }
}

@Composable
private fun HomeTopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(R.drawable.ic_train_tb),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.size(40.dp)
            )
            Spacer(Modifier.size(10.dp))
            Text(
                text = "Metro Navigator",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        Text(
            text = "AR | EN",
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(horizontal = 10.dp, vertical = 6.dp)
        )
    }
}

@Composable
private fun StationsCard(
    state: HomeUiState,
    onFromClick: () -> Unit,
    onToClick: () -> Unit,
    onSwapClick: () -> Unit,
    onFindRouteClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Box {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    StationField(
                        label = "FROM",
                        station = state.from,
                        iconRes = R.drawable.ic_circle_dot,
                        iconTint = Line1Blue,
                        isError = false,
                        trailing = lineLabel(state.fromLines),
                        onClick = onFromClick
                    )
                    StationField(
                        label = "TO",
                        station = state.to,
                        iconRes = R.drawable.ic_pin,
                        iconTint = ErrorRed,
                        isError = state.sameStationError,
                        trailing = if (state.sameStationError) "Duplicate" else lineLabel(state.toLines),
                        onClick = onToClick
                    )
                }

                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.surface,
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 30.dp)
                        .size(34.dp)
                        .clickable(onClick = onSwapClick)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            painter = painterResource(R.drawable.ic_swap),
                            contentDescription = "Swap stations",
                            tint = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }

            if (state.sameStationError) {
                Spacer(Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_error),
                        tint = Color.Unspecified,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp)
                    )
                    Text(
                        text = "Please choose two different stations",
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = onFindRouteClick,
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (state.sameStationError) MaterialTheme.colorScheme.surfaceVariant
                    else MaterialTheme.colorScheme.primary,
                    contentColor = if (state.sameStationError) MaterialTheme.colorScheme.onSurfaceVariant
                    else MaterialTheme.colorScheme.onPrimary
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                Text("Find route", fontSize = 16.sp, fontWeight = FontWeight.Medium)
                Spacer(Modifier.size(8.dp))
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

@Composable
private fun StationField(
    label: String,
    station: String,
    iconRes: Int,
    iconTint: Color,
    isError: Boolean,
    trailing: String? = null,
    onClick: () -> Unit
) {
    val borderColor = if (isError) MaterialTheme.colorScheme.error
    else MaterialTheme.colorScheme.outline
    val fieldColor = if (isError) MaterialTheme.colorScheme.error.copy(alpha = 0.08f)
    else MaterialTheme.colorScheme.background
    val shape = RoundedCornerShape(14.dp)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape)
            .background(fieldColor)
            .border(if (isError) 1.5.dp else 1.dp, borderColor, shape)
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = null,
            tint = iconTint,
            modifier = Modifier.size(20.dp)
        )
        Spacer(Modifier.size(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label,
                fontSize = 10.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = station.ifEmpty { "Select station" },
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        if (trailing != null) {
            Text(
                text = trailing,
                fontSize = 12.sp,
                color = if (isError) MaterialTheme.colorScheme.error
                else MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun RecentTripsHeader() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "RECENT TRIPS",
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            letterSpacing = 1.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        TextButton(onClick = { }) {
            Text(
                text = "Clear",
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun RecentTripItem(trip: RecentTrip) {
    val toColor = MaterialTheme.colorScheme.onSurfaceVariant

    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_clock),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(18.dp)
                )
            }
            Spacer(Modifier.size(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(SpanStyle(fontWeight = FontWeight.SemiBold)) { append(trip.from) }
                        withStyle(SpanStyle(color = toColor)) { append(" to ") }
                        withStyle(SpanStyle(fontWeight = FontWeight.SemiBold)) { append(trip.to) }
                    },
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = trip.details,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

private fun lineLabel(lines: List<Int>): String? = when {
    lines.isEmpty() -> null
    lines.size == 1 -> "Line ${lines.first()}"
    else -> "Interchange"
}

@Preview(showBackground = true, name = "Light")
@Composable
private fun HomeScreenPreview() {
    CairoMetroNavigatorTheme(darkTheme = false) {
        HomeScreen(
            state = HomeUiState(
                from = "Helwan", to = "Sadat",
                fromLines = listOf(1), toLines = listOf(1, 2)
            ),
            onFromClick = {}, onToClick = {}, onSwapClick = {}, onFindRouteClick = {}
        )
    }
}

@Preview(showBackground = true, name = "Dark")
@Composable
private fun HomeScreenPreviewDark() {
    CairoMetroNavigatorTheme(darkTheme = true) {
        HomeScreen(
            state = HomeUiState(
                from = "Helwan", to = "Sadat",
                fromLines = listOf(1), toLines = listOf(1, 2)
            ),
            onFromClick = {}, onToClick = {}, onSwapClick = {}, onFindRouteClick = {}
        )
    }
}

@Preview(showBackground = true, name = "Error")
@Composable
private fun HomeScreenErrorPreview() {
    CairoMetroNavigatorTheme(darkTheme = false) {
        HomeScreen(
            state = HomeUiState(
                from = "Helwan", to = "Helwan",
                fromLines = listOf(1), toLines = listOf(1, 2),
                sameStationError = true
            ),
            onFromClick = {}, onToClick = {}, onSwapClick = {}, onFindRouteClick = {}
        )
    }
}