package ke.kiura.peshy.ui.products


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import ke.kiura.peshy.R
import ke.kiura.peshy.data.model.Product
import ke.kiura.peshy.ui.components.PeshyLoader
import ke.kiura.peshy.ui.components.PeshyOutlinedTextField
import ke.kiura.peshy.ui.navigation.NavEvent
import ke.kiura.peshy.ui.navigation.ProductEvent
import ke.kiura.peshy.ui.products.ProductListViewModel.ProductListUiState

@Composable
fun ProductListScreen(navigateTo: (NavEvent) -> Unit) {
    val viewModel: ProductListViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column {
        SearchBar(
            value = "",
            onValueChange = viewModel::onSearchQueryChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            when (uiState) {
                is ProductListUiState.Success -> ProductListContent(
                    products = (uiState as ProductListUiState.Success).products,
                    onProductClick = {
                        navigateTo(
                            ProductEvent(it)
                        )
                    }
                )

                is ProductListUiState.Error -> Error((uiState as ProductListUiState.Error).message)
                else -> PeshyLoader()
            }
        }
    }
}

@Composable
fun ProductListContent(
    products: List<Product>,
    onProductClick: (Int) -> Unit,
) {
    LazyColumn {
        items(products) { product ->
            ProductItem(
                product = product,
                onClick = { onProductClick(product.id) }
            )
        }
    }

}

@Composable
fun SearchBar(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    PeshyOutlinedTextField(
        value = value,
        onTextValueChanged = onValueChange,
        label = { Text(stringResource(R.string.search_products)) },
        modifier = modifier
    )
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ProductItem(
    product: Product,
    onClick: () -> Unit
) {
    OutlinedCard(
        border = CardDefaults.outlinedCardBorder(
            enabled = true
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            GlideImage(
                model = product.thumbnail,
                contentDescription = null,
                loading = placeholder(R.drawable.ic_shopping),
                failure = placeholder(R.drawable.ic_shopping),
                modifier = Modifier
                    .size(80.dp)
                    .padding(end = 4.dp)
            )

            Column {
                Text(
                    text = product.title,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "$${product.price}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
fun Error(message: String) {
    Box(Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .align(Alignment.Center),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier.size(250.dp),
                painter = painterResource(R.drawable.ic_error),
                tint = MaterialTheme.colorScheme.primaryContainer,
                contentDescription = null
            )

            Text(
                text = message,
                style = MaterialTheme.typography.labelMedium
            )

        }
    }
}