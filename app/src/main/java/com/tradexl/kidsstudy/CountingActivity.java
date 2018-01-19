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
import android.widget.Toast;

import com.tradexl.kidsstudy.adapters.CountingAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Raghav on 05-Jan-18.
 */

public class CountingActivity extends AppCompatActivity implements TextToSpeech.OnInitListener, CountingAdapter.ClickListenerInterface {

    private static final String TAG = CountingActivity.class.getSimpleName();
    ;
    private TextToSpeech textToSpeech;
    private String speechText;
    private List<String> countinList = new ArrayList<>();
    @BindView(R.id.counting_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.back)
    ImageView back;
    private GridLayoutManager recyclerViewLayoutManager;
    private CountingAdapter adapter;
    private int count = 1;
    private int mStatus = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_counting);
        ButterKnife.bind(CountingActivity.this);

        textToSpeech = new TextToSpeech(this, this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            speechText = bundle.getString("speechText");
//            showToast(speechText);
        }

        generateCounting();
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
        mStatus=status;
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

    private void setDashAdapter() {

        recyclerViewLayoutManager = new GridLayoutManager(CountingActivity.this, 4);
        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        recyclerView.scrollToPosition(countinList.size());
        adapter = new CountingAdapter(CountingActivity.this, countinList, CountingActivity.this,recyclerView);
        recyclerView.setAdapter(adapter);

    }

    private void generateCounting() {
        count = 1;
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            public void run() {
//                showToast(count + "");
                countinList.add(count + "");
                if (count == 1) {
                    setDashAdapter();
                } else {
//                    adapter.notifyDataSetChanged();
                    adapter.notifyItemChanged(countinList.size());
                    recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount());
                    speakOut(count+"");


                }

                if (count++ < 100) {
                    handler.postDelayed(this, 1500);
                }
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
        Toast.makeText(CountingActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        textToSpeech = new TextToSpeech(this, this);
        super.onResume();
    }

    @Override
    public void clickListener(int position) {
        speakOut(countinList.get(position).toString());
    }
}
