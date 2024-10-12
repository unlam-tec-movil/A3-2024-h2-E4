package ar.edu.unlam.mobile.scaffolding.ui.screens.selectComScreen

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaPlayer
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import ar.edu.unlam.mobile.scaffolding.R
import ar.edu.unlam.mobile.scaffolding.data.local.OrientationScreen.PORTRAIT
import ar.edu.unlam.mobile.scaffolding.data.local.model.SuperHeroItem
import ar.edu.unlam.mobile.scaffolding.data.local.navigation.CameraScreenRoute
import ar.edu.unlam.mobile.scaffolding.data.local.navigation.DetailRoute
import ar.edu.unlam.mobile.scaffolding.data.local.navigation.HomeScreenRoute
import ar.edu.unlam.mobile.scaffolding.data.local.navigation.QRGenerateScreenRoute
import ar.edu.unlam.mobile.scaffolding.data.local.navigation.SelectPlayerRoute
import ar.edu.unlam.mobile.scaffolding.ui.screens.components.ButtonWithBackgroundImage
import ar.edu.unlam.mobile.scaffolding.ui.screens.components.ExitConfirmation
import ar.edu.unlam.mobile.scaffolding.ui.screens.components.IconPowerDetail
import ar.edu.unlam.mobile.scaffolding.ui.screens.components.SearchHero
import ar.edu.unlam.mobile.scaffolding.ui.screens.components.SetOrientationScreen
import ar.edu.unlam.mobile.scaffolding.ui.screens.components.mediaPlayer
import ar.edu.unlam.mobile.scaffolding.ui.screens.selectComScreen.viewmodel.SelectComScreenViewModel
import ar.edu.unlam.mobile.scaffolding.ui.theme.SilverA
import ar.edu.unlam.mobile.scaffolding.ui.theme.VioletSky
import coil.compose.rememberAsyncImagePainter

