package ke.kiura.peshy.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ke.kiura.peshy.ui.productDetails.ProductDetailsScreen
import ke.kiura.peshy.ui.products.ProductListScreen


fun NavEvent.navigateTo(navController: NavController, options: NavOptions? = null) {
    if (this is Back) {
        navController.popBackStack()
    } else navController.navigate(this, navOptions = options)
}

@Composable
fun ProductNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = ProductsEvent
    ) {
        composable<ProductsEvent> {
            ProductListScreen(
                navigateTo = {
                    it.navigateTo(navController)
                }
            )
        }

        composable<ProductEvent> {
            ProductDetailsScreen(
                navigateTo = {
                    it.navigateTo(navController)
                }
            )
        }
    }
}