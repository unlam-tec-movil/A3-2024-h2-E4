package ar.edu.unlam.mobile.scaffolding.domain.usecases

import ar.edu.unlam.mobile.scaffolding.domain.repository.SuperHeroRepositoryInterface
import ar.edu.unlam.mobile.scaffolding.domain.usecases.service.HeroListMock
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class SetSuperHeroDetailUseCaseTest {

    @RelaxedMockK
    private lateinit var superHeroRepository: SuperHeroRepositoryInterface
    private lateinit var setSuperHeroDetailUseCase: SetSuperHeroDetailUseCase
    private lateinit var getSuperHeroDetailUseCase: GetSuperHeroDetailUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getSuperHeroDetailUseCase = GetSuperHeroDetailUseCase(superHeroRepository)
        setSuperHeroDetailUseCase = SetSuperHeroDetailUseCase(superHeroRepository)
    }

    @Test
    fun `Que se inserte un superheroe y que lo devuelva correctamente`() {
        // Given
        val hero = HeroListMock.heroGetMock
        every { superHeroRepository.setHeroDetail(hero) } answers {
            HeroListMock.heroSetMock = hero
        }
        every { superHeroRepository.getHeroDetail() } returns HeroListMock.heroGetMock
        // When
        setSuperHeroDetailUseCase(hero)
        val getHero = getSuperHeroDetailUseCase()
        // Then
        verify(exactly = 1) { superHeroRepository.setHeroDetail(hero) }
        verify(exactly = 1) { superHeroRepository.getHeroDetail() }
        assertEquals(hero, getHero)
        assertNotNull(getHero)
    }
}
