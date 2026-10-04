package com.example.data.repository

import com.example.data.local.KirubaDao
import com.example.data.model.*
import kotlinx.coroutines.flow.Flow

class KirubaRepository(private val dao: KirubaDao) {

    val allMilestones: Flow<List<StudentMilestone>> = dao.getAllMilestones()
    val allExamResults: Flow<List<MockExamResult>> = dao.getAllExamResults()
    val allInquiries: Flow<List<EnrollmentInquiry>> = dao.getAllInquiries()

    suspend fun toggleMilestone(id: String, completed: Boolean, completionDate: String?) {
        dao.toggleMilestone(id, completed, completionDate)
    }

    suspend fun saveExamResult(score: Int, total: Int): Long {
        val percentage = ((score.toFloat() / total.toFloat()) * 100).toInt()
        val passed = percentage >= 75 // DMT standard 30/40 is 75%
        return dao.insertExamResult(
            MockExamResult(
                score = score,
                totalQuestions = total,
                passed = passed,
                percentage = percentage
            )
        )
    }

    suspend fun submitInquiry(inquiry: EnrollmentInquiry): Long {
        return dao.insertInquiry(inquiry)
    }

    fun getCourses(): List<Course> = listOf(
        Course(
            id = "c_beginner_car",
            title = "Beginner Car Course (Manual & Auto)",
            tamilTitle = "தொடக்க கார் பயிற்சி (Manual & Auto)",
            category = CourseCategory.LIGHT_VEHICLE,
            description = "Complete start-to-finish driving course with modern dual-control training cars. Designed for beginners to achieve 100% confidence and pass the DMT test on first attempt.",
            duration = "4 to 6 Weeks (Flexible schedules)",
            vehicleClasses = "Class B (Light Motor Vehicles up to 3500kg)",
            transmission = "Choice of Manual or Automatic Transmission",
            highlights = listOf(
                "Dual-control safety vehicles",
                "Dedicated yard training for Hill Start & Reverse",
                "Full DMT highway code lecture & practice materials",
                "Pre-trial mock evaluation before final test"
            ),
            syllabus = listOf(
                "Vehicle cockpit drill and mirror adjustments",
                "Clutch friction point mastery and gear transitions",
                "3-point turns, parallel parking, and reverse L-shape",
                "Incline hill start with zero rollback",
                "Roundabouts, traffic lights, and pedestrian crossings in Jaffna/Vavuniya"
            ),
            badge = "Most Popular"
        ),
        Course(
            id = "c_heavy_bus",
            title = "Heavy Vehicle & Bus Training",
            tamilTitle = "கனரக வாகனம் & பஸ் பயிற்சி",
            category = CourseCategory.HEAVY_VEHICLE,
            description = "Specialized heavy commercial vehicle training conducted with our modern Ashok Leyland buses and commercial trucks. Taught by certified senior instructors with decades of commercial experience.",
            duration = "6 to 8 Weeks",
            vehicleClasses = "Class D / DE (Heavy Passenger Bus) & Class C (Commercial Lorry)",
            transmission = "Manual Heavy Duty Transmission with Air Brakes",
            highlights = listOf(
                "Training on genuine Ashok Leyland bus",
                "Pneumatic air-brake operations and safety checks",
                "Long vehicle turning radius & blind spot handling",
                "Commercial passenger and cargo safety regulations"
            ),
            syllabus = listOf(
                "Heavy vehicle walk-around inspection and air pressure checks",
                "Dual clutch and synchronized heavy gear shifting",
                "Reverse straight line and bay docking with spotter signals",
                "Highway driving and hill navigation under loaded conditions",
                "Emergency procedures, breakdown safety, and brake failure response"
            ),
            badge = "Commercial Pro"
        ),
        Course(
            id = "c_motorcycle",
            title = "Motorcycle & Scooter Training",
            tamilTitle = "மோட்டார் சைக்கிள் & ஸ்கூட்டர் பயிற்சி",
            category = CourseCategory.TWO_THREE_WHEEL,
            description = "Comprehensive two-wheeler safety program covering balance, slalom maneuvering, emergency braking, and DMT trial figure-eight test preparation.",
            duration = "2 to 3 Weeks",
            vehicleClasses = "Class A (Motorcycles > 100cc) & Class A1 (Light Motorcycles / Scooters)",
            transmission = "Clutch Motorcycle & Automatic Gearless Scooter",
            highlights = listOf(
                "Mastery of the official DMT Figure-8 track",
                "Low-speed balance and slalom cone drills",
                "Emergency threshold braking techniques",
                "Protective gear standards and road hazard awareness"
            ),
            syllabus = listOf(
                "Mounting, kick/electric start, and balance controls",
                "Clutch friction zone in slow-speed traffic",
                "Figure-of-8 track mastery without touching ground",
                "Emergency stopping without locking front wheels",
                "Defensive riding in crowded town streets"
            )
        ),
        Course(
            id = "c_tuk_tuk",
            title = "Three-Wheeler (Tuk-Tuk) Course",
            tamilTitle = "முச்சக்கர வண்டி பயிற்சி (Three-Wheeler)",
            category = CourseCategory.TWO_THREE_WHEEL,
            description = "Essential training for personal and commercial three-wheeler driving in Sri Lanka. Focuses on safe cornering, passenger comfort, and narrow lane navigation.",
            duration = "2 to 3 Weeks",
            vehicleClasses = "Class B1 (Motor Tricycle / Three-Wheeler)",
            transmission = "Handlebar manual twist gear / automatic",
            highlights = listOf(
                "Center of gravity management on curves",
                "Safe passenger loading and unloading protocol",
                "Reverse maneuvering in narrow Sri Lankan streets",
                "Commercial passenger safety guidelines"
            ),
            syllabus = listOf(
                "Engine controls, handlebar steering, and clutch cable operation",
                "Reverse lever engagement and tight turning",
                "Negotiating speed bumps and uneven road surfaces",
                "Trial exam course preparation and test readiness"
            )
        ),
        Course(
            id = "c_defensive_refresher",
            title = "Defensive Driving & Refresher Program",
            tamilTitle = "பாதுகாப்பான ஓட்டுநர் & மீள்பயிற்சி",
            category = CourseCategory.SPECIALIZED,
            description = "Designed for license holders wanting to regain confidence, learn night driving, or master high-speed highway driving along the A9 route.",
            duration = "1 to 2 Weeks (Tailored sessions)",
            vehicleClasses = "Any Light or Heavy Vehicle Class",
            transmission = "Manual or Automatic",
            highlights = listOf(
                "Hazard perception and space cushion management",
                "Overcoming driving anxiety and town traffic fear",
                "Night driving and heavy monsoon rain driving",
                "Fuel-efficient eco-driving habits"
            ),
            syllabus = listOf(
                "Mirror-Signal-Maneuver (MSM) routine reinforcement",
                "Emergency brake reaction testing",
                "Overtaking etiquette and high-speed highway entry/exit",
                "Navigating complex junctions and multi-lane roundabouts"
            ),
            badge = "Confidence Booster"
        )
    )

