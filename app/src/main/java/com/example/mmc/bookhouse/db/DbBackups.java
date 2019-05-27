package com.example.mmc.bookhouse.db;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.example.mmc.bookhouse.model.Constant;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * 数据库备份还原类
 * Created by Lizhuang on 2016/12/12.
 *
 * 备份操作
 * new DbBackups(this).execute("backupDatabase");
 * 还原操作
 * new DbBackups(this).execute("restroeDatabase");
 *
 */

public class DbBackups extends AsyncTask<String, Void, Integer> {
    public static final String COMMAND_BACKUP = "backupDatabase";
    public static final String COMMAND_RESTORE = "restroeDatabase";
    private static final int BACKUP_SUCCESS = 1;
    public static final int RESTORE_SUCCESS = 2;
    private static final int BACKUP_ERROR = 3;
    public static final int RESTORE_NOFLEERROR = 4;

    String COPY_PATH = Environment.getExternalStorageDirectory().getPath()+"/mmc_book";
   private String DB_PATH;
    private Context myContext;

    public DbBackups(Context context) {
         this.myContext = context;
         DB_PATH = "/data"+Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+context.getPackageName()+"/databases";
    }

    @Override
    protected Integer doInBackground(String... params) {
        //内存存储中数据库的位置
        File dbFile = new File(Constant.DB_PATH, "BookDatabase_1.db");
        File exportDir = new File(COPY_PATH, "CopyBookHouse");//sd卡放数据库备份文件的位置
        if (!exportDir.exists()) {
            exportDir.mkdirs();
        }
//        File backup = new File(exportDir, dbFile.getName());
        File backup = new File(exportDir, "city.db");
        String command = params[0];
        if (command.equals(COMMAND_BACKUP)) {
            try {
                backup.createNewFile();
                fileCopy(dbFile, backup);
                return BACKUP_SUCCESS;
            } catch (Exception e) {
                Log.e("DbBackups", "数据库备份异常" + e.getMessage());
                return BACKUP_ERROR;
            }
        } else if (command.equals(COMMAND_RESTORE)) {
            try {
                fileCopy(backup, dbFile);
                return RESTORE_SUCCESS;
            } catch (Exception e) {
                Log.e("DbBackups", "数据库还原异常" + e.getMessage());
                return RESTORE_NOFLEERROR;
            }
        } else {
            return null;
        }
    }

    /**
     * 备份的时候：把data下的数据库文件写入sd卡中
     * 还原的时候：把sd卡的数据库文件写入data/data/pgm/databases下
     * @throws IOException
     */
    private void fileCopy(File read, File out) throws IOException {
//        InputStream ins = myContext.getClass().getClassLoader().getResourceAsStream("assets/" + "city.db");
//        Log.d("=mmc=","----dbFile----"+dbFile.getAbsolutePath());
        InputStream ins = new FileInputStream(read);
        OutputStream outs = new FileOutputStream(out);
        byte[] buffer = new byte[1024];
        int length;
        while ((length=ins.read(buffer))>0){
            outs.write(buffer,0,length);
        }
        ins.close();
        outs.close();
    }

    @Override
    protected void onPostExecute(Integer result) {
        super.onPostExecute(result);
        switch (result) {
            case BACKUP_SUCCESS:
                Log.d("数据库备份", "成功");
                break;
            case BACKUP_ERROR:
                Log.d("数据库备份", "失败");
                break;
            case RESTORE_SUCCESS:
                Log.d("数据库还原", "成功");
                break;
            case RESTORE_NOFLEERROR:
                Log.d("数据库还原", "失败");
                break;
            default:
                break;
        }
    }

}
