package zhang.test.android_notification;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Config;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/7.
 */

public class HKApplication extends Application {

    private List<Activity> activityList = new ArrayList<Activity>();

    @Override
    public void onCreate() {
        super.onCreate();

// 以下用来捕获程序崩溃异常
        if (!Config.DEBUG) {
            Thread.setDefaultUncaughtExceptionHandler(restartHandler); // 程序崩溃时触发线程
        }
    }

    // 创建服务用于捕获崩溃异常
    private Thread.UncaughtExceptionHandler restartHandler = new Thread.UncaughtExceptionHandler() {
        @Override
        public void uncaughtException(Thread thread, Throwable ex) {
            restartApp();//发生崩溃异常时,重启应用
        }
    };

    // activity管理：从列表中移除activity
    public void removeActivity(Activity activity) {
        activityList.remove(activity);
    }

    // activity管理：添加activity到列表
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    // activity管理：结束所有activity，彻底关闭应用
    public void finishProgram() {
        for (Activity activity : activityList) {
            if (null != activity) {
                activity.finish();
            }
        }
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    // activity管理：结束所有activity
    public void finishAllActivity() {
        for (Activity activity : activityList) {
            if (null != activity) {
                activity.finish();
            }
        }
    }

    // 重启应用
    @SuppressWarnings("WrongConstant")
    public void restartApp() {
        Intent intent = new Intent();
// 参数1：包名，参数2：程序入口的activity
        intent.setClassName(getPackageName(), "zhang.test.android_notification.MainActivity");
        PendingIntent restartIntent = PendingIntent.getActivity(
                getApplicationContext(), 0, intent,
                Intent.FLAG_ACTIVITY_NEW_TASK);
        AlarmManager mgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000,
                restartIntent); // 1秒钟后重启应用
        finishProgram(); // 自定义方法，关闭当前打开的所有avtivity
    }
}
