package hewson.logindemo2.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alipay.sdk.app.PayTask;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Map;

import hewson.logindemo2.R;
import hewson.logindemo2.activity.alipay.PayDemoActivity;
import hewson.logindemo2.activity.alipay.PayResult;
import hewson.logindemo2.common.Const;
import hewson.logindemo2.utils.ActivityCollectorUtil;
import hewson.logindemo2.utils.OkHttpCallback;
import hewson.logindemo2.utils.OkhttpUtils;
import hewson.logindemo2.utils.OrderInfoUtil2_0;
import hewson.logindemo2.vo.ServerResponse;

import static hewson.logindemo2.common.Const.RSA2_PRIVATE;

public class pay_activity extends AppCompatActivity implements View.OnClickListener {
    private static final int SDK_PAY_FLAG = 1;

    TextView textview_distinction;
    TextView textview_ordertitle;
    TextView textview_orderdetail;
    TextView textview_money;
    BootstrapButton button_pay;
    BootstrapButton button_seemore;
    String publishuserid;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        ActivityCollectorUtil.addActivity(pay_activity.this);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
            getSupportActionBar().hide();
        }
        textview_distinction=findViewById(R.id.textview_distinction);
        textview_ordertitle=findViewById(R.id.textview_ordertitle);
        textview_orderdetail=findViewById(R.id.textview_orderdetail);
        textview_money=findViewById(R.id.textview_money);
        button_pay=findViewById(R.id.button_pay);
        button_seemore=findViewById(R.id.button_seemore);

        Intent intent=getIntent();

        textview_distinction.setText(intent.getStringExtra("distinction"));
        textview_ordertitle.setText(intent.getStringExtra("ordertitle"));
        textview_orderdetail.setText(intent.getStringExtra("orderdetail"));
        textview_money.setText(intent.getStringExtra("money"));
        publishuserid=intent.getStringExtra("publishuserid");

        button_pay.setOnClickListener(this);
        button_seemore.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollectorUtil.removeActivity(pay_activity.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_pay:
                payV2(v);
                break;

            case R.id.button_seemore:
                //activity的跳转,跳转到首页。注意：Looper.loop();不能使用！！！
                Intent intent=new Intent(pay_activity.this,home_activity.class);
                pay_activity.this.startActivity(intent);
                //关闭当前activity
                pay_activity.this.finish();
                break;
        }
    }

    //支付宝调用支付页面
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {

                        //支付成功，即可执行插入任务操作
                        showAlert(pay_activity.this, "支付成功！" );

                        OkhttpUtils.get(Const.IpAddress+"protal/Task/addTask.do?publishuserid="+publishuserid+"&tTitle="+textview_ordertitle.getText().toString()+"&tDetail="+textview_orderdetail.getText().toString()+"&tMoney="+textview_money.getText().toString()+"&tAddress="+textview_distinction.getText().toString(),
                                new OkHttpCallback(){
                                    @Override
                                    public void OnFinish(String status, String msg) {
                                        super.OnFinish(status, msg);
                                        //解析数据,将json格式的msg转为ServerResponse对象
                                        Gson gson = new Gson();

                                        //将泛型解析成String对象：new TypeToken<ServerResponse<String>>(){}.getType()
                                        ServerResponse<String> serverResponse = gson.fromJson(msg, new TypeToken<ServerResponse<String>>(){}.getType());
                                        Looper.prepare();
                                        Toast.makeText(pay_activity.this,serverResponse.getMsg(), Toast.LENGTH_LONG).show();
                                        Looper.loop();
                                    }
                                });
//                        pay_activity.this.startActivity(new Intent(pay_activity.this, addOrderDone_activity.class));
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
//                        showAlert(getContext(), getString(R.string.pay_failed) + payResult);
                        showAlert(pay_activity.this, "失败！" );
                    }
                    break;
                }
                default:
                    break;
            }
        };
    };

    //支付demo
    public void payV2(View v) {
        if (TextUtils.isEmpty(Const.APPID) || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(Const.RSA_PRIVATE))) {
//            showAlert(getContext(), getString(R.string.error_missing_appid_rsa_private));
            return;
        }
        /*
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo 的获取必须来自服务端；
         */
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(Const.APPID, rsa2,textview_money.getText().toString());
//        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(Const.APPID, rsa2);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
        String privateKey = rsa2 ? RSA2_PRIVATE : Const.RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
        final String orderInfo = orderParam + "&" + sign;

        final Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(pay_activity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    private static void showAlert(Context ctx, String info, DialogInterface.OnDismissListener onDismiss) {
        new AlertDialog.Builder(ctx)
                .setMessage(info)
                .setPositiveButton(R.string.confirm, null)
                .setOnDismissListener(onDismiss)
                .show();
    }

    private static void showAlert(Context ctx, String info) {
        showAlert(ctx, info, null);
    }

}
