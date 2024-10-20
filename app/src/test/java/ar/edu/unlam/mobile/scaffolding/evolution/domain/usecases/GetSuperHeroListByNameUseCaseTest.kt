package ar.edu.unlam.mobile.scaffolding.evolution.domain.usecases

import ar.edu.unlam.mobile.scaffolding.evolution.data.local.SuperHeroItem
import ar.edu.unlam.mobile.scaffolding.evolution.domain.repository.SuperHeroRepositoryInterface
import ar.edu.unlam.mobile.scaffolding.evolution.domain.services.GetSuperHeroListByNameService
import ar.edu.unlam.mobile.scaffolding.evolution.domain.usecases.service.HeroListMock
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetSuperHeroListByNameUseCaseTest {
    @RelaxedMockK
    private lateinit var superHeroRepository: SuperHeroRepositoryInterface
    private lateinit var getSuperHeroListByNameUseCase: GetSuperHeroListByNameService

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getSuperHeroListByNameUseCase = GetSuperHeroListByNameUseCase(superHeroRepository)
    }

    @Test
    fun ` Si la respuesta de la api esta vacia retorna una lista vacia `() =
        runBlocking {
            // Given
            coEvery { superHeroRepository.getSuperHeroListByName("super") } returns emptyList()
            // When
            val response = getSuperHeroListByNameUseCase("super")
            // Then
            coVerify(exactly = 1) { superHeroRepository.getSuperHeroListByName("super") }

            assertEquals(emptyList<SuperHeroItem>(), response)
        }

    @Test
    fun ` Si la respuesta de la api no esta vacia retorna la lista de super heroes`() =
        runBlocking {
            // Given
            val heroList = HeroListMock.heroList
            coEvery { superHeroRepository.getSuperHeroListByName("super") } returns heroList
            // When
            val response = getSuperHeroListByNameUseCase("super")
            // Then
            coVerify(exactly = 1) { superHeroRepository.getSuperHeroListByName("super") }

            assertEquals(heroList, response)
        }
}
