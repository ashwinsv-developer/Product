package com.product.ui.home
import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.product.data.model.Product
import com.product.ui.components.AppTopBar
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import com.product.MainActivity
import com.product.MainViewModel
import com.product.di.ApiResult
import com.product.ui.components.CategoryChip
import com.product.ui.components.shimmerEffect
import com.product.util.Constants

@SuppressLint("ContextCastToActivity")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onLogout: () -> Unit,
    onProductClick: (Int) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()
    val categories = viewModel.categories
    val selectedCategory = viewModel.selectedCategory

    val activity = LocalContext.current as MainActivity

    val mainViewModel: MainViewModel = hiltViewModel(activity)
    val email = mainViewModel.getUserEmail()

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Products",
                headerText = email,
                showBackButton = false,
                actions = {
                    IconButton(onClick = {
                        mainViewModel.logout()
                        onLogout()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = Constants.LOGOUT,
                            tint = Color.White
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Category Filters
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent)
                    .padding(vertical = 8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(categories) { category ->
                    CategoryChip(
                        category = category,
                        selected = selectedCategory == category,
                        onClick = {
                            viewModel.selectCategory(category)
                        }
                    )
                }
            }

            when (val state = uiState) {
                is ApiResult.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                is ApiResult.Success -> {
                    ProductList(
                        products = state.data,
                        onProductClick = onProductClick
                    )
                }
                is ApiResult.Error -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "Error: ${state.exception}", color = MaterialTheme.colorScheme.error)
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(onClick = { viewModel.fetchProducts() }) {
                                Text(Constants.RETRY)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProductList(
    products: List<Product>,
    onProductClick: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(products, key = { it.id }) { product ->
            ProductItem(product = product, onClick = { onProductClick(product.id) })
        }
    }
}

@Composable
fun ProductItem(
    product: Product,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .height(120.dp)
        ) {
            val imageUrl = product.thumbnail

            CoilImage(
                imageModel = { imageUrl },

                modifier = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(8.dp)),

                imageOptions = ImageOptions(
                    contentScale = ContentScale.Crop,
                    contentDescription = product.title
                ),

                loading = {

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .shimmerEffect()
                    )
                },

                failure = { state ->

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.LightGray)
                    ) {

                        Text(
                            text = Constants.FAILED_TO_LOAD,
                            fontSize = 12.sp
                        )
                    }

                    Log.e(
                        "HomeScreen",
                        "Failed to load image: $imageUrl"
                    )

                    state.reason?.let {
                        Log.e(
                            "HomeScreen",
                            "Error: ${it.message}",
                            it
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = product.title,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.Black
                    )
                    Text(
                        text = product.brand,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Black
                    )
                    
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "$${product.price}",
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        val originalPrice = (product.price / (1 - product.discountPercentage / 100)).toInt()
                        Text(
                            text = "$$originalPrice",
                            style = MaterialTheme.typography.bodySmall.copy(
                                textDecoration = TextDecoration.LineThrough,
                                color = Color.Black
                            )
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${product.discountPercentage}% OFF",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF4CAF50)
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    RatingBar(rating = product.rating)
                    StockStatus(stock = product.stock)
                }
            }
        }
    }
}

@Composable
fun RatingBar(rating: Double) {
    Surface(
        color = Color(0xFFFFC107),
        shape = RoundedCornerShape(4.dp)
    ) {
        Text(
            text = "★ $rating",
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
            style = MaterialTheme.typography.labelSmall,
            color = Color.White
        )
    }
}

@Composable
fun StockStatus(stock: Int) {
    val (statusText, color) = when {
        stock > 50 -> Constants.Available to Color(0xFF2E7D32) // Green
        stock in 1..50 -> Constants.Limited to Color(0xFFEF6C00) // Orange
        else -> Constants.UnAvailable to Color(0xFFC62828) // Red
    }

    Text(
        text = statusText,
        color = color,
        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
    )
}
