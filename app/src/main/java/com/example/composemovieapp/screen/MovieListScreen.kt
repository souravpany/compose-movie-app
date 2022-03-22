package com.example.composemovieapp.screen

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.composemovieapp.MovieViewModel
import com.example.composemovieapp.R
import com.example.composemovieapp.model.Movie
import com.example.composemovieapp.utils.ApiState
import com.example.composemovieapp.utils.DEFAULT_IMAGE
import com.example.composemovieapp.utils.Screen
import com.example.composemovieapp.utils.loadPicture
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@ExperimentalMaterialApi
@Composable
fun MovieListScreen(
    viewModel: MovieViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val activity = (LocalContext.current as? Activity)
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Popular Movies") },
                navigationIcon = {
                    IconButton(onClick = {
                        activity?.finish()
                    }) {
                        Icon(Icons.Filled.Close, contentDescription = "Close")
                    }
                }
            )
        },
    ) {
        val state by viewModel.getMovieStateFlow.collectAsState()

        state.let { apiState ->
            when (apiState) {
                is ApiState.Empty ->
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(R.string.generic_error),
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                is ApiState.Loading ->
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator()
                    }
                is ApiState.Failure -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = apiState.errorMessage?.let { stringResource(id = it) }
                                .toString(),
                            modifier = Modifier.padding(16.dp),
                            fontSize = 20.sp
                        )
                    }
                }
                is ApiState.Success<*> -> {
                    Box(modifier = Modifier.fillMaxSize()) {
                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            items(apiState.data as ArrayList<Movie>) { items ->
                                MovieListItem(items) { item ->
                                    navController.navigate(route = "${Screen.MovieDetailsScreen.route}/${item.title}/${item.releaseDate}/${item.voteAverage}/${item.overview}${item.posterPath}")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@ExperimentalCoroutinesApi
@ExperimentalMaterialApi
@Composable
fun MovieListItem(item: Movie, onItemClicked: (Movie) -> Unit) {
    Card(
        elevation = 4.dp,
        onClick = { onItemClicked(item) },
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(height = 385.dp)
    ) {
        Column {
            val imagePath = "https://image.tmdb.org/t/p/w500/${item.posterPath}"
            imagePath.let { url ->
                val image = loadPicture(url = url, defaultImage = DEFAULT_IMAGE).value
                image?.let { img ->
                    Image(
                        bitmap = img.asImageBitmap(),
                        contentScale = ContentScale.FillWidth,
                        contentDescription = "",
                        modifier = Modifier
                            .height(200.dp)
                            .fillMaxWidth()
                    )
                }
            }
            Text(
                text = item.title,
                modifier = Modifier.padding(8.dp),
                style = MaterialTheme.typography.h6
            )

            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Release Date: ${item.releaseDate}",
                    style = MaterialTheme.typography.subtitle2
                )

                Text(
                    text = "Vote: ${item.voteAverage}",
                    style = MaterialTheme.typography.subtitle2
                )
            }

            Text(
                text = item.overview.toString(),
                modifier = Modifier.padding(8.dp),
                maxLines = 8,
                style = MaterialTheme.typography.caption
            )
        }
    }
}
