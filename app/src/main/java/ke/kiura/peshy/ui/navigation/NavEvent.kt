package ke.kiura.peshy.ui.navigation

import kotlinx.serialization.Serializable

sealed interface NavEvent

@Serializable
data object Back: NavEvent

@Serializable
data object ProductsEvent: NavEvent

@Serializable
data class ProductEvent(val productId: Int): NavEvent
