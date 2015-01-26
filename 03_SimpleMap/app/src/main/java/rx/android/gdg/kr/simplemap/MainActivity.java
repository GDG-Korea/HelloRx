package rx.android.gdg.kr.simplemap;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 문자열로 부터 간단히 옵저버블을 생성한다.
        Observable<String> simpleObservable = Observable.just("Hello RxAndroid");

        // map을 통해 스트림에 있는 "Hello RxAndroid"를 대문자로 변환하는 과정을 추가한다.

        simpleObservable
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String text) {
                        return text.toUpperCase();
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String text) {
                        ((TextView) findViewById(R.id.textView)).setText(text);
                    }
                });

        // 다른 타입으로도 가공할 수 있다.

//        simpleObservable
//                .map(new Func1<String, Integer>() {
//                    @Override
//                    public Integer call(String text) {
//                        return text.length();
//                    }
//                })
//                .subscribe(new Action1<Integer>() {
//                    @Override
//                    public void call(Integer length) {
//                        ((TextView) findViewById(R.id.textView)).setText("length: " + length);
//                    }
//                });
    }
}
