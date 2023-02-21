package com.oishikenko.android.recruitment.feature.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.oishikenko.android.recruitment.data.model.CookingRecord
import com.oishikenko.android.recruitment.data.repository.CookingRecordsPagingSource
import com.oishikenko.android.recruitment.data.repository.CookingRecordsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    cookingRecordsRepository: CookingRecordsRepository
) : ViewModel() {
//    val cookingRecords: StateFlow<List<CookingRecord>> =
//        cookingRecordsRepository.getCookingRecords(offet = 0, limit = 30).map {
//            it.body()?.cookingRecords ?: emptyList<CookingRecord>()
//        }.stateIn(
//            scope = viewModelScope,
//            started = SharingStarted.WhileSubscribed(5000),
//            initialValue = emptyList<CookingRecord>()
//        )

    val cookingRecords: Flow<PagingData<CookingRecord>> =
        Pager(
            PagingConfig(pageSize = 10, initialLoadSize = 10)
        ) {
            CookingRecordsPagingSource(cookingRecordsRepository)
        }.flow
}