package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.KirubaDatabase
import com.example.data.model.*
import com.example.data.repository.KirubaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class AppTab(val title: String) {
    HOME("Home"),
    COURSES("Courses"),
    THEORY("Theory & Signs"),
    PROGRESS("My License"),
    BRANCHES("Branches")
}

enum class TheorySubTab(val label: String) {
    MOCK_EXAM("Mock Exam"),
    ROAD_SIGNS("Road Signs"),
    LICENSE_STEPS("License Steps")
}

data class ExamState(
    val isStarted: Boolean = false,
    val isFinished: Boolean = false,
    val currentQuestionIndex: Int = 0,
    val selectedAnswers: Map<Int, Int> = emptyMap(), // questionId to selected option index
    val score: Int = 0,
    val total: Int = 0,
    val percentage: Int = 0,
    val passed: Boolean = false,
    val questions: List<TheoryQuestion> = emptyList()
)

data class EnrollmentFormState(
    val isOpen: Boolean = false,
    val courseTitle: String = "",
    val studentName: String = "",
    val phone: String = "",
    val nicOrPassport: String = "",
    val preferredBranch: String = "Jaffna Head Office (Kachcheri Nallur)",
    val preferredTiming: String = "Morning (7:30 AM - 10:30 AM)",
    val message: String = "",
    val isSubmitting: Boolean = false,
    val isSuccess: Boolean = false
)

class KirubaViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: KirubaRepository

    init {
        val database = KirubaDatabase.getDatabase(application, viewModelScope)
        repository = KirubaRepository(database.kirubaDao())
    }

    // Active bottom navigation tab
    private val _currentTab = MutableStateFlow(AppTab.HOME)
    val currentTab: StateFlow<AppTab> = _currentTab.asStateFlow()

    // Course filters and selection
    private val _selectedCourseCategory = MutableStateFlow(CourseCategory.ALL)
    val selectedCourseCategory: StateFlow<CourseCategory> = _selectedCourseCategory.asStateFlow()

    private val _selectedCourseDetail = MutableStateFlow<Course?>(null)
    val selectedCourseDetail: StateFlow<Course?> = _selectedCourseDetail.asStateFlow()

    // Theory and road signs sub-tab
    private val _theorySubTab = MutableStateFlow(TheorySubTab.MOCK_EXAM)
    val theorySubTab: StateFlow<TheorySubTab> = _theorySubTab.asStateFlow()

    private val _signFilter = MutableStateFlow<SignCategory?>(null)
    val signFilter: StateFlow<SignCategory?> = _signFilter.asStateFlow()

    // Mock Exam State
    private val _examState = MutableStateFlow(ExamState())
    val examState: StateFlow<ExamState> = _examState.asStateFlow()

    // Enrollment Form
    private val _enrollmentForm = MutableStateFlow(EnrollmentFormState())
    val enrollmentForm: StateFlow<EnrollmentFormState> = _enrollmentForm.asStateFlow()

    // Room Database Flows
    val milestones: StateFlow<List<StudentMilestone>> = repository.allMilestones
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val examHistory: StateFlow<List<MockExamResult>> = repository.allExamResults
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val inquiryHistory: StateFlow<List<EnrollmentInquiry>> = repository.allInquiries
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Static collections from repository
    val courses: List<Course> = repository.getCourses()
    val allQuestions: List<TheoryQuestion> = repository.getTheoryQuestions()
    val roadSigns: List<RoadSignItem> = repository.getRoadSigns()
    val licenseSteps: List<LicenseStep> = repository.getLicenseSteps()
    val branches: List<BranchLocation> = repository.getBranches()

    fun setTab(tab: AppTab) {
        _currentTab.value = tab
    }

    fun setCourseCategory(category: CourseCategory) {
        _selectedCourseCategory.value = category
    }

    fun openCourseDetail(course: Course?) {
        _selectedCourseDetail.value = course
    }

    fun setTheorySubTab(subTab: TheorySubTab) {
        _theorySubTab.value = subTab
    }

    fun setSignFilter(filter: SignCategory?) {
        _signFilter.value = filter
    }

    // Exam Actions
    fun startExam() {
        val questions = allQuestions.shuffled().take(10) // 10 questions per quick test round
        _examState.value = ExamState(
            isStarted = true,
            isFinished = false,
            currentQuestionIndex = 0,
            selectedAnswers = emptyMap(),
            questions = questions,
            total = questions.size
        )
    }

    fun selectAnswer(questionId: Int, optionIndex: Int) {
        val currentAnswers = _examState.value.selectedAnswers.toMutableMap()
        currentAnswers[questionId] = optionIndex
        _examState.value = _examState.value.copy(selectedAnswers = currentAnswers)
    }

    fun nextQuestion() {
        val current = _examState.value
        if (current.currentQuestionIndex < current.questions.size - 1) {
            _examState.value = current.copy(currentQuestionIndex = current.currentQuestionIndex + 1)
        } else {
            finishExam()
        }
    }

    fun prevQuestion() {
        val current = _examState.value
        if (current.currentQuestionIndex > 0) {
            _examState.value = current.copy(currentQuestionIndex = current.currentQuestionIndex - 1)
        }
    }

    fun finishExam() {
        val current = _examState.value
        var correctCount = 0
        current.questions.forEach { q ->
            if (current.selectedAnswers[q.id] == q.correctIndex) {
                correctCount++
            }
        }
        val total = current.questions.size
        val percentage = if (total > 0) ((correctCount.toFloat() / total.toFloat()) * 100).toInt() else 0
        val passed = percentage >= 70

        _examState.value = current.copy(
            isFinished = true,
            score = correctCount,
            total = total,
            percentage = percentage,
            passed = passed
        )

        viewModelScope.launch {
            repository.saveExamResult(correctCount, total)
        }
    }

    fun resetExam() {
        _examState.value = ExamState()
    }

    // Milestones toggle
    fun toggleMilestone(id: String, completed: Boolean) {
        viewModelScope.launch {
            val date = if (completed) "Completed Today" else null
            repository.toggleMilestone(id, completed, date)
        }
    }

    // Enrollment Form Actions
    fun openEnrollmentForm(courseTitle: String = "") {
        _enrollmentForm.value = EnrollmentFormState(
            isOpen = true,
            courseTitle = courseTitle.ifBlank { "Beginner Car Course (Manual & Auto)" }
        )
    }

    fun closeEnrollmentForm() {
        _enrollmentForm.value = EnrollmentFormState(isOpen = false)
    }

    fun updateEnrollmentForm(
        studentName: String? = null,
        phone: String? = null,
        nicOrPassport: String? = null,
        preferredBranch: String? = null,
        preferredTiming: String? = null,
        courseTitle: String? = null,
        message: String? = null
    ) {
        val current = _enrollmentForm.value
        _enrollmentForm.value = current.copy(
            studentName = studentName ?: current.studentName,
            phone = phone ?: current.phone,
            nicOrPassport = nicOrPassport ?: current.nicOrPassport,
            preferredBranch = preferredBranch ?: current.preferredBranch,
            preferredTiming = preferredTiming ?: current.preferredTiming,
            courseTitle = courseTitle ?: current.courseTitle,
            message = message ?: current.message
        )
    }

    fun submitEnrollment() {
        val form = _enrollmentForm.value
        if (form.studentName.isBlank() || form.phone.isBlank()) {
            return
        }
        _enrollmentForm.value = form.copy(isSubmitting = true)

        viewModelScope.launch {
            val inquiry = EnrollmentInquiry(
                studentName = form.studentName,
                phone = form.phone,
                nicOrPassport = form.nicOrPassport,
                preferredBranch = form.preferredBranch,
                courseTitle = form.courseTitle,
                preferredTiming = form.preferredTiming,
                message = form.message
            )
            repository.submitInquiry(inquiry)
            _enrollmentForm.value = form.copy(
                isSubmitting = false,
                isSuccess = true
            )
        }
    }
}
