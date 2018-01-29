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
            sInstance.fillTableWithTypes();
        }
        return sInstance;
    }

    public void fillTableWithTypes(){
        if (expenseDao().countTypes() == 0){
            ExpenseType type = new ExpenseType();
            beginTransaction();
            try {
                for (int i = 0; i < ExpenseType.expenseTypes.length; i++){
                    type.typeName = ExpenseType.expenseTypes[i];
                    expenseDao().insert(type);
                }
                setTransactionSuccessful();
            } finally {
                endTransaction();
            }
        }
    }
}
