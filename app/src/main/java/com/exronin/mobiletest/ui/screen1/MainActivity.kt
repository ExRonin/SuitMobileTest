package com.exronin.mobiletest.ui.screen1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import androidx.appcompat.app.AlertDialog
import com.exronin.mobiletest.databinding.ActivityMainBinding
import com.exronin.mobiletest.ui.screen2.SecondScreen


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.checkButton.setOnClickListener {
            val sentence = binding.sentenceInput.text.toString()
            if (isPalindrome(sentence)) {
                showMessageDialog("Palindrome")
            } else {
                showMessageDialog("Not Palindrome")
            }
        }
        binding.nextButton.setOnClickListener {
            val intent = Intent(this, SecondScreen::class.java)
            intent.putExtra("NAME", binding.nameInput.text.toString())
            startActivity(intent)
        }
    }
    private fun isPalindrome(sentence: String): Boolean {
        val cleanedSentence = sentence.replace("\\s".toRegex(), "").lowercase()
        return cleanedSentence == cleanedSentence.reversed()
    }
    private fun showMessageDialog(message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(message)
            .setPositiveButton("OK", null)
            .create()
            .show()
    }
}