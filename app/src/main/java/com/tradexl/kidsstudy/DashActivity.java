package com.tradexl.kidsstudy;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.tradexl.kidsstudy.adapters.ABCDAdapter;
import com.tradexl.kidsstudy.adapters.DashAdapter;
import com.tradexl.kidsstudy.adapters.TableOfAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Raghav on 02-Jan-18.
 */

public class DashActivity extends AppCompatActivity implements DashAdapter.ClickListenerInterface {
    private DashAdapter dashAdapter;
    private List<String> list = new ArrayList<>();
    private List<String> tablelist = new ArrayList<>();
    @BindView(R.id.dash_recycler_view)
    RecyclerView recyclerView;
    private RecyclerView.LayoutManager recyclerViewLayoutManager;
    @BindView(R.id.mic)
    ImageView mic;
    private PopupWindow cityPopupWindow;
    private TableOfAdapter tableAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_dash);

        ((App)getApplication()).getAppComponents().inject(DashActivity.this);

        ButterKnife.bind(DashActivity.this);

        setDashAdapter();

    }

    @OnClick({R.id.mic})
    void onclickListeners(View view) {
        switch (view.getId()) {
            case R.id.mic:
//                Intent intent=new Intent(DashActivity.this,MicActivity.class);
//                startActivity(intent);
                finish();
                break;
        }
    }

    private void setDashAdapter() {
        String[] dashTitle = getResources().getStringArray(R.array.dash_item);
        if (list != null) {
            list.clear();
        }
        for (int i = 0; i < dashTitle.length; i++) {
            list.add(dashTitle[i]);
        }
        recyclerViewLayoutManager = new GridLayoutManager(DashActivity.this, 2);
        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        dashAdapter = new DashAdapter(DashActivity.this, list, DashActivity.this);
        recyclerView.setAdapter(dashAdapter);

    }

    @Override
    public void clickListener(int position) {
        if (list.get(position).toString().equals("Counting") ) {
            Intent intent = new Intent(DashActivity.this, CountingActivity.class);
            startActivity(intent);
        }
        if (list.get(position).toString().equals("ABCD")) {
            Intent intent = new Intent(DashActivity.this, ABCDActivity.class);
            startActivity(intent);
        }
        if (list.get(position).toString().equals("Tabels")) {
            setTablesPopupWindow();
        }
    }

    protected void showToast(String msg) {
        Toast.makeText(DashActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void setTablesPopupWindow() {
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        View customView = inflater.inflate(R.layout.table_popup, null);
        cityPopupWindow = new PopupWindow(
                customView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        if (Build.VERSION.SDK_INT >= 21) {
            cityPopupWindow.setElevation(5.0f);
        }

        final String[] tableTitle = getResources().getStringArray(R.array.tables_item);
        if (tablelist != null) {
            tablelist.clear();
        }
        for (int i = 0; i < tableTitle.length; i++) {
            tablelist.add(tableTitle[i]);
        }

        CardView cardView = (CardView) customView.findViewById(R.id.parentlayout);
        ListView listView = (ListView) customView.findViewById(R.id.listView);
        tableAdapter = new TableOfAdapter(DashActivity.this,tablelist);
        listView.setAdapter(tableAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cityPopupWindow.dismiss();
                String tableof= tablelist.get(position).toString().replaceAll("[^0-9]", "");
                Intent intent = new Intent(DashActivity.this, TableActivity.class);
                intent.putExtra("tableof",tableof);
                startActivity(intent);
            }
        });
        cityPopupWindow.showAtLocation(cardView, Gravity.CENTER, 0, 0);
    }

}
