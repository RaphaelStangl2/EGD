package com.example.egd.ui.getStarted

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.egd.R
import com.example.egd.data.entities.User
import com.example.egd.ui.EGDViewModel

@Composable
fun AddUserScreen(
    viewModel: EGDViewModel,
    friendSearchBarContent: String,
    assignedFriendsList: Array<User>?,
    searchedFriendsList: Array<User>?
){
    Text(text="Assign Friends to your Car, so they have access to it's location and route tracking features")
    Spacer(modifier = Modifier.height(10.dp))

    Row(){
        OutlinedTextField(
            value = friendSearchBarContent,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {}),
            onValueChange = {viewModel.setFriendSearchBarContent(it)},
            leadingIcon = { Icon(painter = painterResource(id = R.drawable.ic_baseline_search_24), contentDescription = "Search Bar") },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = MaterialTheme.colors.background,
                focusedBorderColor = MaterialTheme.colors.background,
                unfocusedBorderColor = MaterialTheme.colors.background

            ),
            modifier = Modifier
                .fillMaxWidth()
                .shadow(6.dp, shape = RoundedCornerShape(20.dp))
        )
    }
    Row(){
        if (assignedFriendsList != null) {
            for (user in assignedFriendsList)
            {
                UserLabel(userName = user.userName)
            }

        }
        Divider(thickness = 1.dp)
    }
    Row(){
        if (searchedFriendsList != null) {
            for (user in searchedFriendsList)
            {
                UserLabel(userName = user.userName)
            }

        }
    }

}


@Composable
fun UserLabel (userName:String){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        backgroundColor = MaterialTheme.colors.background,
        elevation = 8.dp,
        shape = MaterialTheme.shapes.large
    ) {

        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .padding(start = 17.dp, top = 10.dp, bottom = 10.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text=userName)

                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.background, contentColor = MaterialTheme.colors.primaryVariant),
                    modifier = Modifier.shadow(0.dp),
                    elevation = ButtonDefaults.elevation(
                        defaultElevation = 0.dp,
                        pressedElevation = 0.dp
                    ),
                    shape= MaterialTheme.shapes.large

                ) {
                    Text(text="Assign",color = MaterialTheme.colors.primaryVariant, fontWeight = MaterialTheme.typography.body2.fontWeight)
                }
            }

        }

    }
}