package org.cristiangoncas.architectcoders.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.cristiangoncas.architectcoders.ui.screens.catalog.CatalogScreen
import org.cristiangoncas.architectcoders.ui.screens.detail.DetailScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavItem.Catalog.route) {
        composable(NavItem.Catalog) {
            CatalogScreen(viewModel = viewModel(), onClick = { movieId ->
                navController.navigate(NavItem.Detail.createRoute(movieId))
            })
        }
        composable(NavItem.Detail) { backStackEntry ->
            DetailScreen(
                viewModel = viewModel(),
                movieId = backStackEntry.findArg(arg = NavArg.MediaId),
                onBack = { navController.popBackStack() }
            )
        }
    }
}

private fun NavGraphBuilder.composable(
    navItem: NavItem,
    content: @Composable (NavBackStackEntry) -> Unit
) {
    composable(
        route = navItem.route,
        arguments = navItem.args
    ) {
        content(it)
    }
}

private inline fun <reified T> NavBackStackEntry.findArg(arg: NavArg): T {
    requireNotNull(arguments)

    val value: Any? = when (T::class) {
        String::class -> arguments?.getString(arg.key)
        Int::class -> arguments?.getInt(arg.key)
        Boolean::class -> arguments?.getBoolean(arg.key)
        Float::class -> arguments?.getFloat(arg.key)
        Long::class -> arguments?.getLong(arg.key)
        else -> null
    }
    requireNotNull(value)
    return value as T
}
