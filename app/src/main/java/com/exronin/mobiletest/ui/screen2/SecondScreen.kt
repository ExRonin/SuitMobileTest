package com.exronin.mobiletest.ui.screen2

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.exronin.mobiletest.databinding.ActivitySecondBinding
import com.exronin.mobiletest.ui.screen3.ThirdScreen

class SecondScreen : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding
    private val REQUEST_CODE_SELECT_USER = 1

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra("NAME")

        binding.nameLabel.text = name
        binding.selectedUserNameLabel.text = "Selected User Name"

        binding.chooseUserButton.setOnClickListener {
            val intent = Intent(this, ThirdScreen::class.java)
            @Suppress("DEPRECATION")
            startActivityForResult(intent, REQUEST_CODE_SELECT_USER)
        }

        binding.backButton.setOnClickListener {
            onBackPressed()
        }
    }



    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SELECT_USER && resultCode == RESULT_OK) {
            val selectedUser = data?.getStringExtra("SELECTED_USER")
            binding.selectedUserNameLabel.text = selectedUser
        }
    }
}