    fun getTheoryQuestions(): List<TheoryQuestion> = listOf(
        TheoryQuestion(
            id = 1,
            question = "When approaching a pedestrian crossing (Zebra Crossing) where a pedestrian is waiting to cross, what should you do?",
            tamilQuestion = "பாதசாரி கடவையில் ஒருவர் கடக்க காத்திருக்கும் போது நீங்கள் என்ன செய்ய வேண்டும்?",
            options = listOf(
                "Sound your horn and accelerate through quickly",
                "Slow down, stop before the stop line, and give way to the pedestrian",
                "Overtake the car stopped in front of the crossing",
                "Flash your headlights to tell the pedestrian to hurry"
            ),
            correctIndex = 1,
            explanation = "In Sri Lanka, drivers must give right-of-way to pedestrians waiting or crossing at a zebra crossing. Overtaking near a zebra crossing is strictly illegal.",
            category = "Pedestrian Safety"
        ),
        TheoryQuestion(
            id = 2,
            question = "What is the general speed limit for light vehicles (Cars/Vans) in built-up / urban areas in Sri Lanka?",
            tamilQuestion = "இலங்கையில் நகர்ப்புறங்களில் இலகுரக வாகனங்களுக்கான வேக வரம்பு என்ன?",
            options = listOf(
                "70 km/h",
                "50 km/h",
                "30 km/h",
                "60 km/h"
            ),
            correctIndex = 1,
            explanation = "According to Sri Lanka Motor Traffic regulations, the standard speed limit for cars and motorcycles in urban/built-up areas is 50 km/h unless otherwise signed.",
            category = "Speed Limits"
        ),
        TheoryQuestion(
            id = 3,
            question = "When entering a roundabout in Sri Lanka (where traffic moves clockwise), who has the right of way?",
            tamilQuestion = "வட்டச்சுற்றுச் சந்தியில் (Roundabout) யாருக்கு முன்னுரிமை உண்டு?",
            options = listOf(
                "Vehicles entering the roundabout from the left",
                "Vehicles already circulating inside the roundabout coming from your right",
                "Heavy vehicles entering from any direction",
                "The fastest moving vehicle"
            ),
            correctIndex = 1,
            explanation = "In Sri Lanka, drive on the left; traffic inside the roundabout flows clockwise. You must give way to traffic approaching from your immediate right already in the circle.",
            category = "Right of Way"
        ),
        TheoryQuestion(
            id = 4,
            question = "What does a single solid continuous white line in the center of the road mean?",
            tamilQuestion = "வீதியின் நடுவில் உள்ள தொடர்ச்சியான ஒற்றை வெள்ளைக் கோடு எதனைக் குறிக்கிறது?",
            options = listOf(
                "You may overtake whenever you wish",
                "You must not cross or straddle the line to overtake",
                "Parking is allowed on both sides",
                "The road is closed to traffic"
            ),
            correctIndex = 1,
            explanation = "A solid continuous white center line strictly prohibits crossing or straddling to overtake. It is painted where overtaking is hazardous due to curves or poor visibility.",
            category = "Road Markings"
        ),
        TheoryQuestion(
            id = 5,
            question = "What is the minimum legal blood alcohol concentration (BAC) limit allowed for drivers in Sri Lanka?",
            tamilQuestion = "இலங்கையில் சாரதிகளுக்கு அனுமதிக்கப்பட்ட இரத்த மதுபான அளவு என்ன?",
            options = listOf(
                "0.08 grams per 100 ml (Strict Zero tolerance for commercial/learner)",
                "0.15 grams per 100 ml",
                "0.20 grams per 100 ml",
                "There is no legal limit as long as you drive safely"
            ),
            correctIndex = 0,
            explanation = "The legal limit in Sri Lanka is 0.08 g/100ml for regular drivers, and zero-tolerance is strictly enforced for learner drivers and public transport operators.",
            category = "Rules & Law"
        ),
        TheoryQuestion(
            id = 6,
            question = "What shape are mandatory/regulatory traffic signs in Sri Lanka (except Stop and Give Way)?",
            tamilQuestion = "இலங்கையில் கட்டாய போக்குவரத்து சமிஞ்சைகளின் வடிவம் என்ன?",
            options = listOf(
                "Circular (Round)",
                "Triangular",
                "Rectangular",
                "Diamond shaped"
            ),
            correctIndex = 0,
            explanation = "Mandatory/prohibitory signs are circular (e.g. red circular borders). Warning signs are triangular, and informational signs are rectangular.",
            category = "Road Signs"
        ),
        TheoryQuestion(
            id = 7,
            question = "When driving at night, when should you switch your headlights from high beam to low (dipped) beam?",
            tamilQuestion = "இரவில் வாகனம் செலுத்தும் போது எப்போது பிரகாசமான விளக்கிலிருந்து மங்கலான விளக்கிற்கு மாற்ற வேண்டும்?",
            options = listOf(
                "Only when entering a petrol shed",
                "When following another vehicle closely or when oncoming vehicles approach within 200 meters",
                "Only if the oncoming driver flashes their lights repeatedly",
                "Never, high beam is required at all times"
            ),
            correctIndex = 1,
            explanation = "High beams dazzle oncoming drivers and drivers ahead through their rear-view mirrors. Always dip your lights when an oncoming car approaches or when following closely.",
            category = "Vehicle Safety"
        ),
        TheoryQuestion(
            id = 8,
            question = "What is the primary purpose of the 'Two-Second Rule' while following another vehicle?",
            tamilQuestion = "வாகனம் செலுத்தும் போது 'இரண்டு வினாடி விதி' (Two-Second Rule) எதற்காகப் பயன்படுகிறது?",
            options = listOf(
                "To determine when to overtake",
                "To ensure a safe stopping distance behind the vehicle in front in dry conditions",
                "To measure the time needed to change gears",
                "To check if the indicator light is blinking"
            ),
            correctIndex = 1,
            explanation = "The 2-second rule provides a minimum safe following gap under dry road conditions. Under wet rain conditions, double this to at least 4 seconds.",
            category = "Defensive Driving"
        ),
        TheoryQuestion(
            id = 9,
            question = "What must a learner driver in Sri Lanka display on the front and rear of the vehicle?",
            tamilQuestion = "இலங்கையில் பழகுநர் ஒருவர் வாகனத்தின் முன்பின்னாக எதனைக் காட்சிப்படுத்த வேண்டும்?",
            options = listOf(
                "A green flag",
                "A red 'L' plate on a white background",
                "A yellow safety triangle",
                "A written student badge"
            ),
            correctIndex = 1,
            explanation = "Learner drivers must display a clear red 'L' plate measuring standard regulatory dimensions on the front and rear of the training vehicle.",
            category = "Learner Rules"
        ),
        TheoryQuestion(
            id = 10,
            question = "What does a yellow box junction with criss-cross diagonal lines indicate?",
            tamilQuestion = "சந்திகளில் உள்ள மஞ்சள் கட்டக் கோடுகள் (Yellow Box Junction) எதனைக் குறிக்கின்றன?",
            options = listOf(
                "You can stop and park for up to 5 minutes",
                "You must not enter the box unless your exit road is clear (except when turning right)",
                "Only three-wheelers can enter",
                "Vehicles can overtake from the left side"
            ),
            correctIndex = 1,
            explanation = "Yellow box junctions keep intersections clear of gridlock. Never enter the box unless your exit is free, except when turning right and waiting for oncoming traffic to pass.",
            category = "Road Markings"
        ),
        TheoryQuestion(
            id = 11,
            question = "When should you use the vehicle's hazard warning lights (double flashers)?",
            tamilQuestion = "வாகனத்தின் எச்சரிக்கை இரட்டை விளக்குகளை (Hazard Lights) எப்போது பயன்படுத்த வேண்டும்?",
            options = listOf(
                "When parking illegally on the side of a busy street",
                "When your vehicle is temporarily broken down or causing a stationary hazard on the roadway",
                "While driving straight through a 4-way junction",
                "When driving in light rain"
            ),
            correctIndex = 1,
            explanation = "Hazard lights are only for stationary emergencies or sudden extreme hazards ahead. Using hazard lights while driving across junctions is incorrect and confusing to other road users.",
            category = "Vehicle Safety"
        ),
        TheoryQuestion(
            id = 12,
            question = "Before moving off from the side of the road, what is the correct safety observation routine?",
            tamilQuestion = "சாலையோரத்தில் இருந்து வாகனத்தை முன்னோக்கி செலுத்துவதற்கு முன் என்ன செய்ய வேண்டும்?",
            options = listOf(
                "Press accelerator and blow the horn",
                "Check interior and exterior mirrors, check blind spot over your shoulder, and signal your intention",
                "Look only at the speedometer",
                "Immediately steer into the middle of the road"
            ),
            correctIndex = 1,
            explanation = "The MSM (Mirror-Signal-Maneuver) and blind-spot shoulder check routine ensures no cyclist, motorcycle, or pedestrian is in your unobserved angle.",
            category = "Practical Maneuvers"
        )
    )

