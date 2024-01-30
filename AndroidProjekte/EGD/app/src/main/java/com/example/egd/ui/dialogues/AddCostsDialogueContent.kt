package com.example.egd.ui.dialogues

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.egd.data.costsEnum.CostsEnum
import com.example.egd.ui.EGDViewModel
import com.example.egd.ui.validation.ErrorText
import com.example.egd.ui.validation.ValidationService

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddCostsDialogueContent(viewModel: EGDViewModel) {

    var costsState = viewModel.costsState.collectAsState().value
    var costs = costsState.costs
    var reason = costsState.reason

    var validationService = ValidationService()

    var validationCosts = validationService.validateCosts(costs)
    var validationReason = validationService.validateReason(reason)

    var triedToSubmit = costsState.triedToSubmit

    var isExpanded by remember {
        mutableStateOf(false)
    }
    Card(
        modifier = Modifier
            .fillMaxHeight(0.55f)
            .fillMaxWidth(0.9f),
        shape = RoundedCornerShape(8.dp),
        backgroundColor = Color.White
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
                   value = costs,
                   onValueChange = {
                                   viewModel.setCosts(it)
                   },
                   placeholder = { Text(text= "30") },
                   keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                   colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.background),
                   modifier = Modifier.width(77.dp),
                   isError = false
               )
               Text(text="€")
           }
            Row{
                if (!validationCosts.valid && triedToSubmit){
                    ErrorText(message = validationCosts.message)
                }
            }
           Row(verticalAlignment = Alignment.CenterVertically){
               Text(text = "Reason:", fontSize = 15.sp, modifier = Modifier.fillMaxWidth(0.23f))
               Box(){
                   TextField(
                       value = reason?.name ?: "",
                       onValueChange = {},
                       readOnly = true,
                       trailingIcon = {
                           ExposedDropdownMenuDefaults.TrailingIcon(
                               expanded = isExpanded,
                               onIconClick = { isExpanded = true })
                       },
                       placeholder = {
                           Text(text = "Select a Reason")
                       },
                       colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.background),
                       interactionSource = remember { MutableInteractionSource() }
                           .also { interactionSource ->
                               LaunchedEffect(interactionSource) {
                                   interactionSource.interactions.collect {
                                       if (it is PressInteraction.Release) {
                                           isExpanded = !isExpanded
                                       }
                                   }
                               }
                           }
                   )

                   DropdownMenu(expanded = isExpanded, onDismissRequest = { isExpanded = false }, modifier = Modifier.background(color = Color.White)) {
                       for (reason in CostsEnum.values()){
                           DropdownMenuItem(
                               onClick = {
                                   isExpanded=false
                                   viewModel.setReason(reason)
                               },
                               enabled = true,
                               //contentPadding = PaddingValues(top=0.dp, bottom = 0.dp),
                               modifier = Modifier.background(color = Color.White)
                           ) {
                               Text(color = MaterialTheme.colors.primary, text = reason.name)
                           }
                       }
                   }
               }
           }
            Row{
                if (!validationReason.valid && triedToSubmit){
                    ErrorText(message = validationReason.message)
                }
            }
           Row(modifier = Modifier
               .fillMaxWidth()
               .padding(5.dp), horizontalArrangement = Arrangement.Center){
                Button(onClick = { viewModel.addCosts()
                                 //viewModel.setShowCosts(false)
                                 }, modifier = Modifier.fillMaxWidth(0.5f)){
                    Text(text="Add")
                }
           }
       }
    }

}
/*@Composable
@Preview
fun AddCostsDialoguePreview(){
    AddCostsDialogue()
}*/