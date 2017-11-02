package jiangc.cn.yview.handlethreadtest;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
/*HandlerThread 使用小实例*/
public class MainActivity extends AppCompatActivity {

    private  HandlerThread myHandlerThread;
    private  Handler myHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*创建一个线程， 名称：handler*/
        myHandlerThread = new HandlerThread("handler");
        /*开启一个线程*/
        myHandlerThread.start();

        myHandler = new Handler(myHandlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                /*这里收到消息后做耗时操作，这里已经属于handler子线程了*/
                Log.e("jiangc----" ,  "收到消息" + msg.what + Thread.currentThread().getName());
            }
        };

        /*这里打印出main  上面打印出handler 说明已经不在一个线程中了，感觉上就是线程的重复使用 - 个人理解
        * 因为你可以在外面发送不同的消息进行不同的处理而不需要创建多个线程了 - 个人理解
        * */
        Log.e("jiangc----" ,   Thread.currentThread().getName());


        myHandler.sendEmptyMessage(1);

        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.e("jiangc----" ,   Thread.currentThread().getName());     /*这里打印了这个线程的名字*/
            }
        }).start();
    }
}
