package com.example.zhangjian.three;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {


    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_main);
        //getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.add_title);




        ListView l1=(ListView)findViewById(R.id.l1);

        final SQLiteDatabase database=SQLiteDatabase.openOrCreateDatabase(
                "/data/data/com.example.zhangjian.three/users.db",null);//创建或打开数据库



        String tabn="secrets";

        if (tableIsExist(tabn,database)==true)//如有表则跳过，不然创建数据库
        {

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


        Cursor cursor=database.query(
                "secrets",
                new String[]{"_id","cipher","detail"},//cursor必须有一个_id的记录才可以
                null,null,null,null,
                "detail desc");//降序
        final SimpleCursorAdapter simpleCursorAdapter=new SimpleCursorAdapter(
                MainActivity.this,
                R.layout.another_activity,
                cursor,
                new String[]{"_id","cipher","detail"},
                new int[]{R.id.t2,R.id.t3,R.id.t1},
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER
        );

        l1.setAdapter(simpleCursorAdapter);
        l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {//点击事件,position是第几行，从0开始
                TextView t2=(TextView)view.findViewById(R.id.t2);//账号
                TextView t1=(TextView)view.findViewById(R.id.t1);//备注
                TextView t3=(TextView)view.findViewById(R.id.t3);//密码
                String ds=t1.getText().toString();//item中的数据,ac为账号，ps为密码
                String ac=t2.getText().toString();
                String ps=t3.getText().toString();


                Intent intent=new Intent();
                intent.setClass(MainActivity.this,DetailData.class);
                Bundle bundle=new Bundle();
                bundle.putString("details",ds);
                bundle.putString("accounts",ac);
                bundle.putString("passwords",ps);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        final String []zones ={"修改记录","删除记录"};
        final Button button1;
        AlertDialog alertDialog;
        l1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {


                TextView t1=view.findViewById(R.id.t1);
                TextView t2=view.findViewById(R.id.t2);
                TextView t3=view.findViewById(R.id.t3);
                final String cac=t2.getText().toString();
                final String cpd=t3.getText().toString();
                final String cdt=t1.getText().toString();
                final int last=position;



                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("请选择目标！");
                builder.setItems(zones, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(getApplicationContext(),zones[which],Toast.LENGTH_SHORT).show();
                        //当再次打开一个弹窗后，原来的弹窗会自动关闭
                        String det=zones[which];
                        if(det.equals("修改记录"))
                        {
                            //Toast.makeText(getApplicationContext(),x,Toast.LENGTH_SHORT).show();
                            //database.delete("secrets","_id=?",new String[]{cac});//删除这条记录，下个act重建
                            Intent intent=new Intent();
                            intent.setClass(MainActivity.this,ChangeData.class);
                            Bundle bundle=new Bundle();
                            bundle.putString("details",cdt);
                            bundle.putString("accounts",cac);
                            bundle.putString("passwords",cpd);
                            intent.putExtras(bundle);
                            startActivity(intent);

                        }else
                        {


                            //database.delete("user_info","_id=?",new String []{"2"});
                            database.delete("secrets","_id=?",new String[]{cac});

                            Intent intent=new Intent();
                            intent.setClass(MainActivity.this,MainActivity.class);
                            startActivity(intent);
                            //刷新页面，重新获取这个页面


                    }

                    }
                });//设置条目，然后产生点击事件
                builder.setNegativeButton("取消",null);//点击取消，则失去焦点，弹窗隐藏
                AlertDialog alertDialog1=builder.show();//显示弹窗
                return true;//返回为true，则长按事件被消耗掉，这样就不会与点击事件相抵触
            }
        });













        ImageButton fab=(ImageButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(MainActivity.this,NewData.class);

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
