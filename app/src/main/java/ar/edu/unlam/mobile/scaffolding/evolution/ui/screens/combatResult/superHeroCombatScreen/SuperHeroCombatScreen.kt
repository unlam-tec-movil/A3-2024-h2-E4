package ar.edu.unlam.mobile.scaffolding.evolution.ui.screens.combatResult.superHeroCombatScreen

import android.media.MediaPlayer
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import ar.edu.unlam.mobile.scaffolding.R
import ar.edu.unlam.mobile.scaffolding.evolution.domain.model.SuperHeroCombat
import ar.edu.unlam.mobile.scaffolding.evolution.ui.components.AttackEffect
import ar.edu.unlam.mobile.scaffolding.evolution.ui.components.ButtonWithBackgroundImage
import ar.edu.unlam.mobile.scaffolding.evolution.ui.components.ExitConfirmation
import ar.edu.unlam.mobile.scaffolding.evolution.ui.components.SetOrientationScreen
import ar.edu.unlam.mobile.scaffolding.evolution.ui.components.StatsBattle
import ar.edu.unlam.mobile.scaffolding.evolution.ui.components.screenSize
import ar.edu.unlam.mobile.scaffolding.evolution.ui.core.local.OrientationScreen
import ar.edu.unlam.mobile.scaffolding.evolution.ui.core.routes.Routes.*
import ar.edu.unlam.mobile.scaffolding.evolution.ui.screens.combatResult.superHeroCombatScreen.viewmodel.CombatViewModel
import coil.compose.rememberAsyncImagePainter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SuperHeroCombat(
    navController: NavHostController,
    viewModel: CombatViewModel = hiltViewModel(),
) {
    val nickName by viewModel.nickName.collectAsState()
    val superHeroPlayer by viewModel.superHeroPlayer.collectAsState()
    val superHeroCom by viewModel.superHeroCom.collectAsState()
    val backgroundData by viewModel.background.collectAsState()
    val enableButton by viewModel.buttonEnable.collectAsState()
    val attackEffect by viewModel.attackEffect.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val roundCount by viewModel.roundCount.collectAsState()
    val lifePlayer = viewModel.lifePlayer
    val lifeCom = viewModel.lifeCom

    val navigationOK by viewModel.navigationDone.collectAsState()
    var showExitConfirmation by rememberSaveable { mutableStateOf(false) }
    val attackPlayer by viewModel.attackPlayer.collectAsState()
    val context = LocalContext.current
    val screenSizeSmall = screenSize(context)
    val vibratorActivated by viewModel.vibratorActivated.collectAsState()

    val iconButtonPotion by viewModel.iconButtonPotion.collectAsState()
    val iconButtonPowerUp by viewModel.iconButtonPowerUp.collectAsState()
    val iconButtonDefensive by viewModel.iconButtonDefensive.collectAsState()

    val iconButtonPotionCom by viewModel.iconButtonPotionCom.collectAsState()
    val iconButtonPowerUpCom by viewModel.iconButtonPowerUpCom.collectAsState()
    val iconButtonDefensiveCom by viewModel.iconButtonDefensiveCom.collectAsState()

    val playerDefenseActivated by viewModel.playerDefenseActivated.collectAsState()
    val comDefenseActivated by viewModel.comDefenseActivated.collectAsState()
    val playerAttackActivated by viewModel.playerAttackActivated.collectAsState()
    val comAttackActivated by viewModel.comAttackActivated.collectAsState()
    val playerHealingActivated by viewModel.playerHealingActivated.collectAsState()
    val comHealingActivated by viewModel.comHealingActivated.collectAsState()
    val vibrator = remember { ContextCompat.getSystemService(context, Vibrator::class.java) }
    val vibratorOff =
        remember {
            mutableStateOf(false)
        }

    if (vibratorActivated && !vibratorOff.value) {
        vibrator?.vibrate(VibrationEffect.createOneShot(2000, VibrationEffect.DEFAULT_AMPLITUDE))
        vibratorOff.value = true
    }

    SetOrientationScreen(
        context = LocalContext.current,
        orientation = OrientationScreen.LANDSCAPE.orientation,
        hideStatusBar = true,
    )

    if (isLoading) {
        if (screenSizeSmall) {
            Box(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .background(Color.Black),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator(color = Color.Cyan)
            }
        } else {
            Box(Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = R.drawable.iv_vs),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                )
                LinearProgressIndicator(
                    Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 8.dp)
                        .fillMaxWidth(),
                )
                Text(
                    text = "Loading ...",
                    modifier =
                        Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 16.dp),
                )
            }
        }
    } else {
        val audio =
            remember {
                MediaPlayer
                    .create(context, backgroundData!!.theme)
                    .apply { setVolume(0.5f, 0.5f) }
            }

        DisposableEffect(Unit) {
            audio.start()
            onDispose {
                audio.stop()
                audio.release()
            }
        }

        if ((superHeroPlayer!!.life <= 0 || superHeroCom!!.life <= 0) && !navigationOK) {
            viewModel.setDataScreenResult(
                superHeroPlayer = superHeroPlayer!!,
                superHeroCombat = superHeroCom!!,
            )
            viewModel.markNavigationDone()
            navController.navigate(CombatResultRoute)
        }

        Box(
            modifier =
                Modifier
                    .fillMaxSize(),
        ) {
            Image(
                // BACKGROUND
                painter = painterResource(id = backgroundData!!.background),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
            )

            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Box(
                    // BOX PLAYER
                    modifier =
                        Modifier
                            .fillMaxHeight()
                            .width(250.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Column(
                        modifier = Modifier.padding(top = 60.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround,
                        ) {
                            IconButton(
                                // HEAL PLAYER
                                onClick = {
                                    viewModel.healingPotion(lifePlayer.toInt())
                                },
                                enabled = (iconButtonPotion && enableButton),
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.icon_pocion),
                                    contentDescription = "healing potion",
                                    tint =
                                        if (iconButtonPotion) {
                                            Color.Green
                                        } else {
                                            Color.DarkGray
                                        },
                                    modifier = Modifier.size(70.dp),
                                )
                            }

                            IconButton(
                                // ATTACK PLAYER
                                onClick = {
                                    viewModel.specialAttack()
                                },
                                enabled = iconButtonPowerUp && enableButton,
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.icon_special_energy),
                                    contentDescription = null,
                                    tint =
                                        if (iconButtonPowerUp) {
                                            Color.Red
                                        } else {
                                            Color.DarkGray
                                        },
                                    modifier = Modifier.size(40.dp),
                                )
                            }

                            IconButton(
                                // DEFENSE PLAYER
                                onClick = {
                                    viewModel.specialDefense()
                                },
                                enabled = iconButtonDefensive && enableButton,
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.icon_shield_healing),
                                    contentDescription = null,
                                    tint =
                                        if (iconButtonDefensive) {
                                            Color.Yellow
                                        } else {
                                            Color.DarkGray
                                        },
                                    modifier = Modifier.size(37.dp),
                                )
                            }
                        }

                        Card(
                            // CARD PLAYER
                            modifier =
                                Modifier
                                    .width(240.dp)
                                    .height(240.dp)
                                    .padding(top = 2.dp),
                            shape = CardDefaults.elevatedShape,
                            elevation = CardDefaults.cardElevation(40.dp),
                            border =
                                if (playerAttackActivated) {
                                    val color = getColorEffect(R.color.attackSpecialEnabled)
                                    BorderStroke(2.5.dp, color = color.value)
                                } else if (playerHealingActivated) {
                                    BorderStroke(3.dp, colorResource(id = R.color.healingEnabled))
                                } else {
                                    BorderStroke(
                                        0.5.dp,
                                        colorResource(id = R.color.attackSpecialDisabled),
                                    )
                                },
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center,
                            ) {
                                Image(
                                    painter = rememberAsyncImagePainter(superHeroPlayer!!.image.url),
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize(),
                                )
                                Box(
                                    modifier =
                                        Modifier
                                            .fillMaxWidth()
                                            .height(30.dp)
                                            .align(Alignment.BottomCenter)
                                            .background(
                                                colorResource(id = R.color.superhero_item_name),
                                            ),
                                ) {
                                    Text(
                                        text = superHeroPlayer!!.name,
                                        fontSize = 24.sp,
                                        modifier = Modifier.align(Alignment.BottomCenter),
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White,
                                    )
                                }
                            }
                        }

                        StatsBattle(superHero = superHeroPlayer)
                    }
                }

                Box(
                    // BOX COM
                    modifier =
                        Modifier
                            .fillMaxHeight()
                            .width(250.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Column(
                        modifier = Modifier.padding(top = 60.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround,
                        ) {
                            IconButton(
                                // HEAL COM
                                onClick = {
                                },
                                enabled = (iconButtonPotionCom && enableButton),
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.icon_pocion),
                                    contentDescription = "healing potion",
                                    tint =
                                        if (iconButtonPotionCom) {
                                            Color.Green
                                        } else {
                                            Color.DarkGray
                                        },
                                    modifier = Modifier.size(70.dp),
                                )
                            }

                            IconButton(
                                // ATTACK COM
                                onClick = {
                                },
                                enabled = iconButtonPowerUpCom && enableButton,
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.icon_special_energy),
                                    contentDescription = null,
                                    tint =
                                        if (iconButtonPowerUpCom) {
                                            Color.Red
                                        } else {
                                            Color.DarkGray
                                        },
                                    modifier = Modifier.size(40.dp),
                                )
                            }

                            IconButton(
                                // DEFENSE COM
                                onClick = {
                                },
                                enabled = iconButtonDefensiveCom && enableButton,
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.icon_shield_healing),
                                    contentDescription = null,
                                    tint =
                                        if (iconButtonDefensiveCom) {
                                            Color.Yellow
                                        } else {
                                            Color.DarkGray
                                        },
                                    modifier = Modifier.size(37.dp),
                                )
                            }
                        }

                        Card(
                            // CARD COM
                            modifier =
                                Modifier
                                    .width(240.dp)
                                    .height(240.dp)
                                    .padding(top = 2.dp),
                            shape = CardDefaults.elevatedShape,
                            elevation = CardDefaults.cardElevation(40.dp),
                            border =
                                if (comAttackActivated) {
                                    val color = getColorEffect(R.color.attackSpecialEnabled)
                                    BorderStroke(2.5.dp, color = color.value)
                                } else if (comHealingActivated) {
                                    BorderStroke(3.dp, colorResource(id = R.color.healingEnabled))
                                } else {
                                    BorderStroke(
                                        0.5.dp,
                                        colorResource(id = R.color.attackSpecialDisabled),
                                    )
                                },
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center,
                            ) {
                                Image(
                                    painter = rememberAsyncImagePainter(superHeroCom!!.image.url),
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize(),
                                )
                                Box(
                                    modifier =
                                        Modifier
                                            .fillMaxWidth()
                                            .height(30.dp)
                                            .align(Alignment.BottomCenter)
                                            .background(
                                                colorResource(id = R.color.superhero_item_name),
                                            ),
                                ) {
                                    Text(
                                        text = superHeroCom!!.name,
                                        fontSize = 24.sp,
                                        modifier = Modifier.align(Alignment.BottomCenter),
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White,
                                    )
                                }
                            }
                        }

                        StatsBattle(superHero = superHeroCom)
                    }
                }
            }

            Text(
                text = "VS",
                fontSize = 54.sp,
                modifier = Modifier.align(Alignment.Center),
                fontFamily = FontFamily(Font(R.font.font_bodoni_italic)),
                color = Color.White,
            )

            Box(
                // BUTTON ATTACK
                modifier =
                    Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 12.dp),
            ) {
                ButtonWithBackgroundImage(
                    imageResId = R.drawable.iv_attack,
                    onClick = {
                        viewModel.getRandomAudioAttack()
                        viewModel.initAttack()
                    },
                    enabledButton = enableButton,
                    modifier =
                        Modifier
                            .height(150.dp)
                            .width(350.dp),
                ) {
                    Text(
                        text = "Attack",
                        fontSize = 36.sp,
                        modifier = Modifier.padding(horizontal = 36.dp),
                        fontWeight = FontWeight.ExtraBold,
                    )
                }
            }
        }

        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.Top,
        ) {
            // LifePlayer
            Box(
                modifier =
                    Modifier
                        .width(300.dp)
                        .height(30.dp)
                        .border(1.dp, Color.Black)
                        .background(Color.White),
            ) {
                Box(
                    modifier =
                        Modifier
                            .height(30.dp)
                            .width(superHeroPlayer!!.life.dp)
                            .background(
                                if (playerDefenseActivated) {
                                    colorResource(id = R.color.defenseSpecialActivated)
                                } else {
                                    setColorLifePlayer(superHeroPlayer!!)
                                },
                            ),
                )
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = nickName,
                        textAlign = TextAlign.Start,
                        fontSize = 24.sp,
                        color = Color.Gray,
                    )
                    Text(
                        text = "${superHeroPlayer!!.life}/$lifePlayer",
                        textAlign = TextAlign.End,
                        fontSize = 24.sp,
                        color = Color.Gray,
                    )
                }
            }

            Box(
                // round
                modifier =
                    Modifier
                        .height(70.dp)
                        .width(200.dp)
                        .background(
                            if (backgroundData!!.background == R.drawable.iv_dragonballfight) {
                                Color.DarkGray
                            } else {
                                Color.Unspecified
                            },
                        ),
                contentAlignment = Alignment.TopCenter,
            ) {
                Card(
                    shape = CardDefaults.elevatedShape,
                    elevation = CardDefaults.cardElevation(40.dp),
                    modifier = Modifier.align(Alignment.TopCenter),
                ) {
                    Text(
                        text = " Round $roundCount ",
                        modifier = Modifier.padding(4.dp),
                        fontSize = 24.sp,
                        fontFamily = FontFamily(Font(R.font.font_bodoni_italic)),
                        color = Color.White,
                    )
                }

                Icon(
                    painter = painterResource(id = setIconPlayer(attackPlayer)),
                    contentDescription = null,
                    tint = Color.White,
                    modifier =
                        Modifier
                            .align(Alignment.TopStart)
                            .size(35.dp),
                )

                Icon(
                    painter = painterResource(id = setIconCom(attackPlayer)),
                    contentDescription = null,
                    tint = Color.White,
                    modifier =
                        Modifier
                            .align(Alignment.TopEnd)
                            .size(35.dp),
                )
                Text(
                    text = stringResource(id = setMessageAttack(attackPlayer)),
                    modifier =
                        Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 10.dp),
                    color = Color.White,
                    fontWeight = FontWeight.ExtraBold,
                )
            }

            Box(
                // lifeCom
                modifier =
                    Modifier
                        .width(300.dp)
                        .height(30.dp)
                        .border(1.dp, Color.Black)
                        .background(Color.White),
            ) {
                Box(
                    modifier =
                        Modifier
                            .height(30.dp)
                            .width(superHeroCom!!.life.dp)
                            .background(
                                if (comDefenseActivated) {
                                    colorResource(id = R.color.defenseSpecialActivated)
                                } else {
                                    setColorLifePlayer(superHeroCom!!)
                                },
                            ),
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = "Com",
                        textAlign = TextAlign.Start,
                        fontSize = 24.sp,
                        modifier = Modifier.padding(start = 8.dp),
                        color = Color.Gray,
                    )
                    Text(
                        text = "${superHeroCom!!.life}/$lifeCom",
                        textAlign = TextAlign.End,
                        fontSize = 24.sp,
                        modifier = Modifier.padding(end = 8.dp, start = 130.dp),
                        color = Color.Gray,
                    )
                }
            }
        }

        AttackEffect(attackEffect, enableButton, context, viewModel)
    }

    BackHandler {
        showExitConfirmation = true
    }

    ExitConfirmation(
        show = showExitConfirmation,
        onDismiss = { showExitConfirmation = false },
        onConfirm = {
            navController.navigate(HomeScreenRoute) {
                popUpTo<HomeScreenRoute> { inclusive = true }
            }
        },
        title = stringResource(id = R.string.ExitConfirmation),
        message = stringResource(id = R.string.ExitCombat),
    )
}

@Composable
private fun getColorEffect(idColor: Int): State<Color> {
    val transition = rememberInfiniteTransition(label = "Infinity")
    return transition.animateColor(
        initialValue = colorResource(id = idColor),
        targetValue =
            colorResource(
                id = R.color.combatColorEffect2,
            ),
        animationSpec = infiniteRepeatable(animation = tween(500), repeatMode = RepeatMode.Restart),
        label = "",
    )
}

fun setMessageAttack(attackPlayer: Boolean): Int =
    if (attackPlayer) {
        R.string.AttackMessage
    } else {
        R.string.DefenseMessage
    }

fun setIconCom(attackPlayer: Boolean): Int =
    if (!attackPlayer) {
        R.drawable.icon_attacksword
    } else {
        R.drawable.icon_defense
    }

fun setIconPlayer(attackPlayer: Boolean): Int =
    if (attackPlayer) {
        R.drawable.icon_attacksword
    } else {
        R.drawable.icon_defense
    }

fun setColorLifePlayer(heroItem: SuperHeroCombat): Color {
    val durability = heroItem.life
    val lifeColor =
        when (durability) {
            in 0..100 -> Color.Red
            in 101..200 -> Color.Yellow
            else -> Color.Green
        }
    return lifeColor
}
