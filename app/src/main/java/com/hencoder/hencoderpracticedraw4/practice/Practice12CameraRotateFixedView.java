package com.hencoder.hencoderpracticedraw4.practice;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.hencoder.hencoderpracticedraw4.R;

public class Practice12CameraRotateFixedView extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Bitmap bitmap;
    Point point1 = new Point(200, 200);
    Point point2 = new Point(600, 200);

    Camera camera = new Camera();
    Matrix matrix = new Matrix();

    public Practice12CameraRotateFixedView(Context context) {
        super(context);
    }

    public Practice12CameraRotateFixedView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice12CameraRotateFixedView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.maps);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        int center1X = point1.x + width / 2;
        int center1Y = point1.y + height / 2;

        int center2X = point2.x + width / 2;
        int center2Y = point2.y + height / 2;


        /**
         * 下面这段理解非常重要！！！！
         *
         * Canvas 的几何变换顺序是反的，所以要把移动到中心的代码写在下面，把从中心移动回来的代码写在上面。
         *
         * */

        camera.save();
        camera.rotateX(30);// 旋转 Camera 的三维空间

        canvas.save();
        /**
         * 再把画布移到原来的位置（还原）
         * 画布的操作是反着的
         * camera的操作是正着的
         * */
        canvas.translate(center1X, center1Y);// 旋转之后把投影移动回来
        camera.applyToCanvas(canvas);// 把旋转投影到 Canvas

        camera.restore();
        canvas.translate(-center1X, -center1Y);// 旋转之前把绘制内容移动到轴心（原点）

        canvas.drawBitmap(bitmap, point1.x, point1.y, paint);
        /**
         * 先把画布图形中心点移到轴心
         * */
        canvas.restore();






        camera.save();
        matrix.reset();

        camera.rotateY(30);
        /**
         * 这句？？？？
         * */
        camera.getMatrix(matrix);
        camera.restore();
        matrix.preTranslate(-center2X, -center2Y);
        matrix.postTranslate(center2X, center2Y);

        canvas.save();
        canvas.concat(matrix);


        canvas.drawBitmap(bitmap, point2.x, point2.y, paint);
        canvas.restore();
    }
}
