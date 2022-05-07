package lostankit7.droid.stockmarket.presentation.company_listings.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import lostankit7.droid.stockmarket.R
import lostankit7.droid.stockmarket.presentation.company_listings.CompanyListingsEvent
import lostankit7.droid.stockmarket.presentation.company_listings.CompanyListingsViewModel

@Composable
@Destination(start = true)
fun CompanyListingsScreen(
    navigator: DestinationsNavigator,
    viewModel: CompanyListingsViewModel = hiltViewModel(),
) {
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = viewModel.state.isRefreshing)
    val state = viewModel.state

    OutlinedTextField(
        value = state.searchQuery,
        onValueChange = {
            viewModel.onEvent(CompanyListingsEvent.OnSearchQueryChanged(it))
        },
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        placeholder = {
            Text(text = stringResource(R.string.search_bar_placeholder))
        },
        maxLines = 1,
        singleLine = true
    )
    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = {
            viewModel.onEvent(CompanyListingsEvent.Refresh)
        }
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(state.companies.size) { index ->
                val company = state.companies[index]
                CompanyItem(
                    company = company,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            //todo navigate to detail screen
                        }
                        .padding(16.dp)
                )

                if (index < state.companies.size) {
                    Divider(modifier = Modifier.padding(
                        horizontal = 16.dp
                    ))
                }
            }
        }
    }
}