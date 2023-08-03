package com.example.baseballmanagementapp.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.evrencoskun.tableview.adapter.AbstractTableAdapter;
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder;
import com.example.baseballmanagementapp.R;
import com.example.baseballmanagementapp.models.Cell;
import com.example.baseballmanagementapp.models.ColumnHeader;
import com.example.baseballmanagementapp.models.RowHeader;

public class TeamMembersTableAdapter extends AbstractTableAdapter<ColumnHeader, RowHeader, Cell> {
//    private final ArrayList<UserTeam> list;
//    private final Context context;
//    public TeamMembersTableAdapter(Context context, ArrayList<UserTeam> list) {
//        super();
//        this.context = context;
//        this.list = list;
//    }

    public TeamMembersTableAdapter() {

    }

    @Override
    public int getColumnHeaderItemViewType(int position) {
        return 1;
    }

    @Override
    public int getRowHeaderItemViewType(int position) {
        return 1;
    }

    @Override
    public int getCellItemViewType(int position) {
        return 1;
    }

    @NonNull
    @Override
    public AbstractViewHolder onCreateCellViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Get cell xml layout
        View layout = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.table_view_cell_layout, parent, false);
        // Create a Custom ViewHolder for a Cell item.
        return new TableCellViewHolder(layout);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindCellViewHolder(@NonNull AbstractViewHolder holder, @Nullable Cell cellItemModel, int columnPosition, int rowPosition) {
        TableCellViewHolder viewHolder = (TableCellViewHolder) holder;
//        assert cellItemModel != null;
////        assert cellItemModel.getData() != null;
        Cell cell = (Cell) cellItemModel;
        if (cellItemModel.getData() != null) {
            viewHolder.cell_textview.setText(cell.getData().toString());
        }
        else {
            viewHolder.cell_textview.setText("Unknown");
        }
        switch (columnPosition) {
            case 0:
                viewHolder.cell_container.getLayoutParams().width = 300;
                break;
            case 1:
                viewHolder.cell_container.getLayoutParams().width = 500;
                break;
            case 2:
            case 4:
            case 3:
                viewHolder.cell_container.getLayoutParams().width = 250;
                break;
        }

        viewHolder.cell_textview.requestLayout();
    }

    @NonNull
    @Override
    public AbstractViewHolder onCreateColumnHeaderViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Get Column Header xml Layout
        View layout = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.table_view_column_header_layout, parent, false);

        // Create a ColumnHeader ViewHolder
        return new MyColumnHeaderViewHolder(layout);
    }

    @Override
    public void onBindColumnHeaderViewHolder(@NonNull AbstractViewHolder holder, @Nullable ColumnHeader columnHeaderItemModel, int columnPosition) {
        ColumnHeader columnHeader = (ColumnHeader) columnHeaderItemModel;

        // Get the holder to update cell item text
        MyColumnHeaderViewHolder columnHeaderViewHolder = (MyColumnHeaderViewHolder) holder;
        columnHeaderViewHolder.cell_textview.setText(columnHeader.getData().toString());

        // If your TableView should have auto resize for cells & columns.
        // Then you should consider the below lines. Otherwise, you can ignore them.

        // It is necessary to remeasure itself.
        switch (columnPosition) {
            case 0:
                columnHeaderViewHolder.column_header_container.getLayoutParams().width = 300;
                break;
            case 1:
                columnHeaderViewHolder.column_header_container.getLayoutParams().width = 500;
                break;
            case 2:
            case 4:
            case 3:
                columnHeaderViewHolder.column_header_container.getLayoutParams().width = 250;
                break;
        }

        columnHeaderViewHolder.cell_textview.requestLayout();
    }

    @NonNull
    @Override
    public AbstractViewHolder onCreateRowHeaderViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindRowHeaderViewHolder(@NonNull AbstractViewHolder holder, @Nullable RowHeader rowHeaderItemModel, int rowPosition) {

    }

    @NonNull
    @Override
    public View onCreateCornerView(@NonNull ViewGroup parent) {
        return LayoutInflater.from(parent.getContext())
                .inflate(R.layout.table_view_corner_layout, parent, false);
    }

//    @Override
//    public int getRowCount() {
//        // Return the number of rows (number of team members)
//        return list.size();
//    }
//
//    @Override
//    public int getColumnCount() {
//        // Return the number of columns (number of member attributes)
//        return 5;
//    }



    // Create a ViewHolder class for the table cell
    private static class TableCellViewHolder extends AbstractViewHolder {
        final LinearLayout cell_container;
        final TextView cell_textview;

        TableCellViewHolder(View itemView) {
            super(itemView);
            cell_container = itemView.findViewById(R.id.cell_container);
            cell_textview = itemView.findViewById(R.id.cell_data);
        }
    }

    private static class MyColumnHeaderViewHolder extends AbstractViewHolder {

        final LinearLayout column_header_container;
        final TextView cell_textview;

        public MyColumnHeaderViewHolder(View itemView) {
            super(itemView);
            this.column_header_container = itemView.findViewById(R.id.column_header_container);
            this.cell_textview = itemView.findViewById(R.id.column_header_textView);
        }
    }
}

