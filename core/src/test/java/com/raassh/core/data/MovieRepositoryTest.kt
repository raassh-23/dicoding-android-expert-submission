package com.raassh.core.data

import com.raassh.core.di.repositoryModule
import com.raassh.core.di.testModules
import com.raassh.core.domain.model.MovieDomain
import com.raassh.core.domain.repository.IMovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject

@OptIn(ExperimentalCoroutinesApi::class)
class MovieRepositoryTest : KoinTest {
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()
    private val movieRepository by inject<IMovieRepository>()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        startKoin {
            modules(listOf(testModules, repositoryModule))
        }
    }

    @Test
    fun `getAllMovies should return a list of movies`() = runTest {
        val expected1 = MovieDomain(id = 1, title = "Movie 1",  isFavorite = false)
        val expected2 = MovieDomain(id = 10, title = "Movie 10",  isFavorite = false)

        val movies = movieRepository.getAllMovie().last()
        assertNotNull(movies)
        assertFalse(movies.data.isNullOrEmpty())
        assertEquals(10, movies.data?.size)
        assertEquals(expected1, movies.data?.get(0))
        assertEquals(expected2, movies.data?.get(9))
    }

    @Test
    fun `setFavoriteMovie should set favorite to true and getFavoriteMovies should return a list of favorite movies`() = runTest {
        val expected1 = MovieDomain(id = 1, title = "Movie 1",  isFavorite = true)

        movieRepository.getAllMovie().last() // load all movies first
        movieRepository.setFavoriteMovie(1, true)

        val movies = movieRepository.getFavoriteMovie().last()
        assertEquals(1, movies.size)
        assertEquals(expected1, movies[0])
    }

    @Test
    fun `setFavoriteMovie should set favorite to false and getFavoriteMovies should return an empty list`() = runTest {
        movieRepository.getAllMovie().last() // load all movies first
        movieRepository.setFavoriteMovie(1, true)
        movieRepository.setFavoriteMovie(1, false)

        val movies = movieRepository.getFavoriteMovie().last()
        assertTrue(movies.isEmpty())
    }

    @Test
    fun `searchMovie should return a list with of movies searched`() = runTest {
        val query = "Searched movie"
        val expected = MovieDomain(id = 11, title = query,  isFavorite = false)

        val movies = movieRepository.searchMovie(query).last()
        assertNotNull(movies)
        assertFalse(movies.data.isNullOrEmpty())
        assertEquals(1, movies.data?.size)
        assertEquals(expected, movies.data?.get(0))
    }

    @After
    fun tearDown() {
        stopKoin()
        Dispatchers.resetMain()
    }
}