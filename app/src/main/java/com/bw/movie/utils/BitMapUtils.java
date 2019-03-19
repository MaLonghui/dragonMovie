package com.bw.movie.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Base64;
import android.widget.ImageView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class BitMapUtils {
//    private static final int OPTIONS_NONE = 0;
//    private static final int OPTIONS_SCALE_UP = 1;
//
//    /**
//     * Creates a centered bitmap of the desired size.//创建所需大小的中心位图。
//     *
//     * @param source original bitmap source
//     * @param width targeted width
//     * @param height targeted height
//     */
//    public static Bitmap extractThumbnail(
//            Bitmap source, int width, int height) {
//        return extractThumbnail(source, width, height, OPTIONS_NONE);
//    }
//
//    /**
//     * Creates a centered bitmap of the desired size.
//     *
//     * @param source original bitmap source
//     * @param width targeted width
//     * @param height targeted height
//     * @param options options used during thumbnail extraction
//     */
//    public static Bitmap extractThumbnail(
//            Bitmap source, int width, int height, int options) {
//        if (source == null) {
//            return null;
//        }
//
//        float scale;
//        if (source.getWidth() < source.getHeight()) {
//            scale = width / (float) source.getWidth();
//        } else {
//            scale = height / (float) source.getHeight();
//        }
//        Matrix matrix = new Matrix();
//        matrix.setScale(scale, scale);
//        Bitmap thumbnail = transform(matrix, source, width, height,
//                OPTIONS_SCALE_UP | options);
//        return thumbnail;
//    }

//2、BitmapUtils工具类方法的收集
    public static class BitmapUtils {

        /**
         * 将base64转换成bitmap图片
         *  @param string base64字符串
         *  @return bitmap
         *
         */
        public static Bitmap stringToBitmap(String string) {
            if (TextUtils.isEmpty(string)) {
                return null;
            }
            // 将字符串转换成Bitmap类型
            Bitmap bitmap = null;
            try {
                byte[] bitmapArray;
                bitmapArray = Base64.decode(string, Base64.DEFAULT);
                bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
                        bitmapArray.length);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        /**
         * 将Bitmap照片保存到本地
         * @param bitmap
         * @param bitName
         */
        public static String saveBitmap(Bitmap bitmap,String bitName)
        {
            String signPhotoPath = Environment.getExternalStorageDirectory( ) + "/DCIM/" + bitName + ".jpg";
            File file = new File(signPhotoPath);
            if(file.exists()){
                file.delete();
            }
            FileOutputStream out;
            try{
                out = new FileOutputStream(file);
                if(bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)){
                    out.flush();
                    out.close();
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
            return signPhotoPath;
        }

        /**
         * 按照一定的宽高比例裁剪图片
         *
         * @param bitmap
         * @param num1
         *            长边的比例
         * @param num2
         *            短边的比例
         * @return
         */
        public static Bitmap ImageCrop(Bitmap bitmap, int num1, int num2,
                                       boolean isRecycled)
        {
            if (bitmap == null)
            {
                return null;
            }
            int w = bitmap.getWidth(); // 得到图片的宽，高
            int h = bitmap.getHeight();
            int retX, retY;
            int nw, nh;
            if (w > h)
            {
                if (h > w * num2 / num1)
                {
                    nw = w;
                    nh = w * num2 / num1;
                    retX = 0;
                    retY = (h - nh) / 2;
                } else
                {
                    nw = h * num1 / num2;
                    nh = h;
                    retX = (w - nw) / 2;
                    retY = 0;
                }
            } else
            {
                if (w > h * num2 / num1)
                {
                    nh = h;
                    nw = h * num2 / num1;
                    retY = 0;
                    retX = (w - nw) / 2;
                } else
                {
                    nh = w * num1 / num2;
                    nw = w;
                    retY = (h - nh) / 2;
                    retX = 0;
                }
            }
//            LogUtil.d("retX",retX+"");
//            LogUtil.d("retY",retY+"");
//            LogUtil.d("nw",nw+"");
//            LogUtil.d("nh",nh+"");
            Bitmap bmp = Bitmap.createBitmap(bitmap, retX, retY, nw, nh, null,
                    false);
            if (isRecycled && bitmap != null && !bitmap.equals(bmp)
                    && !bitmap.isRecycled())
            {
                bitmap.recycle();
                bitmap = null;
            }
            return bmp;// Bitmap.createBitmap(bitmap, retX, retY, nw, nh, null,
            // false);
        }

        /**
         * 质量压缩方法
         * @param mBitmap
         * @return
         */
        public static Bitmap qualityCompressBitmap(Bitmap mBitmap) {

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
            int options = 100;
//            LogUtil.d("压缩前图片占内存大小（KB）====" + baos.toByteArray().length / 1024);
            while ( baos.toByteArray().length / 1024>80) { //循环判断如果压缩后图片是否大于100kb,大于继续压缩
                baos.reset();//重置baos即清空baos
                mBitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
                options -= 10;//每次都减少10
//                LogUtil.d("压缩后图片占内存大小（KB）" + baos.toByteArray().length / 1024);

            }
            if ( !mBitmap.isRecycled()){
                mBitmap.recycle();
                mBitmap =  null;
                System.gc();
            }
            ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
            Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
            return bitmap;
        }

        /**
         * 图片按比例大小压缩方法（根据Bitmap图片压缩）
         * @param mBitmap
         * @return
         */
        public static Bitmap proportionCompressBitmap(Bitmap mBitmap) {

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            if( baos.toByteArray().length / 1024 > 80) {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
                baos.reset();//重置baos即清空baos
                mBitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);//这里压缩50%，把压缩后的数据存放到baos中
            }
            ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
            BitmapFactory.Options newOpts = new BitmapFactory.Options();
            //开始读入图片，此时把options.inJustDecodeBounds 设回true了
            newOpts.inJustDecodeBounds = true;
            Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
            newOpts.inJustDecodeBounds = false;
            int w = newOpts.outWidth;
            int h = newOpts.outHeight;
            //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
            float hh = 480f;//这里设置高度为800f
            float ww = 640f;//这里设置宽度为480f
            //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
            int be = 1;//be=1表示不缩放
            if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
                be = (int) (newOpts.outWidth / ww);
            } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
                be = (int) (newOpts.outHeight / hh);
            }
            if (be <= 0)
                be = 1;
            newOpts.inSampleSize = be;//设置缩放比例
            //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
            isBm = new ByteArrayInputStream(baos.toByteArray());
            bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
            return qualityCompressBitmap(bitmap);//压缩好比例大小后再进行质量压缩
//        return bitmap;//压缩好比例大小
        }

        public static byte[] transferBitmapToBytes(Bitmap bitmap){
            ByteArrayOutputStream output = new ByteArrayOutputStream();//初始化一个流对象
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);//把bitmap100%高质量压缩 到 output对象里
            bitmap.recycle();//自由选择是否进行回收
            byte[] result = output.toByteArray();//转换成功了
            try {
                output.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        /**
         * 设置照片
         * @param imageView
         * @param bytes
         */
        public static void setBitmap(ImageView imageView, byte[] bytes){
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = false;
            options.inSampleSize = 2;//图片宽高都为原来的二分之一，即图片为原来的四分之一
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length, options);
            imageView.setImageBitmap(bitmap);
            if (bitmap != null&&!bitmap.isRecycled()){
                bitmap.isRecycled();
                bitmap = null;
                System.gc();
            }
        }

        public static Bitmap getBitmap(byte[] bytes){
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = false;
            options.inSampleSize = 2;//图片宽高都为原来的二分之一，即图片为原来的四分之一
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length, options);
            return  bitmap;
        }
        /**
         * 将图片转成指定弧度（角度）的图片
         *
         * @param bitmap 需要修改的图片
         * @param pixels 圆角的弧度
         * @return 圆角图片
         */
        public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {
            Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            //根据图片创建画布
            Canvas canvas = new Canvas(output);
            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            final RectF rectF = new RectF(rect);
            final float roundPx = pixels;
            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(0xff424242);
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(bitmap, rect, rect, paint);
            return output;
        }
        public static Bitmap toRoundBitmap(Bitmap bitmap) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            float roundPx;
            float left,top,right,bottom,dst_left,dst_top,dst_right,dst_bottom;
            if (width <= height) {
                roundPx = width / 2;
                top = 0;
                bottom = width;
                left = 0;
                right = width;
                height = width;
                dst_left = 0;
                dst_top = 0;
                dst_right = width;
                dst_bottom = width;
            } else {
                roundPx = height / 2;
                float clip = (width - height) / 2;
                left = clip;
                right = width - clip;
                top = 0;
                bottom = height;
                width = height;
                dst_left = 0;
                dst_top = 0;
                dst_right = height;
                dst_bottom = height;
            }
            Bitmap output = Bitmap.createBitmap(width,
                    height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);
            final int color = 0xff424242;
            final Paint paint = new Paint();
            final Rect src = new Rect((int)left, (int)top, (int)right, (int)bottom);
            final Rect dst = new Rect((int)dst_left, (int)dst_top, (int)dst_right, (int)dst_bottom);
            final RectF rectF = new RectF(dst);
            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(color);
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(bitmap, src, dst, paint);
            return output;
        }
    }
}
