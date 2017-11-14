package com.example.zhangjian.three;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by zhangjian on 2017/11/11.
 */

public class DetailData extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.detail_data);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.detail_data_title);




        Bundle bundle=this.getIntent().getExtras();//不能用树上的方法,前面用bundle传，后面就要用bundle接收
        String ac,ps,ds;
        ac=bundle.getString("accounts");
        ps=bundle.getString("passwords");
        ds=bundle.getString("details");
        final TextView t1=(TextView)findViewById(R.id.detail_t1);
        TextView t3=(TextView)findViewById(R.id.detail_t3);
        final TextView t2=(TextView)findViewById(R.id.detail_t2);
        t1.setText(ac);
        t2.setText(ps);
        t3.setText(ds);


        Button ddt=(Button)findViewById(R.id.ddt);//返回
        ddt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(DetailData.this,MainActivity.class);
                startActivity(intent);
            }
        });


        ImageButton iac=(ImageButton)findViewById(R.id.ibtn_ac_copy);
        ImageButton ipc=(ImageButton)findViewById(R.id.ibtn_ps_copy);
        iac.setOnClickListener(new View.OnClickListener() {//复制到剪切板
            @Override
            public void onClick(View v) {
                String accountnums=t1.getText().toString();
                ClipboardManager clipboardManager=
                        (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                ClipData clipData=ClipData.newPlainText("text",accountnums);
                //前者为标签，后者为内容
                clipboardManager.setPrimaryClip(clipData);
            }
        });

        ipc.setOnClickListener(new View.OnClickListener() {//复制到剪切板
            @Override
            public void onClick(View v) {
                String passwordss=t2.getText().toString();
                ClipboardManager clipboardManager=
                        (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                ClipData clipData=ClipData.newPlainText("text",passwordss);
                //前者为标签，后者为内容
                clipboardManager.setPrimaryClip(clipData);
            }
        });
    }
}
