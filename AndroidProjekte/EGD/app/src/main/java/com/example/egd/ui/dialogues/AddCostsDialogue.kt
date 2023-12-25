package com.example.egd.ui.dialogues

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.ViewModel

@Composable
fun AddCostsDialogue() {
    Dialog(
        onDismissRequest = { /*TODO*/ },
        content = {
            AddCostsDialogueContent()
        },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = false
        )
    )
}
