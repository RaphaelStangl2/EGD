package com.example.egd.ui.dialogues

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.egd.ui.EGDViewModel

@Composable
fun AddCostsDialogue(viewModel: EGDViewModel) {
    Dialog(
        onDismissRequest = {
            viewModel.setShowCosts(false)
            viewModel.clearCostsData()
                           },
        content = {
            AddCostsDialogueContent(viewModel)
        },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = false
        ),
    )
}
