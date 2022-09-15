package uz.mahmudxon.halqa.datasource.network

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.FileOutputStream

object DownloadManger {
    private val service by lazy {
        Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://mahmudxon.uz/").build().create(DownloadService::class.java)
    }

    private var _listener: OnDownloadListener? = null

    fun setListener(listener: OnDownloadListener) {
        _listener = listener
    }

    private val jobs = HashMap<Int, Job>()

    fun download(id: Int) {
        val job = CoroutineScope(IO).launch {
            val result = service.downloadFile(fileUrl = "${AudioUrl.generate(id)}")
            result.body()?.let {
                try {
                    val file = File(AudioUrl.offLineUrl(id))
                    file.delete()
                    val fileReader = ByteArray(4096)
                    val fileSize = it.contentLength()
                    var fileSizeDownloaded: Long = 0
                    val inputStream = it.byteStream()
                    val outputStream = FileOutputStream(AudioUrl.offLineUrl(id))
                    while (true) {
                        val read = inputStream.read(fileReader)
                        if (read == -1) {
                            break
                        }
                        outputStream.write(fileReader, 0, read)
                        fileSizeDownloaded += read.toLong()
                        withContext(Main) {
                            _listener?.onProcess(id, fileSizeDownloaded, fileSize)
                        }
                    }
                    outputStream.flush()
                    withContext(Main) {
                        _listener?.onDownloadComplete(id)
                    }
                } catch (e: Exception) {
                    println(e.toString())
                }
            }
        }
        jobs[id] = job
    }

    fun cancel(id: Int) {
        jobs[id]?.cancel()
    }

    interface OnDownloadListener {
        fun onProcess(id: Int, current: Long, total: Long)
        fun onDownloadComplete(id: Int)
    }
}