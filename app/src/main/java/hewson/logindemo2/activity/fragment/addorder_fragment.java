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

import com.baidu.location.LocationClient;

import hewson.logindemo2.R;
import hewson.logindemo2.activity.GeoCoderDemo;
import hewson.logindemo2.activity.PoiSugSearchDemo;
import hewson.logindemo2.utils.SharePreferencesUtil;

public class addorder_fragment extends Fragment implements View.OnClickListener{

    TextView distinction;
    EditText edittext_ordertitle;
    EditText edittext_orderdetail;
    EditText edittext_money;
    DatePicker datepicker_deadline;

    private String addressValue;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_addorder,container,false);

        //获取组件
        distinction=(TextView)view.findViewById(R.id.textview_selectPosition);
        edittext_ordertitle=(EditText)view.findViewById(R.id.edittext_ordertitle);
        edittext_orderdetail=(EditText)view.findViewById(R.id.edittext_orderdetail);
        edittext_money=(EditText)view.findViewById(R.id.edittext_money);
        datepicker_deadline=(DatePicker)view.findViewById(R.id.datepicker_deadline);

        distinction.setOnClickListener(this);


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
                Intent intent=new Intent(getActivity(), PoiSugSearchDemo.class);
//                Intent intent=new Intent(getActivity(), GeoCoderDemo.class);
                startActivity(intent);
                this.onPause();
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



}
