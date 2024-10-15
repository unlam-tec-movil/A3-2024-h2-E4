package ar.edu.unlam.mobile.scaffolding.ui.screens.selectPlayerScreen.viewmodel

import app.cash.turbine.test
import ar.edu.unlam.mobile.scaffolding.domain.services.GetSuperHeroListByNameService
import ar.edu.unlam.mobile.scaffolding.domain.usecases.SetSuperHeroDetailUseCase
import ar.edu.unlam.mobile.scaffolding.domain.usecases.service.HeroListMock
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


class SelectCharacterViewModelTest{

    @RelaxedMockK
    private lateinit var getSuperHeroListByNameService: GetSuperHeroListByNameService
    @RelaxedMockK
    private lateinit var setSuperHeroDetailUseCase: SetSuperHeroDetailUseCase

    private lateinit var selectCharacterViewModel: SelectCharacterViewModel


    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = UnconfinedTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        selectCharacterViewModel = SelectCharacterViewModel(getSuperHeroListByNameService,setSuperHeroDetailUseCase)

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `initListHero should populate hero list and set isLoading to false`() = runTest {
        // Given
        val heroList = HeroListMock.heroList
        coEvery { getSuperHeroListByNameService(any()) } returns heroList

        // When
        advanceTimeBy(5000) // Avanza el tiempo para que el delay termine
        selectCharacterViewModel.isLoading.test {
            assertEquals(true, awaitItem()) // Al principio está cargando
            assertEquals(false, awaitItem()) // Luego de cargar los héroes, debe ser false
        }

        // Then
        selectCharacterViewModel.superHeroList.test {
            val result = awaitItem()
            assertEquals(heroList, result) // Verifica que la lista de héroes se actualizó correctamente
        }
    }

    @Test
    fun `setSuperHeroDetail should call use case`() = runTest {
        // Given
        val hero = HeroListMock.heroGetMock

        // When
        selectCharacterViewModel.setSuperHeroDetail(hero)

        // Then
        coVerify(exactly = 1) { setSuperHeroDetailUseCase(hero) }
    }


}