package uz.mahmudxon.halqa.interactor.audio

import uz.mahmudxon.halqa.datasource.db.audio.AudioDao
import uz.mahmudxon.halqa.datasource.db.audio.AudioEntityMapper

class GetAudioBook (private val dao: AudioDao, private val mapper: AudioEntityMapper) {
    suspend fun execute() = dao.getAll()
}