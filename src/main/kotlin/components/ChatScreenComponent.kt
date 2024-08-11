package components

import com.arkivanov.decompose.ComponentContext

class ChatScreenComponent(
    componentContext: ComponentContext,
    private val ip: String,
    private val port: Int,
    private val onGoBack: () -> Unit
): ComponentContext by componentContext {

}