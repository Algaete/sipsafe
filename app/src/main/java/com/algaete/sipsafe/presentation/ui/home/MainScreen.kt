package com.algaete.sipsafe.presentation.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MainScreen(homeViewModel: HomeViewModel) {
    var selectedTabIndex by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Tabs (Header)
        TabRow(selectedTabIndex = selectedTabIndex) {
            Tab(selected = selectedTabIndex == 0, onClick = { selectedTabIndex = 0 }) {
                Text("Hydration", modifier = Modifier.padding(16.dp))
            }
            Tab(selected = selectedTabIndex == 1, onClick = { selectedTabIndex = 1 }) {
                Text("Medications", modifier = Modifier.padding(16.dp))
            }
            Tab(selected = selectedTabIndex == 2, onClick = { selectedTabIndex = 2 }) {
                Text("Profile", modifier = Modifier.padding(16.dp))
            }
        }

        // Content based on selected tab
        when (selectedTabIndex) {
            0 -> HydrationTab()
            1 -> MedicationsTab()
            2 -> ProfileTab()
        }
    }
}

@Composable
fun HydrationTab() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Circular Hydration Progress Ring (Placeholder)
        Box(
            modifier = Modifier
                .size(150.dp)
                .background(Color.LightGray), // Simulating a progress ring
            contentAlignment = Alignment.Center
        ) {
            Text("75%", fontSize = 24.sp)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text("75% of Daily Goal Achieved", fontSize = 16.sp, textAlign = TextAlign.Center)

        Spacer(modifier = Modifier.height(24.dp))

        // Quick Add Water Buttons
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(onClick = { /* Add 250ml */ }) {
                Text("+250ml")
            }
            Button(onClick = { /* Add 500ml */ }) {
                Text("+500ml")
            }
            Button(onClick = { /* Add 750ml */ }) {
                Text("+750ml")
            }
        }
    }
}

@Composable
fun MedicationsTab() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 32.dp)
    ) {
        Text("Medication Schedule for Today", fontSize = 20.sp, modifier = Modifier.padding(bottom = 16.dp))

        // Medication List
        MedicationItem("Vitamin D", "8:00 AM", false)
        MedicationItem("Aspirin", "2:00 PM", false)
        MedicationItem("Antibiotic", "8:00 PM", false)
    }
}

@Composable
fun MedicationItem(name: String, time: String, isChecked: Boolean) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Checkbox(checked = isChecked, onCheckedChange = { /* Handle Check Change */ })
        Spacer(modifier = Modifier.width(8.dp))
        Text("$name - $time", fontSize = 16.sp)
    }
}

@Composable
fun ProfileTab() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 32.dp)
    ) {
        Text("User Information", fontSize = 20.sp, modifier = Modifier.padding(bottom = 16.dp))
        Text("Name: John Doe", fontSize = 16.sp)
        Text("Age: 30", fontSize = 16.sp)
        Text("Weight: 70kg", fontSize = 16.sp)

        Spacer(modifier = Modifier.height(32.dp))

        Text("Settings Access", fontSize = 20.sp, modifier = Modifier.padding(bottom = 16.dp))
        Text("Notifications, Goals, Preferences", fontSize = 16.sp)
    }
}