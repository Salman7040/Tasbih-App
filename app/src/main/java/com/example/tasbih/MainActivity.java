package com.example.tasbih;

import android.annotation.SuppressLint;
import android.content.*;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.*;
import android.text.*;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.*;
import android.widget.*;
import android.view.animation.*;
import androidx.appcompat.app.*;
import androidx.core.view.*;
import androidx.activity.EdgeToEdge;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;





public class MainActivity extends AppCompatActivity {
    ImageView imageView, imageView1, imageView2, imageView3, imageView4, imageView5, imageView6;
    private TextView tx, count;
    private Button buttonCl, buttonHide, soundBtn;
    private View line;
    private int inc = 0;
    private String Preview;
    private RelativeLayout main;
    private EditText limit;
    private String limitValidate = "";
    private int limitScore = 0, i = 0;
    boolean isScreenOn;
    boolean bool = true;
    int soundIcon = 0;
    boolean countingStop=true;
    Window window;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity ScreenUtils = this;
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        isScreenOn = pm.isScreenOn();
        window= this.getWindow();

        window= this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.bar));

        setContentView(R.layout.activity_main);
        //creating objects of xml attributes
        limit = findViewById(R.id.limit);
        main = findViewById(R.id.main);
        buttonCl = findViewById(R.id.clear);
        buttonHide = findViewById(R.id.hide);
        soundBtn = findViewById(R.id.sound);
        tx = findViewById(R.id.show);
        line = findViewById(R.id.linear1);
        limit.addTextChangedListener(textWatcher);

        imageView = findViewById(R.id.imageview);
        imageView1 = findViewById(R.id.imageview1);
        imageView2 = findViewById(R.id.imageview2);
        imageView3 = findViewById(R.id.imageview3);
        imageView4 = findViewById(R.id.imageview4);
        imageView5 = findViewById(R.id.imageview5);
        imageView6 = findViewById(R.id.imageview6);

        limitsUpdate();
        addListenerOnButton();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (isScreenOn == true) {
            unhide();
            bool = true;
        }

    }

    public void limitsUpdate() {
        incOrNot = true;
        limitValidate = limit.getText().toString();
        if (!limitValidate.equals("")) {
            limitScore = Integer.parseInt(limitValidate);
            i = limitScore;
        }

    }

    TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            inc = 0;
            limitsUpdate();
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }

    };

    public void hide() {



        window.setStatusBarColor(this.getResources().getColor(R.color.black));

        tx.setVisibility(View.INVISIBLE);
        main.setBackgroundResource(R.color.black);
        line.setVisibility(View.INVISIBLE);
        buttonHide.setTextColor(Color.parseColor("#171616"));
        buttonHide.setBackgroundResource(R.drawable.unhide);
        buttonCl.setVisibility(View.INVISIBLE);
    }

    public void unhide() {
        window.setStatusBarColor(this.getResources().getColor(R.color.bar));
        line.setVisibility(View.VISIBLE);
        tx.setVisibility(View.VISIBLE);
        main.setBackgroundResource(R.drawable.m4);
        buttonHide.setBackgroundResource(R.drawable.hide);
        buttonCl.setVisibility(View.VISIBLE);
    }



    public void marbleMove(int Ball3, int FromBack, int Move, int Fast, int Reset) {
        Animation move = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move);
        Animation reset = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.reset);
        Animation fast = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fast);
        Animation ball3 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.ball3);
        Animation fromback = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fromback);


        ball3.setDuration(Ball3);
        fromback.setDuration(FromBack);
        fast.setDuration(Fast);
        reset.setDuration(Reset);
        move.setDuration(Move);
        imageView1.startAnimation(ball3);
        imageView2.startAnimation(ball3);
        imageView3.startAnimation(ball3);

        imageView4.startAnimation(move);
        imageView.startAnimation(fromback);
        imageView5.startAnimation(fast);
        imageView6.startAnimation(reset);
        nextMove();

    }


    public  void  onClickAudio(){

        if (soundIcon == 0) {
            MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.s1);
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.reset();
                    mp.release();

                }
            });
            mediaPlayer.start();

        }
        else if (soundIcon == 2) {
            Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vibe.vibrate(20);
        }
    }

    boolean incOrNot = true;
    public void nextMove() {
if (countingStop) {
    onClickAudio();
    inc++;
}
        String incVal = String.valueOf(inc);

        if (limitValidate.equals(incVal)) {
            countingStop=false;
            Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vibe.vibrate(500);
            limitScore = i + limitScore;
            limitValidate = String.valueOf(limitScore);

            if (incOrNot) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Do You Want To Continue Your Dhikr");
                builder.setTitle("Counter Limit Reached: "+incVal);
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
                    incOrNot = false;
                    dialog.cancel();

                    countingStop=true;
                });
                builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
                    inc = 0;
                    countingStop=true;
                    limitsUpdate();
                    dialog.cancel();
                });
                unhide();
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                alertDialog.getWindow().setGravity(Gravity.TOP);
            }
            else
                countingStop=true;

        }

        tx.setText(incVal);

    }

    @SuppressLint("ClickableViewAccessibility")
    public void addListenerOnButton() {
        main.setOnTouchListener(new View.OnTouchListener() {
            Handler handler = new Handler();
            int numberOfTaps = 0;
            long lastTapTimeMs = 0;
            long touchDownMs = 0;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        touchDownMs = System.currentTimeMillis();
                        break;
                    case MotionEvent.ACTION_UP:
                        handler.removeCallbacksAndMessages(null);
                        if (System.currentTimeMillis() - touchDownMs > ViewConfiguration.getTapTimeout()) {
                            numberOfTaps = 0;
                            lastTapTimeMs = 0;
                        }
                        if (numberOfTaps > 0 && System.currentTimeMillis() - lastTapTimeMs < ViewConfiguration.getDoubleTapTimeout()) {
                            numberOfTaps += 1;
                        } else {
                            numberOfTaps = 1;
                        }
                        lastTapTimeMs = System.currentTimeMillis();

                        if (numberOfTaps == 1) {
                            marbleMove(300, 300, 300, 250, 400);
                        }  if (numberOfTaps == 2) {
                            marbleMove(250, 220, 220, 240, 370);
                        } else if (numberOfTaps == 3) {
                            marbleMove(250, 200, 200, 220, 350);
                        } else if (numberOfTaps == 4) {
                            marbleMove(250, 180, 180, 200, 330);
                        } else if (numberOfTaps >= 5) {
                            marbleMove(180, 150, 130, 160, 180);
                        }
                        break;
                }
                return true;
            }

        });


        buttonCl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limitsUpdate();
                inc = 0;
                tx.setText("0");
            }
        });



        buttonHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bool == true) {
                    hide();
                    bool = false;
                } else {
                    unhide();
                    bool = true;
                }
            }
        });


        soundBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundIcon++;
                if (soundIcon > 2)
                    soundIcon = 0;

                if (soundIcon == 0) {
                    soundBtn.setBackgroundResource(R.drawable.sound);

                }
                else if (soundIcon == 1) {
                    soundBtn.setBackgroundResource(R.drawable.nos);

                }
                else if (soundIcon == 2)

                    soundBtn.setBackgroundResource(R.drawable.vibration);
            }
        });


    }
}