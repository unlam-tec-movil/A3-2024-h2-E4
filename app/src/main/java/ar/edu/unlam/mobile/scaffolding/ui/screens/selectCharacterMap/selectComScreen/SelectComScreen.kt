package ar.edu.unlam.mobile.scaffolding.ui.screens.selectCharacterMap.selectComScreen

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaPlayer
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import ar.edu.unlam.mobile.scaffolding.R
import ar.edu.unlam.mobile.scaffolding.data.local.SuperHeroItem
import ar.edu.unlam.mobile.scaffolding.ui.components.ButtonWithBackgroundImage
import ar.edu.unlam.mobile.scaffolding.ui.components.ExitConfirmation
import ar.edu.unlam.mobile.scaffolding.ui.components.IconPowerDetail
import ar.edu.unlam.mobile.scaffolding.ui.components.SearchHero
import ar.edu.unlam.mobile.scaffolding.ui.components.SetOrientationScreen
import ar.edu.unlam.mobile.scaffolding.ui.components.mediaPlayer
import ar.edu.unlam.mobile.scaffolding.ui.core.local.OrientationScreen.PORTRAIT
import ar.edu.unlam.mobile.scaffolding.ui.core.routes.DetailRoute
import ar.edu.unlam.mobile.scaffolding.ui.core.routes.SelectMapRoute
import ar.edu.unlam.mobile.scaffolding.ui.core.routes.SelectPlayerRoute
import ar.edu.unlam.mobile.scaffolding.ui.screens.selectCharacterMap.selectPlayerScreen.viewmodel.SelectCharacterViewModel
import ar.edu.unlam.mobile.scaffolding.ui.theme.SilverA
import ar.edu.unlam.mobile.scaffolding.ui.theme.VioletSky
import coil.compose.rememberAsyncImagePainter

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SelectCom(
    navController: NavHostController,
    selectCharacterViewModel: SelectCharacterViewModel,
) {
    val context = LocalContext.current
    val isLoading = selectCharacterViewModel.isLoading.collectAsState()
    var showExitConfirmation by rememberSaveable {
        mutableStateOf(false)
    }
    val audioPosition = selectCharacterViewModel.audioPosition.collectAsState()
    val audio = mediaPlayer(context, audioPosition)

    SetOrientationScreen(
        context = context,
        orientation = PORTRAIT.orientation,
    )

    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .background(
                    brush =
                        Brush.verticalGradient(
                            listOf(SilverA, VioletSky),
                            startY = 0f,
                            endY = 600f,
                        ),
                ),
    ) {
        if (isLoading.value) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            Scaffold(
                topBar = {
                    TopBar(
                        navController,
                        selectCharacterViewModel,
                        context,
                    ) { showExitConfirmation = true }
                },
                content = {
                    ContentView(
                        navController = navController,
                        selectCharacterViewModel = selectCharacterViewModel,
                        context = context,
                        audio = audio,
                    )
                },
            )
        }

        ExitConfirmation(
            show = showExitConfirmation,
            onDismiss = { showExitConfirmation = false },
            onConfirm = {
                selectCharacterViewModel.setAudioPosition(audio.currentPosition)
                navController.navigate(SelectPlayerRoute) {
                    popUpTo(SelectPlayerRoute) { inclusive = true }
                }
            },
            title = stringResource(id = R.string.ExitConfirmation),
            message = stringResource(id = R.string.ExitSelectCharacter),
        )

        BackHandler {
            showExitConfirmation = true
        }
    }
}

