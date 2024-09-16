package com.android.quotes
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import android.content.Context
import androidx.compose.foundation.border
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
                QuoteApp()
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun QuoteApp() {
    var currentQuote by remember { mutableStateOf(getRandomQuote()) }
    var favoriteQuotes by remember { mutableStateOf(emptyList<String>()) }
    var userQuotes by remember { mutableStateOf(emptyList<String>()) }
    var newQuoteText by remember { mutableStateOf("") }
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    MaterialTheme {
        Scaffold(
            topBar = {
                TopAppBar(title = { Text("Inspiring Quotes") })
            },
            content = {
                Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                    QuoteDisplay(currentQuote)
                    Spacer(modifier = Modifier.height(16.dp))

                    // Add user input for new quotes
                    BasicTextField(
                        value = newQuoteText,
                        onValueChange = { newQuoteText = it },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = { keyboardController?.hide() }
                        ),
                        modifier = Modifier.fillMaxWidth().padding(8.dp).border(1.dp, Color.Gray).padding(8.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = {
                        if (newQuoteText.isNotBlank()) {
                            userQuotes = userQuotes + newQuoteText
                            newQuoteText = ""
                            Toast.makeText(context, "Quote added", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "Please enter a quote", Toast.LENGTH_SHORT).show()
                        }
                    }) {
                        Text("Add Quote")
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Button(onClick = { currentQuote = getRandomQuote() }) {
                            Text("Refresh Quote")
                        }
                        Button(onClick = {
                            shareQuote(context, currentQuote)
                        }) {
                            Text("Share Quote")
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = {
                        favoriteQuotes = favoriteQuotes + currentQuote
                        Toast.makeText(context, "Quote added to favorites", Toast.LENGTH_SHORT).show()
                    }) {
                        Text("Favorite Quote")
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    FavoriteQuotesScreen(favoriteQuotes)
                    Spacer(modifier = Modifier.height(16.dp))
                    UserQuotesScreen(userQuotes)
                }
            }
        )
    }
}

@Composable
fun QuoteDisplay(quote: String) {
    Text(
        text = quote,
        style = TextStyle(
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center
        ),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun FavoriteQuotesScreen(favorites: List<String>) {
    Column {
        Text(
            text = "Favorite Quotes",
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 8.dp)
        )
        LazyColumn {
            items(favorites) { favorite ->
                Text(
                    text = favorite,
                    style = TextStyle(fontSize = 18.sp, color = Color.Gray),
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }
}

@Composable
fun UserQuotesScreen(userQuotes: List<String>) {
    Column {
        Text(
            text = "User Quotes",
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 8.dp)
        )
        LazyColumn {
            items(userQuotes) { userQuote ->
                Text(
                    text = userQuote,
                    style = TextStyle(fontSize = 18.sp, color = Color.Gray),
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }
}

fun getRandomQuote(): String {
    val quotes = listOf(
        "The best way to predict the future is to invent it.",
        "Your limitation—it's only your imagination.",
        "Push yourself, because no one else is going to do it for you.",
        "Great things never come from comfort zones.",
        "Dream it. Wish it. Do it.",
        "The only way to do great work is to love what you do.— Steve Jobs",
    "In the middle of difficulty lies opportunity. — Albert Einstein",
    "Success is not final, failure is not fatal: It is the courage to continue that counts. — Winston Churchill",
    "You miss 100% of the shots you don’t take. — Wayne Gretzky",
    "Life is what happens when you’re busy making other plans. — John Lennon",
    "The only limit to our realization of tomorrow is our doubts of today. — Franklin D. Roosevelt",
    "Do not go where the path may lead, go instead where there is no path and leave a trail. — Ralph Waldo Emerson",
    "The journey of a thousand miles begins with one step. — Lao Tzu",
    "To be yourself in a world that is constantly trying to make you something else is the greatest accomplishment.— Ralph Waldo Emerson",
    "In three words I can sum up everything I've learned about life: it goes on. — Robert Frost",
    "You must be the change you wish to see in the world. — Mahatma Gandhi",
    "The best way to predict the future is to invent it. — Alan Kay",
    "Happiness is not something ready made. It comes from your own actions. — Dalai Lama",
    "We are what we repeatedly do. Excellence, then, is not an act, but a habit. — Aristotle",
    "The greatest glory in living lies not in never falling, but in rising every time we fall. — Nelson Mandela",
    "The only impossible journey is the one you never begin. — Tony Robbins",
    "Act as if what you do makes a difference. It does. — William James",
    "The way to get started is to quit talking and begin doing. — Walt Disney",
    "You only live once, but if you do it right, once is enough. — Mae West",
    "To live is the rarest thing in the world. Most people exist, that is all.— Oscar Wilde",
    "Dream big and dare to fail. — Norman Vaughan",
    "The future belongs to those who believe in the beauty of their dreams. — Eleanor Roosevelt",
    "The only true wisdom is in knowing you know nothing. — Socrates",
    "The mind is everything. What you think you become. — Buddha",
    "Don’t watch the clock; do what it does. Keep going. — Sam Levenson",
    "It does not matter how slowly you go as long as you do not stop. — Confucius",
    "You can’t help everyone, but everyone can help someone. — Ronald Reagan",
    "Do not wait to strike till the iron is hot, but make it hot by striking. — William Butler Yeats",
    "What we think, we become. — Buddha",
    "To be great is to be misunderstood. — Ralph Waldo Emerson",
    "The only person you are destined to become is the person you decide to be.— Ralph Waldo Emerson",
    "I have not failed. I’ve just found 10,000 ways that won’t work. — Thomas Edison",
    "The best revenge is massive success. — Frank Sinatra",
    "Life isn’t about finding yourself. Life is about creating yourself. — George Bernard Shaw",
    "You can’t use up creativity. The more you use, the more you have. — Maya Angelou",
    "Believe you can and you’re halfway there. — Theodore Roosevelt",
    "Everything you’ve ever wanted is on the other side of fear. — George Addair",
    "The only way to achieve the impossible is to believe it is possible. — Charles Kingsleigh",
    "Success usually comes to those who are too busy to be looking for it. — Henry David Thoreau",
    "Opportunities don't happen, you create them. — Chris Grosser",
    "Everything has beauty, but not everyone can see. — Confucius",
    "You don’t have to be great to start, but you have to start to be great. — Zig Ziglar",
    "Start where you are. Use what you have. Do what you can. — Arthur Ashe",
    "What lies behind us and what lies before us are tiny matters compared to what lies within us. — Ralph Waldo Emerson",
    "You are never too old to set another goal or to dream a new dream. — C.S. Lewis",
    "Do not wait; the time will never be ‘just right.’ Start where you stand, and work with whatever tools you may have at your command, and better tools will be found as you go along. — Napoleon Hill",
    "If you tell the truth, you don’t have to remember anything. — Mark Twain",
    "The only way out is through. — Robert Frost",
    "A journey of a thousand miles begins with a single step. — Lao Tzu",
    "The most wasted of days is one without laughter. — E.E. Cummings",
    "Success is going from failure to failure without losing your enthusiasm. — Winston Churchill",
    "We don't see things as they are, we see them as we are. — Anaïs Nin",
    "Everything you can imagine is real. — Pablo Picasso",
    "It always seems impossible until it’s done. — Nelson Mandela",
    "Life is really simple, but we insist on making it complicated. — Confucius",
    "The harder I work, the luckier I get. — Samuel Goldwyn",
    "Be yourself; everyone else is already taken. — Oscar Wilde",
    "Life isn’t about waiting for the storm to pass. It’s about learning how to dance in the rain. — Vivian Greene",
    "You do not find the happy life. You make it. — Camilla E. Kimball",
    "Happiness depends upon ourselves. — Aristotle",
    "You can’t go back and change the beginning, but you can start where you are and change the ending. — C.S. Lewis",
    "Success is not how high you have climbed, but how you make a positive difference to the world. — Roy T. Bennet",
    "Life is short, and it is up to you to make it sweet. — Sarah Louise Delany",
    "The best time to plant a tree was 20 years ago. The second-best time is now.— Chinese Proverb",
    "The only thing standing between you and your goal is the story you keep telling yourself as to why you can’t achieve it. — Jordan Belfort",
    "Don’t let the noise of others’ opinions drown out your own inner voice. — Steve Jobs",
    "You are never too old to set another goal or to dream a new dream. — C.S. Lewis",
    "Nothing in life is to be feared, it is only to be understood. Now is the time to understand more so that we may fear less. — Marie Curie",
    "The secret of getting ahead is getting started. — Mark Twain",
    "Success is not in what you have, but who you are. — Bo Bennett",
    "It is never too late to be what you might have been. — George Eliot",
    "Don’t judge each day by the harvest you reap but by the seeds that you plant. — Robert Louis Stevenson",
    "You must do the things you think you cannot do. — Eleanor Roosevelt",
    "When you cease to dream, you cease to live. — Malcolm Forbes",
    "There is only one way to avoid criticism: do nothing, say nothing, and be nothing. — Aristotle",
    "The best way to find yourself is to lose yourself in the service of others. — Mahatma Gandhi",
    "If you want to lift yourself up, lift up someone else. — Booker T. Washington",
    "The only limit to our realization of tomorrow is our doubts of today. — Franklin D. Roosevelt",
    "You don’t have to be perfect to be amazing. — Unknown",
    "A person who never made a mistake never tried anything new. — Albert Einstein",
    "You are not a drop in the ocean. You are the entire ocean in a drop. — Rumi",
    "The only real mistake is the one from which we learn nothing. — Henry Ford",
    "What we achieve inwardly will change outer reality. — Plutarch",
    "Happiness is not something ready-made. It comes from your own actions. — Dalai Lama",
    "We become what we think about. — Earl Nightingale",
    "Change your thoughts and you change your world. — Norman Vincent Peale",
    "The only way to make sense out of change is to plunge into it, move with it, and join the dance. — Alan Watts",
    "What lies behind us and what lies before us are tiny matters compared to what lies within us. — Ralph Waldo Emerson",
    "The mind is everything. What you think you become. — Buddha",
    "Do what you can, with what you have, where you are. — Theodore Roosevelt",
    "The most beautiful and profound way to change yourself is to accept yourself completely, as imperfect as you are. — Maxime Lagace",
    )
    return quotes.random()
}

fun shareQuote(context: Context, quote: String) {
    val sendIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, quote)
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, null)
    context.startActivity(shareIntent)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    QuoteApp()
}