package uz.mahmudxon.halqa.interactor.story

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import uz.mahmudxon.halqa.datasource.db.story.StoryDao
import uz.mahmudxon.halqa.datasource.db.story.StoryEntityMapper
import uz.mahmudxon.halqa.domain.data.DataState
import uz.mahmudxon.halqa.domain.model.Chapter

class GetChapterList(
    private val dao: StoryDao,
    private val storyEntityMapper: StoryEntityMapper
) {
    fun execute(): Flow<DataState<List<Chapter>>> {
        return flow {
            emit(DataState.loading())
            try {
                val cacheStories = dao.getChapters()
                emit(DataState.success(cacheStories.map { storyEntityMapper.mapToDomainModel(it) }))
            } catch (e: Exception) {
                emit(DataState.error(e.message ?: "Unknown Error"))
            }
        }
    }
}