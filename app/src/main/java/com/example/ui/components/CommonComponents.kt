package com.example.ui.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Course
import com.example.data.model.RoadSignItem
import com.example.ui.theme.RoyalBluePrimary
import com.example.ui.theme.SafetyAmber
import com.example.ui.theme.TrafficGreen
import com.example.ui.viewmodel.EnrollmentFormState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KirubaTopAppBar(
    onCallClick: () -> Unit = {},
    onWhatsAppClick: () -> Unit = {}
) {
    Surface(
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 2.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(RoyalBluePrimary),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.DirectionsCar,
                        contentDescription = "Kiruba Logo",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "KIRUBA",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Black,
                                letterSpacing = 1.sp,
                                color = RoyalBluePrimary
                            )
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "LEARNERS",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        )
                    }
                    Text(
                        text = "Jaffna • Vavuniya | Est. 2009",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                IconButton(
                    onClick = onWhatsAppClick,
                    modifier = Modifier.testTag("top_whatsapp_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Chat,
                        contentDescription = "WhatsApp Helpline",
                        tint = TrafficGreen
                    )
                }
                IconButton(
                    onClick = onCallClick,
                    modifier = Modifier.testTag("top_call_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Phone,
                        contentDescription = "Call Driving School",
                        tint = RoyalBluePrimary
                    )
                }
            }
        }
    }
}

