package navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import components.ChatScreenComponent
import components.HomeScreenComponent
import kotlinx.serialization.Serializable

class DecomposeNav(
    componentContext: ComponentContext
) : ComponentContext by componentContext {

    private val _navigation = StackNavigation<Configuration>()

    val childStack = childStack(
        source = _navigation,
        serializer = Configuration.serializer(),
        initialConfiguration = Configuration.HomeScreen,
        handleBackButton = true,
        childFactory = ::createChild
    )

    @OptIn(ExperimentalDecomposeApi::class)
    private fun createChild(
        config: Configuration,
        context: ComponentContext
    ) : Child
    {
        return when (config){
            is Configuration.HomeScreen -> Child.HomeScreen(
                component = HomeScreenComponent(
                    componentContext = context,
                    onGoChat = {ip, port ->
                        _navigation.pushNew(Configuration.ChatScreen(ip, port))
                    }
                )
            )
            is Configuration.ChatScreen -> Child.ChatScreen(
                component = ChatScreenComponent(
                    componentContext = context,
                    ip = config.ip,
                    port = config.port,
                    onGoBack = {
                        _navigation.pop()
                    }
                )
            )
        }
    }


    sealed class Child {
        data class HomeScreen(val component: HomeScreenComponent) : Child()
        data class ChatScreen(val component: ChatScreenComponent) : Child()
    }

    @Serializable
    sealed class Configuration {
        @Serializable
        data object HomeScreen : Configuration()

        @Serializable
        data class ChatScreen(val ip: String, val port: Int) : Configuration()
    }

}

