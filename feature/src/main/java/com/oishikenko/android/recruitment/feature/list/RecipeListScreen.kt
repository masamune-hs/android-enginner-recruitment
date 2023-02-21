package com.oishikenko.android.recruitment.feature.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.oishikenko.android.recruitment.data.model.CookingRecord
import com.oishikenko.android.recruitment.feature.R

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun RecipeListScreen(
    viewModel: RecipeListViewModel = hiltViewModel()
) {
//    val cookingRecords by viewModel.cookingRecords.collectAsStateWithLifecycle()
    val lazyPagingItems = viewModel.cookingRecords.collectAsLazyPagingItems()
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "recipeList") {
        composable("recipeList") {
            RecipeLists(lazyPagingItems, navController)
        }
        composable(
            route = "recipeDetail/{index}",
            arguments = listOf(navArgument("index") { type = NavType.IntType })
        ) { backStackEntry ->
            lazyPagingItems[backStackEntry.arguments?.getInt("index") ?: 1]?.let {
                RecipeDetail(
                    cookingRecord = it
                ) {
                    navController.navigateUp()
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RecipeLists(
    cookingRecords: LazyPagingItems<CookingRecord>,
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
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .consumedWindowInsets(innerPadding)
        ) {
            items(cookingRecords.itemCount) { index ->
                val cookingRecord = cookingRecords[index]
                if (cookingRecord != null) {
                    RecipeListItem(cookingRecord = cookingRecord) {
                        navController.navigate("recipeDetail/$index")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewRecipeListScreen() {
    MaterialTheme {
//        RecipeListScreen()
    }
}
