package ckdrn777.gmail.com.a0608kakaologin;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.exception.KakaoException;

import java.security.MessageDigest;

public class MainActivity extends AppCompatActivity {
    //안드로이드 키해시를 로그로 출력하는 매소드
    //키해시 : 안드로이드 앱마다 별도로 생성되는 ID 같은 개념입니다.
    //한 번만 호출하면 됩니다
    private void getHashKey() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo("ckdrn777.gmail.com.a0608kakaologin", PackageManager.GET_SIGNATURES);
            Log.e("ddd", "dkdkdk");
            Toast.makeText(this, "왜", Toast.LENGTH_LONG).show();
            for (Signature signature : info.signatures) {
                //SHA는 암호화 알고리즘의 종류이고 MessageDigest는 암호화 관현
                //모듈입니다 이 단어의 약자는 md 입니다
                MessageDigest md = MessageDigest.getInstance("SHA");

                md.update(signature.toByteArray());
                //키 해시 출력
                Log.e("키 해시", Base64.encodeToString(md.digest(), Base64.DEFAULT));
                Toast.makeText(this, Base64.encodeToString(md.digest(), Base64.DEFAULT), Toast.LENGTH_LONG).show();
            }


        } catch (Exception e) {
            Log.e("해시 가져오기 실패", e.getMessage());

        }
    }

    class SessionCallback implements ISessionCallback {
        @Override
        public void onSessionOpened() {
            UserManagement.requestMe(new MeResponseCallback() {
                @Override
                public void onSessionClosed(ErrorResult errorResult) {

                }

                @Override
                public void onNotSignedUp() {

                }

                @Override
                public void onSuccess(UserProfile result) {
                    Log.e("사용자 정보", result.toString());
                    Log.e("사용자 이름", result.getNickname());

                }

                //로그인 실패한 경우
                @Override
                public void onFailure(ErrorResult result) {
                    finish();
                }
            });
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            Log.e("세션 연결 실패", exception.getMessage());
        }
    }

    SessionCallback sessionCallback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //키해시를 출력하기 위한 메소드 호출
        //getHashKey();

        sessionCallback = new SessionCallback();
        Session.getCurrentSession().addCallback(sessionCallback);
    }
}
