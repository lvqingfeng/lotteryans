package com.juyikeji.caipiao.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.juyikeji.caipiao.R;

/**
 * 手机认证2
 */
public class PhoneAttestation2 extends Activity implements View.OnClickListener{
    private TextView tv_phone,tv_xgphone;
    private ImageView fanhui;
    private String s="",s1="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_attestation2);

        Intent intent=getIntent();
        s=intent.getStringExtra("phone");

        init();
    }

    private void init(){
        tv_phone= (TextView) findViewById(R.id.tv_phone);
        //将一截字符串替换为“*”
        String x=s.substring(3,7);
        s1=s.replace(x,"****");
        tv_phone.setText(s1);

        tv_xgphone= (TextView) findViewById(R.id.tv_xgphone);
        tv_xgphone.setOnClickListener(this);
        fanhui= (ImageView) findViewById(R.id.fanhui);
        fanhui.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //修改手机号
            case R.id.tv_xgphone:
                Intent intent=new Intent(PhoneAttestation2.this,PhoneAttestation.class);
                intent.putExtra("phone",s);
                startActivity(intent);
                break;
            //返回
            case R.id.fanhui:
                finish();
                break;
        }
    }
}
