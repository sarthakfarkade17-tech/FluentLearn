package com.fluentlearn.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Bg = Color(0xFF0B0C14)
private val Surface = Color(0xFF171927)
private val Accent = Color(0xFF9B87FF)
private val Muted = Color(0xFF9CA3B8)


class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent { FluentLearnApp() }
  }
}


@Composable fun FluentLearnApp() {
  var tab by remember { mutableStateOf(0) }
  MaterialTheme(colorScheme = darkColorScheme(background = Bg, surface = Surface, primary = Accent)) {
    Scaffold(containerColor = Bg, bottomBar = {
      NavigationBar(containerColor = Surface, tonalElevation = 0.dp) {
        val items = listOf("Home", "AI World", "Talk", "Progress", "Profile")
        val icons = listOf(Icons.Default.Home, Icons.Default.Groups, Icons.Default.Mic, Icons.Default.Insights, Icons.Default.Person)
        items.forEachIndexed { i, name -> NavigationBarItem(selected = tab == i, onClick = { tab = i }, icon = { Icon(icons[i], name) }, label = { Text(name, fontSize = 10.sp) }) }
      }
    }) { padding ->
      Box(Modifier.padding(padding).fillMaxSize()) {
        when(tab) {
          0 -> HomeScreen(onStart = { tab = 2 }, onExplore = { tab = 1 })
          1 -> WorldScreen(onTalk = { tab = 2 })
          2 -> TalkScreen()
          3 -> ProgressScreen()
          else -> ProfileScreen()
        }
      }
    }
  }
}


