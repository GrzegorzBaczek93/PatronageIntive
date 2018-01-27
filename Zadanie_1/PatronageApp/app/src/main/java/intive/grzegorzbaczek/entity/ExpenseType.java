package intive.grzegorzbaczek.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class ExpenseType {

    @PrimaryKey(autoGenerate = true)
    public int typeId;

    @ColumnInfo(name = "type_name")
    public String typeName;
}
