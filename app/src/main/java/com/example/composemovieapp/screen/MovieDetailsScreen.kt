package com.example.composemovieapp.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.composemovieapp.utils.DEFAULT_IMAGE
import com.example.composemovieapp.utils.loadPicture
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@ExperimentalMaterialApi
@Composable
fun MovieDetailsScreen(
    navController: NavHostController,
    title: String?,
    releaseDate: String?,
    vote: String?,
    overView: String?,
    posterPath: String?
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = title.toString()) },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(Icons.Filled.ArrowBack, "backIcon")
                    }
                }
            )
        },
    ) {
        Card(
            elevation = 4.dp,
            onClick = {},
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .height(height = 575.dp)
        ) {
            Column {
//                Image(
//                    painter = painterResource(id = R.drawable.ic_launcher_background),
//                    contentScale = ContentScale.FillWidth,
//                    contentDescription = "",
//                    modifier = Modifier
//                        .height(200.dp)
//                        .fillMaxWidth()
//                )
                val imagePath = "https://image.tmdb.org/t/p/w500/${posterPath}"
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
                    text = title.toString(),
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
                        text = "Release Date: $releaseDate",
                        modifier = Modifier.padding(8.dp),
                        style = MaterialTheme.typography.subtitle2
                    )
                    Text(
                        text = "Vote: $vote",
                        modifier = Modifier.padding(8.dp),
                        style = MaterialTheme.typography.subtitle2
                    )
                }

                Text(
                    text = overView.toString(),
                    modifier = Modifier.padding(8.dp),
                    maxLines = 5,
                    style = MaterialTheme.typography.caption
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp, horizontal = 12.dp),
                    horizontalAlignment = Alignment.End
                ) {
                    Button(onClick = {
                        navController.popBackStack()
                    }
                    ) {
                        Text(text = "Submit")
                    }
                }
            }
        }
    }
}