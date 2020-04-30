package hewson.logindemo2.activity.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alipay.sdk.app.EnvUtils;
import com.beardedhen.androidbootstrap.BootstrapButton;

import hewson.logindemo2.R;
import hewson.logindemo2.activity.PoiSugSearchDemo;
import hewson.logindemo2.activity.pay_activity;
import hewson.logindemo2.utils.SharePreferencesUtil;
import hewson.logindemo2.utils.myUserInfo;

public class addorder_fragment extends Fragment implements View.OnClickListener{

    TextView distinction;
    EditText edittext_ordertitle;
    EditText edittext_orderdetail;
    EditText edittext_money;
    DatePicker datepicker_deadline;
    BootstrapButton button_addorder;

    private String addressValue;


    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_addorder,container,false);

        //获取组件
        distinction=(TextView)view.findViewById(R.id.textview_selectPosition);
        edittext_ordertitle=(EditText)view.findViewById(R.id.edittext_ordertitle);
        edittext_orderdetail=(EditText)view.findViewById(R.id.edittext_orderdetail);
        edittext_money=(EditText)view.findViewById(R.id.edittext_money);
        datepicker_deadline=(DatePicker)view.findViewById(R.id.datepicker_deadline);
        button_addorder=(BootstrapButton)view.findViewById(R.id.button_addorder);
        distinction.setOnClickListener(this);

        button_addorder.setOnClickListener(this);
        //配置沙箱环境
        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);

        deleteInfo();
        return view;
    }

    public addorder_fragment(){}

    @Override
    public void onPause() {
        super.onPause();
        savaInfo();
    }

    @Override
    public void onResume() {
        super.onResume();
        readInfo();
        readAddress();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        System.out.println("onAttach!!!");

//        Bundle bundle=getArguments();
//        String s=bundle.getString("address");
//        distinction.setText(s);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.textview_selectPosition:
                startActivity(new Intent(getActivity(), PoiSugSearchDemo.class));
                this.onPause();
                break;
            case R.id.button_addorder:
                if(edittext_money.getText().toString()!=null&&edittext_money.getText().toString().length()!=0) {
                    Intent intent=new Intent(getActivity(), pay_activity.class);

                    intent.putExtra("distinction",distinction.getText().toString());
                    intent.putExtra("ordertitle",edittext_ordertitle.getText().toString());
                    intent.putExtra("orderdetail",edittext_orderdetail.getText().toString());
                    intent.putExtra("money",edittext_money.getText().toString());
                    intent.putExtra("publishuserid", myUserInfo.getuser(getContext()).getUserid());
                    startActivity(intent);
                    getActivity().finish();
                }else {
                    //赏金为空
                }
//                Intent intent=new Intent(getActivity(), GeoCoderDemo.class);
//                startActivity(new Intent(getActivity(), ReceiveDone_activity.class));
//                getActivity().finish();
                break;
        }

    }

    public void setAddressValue(String addressValue){
        this.addressValue=addressValue;
    }

    public String getAddressValue(){
        return addressValue;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void savaInfo(){
        SharePreferencesUtil util= SharePreferencesUtil.getSharePreferencesInstance(getActivity());
        util.putString("edittext_ordertitle",edittext_ordertitle.getText().toString());
        util.putString("edittext_orderdetail",edittext_orderdetail.getText().toString());
        util.putString("edittext_money",edittext_money.getText().toString());
        util.putBoolean("OrderInfo_Exist",true);
    }

    public void deleteInfo(){
        SharePreferencesUtil util= SharePreferencesUtil.getSharePreferencesInstance(getActivity());
        util.delete("edittext_ordertitle");
        util.delete("edittext_orderdetail");
        util.delete("edittext_money");
        util.delete("OrderInfo_Exist");
    }

    public void readInfo(){
        //调用SharePreferences工具类
        SharePreferencesUtil util= SharePreferencesUtil.getSharePreferencesInstance(getActivity());
        if(util.readBoolean("OrderInfo_Exist")){
            edittext_ordertitle.setText(util.readString("edittext_ordertitle"));
            edittext_orderdetail.setText(util.readString("edittext_orderdetail"));
            edittext_money.setText(util.readString("edittext_money"));
            util.delete("edittext_ordertitle");
            util.delete("edittext_orderdetail");
            util.delete("edittext_money");
            util.delete("OrderInfo_Exist");
            util.putBoolean("OrderInfo_Exist",false);
        }

    }

    public void readAddress(){
        //添加地址界面传来的值
        SharePreferencesUtil util= SharePreferencesUtil.getSharePreferencesInstance(getActivity());
        String key=util.readString("address");
        if(key!=null&&key!=""){
            distinction.setText(key);
            util.delete("address");
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}
