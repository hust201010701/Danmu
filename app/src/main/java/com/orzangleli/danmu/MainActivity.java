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
