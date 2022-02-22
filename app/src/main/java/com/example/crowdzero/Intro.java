package com.example.crowdzero;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static java.lang.Math.abs;

public class Intro extends AppCompatActivity {

    private ViewPager Slide;
    private IntroPage introPage;

    private LinearLayout linearLayout;
    private TextView[] nDots;

    private Button BTN_Voltar;
    private Button BTN_Proximo;
    private int CurrentPage;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_intro);

        if(loadState())
        {
            startActivity(new Intent(Intro.this,Login.class));
        }

        Slide = (ViewPager) findViewById(R.id.intro_view_pager);
        linearLayout = (LinearLayout) findViewById(R.id.dots_layout);
        BTN_Voltar = findViewById(R.id.Voltar_Intro);
        BTN_Proximo = findViewById(R.id.Proximo_Intro);

        introPage = new IntroPage(this,getResources().getStringArray(R.array.Descricao));



        Slide.setAdapter(introPage);
        Slide.setPageTransformer(true,new FadeOutTransformation());

        addDots(0);
        Slide.addOnPageChangeListener(viewListener);



        BTN_Proximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(CurrentPage == nDots.length - 1)
                {
                    startActivity(new Intent(Intro.this,Login.class));
                    saveState();
                }else{
                    Slide.setCurrentItem(CurrentPage + 1,true);
                }

            }
        });

        BTN_Voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Slide.setCurrentItem(CurrentPage - 1,true);
            }
        });


    }

    public void addDots(int position)
    {
        nDots = new TextView[getResources().getStringArray(R.array.Descricao).length];
        linearLayout.removeAllViews();

        for(int i = 0; i < nDots.length; i++)
        {
            nDots[i] = new TextView(this);
            nDots[i].setText(Html.fromHtml("&#8226;"));
            nDots[i].setTextSize(35);
            nDots[i].setTextColor(getResources().getColor(R.color.TextAlt));

            linearLayout.addView(nDots[i]);
        }

        if(nDots.length > 0)
        {
            nDots[position].setTextColor(getResources().getColor(R.color.TextMain));
        }


    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDots(position);
            CurrentPage = position;

            if(position == 0)
            {
                BTN_Proximo.setEnabled(true);
                BTN_Voltar.setEnabled(false);
                BTN_Voltar.setVisibility(View.INVISIBLE);

                BTN_Proximo.setText(getString(R.string.Proximo));
                BTN_Voltar.setText("");

            } else if(position == nDots.length - 1)
            {
                BTN_Proximo.setEnabled(true);
                BTN_Voltar.setEnabled(true);
                BTN_Voltar.setVisibility(View.VISIBLE);

                BTN_Voltar.setText(getString(R.string.Voltar));

            }else {
                BTN_Proximo.setEnabled(true);
                BTN_Voltar.setEnabled(true);
                BTN_Voltar.setVisibility(View.VISIBLE);

                BTN_Proximo.setText(getString(R.string.Proximo));
                BTN_Voltar.setText(getString(R.string.Voltar));

            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }

    };

    private void saveState()
    {
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isIntroOpnend",true);
        editor.commit();
    }

    private Boolean loadState()
    {
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        Boolean isIntroOpnendBefore = preferences.getBoolean("isIntroOpnend",false);
        return isIntroOpnendBefore;
    }

    public class FadeOutTransformation implements ViewPager.PageTransformer{
        @Override
        public void transformPage(View page, float position) {
            page.setAlpha(1 - abs(position));
            page.setTranslationX(- position * page.getWidth());
        }
    }

}

