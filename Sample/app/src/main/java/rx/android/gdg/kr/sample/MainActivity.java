package rx.android.gdg.kr.sample;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

/**
 * Created by Steve SeongUg Jung on 15. 3. 2..
 */
public class MainActivity extends ActionBarActivity {


    private int publishType = 0;
    private PublishSubject<String> samplePublishObject;
    private PublishSubject<String> throttleFirstPublishObject;
    private PublishSubject<String> throttleLastPublishObject;
    private TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);

        setUpActionBar();

        EditText searchEditText = (EditText) findViewById(R.id.et_main_search);
        resultTextView = (TextView) findViewById(R.id.txt_main_result);

        initSamplePublisher();
        initThrottleFirstPublisher();
        initThrottleLastPublisher();


        searchEditText.addTextChangedListener(new SimpleTextChangeWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                PublishType type = PublishType.values()[publishType];

                switch (type) {

                    case Sample:
                        samplePublishObject.onNext(s.toString());
                        break;
                    case ThrottleFirst:
                        throttleFirstPublishObject.onNext(s.toString());
                        break;
                    case ThrottleLast:
                        throttleLastPublishObject.onNext(s.toString());
                        break;
                }

            }
        });
    }

    private void initThrottleFirstPublisher() {
        throttleFirstPublishObject = PublishSubject.create();
        throttleFirstPublishObject.throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        setResultText(s);
                    }
                });
    }

    private void setResultText(String s) {
        resultTextView.setText(s);
    }

    private void initThrottleLastPublisher() {
        throttleLastPublishObject = PublishSubject.create();
        throttleLastPublishObject.throttleLast(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        setResultText(s);
                    }
                });
    }

    private void initSamplePublisher() {
        samplePublishObject = PublishSubject.create();
        samplePublishObject.sample(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        setResultText(s);
                    }
                });
    }

    private void setUpActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        setActionBarTitle();

    }

    private void setActionBarTitle() {
        ActionBar actionBar = getSupportActionBar();

        PublishType type = PublishType.values()[publishType];
        String title = type.name();
        actionBar.setTitle("Sample - " + title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_sample, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();

        if (itemId == R.id.menu_sample) {
            publishType = PublishType.Sample.ordinal();
        } else if (itemId == R.id.menu_throttle_first) {
            publishType = PublishType.ThrottleFirst.ordinal();
        } else {
            publishType = PublishType.ThrottleLast.ordinal();
        }

        setActionBarTitle();

        return true;
    }

    private static abstract class SimpleTextChangeWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    private enum PublishType {
        Sample, ThrottleFirst, ThrottleLast
    }
}
