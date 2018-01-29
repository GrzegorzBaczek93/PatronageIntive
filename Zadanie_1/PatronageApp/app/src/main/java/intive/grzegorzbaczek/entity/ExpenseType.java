package intive.grzegorzbaczek.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.provider.BaseColumns;

@Entity(tableName = ExpenseType.TABLE_NAME)
public class ExpenseType {

    public static final String TABLE_NAME = "expenseTypes";
    public static final String INDEX_NAME = "typeID";
    public static final String TYPE_NAME = "typeName";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true, name = ExpenseType.INDEX_NAME)
    public int typeId;

    @ColumnInfo(name = ExpenseType.TYPE_NAME)
    public String typeName;

    public static String[] expenseTypes = {
            "Food", "Bills", "Health Care", "Clothes", "Others"
    };
}
