package com.oishikenko.android.recruitment.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.oishikenko.android.recruitment.data.model.CookingRecord
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CookingRecordPagingSource @Inject constructor(
    private var cookingRecordsRepository: CookingRecordsRepository
) : PagingSource<Int, CookingRecord>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CookingRecord> {
        val pageNum = params.key ?: 0

        val response = cookingRecordsRepository.getCookingRecords(
            offet = pageNum * 10,
            limit = 10
        )

        var cookingRecordList: List<CookingRecord> = emptyList()
        response.map {
            it.body()?.cookingRecords ?: emptyList()
        }.collect { new ->
            cookingRecordList = new
        }
        return LoadResult.Page(
            data = cookingRecordList,
            prevKey = null,
            nextKey = if (cookingRecordList.isNotEmpty()) pageNum + 1 else null
        )
    }

    override fun getRefreshKey(state: PagingState<Int, CookingRecord>): Int? {
        return state.anchorPosition
    }
}