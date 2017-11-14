package com.example.zhangjian.three;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by zhangjian on 2017/11/8.
 */

public class NewData extends Activity {

    private Button bs;//保存
    private Button bc;//取消
    private EditText e_account;//账号
    private EditText e_password;//密码
    private EditText e_detail;//备注


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.new_data);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.add_title);

        final SQLiteDatabase database=SQLiteDatabase.openOrCreateDatabase(
                "/data/data/com.example.zhangjian.three/users.db",null);//创建数据库




        String tabn="secrets";

        if (tableIsExist(tabn,database)==true)//如有表则跳过，不然创建数据库
        {
            Toast.makeText(getApplicationContext(),"youde ",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"meiyou",Toast.LENGTH_SHORT).show();

            String create_b="create table secrets(" +
                    "_id varchar(60)," +
                    "cipher varchar(30)," +
                    "detail varchar(60))";
            database.execSQL(create_b);
        }






        e_account=(EditText)findViewById(R.id.username);//获取数据
        e_password=(EditText)findViewById(R.id.userpassword);
        e_detail=(EditText)findViewById(R.id.userdetail);







        bs=(Button)findViewById(R.id.btn_sure);
        bs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String s_account=e_account.getText().toString();
                final String s_password=e_password.getText().toString();
                final String s_detail=e_detail.getText().toString();

                String insert_str="insert into secrets(_id,cipher,detail)" +
                        "values(?,?,?)";
                Object[] valueobjects={s_account,s_password,s_detail};
                database.execSQL(insert_str,valueobjects);
                Toast.makeText(getApplicationContext(),"保存成功",Toast.LENGTH_SHORT).show();





                Intent intent=new Intent();
                intent.setClass(NewData.this,MainActivity.class);
                startActivity(intent);

            }
        });


        bc=(Button)findViewById(R.id.btn_cancel);
        bc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(NewData.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }

    public boolean tableIsExist(String tabName, SQLiteDatabase db) {
        boolean result = false;
        if (tabName == null) {
            return false;
        }
        Cursor cursor = null;
        try {

            String sql = "select count(*) as c from sqlite_master where type ='table' and name ='"
                    + tabName.trim() + "' ";
            cursor = db.rawQuery(sql, null);
            if (cursor.moveToNext()) {
                int count = cursor.getInt(0);
                if (count > 0) {
                    result = true;
                }
            }

        } catch (Exception e) {
            // TODO: handle exception
        }
        return result;
    }
}
