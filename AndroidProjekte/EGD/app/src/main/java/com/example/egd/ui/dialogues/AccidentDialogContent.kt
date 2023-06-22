package com.example.egd.ui.dialogues

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.egd.ui.EGDViewModel
import com.example.egd.ui.validation.ErrorText

@Composable
fun AccidenDialogContent(viewModel: EGDViewModel){

    Card(
        modifier = Modifier
            .fillMaxHeight(0.5f)
            .fillMaxWidth(0.9f),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){
            Row(horizontalArrangement = Arrangement.Center){
                Text(text = "Unfall", fontSize = 25.sp
                )
            }
            Row(){
                Text("Eines der von Ihnen angemeldeten Fahrzeuge hatte einen Unfall")
            }
            Row(horizontalArrangement = Arrangement.Center){
                Button(onClick = { viewModel.setAccidentCode("0") }) {
                    Text("Ok")
                }
            }
        }
    }
}