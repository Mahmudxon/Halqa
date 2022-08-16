package uz.mahmudxon.halqa.ui.story

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.mahmudxon.halqa.domain.data.DataState
import uz.mahmudxon.halqa.domain.model.Chapter
import uz.mahmudxon.halqa.interactor.story.GetChapter
import uz.mahmudxon.halqa.interactor.story.UpdateChapter
import javax.inject.Inject

@HiltViewModel
class StoryViewModel @Inject constructor(
    private val getChapter: GetChapter,
    private val updateChapter: UpdateChapter
) : ViewModel() {
    private val _chapter = MutableLiveData<DataState<Chapter>>()
    val chapter : LiveData<DataState<Chapter>> = _chapter

    fun getChapter(id: Int) {
        getChapter.execute(id).onEach {
            _chapter.value = it
        }.launchIn(viewModelScope)
    }
}