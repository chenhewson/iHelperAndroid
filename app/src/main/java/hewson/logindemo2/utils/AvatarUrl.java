package hewson.logindemo2.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Random;

public class AvatarUrl {

    public static String getAvatarUrl() {
        //生成（0~14）+1的随机整数
        Integer key = Math.abs(new Random().nextInt() % 14)+1;
        HashMap<Integer,String> hashMap=new HashMap<>();
        hashMap.put(1,"http://img.xiaosen.fun/avatar_20200511150901.jpg");
        hashMap.put(2,"http://img.xiaosen.fun/avatar_20200511150901.jpg");
        hashMap.put(3,"http://img.xiaosen.fun/avatar_20200511150939.jpg");
        hashMap.put(4,"http://img.xiaosen.fun/avatar_20200511150945.jpg");
        hashMap.put(5,"http://img.xiaosen.fun/avatar_20200511150950.jpg");
        hashMap.put(6,"http://img.xiaosen.fun/avatar_20200511150950.jpg");
        hashMap.put(7,"http://img.xiaosen.fun/avatar_20200511151023.jpg");
        hashMap.put(8,"http://img.xiaosen.fun/avatar_20200511151030.jpg");
        hashMap.put(9,"http://img.xiaosen.fun/avatar_20200511151039.jpg");
        hashMap.put(10,"http://img.xiaosen.fun/avatar_20200511151039.jpg");
        hashMap.put(11,"http://img.xiaosen.fun/avatar_20200511151039.jpg");
        hashMap.put(12,"http://img.xiaosen.fun/avatar_20200511151128.jpg");
        hashMap.put(13,"http://img.xiaosen.fun/avatar_20200511151128.jpg");
        hashMap.put(14,"http://img.xiaosen.fun/avatar_20200511151128.jpg");
        hashMap.put(15,"http://img.xiaosen.fun/avatar_20200511151151.jpg");
        return hashMap.get(key);
    }
}
