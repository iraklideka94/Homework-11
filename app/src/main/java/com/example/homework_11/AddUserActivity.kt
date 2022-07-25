package com.example.homework_11

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.view.children
import com.example.homework_11.databinding.ActivityAddUserBinding

class AddUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddUserBinding
    private var withImage = false
    private var userPosition: Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        listeners()
    }

    private fun init() {
        userPosition = intent.getIntExtra("position", -1)
        if (userPosition != -1) {
            val note = userList[userPosition]
            binding.apply {
                textView.text = "Edit User"
                edName.setText(note.name)
                edLastName.setText(note.lastName)
                if (note.image != null) {
                    checkBox.isChecked = true
                    withImage = true
                    switchModes(true)
                    urlEditText.setText(note.image)
                }
            }
        }
    }

    private fun listeners() = with(binding) {
        checkBox.setOnCheckedChangeListener { _, isChecked ->
            withImage = isChecked
            switchModes(isChecked)
        }
        addbutton.setOnClickListener {
            if (!EmptyLines(binding.root)) {
                if (userPosition == -1) {
                    userList.add(getUser())
                    id++
                } else {
                    userList[userPosition] = getUser()
                }
            }
        }
    }

    private fun switchModes(boolean: Boolean) {
        if (boolean) {
            binding.urlEditText.visibility = View.VISIBLE
        } else {
            binding.urlEditText.visibility = View.GONE
        }
    }

    private fun EmptyLines(container: ViewGroup): Boolean {
        container.children.forEach {
            if (it is EditText && it.text.isEmpty() && it.visibility != View.GONE)
                return true
        }
        return false
    }

    private fun getUser(): User {
        val title = binding.edName.text.toString()
        val body = binding.edLastName.text.toString()
        val url = if (withImage) binding.urlEditText.text.toString() else null
        return User(id, title, body, url)
    }
}