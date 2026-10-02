package data.repository

import data.model.Exercise
import data.model.Flashcard
import data.model.Question
import data.model.TrueFalseQuestion
import data.model.Subject
import data.model.Course
import data.model.Chapter
import data.model.StudySession
import data.model.RevisionReminder
import android.content.Context
import com.studsty.app.data.database.DatabaseProvider
import com.studsty.app.data.database.StudySessionEntity
import com.studsty.app.data.database.RevisionReminderEntity

class StudstyRepository(
    private val context: Context? = null
) {

    private val database =
        context?.let {
            DatabaseProvider.getDatabase(it)
        }

    companion object {


    }

    private val subjects = listOf(
        Subject(
            id = 1,
            name = "Algorithmique"
        ),
        Subject(
            id = 2,
            name = "Bases de données"
        ),
        Subject(
            id = 3,
            name = "Génie logiciel"
        ),
        Subject(
            id = 4,
            name = "Développement mobile"
        )
    )

    private val courses = listOf(
        Course(
            id = 1,
            name = "Algorithmique",
            level = "Niveau 2"
        ),
        Course(
            id = 2,
            name = "Bases de données",
            level = "Niveau 2"
        ),
        Course(
            id = 3,
            name = "Génie logiciel",
            level = "Niveau 2"
        ),
        Course(
            id = 4,
            name = "Développement mobile",
            level = "Niveau 2"
        )
    )

    private val chapters = listOf(

        // Algorithmique
        Chapter(
            id = 1,
            courseId = 1,
            title = "Introduction à l'algorithmique"
        ),
        Chapter(
            id = 5,
            courseId = 1,
            title = "Variables et types de données"
        ),
        Chapter(
            id = 6,
            courseId = 1,
            title = "Conditions"
        ),
        Chapter(
            id = 7,
            courseId = 1,
            title = "Boucles"
        ),
        Chapter(
            id = 8,
            courseId = 1,
            title = "Fonctions"
        ),

        // Bases de données
        Chapter(
            id = 2,
            courseId = 2,
            title = "Introduction aux bases de données"
        ),
        Chapter(
            id = 9,
            courseId = 2,
            title = "Modèle relationnel"
        ),
        Chapter(
            id = 10,
            courseId = 2,
            title = "SQL"
        ),
        Chapter(
            id = 11,
            courseId = 2,
            title = "Clés et contraintes"
        ),

        // Génie logiciel
        Chapter(
            id = 3,
            courseId = 3,
            title = "Introduction au génie logiciel"
        ),
        Chapter(
            id = 12,
            courseId = 3,
            title = "Cycle de vie logiciel"
        ),
        Chapter(
            id = 13,
            courseId = 3,
            title = "Méthodes Agile"
        ),
        Chapter(
            id = 14,
            courseId = 3,
            title = "UML"
        ),

        // Développement mobile
        Chapter(
            id = 4,
            courseId = 4,
            title = "Introduction au développement mobile"
        ),
        Chapter(
            id = 15,
            courseId = 4,
            title = "Android et Kotlin"
        ),
        Chapter(
            id = 16,
            courseId = 4,
            title = "Jetpack Compose"
        ),
        Chapter(
            id = 17,
            courseId = 4,
            title = "Architecture d'une application Android"
        )
    )

    private val questions = listOf(

        // Algorithmique - Introduction
        Question(
            id = 1,
            chapterId = 1,
            subject = "Algorithmique",
            question = "Quelle structure permet de répéter une instruction ?",
            options = listOf(
                "Une boucle",
                "Une variable",
                "Une classe",
                "Une fonction"
            ),
            correctAnswer = 0
        ),

        Question(
            id = 2,
            chapterId = 1,
            subject = "Algorithmique",
            question = "Quel mot-clé est utilisé pour une condition en Kotlin ?",
            options = listOf(
                "if",
                "loop",
                "repeat",
                "condition"
            ),
            correctAnswer = 0
        ),

        // Algorithmique - Variables et types
        Question(
            id = 3,
            chapterId = 5,
            subject = "Algorithmique",
            question = "Quelle structure permet de stocker une valeur qui peut changer ?",
            options = listOf(
                "Une variable",
                "Une boucle",
                "Une condition",
                "Une classe"
            ),
            correctAnswer = 0
        ),

        Question(
            id = 4,
            chapterId = 5,
            subject = "Algorithmique",
            question = "Lequel est un type de donnée entier ?",
            options = listOf(
                "Int",
                "String",
                "Boolean",
                "Double"
            ),
            correctAnswer = 0
        ),

        // Algorithmique - Conditions
        Question(
            id = 5,
            chapterId = 6,
            subject = "Algorithmique",
            question = "Quelle instruction permet d'exécuter du code selon une condition ?",
            options = listOf(
                "if",
                "for",
                "while",
                "class"
            ),
            correctAnswer = 0
        ),

        Question(
            id = 6,
            chapterId = 6,
            subject = "Algorithmique",
            question = "Quelle structure permet de gérer plusieurs conditions ?",
            options = listOf(
                "if / else if / else",
                "for",
                "while",
                "variable"
            ),
            correctAnswer = 0
        ),

        // Algorithmique - Boucles
        Question(
            id = 7,
            chapterId = 7,
            subject = "Algorithmique",
            question = "Quelle boucle est adaptée lorsqu'on connaît le nombre de répétitions ?",
            options = listOf(
                "for",
                "if",
                "when",
                "class"
            ),
            correctAnswer = 0
        ),

        Question(
            id = 8,
            chapterId = 7,
            subject = "Algorithmique",
            question = "Quelle boucle continue tant qu'une condition est vraie ?",
            options = listOf(
                "while",
                "if",
                "for",
                "when"
            ),
            correctAnswer = 0
        ),

        // Algorithmique - Fonctions
        Question(
            id = 9,
            chapterId = 8,
            subject = "Algorithmique",
            question = "Quel mot-clé permet de déclarer une fonction en Kotlin ?",
            options = listOf(
                "fun",
                "function",
                "def",
                "method"
            ),
            correctAnswer = 0
        ),

        Question(
            id = 10,
            chapterId = 8,
            subject = "Algorithmique",
            question = "Quel est l'objectif principal d'une fonction ?",
            options = listOf(
                "Regrouper des instructions réutilisables",
                "Créer uniquement des variables",
                "Arrêter le programme",
                "Créer une base de données"
            ),
            correctAnswer = 0
        ),

        // Bases de données - Introduction
        Question(
            id = 11,
            chapterId = 2,
            subject = "Bases de données",
            question = "Qu'est-ce qu'une base de données ?",
            options = listOf(
                "Un ensemble organisé de données",
                "Un langage de programmation",
                "Un système d'exploitation",
                "Un compilateur"
            ),
            correctAnswer = 0
        ),

        Question(
            id = 12,
            chapterId = 2,
            subject = "Bases de données",
            question = "Quel système permet de gérer une base de données ?",
            options = listOf(
                "Un SGBD",
                "Un navigateur",
                "Un antivirus",
                "Un compilateur"
            ),
            correctAnswer = 0
        ),

        // Bases de données - Modèle relationnel
        Question(
            id = 13,
            chapterId = 9,
            subject = "Bases de données",
            question = "Dans une base de données relationnelle, les données sont principalement organisées sous forme de :",
            options = listOf(
                "Tables",
                "Images",
                "Fichiers audio",
                "Programmes"
            ),
            correctAnswer = 0
        ),

        Question(
            id = 14,
            chapterId = 9,
            subject = "Bases de données",
            question = "Une ligne d'une table représente généralement :",
            options = listOf(
                "Un enregistrement",
                "Une base de données",
                "Une requête",
                "Une colonne"
            ),
            correctAnswer = 0
        ),

        // Bases de données - SQL
        Question(
            id = 15,
            chapterId = 10,
            subject = "Bases de données",
            question = "Quel langage est principalement utilisé pour interroger une base de données relationnelle ?",
            options = listOf(
                "SQL",
                "HTML",
                "CSS",
                "XML"
            ),
            correctAnswer = 0
        ),

        Question(
            id = 16,
            chapterId = 10,
            subject = "Bases de données",
            question = "Quelle commande SQL permet de récupérer des données ?",
            options = listOf(
                "SELECT",
                "DELETE",
                "DROP",
                "INSERT"
            ),
            correctAnswer = 0
        ),

        // Génie logiciel - Introduction
        Question(
            id = 17,
            chapterId = 3,
            subject = "Génie logiciel",
            question = "Quel est l'objectif principal du génie logiciel ?",
            options = listOf(
                "Développer des logiciels de manière organisée et fiable",
                "Créer uniquement des sites web",
                "Remplacer les bases de données",
                "Installer un système d'exploitation"
            ),
            correctAnswer = 0
        ),

        Question(
            id = 18,
            chapterId = 3,
            subject = "Génie logiciel",
            question = "Que représente UML ?",
            options = listOf(
                "Un langage de modélisation",
                "Un langage de programmation",
                "Une base de données",
                "Un système d'exploitation"
            ),
            correctAnswer = 0
        ),

        // Génie logiciel - Cycle de vie
        Question(
            id = 19,
            chapterId = 12,
            subject = "Génie logiciel",
            question = "Quelle étape consiste à identifier les besoins du client ?",
            options = listOf(
                "Analyse des besoins",
                "Compilation",
                "Installation",
                "Débogage uniquement"
            ),
            correctAnswer = 0
        ),

        Question(
            id = 20,
            chapterId = 12,
            subject = "Génie logiciel",
            question = "Le cycle de vie d'un logiciel décrit principalement :",
            options = listOf(
                "Les différentes étapes de développement du logiciel",
                "La vitesse du processeur",
                "La taille de l'écran",
                "La connexion Internet"
            ),
            correctAnswer = 0
        ),

        // Génie logiciel - Agile
        Question(
            id = 21,
            chapterId = 13,
            subject = "Génie logiciel",
            question = "Quelle méthode Agile utilise des sprints ?",
            options = listOf(
                "Scrum",
                "Waterfall",
                "Spiral",
                "RAD"
            ),
            correctAnswer = 0
        ),

        Question(
            id = 22,
            chapterId = 13,
            subject = "Génie logiciel",
            question = "Quelle est une caractéristique importante des méthodes Agiles ?",
            options = listOf(
                "L'adaptation aux changements",
                "L'absence totale de tests",
                "L'absence de communication",
                "Le développement sans client"
            ),
            correctAnswer = 0
        ),

        // Développement mobile - Introduction
        Question(
            id = 23,
            chapterId = 4,
            subject = "Développement mobile",
            question = "Quel système d'exploitation mobile est développé par Google ?",
            options = listOf(
                "Android",
                "Windows",
                "Ubuntu Desktop",
                "macOS"
            ),
            correctAnswer = 0
        ),

        Question(
            id = 24,
            chapterId = 4,
            subject = "Développement mobile",
            question = "Quel langage est officiellement utilisé pour le développement Android moderne ?",
            options = listOf(
                "Kotlin",
                "COBOL",
                "Fortran",
                "Pascal"
            ),
            correctAnswer = 0
        ),

        // Développement mobile - Android et Kotlin
        Question(
            id = 25,
            chapterId = 15,
            subject = "Développement mobile",
            question = "Quel outil permet principalement de développer des applications Android ?",
            options = listOf(
                "Android Studio",
                "Photoshop",
                "Excel",
                "PowerPoint"
            ),
            correctAnswer = 0
        ),

        Question(
            id = 26,
            chapterId = 15,
            subject = "Développement mobile",
            question = "Quelle technologie est utilisée pour construire l'interface de notre application Studsty ?",
            options = listOf(
                "Jetpack Compose",
                "HTML uniquement",
                "JavaFX",
                "Swing"
            ),
            correctAnswer = 0
        ),

        // Développement mobile - Jetpack Compose
        Question(
            id = 27,
            chapterId = 16,
            subject = "Développement mobile",
            question = "Quel élément permet de déclarer une fonction d'interface utilisateur en Jetpack Compose ?",
            options = listOf(
                "@Composable",
                "@Override",
                "@Database",
                "@Entity"
            ),
            correctAnswer = 0
        ),

        Question(
            id = 28,
            chapterId = 16,
            subject = "Développement mobile",
            question = "Quel composant permet d'organiser des éléments verticalement en Compose ?",
            options = listOf(
                "Column",
                "Row",
                "Box uniquement",
                "Table"
            ),
            correctAnswer = 0
        )
    )

    private val flashcards = listOf(

        // Algorithmique - Introduction
        Flashcard(
            id = 1,
            chapterId = 1,
            question = "Qu'est-ce qu'un algorithme ?",
            answer = "Un algorithme est une suite d'instructions permettant de résoudre un problème."
        ),
        Flashcard(
            id = 2,
            chapterId = 1,
            question = "Qu'est-ce qu'une boucle ?",
            answer = "Une boucle permet de répéter une ou plusieurs instructions."
        ),

        // Algorithmique - Variables
        Flashcard(
            id = 3,
            chapterId = 5,
            question = "Qu'est-ce qu'une variable ?",
            answer = "Une variable est un espace mémoire associé à un nom et contenant une valeur."
        ),
        Flashcard(
            id = 4,
            chapterId = 5,
            question = "Qu'est-ce qu'un type de donnée ?",
            answer = "Un type de donnée définit la nature des valeurs qu'une variable peut contenir."
        ),

        // Algorithmique - Conditions
        Flashcard(
            id = 5,
            chapterId = 6,
            question = "À quoi sert une condition ?",
            answer = "Une condition permet d'exécuter différentes instructions selon qu'une expression est vraie ou fausse."
        ),
        Flashcard(
            id = 6,
            chapterId = 6,
            question = "Quel mot-clé Kotlin permet de créer une condition ?",
            answer = "Le mot-clé if permet d'exécuter une instruction lorsqu'une condition est vraie."
        ),

        // Algorithmique - Boucles
        Flashcard(
            id = 7,
            chapterId = 7,
            question = "À quoi sert une boucle ?",
            answer = "Une boucle permet de répéter plusieurs fois une ou plusieurs instructions."
        ),
        Flashcard(
            id = 8,
            chapterId = 7,
            question = "Quelle boucle Kotlin utilise-t-on généralement lorsqu'on connaît le nombre de répétitions ?",
            answer = "La boucle for est généralement utilisée lorsqu'on connaît le nombre de répétitions."
        ),

        // Algorithmique - Fonctions
        Flashcard(
            id = 9,
            chapterId = 8,
            question = "Qu'est-ce qu'une fonction ?",
            answer = "Une fonction est un bloc d'instructions réutilisable permettant d'effectuer une tâche précise."
        ),
        Flashcard(
            id = 10,
            chapterId = 8,
            question = "Quel mot-clé permet de déclarer une fonction en Kotlin ?",
            answer = "Le mot-clé fun permet de déclarer une fonction en Kotlin."
        ),

        // Bases de données - Introduction
        Flashcard(
            id = 11,
            chapterId = 2,
            question = "Qu'est-ce qu'une base de données ?",
            answer = "Une base de données est un ensemble organisé de données pouvant être stockées et consultées."
        ),
        Flashcard(
            id = 12,
            chapterId = 2,
            question = "Qu'est-ce qu'un SGBD ?",
            answer = "Un SGBD est un système permettant de créer, gérer et manipuler des bases de données."
        ),

        // Bases de données - Modèle relationnel
        Flashcard(
            id = 13,
            chapterId = 9,
            question = "Qu'est-ce qu'une table relationnelle ?",
            answer = "Une table organise les données sous forme de lignes et de colonnes."
        ),
        Flashcard(
            id = 14,
            chapterId = 9,
            question = "Qu'est-ce qu'un enregistrement ?",
            answer = "Un enregistrement correspond généralement à une ligne d'une table."
        ),

        // Bases de données - SQL
        Flashcard(
            id = 15,
            chapterId = 10,
            question = "Qu'est-ce que SQL ?",
            answer = "SQL est un langage utilisé pour manipuler et interroger les bases de données relationnelles."
        ),
        Flashcard(
            id = 16,
            chapterId = 10,
            question = "À quoi sert SELECT ?",
            answer = "SELECT permet de récupérer des données dans une base de données."
        ),

        // Génie logiciel - Introduction
        Flashcard(
            id = 17,
            chapterId = 3,
            question = "Qu'est-ce que le génie logiciel ?",
            answer = "Le génie logiciel regroupe les méthodes et techniques permettant de développer et maintenir des logiciels de qualité."
        ),
        Flashcard(
            id = 18,
            chapterId = 3,
            question = "Qu'est-ce que UML ?",
            answer = "UML est un langage de modélisation utilisé pour représenter graphiquement un système logiciel."
        ),

        // Génie logiciel - Cycle de vie
        Flashcard(
            id = 19,
            chapterId = 12,
            question = "Qu'est-ce que le cycle de vie d'un logiciel ?",
            answer = "Il représente les différentes étapes nécessaires au développement et à la maintenance d'un logiciel."
        ),
        Flashcard(
            id = 20,
            chapterId = 12,
            question = "Quelle est une étape importante avant le développement ?",
            answer = "L'analyse des besoins permet de comprendre les attentes des utilisateurs et du client."
        ),

        // Génie logiciel - Agile
        Flashcard(
            id = 21,
            chapterId = 13,
            question = "Qu'est-ce qu'une méthode Agile ?",
            answer = "Une méthode Agile permet de développer progressivement un logiciel en favorisant l'adaptation aux changements."
        ),
        Flashcard(
            id = 22,
            chapterId = 13,
            question = "Qu'est-ce qu'un sprint dans Scrum ?",
            answer = "Un sprint est une période de développement pendant laquelle l'équipe réalise un ensemble de fonctionnalités."
        ),

        // Développement mobile - Introduction
        Flashcard(
            id = 23,
            chapterId = 4,
            question = "Qu'est-ce qu'une application mobile ?",
            answer = "Une application mobile est un logiciel conçu pour fonctionner sur un appareil mobile."
        ),
        Flashcard(
            id = 24,
            chapterId = 4,
            question = "Qu'est-ce qu'Android ?",
            answer = "Android est un système d'exploitation mobile principalement développé par Google."
        ),

        // Développement mobile - Android et Kotlin
        Flashcard(
            id = 25,
            chapterId = 15,
            question = "Quel langage est utilisé dans Studsty ?",
            answer = "Studsty utilise principalement le langage Kotlin."
        ),
        Flashcard(
            id = 26,
            chapterId = 15,
            question = "Quel environnement de développement utilisons-nous pour Studsty ?",
            answer = "Nous utilisons Android Studio pour développer Studsty."
        ),

        // Développement mobile - Jetpack Compose
        Flashcard(
            id = 27,
            chapterId = 16,
            question = "Qu'est-ce que Jetpack Compose ?",
            answer = "Jetpack Compose est un toolkit moderne permettant de créer des interfaces Android avec Kotlin."
        ),
        Flashcard(
            id = 28,
            chapterId = 16,
            question = "À quoi sert @Composable ?",
            answer = "@Composable indique qu'une fonction peut être utilisée pour construire une interface utilisateur avec Compose."
        )
    )

    private val trueFalseQuestions = listOf(

        // Algorithmique - Introduction
        TrueFalseQuestion(
            id = 1,
            chapterId = 1,
            question = "Une boucle permet de répéter une instruction.",
            correctAnswer = true
        ),
        TrueFalseQuestion(
            id = 2,
            chapterId = 1,
            question = "Un algorithme ne peut contenir qu'une seule instruction.",
            correctAnswer = false
        ),

        // Algorithmique - Variables
        TrueFalseQuestion(
            id = 3,
            chapterId = 5,
            question = "Une variable peut contenir une valeur.",
            correctAnswer = true
        ),
        TrueFalseQuestion(
            id = 4,
            chapterId = 5,
            question = "Une variable ne peut jamais changer de valeur.",
            correctAnswer = false
        ),

        // Algorithmique - Conditions
        TrueFalseQuestion(
            id = 5,
            chapterId = 6,
            question = "Une condition permet de choisir entre différentes instructions.",
            correctAnswer = true
        ),
        TrueFalseQuestion(
            id = 6,
            chapterId = 6,
            question = "L'instruction if permet de créer une condition en Kotlin.",
            correctAnswer = true
        ),

        // Algorithmique - Boucles
        TrueFalseQuestion(
            id = 7,
            chapterId = 7,
            question = "La boucle for peut être utilisée pour répéter des instructions.",
            correctAnswer = true
        ),
        TrueFalseQuestion(
            id = 8,
            chapterId = 7,
            question = "Une boucle while ne peut jamais dépendre d'une condition.",
            correctAnswer = false
        ),

        // Algorithmique - Fonctions
        TrueFalseQuestion(
            id = 9,
            chapterId = 8,
            question = "Une fonction permet de regrouper des instructions réutilisables.",
            correctAnswer = true
        ),
        TrueFalseQuestion(
            id = 10,
            chapterId = 8,
            question = "Le mot-clé fun permet de déclarer une fonction en Kotlin.",
            correctAnswer = true
        ),

        // Bases de données - Introduction
        TrueFalseQuestion(
            id = 11,
            chapterId = 2,
            question = "Une base de données permet de stocker des données de manière organisée.",
            correctAnswer = true
        ),
        TrueFalseQuestion(
            id = 12,
            chapterId = 2,
            question = "Un SGBD signifie Système de Gestion de Base de Données.",
            correctAnswer = true
        ),

        // Bases de données - Modèle relationnel
        TrueFalseQuestion(
            id = 13,
            chapterId = 9,
            question = "Une base de données relationnelle utilise des tables.",
            correctAnswer = true
        ),
        TrueFalseQuestion(
            id = 14,
            chapterId = 9,
            question = "Une ligne d'une table correspond généralement à une colonne.",
            correctAnswer = false
        ),

        // Bases de données - SQL
        TrueFalseQuestion(
            id = 15,
            chapterId = 10,
            question = "SQL permet d'interroger une base de données relationnelle.",
            correctAnswer = true
        ),
        TrueFalseQuestion(
            id = 16,
            chapterId = 10,
            question = "SELECT permet de récupérer des données.",
            correctAnswer = true
        ),

        // Génie logiciel - Introduction
        TrueFalseQuestion(
            id = 17,
            chapterId = 3,
            question = "Le génie logiciel concerne le développement et la maintenance des logiciels.",
            correctAnswer = true
        ),
        TrueFalseQuestion(
            id = 18,
            chapterId = 3,
            question = "UML est un système d'exploitation.",
            correctAnswer = false
        ),

        // Génie logiciel - Cycle de vie
        TrueFalseQuestion(
            id = 19,
            chapterId = 12,
            question = "Le cycle de vie décrit différentes étapes du développement d'un logiciel.",
            correctAnswer = true
        ),
        TrueFalseQuestion(
            id = 20,
            chapterId = 12,
            question = "L'analyse des besoins peut être réalisée avant le développement.",
            correctAnswer = true
        ),

        // Génie logiciel - Agile
        TrueFalseQuestion(
            id = 21,
            chapterId = 13,
            question = "Scrum est une méthode Agile.",
            correctAnswer = true
        ),
        TrueFalseQuestion(
            id = 22,
            chapterId = 13,
            question = "Un sprint est une période de développement dans Scrum.",
            correctAnswer = true
        ),

        // Développement mobile - Introduction
        TrueFalseQuestion(
            id = 23,
            chapterId = 4,
            question = "Android est un système d'exploitation mobile.",
            correctAnswer = true
        ),
        TrueFalseQuestion(
            id = 24,
            chapterId = 4,
            question = "Une application mobile est uniquement destinée aux ordinateurs de bureau.",
            correctAnswer = false
        ),

        // Développement mobile - Android et Kotlin
        TrueFalseQuestion(
            id = 25,
            chapterId = 15,
            question = "Kotlin peut être utilisé pour développer des applications Android.",
            correctAnswer = true
        ),
        TrueFalseQuestion(
            id = 26,
            chapterId = 15,
            question = "Android Studio est un environnement de développement pour Android.",
            correctAnswer = true
        ),

        // Développement mobile - Jetpack Compose
        TrueFalseQuestion(
            id = 27,
            chapterId = 16,
            question = "Jetpack Compose permet de créer des interfaces Android.",
            correctAnswer = true
        ),
        TrueFalseQuestion(
            id = 28,
            chapterId = 16,
            question = "@Composable est utilisé avec Jetpack Compose.",
            correctAnswer = true
        )
    )

    private val exercises = listOf(

        // Algorithmique - Introduction
        Exercise(
            id = 1,
            chapterId = 1,
            subject = "Algorithmique",
            statement = "Écrire un algorithme qui calcule la somme de deux nombres.",
            expectedAnswer = "Lire deux nombres puis calculer leur somme.",
            keywords = listOf(
                "deux nombres",
                "somme",
                "addition"
            )
        ),

        Exercise(
            id = 2,
            chapterId = 1,
            subject = "Algorithmique",
            statement = "Écrire un algorithme qui calcule la moyenne de trois nombres.",
            expectedAnswer = "Additionner les trois nombres puis diviser le résultat par 3.",
            keywords = listOf(
                "trois nombres",
                "addition",
                "somme",
                "divis",
                "3"
            )
        ),

        // Algorithmique - Variables
        Exercise(
            id = 3,
            chapterId = 5,
            subject = "Algorithmique",
            statement = "Déclarer une variable entière appelée age et lui affecter la valeur 20.",
            expectedAnswer = "Déclarer une variable entière age et lui affecter la valeur 20.",
            keywords = listOf(
                "variable",
                "entier",
                "age",
                "20"
            )
        ),

        Exercise(
            id = 4,
            chapterId = 5,
            subject = "Algorithmique",
            statement = "Écrire une instruction permettant de stocker le nombre 10 dans une variable.",
            expectedAnswer = "Créer une variable et lui affecter la valeur 10.",
            keywords = listOf(
                "variable",
                "10",
                "affecter"
            )
        ),

        // Algorithmique - Conditions
        Exercise(
            id = 5,
            chapterId = 6,
            subject = "Algorithmique",
            statement = "Écrire une condition qui affiche 'Majeur' lorsque l'âge est supérieur ou égal à 18.",
            expectedAnswer = "Utiliser une condition if avec age >= 18.",
            keywords = listOf(
                "if",
                "age",
                "18",
                "majeur"
            )
        ),

        Exercise(
            id = 6,
            chapterId = 6,
            subject = "Algorithmique",
            statement = "Écrire une condition qui vérifie si un nombre est positif.",
            expectedAnswer = "Vérifier si le nombre est supérieur ou égal à zéro.",
            keywords = listOf(
                "nombre",
                "positif",
                "supérieur",
                "zéro"
            )
        ),

        // Algorithmique - Boucles
        Exercise(
            id = 7,
            chapterId = 7,
            subject = "Algorithmique",
            statement = "Écrire une boucle qui affiche les nombres de 1 à 10.",
            expectedAnswer = "Utiliser une boucle for allant de 1 à 10.",
            keywords = listOf(
                "boucle",
                "for",
                "1",
                "10"
            )
        ),

        Exercise(
            id = 8,
            chapterId = 7,
            subject = "Algorithmique",
            statement = "Écrire une boucle qui affiche cinq fois le message Bonjour.",
            expectedAnswer = "Utiliser une boucle qui répète l'affichage de Bonjour cinq fois.",
            keywords = listOf(
                "boucle",
                "Bonjour",
                "cinq",
                "répéter"
            )
        ),

        // Algorithmique - Fonctions
        Exercise(
            id = 9,
            chapterId = 8,
            subject = "Algorithmique",
            statement = "Écrire une fonction qui retourne la somme de deux nombres.",
            expectedAnswer = "Créer une fonction prenant deux nombres et retournant leur somme.",
            keywords = listOf(
                "fonction",
                "deux nombres",
                "somme",
                "retourne"
            )
        ),

        Exercise(
            id = 10,
            chapterId = 8,
            subject = "Algorithmique",
            statement = "Écrire une fonction qui calcule le carré d'un nombre.",
            expectedAnswer = "Créer une fonction qui multiplie un nombre par lui-même.",
            keywords = listOf(
                "fonction",
                "carré",
                "nombre",
                "multiplie"
            )
        ),

        // Bases de données - Introduction
        Exercise(
            id = 11,
            chapterId = 2,
            subject = "Bases de données",
            statement = "Expliquer pourquoi une entreprise utilise une base de données.",
            expectedAnswer = "Une base de données permet de stocker, organiser et consulter les données.",
            keywords = listOf(
                "stocker",
                "organiser",
                "données"
            )
        ),

        Exercise(
            id = 12,
            chapterId = 2,
            subject = "Bases de données",
            statement = "Donner la définition d'un SGBD.",
            expectedAnswer = "Un SGBD est un système permettant de gérer une base de données.",
            keywords = listOf(
                "SGBD",
                "système",
                "gérer",
                "base de données"
            )
        ),

        // Bases de données - Modèle relationnel
        Exercise(
            id = 13,
            chapterId = 9,
            subject = "Bases de données",
            statement = "Expliquer la différence entre une ligne et une colonne dans une table.",
            expectedAnswer = "Une ligne représente un enregistrement tandis qu'une colonne représente un attribut.",
            keywords = listOf(
                "ligne",
                "enregistrement",
                "colonne",
                "attribut"
            )
        ),

        Exercise(
            id = 14,
            chapterId = 9,
            subject = "Bases de données",
            statement = "Créer une table Etudiant contenant un identifiant, un nom et un âge.",
            expectedAnswer = "Créer une table avec les colonnes id, nom et age.",
            keywords = listOf(
                "table",
                "Etudiant",
                "id",
                "nom",
                "age"
            )
        ),

        // Bases de données - SQL
        Exercise(
            id = 15,
            chapterId = 10,
            subject = "Bases de données",
            statement = "Écrire une requête SQL permettant de récupérer tous les étudiants.",
            expectedAnswer = "Utiliser SELECT * FROM Etudiant.",
            keywords = listOf(
                "SELECT",
                "FROM",
                "Etudiant"
            )
        ),

        Exercise(
            id = 16,
            chapterId = 10,
            subject = "Bases de données",
            statement = "Écrire une requête SQL permettant d'ajouter un étudiant.",
            expectedAnswer = "Utiliser la commande INSERT INTO.",
            keywords = listOf(
                "INSERT",
                "INTO",
                "étudiant"
            )
        ),

        // Génie logiciel - Introduction
        Exercise(
            id = 17,
            chapterId = 3,
            subject = "Génie logiciel",
            statement = "Expliquer ce qu'est le génie logiciel.",
            expectedAnswer = "Le génie logiciel regroupe les méthodes et techniques permettant de développer des logiciels de qualité.",
            keywords = listOf(
                "génie logiciel",
                "méthodes",
                "techniques",
                "logiciel"
            )
        ),

        Exercise(
            id = 18,
            chapterId = 3,
            subject = "Génie logiciel",
            statement = "Expliquer l'utilité d'UML.",
            expectedAnswer = "UML permet de modéliser et représenter graphiquement un système.",
            keywords = listOf(
                "UML",
                "modéliser",
                "système"
            )
        ),

        // Génie logiciel - Cycle de vie
        Exercise(
            id = 19,
            chapterId = 12,
            subject = "Génie logiciel",
            statement = "Citer trois étapes du cycle de vie d'un logiciel.",
            expectedAnswer = "Analyse des besoins, conception et développement.",
            keywords = listOf(
                "analyse",
                "conception",
                "développement"
            )
        ),

        Exercise(
            id = 20,
            chapterId = 12,
            subject = "Génie logiciel",
            statement = "Expliquer pourquoi l'analyse des besoins est importante.",
            expectedAnswer = "Elle permet de comprendre les besoins et attentes des utilisateurs.",
            keywords = listOf(
                "analyse",
                "besoins",
                "utilisateurs"
            )
        ),

        // Génie logiciel - Agile
        Exercise(
            id = 21,
            chapterId = 13,
            subject = "Génie logiciel",
            statement = "Expliquer ce qu'est un sprint dans Scrum.",
            expectedAnswer = "Un sprint est une période pendant laquelle une équipe réalise un ensemble de fonctionnalités.",
            keywords = listOf(
                "sprint",
                "Scrum",
                "équipe",
                "fonctionnalités"
            )
        ),

        Exercise(
            id = 22,
            chapterId = 13,
            subject = "Génie logiciel",
            statement = "Citer une caractéristique importante de la méthode Agile.",
            expectedAnswer = "L'adaptation aux changements et la collaboration avec le client.",
            keywords = listOf(
                "Agile",
                "changements",
                "collaboration"
            )
        ),

        // Développement mobile - Introduction
        Exercise(
            id = 23,
            chapterId = 4,
            subject = "Développement mobile",
            statement = "Expliquer ce qu'est une application mobile.",
            expectedAnswer = "Une application mobile est un logiciel conçu pour fonctionner sur un appareil mobile.",
            keywords = listOf(
                "application",
                "mobile",
                "logiciel"
            )
        ),

        Exercise(
            id = 24,
            chapterId = 4,
            subject = "Développement mobile",
            statement = "Citer deux systèmes d'exploitation mobiles.",
            expectedAnswer = "Android et iOS.",
            keywords = listOf(
                "Android",
                "iOS"
            )
        ),

        // Développement mobile - Android et Kotlin
        Exercise(
            id = 25,
            chapterId = 15,
            subject = "Développement mobile",
            statement = "Expliquer le rôle de Kotlin dans le développement Android.",
            expectedAnswer = "Kotlin est un langage utilisé pour développer des applications Android.",
            keywords = listOf(
                "Kotlin",
                "Android",
                "langage"
            )
        ),

        Exercise(
            id = 26,
            chapterId = 15,
            subject = "Développement mobile",
            statement = "Quel outil utilisons-nous pour développer Studsty ?",
            expectedAnswer = "Nous utilisons Android Studio.",
            keywords = listOf(
                "Android Studio",
                "Studsty"
            )
        ),

        // Développement mobile - Jetpack Compose
        Exercise(
            id = 27,
            chapterId = 16,
            subject = "Développement mobile",
            statement = "Expliquer le rôle de Jetpack Compose.",
            expectedAnswer = "Jetpack Compose permet de créer des interfaces utilisateur Android avec Kotlin.",
            keywords = listOf(
                "Jetpack Compose",
                "interface",
                "Android",
                "Kotlin"
            )
        ),

        Exercise(
            id = 28,
            chapterId = 16,
            subject = "Développement mobile",
            statement = "À quoi sert l'annotation @Composable ?",
            expectedAnswer = "Elle indique qu'une fonction peut construire une interface utilisateur avec Jetpack Compose.",
            keywords = listOf(
                "Composable",
                "fonction",
                "interface",
                "Compose"
            )
        )
    )

    suspend fun getQuestionsByChapter(
        chapterId: Int
    ): List<Question> {

        val entities = database?.quizQuestionDao()
            ?.getQuestionsByChapter(chapterId)
            ?: return emptyList()

        return entities.map { entity ->
            Question(
                id = entity.id,
                chapterId = entity.chapterId,
                subject = "",
                question = entity.question,
                options = listOf(
                    entity.optionA,
                    entity.optionB,
                    entity.optionC,
                    entity.optionD
                ),
                correctAnswer = entity.correctAnswer.toInt()
            )
        }
    }

    suspend fun getFlashcardsByChapter(
        chapterId: Int
    ): List<Flashcard> {

        val entities = database?.flashcardDao()
            ?.getFlashcardsByChapter(chapterId)
            ?: return emptyList()

        return entities.map { entity ->
            Flashcard(
                id = entity.id,
                chapterId = entity.chapterId,
                question = entity.question,
                answer = entity.answer
            )
        }
    }

    suspend fun getTrueFalseByChapter(
        chapterId: Int
    ): List<TrueFalseQuestion> {

        val entities = database?.trueFalseDao()
            ?.getQuestionsByChapter(chapterId)
            ?: return emptyList()

        return entities.map { entity ->
            TrueFalseQuestion(
                id = entity.id,
                chapterId = entity.chapterId,
                question = entity.statement,
                correctAnswer = entity.correctAnswer
            )
        }
    }

    suspend fun getExercisesByChapter(
        chapterId: Int,
        subject: String
    ): List<Exercise> {

        val entities = database?.exerciseDao()
            ?.getExercisesByChapter(chapterId)
            ?: return emptyList()

        return entities.map { entity ->
            Exercise(
                id = entity.id,
                chapterId = entity.chapterId,
                subject = subject,
                statement = entity.statement,
                expectedAnswer = entity.expectedAnswer,
                keywords = entity.keywords
                    .split("|")
                    .filter { it.isNotBlank() }
            )
        }
    }

    fun getSubjects(): List<Subject> {
        return subjects
    }
    fun getCourses(): List<Course> {
        return courses
    }

    fun getChaptersByCourse(courseId: Int): List<Chapter> {
        return chapters
            .filter { it.courseId == courseId }
            .sortedBy { it.id }
    }

    fun getChapterById(chapterId: Int): Chapter? {
        return chapters.find {
            it.id == chapterId
        }
    }

    fun getCourseBySubject(subjectName: String): Course? {
        return courses.find {
            it.name == subjectName
        }
    }
    fun getFirstChapterBySubject(subjectName: String): Chapter? {
        val course = getCourseBySubject(subjectName)

        return if (course != null) {
            chapters.find {
                it.courseId == course.id
            }
        } else {
            null
        }
    }
    suspend fun saveStudySession(
        session: StudySession
    ) {
        database?.studySessionDao()?.insertSession(
            StudySessionEntity(
                id = session.id,
                chapterId = session.chapterId,
                contentType = session.type,
                score = session.score,
                totalQuestions = session.total,
                date = session.date
            )
        )
    }

    suspend fun getStudySessions(): List<StudySession> {

        val entities = database
            ?.studySessionDao()
            ?.getAllSessions()
            ?: return emptyList()

        val subjects = database
            ?.subjectDao()
            ?.getAllSubjects()
            ?: emptyList()

        val chapterSubjects = subjects
            .flatMap { subject ->
                database
                    ?.chapterDao()
                    ?.getChaptersBySubject(subject.id)
                    ?.map { chapter ->
                        chapter.id to subject.name
                    }
                    ?: emptyList()
            }
            .toMap()

        return entities.map { entity ->
            StudySession(
                id = entity.id,
                chapterId = entity.chapterId,
                subject = chapterSubjects[entity.chapterId] ?: "",
                type = entity.contentType,
                score = entity.score,
                total = entity.totalQuestions,
                date = entity.date
            )
        }
    }
    suspend fun saveRevisionReminder(
        reminder: RevisionReminder
    ) {
        database?.revisionReminderDao()?.insertReminder(
            RevisionReminderEntity(
                id = reminder.id,
                subject = reminder.subject,
                date = reminder.date,
                enabled = reminder.enabled
            )
        )
    }

    suspend fun getRevisionReminders(): List<RevisionReminder> {

        val entities = database
            ?.revisionReminderDao()
            ?.getAllReminders()
            ?: return emptyList()

        return entities.map { entity ->
            RevisionReminder(
                id = entity.id,
                subject = entity.subject,
                date = entity.date,
                enabled = entity.enabled
            )
        }
    }

    fun getAllQuestions(): List<Question> {
        return questions
    }

    fun getAllFlashcards(): List<Flashcard> {
        return flashcards
    }

    fun getAllTrueFalseQuestions(): List<TrueFalseQuestion> {
        return trueFalseQuestions
    }

    fun getAllExercises(): List<Exercise> {
        return exercises
    }

    fun getAllChapters(): List<Chapter> {
        return chapters
    }

}
