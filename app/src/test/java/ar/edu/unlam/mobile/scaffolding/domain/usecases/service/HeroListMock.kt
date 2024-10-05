package ar.edu.unlam.mobile.scaffolding.domain.usecases.service

import ar.edu.unlam.mobile.scaffolding.data.local.model.SuperHeroAppearance
import ar.edu.unlam.mobile.scaffolding.data.local.model.SuperHeroBiography
import ar.edu.unlam.mobile.scaffolding.data.local.model.SuperHeroImage
import ar.edu.unlam.mobile.scaffolding.data.local.model.SuperHeroItem
import ar.edu.unlam.mobile.scaffolding.data.local.model.SuperHeroPowerStats

object HeroListMock {

    val heroList = listOf(
        SuperHeroItem(
            "1",
            "Name",
            SuperHeroPowerStats("50", "50", "50", "50", "50", "50"),
            SuperHeroBiography("pepito", "pepito", "pepito", "pepito"),
            SuperHeroAppearance("gender", "race"),
            SuperHeroImage("URL"),
            null
        ), SuperHeroItem(
            "2",
            "Name",
            SuperHeroPowerStats("50", "50", "50", "50", "50", "50"),
            SuperHeroBiography("pepito", "pepito", "pepito", "pepito"),
            SuperHeroAppearance("gender", "race"),
            SuperHeroImage("URL"),
            null
        ), SuperHeroItem(
            "3",
            "Name",
            SuperHeroPowerStats("50", "50", "50", "50", "50", "50"),
            SuperHeroBiography("pepito", "pepito", "pepito", "pepito"),
            SuperHeroAppearance("gender", "race"),
            SuperHeroImage("URL"),
            null
        )
    )

    val heroGetMock =  SuperHeroItem(
        "1",
        "Name",
        SuperHeroPowerStats("50", "50", "50", "50", "50", "50"),
        SuperHeroBiography("pepito", "pepito", "pepito", "pepito"),
        SuperHeroAppearance("gender", "race"),
        SuperHeroImage("URL"),
        null
    )

    var heroSetMock : SuperHeroItem? = null
}