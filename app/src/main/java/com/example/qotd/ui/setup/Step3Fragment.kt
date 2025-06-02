package com.example.qotd.ui.setup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment

class Step3Fragment: Fragment(){

    private lateinit var setupViewModel: SetupViewModel



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Get ViewModel from parent activity
        setupViewModel = (requireActivity() as SetupActivity).getSetupViewModel()

        return ComposeView(requireContext()).apply {
            setContent {
                SettingsScreen(viewModel = setupViewModel)
            }
        }
    }




    @Composable
    fun SettingsScreen(viewModel: SetupViewModel) {

        Column(modifier = Modifier.padding(16.dp))
        {
            Text("Settings", style = MaterialTheme.typography.headlineMedium)
            Text("Customize your experience", style = MaterialTheme.typography.bodyLarge)


            Spacer(modifier = Modifier.height(16.dp))


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = viewModel.wallpaperEnabled,
                    onCheckedChange = {
                        viewModel.toggleWallpaperEnabled()
                    }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Enable Lockscreen Wallpaper Modifications",
                    style = MaterialTheme.typography.bodyLarge
                )
            }





        }
    }


    @Preview(showBackground = true)
    @Composable

    fun PreviewSettingsScreen() {
    }



}

