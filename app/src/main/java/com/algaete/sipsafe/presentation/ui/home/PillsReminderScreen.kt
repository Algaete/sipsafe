package com.algaete.sipsafe.presentation.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PillsReminderScreen(homeViewModel: HomeViewModel) {
    val medications = listOf(
        Medication("8:00 AM", "Vitamin D", "1 tablet"),
        Medication("2:00 PM", "Aspirin", "2 tablets"),
        Medication("8:00 PM", "Antibiotic", "1 capsule")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Medications") },
                navigationIcon = {
                    IconButton(onClick = { /* Handle back navigation */ }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /* Handle adding new medication */ }) {
                Icon(Icons.Filled.Add, contentDescription = "Add Medication")
            }
        },
        content = { paddingValues ->
            MedicationList(medications, Modifier.padding(paddingValues))
        }
    )
}

@Composable
fun BottomNavigation(content: () -> Unit) {

}

@Composable
fun MedicationList(medications: List<Medication>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier.padding(16.dp)) {
        item {
            Text(
                text = "Today's Medications",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        items(medications) { medication ->
            MedicationItem(medication)
            Divider(modifier = Modifier.padding(vertical = 8.dp))
        }
    }
}

@Composable
fun MedicationItem(medication: Medication) {
    var isChecked by remember { mutableStateOf(false) }

    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = isChecked,
                onCheckedChange = { isChecked = it }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "${medication.time} - ${medication.name} (${medication.dosage})",
                fontSize = 16.sp
            )
        }
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()
        ) {
            TextButton(onClick = { /* Handle snooze */ }) {
                Text("Snooze")
            }
            TextButton(onClick = { /* Handle skip */ }) {
                Text("Skip")
            }
        }
    }
}

data class Medication(val time: String, val name: String, val dosage: String)