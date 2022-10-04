package uz.mahmudxon.halqa.domain.model

data class Author(
    val id: Int,
    val name: String,
    val imgUrl : String,
    val jobTitle: String,
    val description: String,
    val socialMediaLinks: List<SocialMediaLink>
)