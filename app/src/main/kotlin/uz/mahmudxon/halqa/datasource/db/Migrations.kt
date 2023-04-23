package uz.mahmudxon.halqa.datasource.db

import androidx.room.migration.Migration

val MIGRATION_1_TO_2 = Migration(1, 2)
{ database ->
    database.execSQL("CREATE TABLE IF NOT EXISTS `AudioBook` (`storyId` INTEGER not null, `size` INTEGER not null, `isDownloaded` INTEGER not null, `currentDownloadSize` INTEGER not null, PRIMARY KEY(`storyId`))")
}