@SuppressLint ("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SelectCom(navController: NavHostController,
              selectComScreenViewModel: SelectComScreenViewModel)
    {
        val context = LocalContext.current
        val isLoading = selectComScreenViewModel.isLoading.collectAsState()
        var showExitConfirmation by rememberSaveable {
            mutableStateOf(false)
        }

        SetOrientationScreen(
            context = context,
            orientation = PORTRAIT.orientation
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        listOf(SilverA, VioletSky),
                        startY = 0f,
                        endY = 600f)
                )
        ) {
            if (isLoading.value) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                Scaffold(
                    topBar = { TopBar(navController, selectComScreenViewModel, context) },
                    content = {
                        ContentView(
                            navController = navController,
                            selectComScreenViewModel = selectComScreenViewModel,
                            context = context
                        )
                    }
                )
            }

            ExitConfirmation(
                show = showExitConfirmation,
                onDismiss = { showExitConfirmation = false },
                onConfirm = {
                    navController.navigate(HomeScreenRoute) {
                        popUpTo(HomeScreenRoute) { inclusive = true }
                    }
                },
                title = stringResource(id = R.string.ExitConfirmation),
                message = stringResource(id = R.string.ExitSelectCOM)
            )

            BackHandler {
                navController.navigate(SelectPlayerRoute)
            }
        }
    }

    @SuppressLint("RestrictedApi")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TopBar(
        navController: NavHostController,
        selectComScreenViewModel: SelectComScreenViewModel,
        context: Context
    ) {
        val (expanded, setExpanded) = remember { mutableStateOf(false) }

        TopAppBar(
            modifier = Modifier.height(48.dp),
            title = {
                Text(
                    text = "COM Selection",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    textAlign = TextAlign.Start,
                    color = Color.White
                )
            },
            navigationIcon = {
                IconButton(onClick = { navController.navigate(SelectPlayerRoute) }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(Color.Black),
            actions = {
                IconButton(onClick = {
                    selectComScreenViewModel.initListHero()
                    Toast.makeText(context, "Update COM characters", Toast.LENGTH_SHORT).show()
                }) {
                    Icon(
                        imageVector = Icons.Filled.Refresh,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
                IconButton(onClick = { setExpanded(true) }) {
                    Icon(
                        imageVector = Icons.Filled.MoreVert,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { setExpanded(false) }
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clickable { } // TODO * ADD CLICK LOGIC *
                            .fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.List,
                            contentDescription = null,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )

                        Text(
                            text = "View Ranked",
                            fontSize = 19.sp,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier.padding(end = 16.dp)
                        )
                    }
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                    )
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clickable {
                                navController.navigate(QRGenerateScreenRoute)
                            }
                            .fillMaxWidth()
                    ) {

                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = null,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )

                        Text(
                            text = "Test QR",
                            fontSize = 19.sp,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier.padding(end = 16.dp)
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clickable {
                                navController.navigate(CameraScreenRoute)
                            }
                            .fillMaxWidth()
                    ) {

                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.List,
                            contentDescription = null,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )

                        Text(
                            text = "Test camara",
                            fontSize = 19.sp,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier.padding(end = 16.dp)
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clickable { } // TODO * ADD CLICK LOGIC *
                            .fillMaxWidth()
                    ) {

                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.List,
                            contentDescription = null,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )

                        Text(
                            text = "View Ranked",
                            fontSize = 19.sp,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier.padding(end = 16.dp)
                        )
                    }

                }
            }
        )
    }

    @Composable
    fun ContentView(
        navController: NavHostController,
        selectComScreenViewModel: SelectComScreenViewModel,
        context: Context
    ) {
        val comList by selectComScreenViewModel.superHeroListCom.collectAsState()
        var searchHeroCom by remember { mutableStateOf("") }
        val com by selectComScreenViewModel.comSelected.collectAsState()
        val audioPosition = selectComScreenViewModel.audioPosition.collectAsState()
        val audio = mediaPlayer(context, audioPosition)

        if (comList.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 48.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {

                    Text(text = "Select your most lethal enemy COM opponent")

                    SearchHero(
                        query = searchHeroCom,
                        onQueryChange = { searchHeroCom = it },
                        onSearch = { selectComScreenViewModel.searchHeroByNameToCom(searchHeroCom) },
                        searchEnabled = true
                    )

                    Box(modifier = Modifier.weight(2f)) {
                        LazyVerticalGridWithImagesHeroCOM(
                            heroList = comList,
                            selectComScreenViewModel,
                            com,
                            navController,
                            audio
                        )
                    }

                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(4.dp)
                            .padding(top = 4.dp),
                        color = Color.White
                    )

                    Text(
                        text = "Select your most lethal enemy COM opponent",
                        modifier = Modifier.padding(top = 8.dp)
                    )

                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(1.dp)
                            .padding(top = 2.dp),
                        color = Color.White
                    )
                    Box(modifier = Modifier.weight(1f)) {
                        ButtonWithBackgroundImage(
                            imageResId = R.drawable.iv_attack,
                            onClick = {},
                            modifier = Modifier
                                .align(alignment = Alignment.Center)
                                .fillMaxSize()
                        ) {
                            Text(text = "Select and Continue", fontSize = 24.sp)
                        }

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
    fun LazyVerticalGridWithImagesHeroCOM(
        heroList: List<SuperHeroItem>,
        selectComScreenViewModel: SelectComScreenViewModel,
        com: SuperHeroItem?,
        navController: NavHostController,
        audio: MediaPlayer
    ) {
        val selectAudio = MediaPlayer.create(LocalContext.current, R.raw.raw_select)
        val cancelSelect = MediaPlayer.create(LocalContext.current, R.raw.raw_cancelselect)
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(heroList) { hero ->
                Card(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .fillMaxWidth()
                        .height(125.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .clickable {
                            selectComScreenViewModel.setComHero(hero)
                            if (com == hero) cancelSelect.start() else selectAudio.start()
                        }
                        .border(
                            width = 2.dp,
                            color = if (com != null && com == hero) Color.Green else Color.Transparent,
                            shape = RoundedCornerShape(8.dp)
                        ),

                    elevation = CardDefaults.cardElevation(defaultElevation = 16.dp)
                ) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

                        Image(
                            painter = rememberAsyncImagePainter(hero.image.url),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )

                        IconButton(
                            onClick = {
                                selectComScreenViewModel.setSuperHeroDetail(hero)
                                navController.navigate(DetailRoute)
                                selectComScreenViewModel.setAudioPosition(audio.currentPosition)
                            }, modifier = Modifier.align(
                                Alignment.TopStart
                            )
                        ) {
                            IconPowerDetail()
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(30.dp)
                                .align(Alignment.BottomCenter)
                                .background(
                                    colorResource(id = R.color.superhero_item_name)
                                )
                        ) {
                            Text(
                                text = hero.name,
                                modifier = Modifier.align(Alignment.BottomCenter),
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )

                        }
                    }

                }
            }
        }
    }