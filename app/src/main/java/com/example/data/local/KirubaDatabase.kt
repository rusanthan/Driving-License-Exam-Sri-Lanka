package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.data.model.EnrollmentInquiry
import com.example.data.model.MockExamResult
import com.example.data.model.StudentMilestone
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [StudentMilestone::class, MockExamResult::class, EnrollmentInquiry::class],
    version = 1,
    exportSchema = false
)
abstract class KirubaDatabase : RoomDatabase() {

    abstract fun kirubaDao(): KirubaDao

    companion object {
        @Volatile
        private var INSTANCE: KirubaDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): KirubaDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    KirubaDatabase::class.java,
                    "kiruba_learners_database"
                )
                    .addCallback(KirubaDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class KirubaDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateInitialMilestones(database.kirubaDao())
                    }
                }
            }

            private suspend fun populateInitialMilestones(dao: KirubaDao) {
                val defaultMilestones = listOf(
                    StudentMilestone(
                        id = "m01",
                        stageName = "Stage 1: Documentation",
                        title = "National Transport Medical (NTMI) Certificate Passed",
                        isCompleted = true,
                        completionDate = "Verified",
                        notes = "Completed eye check, blood pressure and physical fitness at NTMI."
                    ),
                    StudentMilestone(
                        id = "m02",
                        stageName = "Stage 1: Documentation",
                        title = "DMT Registration & Learner Permit (L-Plate)",
                        isCompleted = true,
                        completionDate = "Permit Active",
                        notes = "Official Learner Permit valid for 18 months."
                    ),
                    StudentMilestone(
                        id = "m03",
                        stageName = "Stage 2: Theory Prep",
                        title = "Kiruba Learners Highway Code & Road Signs Classes",
                        isCompleted = true,
                        completionDate = "Classes Done",
                        notes = "Attended comprehensive lecture and road sign review sessions."
                    ),
                    StudentMilestone(
                        id = "m04",
                        stageName = "Stage 2: Theory Prep",
                        title = "DMT Computerized Theory Exam Passed",
                        isCompleted = false,
                        notes = "Score at least 30 out of 40 to qualify for practical trial."
                    ),
                    StudentMilestone(
                        id = "m05",
                        stageName = "Stage 3: Yard Training",
                        title = "Clutch Control, Cockpit Drill & Smooth Starting",
                        isCompleted = false,
                        notes = "Learn biting point, accelerator balance, and dashboard instruments."
                    ),
                    StudentMilestone(
                        id = "m06",
                        stageName = "Stage 3: Yard Training",
                        title = "Three-Point Turn & Tight U-Turn Maneuvering",
                        isCompleted = false,
                        notes = "Yard maneuver with 360-degree blind spot observation."
                    ),
                    StudentMilestone(
                        id = "m07",
                        stageName = "Stage 3: Yard Training",
                        title = "Reverse L-Shape & Parallel Bay Parking",
                        isCompleted = false,
                        notes = "Reverse steering techniques using mirrors and reference points."
                    ),
                    StudentMilestone(
                        id = "m08",
                        stageName = "Stage 3: Yard Training",
                        title = "Hill Start Mastery (Handbrake & Zero Rollback)",
                        isCompleted = false,
                        notes = "Essential practical test maneuver on steep incline."
                    ),
                    StudentMilestone(
                        id = "m09",
                        stageName = "Stage 4: Road Driving",
                        title = "City Traffic Driving & Roundabout Navigation",
                        isCompleted = false,
                        notes = "Jaffna/Vavuniya town road training with instructor guidance."
                    ),
                    StudentMilestone(
                        id = "m10",
                        stageName = "Stage 4: Road Driving",
                        title = "A9 Highway & Night Defensive Driving",
                        isCompleted = false,
                        notes = "High-speed lane discipline, overtaking, and headlight control."
                    ),
                    StudentMilestone(
                        id = "m11",
                        stageName = "Stage 5: Official Trial",
                        title = "Kiruba Learners Mock Practical Assessment",
                        isCompleted = false,
                        notes = "Simulated trial under official examiner conditions."
                    ),
                    StudentMilestone(
                        id = "m12",
                        stageName = "Stage 5: Official Trial",
                        title = "Final DMT Practical Driving Test Passed",
                        isCompleted = false,
                        notes = "Obtain temporary driving certificate followed by Smart Card License."
                    )
                )
                dao.insertMilestones(defaultMilestones)
            }
        }
    }
}
