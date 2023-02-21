package com.oishikenko.android.recruitment.feature.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.oishikenko.android.recruitment.data.repository.CookingRecordPagingSource
import com.oishikenko.android.recruitment.data.repository.CookingRecordsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    cookingRecordsRepository: CookingRecordsRepository
) : ViewModel() {
    val cookingRecordsPager =
        Pager(PagingConfig(pageSize = 1000)) {
            CookingRecordPagingSource(cookingRecordsRepository)
        }.flow.cachedIn(viewModelScope)
}