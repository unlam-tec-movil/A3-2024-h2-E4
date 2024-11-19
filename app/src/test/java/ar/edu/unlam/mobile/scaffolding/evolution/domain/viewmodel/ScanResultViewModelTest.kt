package ar.edu.unlam.mobile.scaffolding.evolution.domain.viewmodel

import ar.edu.unlam.mobile.scaffolding.evolution.data.database.UserRanked
import ar.edu.unlam.mobile.scaffolding.evolution.domain.usecases.GetUserIDFromUserDataUseCase
import ar.edu.unlam.mobile.scaffolding.evolution.domain.usecases.GetUserRankedByUserIDUseCase
import ar.edu.unlam.mobile.scaffolding.evolution.ui.screens.homeLoginProfile.qrGenerateScreen.viewmodel.ScanResultViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ScanResultViewModelTest {
    @RelaxedMockK
    private lateinit var getUserIDFromUserDataUseCase: GetUserIDFromUserDataUseCase

    @RelaxedMockK
    private lateinit var getUserRankedByUserIDUseCase: GetUserRankedByUserIDUseCase

    private lateinit var viewModel: ScanResultViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = ScanResultViewModel(getUserIDFromUserDataUseCase, getUserRankedByUserIDUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // Restaurar el dispatcher Main original
    }

    @Test
    fun `cuando se encuentra un UserRanked valido  se debe actualizar el estado del usuario`() =
        runTest {
            // Given
            val userDataId = "userData123"
            val userID = "userID123"
            val expectedUserRanked =
                UserRanked(
                    userID = userID,
                    userName = "John",
                    userLocation = null,
                    userVictories = 10,
                    avatarUrl = "avatar.png",
                )

            coEvery { getUserIDFromUserDataUseCase(userDataId) } returns flowOf(userID)
            coEvery { getUserRankedByUserIDUseCase(userID) } returns flowOf(expectedUserRanked)

            // When
            viewModel.setScanResult(userDataId)
            advanceUntilIdle()
            // Then
            val user = viewModel.user.first() // Recolectar el valor emitido
            assertEquals(expectedUserRanked, user)
            assertEquals(expectedUserRanked, viewModel.user.value)
        }

    @Test
    fun `cuando no se encuentra ningun ID de usuario no debe setear UserRanked  `() =
        runTest(testDispatcher) {
            // Given
            val userDataId = "invalidUserDataId"

            coEvery { getUserIDFromUserDataUseCase(userDataId) } returns flowOf(null)

            // When
            viewModel.setScanResult(userDataId)
            advanceUntilIdle()
            // Then
            val user = viewModel.user.first() // Recolectar el valor emitido
            assertNull(user)
        }
}
