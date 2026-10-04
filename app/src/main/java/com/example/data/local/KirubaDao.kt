package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.EnrollmentInquiry
import com.example.data.model.MockExamResult
import com.example.data.model.StudentMilestone
import kotlinx.coroutines.flow.Flow

@Dao
interface KirubaDao {

    // Student Milestones
    @Query("SELECT * FROM student_milestones ORDER BY id ASC")
    fun getAllMilestones(): Flow<List<StudentMilestone>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMilestones(milestones: List<StudentMilestone>)

    @Update
    suspend fun updateMilestone(milestone: StudentMilestone)

    @Query("UPDATE student_milestones SET isCompleted = :isCompleted, completionDate = :completionDate WHERE id = :id")
    suspend fun toggleMilestone(id: String, isCompleted: Boolean, completionDate: String?)

    // Exam Results
    @Query("SELECT * FROM mock_exam_results ORDER BY timestamp DESC")
    fun getAllExamResults(): Flow<List<MockExamResult>>

    @Insert
    suspend fun insertExamResult(result: MockExamResult): Long

    // Inquiries / Enrollments
    @Query("SELECT * FROM enrollment_inquiries ORDER BY timestamp DESC")
    fun getAllInquiries(): Flow<List<EnrollmentInquiry>>

    @Insert
    suspend fun insertInquiry(inquiry: EnrollmentInquiry): Long
}