@Composable
fun StatCard(
    number: String,
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    modifier: Modifier = Modifier,
    accentColor: Color = RoyalBluePrimary
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        ),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(accentColor.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = accentColor,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = number,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun RoadSignVisual(
    sign: RoadSignItem,
    modifier: Modifier = Modifier,
    sizeDp: Int = 54
) {
    Box(
        modifier = modifier
            .size(sizeDp.dp)
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        when (sign.shape) {
            "OCTAGON" -> {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val w = size.width
                    val h = size.height
                    val p = Path().apply {
                        moveTo(w * 0.3f, 0f)
                        lineTo(w * 0.7f, 0f)
                        lineTo(w, h * 0.3f)
                        lineTo(w, h * 0.7f)
                        lineTo(w * 0.7f, h)
                        lineTo(w * 0.3f, h)
                        lineTo(0f, h * 0.7f)
                        lineTo(0f, h * 0.3f)
                        close()
                    }
                    drawPath(p, color = Color(0xFFD32F2F))
                    drawPath(p, color = Color.White, style = Stroke(width = 2.dp.toPx()))
                }
                Text(
                    text = "STOP",
                    color = Color.White,
                    fontWeight = FontWeight.Black,
                    fontSize = 10.sp
                )
            }
            "TRIANGLE" -> {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val w = size.width
                    val h = size.height
                    val p = Path().apply {
                        moveTo(w / 2f, 2f)
                        lineTo(w - 2f, h - 2f)
                        lineTo(2f, h - 2f)
                        close()
                    }
                    drawPath(p, color = Color.White)
                    drawPath(p, color = Color(sign.primaryColorHex), style = Stroke(width = 4.dp.toPx()))
                }
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = null,
                    tint = Color(sign.primaryColorHex),
                    modifier = Modifier.size((sizeDp / 2.5).dp)
                )
            }
            "CIRCLE" -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                        .background(Color.White)
                        .border(4.dp, Color(sign.primaryColorHex), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    if (sign.id.contains("50")) {
                        Text(
                            text = "50",
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            fontSize = 14.sp
                        )
                    } else if (sign.id.contains("turn")) {
                        Icon(
                            imageVector = Icons.Default.TurnLeft,
                            contentDescription = null,
                            tint = Color(sign.primaryColorHex),
                            modifier = Modifier.size((sizeDp / 2).dp)
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Block,
                            contentDescription = null,
                            tint = Color(sign.primaryColorHex),
                            modifier = Modifier.size((sizeDp / 2).dp)
                        )
                    }
                }
            }
            else -> { // RECTANGLE
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color(sign.primaryColorHex)),
                    contentAlignment = Alignment.Center
                ) {
                    if (sign.id.contains("parking")) {
                        Text(
                            text = "P",
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontSize = 18.sp
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.LocalHospital,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size((sizeDp / 2).dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun EnrollmentDialog(
    formState: EnrollmentFormState,
    onDismiss: () -> Unit,
    onUpdateForm: (
        studentName: String?,
        phone: String?,
        nicOrPassport: String?,
        preferredBranch: String?,
        preferredTiming: String?,
        courseTitle: String?,
        message: String?
    ) -> Unit,
    onSubmit: () -> Unit
) {
    val branches = listOf(
        "Jaffna Head Office (Kachcheri Nallur)",
        "Jaffna City Branch (Kasthuriyar Road)",
        "Vavuniya Regional Branch (A9 Road)"
    )

    val timings = listOf(
        "Morning (7:30 AM - 10:30 AM)",
        "Afternoon (1:00 PM - 4:00 PM)",
        "Evening (4:00 PM - 6:30 PM)",
        "Weekend Only (Sat & Sun)"
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.School,
                    contentDescription = null,
                    tint = RoyalBluePrimary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (formState.isSuccess) "Enrollment Submitted!" else "Enroll in Course",
                    fontWeight = FontWeight.Bold
                )
            }
        },
        text = {
            if (formState.isSuccess) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = TrafficGreen,
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Vanakkam & Thank You!",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Our Kiruba Learners branch admission officer will contact you shortly via WhatsApp / Phone to confirm your registration and trial schedule.",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "Course: ${formState.courseTitle}",
                        style = MaterialTheme.typography.labelLarge.copy(
                            color = RoyalBluePrimary,
                            fontWeight = FontWeight.Bold
                        )
                    )

                    OutlinedTextField(
                        value = formState.studentName,
                        onValueChange = { onUpdateForm(it, null, null, null, null, null, null) },
                        label = { Text("Full Name *") },
                        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth().testTag("enroll_name_input")
                    )

                    OutlinedTextField(
                        value = formState.phone,
                        onValueChange = { onUpdateForm(null, it, null, null, null, null, null) },
                        label = { Text("Phone / WhatsApp Number *") },
                        placeholder = { Text("+94 77 XXXXXXX") },
                        leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null) },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth().testTag("enroll_phone_input")
                    )

                    OutlinedTextField(
                        value = formState.nicOrPassport,
                        onValueChange = { onUpdateForm(null, null, it, null, null, null, null) },
                        label = { Text("NIC / Passport No. (Optional)") },
                        leadingIcon = { Icon(Icons.Default.Badge, contentDescription = null) },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Text(
                        text = "Preferred Branch:",
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    branches.forEach { branch ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onUpdateForm(null, null, null, branch, null, null, null) }
                                .padding(vertical = 2.dp)
                        ) {
                            RadioButton(
                                selected = formState.preferredBranch == branch,
                                onClick = { onUpdateForm(null, null, null, branch, null, null, null) }
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(text = branch, style = MaterialTheme.typography.bodySmall)
                        }
                    }

                    Text(
                        text = "Preferred Batch Timing:",
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    timings.forEach { timing ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onUpdateForm(null, null, null, null, timing, null, null) }
                                .padding(vertical = 2.dp)
                        ) {
                            RadioButton(
                                selected = formState.preferredTiming == timing,
                                onClick = { onUpdateForm(null, null, null, null, timing, null, null) }
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(text = timing, style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
        },
        confirmButton = {
            if (formState.isSuccess) {
                Button(onClick = onDismiss) {
                    Text("Done")
                }
            } else {
                Button(
                    onClick = onSubmit,
                    enabled = formState.studentName.isNotBlank() && formState.phone.isNotBlank() && !formState.isSubmitting,
                    colors = ButtonDefaults.buttonColors(containerColor = RoyalBluePrimary),
                    modifier = Modifier.testTag("submit_enroll_button")
                ) {
                    if (formState.isSubmitting) {
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(18.dp))
                    } else {
                        Text("Confirm Registration")
                    }
                }
            }
        },
        dismissButton = {
            if (!formState.isSuccess) {
                TextButton(onClick = onDismiss) {
                    Text("Cancel")
                }
            }
        }
    )
}
