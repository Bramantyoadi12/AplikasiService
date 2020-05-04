package com.example.aplikasiservice;

import android.app.Service;
import android.app.WallpaperManager;
import android.os.Bundle;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import java.io.IOException;

public class WallpaperChangeService extends Service implements Runnable{
    /* referensi gambar wallpaper disimpan dalam sebuah array, wallpaper1 dan wallpaper2 adalah nama file png atau jpeg anda */
    private int wallpaperId[] = {R.drawable.wallpaper1, R.drawable.wallpaper2};
    /* Deklarasi variabel yg digunakan utk menyimpan durasi yg dipilih user */
    private int waktu;
    /* Deklarasi variabel flag utk nge check gambar mana yang akan ditampilkan berikutnya */
    private int FLAG=0;
    private Thread t;
    /* Deklarasi 2 buah variabel bitmat utk menyimpan gambar wallpaper */
    private Bitmap gambar;
    private Bitmap gambar1;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flag, int startId)
    {
        super.onStartCommand(intent, flag, startId);
        /* peroleh bundle yang dikirim melalui intent dari MainActivity */
        Bundle bundle=intent.getExtras();
        /* baca nilai yg tersimpan dengan kunci 'durasi' */
        waktu=bundle.getInt("durasi");
        /* Inisialisasi obyek Bitmap dengan gambar wallpaper */
        gambar=BitmapFactory.decodeResource(getResources(), wallpaperId[0]);
        gambar1=BitmapFactory.decodeResource(getResources(), wallpaperId[1]);
        /* Memulai sebuah thread agar service tetap berjalan di latar belakang. */

        t = new Thread(WallpaperChangeService.this);
        t.start();
        return START_STICKY_COMPATIBILITY;
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        System.exit(0);
    }
    public void run() {
        WallpaperManager myWallpaper;
        myWallpaper = WallpaperManager.getInstance(this);
        try {
            while (true) {
                if (FLAG==0) {
                    myWallpaper.setBitmap(gambar);
                    FLAG++;
                }
                else {
                    myWallpaper.setBitmap(gambar1);
                    FLAG--;
                }
                Thread.sleep(100*waktu);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}