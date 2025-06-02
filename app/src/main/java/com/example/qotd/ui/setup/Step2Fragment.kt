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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment

class Step2Fragment : Fragment() {
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
                GameSelectionScreen(viewModel = setupViewModel)
            }
        }
    }
}

@Composable
fun GameSelectionScreen(viewModel: SetupViewModel) {

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Choose your favorite games",
            style = MaterialTheme.typography.headlineMedium
        )

        Text("Selected: ${viewModel.selectedGames.size} games")
        Spacer(modifier = Modifier.height(16.dp))

        // Show loading or games list
        if (viewModel.isLoading) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                CircularProgressIndicator(modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Loading games...")
            }
        } else {

            // Use real data from repository
            viewModel.availableGames.forEach { game ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = viewModel.isGameSelected(game.game),
                        onCheckedChange = {
                            viewModel.toggleGameSelection(game.game)
                        }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = game.game,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}
