package mivs.ktozjakbog;

import android.content.Context;
import android.content.SharedPreferences;

public class Class_Prefs
{
    Context context;
    public void SavePrefString(Context context, String filename, String content)
    {
        this.context = context;
        SharedPreferences preferences = context.getSharedPreferences("def_shared", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(filename , content);
        editor.apply();
    }
    public String LoadPrefString(Context context, String filename)
    {
        SharedPreferences sp = context.getSharedPreferences("def_shared", Context.MODE_PRIVATE);
        String content = sp.getString(filename, "");

        return content;
    }

    public void SavePrefInt(Context context, String filename, int content)
    {
        this.context = context;
        SharedPreferences preferences = context.getSharedPreferences("def_shared", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(filename , content);
        editor.apply();
    }

    public void SavePrefFloat(Context context, String filename, float content)
    {
        this.context = context;
        SharedPreferences preferences = context.getSharedPreferences("def_shared", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putFloat(filename , content);
        editor.apply();
    }

    public void SavePrefBoolean(Context context, String filename, Boolean content)
    {
        this.context = context;
        SharedPreferences preferences = context.getSharedPreferences("def_shared", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(filename , content);
        editor.apply();
    }

    public int LoadPrefInt(Context context, String filename)
    {
        SharedPreferences sp = context.getSharedPreferences("def_shared", Context.MODE_PRIVATE);
        int content = sp.getInt(filename, 0);

        return content;
    }

    public float LoadPrefFloat(Context context, String filename)
    {
        SharedPreferences sp = context.getSharedPreferences("def_shared", Context.MODE_PRIVATE);
        float content = sp.getFloat(filename, 0);

        return content;
    }

    public boolean LoadPrefBoolean(Context context, String filename)
    {
        SharedPreferences sp = context.getSharedPreferences("def_shared", Context.MODE_PRIVATE);
        boolean content = sp.getBoolean(filename, false);

        return content;
    }
}
