package uz.mahmudxon.halqa.domain.model

import uz.mahmudxon.halqa.R

data class SocialMediaLink(
    var type: Int = 0,
    var link: String = ""
) {
    object Type {
        const val FACEBOOK = 1
        const val YOUTUBE = 2
        const val TWITTER = 3
        const val INSTAGRAM = 4
        const val TELEGRAM = 5
        const val GITHUB = 6
        const val LINKED_IN = 7
    }


    val icon: Int
        get() = when (type) {
            Type.FACEBOOK -> R.drawable.ic_facebook
            Type.INSTAGRAM -> R.drawable.ic_instagram
            Type.YOUTUBE -> R.drawable.ic_youtube
            Type.TWITTER -> R.drawable.ic_twitter
            Type.TELEGRAM -> R.drawable.ic_telegram
            Type.GITHUB -> R.drawable.ic_github
            Type.LINKED_IN -> R.drawable.ic_linkedin
            else -> R.drawable.ic_link
        }
}