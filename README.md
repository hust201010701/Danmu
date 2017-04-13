注：本人重制了多功能高自由度的弹幕控件，见：[XDanmuku](https://github.com/hust201010701/XDanmuku)


-----------------------------分隔符------------------------------------

# Android 自定义Danmu
## 1.自定义弹幕的Model层
本着MVC的框架，弹幕分为弹幕的Model层和View层，Activity中负责Controller层连接二者。
自定义一个DanmuText类 

    private String content ;
    private int textSize ;
    private int textColor;
    private int x;
    private int y;
    private double xRate;
    private double yRate;
    private Paint paint;
    private boolean isShow ;
    private int speed ;
    private int width;
    private int height;
    private int orientation; //方向，0代表水平，1代表竖直
    private int antiDirection; //是否反方向运动，0代表否，1代表是
    
分别定义以上弹幕属性，然后添加get，set方法。
并添加默认构造方法：
    
    DanmuText()
    {
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setTextSize(80);
        width = 0;
        speed = 1;
        xRate = yRate = Math.random();
        orientation = 0;
        antiDirection = 0;
        new Thread(new DanmuRollRunnable()).start();
    }
    
在构造方法中启动一个线程，以对弹幕进行滚动处理。

    public class DanmuRollRunnable implements Runnable {
            @Override
            public void run() {
                while (true) {
                    if(orientation == 0)
                        x += speed;
                    else
                        y += speed;
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
    }
    
    
DanmuRollRunnable 中是一个死循环，每隔10ms判断一次，orientation 代表
弹幕的滚动方向是水平的还是竖直的，0代表水平，1代表竖直。

## 2.自定义弹幕View层
自定义一个DanmuView类，DanmuView是继承自View，其中主要的变量是

    private String mOrientation = "horizontal";
    private boolean mAntidirection = false;
    private int width;
    private int height;
    ArrayList<DanmuText> list;
    
mOrientation 和 mAntidirection  用来保存从XML文件中获取的弹幕的排列方向和运动方向。
width和height用于保存DanmuView的宽和高。list用于动态存储弹幕DanmuText类。

重点：
在onDraw中对list中所有弹幕进行判断处理。

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).getAntiDirection() == 0) {
                //竖直方向运动
                if (list.get(i).getOrientation() == 1) {
                    if (list.get(i).getY() < height + list.get(i).getHeight()) {
                        canvas.drawText(list.get(i).getContent(), (int) (list.get(i).getxRate() * width), height - list.get(i).getY(), list.get(i).getPaint());
                        postInvalidate();
                    } else {
                        list.get(i).setShow(false);
                        removeDanmu(i);
                        postInvalidate();
                    }
                } else {
                    if (list.get(i).getX() < width + list.get(i).getWidth()) {
                        canvas.drawText(list.get(i).getContent(), width - list.get(i).getX(), (int) (list.get(i).getyRate() * height), list.get(i).getPaint());
                        postInvalidate();
                    } else {
                        list.get(i).setShow(false);
                        removeDanmu(i);
                        postInvalidate();
                    }
                }
            }
            else
            {
                //竖直方向运动
                if (list.get(i).getOrientation() == 1) {
                    if (list.get(i).getY() < height) {
                        canvas.drawText(list.get(i).getContent(), (int) (list.get(i).getxRate() * width), list.get(i).getY() - list.get(i).getHeight(), list.get(i).getPaint());
                        postInvalidate();
                    } else {
                        list.get(i).setShow(false);
                        removeDanmu(i);
                        postInvalidate();
                    }
                } else {
                    if (list.get(i).getX() < width) {
                        canvas.drawText(list.get(i).getContent(), list.get(i).getX() - list.get(i).getWidth(), (int) (list.get(i).getyRate() * height), list.get(i).getPaint());
                        postInvalidate();
                    } else {
                        list.get(i).setShow(false);
                        removeDanmu(i);
                        postInvalidate();
                    }
                }
            }
        }

    }


同时增加添加弹幕和移除弹幕的方法：

    public void addDanmu(DanmuText text) {
        if (mOrientation.equals("vertical"))
            text.setOrientation(1);
        else
            text.setOrientation(0);
        if (mAntidirection)
            text.setAntiDirection(1);
        else
            text.setAntiDirection(0);
        list.add(text);
    }

    public void removeDanmu(int i) {
        list.remove(i);
    }
    
3.在Activity中对弹幕进行调用
    
    package com.orzangleli.danmu;
    
    import android.graphics.Color;
    import android.os.Bundle;
    import android.support.v7.app.AppCompatActivity;
    import android.support.v7.widget.Toolbar;
    import android.view.Menu;
    import android.view.MenuItem;
    import android.view.View;
    import android.widget.Button;
    import android.widget.EditText;
    
    import java.util.Random;
    
    public class MainActivity extends AppCompatActivity {
    
        String danmu[] = new String[]{"这是一个Danmu Demo","它可以自定义Danmu的速度","方向","还有颜色","文本大小","还有滑动方向"};
        DanmuView danmuView;
        EditText message;
        Button send;
    
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
    
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            toolbar.setOnMenuItemClickListener(onMenuItemClick);
    
            danmuView = (DanmuView) this.findViewById(R.id.danmuView);
            message = (EditText) this.findViewById(R.id.message);
            send = (Button) this.findViewById(R.id.send);
    
            send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!message.getText().toString().trim().equals(""))
                    {
                        DanmuText danmuText = new DanmuText();
                        danmuText.setContent(message.getText().toString().trim());
                        Random random =new Random();
                        danmuText.setTextColor(Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
                        danmuView.addDanmu(danmuText);
                        message.setText("");
                    }
                }
            });
    
            for(int i=0;i<danmu.length;i++)
            {
                DanmuText danmuText = new DanmuText();
                danmuText.setContent(danmu[i]);
                Random random =new Random();
                danmuText.setTextColor(Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
                danmuView.addDanmu(danmuText);
            }
    
        }
    
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.main_menu, menu);
            return true;
        }
    
        private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_vertical:
                        danmuView.setOrientation("vertical");
                        break;
                    case R.id.action_horizontal:
                        danmuView.setOrientation("horizontal");
                        break;
                    case R.id.action_normaldirection:
                        danmuView.setmAntidirection(false);
                        break;
                    case R.id.action_antinormaldirention:
                        danmuView.setmAntidirection(true);
                        break;
                }
                return true;
            }
        };
    
    }

添加一个效果图：

<img src="http://7xrrni.com1.z0.glb.clouddn.com/ScreenRecord_2016-09-05-13-53-14.mp4_1473055629.gif?imageView2/0/w/360">

