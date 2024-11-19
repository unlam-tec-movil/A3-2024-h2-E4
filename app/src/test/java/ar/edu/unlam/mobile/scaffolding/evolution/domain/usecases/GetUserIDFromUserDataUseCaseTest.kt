package ar.edu.unlam.mobile.scaffolding.evolution.domain.usecases

import ar.edu.unlam.mobile.scaffolding.evolution.data.database.UserData
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

class GetUserIDFromUserDataUseCaseTest {
    @RelaxedMockK
    private lateinit var repository: SuperHeroRepository

    private lateinit var useCase: GetUserIDFromUserDataUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = GetUserIDFromUserDataUseCase(repository)
    }

    @Test
    fun `si el repositorio devuelve datos de usuario validos deberia devolver ID de usuario`() =
        runTest {
            // Given
            val userDataId = "userData123"
            val expectedUserID = "userID123"

            coEvery { repository.getUserDataById(userDataId) } returns flowOf(UserData(userID = expectedUserID))

            // When
            val response = useCase(userDataId).first()

            // Then
            coVerify(exactly = 1) { repository.getUserDataById(userDataId) }
            assertEquals(expectedUserID, response)
        }

    @Test
    fun `si el repositorio no encuentra datos de usuario deberia devolver nulo`() =
        runTest {
            // Given
            val userDataId = "invalidId"

            coEvery { repository.getUserDataById(userDataId) } returns flowOf(null)

            // When
            val response = useCase(userDataId).first()

            // Then
            coVerify(exactly = 1) { repository.getUserDataById(userDataId) }
            assertNull(response)
        }
}
