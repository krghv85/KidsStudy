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
import android.widget.Toast;

import com.tradexl.kidsstudy.adapters.TableAdapter;
import com.tradexl.kidsstudy.adapters.TableOfAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Raghav on 05-Jan-18.
 */

public class TableActivity extends AppCompatActivity implements TextToSpeech.OnInitListener, TableAdapter.ClickListenerInterface {

    private static final String TAG = TableActivity.class.getSimpleName();
    private TextToSpeech textToSpeech;
    private String speechText;
    private int tableOf;
    private int count = 0;
    private GridLayoutManager recyclerViewLayoutManager;
    @BindView(R.id.table_recycler_view)
    RecyclerView recyclerView;
    private TableAdapter tableAdapter;
    private List<String> tableList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_table);

        ((App) getApplication()).getAppComponents().inject(TableActivity.this);
        ButterKnife.bind(TableActivity.this);


        textToSpeech = new TextToSpeech(this, this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            speechText = bundle.getString("speechText");
            tableOf = Integer.parseInt(bundle.getString("tableof"));
//            showToast(speechText+" "+tableOf);
        }

        generateTabels();
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
        count = 1;
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            public void run() {

                int mul = tableOf * count;
                tableList.add(tableOf+" * "+count+" = "+mul);

                if (count == 1) {
                    setDashAdapter();
                } else {
//                    adapter.notifyDataSetChanged();
                    tableAdapter.notifyItemChanged(tableList.size());
                    recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount());
                    speakOut(tableOf+" * "+count+" = "+mul);

                }

                if (count++ < 10) {
                    handler.postDelayed(this, 2500);
                }


            }
        };
        handler.post(runnable);
    }

    private void setDashAdapter() {

        recyclerViewLayoutManager = new GridLayoutManager(TableActivity.this, 1);
        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        recyclerView.scrollToPosition(tableList.size());
        tableAdapter = new TableAdapter(TableActivity.this, tableList, TableActivity.this, recyclerView);
        recyclerView.setAdapter(tableAdapter);

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
        Toast.makeText(TableActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        textToSpeech = new TextToSpeech(this, this);
        super.onResume();
    }

    @Override
    public void clickListener(int position) {
    speakOut(tableList.get(position).toString());
    }
}
