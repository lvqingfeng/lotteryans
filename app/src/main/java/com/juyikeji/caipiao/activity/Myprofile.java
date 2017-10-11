package com.juyikeji.caipiao.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.juyikeji.caipiao.R;
import com.juyikeji.caipiao.utils.SharedPreferencesUtil;
import com.juyikeji.caipiao.utils.URLConnectionUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 我的资料
 */
public class Myprofile extends Activity implements View.OnClickListener{
    //修改头像，修改昵称，真实姓名，身份证号
    private RelativeLayout touxiang_layout,name_layout,zname_layout,id_layout;
    //返回
    private ImageView fanhui,iv_touxiang;

    private String resultphoto="";
    //修改头像接口
    private String name_space="editheadimg";

    private String result="";

    /**
     * 定义从相机或者相册 0是相机 1相册2剪辑后的图片
     */
    protected static final String IMAGE_FILE_NAME = "image.jsp";
    protected static final int REQUESTCODE_TAKE = 0;
    protected static final int REQUESTCODE_PICK = 1;
    protected static final int REQUESTCODE_CUTTING = 2;

    private ImageLoader imageLoader;

    private TextView name,zname,id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprofile);

        imageLoader= ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(Myprofile.this));

        Intent intent=getIntent();
        result=intent.getStringExtra("result");
        init();
        jx();
    }

    /**
     * 实例化控件并设置监听
     */
    private void init(){
        touxiang_layout= (RelativeLayout) findViewById(R.id.touxiang_layout);
        touxiang_layout.setOnClickListener(this);
        name_layout= (RelativeLayout) findViewById(R.id.name_layout);
        name_layout.setOnClickListener(this);
        zname_layout= (RelativeLayout) findViewById(R.id.zname_layout);
        zname_layout.setOnClickListener(this);
        id_layout= (RelativeLayout) findViewById(R.id.id_layout);
        id_layout.setOnClickListener(this);
        fanhui= (ImageView) findViewById(R.id.fanhui);
        fanhui.setOnClickListener(this);

        iv_touxiang= (ImageView) findViewById(R.id.iv_touxiang);

        name= (TextView) findViewById(R.id.name);
        zname= (TextView) findViewById(R.id.zname);
        id= (TextView) findViewById(R.id.id);
    }

    /**
     * 解析传过来的数据
     */
    private void jx(){
        JSONObject jobj = null;
        try {
            jobj = new JSONObject(result);
            JSONObject jo=jobj.getJSONObject("data");
            String headImg=jo.getString("headImg");

            String uname=jo.getString("uname");
            if(!"null".equals(uname)){
                zname.setText(uname);
            }else {
                zname.setText("请输入真实姓名");
            }
            String idnumber=jo.getString("idnumber");
            if(!"null".equals(idnumber)){
                id.setText(idnumber);
            }else {
                id.setText("请输入身份证帐号");
            }
            String nickname=jo.getString("nickname");
            if(!"null".equals(nickname)){
                name.setText(nickname);
            }else {
                name.setText("请输入昵称");
            }
            imageLoader.displayImage(headImg, iv_touxiang, SharedPreferencesUtil.getDefaultOptions());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //修改头像
            case R.id.touxiang_layout:
                showTouxiangDialog();
                break;
            //修改昵称
            case R.id.name_layout:
                Intent intent1=new Intent(Myprofile.this,LotteryShop.class);
                intent1.putExtra("code","2");
                intent1.putExtra("name", name.getText());
//                startActivity(intent1);
                startActivityForResult(intent1,111);
                break;
            //真实姓名
            case R.id.zname_layout:
                Intent intent2=new Intent(Myprofile.this,LotteryShop.class);
                intent2.putExtra("code","3");
                intent2.putExtra("name", zname.getText());
//                startActivity(intent2);
                startActivityForResult(intent2,222);
                break;
            //身份证号
            case R.id.id_layout:
                Intent intent=new Intent(Myprofile.this,LotteryShop.class);
                intent.putExtra("code","1");
                intent.putExtra("name", id.getText());
//                startActivity(intent);
                startActivityForResult(intent, 333);
                break;
            //返回
            case R.id.fanhui:
                finish();
                break;
        }
    }
    /**
     * 头像选择弹出框
     */
    public void showTouxiangDialog() {
        final AlertDialog dialog = new AlertDialog.Builder(Myprofile.this).create();
        dialog.show();
        Window window = dialog.getWindow();
        // 设置布局
        window.setContentView(R.layout.fragment_three_touxiangdialog);
        // 设置宽高
        window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置弹出的动画效果
//        window.setWindowAnimations(R.style.AnimBottom);// 动画效果
        View view = (View) window.findViewById(R.id.view);
        final TextView tv_photo = (TextView) window.findViewById(R.id.tv_photo);
        final TextView tv_xiangce = (TextView) window.findViewById(R.id.tv_xiangce);
        TextView tv_cancel = (TextView) window.findViewById(R.id.tv_cancel);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tv_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建Intent 来调用相机
                Intent takeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //获取照片路径
                String imagePath = Environment.getExternalStorageDirectory()
                        + "";
                // 设置文件路径
                File file = new File(imagePath);
                // 判断文件夹是否存在，不存在创建一个
                if (!file.exists()) {
                    file.mkdirs();
                }
                // 设置URI，指定相册拍照后保存图片的路径
                Uri imageUri = Uri
                        .fromFile(new File(imagePath, IMAGE_FILE_NAME));
                takeIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(takeIntent, REQUESTCODE_TAKE);
                // 拍照完以后，文件就会保存在这个指定的目录下了。Uri 里指定了相机拍照的路径
                dialog.dismiss();
            }
        });
        tv_xiangce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickIntent = new Intent(Intent.ACTION_PICK, null);
                // 如果要限制上传到服务器的图片类型时可以直接写如：image/jpeg 、 image/png等的类型
                pickIntent.setDataAndType(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        "image/png");

                startActivityForResult(pickIntent, REQUESTCODE_PICK);
                dialog.dismiss();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUESTCODE_PICK:// 直接从相册获取
                try {
                    CutPhoto(data.getData());
                } catch (NullPointerException e) {
                    e.printStackTrace();// 用户点击取消操作
                }
                break;
            case REQUESTCODE_TAKE:// 调用相机拍照
                File temp = new File(Environment.getExternalStorageDirectory()
                        + "/" + IMAGE_FILE_NAME);
                CutPhoto(Uri.fromFile(temp));
                break;
            case REQUESTCODE_CUTTING:// 取得裁剪后的图片
