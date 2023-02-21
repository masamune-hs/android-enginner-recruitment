package com.oishikenko.android.recruitment.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.oishikenko.android.recruitment.data.model.CookingRecord

class CookingRecordsPagingSource(
    private val cookingRecordsRepository: CookingRecordsRepository
) : PagingSource<Int, CookingRecord>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CookingRecord> {
        val perPage = params.loadSize
        val page = params.key ?: 1
        val next = page + 1
        val prev = if (page == 1) null else page - 1

        return LoadResult.Page(
            data = cookingRecordsRepository.getPagingCookingRecords(perPage, 10).body()?.cookingRecords!!,
            prevKey = prev,
            nextKey = next
        )

//        return try {
//            val nextItem = params.key ?: 1
//            val response = cookingRecordsRepository.getPagingCookingRecords(nextItem, 10)
//            LoadResult.Page(
//                data = response.body()?.cookingRecords!!,
//                prevKey = if (nextItem == 1) null else nextItem.minus(1),
//                nextKey = if (response.body()?.cookingRecords != null) null else nextItem.plus(1),
//            )
//        } catch (e: Exception) {
//            LoadResult.Error(e)
//        }
    }

    override fun getRefreshKey(state: PagingState<Int, CookingRecord>): Int? {
        return state.anchorPosition
    }
}