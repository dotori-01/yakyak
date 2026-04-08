package com.app.smh;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView; // 배너 메시지용

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fabChatbot;
    private BottomNavigationView bottomNavigationView;
    private TextView tvBannerMessage; // [추가] 배너 메시지용
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setupBottomNavigation();
        setupFabChatbot();
        applyHomeBannerMessage();   // [추가] 처음 홈 화면이 열릴 때 저장된 문구 적용
    }

    @Override
    protected void onResume() {   // [추가] 내정보 화면에서 돌아왔을 때 다시 문구 반영
        super.onResume();
        applyHomeBannerMessage();
    }
    private void initViews() {
        fabChatbot = findViewById(R.id.fab_chatbot);
        bottomNavigationView = findViewById(R.id.bottom_nav);
        tvBannerMessage = findViewById(R.id.tv_banner_message);   // [추가] 배너 TextView 연결
    }

    private void setupBottomNavigation() {
        // 시작 시 홈 탭 선택 상태로 표시
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
        // 하단 네비게이션 클릭 이벤트 처리
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                // 이미 현재 화면이 홈 화면이므로 그대로 유지
                return true;
            } else if (id == R.id.nav_camera) {
                // TODO: 스캔 화면으로 이동 또는 Fragment 교체
                return true;
            } else if (id == R.id.nav_profile) {
                // TODO: 내정보 화면으로
                Intent intent = new Intent(MainActivity.this, MyPageLoggedOutActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }

            return false;
        });
    }


    private void setupFabChatbot() {
        fabChatbot.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ChatbotActivity.class);
            startActivity(intent);
        });
    }
    private void applyHomeBannerMessage() {   // [추가] 저장된 홈 문구를 배너에 적용하는 메서드
        String message = SettingsManager.getHomeMessage(this);   // [추가] SharedPreferences에서 홈 문구 읽기
        tvBannerMessage.setText(message);   // [추가] 배너 TextView에 반영
    }

}