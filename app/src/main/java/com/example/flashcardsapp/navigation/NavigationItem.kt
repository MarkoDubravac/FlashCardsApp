package com.example.flashcardsapp.navigation

import com.example.flashcardsapp.R

const val HOME_ROUTE = "Home"
const val FAVORITES_ROUTE = "Favorites"
const val COMPLETED_ROUTE = "Completed"

sealed class NavigationItem(
    override val route: String,
    val selectedIconId: Int,
    val unselectedIconId: Int,
    val labelId: Int,
) : FscAppDestination(route) {
    object HomeDestination : NavigationItem(
        route = HOME_ROUTE,
        selectedIconId = R.drawable.ic_home_filled,
        unselectedIconId = R.drawable.ic_home_outlined,
        labelId = R.string.home_text,
    )

    object FavoritesDestination : NavigationItem(
        route = FAVORITES_ROUTE,
        selectedIconId = R.drawable.ic_favorite_filled,
        unselectedIconId = R.drawable.ic_favorite_outlined,
        labelId = R.string.favorites_text,
    )

    object CompleteDestination : NavigationItem(
        route = COMPLETED_ROUTE,
        selectedIconId = R.drawable.ic_completed_filled,
        unselectedIconId = R.drawable.ic_completed_outlined,
        labelId = R.string.completed_text,
    )
}
