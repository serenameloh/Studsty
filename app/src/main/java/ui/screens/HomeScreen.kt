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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import data.model.StudySession
import data.repository.StudstyRepository
@Composable
fun HomeScreen(
    onQuizClick: () -> Unit,
    onFlashcardsClick: () -> Unit,
    onTrueFalseClick: () -> Unit,
    onExercisesClick: () -> Unit,
    onProgressionClick: () -> Unit,
    onReminderClick: () -> Unit
) {
    val scrollState = rememberScrollState()
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
            (totalCorrect.toFloat() / totalQuestions.toFloat()) * 100f
        } else {
            0f
        }
    val progression = successRate / 100f
    val exerciseSessions = sessions.count {
        it.type == "Exercices"
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(Color(0xFFF7F8FC))
            .padding(horizontal = 20.dp, vertical = 24.dp)
    ) {
        // --------------------------------------------------
        // HEADER
        // --------------------------------------------------
        Text(
            text = "Bonjour !",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF202124)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Prêt pour une nouvelle séance de révision ?",
            style = MaterialTheme.typography.bodyLarge,
            color = Color(0xFF6B7280)
        )
        Spacer(modifier = Modifier.height(24.dp))
        // --------------------------------------------------
        // CARD PROGRESSION
        // --------------------------------------------------
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color(0xFF6C63FF),
                                Color(0xFF8B7CFF)
                            )
                        ),
                        shape = RoundedCornerShape(24.dp)
                    )
                    .padding(22.dp)
            ) {
                Column {
                    Text(
                        text = "Ma progression",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Continue tes révisions pour progresser.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.85f)
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = "Progression",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.White.copy(alpha = 0.8f)
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            LinearProgressIndicator(
                                progress = { progression },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(8.dp),
                                color = Color.White,
                                trackColor = Color.White.copy(alpha = 0.25f)
                            )
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = "${successRate.toInt()}%",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.height(18.dp))
                    Card(
                        onClick = onProgressionClick,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White.copy(alpha = 0.16f)
                        )
                    ) {
                        Text(
                            text = "Voir ma progression",
                            modifier = Modifier.padding(
                                horizontal = 16.dp,
                                vertical = 12.dp
                            ),
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(28.dp))
        // --------------------------------------------------
        // TITLE
        // --------------------------------------------------
        Text(
            text = "Mes révisions",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF202124)
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "Choisis une méthode pour commencer.",
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF6B7280)
        )
        Spacer(modifier = Modifier.height(16.dp))
        // --------------------------------------------------
        // REVISION CARDS
        // --------------------------------------------------
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            RevisionModeCard(
                modifier = Modifier.weight(1f),
                title = "QCM",
                description = "Teste tes connaissances",
                badge = "Q",
                backgroundColor = Color(0xFFEDE9FE),
                badgeColor = Color(0xFF7C3AED),
                onClick = onQuizClick
            )
            RevisionModeCard(
                modifier = Modifier.weight(1f),
                title = "Flashcards",
                description = "Mémorise rapidement",
                badge = "F",
                backgroundColor = Color(0xFFE0F2FE),
                badgeColor = Color(0xFF0284C7),
                onClick = onFlashcardsClick
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            RevisionModeCard(
                modifier = Modifier.weight(1f),
                title = "Vrai ou Faux",
                description = "Vérifie tes acquis",
                badge = "VF",
                backgroundColor = Color(0xFFDCFCE7),
                badgeColor = Color(0xFF16A34A),
                onClick = onTrueFalseClick
            )
            RevisionModeCard(
                modifier = Modifier.weight(1f),
                title = "Exercices",
                description = "Mets tes connaissances en pratique",
                badge = "EX",
                backgroundColor = Color(0xFFFFEDD5),
                badgeColor = Color(0xFFEA580C),
                onClick = onExercisesClick
            )
        }
        Spacer(modifier = Modifier.height(28.dp))
        // --------------------------------------------------
        // PROCHAINE REVISION
        // --------------------------------------------------
        Text(
            text = "À venir",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF202124)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Card(
            onClick = onReminderClick,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 2.dp
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
                Spacer(modifier = Modifier.width(14.dp))
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Mes rappels",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Consulte et programme tes prochaines révisions.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF6B7280)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        // --------------------------------------------------
        // STATISTIQUES
        // --------------------------------------------------
        Text(
            text = "Mon activité",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF202124)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ActivityCard(
                modifier = Modifier.weight(1f),
                value = totalSessions.toString(),
                label = "Séances"
            )
            ActivityCard(
                modifier = Modifier.weight(1f),
                value = exerciseSessions.toString(),
                label = "Exercices"
            )
            ActivityCard(
                modifier = Modifier.weight(1f),
                value = "${successRate.toInt()}%",
                label = "Réussite"
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
    }
}
@Composable
private fun RevisionModeCard(
    modifier: Modifier,
    title: String,
    description: String,
    badge: String,
    backgroundColor: Color,
    badgeColor: Color,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .background(
                        color = badgeColor,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = badge,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(14.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF202124)
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF6B7280)
            )
        }
    }
}
@Composable
private fun ActivityCard(
    modifier: Modifier,
    value: String,
    label: String
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(18.dp),
        color = Color.White,
        shadowElevation = 1.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
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
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF6B7280)
            )
        }
    }
}