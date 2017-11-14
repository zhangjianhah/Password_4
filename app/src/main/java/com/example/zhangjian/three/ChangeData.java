package com.example.zhangjian.three;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by zhangjian on 2017/11/14.
 */

public class ChangeData extends Activity {
    //@Override

    Button btn_sures,btn_cancels;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.change_data);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.add_title);
        Bundle bundle=this.getIntent().getExtras();//不能用树上的方法,前面用bundle传，后面就要用bundle接收
        final String ac,ps,ds;
        ac=bundle.getString("accounts");
        ps=bundle.getString("passwords");
        ds=bundle.getString("details");
        final EditText t1=(EditText)findViewById(R.id.change_name);
        final EditText t2=(EditText)findViewById(R.id.change_pd);
        final EditText t3=(EditText)findViewById(R.id.change_dl);
        t1.setText(ac);
        t2.setText(ps);
        t3.setText(ds);


        final SQLiteDatabase database=SQLiteDatabase.openOrCreateDatabase(
                "/data/data/com.example.zhangjian.three/users.db",null);

        btn_cancels=(Button)findViewById(R.id.btn_cancel);
        btn_sures=(Button)findViewById(R.id.btn_sure);

        btn_sures.setOnClickListener(new View.OnClickListener() {//保存
            @Override
            public void onClick(View v) {

                //获取修改后数据
                String final_ac=t1.getText().toString();
                String final_ps=t1.getText().toString();
                String final_ds=t1.getText().toString();
                database.delete("secrets","_id=?",new String[]{ac});//删除原数据
                //存储新数据，因为任意一个都可被修改
                String insert_str="insert into secrets(_id,cipher,detail)" +
                        "values(?,?,?)";
                Object[] valueobjects={final_ac,final_ps,final_ds};
                database.execSQL(insert_str,valueobjects);
                Toast.makeText(getApplicationContext(),"修改成功",Toast.LENGTH_SHORT).show();

                Intent intent=new Intent();
                intent.setClass(ChangeData.this,MainActivity.class);
                startActivity(intent);
            }
        });

        btn_cancels.setOnClickListener(new View.OnClickListener() {//取消
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(ChangeData.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
