/*
 * Copyright (C) 2020 xuexiangjys(xuexiangjys@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package hewson.logindemo2.utils;
import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class OkHttpCallback implements Callback {
    private final String TAG = OkHttpCallback.class.getSimpleName();


    public String url;
    public String result;


    public void onResponse(Call call, Response response) throws IOException {
        Log.d(TAG,"url:"+url);

        //resut就是请求返回的结果
        result=response.body().string().toString();
        Log.d(TAG,"请求成功:"+result);
        OnFinish("success",result);
    }



    public void onFailure(Call call, IOException e) {
        Log.d(TAG,"url:"+url);
        Log.d(TAG,"请求失败:"+e.toString());
        OnFinish("failure",e.toString());
    }

    public void OnFinish(String status, String result) {

        Log.d(TAG,"url:"+url+"status:"+status);
    }
}

