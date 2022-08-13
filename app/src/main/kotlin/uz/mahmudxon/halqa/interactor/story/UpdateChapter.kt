package uz.mahmudxon.halqa.interactor.story

import kotlinx.coroutines.flow.flow
import uz.mahmudxon.halqa.datasource.db.story.StoryDao
import uz.mahmudxon.halqa.datasource.db.story.StoryEntityMapper

class UpdateChapter(
    private val dao: StoryDao,
    private val storyEntityMapper: StoryEntityMapper
) {
    fun execute(id: Int, story: String) = flow<Boolean> {
        try {
            val storyEntity = dao.getChapterById(id)
            storyEntity.story = story
            dao.update(storyEntity)
            emit(true)
        } catch (e: Exception) {
            emit(false)
        }
    }
}