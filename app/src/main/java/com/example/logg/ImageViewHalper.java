package com.example.logg;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class ImageViewHalper {
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static Bitmap ImageFromDrawable(Context context , Bitmap bitmap){
       // Bitmap bitmap = ((BitmapDrawable) context.getDrawable(drawable)).getBitmap();
        //Bitmap bitmap = BitmapFactory.decodeByteArray(drawble, 0, drawble.length);
        return roundBitmap(bitmap);
    }
    public static Bitmap roundBitmap(Bitmap bitmap){
        Bitmap roundedBitmap = Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(),bitmap.getConfig());
        Canvas c = new Canvas(roundedBitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(new BitmapShader(bitmap,Shader.TileMode.CLAMP,Shader.TileMode.CLAMP));
        RectF rectF =new RectF(0,0,bitmap.getWidth(),bitmap.getHeight());
        c.drawRoundRect(rectF,20,20,paint);
        return roundedBitmap;

    }
}