    fun getRoadSigns(): List<RoadSignItem> = listOf(
        RoadSignItem(
            id = "sign_stop",
            name = "STOP Sign",
            tamilName = "நிறுத்து (STOP)",
            category = SignCategory.REGULATORY,
            description = "Octagonal red sign with white lettering. Absolute requirement to come to a complete halt before the white line.",
            rule = "You MUST stop completely at the marked stop line even if the road appears empty. Yield to all cross-traffic before proceeding.",
            shape = "OCTAGON",
            primaryColorHex = 0xFFD32F2F
        ),
        RoadSignItem(
            id = "sign_give_way",
            name = "Give Way (Yield)",
            tamilName = "வழி விடுக (GIVE WAY)",
            category = SignCategory.REGULATORY,
            description = "Inverted red equilateral triangle on white background. Slow down and prepare to stop.",
            rule = "Give priority to traffic on the main road you are joining or crossing. Stop if necessary to avoid impeding main traffic.",
            shape = "TRIANGLE",
            primaryColorHex = 0xFFD32F2F
        ),
        RoadSignItem(
            id = "sign_no_entry",
            name = "No Entry for All Vehicles",
            tamilName = "உள்நுழைய தடை (No Entry)",
            category = SignCategory.REGULATORY,
            description = "Solid red circle with a horizontal white bar across the middle.",
            rule = "Prohibits all vehicular traffic from entering the street or ramp in this direction (e.g., one-way streets).",
            shape = "CIRCLE",
            primaryColorHex = 0xFFD32F2F
        ),
        RoadSignItem(
            id = "sign_speed_50",
            name = "Maximum Speed Limit: 50 km/h",
            tamilName = "அதிகபட்ச வேகம்: 50 கி.மீ/மணி",
            category = SignCategory.REGULATORY,
            description = "White circular sign with red border displaying the number '50'.",
            rule = "Do not exceed 50 km/h under any circumstances on this road section.",
            shape = "CIRCLE",
            primaryColorHex = 0xFFD32F2F
        ),
        RoadSignItem(
            id = "sign_no_overtaking",
            name = "No Overtaking",
            tamilName = "முந்திச் செல்ல தடை",
            category = SignCategory.REGULATORY,
            description = "Circular red border showing two cars side-by-side (one red, one black).",
            rule = "You are strictly forbidden to overtake any four-wheeled motor vehicle until the restriction ends.",
            shape = "CIRCLE",
            primaryColorHex = 0xFFD32F2F
        ),
        RoadSignItem(
            id = "sign_compulsory_turn_left",
            name = "Compulsory Turn Left Ahead",
            tamilName = "இடப்பக்கம் கட்டாய திருப்பம்",
            category = SignCategory.REGULATORY,
            description = "Blue circle with a curved white arrow pointing to the left.",
            rule = "Mandatory sign. All vehicles must follow the arrow and turn left at the upcoming junction.",
            shape = "CIRCLE",
            primaryColorHex = 0xFF1976D2
        ),
        RoadSignItem(
            id = "sign_pedestrian_crossing",
            name = "Pedestrian Crossing Ahead",
            tamilName = "பாதசாரி கடவை எச்சரிக்கை",
            category = SignCategory.WARNING,
            description = "Red triangular warning sign displaying a silhouette walking across road stripes.",
            rule = "Alerts drivers that a zebra crossing is approaching. Reduce speed, scan both sides of the sidewalk, and prepare to stop.",
            shape = "TRIANGLE",
            primaryColorHex = 0xFFF57C00
        ),
        RoadSignItem(
            id = "sign_sharp_bend",
            name = "Sharp Bend / Curve to the Right",
            tamilName = "வலப்பக்க கடுமையான வளைவு",
            category = SignCategory.WARNING,
            description = "Red triangular warning sign with an angled arrow bending sharply right.",
            rule = "Slow down before entering the bend, select appropriate gear, and maintain your lane without cutting corners.",
            shape = "TRIANGLE",
            primaryColorHex = 0xFFF57C00
        ),
        RoadSignItem(
            id = "sign_speed_breaker",
            name = "Road Hump / Speed Breaker Ahead",
            tamilName = "வேகத்தடை எச்சரிக்கை",
            category = SignCategory.WARNING,
            description = "Red triangular warning sign displaying two convex humps on the road profile.",
            rule = "Approach at safe low speed (under 20 km/h) to protect passengers and vehicle suspension.",
            shape = "TRIANGLE",
            primaryColorHex = 0xFFF57C00
        ),
        RoadSignItem(
            id = "sign_narrow_bridge",
            name = "Narrow Bridge Ahead",
            tamilName = "குறுகிய பாலம் எச்சரிக்கை",
            category = SignCategory.WARNING,
            description = "Red triangular sign showing road width constricting from both sides.",
            rule = "Roadway narrows on a bridge or culvert. Check for oncoming priority signs and yield if required.",
            shape = "TRIANGLE",
            primaryColorHex = 0xFFF57C00
        ),
        RoadSignItem(
            id = "sign_hospital",
            name = "Hospital & Emergency Care",
            tamilName = "வைத்தியசாலை (Hospital)",
            category = SignCategory.INFORMATIVE,
            description = "Blue rectangular sign showing a red cross or bed symbol.",
            rule = "Indicates a hospital nearby. Maintain low noise levels, avoid unnecessary horn blowing, and yield to ambulances.",
            shape = "RECTANGLE",
            primaryColorHex = 0xFF1976D2
        ),
        RoadSignItem(
            id = "sign_parking",
            name = "Designated Parking Area",
            tamilName = "வாகன தரிப்பிடம் (Parking)",
            category = SignCategory.INFORMATIVE,
            description = "Blue rectangular or square sign with a prominent white capital letter 'P'.",
            rule = "Permitted area to park vehicles according to marked parking bays and municipal regulations.",
            shape = "RECTANGLE",
            primaryColorHex = 0xFF1976D2
        )
    )

