package com.studsty.app.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import data.model.Flashcard
import data.model.StudySession
import data.repository.StudstyRepository
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope

@Composable
fun FlashcardsScreen(
    subject: String = "",
    chapterId: Int = 1,
    onBackToHome: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    val repository = remember {
        StudstyRepository(context)
    }

    var flashcards by remember {
        mutableStateOf<List<Flashcard>>(emptyList())
    }

    LaunchedEffect(chapterId) {
        flashcards = repository.getFlashcardsByChapter(chapterId)
    }

    var currentIndex by remember {
        mutableIntStateOf(0)
    }

    var isFlipped by remember {
        mutableStateOf(false)
    }

    var isFinished by remember {
        mutableStateOf(false)
    }

    fun nextCard() {
        if (currentIndex < flashcards.lastIndex) {

            currentIndex++
            isFlipped = false

        } else {

            coroutineScope.launch {
                repository.saveStudySession(
                    StudySession(
                        id = System.currentTimeMillis().toInt(),
                        chapterId = chapterId,
                        subject = subject,
                        type = "Flashcards",
                        score = flashcards.size,
                        total = flashcards.size,
                        date = System.currentTimeMillis()
                    )
                )
            }
            isFinished = true
        }
    }

    val rotation by animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        animationSpec = tween(durationMillis = 400),
        label = "cardFlipAnimation"
    )

    if (flashcards.isEmpty()) {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Aucune flashcard disponible pour ce chapitre."
            )
        }

        return
    }

    if (isFinished) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Session Flashcards terminée !",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Button(
                onClick = {
                    currentIndex = 0
                    isFlipped = false
                    isFinished = false
                }
            ) {
                Text("Recommencer la révision")
            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Button(
                onClick = onBackToHome,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Retour à l'accueil")
            }
        }

        return
    }

    val currentCard = flashcards[currentIndex]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Flashcards - $subject",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "Carte ${currentIndex + 1} / ${flashcards.size}",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(
            modifier = Modifier.height(32.dp)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
                .graphicsLayer {
                    rotationY = rotation
                    cameraDistance = 12f * density
                }
                .clickable {
                    isFlipped = !isFlipped
                },
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            )
        ) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {

                if (rotation <= 90f) {

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            text = "QUESTION",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Spacer(
                            modifier = Modifier.height(16.dp)
                        )

                        Text(
                            text = currentCard.question,
                            style = MaterialTheme.typography.titleLarge,
                            textAlign = TextAlign.Center
                        )

                        Spacer(
                            modifier = Modifier.height(24.dp)
                        )

                        Text(
                            text = "Touchez pour afficher la réponse",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                    }

                } else {

                    Column(
                        modifier = Modifier.graphicsLayer {
                            rotationY = 180f
                        },
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            text = "RÉPONSE",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.secondary
                        )

                        Spacer(
                            modifier = Modifier.height(16.dp)
                        )

                        Text(
                            text = currentCard.answer,
                            style = MaterialTheme.typography.titleMedium,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }

        Spacer(
            modifier = Modifier.height(32.dp)
        )

        if (isFlipped) {

            Text(
                text = "Évaluez votre niveau de maîtrise :",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                Button(
                    onClick = {
                        nextCard()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4CAF50)
                    )
                ) {
                    Text("Facile")
                }

                Button(
                    onClick = {
                        nextCard()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFF9800)
                    )
                ) {
                    Text("Moyen")
                }

                Button(
                    onClick = {
                        nextCard()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFF44336)
                    )
                ) {
                    Text("Difficile")
                }
            }
        }
    }
}