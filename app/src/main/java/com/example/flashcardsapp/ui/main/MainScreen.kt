package com.example.flashcardsapp.ui.main

import android.content.Context
import android.content.Context.MODE_PRIVATE
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
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
import com.example.flashcardsapp.navigation.LoginNavigation
import com.example.flashcardsapp.navigation.NavigationItem
import com.example.flashcardsapp.ui.completed.CompletedRoute
import com.example.flashcardsapp.ui.deckDetails.DeckDetailsRoute
import com.example.flashcardsapp.ui.favorites.FavoritesRoute
import com.example.flashcardsapp.ui.home.HomeRoute
import com.example.flashcardsapp.ui.signIn.SignInScreen
import com.example.flashcardsapp.ui.signUp.SHARED_PREFS
import com.example.flashcardsapp.ui.signUp.SignUpScreen
import com.example.flashcardsapp.ui.theme.Typography
import com.example.flashcardsapp.ui.theme.mainBeige
import com.example.flashcardsapp.ui.theme.spacing
import com.google.firebase.auth.FirebaseAuth
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val firebaseAuth = FirebaseAuth.getInstance()

    val context = LocalContext.current

    var showBottomBar by remember { mutableStateOf(false) }
    var showTopBar by remember { mutableStateOf(true) }
    val showBackIcon = !showBottomBar
    val density = LocalDensity.current
    val bottomBarAnimationHeight = dimensionResource(id = R.dimen.bottom_bar_animation)
    Scaffold(
        topBar = {
            if(showTopBar) {
                TopBar(
                    navigationIcon = { if (showBackIcon) BackIcon(onBackClick = navController::popBackStack) },
                    onLogoutClick = {
                        firebaseAuth.signOut()
                        val sharedPreferences =
                            context.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE)
                        val editor = sharedPreferences.edit()
                        editor.putString("name", "false")
                        editor.commit()
                        navController.navigate(LoginNavigation.SignInScreen.route)
                    },
                    showLogout = !showBackIcon
                )
            }
        },
        bottomBar = {
            AnimatedVisibility(showBottomBar,
                enter = slideInVertically { with(density) { bottomBarAnimationHeight.roundToPx() } },
                exit = slideOutVertically { with(density) { bottomBarAnimationHeight.roundToPx() } }) {
                BottomNavigationBar(
                    destination = listOf(
                        NavigationItem.HomeDestination,
                        NavigationItem.FavoritesDestination,
                        NavigationItem.CompleteDestination,
                    ), onNavigateToDestination = {
                        navController.popBackStack(
                            NavigationItem.HomeDestination.route,
                            inclusive = false,
                        )
                        navController.navigate(it.route) { launchSingleTop = true }
                    }, currentDestination = navBackStackEntry?.destination
                )
            }
        },
    ) { padding ->
        Surface(
            color = MaterialTheme.colors.background,
            modifier = Modifier.fillMaxSize(),
        ) {
            NavHost(
                navController = navController,
                startDestination = if (checkLogin(context)) {
                    NavigationItem.HomeDestination.route
                } else {
                    LoginNavigation.SignInScreen.route
                },
                //NavigationItem.HomeDestination.route
                modifier = Modifier.padding(padding)
            ) {
                composable(LoginNavigation.SignInScreen.route) {
                    showBottomBar = false
                    showTopBar = false
                    SignInScreen(
                        viewModel = getViewModel(),
                        onSignIn = {
                            navController.navigate(
                                NavigationItem.HomeDestination.route
                            )
                        },
                        goToSignUp = {
                            navController.navigate(
                                LoginNavigation.SignUpScreen.route
                            )
                        }
                    )
                }
                composable(LoginNavigation.SignUpScreen.route) {
                    showBottomBar = false
                    showTopBar = false
                    SignUpScreen(
                        viewModel = getViewModel(),
                        onSignUp = {
                            navController.navigate(
                                NavigationItem.HomeDestination.route
                            )
                        },
                        goToSignIn = {
                            navController.navigate(
                                LoginNavigation.SignInScreen.route
                            )
                        }
                    )
                }
                composable(NavigationItem.HomeDestination.route) {
                    showBottomBar = true
                    showTopBar = true
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
                    showTopBar = true
                    FavoritesRoute(
                        viewModel = getViewModel(),
                        onNavigateToDeckDetails = { id ->
                            navController.navigate(
                                DeckDetailsDestination.createNavigationRoute(id)
                            )
                        },
                    )
                }

                composable(NavigationItem.CompleteDestination.route) {
                    showBottomBar = true
                    showTopBar = true
                    CompletedRoute(viewModel = getViewModel())
                }

                composable(
                    route = DeckDetailsDestination.route,
                    arguments = listOf(navArgument(DECK_ID_KEY) { type = NavType.IntType }),
                ) {
                    showBottomBar = false
                    showTopBar = true
                    DeckDetailsRoute(
                        viewModel = getViewModel {
                            parametersOf(
                                it.arguments?.getInt(
                                    DECK_ID_KEY
                                ) ?: throw IllegalArgumentException("No deck!")
                            )
                        },
                        //todo
                        onPlayClick = {}
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
    BottomNavigation(backgroundColor = mainBeige) {
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
private fun BackIcon(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Image(
        painter = painterResource(id = R.drawable.ic_back_icon),
        contentDescription = stringResource(id = R.string.back),
        modifier = modifier
            .clickable { onBackClick() }
            .padding(start = MaterialTheme.spacing.medium)
            .size(dimensionResource(id = R.dimen.number_in_circle_size)),
        alignment = Alignment.Center,
    )
}

@Composable
private fun LogoutIcon(
    onLogoutClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Image(
        painter = painterResource(id = R.drawable.ic_logout),
        contentDescription = stringResource(id = R.string.logout),
        modifier = modifier
            .clickable { onLogoutClick() }
            .padding(end = MaterialTheme.spacing.medium)
            .size(dimensionResource(id = R.dimen.number_in_circle_size)),
        alignment = Alignment.Center,
    )
}

@Composable
fun TopBar(
    navigationIcon: @Composable (() -> Unit)? = null,
    onLogoutClick: () -> Unit,
    showLogout: Boolean,
) {
    Box(
        modifier = Modifier
            .background(mainBeige)
            .fillMaxWidth()
            .height(dimensionResource(id = R.dimen.top_bar_height)),
        contentAlignment = Alignment.CenterStart,
    ) {
        navigationIcon?.invoke()
        Text(
            text = stringResource(id = R.string.app_name),
            style = Typography.h1,
            modifier = Modifier.align(Alignment.Center),
        )
        if (showLogout) {
            LogoutIcon(
                onLogoutClick = { onLogoutClick() }, modifier = Modifier.align(Alignment.CenterEnd)
            )
        }
    }
}

private fun checkLogin(context: Context): Boolean {
    val sharedPreferences = context.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE)
    val check = sharedPreferences.getString("name", "")
    if (check.equals("true")) {
        return true
    }
    return false
}
