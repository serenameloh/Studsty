package com.studsty.app.ui.screens

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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import data.model.StudySession
import data.repository.StudstyRepository
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext

@Composable
fun ProgressionScreen(
    onBackToHome: () -> Unit
) {
    val context = LocalContext.current

    val repository = remember {
        StudstyRepository(context)
    }

    var sessions by remember {
        mutableStateOf<List<StudySession>>(emptyList())
    }

    LaunchedEffect(Unit) {
        sessions = repository.getStudySessions()
    }

    val totalSessions = sessions.size

    val totalCorrect = sessions.sumOf {
        it.score
    }

    val totalQuestions = sessions.sumOf {
        it.total
    }

    val successRate =
        if (totalQuestions > 0) {
            (totalCorrect.toFloat() / totalQuestions.toFloat()) * 100
        } else {
            0f
        }

    val history = sessions.reversed()

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
            text = "Ma progression",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF202124)
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Suis tes performances et tes séances de révision.",
            style = MaterialTheme.typography.bodyLarge,
            color = Color(0xFF6B7280)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // --------------------------------------------------
        // SCORE GLOBAL
        // --------------------------------------------------

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF6C63FF)
            )
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(22.dp)
            ) {

                Text(
                    text = "SCORE GLOBAL",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.White.copy(alpha = 0.8f)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "$totalCorrect / $totalQuestions",
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "${successRate.toInt()} % de réussite",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(18.dp))

                LinearProgressIndicator(
                    progress = {
                        if (totalQuestions > 0) {
                            totalCorrect.toFloat() / totalQuestions.toFloat()
                        } else {
                            0f
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp),
                    color = Color.White,
                    trackColor = Color.White.copy(alpha = 0.25f)
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // --------------------------------------------------
        // STATISTIQUES
        // --------------------------------------------------

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            StatisticCard(
                modifier = Modifier.weight(1f),
                value = totalSessions.toString(),
                label = "Séances"
            )

            StatisticCard(
                modifier = Modifier.weight(1f),
                value = "${successRate.toInt()} %",
                label = "Réussite"
            )
        }

        Spacer(modifier = Modifier.height(26.dp))

        // --------------------------------------------------
        // ACTIVITÉ
        // --------------------------------------------------

        Text(
            text = "Mon activité",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF202124)
        )

        Spacer(modifier = Modifier.height(12.dp))

        ActivitySummary(
            sessions = sessions
        )

        Spacer(modifier = Modifier.height(26.dp))

        // --------------------------------------------------
        // HISTORIQUE
        // --------------------------------------------------

        Text(
            text = "Historique",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF202124)
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (history.isEmpty()) {

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
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "Aucune séance pour le moment.",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Commence une séance pour voir ta progression.",
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

                items(history) { session ->

                    SessionHistoryCard(
                        session = session
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // --------------------------------------------------
        // RETOUR
        // --------------------------------------------------

        Card(
            onClick = onBackToHome,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 1.dp
            )
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 14.dp),
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = "Retour à l'accueil",
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF6C63FF)
                )
            }
        }
    }
}

@Composable
private fun StatisticCard(
    modifier: Modifier,
    value: String,
    label: String
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        color = Color.White,
        shadowElevation = 1.dp
    ) {

        Column(
            modifier = Modifier.padding(18.dp)
        ) {

            Text(
                text = value,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF6C63FF)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF6B7280)
            )
        }
    }
}

@Composable
private fun ActivitySummary(
    sessions: List<StudySession>
) {
    val types = listOf(
        "QCM",
        "Flashcards",
        "Vrai/Faux",
        "Exercices"
    )

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
                .padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            types.forEach { type ->

                val count = sessions.count {
                    it.type == type
                }

                ActivityRow(
                    type = type,
                    count = count
                )
            }
        }
    }
}

@Composable
private fun ActivityRow(
    type: String,
    count: Int
) {
    val maxValue = 5

    val progress =
        (count.toFloat() / maxValue.toFloat())
            .coerceIn(0f, 1f)

    Column {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = type,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = "$count séance(s)",
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF6B7280)
            )
        }

        Spacer(modifier = Modifier.height(7.dp))

        LinearProgressIndicator(
            progress = {
                progress
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(7.dp),
            color = Color(0xFF6C63FF),
            trackColor = Color(0xFFE9E7FF)
        )
    }
}

@Composable
private fun SessionHistoryCard(
    session: StudySession
) {
    val percentage =
        if (session.total > 0) {
            (session.score.toFloat() / session.total.toFloat()) * 100
        } else {
            0f
        }

    val percentageInt = percentage.toInt()

    val successColor =
        if (percentageInt >= 70) {
            Color(0xFF16A34A)
        } else {
            Color(0xFFEA580C)
        }

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

        Column(
            modifier = Modifier.padding(18.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .background(
                            color = successColor,
                            shape = CircleShape
                        )
                )

                Spacer(modifier = Modifier.width(10.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Text(
                        text = session.subject,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = session.type,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF6B7280)
                    )
                }

                Text(
                    text = "${session.score}/${session.total}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = successColor
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            LinearProgressIndicator(
                progress = {
                    percentage / 100f
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(7.dp),
                color = successColor,
                trackColor = Color(0xFFE5E7EB)
            )

            Spacer(modifier = Modifier.height(7.dp))

            Text(
                text = "$percentageInt % de réussite",
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF6B7280)
            )
        }
    }
}