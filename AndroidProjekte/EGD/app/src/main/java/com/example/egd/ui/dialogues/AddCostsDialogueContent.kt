package com.example.egd.ui.dialogues

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddCostsDialogueContent() {
    Card(
        modifier = Modifier
            .fillMaxHeight(0.55f)
            .fillMaxWidth(0.9f),
        shape = RoundedCornerShape(8.dp)
    ) {
       Column(modifier = Modifier
           .fillMaxWidth()
           .padding(10.dp)) {
           Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
               Text(text="Add Costs", fontSize = 20.sp)
           }
           Row(verticalAlignment = Alignment.CenterVertically){
               Text(text = "Costs:  ", fontSize = 15.sp, modifier = Modifier.fillMaxWidth(0.23f))
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
           Row(verticalAlignment = Alignment.CenterVertically){
               Text(text = "Reason:", fontSize = 15.sp, modifier = Modifier.fillMaxWidth(0.23f))
               Box(){
                   TextField(
                       value = "",
                       onValueChange = {},
                       readOnly = true,
                       trailingIcon = {
                           ExposedDropdownMenuDefaults.TrailingIcon(expanded = false)
                       },
                       placeholder = {
                           Text(text = "Select a Reason")
                       },
                       colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.background),
                       //modifier = Modifier.menuAnchor()
                   )
                   DropdownMenu(expanded = true, onDismissRequest = { }, modifier = Modifier.background(color = Color.White)) {
                       DropdownMenuItem(
                           onClick = {
                           },
                           enabled = true,
                           modifier = Modifier.background(color = Color.White)
                       ) {
                           Text(color = MaterialTheme.colors.primary, text = "Reason 1")
                       }
                       DropdownMenuItem(
                           onClick = {
                           },
                           enabled = true,
                           modifier = Modifier.background(color = Color.White)
                       ) {
                           Text(color = MaterialTheme.colors.primary, text = "Reason 2")
                       }
                   }
               }
           }
           Row(modifier = Modifier
               .fillMaxWidth()
               .padding(5.dp), horizontalArrangement = Arrangement.Center){
                Button(onClick = {}, modifier = Modifier.fillMaxWidth(0.5f)){
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