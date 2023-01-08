package com.example.flashcardsapp.ui.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.flashcardsapp.R
import com.example.flashcardsapp.navigation.DECK_ID_KEY
import com.example.flashcardsapp.navigation.DeckDetailsDestination
import com.example.flashcardsapp.navigation.NavigationItem
import com.example.flashcardsapp.ui.deckDetails.DeckDetailsRoute
import com.example.flashcardsapp.ui.favorites.FavoritesRoute
import com.example.flashcardsapp.ui.home.HomeRoute
import com.example.flashcardsapp.ui.theme.Typography
import com.example.flashcardsapp.ui.theme.deckCardGradiantSecondary
import com.example.flashcardsapp.ui.theme.spacing
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    var showBottomBar by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { TopBar() },
        bottomBar = {
            if (showBottomBar) BottomNavigationBar(
                destination = listOf(
                    NavigationItem.HomeDestination, NavigationItem.FavoritesDestination
                ), onNavigateToDestination = {
                    navController.popBackStack(
                        NavigationItem.HomeDestination.route,
                        inclusive = false,
                    )
                    navController.navigate(it.route) { launchSingleTop = true }
                }, currentDestination = navBackStackEntry?.destination
            )
        },
    ) { padding ->
        Surface(
            color = MaterialTheme.colors.background,
            modifier = Modifier.fillMaxSize(),
        ) {
            NavHost(
                navController = navController,
                startDestination = NavigationItem.HomeDestination.route,
                modifier = Modifier.padding(padding)
            ) {
                composable(NavigationItem.HomeDestination.route) {
                    showBottomBar = true
                    HomeRoute(
                        viewModel = getViewModel(),
                        onNavigateToDeckDetails = { id ->
                            navController.navigate(
                                DeckDetailsDestination.createNavigationRoute(id)
                            )
                        },
                    )
                }
                composable(NavigationItem.FavoritesDestination.route) {
                    showBottomBar = true
                    FavoritesRoute(
                        viewModel = getViewModel(),
                        onNavigateToDeckDetails = { id ->
                            navController.navigate(
                                DeckDetailsDestination.createNavigationRoute(id)
                            )
                        },
                    )
                }
                composable(
                    route = DeckDetailsDestination.route,
                    arguments = listOf(navArgument(DECK_ID_KEY) { type = NavType.IntType }),
                ) {
                    showBottomBar = false
                    DeckDetailsRoute(
                        viewModel = getViewModel {
                            parametersOf(
                                it.arguments?.getInt(
                                    DECK_ID_KEY
                                ) ?: throw IllegalArgumentException("No deck!")
                            )
                        },
                    )
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(
    destination: List<NavigationItem>,
    onNavigateToDestination: (NavigationItem) -> Unit,
    currentDestination: NavDestination?
) {
    BottomNavigation(backgroundColor = MaterialTheme.colors.background) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(MaterialTheme.spacing.extraSmallToSmall),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            destination.forEach { destination ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    if (currentDestination != null) {
                        Image(
                            painter = painterResource(id = if (currentDestination.route == destination.route) destination.selectedIconId else destination.unselectedIconId),
                            contentDescription = stringResource(id = R.string.bottom_icon_description),
                            modifier = Modifier
                                .size(dimensionResource(id = R.dimen.bottom_bar_image_height))
                                .clickable { onNavigateToDestination(destination) },
                        )
                    }
                    Text(
                        text = stringResource(id = destination.labelId),
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }
    }

}

@Composable
fun TopBar() {
    Box(
        modifier = Modifier
            .background(deckCardGradiantSecondary)
            .fillMaxWidth()
            .height(dimensionResource(id = R.dimen.top_bar_height)),
        contentAlignment = Alignment.CenterStart,
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            style = Typography.h1,
            modifier = Modifier.align(Alignment.Center),
        )
    }
}
