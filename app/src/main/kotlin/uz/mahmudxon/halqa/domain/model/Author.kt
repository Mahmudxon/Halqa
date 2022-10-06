package uz.mahmudxon.halqa.domain.model

import uz.mahmudxon.halqa.R

data class Author(
    var id: Int = 0,
    var name: String = "",
    var imgUrl: String = "",
    var jobTitle: String = "",
    var description: String = "",
    var socialMediaLinks: List<SocialMediaLink> = listOf()
) {

    fun getDefaultImage(id: Int) = when (id) {
        1 -> R.drawable.akrom_malik
        2 -> R.drawable.abdukarim_mirzayev
        3 -> R.drawable.mahmudxon
        4 -> R.drawable.shohjahon
        else -> R.drawable.placeholder
    }

}