@SuppressLint("RestrictedApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    navController: NavHostController,
    selectCharacterViewModel: SelectCharacterViewModel,
    context: Context,
    showExitConfirmation: (Boolean) -> Unit,
) {
    val (expanded, setExpanded) = remember { mutableStateOf(false) }

    TopAppBar(
        modifier = Modifier.height(48.dp),
        title = {
            Text(
                text = "Character Com Selection",
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                textAlign = TextAlign.Start,
                color = Color.White,
                fontSize = 20.sp,
            )
        },
        navigationIcon = {
            IconButton(onClick = { showExitConfirmation(true) }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    tint = Color.White,
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(Color.Black),
        actions = {
            IconButton(onClick = {
                selectCharacterViewModel.initListHero()
                Toast.makeText(context, "Update characters", Toast.LENGTH_SHORT).show()
            }) {
                Icon(
                    imageVector = Icons.Filled.Refresh,
                    contentDescription = null,
                    tint = Color.White,
                )
            }
            IconButton(onClick = { setExpanded(true) }) {
                Icon(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = null,
                    tint = Color.White,
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { setExpanded(false) },
            ) {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier =
                        Modifier
                            .clickable { /*Ranked*/ }
                            .fillMaxWidth(),
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.List,
                        contentDescription = null,
                        modifier = Modifier.padding(horizontal = 8.dp),
                    )

                    Text(
                        text = "View Ranked",
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.padding(end = 16.dp),
                    )
                }
            }
        },
    )
}

@Composable
fun ContentView(
    navController: NavHostController,
    selectCharacterViewModel: SelectCharacterViewModel,
    context: Context,
    audio: MediaPlayer,
) {
    val playerList by selectCharacterViewModel.superHeroList.collectAsState()
    var searchHeroPlayer by remember { mutableStateOf("") }
    val comPlayer by selectCharacterViewModel.comSelected.collectAsState()

    if (playerList.isNotEmpty()) {
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(top = 48.dp),
            contentAlignment = Alignment.Center,
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Text(text = "Select your player or search for your favorite...")

                SearchHero(
                    query = searchHeroPlayer,
                    onQueryChange = { searchHeroPlayer = it },
                    onSearch = { selectCharacterViewModel.searchHeroByNameToPlayer(searchHeroPlayer) },
                    searchEnabled = true,
                )

                Box(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(500.dp),
                ) {
                    LazyRowWithImagesHeroPlayer(
                        heroList = playerList,
                        selectCharacterViewModel,
                        comPlayer,
                        navController,
                        audio,
                    )
                }

                ButtonWithBackgroundImage(
                    imageResId = R.drawable.iv_attack,
                    onClick = {
                        if (comPlayer != null) {
                            selectCharacterViewModel.setAudioPosition(audio.currentPosition)
                            navController.navigate(SelectMapRoute)
                            selectCharacterViewModel.initListHero()
                        } else {
                            Toast
                                .makeText(
                                    context,
                                    "Please Select character for continue",
                                    Toast.LENGTH_SHORT,
                                ).show()
                        }
                    },
                    modifier =
                        Modifier
                            .width(500.dp)
                            .height(200.dp)
                            .padding(bottom = 22.dp),
                ) {
                    Text(
                        text = "Continue",
                        fontWeight = FontWeight.Normal,
                        fontFamily = FontFamily(Font(R.font.font_firestar)),
                        fontStyle = FontStyle.Italic,
                        fontSize = 32.sp,
                        color = Color.Black,
                    )
                }
            }
        }
    } else {
        Box(Modifier.fillMaxSize()) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
    }
}

@Composable
fun LazyRowWithImagesHeroPlayer(
    heroList: List<SuperHeroItem>,
    selectCharacterViewModel: SelectCharacterViewModel,
    comPlayer: SuperHeroItem?,
    navController: NavHostController,
    audio: MediaPlayer,
) {
    val selectAudio = MediaPlayer.create(LocalContext.current, R.raw.raw_select)
    val cancelSelect = MediaPlayer.create(LocalContext.current, R.raw.raw_cancelselect)
    LazyRow(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(4.dp),
        userScrollEnabled = true,
    ) {
        items(heroList) { hero ->
            Card(
                modifier =
                    Modifier
                        .padding(vertical = 8.dp, horizontal = 4.dp)
                        .width(400.dp)
                        .height(500.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .clickable {
                            selectCharacterViewModel.setComHero(hero)
                            if (comPlayer == hero) cancelSelect.start() else selectAudio.start()
                        }.border(
                            width = 2.dp,
                            color = if (comPlayer != null && comPlayer == hero) Color.Green else Color.Transparent,
                            shape = RoundedCornerShape(8.dp),
                        ),
                elevation = CardDefaults.cardElevation(defaultElevation = 16.dp),
            ) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Image(
                        painter = rememberAsyncImagePainter(hero.image.url),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize(),
                    )
                    IconButton(
                        onClick = {
                            selectCharacterViewModel.setSuperHeroDetail(hero)
                            navController.navigate(DetailRoute)
                            selectCharacterViewModel.setAudioPosition(audio.currentPosition)
                        },
                        modifier =
                            Modifier.align(
                                Alignment.TopStart,
                            ),
                    ) {
                        IconPowerDetail()
                    }

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
                            text = hero.name,
                            modifier = Modifier.align(Alignment.BottomCenter),
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                        )
                    }
                }
            }
        }
    }
}
