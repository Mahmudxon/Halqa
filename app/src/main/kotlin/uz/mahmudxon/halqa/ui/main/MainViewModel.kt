package uz.mahmudxon.halqa.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.mahmudxon.halqa.domain.data.DataState
import uz.mahmudxon.halqa.domain.model.Chapter
import uz.mahmudxon.halqa.interactor.story.GetChapterList
import uz.mahmudxon.halqa.util.TAG
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject constructor(
    private val getChapterList: GetChapterList
) : ViewModel() {
    private val _chaptersState = MutableLiveData<DataState<List<Chapter>>>(DataState())
    val chaptersState: LiveData<DataState<List<Chapter>>> = _chaptersState

    fun getChaptersList() {
        Log.d(TAG, "getChaptersList : $getChapterList")
        getChapterList.execute().onEach { dataState ->
            _chaptersState.value = dataState
        }.launchIn(viewModelScope)
    }

}