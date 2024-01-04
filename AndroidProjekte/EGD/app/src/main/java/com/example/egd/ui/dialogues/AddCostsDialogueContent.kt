package com.example.egd.ui.dialogues

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel

@Composable
fun AddCostsDialogueContent() {
    Card(
        modifier = Modifier
            .fillMaxHeight(0.5f)
            .fillMaxWidth(0.9f),
        shape = RoundedCornerShape(8.dp)
    ) {
       Column(modifier = Modifier.fillMaxWidth()) {
           Row() {
               Text(text="Add Costs")
           }
           Row(){
               Text(text = "Costs:")
               TextField(
                   value = "",
                   onValueChange = {},
                   placeholder = { Text(text= "30") },
                   keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                   colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.background),
                   modifier = Modifier.width(77.dp),
                   isError = false
               )
               Text(text="€")
           }
           Row(){
                Text(text = "Reason:")
               DropdownMenu(expanded = true, onDismissRequest = { }, modifier = Modifier.background(color = Color.White)) {
                   DropdownMenuItem(
                       onClick = {
                       },
                       enabled = true,
                       modifier = Modifier.background(color = Color.White)
                   ) {
                       Text(color = MaterialTheme.colors.primary, text = "Reason 1")
                   }
               }
           }
           Row(horizontalArrangement = Arrangement.Center){
                Button(onClick = {}){
                    Text(text="Add")
                }
           }
       }
    }

}
@Composable
@Preview
fun AddCostsDialoguePreview(){
    AddCostsDialogue()
}