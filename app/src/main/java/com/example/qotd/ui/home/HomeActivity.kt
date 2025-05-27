package com.example.qotd.ui.home

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.qotd.R
import com.example.qotd.data.repository.QuoteRepository
import com.example.qotd.data.repository.Result
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {

    private val repository = QuoteRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Test the repository
        testRepository()
    }

    private fun testRepository() {
        lifecycleScope.launch {
            Log.d("HomeActivity", "🧪 Testing Repository...")

            // Test 1: Get all quotes
            when (val result = repository.getQuotes()) {
                is Result.Success -> {
                    Log.d("HomeActivity", "✅ SUCCESS: Got ${result.data.size} quotes")
                    Log.d("HomeActivity", "First quote: ${result.data.firstOrNull()?.quote}")
                }
                is Result.Failure -> {
                    Log.e("HomeActivity", "❌ FAILED: ${result.exception.message}")
                }
            }

            // Test 2: Get unique games
            when (val result = repository.getUniqueGames()) {
                is Result.Success -> {
                    Log.d("HomeActivity", "✅ GAMES: ${result.data}")
                }
                is Result.Failure -> {
                    Log.e("HomeActivity", "❌ GAMES FAILED: ${result.exception.message}")
                }
            }

            // Test 3: Get random quote
            when (val result = repository.getRandomQuote()) {
                is Result.Success -> {
                    result.data?.let { quote ->
                        Log.d("HomeActivity", "🎲 RANDOM: ${quote.quote} - ${quote.character}")
                    }
                }
                is Result.Failure -> {
                    Log.e("HomeActivity", "❌ RANDOM FAILED: ${result.exception.message}")
                }
            }
        }
    }
}