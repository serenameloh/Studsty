package com.studsty.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.studsty.app.ui.screens.ChapterScreen
import com.studsty.app.ui.screens.ExercisesScreen
import com.studsty.app.ui.screens.FlashcardsScreen
import com.studsty.app.ui.screens.HomeScreen
import com.studsty.app.ui.screens.LoginScreen
import com.studsty.app.ui.screens.ProgressionScreen
import com.studsty.app.ui.screens.QuizScreen
import com.studsty.app.ui.screens.ReminderScreen
import com.studsty.app.ui.screens.SubjectScreen
import com.studsty.app.ui.screens.TrueFalseScreen
import com.studsty.app.ui.screens.WelcomeScreen

object Routes {
    const val WELCOME = "welcome"
    const val LOGIN = "login"
    const val HOME = "home"
    const val SUBJECTS = "subjects/{mode}"
    const val QUIZ = "quiz/{subject}/{chapterId}"
    const val FLASHCARDS = "flashcards/{subject}/{chapterId}"
    const val TRUE_FALSE = "true_false/{subject}/{chapterId}"
    const val EXERCISES = "exercises/{subject}/{chapterId}"
    const val PROGRESSION = "progression"
    const val REMINDERS = "reminders"
    const val CHAPTERS = "chapters/{mode}/{subjectId}/{subjectName}"
}

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.WELCOME
    ) {

        composable(Routes.WELCOME) {
            WelcomeScreen(
                onStartClick = {
                    navController.navigate(Routes.LOGIN)
                }
            )
        }

        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginClick = {
                    navController.navigate(Routes.HOME)
                }
            )
        }

        composable(Routes.HOME) {
            HomeScreen(
                onQuizClick = {
                    navController.navigate("subjects/quiz")
                },
                onFlashcardsClick = {
                    navController.navigate("subjects/flashcards")
                },
                onTrueFalseClick = {
                    navController.navigate("subjects/true_false")
                },
                onExercisesClick = {
                    navController.navigate("subjects/exercises")
                },
                onProgressionClick = {
                    navController.navigate(Routes.PROGRESSION)
                },
                onReminderClick = {
                    navController.navigate(Routes.REMINDERS)
                }
            )
        }

        composable(Routes.PROGRESSION) {
            ProgressionScreen(
                onBackToHome = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.HOME) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(
            route = Routes.SUBJECTS,
            arguments = listOf(
                navArgument("mode") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->

            val mode =
                backStackEntry.arguments?.getString("mode")
                    ?: "quiz"

            SubjectScreen(
                onSubjectSelected = { subjectId, subjectName ->

                    navController.navigate(
                        "chapters/$mode/$subjectId/$subjectName"
                    )
                }
            )
        }

        composable(
            route = Routes.CHAPTERS,
            arguments = listOf(
                navArgument("mode") {
                    type = NavType.StringType
                },
                navArgument("subjectId") {
                    type = NavType.IntType
                },
                navArgument("subjectName") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->

            val mode =
                backStackEntry.arguments?.getString("mode")
                    ?: "quiz"

            val subjectId =
                backStackEntry.arguments?.getInt("subjectId")
                    ?: 1

            val subjectName =
                backStackEntry.arguments?.getString("subjectName")
                    ?: ""

            ChapterScreen(
                mode = mode,
                subjectId = subjectId,
                subjectName = subjectName,
                onChapterSelected = { subject, chapterId ->

                    when (mode) {

                        "quiz" -> {
                            navController.navigate(
                                "quiz/$subject/$chapterId"
                            )
                        }

                        "flashcards" -> {
                            navController.navigate(
                                "flashcards/$subject/$chapterId"
                            )
                        }

                        "true_false" -> {
                            navController.navigate(
                                "true_false/$subject/$chapterId"
                            )
                        }

                        "exercises" -> {
                            navController.navigate(
                                "exercises/$subject/$chapterId"
                            )
                        }
                    }
                }
            )
        }

        composable(
            route = Routes.QUIZ,
            arguments = listOf(
                navArgument("subject") {
                    type = NavType.StringType
                },
                navArgument("chapterId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->

            val subject =
                backStackEntry.arguments?.getString("subject")
                    ?: ""

            val chapterId =
                backStackEntry.arguments?.getInt("chapterId")
                    ?: 1

            QuizScreen(
                subject = subject,
                chapterId = chapterId,
                onBackToHome = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.HOME) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(
            route = Routes.FLASHCARDS,
            arguments = listOf(
                navArgument("subject") {
                    type = NavType.StringType
                },
                navArgument("chapterId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->

            val subject =
                backStackEntry.arguments?.getString("subject")
                    ?: ""

            val chapterId =
                backStackEntry.arguments?.getInt("chapterId")
                    ?: 1

            FlashcardsScreen(
                subject = subject,
                chapterId = chapterId,
                onBackToHome = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.HOME) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(
            route = Routes.TRUE_FALSE,
            arguments = listOf(
                navArgument("subject") {
                    type = NavType.StringType
                },
                navArgument("chapterId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->

            val subject =
                backStackEntry.arguments?.getString("subject")
                    ?: ""

            val chapterId =
                backStackEntry.arguments?.getInt("chapterId")
                    ?: 1

            TrueFalseScreen(
                subject = subject,
                chapterId = chapterId,
                onBackToHome = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.HOME) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(
            route = Routes.EXERCISES,
            arguments = listOf(
                navArgument("subject") {
                    type = NavType.StringType
                },
                navArgument("chapterId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->

            val subject =
                backStackEntry.arguments?.getString("subject")
                    ?: ""

            val chapterId =
                backStackEntry.arguments?.getInt("chapterId")
                    ?: 1

            ExercisesScreen(
                subject = subject,
                chapterId = chapterId,
                onBackToHome = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.HOME) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(Routes.REMINDERS) {
            ReminderScreen(
                onBackToHome = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.HOME) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}