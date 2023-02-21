package com.oishikenko.android.recruitment.feature.list

import android.util.Log
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.oishikenko.android.recruitment.data.model.CookingRecord
import com.oishikenko.android.recruitment.feature.R

@Composable
fun RecipeListScreen(
    viewModel: RecipeListViewModel = hiltViewModel()
) {
    val cookingRecords = viewModel.cookingRecordsPager.collectAsLazyPagingItems()
    val lazyListState = rememberLazyListState()
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "recipeList") {
        composable("recipeList") {
            RecipeLists(cookingRecords, lazyListState, navController)
        }
        composable(
            route = "recipeDetail/{index}",
            arguments = listOf(navArgument("index") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val index = backStackEntry.arguments?.getInt("index")
            if (index != null) {
                cookingRecords[index].let {
                    if (it != null) {
                        RecipeDetail(cookingRecord = it) {
                            navController.navigateUp()
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RecipeLists(
    cookingRecords: LazyPagingItems<CookingRecord>,
    lazyListState: LazyListState,
    navController: NavController
) {
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(
                    space = 8.dp,
                    alignment = Alignment.CenterHorizontally
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.cooking_records_title),
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
                Image(
                    painter = painterResource(id = R.drawable.header_icon),
                    contentDescription = "header_icon"
                )
            }
        }
    ) { innerPadding ->
        Log.d(
            "miyake",
            "loadstate.append.Loading[${cookingRecords.loadState.append is LoadState.Loading}]\n" +
                    "loadSate.append.Error[${cookingRecords.loadState.append is LoadState.Error}]"
        )
        cookingRecords.loadState
        when (cookingRecords.loadState.append) {
            LoadState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Image(
                        modifier = Modifier.size(48.dp),
                        painter = painterResource(id = R.drawable.circular_progress_bar),
                        contentDescription = "loading"
                    )
                }
            }
            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(innerPadding)
                        .padding(top = 8.dp)
                        .consumedWindowInsets(innerPadding),
                    state = lazyListState
                ) {
                    items(cookingRecords.itemCount) { index ->
                        cookingRecords[index]?.let {
                            RecipeListItem(it) {
                                navController.navigate("recipeDetail/$index")
                            }
                        }
                    }
                    item { Spacer(modifier = Modifier.height(60.dp)) }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewRecipeListScreen() {
    MaterialTheme {
        RecipeListScreen()
    }
}
