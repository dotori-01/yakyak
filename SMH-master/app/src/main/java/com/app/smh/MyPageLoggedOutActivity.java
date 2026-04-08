package com.app.smh;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MyPageLoggedOutActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    private LinearLayout layoutLoginRequired;
    private LinearLayout layoutHomeMessage;
    private LinearLayout layoutAlarm;
    private LinearLayout layoutGuardian;
    private LinearLayout layoutContact;
    private LinearLayout layoutLogin;

    private Switch switchDarkMode;
    private Switch switchTts;
    private Switch switchSeniorMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page_logged_out);

        initViews();
        setupBottomNavigation();
        setupClickListeners();
        setupSwitchListeners();
        updateHomeMessagePreview();
    }

    private void initViews() {
        bottomNavigationView = findViewById(R.id.bottom_nav_profile);

        layoutLoginRequired = findViewById(R.id.layout_login_required);
        layoutHomeMessage = findViewById(R.id.layout_home_message);
        layoutAlarm = findViewById(R.id.layout_alarm);
        layoutGuardian = findViewById(R.id.layout_guardian);
        layoutContact = findViewById(R.id.layout_contact);
        layoutLogin = findViewById(R.id.layout_login);

        switchDarkMode = findViewById(R.id.switch_dark_mode);
        switchTts = findViewById(R.id.switch_tts);
        switchSeniorMode = findViewById(R.id.switch_senior_mode);
    }

    private void setupBottomNavigation() {
        bottomNavigationView.setSelectedItemId(R.id.nav_profile);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                Intent intent = new Intent(MyPageLoggedOutActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
                return true;

            } else if (id == R.id.nav_camera) {
                Toast.makeText(this, "스캔 화면은 아직 연결 전입니다.", Toast.LENGTH_SHORT).show();
                return true;

            } else if (id == R.id.nav_profile) {
                return true;
            }

            return false;
        });
    }

    private void setupClickListeners() {
        layoutLoginRequired.setOnClickListener(v ->
                Toast.makeText(this, "로그인 화면으로 이동", Toast.LENGTH_SHORT).show()
        );

        layoutHomeMessage.setOnClickListener(v -> showHomeMessageDialog());

        layoutAlarm.setOnClickListener(v ->
                Toast.makeText(this, "알림 설정 클릭", Toast.LENGTH_SHORT).show()
        );

        layoutGuardian.setOnClickListener(v ->
                Toast.makeText(this, "보호자 관리 클릭", Toast.LENGTH_SHORT).show()
        );

        layoutContact.setOnClickListener(v ->
                Toast.makeText(this, "문의하기 클릭", Toast.LENGTH_SHORT).show()
        );

        layoutLogin.setOnClickListener(v ->
                Toast.makeText(this, "로그인 클릭", Toast.LENGTH_SHORT).show()
        );
    }

    private void setupSwitchListeners() {
        switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) ->
                Toast.makeText(this,
                        isChecked ? "다크 모드 활성화" : "다크 모드 비활성화",
                        Toast.LENGTH_SHORT).show()
        );

        switchTts.setOnCheckedChangeListener((buttonView, isChecked) ->
                Toast.makeText(this,
                        isChecked ? "TTS 설정 활성화" : "TTS 설정 비활성화",
                        Toast.LENGTH_SHORT).show()
        );

        switchSeniorMode.setOnCheckedChangeListener((buttonView, isChecked) ->
                Toast.makeText(this,
                        isChecked ? "고령자 모드 활성화" : "고령자 모드 비활성화",
                        Toast.LENGTH_SHORT).show()
        );
    }

    private void showHomeMessageDialog() {
        final EditText editText = new EditText(this);
        editText.setText(SettingsManager.getHomeMessage(this));
        editText.setSelection(editText.getText().length());
        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        editText.setFilters(new InputFilter[]{ new InputFilter.LengthFilter(40) });
        int padding = (int) (20 * getResources().getDisplayMetrics().density);
        editText.setPadding(padding, padding, padding, padding);

        new AlertDialog.Builder(this)
                .setTitle("홈 문구 설정")
                .setMessage("메인 홈 배너에 표시할 문구를 입력하세요.")
                .setView(editText)
                .setPositiveButton("저장", (dialog, which) -> {
                    String input = editText.getText().toString().trim();

                    if (input.isEmpty()) {
                        Toast.makeText(this, "문구를 입력해주세요.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    SettingsManager.saveHomeMessage(this, input);
                    updateHomeMessagePreview();
                    Toast.makeText(this, "홈 문구가 저장되었습니다.", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("취소", null)
                .show();
    }

    private void updateHomeMessagePreview() {
        android.widget.TextView tvHomeMessageDesc = findViewById(R.id.tv_home_message_desc);
        tvHomeMessageDesc.setText(SettingsManager.getHomeMessage(this));
    }
}