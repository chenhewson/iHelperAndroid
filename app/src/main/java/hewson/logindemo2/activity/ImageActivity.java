package hewson.logindemo2.activity;


import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;
import java.util.Map;

import hewson.logindemo2.R;


public class ImageActivity extends Activity {
    private ImageView imageview400;
    private ImageView imageview200;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        imageview400 = (ImageView) findViewById(R.id.imageview400);
        imageview200 = (ImageView) findViewById(R.id.imageview200);
        String imagepath="http://img.xiaosen.fun/IMG_0878%2820200426-191036%29.JPG?e=1587903650&token=xHa90Wn7oBwBcoeJAmmQVAGkRRAM_2BBaRpHfD-l:AgaTMboydoVQ1eAgTJ03iwh_5CA=&attname=";
        loadGlide(imagepath,imageview400);
        loadGlide(imagepath,imageview200);
    }

    //加载图片工具类
    private void loadGlide(String mUrl,ImageView mImageView) {
        RequestOptions requestOptions = new RequestOptions()
                .error(R.mipmap.icon_nothing);
        Glide.with(this)
                .load(mUrl)
                .apply(requestOptions)
                .into(mImageView);
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }
}