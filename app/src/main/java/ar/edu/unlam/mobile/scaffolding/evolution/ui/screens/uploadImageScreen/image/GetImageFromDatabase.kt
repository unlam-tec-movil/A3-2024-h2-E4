package ar.edu.unlam.mobile.scaffolding.evolution.ui.screens.uploadImageScreen.image

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import ar.edu.unlam.mobile.scaffolding.evolution.data.network.model.UserDataResponse.Failure
import ar.edu.unlam.mobile.scaffolding.evolution.data.network.model.UserDataResponse.Loading
import ar.edu.unlam.mobile.scaffolding.evolution.data.network.model.UserDataResponse.Success
import ar.edu.unlam.mobile.scaffolding.evolution.ui.screens.homeLoginProfile.userProfileScreen.viewmodel.UploadImageScreenViewModel

@Composable
fun GetImageFromDatabase(
    viewModel: UploadImageScreenViewModel = hiltViewModel(),
    createImageContent: @Composable (imageUrl: String) -> Unit,
) {
    when (val getImageFromDatabaseResponse = viewModel.getImageFromDatabaseResponse) {
        is Loading -> ProgressBar()
        is Success ->
            getImageFromDatabaseResponse.data?.let { imageUrl ->
                createImageContent(imageUrl)
            }

        is Failure -> print(getImageFromDatabaseResponse.e)
    }
}
