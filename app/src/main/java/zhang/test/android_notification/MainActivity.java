package zhang.test.android_notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tv_message)
    Button tvMessage;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.tv_message, R.id.tv_resart_app})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_message: // 通知栏消息
                showNotificationMessage();
                Toast.makeText(this, "to", Toast.LENGTH_SHORT).show();
                break;

            case R.id.tv_resart_app:
                progressDialog = createProgressDialog(this);
                progressDialog.show();
                // 自定义异常
                int i = 1/0;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                    }
                }, 2000);
                break;
        }
    }

    private void showNotificationMessage() {
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        //1.初始化Notification
        Notification notification = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            notification = new Notification.Builder(this)
                    .setContentTitle("我是标题")
                    .setContentText("我是文本")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                    .build();
        }

        //2.显示通知
        nm.notify(0, notification);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.bind(this).unbind();
    }

    public static ProgressDialog createProgressDialog(Context mContext) {
        ProgressDialog dialog = new ProgressDialog(mContext);
        try {
            dialog.show();
        } catch (Exception e) {

        }
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.progres_dialog);
        // dialog.setMessage(Message);
        return dialog;
    }
}
