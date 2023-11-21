package com.Padhantueducation.view_section.Login.LoginSection;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.Padhantueducation.R;
import com.Padhantueducation.Session.Session;
import com.Padhantueducation.remote.APIService;
import com.Padhantueducation.models.OtpResult;
import com.Padhantueducation.remote.ApiClient;
import com.Padhantueducation.view_section.MainActi.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.Padhantueducation.CommonClasses.Utils_common.dismissProgressDialog;
import static com.Padhantueducation.CommonClasses.Utils_common.showProgressDialog;

public class OtpActivity extends AppCompatActivity implements View.OnFocusChangeListener, View.OnKeyListener, TextWatcher {
    private EditText mPinFirstDigitEditText;
    private EditText mPinSecondDigitEditText;
    private EditText mPinThirdDigitEditText;
    private EditText mPinForthDigitEditText;
    private EditText mPinFifthDigitEditText;
    private EditText mPinHiddenEditText;
    String user_id;
    Session session;
    @Override
    public void afterTextChanged(Editable s) {
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    /**
     * Hides soft keyboard.
     *
     * @param editText EditText which has focus
     */
    public void hideSoftKeyboard(EditText editText) {
        if (editText == null)
            return;

        InputMethodManager imm = (InputMethodManager) getSystemService(Service.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    /**
     * Initialize EditText fields.
     */
    private void init() {
        session=new Session(OtpActivity.this);

        mPinFirstDigitEditText = (EditText) findViewById(R.id.pin_first_edittext);
        mPinSecondDigitEditText = (EditText) findViewById(R.id.pin_second_edittext);
        mPinThirdDigitEditText = (EditText) findViewById(R.id.pin_third_edittext);
        mPinForthDigitEditText = (EditText) findViewById(R.id.pin_forth_edittext);
        mPinFifthDigitEditText = (EditText) findViewById(R.id.pin_fifth_edittext);
        mPinHiddenEditText = (EditText) findViewById(R.id.pin_hidden_edittext);
    }
Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        submit=findViewById(R.id.submit);
        init();
        setPINListeners();

        if (getIntent()!=null)
        {
            user_id=getIntent().getStringExtra("user_id");
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String first=mPinFirstDigitEditText.getText().toString();
                String second=mPinSecondDigitEditText.getText().toString();
                String third=mPinThirdDigitEditText.getText().toString();
                String fourth=mPinForthDigitEditText.getText().toString();
                String fifth=mPinFifthDigitEditText.getText().toString();
                String motp = new StringBuilder(first).append(second).append(third).append(fourth).append(fifth).toString();
                otp_api_call(user_id,motp);

            }
        });
        
        

        
    }

    private void otp_api_call(String user_id,String motp) {
            showProgressDialog(OtpActivity.this);
            APIService service = ApiClient.getClient().create(APIService.class);

            Call<OtpResult> userCall = service.VerifyOtp(user_id, motp);
            userCall.enqueue(new Callback<OtpResult>() {
                @Override
                public void onResponse(Call<OtpResult> call, Response<OtpResult> response) {
                    dismissProgressDialog();
                    //onSignupSuccess();
                    if(response.body().getResult().equals("true")) {
                        Log.e("TAG", "response 33: "+new Gson().toJson(response.body()) );
                     //   SharedPrefManager.getInstance(getApplicationContext()).userLogin(response.body().getData());
                        String user_id=response.body().getData().getId();
                        String mobile=response.body().getData().getMobile();
                        String email=response.body().getData().getEmail();
                        String name=response.body().getData().getName();

                        session.setLogin(true);
                        session.setUserId(user_id);
                        session.setMobile(mobile,email);
                        session.setUser_name(name);


                        startActivity(new Intent(OtpActivity.this, MainActivity.class));
                        finish();
                    }else {
                        Toast.makeText(OtpActivity.this, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<OtpResult> call, Throwable t) {
                    dismissProgressDialog();
                    Log.d("onFailure", t.toString());
                }
            });
        }


    @Override
        public void onFocusChange(View v, boolean hasFocus) {
            final int id = v.getId();
            switch (id) {
                case R.id.pin_first_edittext:
                    if (hasFocus) {
                        setFocus(mPinHiddenEditText);
                        showSoftKeyboard(mPinHiddenEditText);
                    }
                    break;

                case R.id.pin_second_edittext:
                    if (hasFocus) {
                        setFocus(mPinHiddenEditText);
                        showSoftKeyboard(mPinHiddenEditText);
                    }
                    break;

                case R.id.pin_third_edittext:
                    if (hasFocus) {
                        setFocus(mPinHiddenEditText);
                        showSoftKeyboard(mPinHiddenEditText);
                    }
                    break;

                case R.id.pin_forth_edittext:
                    if (hasFocus) {
                        setFocus(mPinHiddenEditText);
                        showSoftKeyboard(mPinHiddenEditText);
                    }
                    break;

                case R.id.pin_fifth_edittext:
                    if (hasFocus) {
                        setFocus(mPinHiddenEditText);
                        showSoftKeyboard(mPinHiddenEditText);
                    }
                    break;
                default:
                    break;
            }
        }

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                final int id = v.getId();
                switch (id) {
                    case R.id.pin_hidden_edittext:
                        if (keyCode == KeyEvent.KEYCODE_DEL) {
                            if (mPinHiddenEditText.getText().length() == 5)
                                mPinFifthDigitEditText.setText("");
                            else if (mPinHiddenEditText.getText().length() == 4)
                                mPinForthDigitEditText.setText("");
                            else if (mPinHiddenEditText.getText().length() == 3)
                                mPinThirdDigitEditText.setText("");
                            else if (mPinHiddenEditText.getText().length() == 2)
                                mPinSecondDigitEditText.setText("");
                            else if (mPinHiddenEditText.getText().length() == 1)
                                mPinFirstDigitEditText.setText("");

                            if (mPinHiddenEditText.length() > 0)
                                mPinHiddenEditText.setText(mPinHiddenEditText.getText().subSequence(0, mPinHiddenEditText.length() - 1));

                            return true;
                        }

                        break;

                    default:
                        return false;
                }
            }

            return false;
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            setDefaultPinBackground(mPinFirstDigitEditText);
            setDefaultPinBackground(mPinSecondDigitEditText);
            setDefaultPinBackground(mPinThirdDigitEditText);
            setDefaultPinBackground(mPinForthDigitEditText);
            setDefaultPinBackground(mPinFifthDigitEditText);

            if (s.length() == 0) {
                setFocusedPinBackground(mPinFirstDigitEditText);
                mPinFirstDigitEditText.setText("");
            } else if (s.length() == 1) {
                setFocusedPinBackground(mPinSecondDigitEditText);
                mPinFirstDigitEditText.setText(s.charAt(0) + "");
                mPinSecondDigitEditText.setText("");
                mPinThirdDigitEditText.setText("");
                mPinForthDigitEditText.setText("");
                mPinFifthDigitEditText.setText("");
            } else if (s.length() == 2) {
                setFocusedPinBackground(mPinThirdDigitEditText);
                mPinSecondDigitEditText.setText(s.charAt(1) + "");
                mPinThirdDigitEditText.setText("");
                mPinForthDigitEditText.setText("");
                mPinFifthDigitEditText.setText("");
            } else if (s.length() == 3) {
                setFocusedPinBackground(mPinForthDigitEditText);
                mPinThirdDigitEditText.setText(s.charAt(2) + "");
                mPinForthDigitEditText.setText("");
                mPinFifthDigitEditText.setText("");
            } else if (s.length() == 4) {
                setFocusedPinBackground(mPinFifthDigitEditText);
                mPinForthDigitEditText.setText(s.charAt(3) + "");
                mPinFifthDigitEditText.setText("");
            } else if (s.length() == 5) {
                setDefaultPinBackground(mPinFifthDigitEditText);
                mPinFifthDigitEditText.setText(s.charAt(4) + "");

                hideSoftKeyboard(mPinFifthDigitEditText);
            }
        }

