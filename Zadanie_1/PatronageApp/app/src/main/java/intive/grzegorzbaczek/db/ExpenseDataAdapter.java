package intive.grzegorzbaczek.db;

import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import intive.grzegorzbaczek.R;
import intive.grzegorzbaczek.entity.Expense;
import intive.grzegorzbaczek.entity.ExpenseType;


public class ExpenseDataAdapter extends RecyclerView.Adapter<ExpenseDataAdapter.ViewHolder> {

    private Cursor mCursor;
    private int selectedPosition;
    public static long selectedItemID = -1;
    public static String selectedItemDate;
    public static String selectedItemType;
    public static String selectedItemName;
    public static String selectedItemValue;

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewDate;
        private final TextView textViewType;
        private final TextView textViewName;
        private final TextView textViewValue;

        ViewHolder(View view) {
            super(view);

            textViewDate = view.findViewById(R.id.recyclerExpenseDate_textView);
            textViewType = view.findViewById(R.id.recyclerExpenseType_textView);
            textViewName = view.findViewById(R.id.recyclerExpenseName_textView);
            textViewValue = view.findViewById(R.id.recyclerExpenseValue_textView);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (mCursor.moveToPosition(position)) {
            holder.textViewDate.setText(mCursor.getString(
                    mCursor.getColumnIndexOrThrow(Expense.DATE_NAME)
            ));
            holder.textViewName.setText(mCursor.getString(
                    mCursor.getColumnIndexOrThrow(Expense.NAME_NAME)
            ));
            holder.textViewType.setText(mCursor.getString(
                    mCursor.getColumnIndexOrThrow(ExpenseType.TYPE_NAME)
            ));
            holder.textViewValue.setText(mCursor.getString(
                    mCursor.getColumnIndexOrThrow(Expense.VALUE_NAME)
            ));
        }

        if (selectedPosition == position) {
            holder.itemView.setBackgroundColor(Color.parseColor("#FF4081"));

             selectedItemID = mCursor.getLong(mCursor.getColumnIndexOrThrow(Expense.INDEX_NAME));
            selectedItemDate = mCursor.getString(mCursor.getColumnIndexOrThrow(Expense.DATE_NAME));
            selectedItemType = mCursor.getString(mCursor.getColumnIndexOrThrow(ExpenseType.TYPE_NAME));
            selectedItemName = mCursor.getString(mCursor.getColumnIndexOrThrow(Expense.NAME_NAME));
            selectedItemValue = mCursor.getString(mCursor.getColumnIndexOrThrow(Expense.VALUE_NAME));
        } else {
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedPosition = position;
                notifyDataSetChanged();
            }
        });
    }

    public void setExpenses(Cursor cursor) {
        mCursor = cursor;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mCursor == null ? 0 : mCursor.getCount();
    }
}
