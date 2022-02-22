package com.example.crowdzero;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class IntroPage extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;
    public String[] Vector_Descricao;
    public IntroPage(Context context, String[] Descricao)
    {
        this.context =context;
        Vector_Descricao = Descricao;
    }




    public int[] Vector_Img = {
            R.drawable.intro_img_1,
            R.drawable.intro_img_2,
            R.drawable.intro_img_3,
            R.drawable.intro_img_4,
            R.drawable.intro_img_5
    };

    @Override
    public int getCount() {

        return Vector_Descricao.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (RelativeLayout) object;
    }


    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.intro_page,container,false);

        TextView Descricao = view.findViewById(R.id.TXT_Descricao);
        ImageView Img = view.findViewById(R.id.Img_Intro);

        Descricao.setText(Vector_Descricao[position]);
        Img.setImageResource(Vector_Img[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout) object);
    }
}



