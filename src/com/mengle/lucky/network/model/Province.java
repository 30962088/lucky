package com.mengle.lucky.network.model;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.google.gson.JsonObject;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import com.mengle.lucky.database.DataBaseHelper;
import com.mengle.lucky.utils.Preferences;
import com.mengle.lucky.utils.Preferences.User;

@DatabaseTable(tableName = "Province")
public class Province {
	@DatabaseField(id = true)
	private int id;
	@DatabaseField
	private String name;

	@ForeignCollectionField(eager = false)
	private Collection<City> cities;

	public Province() {
		// TODO Auto-generated constructor stub
	}

	public static void build(Context context) throws Exception {

		Preferences.User user = new Preferences.User(context);

		if (!user.isBuildProvince()) {
			JSONObject provinceObj = new JSONObject(IOUtils.toString(context
					.getAssets().open("txt/province.txt")));

			JSONObject cityObj = new JSONObject(IOUtils.toString(context
					.getAssets().open("txt/city.txt")));

			DataBaseHelper helper = new DataBaseHelper(context);

			for (Iterator<String> itor = provinceObj.keys(); itor.hasNext();) {
				String key = itor.next();
				Province province = new Province();
				province.id = Integer.parseInt(key);
				province.name = provinceObj.getString(key);
				helper.getProvinceDao().createOrUpdate(province);
				JSONObject cObj = cityObj.getJSONObject(key);

				for (Iterator<String> itor2 = cObj.keys(); itor2.hasNext();) {
					City city = new City();

					String ckey = itor2.next();

					city.setId(Integer.parseInt(ckey));
					city.setName(cObj.getString(ckey));
					city.setProvince(province);
					helper.getCityDao().createOrUpdate(city);
				}

			}
			
			user.setBuildProvince(true);
			
		}

	}

	public int getId() {
		return id;
	}

	public Collection<City> getCities() {
		return cities;
	}

	public String getName() {
		return name;
	}

}
