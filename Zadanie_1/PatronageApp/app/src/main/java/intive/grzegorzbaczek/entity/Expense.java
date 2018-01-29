package intive.grzegorzbaczek.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.content.ContentValues;
import android.provider.BaseColumns;

@Entity(tableName = Expense.TABLE_NAME)
public class Expense {

    public static final String INDEX_NAME = BaseColumns._ID;
    public static final String TABLE_NAME = "expenses";
    public static final String DATE_NAME = "date";
    public static final String TYPEID_NAME = "type_id";
    public static final String NAME_NAME = "name";
    public static final String VALUE_NAME = "value";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true, name = Expense.INDEX_NAME)
    public long id;

    @ColumnInfo(name = Expense.DATE_NAME)
    public String date;

    @ForeignKey(entity = ExpenseType.class, parentColumns = Expense.TYPEID_NAME, childColumns = ExpenseType.INDEX_NAME)
    @ColumnInfo(name = Expense.TYPEID_NAME)
    public int typeId;

    @ColumnInfo(name = Expense.NAME_NAME)
    public String name;

    @ColumnInfo(name = Expense.VALUE_NAME)
    public String value;

    public static Expense fromContentValues(ContentValues values){
        final Expense expense = new Expense();
        if(values.containsKey(INDEX_NAME)){
            expense.id = values.getAsLong(INDEX_NAME);
        }
        if(values.containsKey(DATE_NAME)){
            expense.date = values.getAsString(DATE_NAME);
        }
        if(values.containsKey(TYPEID_NAME)){
            expense.typeId = values.getAsInteger(TYPEID_NAME);
        }
        if(values.containsKey(NAME_NAME)){
            expense.name = values.getAsString(NAME_NAME);
        }
        if(values.containsKey(VALUE_NAME)){
            expense.value = values.getAsString(VALUE_NAME);
        }
        return expense;
    }
}

