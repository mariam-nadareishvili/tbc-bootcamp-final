import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.android_bootcamp.R
import com.example.android_bootcamp.presentation.navigation.Screen

@Composable
fun BottomBar(navController: NavController) {
    BottomAppBar(
        modifier = Modifier.fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.primary,
        contentPadding = PaddingValues(16.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.clickable { navController.navigate(Screen.Home) }) {
                Icon(
                    painter = painterResource(R.drawable.ic_home),
                    contentDescription = null,
                    Modifier
                        .size(24.dp)
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text("Home")
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.clickable { navController.navigate(Screen.Search) }) {
                Icon(
                    painter = painterResource(R.drawable.ic_seach),
                    contentDescription = null,
                    Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text("Search")

            }
            Column(horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.clickable { navController.navigate(Screen.BookShelf) }) {
                Icon(
                    painter = painterResource(R.drawable.ic_book_shelf),
                    contentDescription = null,
                    Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text("Bookshelf")

            }
            Column(horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.clickable { navController.navigate(Screen.Profile) }) {
                Icon(
                    painter = painterResource(R.drawable.ic_profile_user),
                    contentDescription = null,
                    Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text("Profile")

            }
        }
    }
}

@Preview
@Composable
fun PreviewBottomBar() {
    val navController = rememberNavController() // Create a simple NavController
    BottomBar(navController = navController)}

