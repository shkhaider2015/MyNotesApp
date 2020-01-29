package com.example.mynotesapp;

import java.util.ArrayList;
import java.util.List;

public class BackgroundColors {

    int[] colors;
    int[] finalColors;


    public BackgroundColors()
    {
        colors = new int[]{R.color.yellow, R.color.purple, R.color.water, R.color.red, R.color.green, R.color.pink};
    }



    protected int[] getColors(int x)
    {
        finalColors = new int[x];
        int temp = -1;

            for (int i = 0; i < x; i++)
            {
                temp = temp + 1;

                if (temp > 5)
                    temp = 0;

                finalColors[i] = colors[temp];
            }

        return finalColors;
    }
}
