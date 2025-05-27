package com.example.qotd.data.repository

import android.util.Log
import com.example.qotd.data.api.QuotesApi
import com.example.qotd.data.model.Quote
import com.example.qotd.di.NetworkModule
import retrofit2.HttpException
import java.io.IOException

class QuoteRepository {

    private val apiService: QuotesApi = NetworkModule.quotesApiService

    /**
     * Fetches all quotes from the API
     * @return List of quotes or empty list if error occurs
     */
    suspend fun getQuotes(): Result<List<Quote>> {
        return try {
            Log.d("QuoteRepository", "🚀 Starting API call...")
            val quotes = apiService.getQuotes()
            Log.d("QuoteRepository", "✅ API call successful, got ${quotes.size} quotes")
            Log.d("QuoteRepository", "First quote: ${quotes.firstOrNull()}")
            Result.Success(quotes)
        } catch (e: IOException) {
            Log.e("QuoteRepository", "💥 IOException: ${e.message}", e)
            Result.Failure(NetworkException("Network error: Check your internet connection"))
        } catch (e: HttpException) {
            Log.e("QuoteRepository", "💥 HttpException: ${e.code()} ${e.message()}", e)
            Result.Failure(ApiException("Server error: ${e.code()} ${e.message()}"))
        } catch (e: Exception) {
            Log.e("QuoteRepository", "💥 Unknown Exception: ${e.message}", e)
            Result.Failure(UnknownException("Unexpected error: ${e.message}"))
        }
    }

    /**
     * Gets unique games from quotes for user selection
     */
    suspend fun getUniqueGames(): Result<List<String>> {
        return when (val quotesResult = getQuotes()) {
            is Result.Success -> {
                val games = quotesResult.data
                    .map { it.game }
                    .distinct()
                    .sorted()
                Result.Success(games)
            }
            is Result.Failure -> Result.Failure(quotesResult.exception)
        }
    }

    /**
     * Filters quotes by selected games
     * @param gameList List of selected games
     * @return Filtered quotes
     */
    suspend fun getQuotesByGames(gameList: List<String>): Result<List<Quote>> {
        return when (val quotesResult = getQuotes()) {
            is Result.Success -> {
                val filteredQuotes = quotesResult.data.filter { quote ->
                    gameList.contains(quote.game)
                }
                Result.Success(filteredQuotes)
            }
            is Result.Failure -> Result.Failure(quotesResult.exception)
        }
    }

    /**
     * Gets a random quote from all quotes
     */
    suspend fun getRandomQuote(): Result<Quote?> {
        return when (val quotesResult = getQuotes()) {
            is Result.Success -> {
                val randomQuote = quotesResult.data.randomOrNull()
                Result.Success(randomQuote)
            }
            is Result.Failure -> Result.Failure(quotesResult.exception)
        }
    }
}

// Custom Result class for better error handling
sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Failure(val exception: Exception) : Result<Nothing>()

    inline fun <R> map(transform: (T) -> R): Result<R> {
        return when (this) {
            is Success -> Success(transform(data))
            is Failure -> this
        }
    }
}

// Custom Exception classes for better error handling
class NetworkException(message: String) : Exception(message)
class ApiException(message: String) : Exception(message)
class UnknownException(message: String) : Exception(message)