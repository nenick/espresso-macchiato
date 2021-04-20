package de.nenick.espressomacchiato.internals

class SimpleTimer(
    private val timeoutMilliseconds: Long
) {
    private val startTime = System.currentTimeMillis()
    fun isTimeout() = System.currentTimeMillis() - startTime > timeoutMilliseconds
}