package rx.android.gdg.kr.simpleobservable;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.TextView;

import rx.Observable;
import rx.Subscriber;


public class MainActivity extends ActionBarActivity {
    private static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 스트림을 생성.
        // onNext - 스트림에 데이터 보내기. 여러번 가능.
        // onCompleted - 종료 신호를 스트림에 전달.
        // onError - 에러를 스트림에 전달.

        Observable<String> simpleObservable =
                Observable.create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        subscriber.onNext("Hello RxAndroid !!");
                        subscriber.onCompleted();
                    }
                });

        // 스트림의 데이터가 발생할 때 마다 반복적으로 Subscriber 객체가 소모.

        simpleObservable
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "complete!");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "error: " + e.getMessage());
                    }

                    @Override
                    public void onNext(String text) {
                        ((TextView) findViewById(R.id.textView)).setText(text);
                    }
                });

        // 항상 onCompleted, onError, onNext를 만들어야 하는가?
        // 예외는 그냥 버리고 싶어요.
    }
}
