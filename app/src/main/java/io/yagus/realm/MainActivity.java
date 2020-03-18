package io.yagus.realm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {
    TextView tv_title,tv_content;
    EditText et_title,et_content;
    RealmResults<MemoVO> realmResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_title = findViewById(R.id.tv_title);
        tv_content = findViewById(R.id.tv_content);
        et_title = findViewById(R.id.et_title);
        et_content = findViewById(R.id.et_content);

        /* 참고사이트: https://realm.io/kr/docs/java/latest#getting-started*/

        Realm.init(this);
        final Realm realm = Realm.getDefaultInstance();

        realmResults = realm.where(MemoVO.class).findAll();
        if (realmResults.size()>0) {
            tv_title.setText(realmResults.get(realmResults.size()-1).title);
            tv_content.setText(realmResults.get(realmResults.size()-1).content);
        }


        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                realm.beginTransaction();
                MemoVO memoVO = realm.createObject(MemoVO.class);
                memoVO.title=et_title.getText().toString();
                memoVO.content=et_content.getText().toString();
                realm.commitTransaction();

                realmResults = realm.where(MemoVO.class).findAll();
                tv_title.setText(realmResults.get(realmResults.size()-1).title);
                tv_content.setText(realmResults.get(realmResults.size()-1).content);

                et_title.setText("");
                et_content.setText("");

            }
        });
    }
}
