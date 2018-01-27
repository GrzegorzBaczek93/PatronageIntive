package intive.grzegorzbaczek.db;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import intive.grzegorzbaczek.dao.ExpenseDao;
import intive.grzegorzbaczek.entity.Expense;
import intive.grzegorzbaczek.entity.ExpenseType;

@Database(entities = {Expense.class, ExpenseType.class}, version=1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract ExpenseDao expenseDao();

    private static AppDatabase sInstance;

    public static synchronized AppDatabase getInstance(Context context){
        if (sInstance == null){
            sInstance = Room
                    .databaseBuilder(context.getApplicationContext(), AppDatabase.class, "expensedb")
                    .build();
        }
        return sInstance;
    }
}
