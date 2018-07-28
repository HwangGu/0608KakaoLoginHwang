package ckdrn777.gmail.com.a0608kakaologin;

import android.app.Activity;
import android.app.Application;

import com.kakao.auth.KakaoSDK;

public class GlobalApplication extends Application {
    //인스턴스 변수 선언
    //volatile : 멀티 스레드 프로그래밍에서 한 번에 작업을 수행하도록 해주는 예약어
    //멀티 스레드 프로그래밍에서 long을  사용할 때 앞에 붙여서 사용합니다.
    private static volatile GlobalApplication obj= null;
    private static  volatile Activity currentActivity = null;

    //안드로이드 스튜디오에서는 상속을 받고 메소드를 재정의할 때
    //에러 메시지를 출력하는 경우가 있는데 이경우는 상위 클래스의
    //메소드를 반드시 호출해주어야합니다
    @Override
    public  void onCreate(){
        super.onCreate();
        obj = this;
        KakaoSDK.init(new KakaoSDKAdapter());
    }


    public static  GlobalApplication getGlobalApplicationCintent(){
        return obj;
    }
    public static  Activity getCurrentActivity(){
        return currentActivity;
    }
    public static  void setCurrentActivity(Activity currentActivity){
        GlobalApplication.currentActivity=currentActivity;
    }
}
