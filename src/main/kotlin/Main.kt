import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.*
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import icons.Close
import icons.Compress
import icons.Expand
import icons.Minimize
import navigation.DecomposeNav
import ui.ChatScreen
import ui.HomeScreen
import ui.theme.gradientBackground


@Composable
fun App(root: DecomposeNav) {
    MaterialTheme {
        val childStack by root.childStack.subscribeAsState()
        Children(
            stack = childStack,
            animation = stackAnimation(slide())
        )
        {child->
            when(val instance = child.instance){
                is DecomposeNav.Child.HomeScreen -> HomeScreen(instance.component)
                is DecomposeNav.Child.ChatScreen -> ChatScreen(instance.component)
            }
        }
    }
}

fun main() = application {
    val root = remember {
        DecomposeNav(DefaultComponentContext(LifecycleRegistry()))
    }

    val state = rememberWindowState(
        position = WindowPosition(Alignment.Center),
        size = DpSize(1635.dp, 920.dp),

    )
    Window(
        state = state,
        resizable = true,
        onCloseRequest = ::exitApplication,
        undecorated = true
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xFF263545)),
            contentAlignment = Alignment.Center
        )
        {
            Box(modifier = Modifier
                .gradientBackground(
                    listOf(Color.Green.copy(alpha = 0.1f), Color.Transparent, Color.Transparent),
                    angle = -96f
                )
                .align(Alignment.TopEnd)
                .width(500.dp)
                .height(150.dp)
            )

            Column (
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                repeat(state.size.height.value.toInt() / 20) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    )
                    {
                        repeat(state.size.width.value.toInt() / 20)
                        {
                            Box(
                                modifier = Modifier
                                    .padding(9.dp)
                                    .size(2.dp)
                                    .background(
                                        color = Color.Gray.copy(alpha = 0.5f),
                                        shape = CircleShape
                                    )
                            )
                        }
                    }
                }
            }

            Column {
                AppBar(
                    state = state,
                    onClose = ::exitApplication
                )
                App(root = root)
            }
        }

    }
}

@Composable
fun WindowScope.AppBar(
    modifier: Modifier = Modifier,
    state: WindowState,
    onClose: () -> Unit,
    onMinimize: () -> Unit = { state.isMinimized = state.isMinimized.not() },
    onMaximize: () -> Unit = {
        state.placement = if (state.placement == WindowPlacement.Maximized)
            WindowPlacement.Floating else WindowPlacement.Maximized
    },
){
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color.Transparent),
        contentAlignment = Alignment.Center
    )
    {
            Row(
                modifier = Modifier.padding(8.dp).fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            )
            {
                Icon(
                    Icons.Filled.Star,
                    contentDescription = "AppIcon"
                )
                Text(
                    text = "Application Name",
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 18.sp
                    )
                )
            }

        WindowDraggableArea {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            )
            {
                IconButton(onClick = onMinimize) {
                    Icon(
                        modifier = Modifier.size(14.dp),
                        imageVector = Minimize,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
                val isFloating = state.placement == WindowPlacement.Floating
                IconButton(onClick = onMaximize) {
                    Icon(
                        modifier = Modifier.size(14.dp),
                        imageVector =  if (isFloating) Expand else Compress ,
                        contentDescription = null,
                        tint = Color.White)
                }
                IconButton(onClick = onClose) {
                    Icon(
                        modifier = Modifier.size(14.dp),
                        imageVector = Close,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
        }
    }
}
