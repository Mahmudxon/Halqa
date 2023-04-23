package uz.mahmudxon.halqa.datasource.db.audio

import uz.mahmudxon.halqa.domain.model.AudioBook
import uz.mahmudxon.halqa.domain.util.DomainMapper

class AudioEntityMapper : DomainMapper<AudioEntity, AudioBook> {
    override fun mapToDomainModel(model: AudioEntity): AudioBook {
        val status: AudioBook.Status =
            if (model.isDownloaded) AudioBook.Status.Downloaded
            else if (model.currentDownloadSize > 0) AudioBook.Status.Downloading(
                model.currentDownloadSize,
                model.size
            )
            else AudioBook.Status.Online(model.size)
        return AudioBook(
            id = model.storyId,
            title = "${model.storyId} - боб",
            status = status
        )
    }

    override fun mapFromDomainModel(domainModel: AudioBook): AudioEntity {
        val downloadingSize = if (domainModel.status is AudioBook.Status.Downloading) {
            (domainModel.status as AudioBook.Status.Downloading).current
        } else -1L

        val isDownloaded = domainModel.status == AudioBook.Status.Downloaded
        return AudioEntity(
            storyId = domainModel.id,
            isDownloaded = isDownloaded,
            currentDownloadSize = downloadingSize
        )
    }
}