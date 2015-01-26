package rx.android.gdg.kr.edittext;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.TextView;

import java.util.Random;

import rx.android.view.ViewObservable;


public class MainActivity extends ActionBarActivity {

    private static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewObservable
                .clicks(findViewById(R.id.button))
                // 클릭 이벤트를 받는 옵저버블

                .map(event -> new Random().nextInt())
                // 이벤트를 랜덤한 숫자로 변경.

                // 뷰단을 처리하는 서브스크라이브 전에 데이터를 가공할 수 있습니다. 이런 가공은 뷰와의 의존성도 적고
                // 별도의 메서드로 분리하여 JUnit등의 테스트 대상으로 삼을 수 있습니다.

                .subscribe(value -> {
                    TextView textView = (TextView) findViewById(R.id.textView);
                    textView.setText("number: " + value.toString());
                }, throwable -> {
                    Log.e(TAG, "Error: " + throwable.getMessage());
                    throwable.printStackTrace();
                });
    }
}