    fun getLicenseSteps(): List<LicenseStep> = listOf(
        LicenseStep(
            stepNumber = 1,
            title = "Medical Examination (NTMI)",
            tamilTitle = "மருத்துவ சான்றிதழ் பெறுதல்",
            subtitle = "National Transport Medical Institute Fitness Check",
            details = listOf(
                "Visit the NTMI office (e.g. Jaffna or Vavuniya branch)",
                "Undergo visual acuity test, color perception, hearing, and blood pressure test",
                "Obtain the official NTMI Medical Fitness Certificate (valid for 6 months)",
                "Requirements: Original NIC / Passport, 2 passport size photos"
            ),
            tips = "Kiruba Learners assists students with scheduling NTMI appointments and paper guidance."
        ),
        LicenseStep(
            stepNumber = 2,
            title = "DMT Registration & Learner Permit",
            tamilTitle = "DMT பதிவு & பழகுநர் அனுமதிப்பத்திரம்",
            subtitle = "Receive your official government Learner Permit (L-Plate)",
            details = listOf(
                "Submit NTMI medical certificate, birth certificate, and NIC copies at DMT",
                "Biometric fingerprint registration and official photo capture",
                "Receive the Learner Permit (valid for 18 months)",
                "Permit allows you to practice under Kiruba Learners instructor supervision"
            ),
            tips = "Kiruba Learners handles document verification and submission batches directly with DMT."
        ),
        LicenseStep(
            stepNumber = 3,
            title = "Highway Code & Theory Exam Pass",
            tamilTitle = "கோட்பாட்டுப் பரீட்சை (Theory Test)",
            subtitle = "Computerized 40-question multiple choice test",
            details = listOf(
                "Attend Kiruba Learners interactive road theory and road sign classes",
                "Practice using our in-app mock exam simulator",
                "Sit for the official DMT computerized exam in Tamil, Sinhala, or English",
                "Score at least 30 out of 40 (75%) to receive trial eligibility"
            ),
            tips = "Kiruba Learners students enjoy an outstanding 98%+ pass rate on their first attempt!"
        ),
        LicenseStep(
            stepNumber = 4,
            title = "Yard & Dual-Control Road Training",
            tamilTitle = "மைதானம் & வீதிப் பயிற்சி",
            subtitle = "Hands-on driving with certified instructors",
            details = listOf(
                "Master yard maneuvers: Hill start, Three-point turn, S/L reverse parking",
                "City driving in Jaffna Town / Vavuniya bustling markets and roundabouts",
                "High-speed lane management on A9 highway",
                "Night driving sessions and monsoon rain defensive techniques"
            ),
            tips = "All training cars feature dual-control pedals so your instructor can assist instantly."
        ),
        LicenseStep(
            stepNumber = 5,
            title = "Official DMT Practical Trial Test",
            tamilTitle = "நடைமுறைப் பரீட்சை & சாரதி அனுமதிப்பத்திரம்",
            subtitle = "Examiner evaluation and issuance of Driving License",
            details = listOf(
                "Pre-trial mock test evaluated by Kiruba Learners chief instructor",
                "Official DMT test at the government trial grounds in Kiruba Learners vehicle",
                "Yard maneuvers test (reverse and hill start) followed by road driving test",
                "Upon passing, receive immediate Temporary Driving Permit, followed by Smart Card"
            ),
            tips = "Kiruba Learners vehicles are stationed at the trial grounds so you test in the car you trained in!"
        )
    )