@Composable fun ScreenColumn(content: @Composable ColumnScope.() -> Unit) = Column(Modifier.fillMaxSize().background(Bg).padding(horizontal = 20.dp), content = content)
@Composable fun Heading(title: String, subtitle: String? = null) {
  Text(title, color = Color.White, fontSize = 26.sp, fontWeight = FontWeight.Bold)
  if (subtitle != null) { Spacer(Modifier.height(4.dp)); Text(subtitle, color = Muted, fontSize = 14.sp) }
}
@Composable fun CardBox(modifier: Modifier = Modifier, content: @Composable ColumnScope.() -> Unit) {
  Column(modifier.background(Surface, RoundedCornerShape(20.dp)).padding(16.dp), content = content)
}
@Composable fun HomeScreen(onStart: () -> Unit, onExplore: () -> Unit) {
  ScreenColumn {
    Spacer(Modifier.height(20.dp)); Text("FLUENTLEARN", color = Accent, letterSpacing = 2.sp, fontSize = 12.sp, fontWeight = FontWeight.Bold)
    Spacer(Modifier.height(12.dp)); Heading("Your world.\nYour voice.", "Practice English naturally, one conversation at a time.")
    Spacer(Modifier.height(20.dp))
    CardBox(Modifier.fillMaxWidth()) {
      Text("TODAY'S GOAL", color = Muted, fontSize = 11.sp, fontWeight = FontWeight.Bold)
      Spacer(Modifier.height(8.dp)); Text("10 minutes of speaking", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
      Spacer(Modifier.height(12.dp)); LinearProgressIndicator(progress = { 0.25f }, modifier = Modifier.fillMaxWidth(), color = Accent, trackColor = Color(0xFF303244))
      Spacer(Modifier.height(6.dp)); Text("2 of 10 minutes completed", color = Muted, fontSize = 12.sp)
    }
    Spacer(Modifier.height(18.dp))
    Button(onClick = onStart, modifier = Modifier.fillMaxWidth().height(56.dp), shape = RoundedCornerShape(16.dp), colors = ButtonDefaults.buttonColors(containerColor = Accent)) { Icon(Icons.Default.Mic, null); Spacer(Modifier.width(8.dp)); Text("Start speaking", fontWeight = FontWeight.Bold) }
    Spacer(Modifier.height(10.dp)); OutlinedButton(onClick = onExplore, modifier = Modifier.fillMaxWidth().height(52.dp), shape = RoundedCornerShape(16.dp)) { Icon(Icons.Default.Explore, null); Spacer(Modifier.width(8.dp)); Text("Explore AI World") }
    Spacer(Modifier.height(20.dp)); Heading("Choose your practice", "Jump into a situation and speak freely")
    Spacer(Modifier.height(10.dp)); PracticeCard("☕", "Casual conversation", "Talk about everyday life", onStart)
    PracticeCard("💼", "Job interview", "Build confidence for interviews", onStart)
    PracticeCard("✈️", "Travel situations", "Practice before your next trip", onStart)
  }
}
@Composable fun PracticeCard(emoji: String, title: String, detail: String, action: () -> Unit) {
  CardBox(Modifier.fillMaxWidth().padding(bottom = 10.dp)) {
    Row(verticalAlignment = Alignment.CenterVertically) {
      Text(emoji, fontSize = 27.sp); Spacer(Modifier.width(12.dp)); Column(Modifier.weight(1f)) { Text(title, color = Color.White, fontWeight = FontWeight.SemiBold); Text(detail, color = Muted, fontSize = 12.sp) }; Icon(Icons.Default.ChevronRight, null, tint = Accent)
    }
  }
}
data class Character(val name: String, val description: String, val style: String, val emoji: String)
val characters = listOf(
  Character("Alex", "Friendly college student", "Casual · American", "🧑‍🎓"),
  Character("Sophia", "Patient English mentor", "Supportive · British", "👩‍🏫"),
  Character("Jordan", "Career-focused professional", "Formal · American", "👨‍💼"),
  Character("Maya", "Curious travel enthusiast", "Expressive · Australian", "🧳"),
  Character("Sam", "Funny and energetic friend", "Slang · American", "😄"),
  Character("Riya", "Tech-loving student", "Relaxed · Indian English", "👩‍💻")
)
@Composable fun WorldScreen(onTalk: () -> Unit) {
  ScreenColumn {
    Spacer(Modifier.height(24.dp)); Heading("AI World", "Choose a conversation partner")
    Spacer(Modifier.height(12.dp)); LazyColumn(contentPadding = PaddingValues(bottom = 16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
      items(characters) { c -> CardBox(Modifier.fillMaxWidth()) { Row(verticalAlignment = Alignment.CenterVertically) { Text(c.emoji, fontSize = 35.sp); Spacer(Modifier.width(12.dp)); Column(Modifier.weight(1f)) { Text(c.name, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold); Text(c.description, color = Muted); Text(c.style, color = Accent, fontSize = 12.sp) }; Button(onClick = onTalk, colors = ButtonDefaults.buttonColors(containerColor = Accent)) { Text("Talk") } } } }
    }
  }
}
@Composable fun TalkScreen() {
  var input by remember { mutableStateOf("") }
  var started by remember { mutableStateOf(false) }
  var messages by remember { mutableStateOf(listOf("Alex" to "Hey! Great to meet you. What do you like doing in your free time?")) }
  ScreenColumn {
    Spacer(Modifier.height(20.dp)); Heading("Live Talk", "Practice conversation")
    Spacer(Modifier.height(12.dp)); CardBox(Modifier.fillMaxWidth()) { Row(verticalAlignment = Alignment.CenterVertically) { Text("🧑‍🎓", fontSize = 34.sp); Spacer(Modifier.width(10.dp)); Column { Text("Alex", color = Color.White, fontWeight = FontWeight.Bold); Text("Friendly college student · Demo chat", color = Muted, fontSize = 12.sp) } } }
    Spacer(Modifier.height(12.dp)); LazyColumn(Modifier.weight(1f), contentPadding = PaddingValues(bottom = 12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) { items(messages) { (who, msg) -> ChatBubble(who, msg) } }
    Row(verticalAlignment = Alignment.CenterVertically) { OutlinedTextField(value = input, onValueChange = { input = it }, modifier = Modifier.weight(1f), placeholder = { Text("Write in English…") }, shape = RoundedCornerShape(18.dp), singleLine = true); Spacer(Modifier.width(8.dp)); IconButton(onClick = { if(input.isNotBlank()) { val text=input.trim(); messages=messages+listOf("You" to text, "Alex" to "Demo mode: AI responses will be connected when the local Qwen engine is integrated. Try describing your day or asking a question!"); input="" } }) { Icon(Icons.Default.Send, "Send", tint = Accent) } }
    Spacer(Modifier.height(8.dp)); Button(onClick = { started = !started }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = Accent), shape = RoundedCornerShape(16.dp)) { Icon(if(started) Icons.Default.Stop else Icons.Default.Mic, null); Spacer(Modifier.width(8.dp)); Text(if(started) "Stop voice (UI demo)" else "Start voice (UI demo)") }
  }
}
@Composable fun ChatBubble(who: String, text: String) { Column(Modifier.fillMaxWidth(), horizontalAlignment = if(who == "You") Alignment.End else Alignment.Start) { Text(who, color = Muted, fontSize = 11.sp); Spacer(Modifier.height(3.dp)); Text(text, color = Color.White, modifier = Modifier.background(if(who=="You") Color(0xFF51468A) else Surface, RoundedCornerShape(16.dp)).padding(12.dp)) } }
@Composable fun ProgressScreen() {
  ScreenColumn { Spacer(Modifier.height(24.dp)); Heading("Your progress", "Small steps add up to fluency"); Spacer(Modifier.height(16.dp)); CardBox(Modifier.fillMaxWidth()) { Text("Speaking streak", color = Muted); Text("🔥 1 day", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold); Text("Keep your momentum going", color = Muted) }; Spacer(Modifier.height(12.dp)); CardBox(Modifier.fillMaxWidth()) { Text("Practice stats", color = Color.White, fontWeight = FontWeight.Bold); Spacer(Modifier.height(12.dp)); StatRow("Sessions completed", "0"); StatRow("Minutes spoken", "0"); StatRow("Words practiced", "0"); StatRow("Vocabulary saved", "0") }; Spacer(Modifier.height(12.dp)); CardBox(Modifier.fillMaxWidth()) { Text("Your goal", color = Color.White, fontWeight = FontWeight.Bold); Text("Speak for 10 minutes every day", color = Muted); Spacer(Modifier.height(8.dp)); LinearProgressIndicator(progress = { 0f }, modifier = Modifier.fillMaxWidth(), color = Accent, trackColor = Color(0xFF303244)) } }
}
@Composable fun StatRow(label: String, value: String) { Row(Modifier.fillMaxWidth().padding(vertical = 7.dp), horizontalArrangement = Arrangement.SpaceBetween) { Text(label, color = Muted); Text(value, color = Color.White, fontWeight = FontWeight.Bold) } }
@Composable fun ProfileScreen() { ScreenColumn { Spacer(Modifier.height(24.dp)); Heading("Profile", "Your FluentLearn space"); Spacer(Modifier.height(16.dp)); CardBox(Modifier.fillMaxWidth()) { Text("👋", fontSize = 38.sp); Text("English learner", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp); Text("Your progress stays on this device in future builds.", color = Muted) }; Spacer(Modifier.height(12.dp)); listOf("AI model" to "Qwen3 (planned)", "Minimum RAM" to "8 GB", "App version" to "0.1.0 prototype", "Privacy" to "Local-first design").forEach { (k,v) -> CardBox(Modifier.fillMaxWidth().padding(bottom = 8.dp)) { Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) { Text(k, color = Muted); Text(v, color = Color.White) } } } } }
