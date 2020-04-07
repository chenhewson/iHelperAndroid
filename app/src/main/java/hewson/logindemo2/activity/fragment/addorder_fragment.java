package hewson.logindemo2.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.baidu.location.LocationClient;

import hewson.logindemo2.R;
import hewson.logindemo2.activity.GeoCoderDemo;
import hewson.logindemo2.activity.PoiSugSearchDemo;

public class addorder_fragment extends Fragment implements View.OnClickListener{

    TextView distinction;
    public LocationClient mLocationClient;
    //private MapView mapview;
    private TextView positionText;
    private StringBuilder currentPosition;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_addorder,container,false);
        distinction=(TextView)view.findViewById(R.id.textview_selectPosition);

        distinction.setOnClickListener(this);
        return view;
    }

    public addorder_fragment(){}

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
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
}
