package de.nenick.espressomacchiato.sampleapp.test

import de.nenick.espressomacchiato.dialog.EspAlertDialog
import de.nenick.espressomacchiato.widget.EspButton
import de.nenick.espressomacchiato.widget.EspEditText
import de.nenick.espressomacchiato.view.EspView
import de.nenick.espressomacchiato.espresso.view.EnabledAssertions
import de.nenick.espressomacchiato.sampleapp.R

@Suppress("UNCHECKED_CAST")
class StatefulButton(id: Int, interactions: StatefulButton.() -> Unit) : EspButton(id, interactions as EspButton.() -> Unit), EnabledAssertions

@Suppress("TestFunctionName", "UNCHECKED_CAST")
class LoginPage(interactions: LoginPage.() -> Unit) : EspView(R.id.layout_activity_login, interactions as EspView.() -> Unit) {

    fun ConfirmButton(interactions: StatefulButton.() -> Unit) = StatefulButton(R.id.confirm, interactions)

    fun UsernameInput(interactions: EspEditText.() -> Unit) = EspEditText(R.id.username, interactions)

    fun PasswordInput(interactions: EspEditText.() -> Unit) = EspEditText(R.id.password, interactions)

    fun ErrorDialog(interactions: EspAlertDialog.() -> Unit) = EspAlertDialog(interactions)
}