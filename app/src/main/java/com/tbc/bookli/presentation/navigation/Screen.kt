import kotlinx.serialization.Serializable


sealed interface Screen

@Serializable
object Login : Screen

@Serializable
object Register : Screen

@Serializable
object HomeScreen : Screen

@Serializable
object BookShelfScreen : Screen

@Serializable
data object SearchScreen : Screen

@Serializable
data object ProfileScreen : Screen

@Serializable
data class BookDetails(val bookId: String) : Screen

@Serializable
data class Read(val url: String) : Screen