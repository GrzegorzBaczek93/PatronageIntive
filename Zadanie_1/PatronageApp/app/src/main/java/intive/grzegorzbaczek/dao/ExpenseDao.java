package intive.grzegorzbaczek.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.Cursor;

import intive.grzegorzbaczek.entity.Expense;
import intive.grzegorzbaczek.entity.ExpenseType;

@Dao
public interface ExpenseDao {
    @Query("SELECT * FROM " + Expense.TABLE_NAME +" LEFT JOIN " + ExpenseType.TABLE_NAME +
    " ON " + Expense.TYPEID_NAME + " = " + ExpenseType.INDEX_NAME)
    Cursor selectAll();

    @Query("SELECT COUNT(*) FROM " + ExpenseType.TABLE_NAME)
    int countTypes();

    @Query("SELECT * FROM " + Expense.TABLE_NAME + " WHERE " + Expense.INDEX_NAME + " = :id")
    Cursor selectById(long id);

    @Query("DELETE FROM " + Expense.TABLE_NAME + " WHERE " + Expense.INDEX_NAME + " = :id")
    int deleteById(long id);

    @Insert
    long[] insertAll(Expense[] expenses);

    @Insert
    long insert(Expense expense);

    @Insert
    void insert(ExpenseType expenseType);

    @Update
    int update(Expense expense);
}
