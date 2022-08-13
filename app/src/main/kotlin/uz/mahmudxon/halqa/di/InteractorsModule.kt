package uz.mahmudxon.halqa.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.mahmudxon.halqa.datasource.db.story.StoryDao
import uz.mahmudxon.halqa.datasource.db.story.StoryEntityMapper
import uz.mahmudxon.halqa.interactor.story.GetChapter
import uz.mahmudxon.halqa.interactor.story.GetChapterList
import uz.mahmudxon.halqa.interactor.story.UpdateChapter

@Module
@InstallIn(SingletonComponent::class)
object InteractorsModule {

    @Provides
    fun provideGetChapterList(
        storyDao: StoryDao,
        storyEntityMapper: StoryEntityMapper
    ): GetChapterList = GetChapterList(storyDao, storyEntityMapper)


    @Provides
    fun provideGetChapter(storyDao: StoryDao, storyEntityMapper: StoryEntityMapper): GetChapter =
        GetChapter(storyDao, storyEntityMapper)

    @Provides
    fun provideUpdateChapter(storyDao: StoryDao, storyEntityMapper: StoryEntityMapper): UpdateChapter =
        UpdateChapter(storyDao, storyEntityMapper)

}