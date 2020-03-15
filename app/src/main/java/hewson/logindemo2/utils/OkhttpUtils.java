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





import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class OkhttpUtils {
    private static final OkHttpClient CLIENT=new OkHttpClient();

    //get
    public static void get(String url, OkHttpCallback callback){
        callback.url=url;
        Request request=new Request.Builder().url(url).build();
        CLIENT.newCall(request).enqueue(callback);
    }

    public static final MediaType JSON=MediaType.parse("application/json;charset=utf-8");
    //post
    public static void get(String url, String json, OkHttpCallback callback){
        callback.url=url;
        RequestBody body= RequestBody.create(JSON,json);
        Request request=new Request.Builder().url(url).post(body).build();
        CLIENT.newCall(request).enqueue(callback);
    }

    //下载文件
    public static void downFile(String url, final String saveDir, OkHttpCallback callback){
        callback.url=url;
        Request request=new Request.Builder().url(url).build();
        CLIENT.newCall(request).enqueue(callback);
    }

}
