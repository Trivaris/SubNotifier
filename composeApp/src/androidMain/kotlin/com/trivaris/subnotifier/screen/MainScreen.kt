package com.trivaris.subnotifier.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import com.trivaris.subnotifier.icons.Reload
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.trivaris.subnotifier.Composables.Dropdown
import com.trivaris.subnotifier.Composables.SubstitutionItem
import com.trivaris.subnotifier.Config
import com.trivaris.subnotifier.Substitution
import com.trivaris.subnotifier.Web
import com.trivaris.subnotifier.getCurrentTeachers
import com.trivaris.subnotifier.icons.Send
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainScreen : Screen {

    @OptIn(DelicateCoroutinesApi::class)
    @Composable
    override fun Content() {

        var substitutions by remember { mutableStateOf<List<Substitution>>(emptyList()) }
        var isLoading by remember { mutableStateOf(false) }

        var inputVisible by remember { mutableStateOf(true) }
        val density = LocalDensity.current

        MaterialTheme {
            Column(
                Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                AnimatedVisibility(
                    visible = inputVisible,
                    enter = slideInVertically {
                        with(density) { -40.dp.roundToPx() }
                    } + expandVertically(
                        expandFrom = Alignment.Top
                    ) + fadeIn(
                        initialAlpha = 0.3f
                    ),
                    exit = slideOutVertically() + shrinkVertically() + fadeOut()
                ) {
                    Input()
                }

                if (substitutions.isNotEmpty()) Dropdown(getCurrentTeachers(substitutions), "Teachers")

                Spacer(modifier = Modifier.height(if (inputVisible) 10.dp else 32.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {

                    ExtendedFloatingActionButton(
                        onClick = {
                            isLoading = true

                            GlobalScope.launch(Dispatchers.Main) {
                                println("[OUT] Started...")
                                substitutions = Web.getSubstitutions()
                                inputVisible = false
                                isLoading = false
                            }

                        },
                        text = { Text("Load Substitutions") },
                        icon = { Image(imageVector = Reload, contentDescription = null) },
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    ExtendedFloatingActionButton(
                        onClick = {

                        },
                        content = {
                            Image(imageVector = Send, contentDescription = null)
                            Text("[WIP] Send Notification")
                        }

                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                if (isLoading)
                    CircularProgressIndicator(modifier = Modifier.padding(16.dp))

                if (substitutions.isNotEmpty())
                    LazyColumn {
                        items(substitutions, key = { it.hashCode() }) { substitution ->
                            if (substitution.getFormattedTeacher() != Config.selectedTeacher) {
                                println("[OUT] Current TEacher: " + substitution.getFormattedTeacher())
                                println("[OUT] Selected TEacher: " + Config.selectedTeacher)
                            }
                            SubstitutionItem(substitution)
                        }
                    }
                if (!isLoading && substitutions.isNotEmpty())
                    Spacer(modifier = Modifier.height(16.dp))
                Text("No substitutions found.")
            }
        }
    }

    @Composable
    fun Input() {
        Column(
            Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = Web.username,
                onValueChange = { Web.username = it },
                label = { Text("Username") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            )
            OutlinedTextField(
                value = Web.password,
                onValueChange = { Web.password = it },
                label = { Text("Password") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password)
            )
        }
    }
}