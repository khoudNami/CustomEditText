package com.example.customedittext;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.res.ResourcesCompat;

public class EditTextWithClear extends AppCompatEditText {

    Drawable mClearButtonImage;

    public EditTextWithClear(@NonNull Context context) {
        super(context);
        init();
    }

    public EditTextWithClear(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EditTextWithClear(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {

        mClearButtonImage = ResourcesCompat.getDrawable(getResources(),
                R.drawable.ic_clear_opaque_24dp, null);

//         If the clear (X) button is tapped, clear the text.
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (getCompoundDrawablesRelative()[2] != null) {
                    float clearButtonStart; //used for LTR languages
                    float clearButtonEnd; //used for RTL languages
                    boolean isClearButtonClicked = false;

                    if (getLayoutDirection() == LAYOUT_DIRECTION_RTL) { // Detect the touch in RTL or LTR layout direction.
                        clearButtonEnd = mClearButtonImage.getIntrinsicWidth() + getPaddingStart();
                        if (event.getX() < clearButtonEnd) {
                            isClearButtonClicked = true;
                        }
                    } else {
                        clearButtonStart = (getWidth() - mClearButtonImage.getIntrinsicWidth() - getPaddingEnd());
                        if (event.getX() > clearButtonStart) {
                            isClearButtonClicked = true;
                        }
                    }

                    if (isClearButtonClicked) {// Check for actions if the button is tapped.
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            mClearButtonImage = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_clear_black_24dp, null);
                            showClearButton();
                        }
                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            mClearButtonImage = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_clear_opaque_24dp, null);
                            getText().clear();
                            hideClearButton();
                            return true;
                        }
                    } else {
                        return false;
                    }
                }
                return false;
            }
        });

//         If the text changes, show or hide the clear (X) button.
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                showClearButton();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * Shows the clear (X) button.
     */
    private void showClearButton() {
        setCompoundDrawablesRelativeWithIntrinsicBounds
                (null,                     // Start of text.
                        null,               // Above text.
                        mClearButtonImage,      // End of text.
                        null);           // Below text.
    }

    /**
     * Hides the clear (X) button.
     */
    private void hideClearButton() {
        setCompoundDrawablesRelativeWithIntrinsicBounds
                (null,                     // Start of text.
                        null,               // Above text.
                        null,              // End of text.
                        null);           // Below text.
    }
}
