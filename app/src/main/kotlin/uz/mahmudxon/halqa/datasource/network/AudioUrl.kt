package uz.mahmudxon.halqa.datasource.network

object AudioUrl {
    fun generate(chapterId : Int) : String? {
        if(chapterId < 1 || chapterId > 32)
            return null
        return "http://mahmudxon.uz/halqa/$chapterId-bob.m4a"
    }
}