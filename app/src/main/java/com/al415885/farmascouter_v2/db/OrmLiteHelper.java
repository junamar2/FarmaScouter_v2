package com.al415885.farmascouter_v2.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.al415885.farmascouter_v2.models.cima.secondlevel.MedSecond;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class OrmLiteHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "favouriteDrugs.db";

    private static final int DATABASE_VERSION = 1;

    private static OrmLiteHelper mInstance = null;

    public OrmLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            // TODO: Add all your entities here, to create the tables.
            TableUtils.createTable(connectionSource, MedSecond.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            // TODO: Add all your entities here, to drop and recreate then.
            TableUtils.dropTable(connectionSource, MedSecond.class, true);
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static OrmLiteHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new OrmLiteHelper(context.getApplicationContext());
        }
        return mInstance;
    }

    public RuntimeExceptionDao getRuntimeDao(Class model) {
        return getRuntimeExceptionDao(model);
    }

}