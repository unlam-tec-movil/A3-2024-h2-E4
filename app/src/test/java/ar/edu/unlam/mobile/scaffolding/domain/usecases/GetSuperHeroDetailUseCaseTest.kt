package ar.edu.unlam.mobile.scaffolding.domain.usecases

import ar.edu.unlam.mobile.scaffolding.domain.repository.SuperHeroRepositoryInterface
import ar.edu.unlam.mobile.scaffolding.domain.usecases.service.HeroListMock
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class GetSuperHeroDetailUseCaseTest {
    @RelaxedMockK
    private lateinit var superHeroRepository: SuperHeroRepositoryInterface
    private lateinit var getSuperHeroDetailUseCase: GetSuperHeroDetailUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getSuperHeroDetailUseCase = GetSuperHeroDetailUseCase(superHeroRepository)
    }

    @Test
    fun `Si hay un heroe seleccionado en el detalle que devuelva el heroe`() {
        // Given
        val hero = HeroListMock.heroGetMock
        every { superHeroRepository.getHeroDetail() } returns hero
        // When
        val response = getSuperHeroDetailUseCase()
        // Then
        verify(exactly = 1) { superHeroRepository.getHeroDetail() }
        assertEquals(hero, response)
        assertNotNull(response)
    }

    @Test
    fun `Si no hay un heroe seleccionado en el detalle que devuelva null`() {
        // Given
        every { superHeroRepository.getHeroDetail() } returns null
        // When
        val response = getSuperHeroDetailUseCase()
        // Then
        verify(exactly = 1) { superHeroRepository.getHeroDetail() }
        assertEquals(null, response)
        assertNull(response)
    }
}
