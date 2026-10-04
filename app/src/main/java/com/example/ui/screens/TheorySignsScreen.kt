package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.RoadSignItem
import com.example.data.model.SignCategory
import com.example.ui.components.RoadSignVisual
import com.example.ui.theme.RoyalBluePrimary
import com.example.ui.theme.SafetyAmber
import com.example.ui.theme.SafetyGold
import com.example.ui.theme.TrafficGreen
import com.example.ui.viewmodel.ExamState
import com.example.ui.viewmodel.KirubaViewModel
import com.example.ui.viewmodel.TheorySubTab

@Composable
fun TheorySignsScreen(
    viewModel: KirubaViewModel,
    modifier: Modifier = Modifier
) {
    val activeSubTab by viewModel.theorySubTab.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("theory_signs_screen")
    ) {
        // Sub-tabs Row
        TabRow(
            selectedTabIndex = activeSubTab.ordinal,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = RoyalBluePrimary
        ) {
            TheorySubTab.values().forEach { tab ->
                Tab(
                    selected = activeSubTab == tab,
                    onClick = { viewModel.setTheorySubTab(tab) },
                    text = {
                        Text(
                            text = tab.label,
                            fontWeight = if (activeSubTab == tab) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                )
            }
        }

        when (activeSubTab) {
            TheorySubTab.MOCK_EXAM -> MockExamContent(viewModel)
            TheorySubTab.ROAD_SIGNS -> RoadSignsContent(viewModel)
            TheorySubTab.LICENSE_STEPS -> LicenseStepsContent(viewModel)
        }
    }
}

@Composable
fun MockExamContent(viewModel: KirubaViewModel) {
    val examState by viewModel.examState.collectAsState()
    val history by viewModel.examHistory.collectAsState()

    if (!examState.isStarted) {
        // Welcome / Start Screen
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(CircleShape)
                                .background(RoyalBluePrimary.copy(alpha = 0.12f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Quiz,
                                contentDescription = null,
                                tint = RoyalBluePrimary,
                                modifier = Modifier.size(32.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Sri Lanka DMT Theory Mock Exam",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "இலங்கை சாரதி அனுமதிப்பத்திர மாதிரிப் பரீட்சை",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            ),
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(16.dp))
                        HorizontalDivider()
                        Spacer(modifier = Modifier.height(16.dp))

                        val rules = listOf(
                            "10 randomized questions based on official Sri Lanka DMT syllabus",
                            "Covers Highway Code, Priority Rules, Speed Limits & Road Signs",
                            "Bilingual questions in English & Tamil",
                            "Passing score is 70% (7 out of 10 correct answers)"
                        )

                        rules.forEach { rule ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = null,
                                    tint = TrafficGreen,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = rule,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))
                        Button(
                            onClick = { viewModel.startExam() },
                            colors = ButtonDefaults.buttonColors(containerColor = RoyalBluePrimary),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .testTag("start_mock_exam_button")
                        ) {
                            Icon(Icons.Default.PlayArrow, contentDescription = null)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Start Mock Test Now", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            if (history.isNotEmpty()) {
                item {
                    Text(
                        text = "Your Recent Practice Attempts",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                }

                items(history.take(5)) { record ->
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(CircleShape)
                                        .background(
                                            if (record.passed) TrafficGreen.copy(alpha = 0.15f)
                                            else Color(0xFFFFEBEE)
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = if (record.passed) Icons.Default.Check else Icons.Default.Close,
                                        contentDescription = null,
                                        tint = if (record.passed) TrafficGreen else Color(0xFFD32F2F),
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(
                                        text = if (record.passed) "PASSED (${record.percentage}%)" else "NEEDS PRACTICE (${record.percentage}%)",
                                        style = MaterialTheme.typography.labelMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = if (record.passed) TrafficGreen else Color(0xFFD32F2F)
                                        )
                                    )
                                    Text(
                                        text = "${record.score} of ${record.totalQuestions} correct",
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    )
                                }
                            }

                            Text(
                                text = "DMT Standard",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            )
                        }
                    }
                }
            }
        }
    } else if (examState.isFinished) {
        // Result Screen with Review
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (examState.passed) Color(0xFFE8F5E9) else Color(0xFFFFEBEE)
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = if (examState.passed) Icons.Default.EmojiEvents else Icons.Default.SentimentDissatisfied,
                            contentDescription = null,
                            tint = if (examState.passed) TrafficGreen else Color(0xFFD32F2F),
                            modifier = Modifier.size(54.dp)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = if (examState.passed) "Congratulations! You Passed!" else "Practice More to Pass",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = if (examState.passed) TrafficGreen else Color(0xFFD32F2F)
                            ),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "Score: ${examState.score} / ${examState.total} (${examState.percentage}%)",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = if (examState.passed)
                                "Excellent job! You are well-prepared for the Sri Lanka DMT theory test."
                            else
                                "Kiruba Learners students aim for 75%+ to pass on their first trial. Review the answers below and try again!",
                            style = MaterialTheme.typography.bodySmall,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { viewModel.startExam() },
                            colors = ButtonDefaults.buttonColors(containerColor = RoyalBluePrimary)
                        ) {
                            Text("Retake Test")
                        }
                    }
                }
            }

            item {
                Text(
                    text = "Question Review & Answers",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
            }

            items(examState.questions) { q ->
                val selected = examState.selectedAnswers[q.id]
                val isCorrect = selected == q.correctIndex

                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(bottom = 6.dp)
                        ) {
                            Icon(
                                imageVector = if (isCorrect) Icons.Default.CheckCircle else Icons.Default.Cancel,
                                contentDescription = null,
                                tint = if (isCorrect) TrafficGreen else Color(0xFFD32F2F),
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (isCorrect) "Correct Answer" else "Incorrect Answer",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = if (isCorrect) TrafficGreen else Color(0xFFD32F2F)
                                )
                            )
                        }

                        Text(
                            text = q.question,
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                        )
                        Text(
                            text = q.tamilQuestion,
                            style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Correct Answer: ${q.options.getOrNull(q.correctIndex) ?: ""}",
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = TrafficGreen
                            )
                        )

                        if (!isCorrect && selected != null) {
                            Text(
                                text = "You Selected: ${q.options.getOrNull(selected) ?: ""}",
                                style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFFD32F2F))
                            )
                        }

                        Spacer(modifier = Modifier.height(4.dp))
                        Surface(
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Explanation: ${q.explanation}",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                ),
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }
                }
            }
        }
    } else {
        // Active Question Taking Screen
        val currentQ = examState.questions.getOrNull(examState.currentQuestionIndex)
        if (currentQ != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Progress Bar & Counter
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Question ${examState.currentQuestionIndex + 1} of ${examState.questions.size}",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = RoyalBluePrimary
                        )
                    )
                    Text(
                        text = currentQ.category,
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))
                LinearProgressIndicator(
                    progress = { (examState.currentQuestionIndex + 1).toFloat() / examState.questions.size.toFloat() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp)),
                    color = RoyalBluePrimary,
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Question Card
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = currentQ.question,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                lineHeight = 22.sp
                            )
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = currentQ.tamilQuestion,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Options List
                val selectedOption = examState.selectedAnswers[currentQ.id]
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(currentQ.options.size) { index ->
                        val isSelected = selectedOption == index
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (isSelected) RoyalBluePrimary.copy(alpha = 0.1f)
                                else MaterialTheme.colorScheme.surface
                            ),
                            border = if (isSelected) androidx.compose.foundation.BorderStroke(2.dp, RoyalBluePrimary)
                            else null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { viewModel.selectAnswer(currentQ.id, index) }
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(14.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = isSelected,
                                    onClick = { viewModel.selectAnswer(currentQ.id, index) }
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = currentQ.options[index],
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                    )
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Navigation Controls
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OutlinedButton(
                        onClick = { viewModel.prevQuestion() },
                        enabled = examState.currentQuestionIndex > 0
                    ) {
                        Text("Previous")
                    }

                    Button(
                        onClick = { viewModel.nextQuestion() },
                        colors = ButtonDefaults.buttonColors(containerColor = RoyalBluePrimary)
                    ) {
                        Text(
                            if (examState.currentQuestionIndex < examState.questions.size - 1) "Next"
                            else "Finish & Score"
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RoadSignsContent(viewModel: KirubaViewModel) {
    val selectedFilter by viewModel.signFilter.collectAsState()
    val allSigns = viewModel.roadSigns
    val filteredSigns = remember(selectedFilter, allSigns) {
        if (selectedFilter == null) allSigns else allSigns.filter { it.category == selectedFilter }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Filter row
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                FilterChip(
                    selected = selectedFilter == null,
                    onClick = { viewModel.setSignFilter(null) },
                    label = { Text("All Signs") },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = RoyalBluePrimary,
                        selectedLabelColor = Color.White
                    )
                )
            }
            items(SignCategory.values()) { cat ->
                FilterChip(
                    selected = selectedFilter == cat,
                    onClick = { viewModel.setSignFilter(cat) },
                    label = { Text(cat.label) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = RoyalBluePrimary,
                        selectedLabelColor = Color.White
                    )
                )
            }
        }

        // Road Signs Grid/List
        LazyColumn(
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 96.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(filteredSigns) { sign ->
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        RoadSignVisual(sign = sign, sizeDp = 58)
                        Spacer(modifier = Modifier.width(14.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Surface(
                                color = Color(sign.primaryColorHex).copy(alpha = 0.12f),
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Text(
                                    text = sign.category.label,
                                    color = Color(sign.primaryColorHex),
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 10.sp
                                    ),
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = sign.name,
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                            )
                            Text(
                                text = sign.tamilName,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = sign.description,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Rule: ${sign.rule}",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    color = RoyalBluePrimary
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LicenseStepsContent(viewModel: KirubaViewModel) {
    val steps = viewModel.licenseSteps

    LazyColumn(
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 96.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            Text(
                text = "How to Get Your Sri Lanka Driving License",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                text = "Complete 5-step roadmap supported by Kiruba Learners instructors",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }

        items(steps) { step ->
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(RoyalBluePrimary),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "${step.stepNumber}",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }

                        Column {
                            Text(
                                text = step.title,
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                            )
                            Text(
                                text = step.tamilTitle,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = step.subtitle,
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = RoyalBluePrimary,
                            fontWeight = FontWeight.SemiBold
                        )
                    )

                    Spacer(modifier = Modifier.height(6.dp))
                    step.details.forEach { detail ->
                        Row(
                            modifier = Modifier.padding(vertical = 2.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Text(
                                text = "• ",
                                color = RoyalBluePrimary,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = detail,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Surface(
                        color = Color(0xFFFFF8E1),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Lightbulb,
                                contentDescription = null,
                                tint = SafetyAmber,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = step.tips,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = Color(0xFF5D4037)
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}
