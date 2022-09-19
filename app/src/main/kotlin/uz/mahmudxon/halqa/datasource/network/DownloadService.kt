package uz.mahmudxon.halqa.datasource.network

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url

interface DownloadService {
    @Streaming
    @GET
    suspend fun downloadFile(@Url fileUrl: String): Response<ResponseBody>

    @GET("halqa/")
    suspend fun getAudiSizes(): Response<Map<String, Long>>
}