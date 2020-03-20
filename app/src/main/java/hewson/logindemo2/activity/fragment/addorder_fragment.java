package hewson.logindemo2.activity.fragment;

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

public class addorder_fragment extends Fragment {

    public LocationClient mLocationClient;
    //private MapView mapview;
    private TextView positionText;
    private StringBuilder currentPosition;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_addorder,container,false);
    }

    public addorder_fragment(){}
}
