package intive.grzegorzbaczek.db;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import intive.grzegorzbaczek.R;
import intive.grzegorzbaczek.entity.Expense;


public class ExpenseDataAdapter extends RecyclerView.Adapter<ExpenseDataAdapter.ViewHolder> {

    private Cursor mCursor;

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
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(mCursor.moveToPosition(position)){
            holder.textViewDate.setText(mCursor.getString(
                    mCursor.getColumnIndexOrThrow(Expense.DATE_NAME)
            ));
            holder.textViewName.setText(mCursor.getString(
                    mCursor.getColumnIndexOrThrow(Expense.NAME_NAME)
            ));
            holder.textViewType.setText(mCursor.getString(
                    mCursor.getColumnIndexOrThrow(Expense.TYPEID_NAME)
            ));
            holder.textViewValue.setText(mCursor.getString(
                    mCursor.getColumnIndexOrThrow(Expense.VALUE_NAME)
            ));
        }
    }

    public void setExpenses(Cursor cursor){
        mCursor = cursor;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mCursor == null ? 0 : mCursor.getCount();
    }
}
