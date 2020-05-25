package hewson.logindemo2.application;
/**
 * 用于存放倒计时时间,倒计时按钮功能
 * @author bnuzlbs-xuboyu 2017/4/5.
 */
import java.util.Map;

import android.app.Application;

public class App extends Application {
    // 用于存放倒计时时间
    public static Map<String, Long> map;
}