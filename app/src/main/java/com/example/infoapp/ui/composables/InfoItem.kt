package com.example.infoapp.ui.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.infoapp.R
import com.example.infoapp.ui.theme.MyApplicationTheme

@Composable
fun InfoItem(
    name: String,
    email: String,
    userName: String,
    phoneNumber: String,
    userWebsite: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier, shape = RoundedCornerShape(0.dp)
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            ProfileImage(userPhoto = R.drawable.ic_account_circle)
            Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Row() {
                    Column() {
                        Text(
                            text = stringResource(id = R.string.text_name),
                            fontWeight = FontWeight.Bold
                        )
                        Text(text = name)
                        Text(
                            text = stringResource(id = R.string.user_name),
                            fontWeight = FontWeight.Bold
                        )
                        Text(text = userName)
                    }
                    Column(Modifier.padding(start = 8.dp)) {
                        Text(
                            text = stringResource(id = R.string.text_email),
                            fontWeight = FontWeight.Bold
                        )
                        Text(text = email)
                        Text(
                            text = stringResource(id = R.string.text_phone_number),
                            fontWeight = FontWeight.Bold
                        )
                        Text(text = phoneNumber)
                    }
                }
                Text(
                    text = stringResource(id = R.string.webpage_format, userWebsite),
                    fontWeight = FontWeight.Bold,
                    modifier = modifier.padding(top = 8.dp)
                )
            }
        }
        Divider(modifier = Modifier.fillMaxWidth(), color = Color.Gray, thickness = 1.dp)
    }
}

@Preview(showBackground = true)
@Composable
fun InfoItemPreview() {
    MyApplicationTheme() {
        InfoItem(
            name = "Harry Potter",
            email = "hp@yahoo.com",
            userName = "hp2001",
            phoneNumber = "3215555",
            userWebsite = "www.hp.com"
        )
    }
}
