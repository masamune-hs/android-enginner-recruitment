package com.oishikenko.android.recruitment.feature.list

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.consumedWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.oishikenko.android.recruitment.feature.R

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CookingRecordsScreen(
    viewModel: CookingRecordsViewModel = CookingRecordsViewModel()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier,
            ) {
                Text(text = stringResource(id = R.string.cooking_records_title))
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .consumedWindowInsets(innerPadding)
        ) {
            items(viewModel.cookingRecords) {
                CookingRecordListItem(it)
            }
        }
    }
}

@Preview
@Composable
fun PreviewCookingRecordsScreen(){
    MaterialTheme {
        CookingRecordsScreen()
    }
}