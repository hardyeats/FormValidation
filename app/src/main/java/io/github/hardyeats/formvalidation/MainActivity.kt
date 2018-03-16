package io.github.hardyeats.formvalidation

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Patterns
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import kotlinx.android.synthetic.main.activity_main.*
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val observableName = RxTextView.textChanges(et_name)
                .map { it.isNotEmpty() && it.contains(" ") }
        val observableEmail = RxTextView.textChanges(et_email)
                .map { Pattern.matches(Patterns.EMAIL_ADDRESS.pattern(), it) }

        val isValid: Observable<Boolean> = Observable.combineLatest<Boolean, Boolean, Boolean>(
                observableName,
                observableEmail,
                BiFunction { isValidName, isValidEmail -> isValidName && isValidEmail })

        isValid
                .subscribe {
                    if(it) btn_ok.background.level = 1
                    else btn_ok.background.level = 0
                }
    }
}
