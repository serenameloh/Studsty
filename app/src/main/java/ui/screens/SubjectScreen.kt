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
import androidx.compose.ui.unit.dp
import com.studsty.app.data.database.SubjectEntity
import com.studsty.app.data.database.DatabaseProvider
import androidx.compose.ui.platform.LocalContext
@Composable
fun SubjectScreen(
    onSubjectSelected: (Int, String) -> Unit
) {
    val context = LocalContext.current
    val database = remember {
        DatabaseProvider.getDatabase(context)
    }
    var subjects by remember {
        mutableStateOf<List<SubjectEntity>>(emptyList())
    }
    var chapterCounts by remember {
        mutableStateOf<Map<Int, Int>>(emptyMap())
    }
    LaunchedEffect(Unit) {
        val loadedSubjects = database
            .subjectDao()
            .getAllSubjects()
        subjects = loadedSubjects
        val counts = loadedSubjects.associate { subject ->
            subject.id to database
                .chapterDao()
                .getChaptersBySubject(subject.id)
                .size
        }
        chapterCounts = counts
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text(
            text = "Choisir une matière",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(
            modifier = Modifier.height(6.dp)
        )
        Text(
            text = "Sélectionne la matière sur laquelle tu veux réviser.",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(
            modifier = Modifier.height(24.dp)
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            subjects.forEachIndexed { index, subject ->
                val chapterCount =
                    chapterCounts[subject.id] ?: 0
                val backgroundColor = when (index % 4) {
                    0 -> Color(0xFFEDE9FE)
                    1 -> Color(0xFFE8F5E9)
                    2 -> Color(0xFFFFF3E0)
                    else -> Color(0xFFE0F7FA)
                }
                Card(
                    onClick = {
                        onSubjectSelected(
                            subject.id,
                            subject.name
                        )
                    },
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
                            text = subject.name,
                            style = MaterialTheme.typography.titleLarge
                        )
                        Spacer(
                            modifier = Modifier.height(6.dp)
                        )
                        Text(
                            text = "$chapterCount chapitre(s)",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(
                            modifier = Modifier.height(4.dp)
                        )
                        Text(
                            text = "Voir les chapitres",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}