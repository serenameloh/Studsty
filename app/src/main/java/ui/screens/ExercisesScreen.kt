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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import data.model.StudySession
import data.repository.StudstyRepository
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import data.model.Exercise
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope

@Composable
fun ExercisesScreen(
    subject: String,
    chapterId: Int,
    onBackToHome: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    val context = LocalContext.current

    val repository = remember {
        StudstyRepository(context)
    }

    var exercises by remember {
        mutableStateOf<List<Exercise>>(emptyList())
    }

    LaunchedEffect(chapterId) {
        exercises = repository.getExercisesByChapter(
            chapterId = chapterId,
            subject = subject
        )
    }

    var currentExercise by remember {
        mutableIntStateOf(0)
    }

    var score by remember {
        mutableIntStateOf(0)
    }

    var userAnswer by remember {
        mutableStateOf("")
    }

    var answerChecked by remember {
        mutableStateOf(false)
    }

    var matchedKeywords by remember {
        mutableIntStateOf(0)
    }

    var sessionFinished by remember {
        mutableStateOf(false)
    }

    if (exercises.isEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Aucun exercice disponible",
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "Aucun exercice n'est disponible pour $subject."
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
                        text = "$score / ${exercises.size}",
                        style = MaterialTheme.typography.headlineLarge
                    )

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    val percentage =
                        (score.toFloat() / exercises.size.toFloat() * 100).toInt()

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
                    currentExercise = 0
                    score = 0
                    userAnswer = ""
                    answerChecked = false
                    matchedKeywords = 0
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

    val exercise = exercises[currentExercise]

    val progress =
        (currentExercise + 1).toFloat() / exercises.size.toFloat()

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
            text = "Exercices",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Text(
            text = "Exercice ${currentExercise + 1} sur ${exercises.size}",
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
                    text = "Énoncé",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                Text(
                    text = exercise.statement,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Text(
            text = "Ta réponse",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        OutlinedTextField(
            value = userAnswer,
            onValueChange = {
                if (!answerChecked) {
                    userAnswer = it
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !answerChecked,
            minLines = 5,
            shape = RoundedCornerShape(16.dp),
            placeholder = {
                Text("Écris ta réponse ici...")
            }
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        if (!answerChecked) {
            Button(
                onClick = {
                    val normalizedAnswer =
                        userAnswer.trim().lowercase()

                    matchedKeywords =
                        exercise.keywords.count { keyword ->
                            normalizedAnswer.contains(
                                keyword.trim().lowercase()
                            )
                        }

                    answerChecked = true

                    val requiredKeywords =
                        exercise.keywords.size

                    if (
                        requiredKeywords > 0 &&
                        matchedKeywords == requiredKeywords
                    ) {
                        score++
                    }
                },
                enabled = userAnswer.isNotBlank(),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text("Corriger ma réponse")
            }
        }

        if (answerChecked) {
            Spacer(
                modifier = Modifier.height(20.dp)
            )

            val keywordPercentage =
                if (exercise.keywords.isNotEmpty()) {
                    matchedKeywords * 100 / exercise.keywords.size
                } else {
                    0
                }

            val resultText =
                when {
                    keywordPercentage >= 60 ->
                        "Réponse correcte"

                    keywordPercentage > 0 ->
                        "Réponse partiellement correcte"

                    else ->
                        "Réponse incorrecte"
                }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(
                    containerColor =
                        MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = resultText,
                        style = MaterialTheme.typography.titleLarge
                    )

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    Text(
                        text = "Éléments trouvés : $matchedKeywords / ${exercise.keywords.size}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            Text(
                text = "Réponse attendue",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = exercise.expectedAnswer,
                    modifier = Modifier.padding(18.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            Button(
                onClick = {
                    if (currentExercise < exercises.lastIndex) {
                        currentExercise++
                        userAnswer = ""
                        answerChecked = false
                        matchedKeywords = 0
                    } else {
                        coroutineScope.launch {
                            repository.saveStudySession(
                                StudySession(
                                    id = System.currentTimeMillis().toInt(),
                                    chapterId = chapterId,
                                    subject = subject,
                                    type = "Exercices",
                                    score = score,
                                    total = exercises.size,
                                    date = System.currentTimeMillis()
                                )
                            )
                        }
                        sessionFinished = true
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text(
                    text = if (
                        currentExercise < exercises.lastIndex
                    ) {
                        "Exercice suivant"
                    } else {
                        "Terminer"
                    }
                )
            }
        }
    }
}