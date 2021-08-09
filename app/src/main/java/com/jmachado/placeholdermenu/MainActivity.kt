package com.jmachado.placeholdermenu

import android.os.Bundle
import android.view.Menu
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.jmachado.placeholdermenu.databinding.ActivityMainBinding
import com.jmachado.placeholdermenu.ui.albums.AlbumsViewModel
import com.jmachado.placeholdermenu.ui.albums.AlbumsViewModelFactory
import com.jmachado.placeholdermenu.ui.posts.PostsViewModel
import com.jmachado.placeholdermenu.ui.posts.PostsViewModelFactory
import com.jmachado.placeholdermenu.ui.todos.ToDosViewModel
import com.jmachado.placeholdermenu.ui.todos.ToDosViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding


    private val postViewModel: PostsViewModel by viewModels {
        PostsViewModelFactory((application as MainApplication).postRepository)
    }

    private val todosViewModel: ToDosViewModel by viewModels {
        ToDosViewModelFactory((application as MainApplication).todosRepository)
    }

    private val albumViewModel: AlbumsViewModel by viewModels {
        AlbumsViewModelFactory((application as MainApplication).albumRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_posts, R.id.nav_albums, R.id.nav_todos
            ), drawerLayout
        )

        fetchAll()

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        //menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun fetchAll(){

        var isConnected = (application as MainApplication).isConnected()

        if(isConnected) {
            postViewModel.fetchPosts()
            albumViewModel.fetchAlbums()
            todosViewModel.fetchTodos()
        }
    }

}