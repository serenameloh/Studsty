package com.studsty.app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import data.model.StudySession
import data.model.TrueFalseQuestion
import data.repository.StudstyRepository
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope

@Composable
fun TrueFalseScreen(
    subject: String,
    chapterId: Int,
    onBackToHome: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    val context = LocalContext.current

    val repository = remember {
        StudstyRepository(context)
    }

    var questions by remember {
        mutableStateOf<List<TrueFalseQuestion>>(emptyList())
    }

    LaunchedEffect(chapterId) {
        questions = repository.getTrueFalseByChapter(chapterId)
    }

    var currentQuestion by remember {
        mutableIntStateOf(0)
    }

    var score by remember {
        mutableIntStateOf(0)
    }

    var selectedAnswer by remember {
        mutableStateOf<Boolean?>(null)
    }

    var sessionFinished by remember {
        mutableStateOf(false)
    }

    if (questions.isEmpty()) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "Aucune question disponible",
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "Aucune question n'est disponible pour $subject."
            )

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            Button(
                onClick = onBackToHome,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text("Retour à l'accueil")
            }
        }

        return
    }

    if (sessionFinished) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "Session terminée",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = subject,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(
                modifier = Modifier.height(28.dp)
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {

                Column(
                    modifier = Modifier.padding(24.dp)
                ) {

                    Text(
                        text = "Ton score",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    Text(
                        text = "$score / ${questions.size}",
                        style = MaterialTheme.typography.headlineLarge
                    )

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    val percentage =
                        (score.toFloat() / questions.size.toFloat() * 100).toInt()

                    Text(
                        text = "$percentage % de réussite",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            Button(
                onClick = {
                    currentQuestion = 0
                    score = 0
                    selectedAnswer = null
                    sessionFinished = false
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text("Recommencer")
            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            OutlinedButton(
                onClick = onBackToHome,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text("Retour à l'accueil")
            }
        }

        return
    }

    val question = questions[currentQuestion]

    val progress =
        (currentQuestion + 1).toFloat() / questions.size.toFloat()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Text(
            text = subject,
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(
            modifier = Modifier.height(4.dp)
        )

        Text(
            text = "Vrai ou Faux",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Text(
            text = "Question ${currentQuestion + 1} sur ${questions.size}",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 2.dp
            )
        ) {

            Column(
                modifier = Modifier.padding(24.dp)
            ) {

                Text(
                    text = "Affirmation",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                Text(
                    text = question.question,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }

        Spacer(
            modifier = Modifier.height(28.dp)
        )

        Text(
            text = "Choisis une réponse",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Button(
            onClick = {
                selectedAnswer = true
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(14.dp),
            colors = if (selectedAnswer == true) {
                ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            } else {
                ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        ) {
            Text("Vrai")
        }

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Button(
            onClick = {
                selectedAnswer = false
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(14.dp),
            colors = if (selectedAnswer == false) {
                ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            } else {
                ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        ) {
            Text("Faux")
        }

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Button(
            onClick = {

                val answerIsCorrect =
                    selectedAnswer == question.correctAnswer

                if (answerIsCorrect) {
                    score++
                }

                if (currentQuestion < questions.lastIndex) {

                    currentQuestion++
                    selectedAnswer = null

                } else {
                    coroutineScope.launch {
                        repository.saveStudySession(
                            StudySession(
                                id = System.currentTimeMillis().toInt(),
                                chapterId = chapterId,
                                subject = subject,
                                type = "Vrai/Faux",
                                score = score,
                                total = questions.size,
                                date = System.currentTimeMillis()
                            )
                        )
                    }
                    sessionFinished = true
                }
            },
            enabled = selectedAnswer != null,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp)
        ) {

            Text(
                text = if (currentQuestion < questions.lastIndex) {
                    "Question suivante"
                } else {
                    "Terminer"
                }
            )
        }
    }
}