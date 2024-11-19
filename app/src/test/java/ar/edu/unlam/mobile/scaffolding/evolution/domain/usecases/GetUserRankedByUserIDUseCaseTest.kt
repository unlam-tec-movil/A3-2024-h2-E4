package ar.edu.unlam.mobile.scaffolding.evolution.domain.usecases
import ar.edu.unlam.mobile.scaffolding.evolution.data.database.UserRanked
import ar.edu.unlam.mobile.scaffolding.evolution.data.repository.SuperHeroRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class GetUserRankedByUserIDUseCaseTest {
    @RelaxedMockK
    private lateinit var repository: SuperHeroRepository

    private lateinit var useCase: GetUserRankedByUserIDUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = GetUserRankedByUserIDUseCase(repository)
    }

    @Test
    fun `si el repositorio devuelve un usuario valido deberia devolver un UserRanked `() =
        runTest {
            // Given
            val userID = "12345"
            val expectedUserRanked =
                UserRanked(
                    userID = userID,
                    userName = "John",
                    userLocation = null,
                    userVictories = 10,
                    avatarUrl = "avatar.png",
                )

            coEvery { repository.getUserRankedByUserID(userID) } returns flowOf(expectedUserRanked)

            // When
            val response = useCase(userID).first()

            // Then
            coVerify(exactly = 1) { repository.getUserRankedByUserID(userID) }
            assertEquals(expectedUserRanked, response)
        }

    @Test
    fun ` si el repositorio no encuentra al usuario deberia devolver nulo  `() =
        runTest {
            // Given
            val userID = "invalidId"

            coEvery { repository.getUserRankedByUserID(userID) } returns flowOf(null)

            // When
            val response = useCase(userID).first()

            // Then
            coVerify(exactly = 1) { repository.getUserRankedByUserID(userID) }
            assertNull(response)
        }
}
