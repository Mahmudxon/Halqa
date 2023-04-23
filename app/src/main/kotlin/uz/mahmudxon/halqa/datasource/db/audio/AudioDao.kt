package uz.mahmudxon.halqa.datasource.db.audio

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import uz.mahmudxon.halqa.datasource.db.BaseDao

@Dao
interface AudioDao : BaseDao<AudioEntity> {

    @Query("select * from AudioBook order by storyId")
    fun getAll(): Flow<List<AudioEntity>>

    @Query("select * from AudioBook where storyId = :storyId")
    fun getByStory(storyId: Int): Flow<AudioEntity>

    @Query("select * from AudioBook where currentDownloadSize > 0 and isDownloaded=0")
    fun getDownloadingAudios(): Flow<List<AudioEntity>>
}