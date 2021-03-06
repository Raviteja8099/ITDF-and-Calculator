package com.example.employeedetails;


import android.util.AttributeSet;
import android.widget.ImageView;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class RoundedImageView extends androidx.appcompat.widget.AppCompatImageView {

    public RoundedImageView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public RoundedImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RoundedImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        Drawable drawable = getDrawable();

        if (drawable == null) {
            return;
        }

        if (getWidth() == 0 || getHeight() == 0) {
            return;
        }

        Bitmap b = ((BitmapDrawable) drawable).getBitmap();
        Bitmap bitmap = b.copy(Bitmap.Config.ARGB_8888, true);

        int w = getWidth();

        Bitmap roundBitmap = getCroppedBitmap(bitmap, w);
        canvas.drawBitmap(roundBitmap, 0, 0, null);
    }

    public static Bitmap getCroppedBitmap(Bitmap bmp, int radius) {
        Bitmap sbmp;
        if (bmp.getWidth() != radius || bmp.getHeight() != radius) {
            float smallest = Math.min(bmp.getWidth(), bmp.getHeight());
            float factor = smallest / radius;
            sbmp = Bitmap.createScaledBitmap(bmp, (int)(bmp.getWidth() / factor), (int)(bmp.getHeight() / factor), false);
        }
        else {
            sbmp = bmp;
        }
        Bitmap output = Bitmap.createBitmap(radius, radius,Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xffa19774;
        final Paint paint = new Paint();
        final Paint stroke = new Paint();
        final Rect rect = new Rect(0, 0, radius, radius);
        paint.setAntiAlias(true);
        stroke.setAntiAlias(true);
        paint.setFilterBitmap(true);
        stroke.setFilterBitmap(true);
        paint.setDither(true);
        stroke.setDither(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(Color.parseColor("#BAB399"));
        stroke.setColor(Color.parseColor("#FFFFFF"));
        stroke.setStyle(Paint.Style.STROKE);
        stroke.setStrokeWidth(4f);
        canvas.drawCircle(radius / 2 + 0.7f,radius / 2 + 0.7f, radius / 2 + 0.1f, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(sbmp, rect, rect, paint);
        canvas.drawCircle(radius / 2 + 0.7f, radius / 2 + 0.7f, radius / 2 - stroke.getStrokeWidth() / 2 + 0.1f, stroke);
        return output;
    }
}

// android.os.FileUriExposedException: file:///storage/emulated/0/temp.jpg exposed beyond app through ClipData.Item.getUri()
//         at android.os.StrictMode.onFileUriExposed(StrictMode.java:1799)
//         at android.net.Uri.checkFileUriExposed(Uri.java:2354)
//         at android.content.ClipData.prepareToLeaveProcess(ClipData.java:832)
//         at android.content.Intent.prepareToLeaveProcess(Intent.java:8909)
//         at android.content.Intent.prepareToLeaveProcess(Intent.java:8894)