package com.guthub.duderazm.hellouser;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.redmadrobot.inputmask.MaskedTextChangedListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class IntroActivity extends AppCompatActivity {

    private static final int MIN_LOGIN_LENGTH = 5;
    private static final int PASSWORD_MIN_LENGTH = 8;

    @BindView(R.id.hint_text_view)
    TextView mHintTextView;

    @BindView(R.id.login_button)
    Button mLoginButton;

    @BindView(R.id.login_edit_text)
    EditText mLoginEditText;

    @BindView(R.id.password_edit_text)
    EditText mPasswordEditText;

    @BindView(R.id.phone_edit_text)
    EditText mPhoneEditText;

    boolean isLoginFieldCorrect = false;
    boolean isPhoneFieldCorrect = false;
    boolean isPasswordCorrect = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        if (Utils.readIsAlreadyLoggedIn(this)) {
            goToMainActivity();
        }

        ButterKnife.bind(this);
        initMaskForPhoneField();
        enableLoginButton(false);

    }

    @OnClick(R.id.login_button)
    void login() {
        if (isInputDataCorrect()) {
            Utils.writeUserLogin(this, mLoginEditText.getText().toString());
            Utils.writeIsAlreadyLoggedIn(this, true);
            goToMainActivity();

        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.invalid_input_data_message), Toast.LENGTH_LONG).show();
        }
    }

    @OnTextChanged(value = R.id.login_edit_text,
            callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void afterLoginInput(Editable editable) {
        isLoginFieldCorrect = editable.length() > MIN_LOGIN_LENGTH;
    }

    @OnTextChanged(value = R.id.password_edit_text,
            callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void afterPasswordInput(Editable editable) {
        isPasswordCorrect = editable.length() >= PASSWORD_MIN_LENGTH;
        if (!isPasswordCorrect) {
            mPasswordEditText.setError("password it easy! minimum " + PASSWORD_MIN_LENGTH + " digits");
        } else {
            mPasswordEditText.setError(null);
            Utils.writePassword(this, editable.toString());
        }
    }


    // >>> private methods

    private void initMaskForPhoneField() {
        final MaskedTextChangedListener listener = new MaskedTextChangedListener(
                "+7 ([000]) [000] [00] [00]",
                true,
                mPhoneEditText,
                null,
                new ValueListener()
        );

        mPhoneEditText.addTextChangedListener(listener);
        mPhoneEditText.setOnFocusChangeListener(listener);
        mPhoneEditText.setHint(listener.placeholder());
    }

    private boolean isInputDataCorrect() {
        return isLoginFieldCorrect && isPhoneFieldCorrect && isPasswordCorrect;
    }

    private void enableLoginButton(boolean enabled) {
        mLoginButton.setEnabled(enabled);
    }

    private void goToMainActivity() {
        startActivity(MainActivity.createExplicitIntent(getApplicationContext(), Utils.readUserLogin(this)));
    }

    // <<< private methods

    private class ValueListener implements MaskedTextChangedListener.ValueListener {

        @Override
        public void onTextChanged(boolean maskFilled, String value) {
            isPhoneFieldCorrect = maskFilled;
            if (maskFilled) {
                enableLoginButton(true);
                mHintTextView.setVisibility(View.INVISIBLE);
                Utils.hideKeyBoard(mPhoneEditText, getApplicationContext());
            } else {
                enableLoginButton(true);
                mHintTextView.setVisibility(View.VISIBLE);
            }
        }
    }

}