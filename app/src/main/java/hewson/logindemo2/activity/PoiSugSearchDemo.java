package hewson.logindemo2.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hewson.logindemo2.R;
import hewson.logindemo2.activity.fragment.addorder_fragment;
import hewson.logindemo2.utils.ActivityCollectorUtil;
import hewson.logindemo2.utils.SharePreferencesUtil;


/**
 * 介绍地点检索输入提示
 */
public class PoiSugSearchDemo extends AppCompatActivity implements OnGetSuggestionResultListener,View.OnClickListener{
    // 搜索模块，也可去掉地图模块独立使用
    private GeoCoder mSearch = null;
    private BaiduMap mBaiduMap = null;
    private MapView mMapView = null;
    private EditText mEditGeoCodeKey;
    private BitmapDescriptor mbitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_marka);

    private SuggestionSearch mSuggestionSearch = null;

    // 搜索关键字输入窗口
    private AutoCompleteTextView mKeyWordsView = null;
    private ListView mSugListView;
    private SimpleAdapter simpleAdapter;

    //确认按钮
    private BootstrapButton button_confirmaddress;

    //完整地址
    private String key;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poisugsearch);
        ActivityCollectorUtil.addActivity(PoiSugSearchDemo.this);
        //隐藏顶部标题栏
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }


        // 初始化建议搜索模块，注册建议搜索事件监听
        mSuggestionSearch = SuggestionSearch.newInstance();
        mSuggestionSearch.setOnGetSuggestionResultListener(this);

        // 初始化view
        mSugListView = (ListView) findViewById(R.id.sug_list);
        mKeyWordsView = (AutoCompleteTextView) findViewById(R.id.searchkey);
        mKeyWordsView.setThreshold(1);

        //获取button
        button_confirmaddress=(BootstrapButton)findViewById((R.id.button_confirmaddress));

        //注册点击事件
        button_confirmaddress.setOnClickListener(this);

        // 当输入关键字变化时，动态更新建议列表
        mKeyWordsView.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                if (cs.length() <= 0) {
                    Log.e("onTextChanged","cs.length() <= 0");
                    return;
                }
                Log.e("onTextChanged","cs.length() > 0");
                // 使用建议搜索服务获取建议列表，结果在onSuggestionResult()中更新
                mSuggestionSearch.requestSuggestion((new SuggestionSearchOption())
                        .keyword(cs.toString()) // 关键字
                        .city("厦门")); // 城市
            }
        });
    }


    /**
     * 获取在线建议搜索结果，得到requestSuggestion返回的搜索结果
     *
     * @param suggestionResult    Sug检索结果
     */
    @Override
    public void onGetSuggestionResult(SuggestionResult suggestionResult) {
        if (suggestionResult == null || suggestionResult.getAllSuggestions() == null) {
            Log.e("suggestionResult","suggestionResult为空");
            return;
        }

        List<HashMap<String, String>> suggest = new ArrayList<>();
        for (SuggestionResult.SuggestionInfo info : suggestionResult.getAllSuggestions()) {
            if (info.getKey() != null && info.getDistrict() != null && info.getCity() != null) {
                HashMap<String, String> map = new HashMap<>();
                map.put("key",info.getKey());
                map.put("city",info.getCity());
                map.put("dis",info.getDistrict());
                suggest.add(map);
            }
        }

        simpleAdapter = new SimpleAdapter(getApplicationContext(),
                suggest,
                R.layout.item_layout,
                new String[]{"key", "city","dis"},
                new int[]{R.id.sug_key, R.id.sug_city, R.id.sug_dis});

        mSugListView.setAdapter(simpleAdapter);
        simpleAdapter.notifyDataSetChanged();

        //设置listview点击事件
        mSugListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        System.out.println(simpleAdapter.getItem(i));
                        //去除花括号，空格
                        String metaAddress=simpleAdapter.getItem(i).toString();
                        String str0=metaAddress.replaceAll(" ","");
                        String str1=str0.replaceAll("\\{","");
                        String str2=str1.replaceAll("\\}","");
                        String[] strings=str2.split(",");
                        String city=strings[0].replaceAll("city=","");
                        String key=strings[1].replaceAll("key=","");
                        String dis=strings[2].replaceAll("dis=","");

                        //完整地址
                        String address=city+dis+key;
                        System.out.println(address);
                        setKey(address);
                        mKeyWordsView.setText(getKey());
                    }
                }
        );
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSuggestionSearch.destroy();
        ActivityCollectorUtil.removeActivity(PoiSugSearchDemo.this);
    }

    public void searchButtonProcess(View v) {
        // 发起Geo搜索
        mSearch.geocode(new GeoCodeOption()
                .city("福建")// 城市
                .address(mEditGeoCodeKey.getText().toString())); // 地址
    }

    @Override
    public void onClick(View v) {
        Log.e("button_confirmaddress","onclick执行");
        switch (v.getId()){
            case R.id.button_confirmaddress:
                Log.e("button_confirmaddress","case执行");
                if(key!=null&&key!=""){
                    Log.e("button_confirmaddress","if1执行");
                    FragmentManager fragmentManager=getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                    Fragment addorder_fragment=fragmentManager.findFragmentByTag("addorder_FRAGMENT_TAG");
                    if(addorder_fragment==null){
                        Log.e("button_confirmaddress","if2执行");
                        //调用SharePreferences工具类，将用户信息保存成文件.
                        SharePreferencesUtil util=SharePreferencesUtil.getSharePreferencesInstance(PoiSugSearchDemo.this);
                        util.delete("address");
                        util.putString("address",getKey());
                        PoiSugSearchDemo.this.finish();
                    }
                    Log.e("button_confirmaddress","button_confirmaddress执行");
                }
                break;
        }
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
