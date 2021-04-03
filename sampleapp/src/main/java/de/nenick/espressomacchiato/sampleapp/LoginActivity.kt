package de.nenick.espressomacchiato.sampleapp

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

/** Dummy Login activity with only fail logic  */
class LoginActivity : AppCompatActivity() {
    private var password: EditText? = null
    private var username: EditText? = null
    private var confirmButton: Button? = null
    private var errorMessage: TextView? = null
    private val textWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            // toggle only after text changed
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            // toggle only after text changed
        }

        override fun afterTextChanged(s: Editable) {
            toggleConfirmButton()
        }
    }
    private val clickListener = View.OnClickListener {
        errorMessage!!.setText(R.string.error_invalid_username_password)
        AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage("Wrong username or password.")
                .setPositiveButton("ok") { _, _ -> /* does automatically dismiss it self */ }
                .show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        username = findViewById<View>(R.id.username) as EditText
        password = findViewById<View>(R.id.password) as EditText
        confirmButton = findViewById<View>(R.id.confirm) as Button
        errorMessage = findViewById<View>(R.id.textViewErrorMessage) as TextView
        username!!.addTextChangedListener(textWatcher)
        password!!.addTextChangedListener(textWatcher)
        confirmButton!!.setOnClickListener(clickListener)
        toggleConfirmButton()
    }

    private fun toggleConfirmButton() {
        val hasUsername = username!!.length() > 0
        val hasPassword = password!!.length() > 0
        confirmButton!!.isEnabled = hasUsername && hasPassword
    }
}