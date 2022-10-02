package uz.mahmudxon.halqa.datasource.network

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.mahmudxon.halqa.util.TAG
import uz.mahmudxon.halqa.util.logd
import java.io.File
import java.io.FileOutputStream

object DownloadManger {
    private val service by lazy {
        Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl(AudioUrl.baseUrl).build().create(DownloadService::class.java)
    }

    private var _listener: OnDownloadListener? = null
    private var sizeListener: AudioSizeListener? = null

    fun setListener(listener: OnDownloadListener) {
        _listener = listener
    }

    fun setSizeListener(listener: AudioSizeListener) {
        sizeListener = listener
    }

    private val jobs = HashMap<Int, Job>()

    fun download(id: Int) {
        val job = CoroutineScope(IO).launch {
            try {
                val url = "${AudioUrl.generate(id)}"
                Log.d(TAG, "download: url=$url, id=$id")
                val result = service.downloadFile(fileUrl = url)
                result.body()?.let {
                    val file = File(AudioUrl.offLineUrl(id))
                    if (file.exists())
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
                }
            } catch (e: Exception) {
                Log.d(TAG, "download: $e")
                withContext(Main) {
                    _listener?.onDownloadCancelled(id)
                }
            }
        }
        jobs[id] = job
    }

    fun cancel(id: Int) {
        jobs[id]?.cancel()
        _listener?.onDownloadCancelled(id)
    }

    fun requestSizes() {
        CoroutineScope(IO).launch {
            try {
                val result = service.getAudiSizes()
                if (result.isSuccessful) {
                    result.body()?.let {
                        withContext(Main) {
                            sizeListener?.onSizesRevive(it)
                        }
                    }
                }
            } catch (e: Exception) {
                e.message?.let { logd(it) }
            }
        }
    }

    interface OnDownloadListener {
        fun onProcess(id: Int, current: Long, total: Long)
        fun onDownloadComplete(id: Int)
        fun onDownloadCancelled(id: Int)
    }

    interface AudioSizeListener {
        fun onSizesRevive(sizes: Map<String, Long>)
    }
}