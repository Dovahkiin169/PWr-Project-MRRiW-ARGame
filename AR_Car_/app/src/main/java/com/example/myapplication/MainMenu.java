package com.example.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.Toast;

import com.captivereality.texturehelper.HelloWorld;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainMenu extends AppCompatActivity {
    int GamePoints = 0;
    String Car2Status;
    String Car3Status;
    android.widget.TextView TextView;
    BackgroundSound mBackgroundSound = new BackgroundSound();
    PopupWindow popupWindow;
    boolean car2isOpen = false;
    boolean car3isOpen = false;
    int ChoosedCar;

    float VolumeOfMusic;
    boolean MusicOff=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_menu);

        boolean isFirstTime = IfAppOpenForTheFirstTime.isFirst(MainMenu.this);
        if(isFirstTime) {
            GamePoints = 0;
            Utility.setPoints(MainMenu.this,GamePoints);
            Utility.setCarsStatus(MainMenu.this,"closed","closed");
            Utility.setMusicVolume(MainMenu.this,1.0f);
            VolumeOfMusic = Utility.getMusicVolume(MainMenu.this);
            Car2Status = "closed";
            Car3Status = "closed";
        }
        else
        {
            GamePoints = Utility.getPoints(MainMenu.this);
            VolumeOfMusic = Utility.getMusicVolume(MainMenu.this);

            String UserInfo = Utility.getCarsStatus(MainMenu.this);
            String[] UI=UserInfo.split("//");
            Car2Status = UI[0];
            Car3Status = UI[1];
        }
    }



    public void startIsPressed(View view) {

        ChoosedCar= Utility.getChoosedCar(MainMenu.this);

        if(ChoosedCar == 1)
        {
            HelloWorld.Result="1";
            Intent mySuperIntent = new Intent(MainMenu.this, UnityPlayerActivity.class);
            startActivity(mySuperIntent);
        }
        else if (ChoosedCar == 2)
        {
            HelloWorld.Result="2";
            Intent mySuperIntent = new Intent(MainMenu.this, UnityPlayerActivity.class);
            startActivity(mySuperIntent);
        }
        else if (ChoosedCar == 3)
        {
            HelloWorld.Result="3";
            Intent mySuperIntent = new Intent(MainMenu.this, UnityPlayerActivity.class);
            startActivity(mySuperIntent);
        }


    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            setContentView(R.layout.main_menu_constraint_layout); // it will use .xml from /res/layout
        }
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            setContentView(R.layout.main_menu_constraint_layout); // it will use xml from /res/layout-land
        }
    }

    public void onButtonShowPopupWindowClick(View view) {

        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.cars_popup_window, null);

        if(Car2Status.equals("opened"))
        {
            Button car2 = popupView.findViewById(R.id.car_2_unlocked);
            Button car2_locked = popupView.findViewById(R.id.car_2);
            car2.setVisibility(View.VISIBLE);
            car2_locked.setVisibility(View.INVISIBLE);
        }
        if(Car3Status.equals("opened"))
        {
            Button car3 = popupView.findViewById(R.id.car_3_unlocked);
            Button car3_locked = popupView.findViewById(R.id.car_3);
            car3.setVisibility(View.VISIBLE);
            car3_locked.setVisibility(View.INVISIBLE);
        }

        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        dimBehind(popupWindow);

        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // popupWindow.dismiss();
                return true;
            }
        });
        TextView = popupView.findViewById(R.id.coinView);
        TextView.setText((GamePoints+"  "));
    }

    public void car1isPressed(View view) {
        Toast toast = Toast.makeText(getApplicationContext(), "SportHatch is selected", Toast.LENGTH_SHORT);
        toast.show();
        ChoosedCar = 1;
        Utility.setChoosedCar(MainMenu.this, ChoosedCar);
    }
    public void car2isPressed(View view) {

        if(car2isOpen||Car2Status.equals("opened"))
        {
            Toast toast = Toast.makeText(getApplicationContext(), "Devil TURBO 3000 is selected", Toast.LENGTH_SHORT);
            toast.show();
            ChoosedCar = 2;
            Utility.setChoosedCar(MainMenu.this, ChoosedCar);
        }
        else
        {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainMenu.this);
                builder.setTitle("This car costs 300 coins");
                builder.setMessage("Do you want to buy Devil TURBO 3000?");
                builder.setCancelable(true);

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {}
                });

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        if(GamePoints>=300)
                        {
                            car2isOpen=true;
                            Car2Status="opened";
                            GamePoints=GamePoints-300;
                            Utility.setPoints(MainMenu.this,GamePoints);
                            TextView.setText((GamePoints+"  "));
                            Utility.setCarsStatus(MainMenu.this,Car2Status,Car3Status);

                            Button car2_locked = view.findViewById(R.id.car_2);
                            car2_locked.setVisibility(View.INVISIBLE);
                            //SetCar as unlocked in sharedpreferences
                        }
                        else
                        {
                            Toast toast = Toast.makeText(getApplicationContext(), "You don't have enough coins", Toast.LENGTH_SHORT);
                            toast.show();
                        }

                    }
                });
                final AlertDialog alertDialog = builder.create();
                alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
                        Button button = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        button.setTextColor(Color.WHITE);
                        Button button2 = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                        button2.setTextColor(Color.WHITE);
                    }
                });
                alertDialog.show();
                alertDialog.getWindow().setBackgroundDrawableResource(R.color.colorPrimary);
        }
    }
    public void car3isPressed(View view) {
        if(car3isOpen||Car3Status.equals("opened"))
        {
            ChoosedCar = 3;
            Toast toast = Toast.makeText(getApplicationContext(), "Police Car is selected", Toast.LENGTH_SHORT);
            toast.show();
            Utility.setChoosedCar(MainMenu.this, ChoosedCar);
        }
        else
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainMenu.this);
            builder.setCancelable(true);
            builder.setTitle("This car costs 700 coins");
            builder.setMessage("Do you want to buy Police Car?");
            builder.setNegativeButton("NO", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {}
            });

            builder.setPositiveButton("YES", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialogInterface, int i)
                {
                    if(GamePoints>=700)
                    {
                        car3isOpen=true;
                        Car3Status="opened";
                        GamePoints=GamePoints-700;
                        Utility.setPoints(MainMenu.this,GamePoints);
                        TextView.setText((GamePoints+"  "));
                        Utility.setCarsStatus(MainMenu.this,Car2Status,Car3Status);
                        Button car3_locked = view.findViewById(R.id.car_3);
                        car3_locked.setVisibility(View.INVISIBLE);
                    }
                    else
                    {
                        Toast toast = Toast.makeText(getApplicationContext(), "You don't have enough coins", Toast.LENGTH_SHORT);
                        toast.show();
                    }

                }
            });
            final AlertDialog alertDialog = builder.create();
            alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialogInterface) {
                    Button button = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                    button.setTextColor(Color.WHITE);
                    Button button2 = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                    button2.setTextColor(Color.WHITE);
                }
            });
            alertDialog.show();
            alertDialog.getWindow().setBackgroundDrawableResource(R.color.colorPrimary);
        }
    }



    public void SettingsPopUp(View view) {

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.options_popup_window, null);

        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        popupWindow = new PopupWindow(popupView, width, height, focusable);

        SeekBar seekBar = (SeekBar) popupView.findViewById(R.id.MusicVolumeBar);
        if(VolumeOfMusic==0.0f && !MusicOff) { seekBar.setProgress(100); }
        else { seekBar.setProgress(Math.round(VolumeOfMusic*100)); }

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                                               public void onProgressChanged(SeekBar bar, int progress, boolean fromUser) {
                                                   player.setVolume((float)progress/(float)100,(float)progress/(float)100);
                                                   VolumeOfMusic = (float)progress/(float)100;
                                                   if(VolumeOfMusic==0.0f) {
                                                       MusicOff = true;
                                                   }
                                                   Utility.setMusicVolume(MainMenu.this,VolumeOfMusic);
                                               }

                                               public void onStartTrackingTouch(SeekBar bar) {

                                               }

                                               public void onStopTrackingTouch(SeekBar bar) {
                                                   // no-op
                                               }
                                           });


        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        dimBehind(popupWindow);

        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // popupWindow.dismiss();
                return true;
            }
        });
    }

    public void CreditsPopUp(View view) {

        popupWindow.dismiss();

        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.credits_popup_window, null);

        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        popupWindow = new PopupWindow(popupView, width, height, focusable);

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        dimBehind(popupWindow);

        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // popupWindow.dismiss();
                return true;
            }
        });

        GamePoints = Utility.getPoints(MainMenu.this);
        Utility.setPoints(MainMenu.this,GamePoints+500);
        GamePoints = Utility.getPoints(MainMenu.this);


    }

    public static void dimBehind(PopupWindow popupWindow) {
        View container = popupWindow.getContentView().getRootView();
        Context context = popupWindow.getContentView().getContext();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams p = (WindowManager.LayoutParams) container.getLayoutParams();
        p.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        p.dimAmount = 0.5f;
        wm.updateViewLayout(container, p);
    }

    MediaPlayer player;
    boolean checker = false;
    public class BackgroundSound extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            checker = true;
            player = MediaPlayer.create(MainMenu.this, R.raw.the_rush);
            player.setLooping(true); // Set looping
            player.setVolume(VolumeOfMusic, VolumeOfMusic);
            player.start();

            return null;
        }
        public void onPause() {
            player.pause();
        }
        public void onResume() {
            player.start();
        }

    }

    public void onResume() {
        super.onResume();
        if(!checker) {
            mBackgroundSound.execute();
        }
        else
        {
            mBackgroundSound.onResume();
        }
    }


    public void onUserLeaveHint() {
        super.onUserLeaveHint();
        mBackgroundSound.onPause();
    }

    public void onStop() {
        super.onStop();
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        boolean isScreenOn = pm.isInteractive();
        if(!isScreenOn)
        {
            mBackgroundSound.onPause();
        }
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode== KeyEvent.KEYCODE_BACK)
        {
            finishAffinity();
            mBackgroundSound.onPause();
        }
        return true;
    }

}
