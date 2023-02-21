package com.oishikenko.android.recruitment.feature.list


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.oishikenko.android.recruitment.data.model.CookingRecord
import com.oishikenko.android.recruitment.feature.R

enum class RecipeType(val recipeType: String) {
    MAIN_DISH("main_dish"),
    SIDE_DISH("side_dish"),
    SOUP("soup")
}

@Composable
fun RecipeDetail(
    cookingRecord: CookingRecord,
    back: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.recipe_detail_toolbar_title))
                },
                navigationIcon = {
                    IconButton(onClick = back) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(Modifier.padding(innerPadding)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 360.dp)
            ) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth(),
                    model = cookingRecord.imageUrl,
                    contentScale = ContentScale.FillWidth,
                    contentDescription = "thumbnail"
                )
                val label = setupRecipeTypeLabel(recipeType = cookingRecord.recipeType)
                if (label != null) {
                    Image(
                        modifier = Modifier
                            .padding(8.dp)
                            .align(Alignment.BottomEnd),
                        painter = label,
                        contentDescription = "recipe_type"
                    )
                }
            }
            Text(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp)
                    .fillMaxWidth(),
                text = cookingRecord.comment,
                fontSize = 16.sp
            )
            Text(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 8.dp)
                    .align(Alignment.End),
                text = cookingRecord.recordedAt,
                fontSize = 14.sp,
                color = colorResource(id = R.color.recorded_at_text_color)
            )
        }
    }
}

@Composable
fun setupRecipeTypeLabel(recipeType: String): Painter? {
    return when (recipeType) {
        RecipeType.MAIN_DISH.recipeType -> painterResource(id = R.drawable.label_main_dish)
        RecipeType.SIDE_DISH.recipeType -> painterResource(id = R.drawable.label_side_dish)
        RecipeType.SOUP.recipeType -> painterResource(id = R.drawable.label_soup)
        else -> null
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRecipeDetail() {
    RecipeDetail(
        CookingRecord(
            comment = "ごまのコクと酸味がさわやかなタレを添えて。",
            imageUrl = "https://cooking-records.ex.oishi-kenko.com/images/52.jpg",
            recordedAt = "2018-04-20 14:04:42",
            recipeType = "main_dish"
        )
    ) {}
}
