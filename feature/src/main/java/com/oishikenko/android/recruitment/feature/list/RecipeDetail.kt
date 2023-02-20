package com.oishikenko.android.recruitment.feature.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.oishikenko.android.recruitment.data.model.CookingRecord
import com.oishikenko.android.recruitment.feature.R

@Composable
fun RecipeDetail(cookingRecord: CookingRecord) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.recipe_detail_toolbar_title))
                },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(Modifier.padding(innerPadding)) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(360.dp)
                .background(color = colorResource(id = R.color.recorded_at_text_color)
                )) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(360.dp)
                        .background(color = colorResource(id = R.color.recorded_at_text_color))
                    ,
                    model = cookingRecord.imageUrl,
                    contentDescription = "thumbnail"
                )
                Text(
                    modifier = Modifier.align(Alignment.BottomEnd),
                    text = cookingRecord.recipeType
                )
            }
            Text(modifier = Modifier.fillMaxWidth(), text = cookingRecord.comment)
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.End),
                text = cookingRecord.recordedAt
            )
        }
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
    )
}
