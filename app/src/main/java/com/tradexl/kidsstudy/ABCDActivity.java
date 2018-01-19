package com.tradexl.kidsstudy;

import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tradexl.kidsstudy.adapters.ABCDAdapter;
import com.tradexl.kidsstudy.adapters.CountingAdapter;
import com.tradexl.kidsstudy.adapters.TableAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Raghav on 05-Jan-18.
 */

public class ABCDActivity extends AppCompatActivity implements TextToSpeech.OnInitListener, ABCDAdapter.ClickListenerInterface {

    private static final String TAG = ABCDActivity.class.getSimpleName();
    private TextToSpeech textToSpeech;
    private String speechText;
    private int tableOf;
    private int count = 0;
    private GridLayoutManager recyclerViewLayoutManager;
    @BindView(R.id.abcd_recycler_view)
    RecyclerView recyclerView;
    private ABCDAdapter tableAdapter;
    private List<String> abcdList = new ArrayList<>();
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    private ABCDAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_abcd);

        ((App) getApplication()).getAppComponents().inject(ABCDActivity.this);
        ButterKnife.bind(ABCDActivity.this);

        title.setText("ABCD");

        textToSpeech = new TextToSpeech(this, this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            speechText = bundle.getString("speechText");
//            showToast(speechText+" "+tableOf);
        }
        generateCounting();
    }

    private void generateCounting() {
      /*  for (int i = 65; i <= 90; i++) {
            System.out.println((char) 65);
            abcdList.add((char) i + "");
        }*/

        count = 65;
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            public void run() {
//                showToast(count + "");
                abcdList.add((char) count + "");
                if (count == 65) {
                    setAdapter();
                } else {
//                    adapter.notifyDataSetChanged();
                    adapter.notifyItemChanged(abcdList.size());
                    recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount());
                    speakOut((char) count + "");
                }

                if (count++ < 90) {
                    handler.postDelayed(this, 1500);
                }
            }
        };
        handler.post(runnable);
    }


    private void setAdapter() {
        recyclerViewLayoutManager = new GridLayoutManager(ABCDActivity.this, 3);
        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        recyclerView.scrollToPosition(abcdList.size());
        adapter = new ABCDAdapter(ABCDActivity.this, abcdList, ABCDActivity.this, recyclerView);
        recyclerView.setAdapter(adapter);

    }

    @OnClick({R.id.back})
    void onclickListeners(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }

    @Override
    public void onInit(int status) {
        setTTS(status);
    }

    private void setTTS(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = textToSpeech.setLanguage(Locale.ENGLISH);
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
//                speakOut(speechText);
            }
        } else {
            Log.e("TTS", "Initilization Failed!");
        }
    }


    private void generateTabels() {

        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            public void run() {


                handler.postDelayed(this, 2000);


            }
        };
        handler.post(runnable);
    }

    private void speakOut(String count) {
        textToSpeech.speak(count, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    protected void onPause() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();

        }
        super.onPause();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onDestroy() {
        // Don't forget to shutdown textToSpeech!
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }

    protected void showToast(String msg) {
        Toast.makeText(ABCDActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        textToSpeech = new TextToSpeech(this, this);
        super.onResume();
    }

    @Override
    public void clickListener(int position) {
        speakOut(abcdList.get(position).toString());
    }
}
