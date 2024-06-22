package com.example.myapplication

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

// Data class for Product
data class Product(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String,
    val rating: Rating
)

// Data class for Rating
data class Rating(
    val rate: Double,
    val count: Int
)

// Retrofit API Service interface
interface ApiService {
    @GET("products")
    suspend fun getProducts(): List<Product>
}

// Repository for fetching products
class ProductRepository {
    suspend fun getProducts(): List<Product> {
        return RetrofitInstance.api.getProducts()
    }
}

// Retrofit instance setup
object RetrofitInstance {
    private const val BASE_URL = "https://fakestoreapi.com/"

    val api: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(ApiService::class.java)
    }
}

// ViewModel for managing products
class ProductViewModel : ViewModel() {
    private val repository = ProductRepository()
    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> get() = _products

    init {
        fetchProducts()
    }

    private fun fetchProducts() {
        viewModelScope.launch {
            _products.value = repository.getProducts()
        }
    }
}

// Composable function for displaying a list of products
@Composable
fun ProductList(viewModel: ProductViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val products by viewModel.products.observeAsState(initial = emptyList())

    LazyColumn {
        items(products) { product ->
            ProductItem(product)
        }
    }
}

// Composable function for displaying a single product item
@Composable
fun ProductItem(product: Product) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = product.title,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Text(
            text = "Price: $${product.price}",
            color = Color.Gray,
            fontSize = 16.sp
        )
        Text(
            text = product.description,
            fontSize = 14.sp
        )
        // Load and display image using Coil
        Image(
            painter = // Enable crossfade animation between placeholders and loaded images
            rememberAsyncImagePainter(
                ImageRequest.Builder // Placeholder drawable resource
                // Error drawable resource
                    (LocalContext.current).data(data = product.image)
                    .apply(block = fun ImageRequest.Builder.() {
                        crossfade(true) // Enable crossfade animation between placeholders and loaded images
                        placeholder(R.drawable.placeholder) // Placeholder drawable resource
                        error(R.drawable.error) // Error drawable resource
                    }).build()
            ),
            contentDescription = product.title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            contentScale = ContentScale.Crop // Adjust content scale type as needed
        )
    }
}