package uz.mahmudxon.halqa.util

import android.content.res.Resources
import uz.mahmudxon.halqa.domain.model.Author
import uz.mahmudxon.halqa.domain.model.SocialMediaLink
import java.util.concurrent.TimeUnit


val Int.dp: Int
    get() =
        (this * Resources.getSystem().displayMetrics.density).toInt()


fun Long.toStringAsFileSize(): String {
    var temp = this.toFloat()
    if (temp < 1024)
        return "${temp.toInt()} B"

    temp /= 1024

    if (temp < 1024)
        return "%.2f KB".format(temp)
    temp /= 1024

    if (temp < 1024)
        return "%.2f MB".format(temp)
    temp /= 1024
    return "%.2f GB".format(temp)
}

fun Long.toStringAsTime(): String {
    val minutes: Long = TimeUnit.MILLISECONDS.toMinutes(this)
    val seconds: Long = TimeUnit.MILLISECONDS.toSeconds(this) - minutes * 60
    return "%02d:%02d".format(minutes, seconds)
}


fun <T> List<T>.asArrayList(): ArrayList<T> {
    val result = ArrayList<T>()
    result.addAll(this)
    return result
}

fun getAuthors(): List<Author> {
    val result = ArrayList<Author>()

    val akromMalik = Author(
        id = 1,
        name = "Aкром Малик",
        imgUrl = "https://firebasestorage.googleapis.com/v0/b/messanger-6a0b4.appspot.com/o/arkom_malik.webp?alt=media&token=dee24abc-2a85-488c-b083-f5d65e606475",
        jobTitle = "Ёзувчи",
        description = "Aкром Малик 1991 йилда Тошкент вилояти Пискент туманида туғилган. Тошкент давлат миллий университети магистри, Aлишер Навоий ижод билимдони, \"Ҳалқа\", \"Жанги\" ва яна бир неча китоблар муаллифи.",
        socialMediaLinks = listOf(
            SocialMediaLink(
                SocialMediaLink.Type.FACEBOOK,
                "https://www.facebook.com/AkromAbdulHamidMalik"
            ),
            SocialMediaLink(
                SocialMediaLink.Type.INSTAGRAM,
                "https://www.instagram.com/akrom_malik_rasmiy"
            ),
            SocialMediaLink(SocialMediaLink.Type.TELEGRAM, "https://t.me/Akrom_Malik_rasmiy"),
            SocialMediaLink(SocialMediaLink.Type.TWITTER, "https://twitter.com/malik_akrom")
        )
    )
    result.add(akromMalik)

    val abdukarimMirzayev = Author(
        id = 2,
        name = "Aбдукарим Мирзаев",
        imgUrl = "https://firebasestorage.googleapis.com/v0/b/messanger-6a0b4.appspot.com/o/abdukarim_mirzayev.jpg?alt=media&token=69138690-c6d2-461c-97a8-1daf8d02f3ce",
        jobTitle = "Журналист, кинорежиссор ва блоггер",
        description = "Абдукарим Мирзаев. 1982 йил 20 февралда Ўзбекистоннинг Сурхондарё вилояти Денов туманида туғилган. K.Behzod номидаги МРДИ ҳамда Кастамону Университетида санъатшунослик ва журналистикадан таълим олган. Журналистика 15 йилдан ортиқ МТКД да фаолият юритган.",
        socialMediaLinks = listOf(
            SocialMediaLink(
                SocialMediaLink.Type.FACEBOOK,
                "https://www.facebook.com/abdukarimmirzayev2002"
            ),
            SocialMediaLink(
                SocialMediaLink.Type.INSTAGRAM,
                "https://www.instagram.com/abdukarimmirzayev"
            ),
            SocialMediaLink(SocialMediaLink.Type.TELEGRAM, "https://t.me/Abdukarim_Mirzayev_Tv"),
            SocialMediaLink(
                SocialMediaLink.Type.YOUTUBE,
                "https://www.youtube.com/c/AbdukarimMirzayev2002"
            ),
            SocialMediaLink(SocialMediaLink.Type.TWITTER, "https://twitter.com/Abdukarim2002")
        )
    )
    result.add(abdukarimMirzayev)

    val mahmudxon = Author(
        id = 3,
        name = "Маҳмудхон Умархонов",
        imgUrl = "https://firebasestorage.googleapis.com/v0/b/messanger-6a0b4.appspot.com/o/mahmudxon.jpg?alt=media&token=0a2f1fd2-e1d4-46a6-a07b-1d1bb375eae6",
        jobTitle = "Дастурчи",
        description = "1998 -йилда Наманан вилояти Чуст туманида туғилган. 2021 - йилда Муҳаммад ал-Хоразмий номидаги Тошкент Aхборот Технологиялари Университетини тамомлаган. 2019 йилдан дастурчи бўлиб ишлаб келади.",
        socialMediaLinks = listOf(
            SocialMediaLink(
                SocialMediaLink.Type.FACEBOOK,
                "http://mahmudxon.uz/social/?social=facebook"
            ),
            SocialMediaLink(
                SocialMediaLink.Type.INSTAGRAM,
                "http://mahmudxon.uz/social/?social=instagram"
            ),
            SocialMediaLink(
                SocialMediaLink.Type.TELEGRAM,
                "http://mahmudxon.uz/social/?social=telegram"
            ),
            SocialMediaLink(
                SocialMediaLink.Type.TWITTER,
                "http://mahmudxon.uz/social/?social=twitter"
            )
        )
    )

    result.add(mahmudxon)


    val shohjahon = Author(
        id = 4,
        name = "Шоҳжаҳон Сирожев",
        imgUrl = "https://firebasestorage.googleapis.com/v0/b/messanger-6a0b4.appspot.com/o/shohjahon.jpg?alt=media&token=c0907906-5273-4f48-af48-e21ef8e3c325",
        jobTitle = "Дастурчи",
        description = "1998 - йилда Навоий вилояти Кармана туманида туғилган. 2021 - йилда Муҳаммад ал-Хоразмий номидаги Тошкент Aхборот Технологиялари Университетини тамомлаган. 2020 йилдан дастурчи бўлиб ишлаб келади.",
        socialMediaLinks = listOf(
            SocialMediaLink(
                SocialMediaLink.Type.TELEGRAM,
                "https://shohjahon24.github.io/shohjahon24/social/?social=telegram"
            ),
            SocialMediaLink(
                SocialMediaLink.Type.GITHUB,
                "https://shohjahon24.github.io/shohjahon24/social/?social=github"
            ),
            SocialMediaLink(
                SocialMediaLink.Type.TWITTER,
                "https://shohjahon24.github.io/shohjahon24/social/?social=twitter"
            ),
            SocialMediaLink(
                SocialMediaLink.Type.LINKED_IN,
                "https://shohjahon24.github.io/shohjahon24/social/?social=linkedIn"
            )
        )
    )

    result.add(shohjahon)

    return result
}