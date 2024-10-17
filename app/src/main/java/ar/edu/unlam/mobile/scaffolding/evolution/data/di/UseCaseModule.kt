package ar.edu.unlam.mobile.scaffolding.evolution.data.di

import ar.edu.unlam.mobile.scaffolding.evolution.data.repository.SuperHeroRepository
import ar.edu.unlam.mobile.scaffolding.evolution.domain.repository.SuperHeroRepositoryInterface
import ar.edu.unlam.mobile.scaffolding.evolution.domain.services.GetSuperHeroListByNameService
import ar.edu.unlam.mobile.scaffolding.evolution.domain.services.SetCombatDataService
import ar.edu.unlam.mobile.scaffolding.evolution.domain.usecases.GetSuperHeroListByNameUseCase
import ar.edu.unlam.mobile.scaffolding.evolution.domain.usecases.SetCombatDataUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {
    @Binds
    abstract fun bindSuperHeroRepository(superHeroRepository: SuperHeroRepository): SuperHeroRepositoryInterface

    @Binds
    abstract fun bindGetSuperHeroByName(getSuperHeroListByNameUseCases: GetSuperHeroListByNameUseCase): GetSuperHeroListByNameService

    @Binds
    abstract fun bindSetCombatDataScreen(setCombatDataUseCase: SetCombatDataUseCase): SetCombatDataService
}
