package com.tradexl.kidsstudy;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MicActivity extends AppCompatActivity {
    private final int REQ_CODE_SPEECH_INPUT = 100;
    @BindView(R.id.txtSpeechInput)
    TextView txtSpeechInput;
    @BindView(R.id.btnSpeak)
    ImageButton btnSpeak;
    @BindView(R.id.list)
    ImageView list;
    private String txtSpeechString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_mic);
        ButterKnife.bind(MicActivity.this);


    }

    @OnClick({R.id.btnSpeak, R.id.list})
    void onclickListeners(View view) {
        switch (view.getId()) {
            case R.id.btnSpeak:
                promptSpeechInput();
                break;
            case R.id.list:
                Intent intent = new Intent(MicActivity.this, DashActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Receiving speech input
     */

    protected void showToast(String msg) {
        Toast.makeText(MicActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
//                    txtSpeechInput.setText(result.get(0));
//                    showToast(txtSpeechString);
                    txtSpeechString = result.get(0).toString();
                    if (txtSpeechString.toLowerCase().contains("counting") || txtSpeechString.toLowerCase().contains("123")) {
                        Intent intent = new Intent(MicActivity.this, CountingActivity.class);
                        intent.putExtra("speechText", txtSpeechString);
                        startActivity(intent);
                    } else {
                        System.out.println("not found");
                    }
                    if (txtSpeechString.toLowerCase().contains("table")) {
                        String tableof = txtSpeechString.toString().replaceAll("[^0-9]", "");
                        Intent intent = new Intent(MicActivity.this, TableActivity.class);
                        intent.putExtra("speechText", txtSpeechString);
                        intent.putExtra("tableof", tableof);
                        startActivity(intent);

                    } else {
                        System.out.println("not found");
                    }

                    if (txtSpeechString.toLowerCase().contains("abcd") || txtSpeechString.toLowerCase().contains("alphabets")) {
                        Intent intent = new Intent(MicActivity.this, ABCDActivity.class);
                        intent.putExtra("speechText", txtSpeechString);
                        startActivity(intent);

                    } else {
                        System.out.println("not found");
                    }

                }
                break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
