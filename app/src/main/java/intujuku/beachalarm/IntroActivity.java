package intujuku.beachalarm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnFailureListener;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.android.play.core.tasks.Task;

public class IntroActivity extends AppCompatActivity {
    private final int MY_REQUEST_CODE = 100;
    private AppUpdateManager mAppUpdateManager;
    Handler handle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mAppUpdateManager = AppUpdateManagerFactory.create(getApplicationContext());

// 업데이트 사용 가능 상태인지 체크
        Task<AppUpdateInfo> appUpdateInfoTask = mAppUpdateManager.getAppUpdateInfo();

// 사용가능 체크 리스너를 달아준다
        appUpdateInfoTask.addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo appUpdateInfo) {
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                        && // 유연한 업데이트 사용 시 (AppUpdateType.FLEXIBLE) 사용
                        appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                    try {
                        mAppUpdateManager.startUpdateFlowForResult(
                                appUpdateInfo,
                                // 유연한 업데이트 사용 시 (AppUpdateType.FLEXIBLE) 사용
                                AppUpdateType.IMMEDIATE,
                                // 현재 Activity
                                IntroActivity.this,
                                // 전역변수로 선언해준 Code
                                MY_REQUEST_CODE);
                    } catch (IntentSender.SendIntentException e) {
                        Log.e("AppUpdater", "AppUpdateManager Error", e);
                        e.printStackTrace();
                    }

                }else{
                    handle = new Handler();
                    handle.postDelayed(rIntent, 1000);
                }
            }
        });
        appUpdateInfoTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                handle = new Handler();
                handle.postDelayed(rIntent, 1000);
            }
        });


    }
    Runnable rIntent = new Runnable() {
        @Override
        public void run() {
            Intent intent = new Intent(IntroActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        handle.removeCallbacks(rIntent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_REQUEST_CODE) {
            if (resultCode != RESULT_OK) {
                Log.d("AppUpdate", "Update flow failed! Result code: " + resultCode); // 로그로 코드 확인
                Toast.makeText(this, "업데이트가 필요 합니다.", Toast.LENGTH_SHORT).show();
                finishAffinity(); // 앱 종료
            }
        }
    }
    @Override
    protected void onResume() {
        super.onResume();

//        mAppUpdateManager.getAppUpdateInfo().addOnSuccessListener(
//            new OnSuccessListener<AppUpdateInfo>() {
//                @Override
//                public void onSuccess(AppUpdateInfo appUpdateInfo) {
//                    if (appUpdateInfo.updateAvailability()
//                            == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
//                        // 인 앱 업데이트가 이미 실행중이었다면 계속해서 진행하도록
//                        try {
//                            mAppUpdateManager.startUpdateFlowForResult(appUpdateInfo, AppUpdateType.IMMEDIATE, IntroActivity.this, MY_REQUEST_CODE);
//                        } catch (IntentSender.SendIntentException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            });
    }
}