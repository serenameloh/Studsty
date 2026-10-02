package com.studsty.app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.studsty.app.data.database.ChapterEntity
import com.studsty.app.data.database.DatabaseProvider

@Composable
fun ChapterScreen(
    mode: String,
    subjectId: Int,
    subjectName: String,
    onChapterSelected: (String, Int) -> Unit
) {
    val context = LocalContext.current

    val database = remember {
        DatabaseProvider.getDatabase(context)
    }

    var chapters by remember {
        mutableStateOf<List<ChapterEntity>>(emptyList())
    }

    LaunchedEffect(subjectId) {
        chapters = database
            .chapterDao()
            .getChaptersBySubject(subjectId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text(
            text = subjectName,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(
            modifier = Modifier.height(6.dp)
        )

        Text(
            text = "Choisir un chapitre",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "Sélectionne le chapitre que tu veux réviser.",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        if (chapters.isEmpty()) {
            Text(
                text = "Aucun chapitre disponible pour cette matière."
            )
        } else {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                chapters.forEachIndexed { index, chapter ->

                    ChapterCard(
                        chapter = chapter,
                        index = index,
                        onClick = {
                            onChapterSelected(
                                subjectName,
                                chapter.id
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun ChapterCard(
    chapter: ChapterEntity,
    index: Int,
    onClick: () -> Unit
) {
    val backgroundColor = when (index % 4) {
        0 -> Color(0xFFEDE9FE)
        1 -> Color(0xFFE8F5E9)
        2 -> Color(0xFFFFF3E0)
        else -> Color(0xFFE0F7FA)
    }

    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = chapter.title,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            Text(
                text = "Chapitre ${chapter.chapterOrder}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            Text(
                text = "Appuie pour commencer",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}