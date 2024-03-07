package com.example.egd.ui.dialogues

import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.egd.ui.EGDViewModel

@Composable
fun AccidentDialogue(viewModel: EGDViewModel){
    Dialog(
        onDismissRequest = { viewModel.setAccidentCode("0")
        },
        content = {
                  AccidenDialogContent(viewModel)
        },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
        )
}