    fun getBranches(): List<BranchLocation> = listOf(
        BranchLocation(
            id = "b_jaffna_nallur",
            name = "Jaffna Head Office (Kachcheri Nallur)",
            tamilName = "யாழ்ப்பாணம் கச்சேரி நல்லூர் தலைமை காரியாலயம்",
            address = "#36, Kachcheri Nallur Road, Jaffna, Sri Lanka",
            landmark = "Near Kachcheri Junction & Nallur Temple corridor",
            phoneNumbers = listOf("+94 77 722 5292", "+94 21 222 4353"),
            email = "kirubalearners@gmail.com",
            openingHours = "Mon - Sat: 7:30 AM - 6:00 PM | Sun: 8:00 AM - 1:00 PM",
            mapQuery = "36 Kachcheri Nallur Rd, Jaffna"
        ),
        BranchLocation(
            id = "b_jaffna_kasthuriyar",
            name = "Jaffna City Branch (Kasthuriyar Road)",
            tamilName = "யாழ்ப்பாணம் கஸ்தூரியார் வீதி கிளை",
            address = "#226, Kasthuriyar Road, Jaffna, Sri Lanka",
            landmark = "Central Jaffna City Centre near commercial hub",
            phoneNumbers = listOf("+94 77 722 5292", "+94 21 222 4353"),
            email = "kirubalearners@gmail.com",
            openingHours = "Mon - Sat: 8:00 AM - 5:30 PM",
            mapQuery = "226 Kasthuriyar Rd, Jaffna"
        ),
        BranchLocation(
            id = "b_vavuniya",
            name = "Vavuniya Regional Branch (A9 Road)",
            tamilName = "வவுனியா A9 வீதி கிளை",
            address = "No. 216, Kandy Road (A9 Road), Vavuniya, Sri Lanka",
            landmark = "Directly along A9 Highway, Vavuniya Town",
            phoneNumbers = listOf("+94 24 222 7777", "+94 71 777 5252"),
            email = "kirubalearners@gmail.com",
            openingHours = "Mon - Sat: 7:30 AM - 6:00 PM",
            mapQuery = "216 Kandy Rd, Vavuniya"
        )
    )
}
