package com.studsty.app.ui.screens
import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.studsty.app.ReminderReceiver
import data.model.RevisionReminder
import data.repository.StudstyRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
@Composable
fun ReminderScreen(
    onBackToHome: () -> Unit
) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val repository = remember {
        StudstyRepository(context)
    }
    val coroutineScope = rememberCoroutineScope()
    val subjects = remember {
        repository.getSubjects()
    }
    var selectedSubject by remember {
        mutableStateOf(
            subjects.firstOrNull()?.name ?: "Algorithmique"
        )
    }
    var dateText by remember {
        mutableStateOf("")
    }
    var timeText by remember {
        mutableStateOf("")
    }
    var reminders by remember {
        mutableStateOf<List<RevisionReminder>>(emptyList())
    }
    var message by remember {
        mutableStateOf("")
    }
    LaunchedEffect(Unit) {
        reminders = repository.getRevisionReminders()
    }
    val notificationPermissionLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission()
        ) { granted ->
            if (!granted) {
                message =
                    "La permission de notification est nécessaire pour recevoir les rappels."
            }
        }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F8FC))
            .padding(horizontal = 20.dp, vertical = 24.dp)
    ) {
        // --------------------------------------------------
        // HEADER
        // --------------------------------------------------
        Text(
            text = "Rappels de révision",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF202124)
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "Organise tes prochaines séances de révision.",
            style = MaterialTheme.typography.bodyLarge,
            color = Color(0xFF6B7280)
        )
        Spacer(modifier = Modifier.height(24.dp))
        // --------------------------------------------------
        // CREATION DU RAPPEL
        // --------------------------------------------------
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 2.dp
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text(
                    text = "Nouveau rappel",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Matière",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = selectedSubject,
                    onValueChange = {
                        selectedSubject = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    label = {
                        Text("Matière")
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Date",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = dateText,
                    onValueChange = {
                        dateText = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    placeholder = {
                        Text("Exemple : 28/09/2026")
                    },
                    label = {
                        Text("Date")
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Heure",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = timeText,
                    onValueChange = {
                        timeText = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    placeholder = {
                        Text("Exemple : 18:30")
                    },
                    label = {
                        Text("Heure")
                    }
                )
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = {
                        if (dateText.isBlank() || timeText.isBlank()) {
                            message =
                                "Veuillez renseigner la date et l'heure."
                            return@Button
                        }
                        val dateParts = dateText.split("/")
                        val timeParts = timeText.split(":")
                        if (
                            dateParts.size != 3 ||
                            timeParts.size != 2
                        ) {
                            message =
                                "Format incorrect. Utilise JJ/MM/AAAA et HH:MM."
                            return@Button
                        }
                        try {
                            val day = dateParts[0].toInt()
                            val month = dateParts[1].toInt()
                            val year = dateParts[2].toInt()
                            val hour = timeParts[0].toInt()
                            val minute = timeParts[1].toInt()
                            val calendar = Calendar.getInstance()
                            calendar.set(
                                year,
                                month - 1,
                                day,
                                hour,
                                minute,
                                0
                            )
                            calendar.set(
                                Calendar.MILLISECOND,
                                0
                            )
                            if (
                                calendar.timeInMillis <=
                                System.currentTimeMillis()
                            ) {
                                message =
                                    "La date et l'heure doivent être dans le futur."
                                return@Button
                            }
                            // --------------------------------------------------
                            // PERMISSION NOTIFICATION
                            // --------------------------------------------------
                            if (
                                android.os.Build.VERSION.SDK_INT >=
                                android.os.Build.VERSION_CODES.TIRAMISU
                            ) {
                                val permissionGranted =
                                    ContextCompat.checkSelfPermission(
                                        context,
                                        Manifest.permission.POST_NOTIFICATIONS
                                    ) ==
                                            PackageManager.PERMISSION_GRANTED
                                if (!permissionGranted) {
                                    notificationPermissionLauncher.launch(
                                        Manifest.permission.POST_NOTIFICATIONS
                                    )
                                }
                            }
                            // --------------------------------------------------
                            // PERMISSION ALARME EXACTE
                            // --------------------------------------------------
                            val alarmManager =
                                context.getSystemService(
                                    Context.ALARM_SERVICE
                                ) as AlarmManager
                            if (
                                android.os.Build.VERSION.SDK_INT >=
                                android.os.Build.VERSION_CODES.S &&
                                !alarmManager.canScheduleExactAlarms()
                            ) {
                                val intent =
                                    Intent(
                                        Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM,
                                        Uri.parse(
                                            "package:${context.packageName}"
                                        )
                                    )
                                context.startActivity(intent)
                                message =
                                    "Autorise les alarmes exactes puis programme à nouveau le rappel."
                                return@Button
                            }
                            val reminderId =
                                System.currentTimeMillis().toInt()
                            val reminder =
                                RevisionReminder(
                                    id = reminderId,
                                    subject = selectedSubject,
                                    date = calendar.timeInMillis,
                                    enabled = true
                                )
                            // --------------------------------------------------
                            // ENREGISTREMENT ROOM
                            // --------------------------------------------------
                            coroutineScope.launch {
                                repository.saveRevisionReminder(
                                    reminder
                                )
                                reminders =
                                    repository.getRevisionReminders()
                            }
                            // --------------------------------------------------
                            // PROGRAMMATION DE L'ALARME
                            // --------------------------------------------------
                            val intent =
                                Intent(
                                    context,
                                    ReminderReceiver::class.java
                                ).apply {
                                    putExtra(
                                        "subject",
                                        selectedSubject
                                    )
                                }
                            val pendingIntent =
                                PendingIntent.getBroadcast(
                                    context,
                                    reminderId,
                                    intent,
                                    PendingIntent.FLAG_UPDATE_CURRENT or
                                            PendingIntent.FLAG_IMMUTABLE
                                )
                            alarmManager.setExactAndAllowWhileIdle(
                                AlarmManager.RTC_WAKEUP,
                                calendar.timeInMillis,
                                pendingIntent
                            )
                            message =
                                "Rappel programmé avec succès."
                            dateText = ""
                            timeText = ""
                        } catch (
                            exception: Exception
                        ) {
                            message =
                                "Impossible de programmer le rappel. Vérifie les informations."
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Text(
                        text = "Programmer le rappel",
                        modifier = Modifier.padding(
                            vertical = 4.dp
                        )
                    )
                }
                if (message.isNotBlank()) {
                    Spacer(modifier = Modifier.height(14.dp))
                    Text(
                        text = message,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF6C63FF)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(26.dp))
        // --------------------------------------------------
        // RAPPELS EXISTANTS
        // --------------------------------------------------
        Text(
            text = "Mes rappels",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF202124)
        )
        Spacer(modifier = Modifier.height(12.dp))
        if (reminders.isEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(22.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .background(
                                Color(0xFFFFF7ED),
                                CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "R",
                            color = Color(0xFFEA580C),
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Aucun rappel programmé",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Programme ta prochaine séance de révision.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF6B7280)
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(reminders) { reminder ->
                    ReminderCard(
                        reminder = reminder
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(14.dp))
        // --------------------------------------------------
        // RETOUR
        // --------------------------------------------------
        OutlinedButton(
            onClick = onBackToHome,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = "Retour à l'accueil",
                modifier = Modifier.padding(
                    vertical = 4.dp
                )
            )
        }
    }
}
@Composable
private fun ReminderCard(
    reminder: RevisionReminder
) {
    val dateFormatter =
        remember {
            SimpleDateFormat(
                "dd/MM/yyyy",
                Locale.getDefault()
            )
        }
    val timeFormatter =
        remember {
            SimpleDateFormat(
                "HH:mm",
                Locale.getDefault()
            )
        }
    val date = Date(reminder.date)
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        Color(0xFFEDE9FE),
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "R",
                    color = Color(0xFF6C63FF),
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.width(14.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = reminder.subject,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "${dateFormatter.format(date)} à ${timeFormatter.format(date)}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF6B7280)
                )
                Spacer(modifier = Modifier.height(7.dp))
                Text(
                    text = if (reminder.enabled) {
                        "Rappel activé"
                    } else {
                        "Rappel désactivé"
                    },
                    style = MaterialTheme.typography.bodySmall,
                    color = if (reminder.enabled) {
                        Color(0xFF16A34A)
                    } else {
                        Color(0xFF6B7280)
                    },
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}