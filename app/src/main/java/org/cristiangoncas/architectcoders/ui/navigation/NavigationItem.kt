package org.cristiangoncas.architectcoders.ui.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class NavItem(
    val baseRoute: String,
    private val navArgs: List<NavArg> = emptyList()
) {
    val route = run {
        val argValues = navArgs.map { "{${it.key}}" }
        listOf(baseRoute)
            .plus(argValues)
            .joinToString("/")
    }

    val args = navArgs.map { arg ->
        navArgument(arg.key) { type = arg.navType }
    }

    data object Catalog : NavItem("catalog")
    data object Detail : NavItem("detail", listOf(NavArg.MediaId)) {
        fun createRoute(movieId: Int) = "$baseRoute/$movieId"
    }
}

enum class NavArg(val key: String, val navType: NavType<*>) {
    MediaId("movieId", NavType.IntType)
}