//                if(!URLConnectionUtil.isOpenNetwork(this)){
//                    Toast.makeText(this, "网络无连接，请检查网络！", Toast.LENGTH_SHORT).show();
//                }else {
                    if (data != null) {
                        SavaPic(data);
                    }
//                }
                break;
            case 111://昵称
                if(resultCode==111){
                    name.setText(data.getStringExtra("nn"));
                }
                break;
            case 222://真实姓名
                if(resultCode==111) {
                    zname.setText(data.getStringExtra("nn"));
                }
                break;
            case 333://身份证号
                if(resultCode==111) {
                    id.setText(data.getStringExtra("nn"));
                }
                break;
        }
    }
    /**
     * 裁剪图片方法
     *
     * @param uri
     */
    public void CutPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/png");
        // crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", true);
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, REQUESTCODE_CUTTING);
    }

    /**
     * 保存裁剪之后的图片
     *
     * @param picdata
     */
    private void SavaPic(Intent picdata) {
        final String token= SharedPreferencesUtil.getSharedPreferences(Myprofile.this).get("token").toString();

        final Bundle extras = picdata.getExtras();
        if (extras != null) {
            // 取得SDCard图片路径做显示
            Bitmap photo = extras.getParcelable("data");
            Drawable drawable = new BitmapDrawable(null, photo);
            // 设置到头像
            iv_touxiang.setImageDrawable(drawable);

            final String strphoto = BitmapToBase64(photo);
            final Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    if (msg.what == 1) {
                        JSONObject jobj = null;
                        try {
                            jobj = new JSONObject(resultphoto);
                            String code = jobj.getString("status");
//                            if (code.equals("1")) {
//                                Toast.makeText(Myprofile.this, "上传成功", Toast.LENGTH_SHORT).show();
//                            } else {
//                                Toast.makeText(Myprofile.this, "上传失败", Toast.LENGTH_SHORT).show();
//                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };

            new Thread() {
                @Override
                public void run() {
                    Map<String, String> map = new HashMap<String, String>();
                    // 获取令牌放入请求参数；
                    map.put("token", token);
                    // 转base64后上传服务器
                    map.put("imgbase", strphoto);
                    // 照片名
                    map.put("imgname", ".png");
                    try {
                        resultphoto = URLConnectionUtil.sendPostRequest(name_space, map, "utf-8", 0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Message m = new Message();
                    m.what = 1;
                    // 发送消息到Handler
                    handler.sendMessage(m);

                }
            }.start();
        }
    }

    /**
     * 图片转base64
     *
     * @param bitmap
     * @return
     */
    public String BitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] bytes = baos.toByteArray();
        // base64 encode
        byte[] encode = Base64.encode(bytes, Base64.DEFAULT);
        String encodeString = new String(encode);
        return encodeString;
    }
}