        /**
         * Sets default PIN background.
         *
         * @param editText edit text to change
         */
        private void setDefaultPinBackground(EditText editText) {
          //  setViewBackground(editText, getResources().getDrawable(R.drawable.textfield_default_holo_light));
        }

        /**
         * Sets focus on a specific EditText field.
         *
         * @param editText EditText to set focus on
         */
        public static void setFocus(EditText editText) {
            if (editText == null)
                return;

            editText.setFocusable(true);
            editText.setFocusableInTouchMode(true);
            editText.requestFocus();
        }

        /**
         * Sets focused PIN field background.
         *
         * @param editText edit text to change
         */
        private void setFocusedPinBackground(EditText editText) {
         //   setViewBackground(editText, getResources().getDrawable(R.drawable.textfield_focused_holo_light));
        }

        /**
         * Sets listeners for EditText fields.
         */
        private void setPINListeners() {
            mPinHiddenEditText.addTextChangedListener(this);

            mPinFirstDigitEditText.setOnFocusChangeListener(this);
            mPinSecondDigitEditText.setOnFocusChangeListener(this);
            mPinThirdDigitEditText.setOnFocusChangeListener(this);
            mPinForthDigitEditText.setOnFocusChangeListener(this);
            mPinFifthDigitEditText.setOnFocusChangeListener(this);

            mPinFirstDigitEditText.setOnKeyListener(this);
            mPinSecondDigitEditText.setOnKeyListener(this);
            mPinThirdDigitEditText.setOnKeyListener(this);
            mPinForthDigitEditText.setOnKeyListener(this);
            mPinFifthDigitEditText.setOnKeyListener(this);
            mPinHiddenEditText.setOnKeyListener(this);
        }

        /**
         * Sets background of the view.
         * This method varies in implementation depending on Android SDK version.
         *
         * @param view       View to which set background
         * @param background Background to set to view
         */
        @SuppressWarnings("deprecation")
        public void setViewBackground(View view, Drawable background) {
            if (view == null || background == null)
                return;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                view.setBackground(background);
            } else {
                view.setBackgroundDrawable(background);
            }
        }

        /**
         * Shows soft keyboard.
         *
         * @param editText EditText which has focus
         */
        public void showSoftKeyboard(EditText editText) {
            if (editText == null)
                return;

            InputMethodManager imm = (InputMethodManager) getSystemService(Service.INPUT_METHOD_SERVICE);
            imm.showSoftInput(editText, 0);
        }

        /**
         * Custom LinearLayout with overridden onMeasure() method
         * for handling software keyboard show and hide events.
         */
        public class MainLayout extends LinearLayout {

            public MainLayout(Context context, AttributeSet attributeSet) {
                super(context, attributeSet);
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
               // inflater.inflate(R.layout.main, this);
            }

            @Override
            protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
                final int proposedHeight = MeasureSpec.getSize(heightMeasureSpec);
                final int actualHeight = getHeight();

                Log.d("TAG", "proposed: " + proposedHeight + ", actual: " + actualHeight);

                if (actualHeight >= proposedHeight) {
                    // Keyboard is shown
                    if (mPinHiddenEditText.length() == 0)
                        setFocusedPinBackground(mPinFirstDigitEditText);
                    else
                        setDefaultPinBackground(mPinFirstDigitEditText);
                }

                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            }
        }
    }
