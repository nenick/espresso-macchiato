package de.nenick.espressomacchiato.sampleapp.test

import de.nenick.espressomacchiato.elements.basics.EspButton
import de.nenick.espressomacchiato.elements.basics.EspEditText
import de.nenick.espressomacchiato.elements.basics.EspView
import de.nenick.espressomacchiato.espresso.view.EnabledAssertions
import de.nenick.espressomacchiato.sampleapp.R

class StatefulButton(id: Int, interactions: StatefulButton.() -> Unit) : EspButton(id, interactions as EspButton.() -> Unit), EnabledAssertions

class LoginPage(interactions: LoginPage.() -> Unit) : EspView(R.id.layout_activity_login) {
    init {
        interactions(this)
    }

    fun anyFunnyUnscoped() {}

    fun ConfirmButton(interactions: StatefulButton.() -> Unit) = StatefulButton(R.id.confirm, interactions)

    fun UsernameInput(interactions: EspEditText.() -> Unit) = EspEditText(R.id.username, interactions)

    fun PasswordInput(interactions: EspEditText.() -> Unit) = EspEditText(R.id.password, interactions)

}