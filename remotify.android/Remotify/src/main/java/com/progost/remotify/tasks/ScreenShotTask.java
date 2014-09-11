package com.progost.remotify.tasks;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import com.progost.remotify.Client;
import com.progost.remotify.GeneralComputerActivity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

/**
 * Created by Ostap on 5/20/2014.
 */
public class ScreenShotTask extends ClientAsyncTask<Void, Void, byte[]> {


    private ImageView mScreenShotView;

    public ScreenShotTask(GeneralComputerActivity activity,ImageView mScreenShotView) {
        super(activity);
        this.mScreenShotView = mScreenShotView;
    }


    @Override
    protected void onPostExecute(byte[] bytes) {
        super.onPostExecute(bytes);
        if (bytes == null)
            return;

        GZIPInputStream gzis = null;
        try {
            gzis = new GZIPInputStream(new ByteArrayInputStream(bytes));


            ByteArrayOutputStream out =
                    new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = gzis.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
            gzis.close();
            out.close();

            ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
            Bitmap bm = BitmapFactory.decodeStream(in);
            DisplayMetrics dm = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(dm);

            mScreenShotView.setMinimumHeight(dm.heightPixels);
            mScreenShotView.setMinimumWidth(dm.widthPixels);
            mScreenShotView.setImageBitmap(bm);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected byte[] execInBackground(Void... strings) {
        try{
            return client.screenShot(key,mac);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}