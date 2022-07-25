package com.example.homework_11

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.homework_11.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val userAdapter by lazy { UserAdapter() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        onClickListeners()
    }

    private fun init() {
        binding.recyclerView.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }
    private fun onClickListeners() {
        binding.floatingActionButton.setOnClickListener {
            startActivity(Intent(this@MainActivity, AddUserActivity::class.java))
        }
        userAdapter.apply {
            onDeleteClickListener = {
                userList.remove(it).also { this.submitList(userList.toList()) }
            }
            onEditClickListener = {
                Intent(this@MainActivity, AddUserActivity::class.java).apply {
                    this.putExtra("position", userList.indexOf(it))
                    startActivity(this)
                }
            }
        }
    }
    override fun onResume() {
        super.onResume()
        userAdapter.submitList(userList.toList())
    }
}