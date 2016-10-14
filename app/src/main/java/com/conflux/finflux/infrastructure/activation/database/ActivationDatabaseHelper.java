package com.conflux.finflux.infrastructure.activation.database;

import android.database.Cursor;

import com.conflux.finflux.data.local.Db;
import com.conflux.finflux.data.local.DbOpenHelper;
import com.conflux.finflux.infrastructure.activation.data.Actication;
import com.conflux.finflux.util.Logger;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Praveen J U on 10/10/2016.
 */
public class ActivationDatabaseHelper {

    private final BriteDatabase mDb;

    @Inject
    public ActivationDatabaseHelper(DbOpenHelper dbOpenHelper) {
        mDb = SqlBrite.create().wrapDatabaseHelper(dbOpenHelper);
    }

    public Observable<List<Actication>> getActivation(){
        Logger.d(getClass().getSimpleName(),"active");
        return  mDb.createQuery(Db.RibotProfileTable.ACTIVATION_TABLE,"SELECT * from "+Db.RibotProfileTable.ACTIVATION_TABLE).
                mapToList(new Func1<Cursor, Actication>() {
                    @Override
                    public Actication call(Cursor cursor) {
                        return Actication.builder(cursor);
                    }
                });
    }
}
