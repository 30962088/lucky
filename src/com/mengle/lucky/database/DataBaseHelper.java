package com.mengle.lucky.database;

import java.sql.SQLException;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.mengle.lucky.adapter.PhotoListAdapter.Photo;
import com.mengle.lucky.network.model.Award;
import com.mengle.lucky.network.model.Chance;
import com.mengle.lucky.network.model.City;
import com.mengle.lucky.network.model.Msg;
import com.mengle.lucky.network.model.MsgMe;
import com.mengle.lucky.network.model.Notice;
import com.mengle.lucky.network.model.Province;
import com.mengle.lucky.network.model.Tousu;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DataBaseHelper extends OrmLiteSqliteOpenHelper {

	private static final String DATABASE_NAME = "com.mengle.lucky.db";

	private static final int DATABASE_VERSION = 1;

	private Context context;

	public DataBaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase arg0, ConnectionSource arg1) {
		try {
			TableUtils.createTableIfNotExists(connectionSource, City.class);
			TableUtils.createTableIfNotExists(connectionSource, Province.class);
			TableUtils.createTableIfNotExists(connectionSource, Photo.class);
			TableUtils.createTableIfNotExists(connectionSource, Notice.class);
			TableUtils.createTableIfNotExists(connectionSource, Msg.class);
			TableUtils.createTableIfNotExists(connectionSource, MsgMe.class);
			TableUtils.createTableIfNotExists(connectionSource, Chance.class);
			TableUtils.createTableIfNotExists(connectionSource, Award.class);
			TableUtils.createTableIfNotExists(connectionSource, Tousu.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void onUpgrade(SQLiteDatabase database, ConnectionSource arg1,
			int arg2, int arg3) {
		try {
			TableUtils.dropTable(connectionSource, City.class, true);
			TableUtils.dropTable(connectionSource, Province.class, true);
			TableUtils.dropTable(connectionSource, Photo.class, true);
			TableUtils.dropTable(connectionSource, Notice.class, true);
			TableUtils.dropTable(connectionSource, Msg.class, true);
			TableUtils.dropTable(connectionSource, MsgMe.class, true);
			TableUtils.dropTable(connectionSource, Chance.class, true);
			TableUtils.dropTable(connectionSource, Award.class, true);
			TableUtils.dropTable(connectionSource, Tousu.class, true);
			onCreate(database, connectionSource);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private Dao<Photo, String> pictureDao;

	public Dao<Photo, String> getPhotoDao() throws SQLException {
		if (pictureDao == null) {
			pictureDao = DaoManager.createDao(getConnectionSource(),
					Photo.class);
		}
		return pictureDao;
	}

	private Dao<Notice, Integer> noticeDao;

	public Dao<Notice, Integer> getNoticeDao() throws SQLException {
		if (noticeDao == null) {
			noticeDao = DaoManager.createDao(getConnectionSource(),
					Notice.class);
		}
		return noticeDao;
	}

	private Dao<City, Integer> cityDao;

	public Dao<City, Integer> getCityDao() throws SQLException {
		if (cityDao == null) {
			cityDao = DaoManager.createDao(getConnectionSource(), City.class);
		}
		return cityDao;
	}

	private Dao<Province, Integer> provinceDao;

	public Dao<Province, Integer> getProvinceDao() throws SQLException {
		if (provinceDao == null) {
			provinceDao = DaoManager.createDao(getConnectionSource(),
					Province.class);
		}
		return provinceDao;
	}

	private Dao<Msg, Integer> msgDao;

	public Dao<Msg, Integer> getMsgDao() throws SQLException {
		if (msgDao == null) {
			msgDao = DaoManager.createDao(getConnectionSource(), Msg.class);
		}
		return msgDao;
	}

	private Dao<MsgMe, String> msgMeDao;

	public Dao<MsgMe, String> getMsgMeDao() throws SQLException {
		if (msgMeDao == null) {
			msgMeDao = DaoManager.createDao(getConnectionSource(), MsgMe.class);
		}
		return msgMeDao;
	}

	private Dao<Chance, String> chanceDao;

	public Dao<Chance, String> getChanceDao() throws SQLException {
		if (chanceDao == null) {
			chanceDao = DaoManager.createDao(getConnectionSource(),
					Chance.class);
		}
		return chanceDao;
	}

	private Dao<Award, String> awardDao;

	public Dao<Award, String> getAwardDao() throws SQLException {
		if (awardDao == null) {
			awardDao = DaoManager.createDao(getConnectionSource(), Award.class);
		}
		return awardDao;
	}

	private Dao<Tousu, String> tousuDao;

	public Dao<Tousu, String> getTousuDao() throws SQLException {
		if (tousuDao == null) {
			tousuDao = DaoManager.createDao(getConnectionSource(), Tousu.class);
		}
		return tousuDao;
	}

}
