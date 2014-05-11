package com.mengle.lucky.database;

import java.sql.SQLException;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.mengle.lucky.adapter.PhotoListAdapter.Photo;
import com.mengle.lucky.network.model.Msg;
import com.mengle.lucky.network.model.Notice;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DataBaseHelper extends OrmLiteSqliteOpenHelper{

	private static final String DATABASE_NAME = "com.baidu.fex.cross.db";
	
	private static final int DATABASE_VERSION = 1;
	
	public DataBaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase arg0, ConnectionSource arg1) {
		try {
			TableUtils.createTableIfNotExists(connectionSource, Photo.class);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, ConnectionSource arg1, int arg2,
			int arg3) {
		try {
			TableUtils.dropTable(connectionSource, Photo.class, true);
			onCreate(database, connectionSource);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private Dao<Photo, String> pictureDao;
	
	public Dao<Photo, String> getPhotoDao() throws SQLException {
        if (pictureDao == null) {
        	pictureDao = DaoManager.createDao(getConnectionSource(), Photo.class);
        }
        return pictureDao;
    }
	
	private Dao<Notice, Integer> noticeDao;
	
	public Dao<Notice, Integer> getNoticeDao() throws SQLException {
        if (noticeDao == null) {
        	noticeDao = DaoManager.createDao(getConnectionSource(), Notice.class);
        }
        return noticeDao;
    }
	
	private Dao<Msg, Integer> msgDao;
	
	public Dao<Msg, Integer> getMsgDao() throws SQLException {
        if (msgDao == null) {
        	msgDao = DaoManager.createDao(getConnectionSource(), Notice.class);
        }
        return msgDao;
    }
	
	
	
}
