package rx.android.gdg.kr.combine;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import rx.Observable;
import rx.android.widget.OnTextChangeEvent;
import rx.android.widget.WidgetObservable;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CheckBox checkBox1 = (CheckBox) findViewById(R.id.checkBox1);
        EditText editText1 = (EditText) findViewById(R.id.editText1);

        // 첫번째 체크박스에서 스트림을 만드는데 input의 두번째 인자를 true로 하여 초기 값도 스트림에 넣습니다.
        // map을 적용하여 이벤트를 체크된 값 (Boolean 타입) 으로 변환합니다.
        Observable<Boolean> checks1 = WidgetObservable.input(checkBox1, true)
                .map(event -> event.value());

        // 스트림 값을 이용하여 에디트 텍스트의 활성 유뮤를 변경합니다.
        checks1.subscribe(check -> editText1.setEnabled(check));

        // 에디트 텍스트의 유무를 스트림으로 만들었습니다. text를 이용해서 에디트 텍스트의 이벤트를 얻습니다.
        // map에서 isEmpty 메소드를 적용하여 이벤트에 포함된 텍스트가 비어있는지 확인합니다.

        // MainActivity::isEmpty는 MainActivity의 isEmpty 스태틱 메서드에 파라미터 값을 전달하라는 것입니다.
        // map(text -> MainActivity.isEmpty(text))와 동일한 코드라고 보시면 됩니다.
        Observable<Boolean> textExists1 = WidgetObservable.text(editText1, true)
                .map(MainActivity::isEmpty);

        // (필수) 체크 박스가 선택되지 않거나 글이 존재하면 첫번째 벨리데이션은 통과합니다.
        // combineLatest가 각 스트림 (Observable)의 마지막 값을 함께 전달해줍니다.
        // 사용자가 체크박스를 변경하거나 텍스트를 입력하는 것은 동시에 일어나지 않기 때문에 마지막 값들을 합쳐주는
        // combineLatest가 적합합니다.
        Observable<Boolean> textValidations1 = Observable
                .combineLatest(checks1, textExists1, (check, exist) -> !check || exist);

        // 아래 내용들은 첫번째 체크박스 / 에디트 텍스트와 동일한 로직들입니다.

        CheckBox checkBox2 = (CheckBox) findViewById(R.id.checkBox2);
        EditText editText2 = (EditText) findViewById(R.id.editText2);

        Observable<Boolean> checks2 = WidgetObservable.input(checkBox2, true)
                .map(event -> event.value());

        checks2.subscribe(check -> editText2.setEnabled(check));

        Observable<Boolean> textExists2 = WidgetObservable.text(editText2, true)
                .map(MainActivity::isEmpty);

        Observable<Boolean> textValidations2 = Observable
                .combineLatest(checks2, textExists2, (check, exist) -> !check || exist);


        CheckBox checkBox3 = (CheckBox) findViewById(R.id.checkBox3);
        EditText editText3 = (EditText) findViewById(R.id.editText3);

        Observable<Boolean> checks3 = WidgetObservable.input(checkBox3, true)
                .map(event -> event.value());

        checks3.subscribe(check -> editText3.setEnabled(check));

        Observable<Boolean> textExists3 = WidgetObservable.text(editText3, true)
                .map(MainActivity::isEmpty);

        Observable<Boolean> textValidations3 = Observable
                .combineLatest(checks3, textExists3, (check, exist) -> !check || exist);


        Button button = (Button) findViewById(R.id.button);


        // 세번의 벨리데이션 결과를 합쳐서 최종 벨리데이션을 만들고 이에 따라 버튼의 활성 여부를 결정합니다.
        // 이전 combineLatest와의 차이는 이번에는 3개의 스트림 (Observable)을 합친 것입니다.
        Observable.combineLatest(textValidations1, textValidations2, textValidations3,
                (validation1, validation2, validation3) ->
                        validation1 && validation2 && validation3)
                .subscribe(validation -> button.setEnabled(validation));
    }

    public static boolean isEmpty(OnTextChangeEvent event) {
        return event.text().length() != 0;
    }
}
