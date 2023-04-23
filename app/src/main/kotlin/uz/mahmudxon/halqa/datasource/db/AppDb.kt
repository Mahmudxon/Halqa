package uz.mahmudxon.halqa.datasource.db

import androidx.room.Database
import androidx.room.RoomDatabase
import uz.mahmudxon.halqa.datasource.db.audio.AudioDao
import uz.mahmudxon.halqa.datasource.db.audio.AudioEntity
import uz.mahmudxon.halqa.datasource.db.story.StoryDao
import uz.mahmudxon.halqa.datasource.db.story.StoryEntity

@Database(entities = [StoryEntity::class, AudioEntity::class], version = 2)
abstract class AppDb : RoomDatabase() {
    abstract fun getStoryDao(): StoryDao
    abstract fun getAudioDao() : AudioDao
}