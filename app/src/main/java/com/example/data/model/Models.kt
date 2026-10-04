package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Vehicle training course offered by Kiruba Learners
 */
data class Course(
    val id: String,
    val title: String,
    val tamilTitle: String,
    val category: CourseCategory,
    val description: String,
    val duration: String,
    val vehicleClasses: String, // e.g. "Class B (Dual Control Cars)"
    val transmission: String,   // "Manual & Automatic"
    val highlights: List<String>,
    val syllabus: List<String>,
    val badge: String? = null
)

enum class CourseCategory(val label: String) {
    ALL("All Courses"),
    LIGHT_VEHICLE("Car (Auto/Manual)"),
    HEAVY_VEHICLE("Heavy Bus & Lorry"),
    TWO_THREE_WHEEL("Motorcycle & Tuk-Tuk"),
    SPECIALIZED("Refresher & Defensive")
}

/**
 * Road sign entity for Sri Lanka Highway Code
 */
enum class SignCategory(val label: String) {
    REGULATORY("Mandatory / Regulatory"),
    WARNING("Danger Warning"),
    INFORMATIVE("Informative & Guidance"),
    MARKINGS("Road Markings")
}

data class RoadSignItem(
    val id: String,
    val name: String,
    val tamilName: String,
    val category: SignCategory,
    val description: String,
    val rule: String,
    val shape: String, // "CIRCLE", "TRIANGLE", "OCTAGON", "RECTANGLE"
    val primaryColorHex: Long = 0xFFD32F2F // Default Red
)

/**
 * Driving license theory test question modeled after Sri Lanka DMT tests
 */
data class TheoryQuestion(
    val id: Int,
    val question: String,
    val tamilQuestion: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String,
    val category: String
)

/**
 * Official Step to get a Sri Lankan Driving License through Kiruba Learners
 */
data class LicenseStep(
    val stepNumber: Int,
    val title: String,
    val tamilTitle: String,
    val subtitle: String,
    val details: List<String>,
    val tips: String
)

/**
 * Branch location info
 */
data class BranchLocation(
    val id: String,
    val name: String,
    val tamilName: String,
    val address: String,
    val landmark: String,
    val phoneNumbers: List<String>,
    val email: String,
    val openingHours: String,
    val mapQuery: String
)

/**
 * Student Training Milestone stored in Room Database
 */
@Entity(tableName = "student_milestones")
data class StudentMilestone(
    @PrimaryKey val id: String,
    val title: String,
    val stageName: String,
    val isCompleted: Boolean = false,
    val completionDate: String? = null,
    val notes: String = ""
)

/**
 * Past Mock Exam Results stored in Room Database
 */
@Entity(tableName = "mock_exam_results")
data class MockExamResult(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val timestamp: Long = System.currentTimeMillis(),
    val score: Int,
    val totalQuestions: Int,
    val passed: Boolean,
    val percentage: Int
)

/**
 * Enrollment or Inquiry request submitted by learner
 */
@Entity(tableName = "enrollment_inquiries")
data class EnrollmentInquiry(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val studentName: String,
    val phone: String,
    val nicOrPassport: String,
    val preferredBranch: String,
    val courseTitle: String,
    val preferredTiming: String,
    val message: String = "",
    val timestamp: Long = System.currentTimeMillis()
)
