package com.soclosetoheaven.client.ui.model;

import com.soclosetoheaven.client.locale.LocaledUI;
import com.soclosetoheaven.client.locale.Localizer;
import com.soclosetoheaven.common.model.Dragon;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;
import java.util.regex.PatternSyntaxException;
import java.util.stream.IntStream;

public class DragonTableModel extends DefaultTableModel {




    public void insertDragon(Dragon dragon) {
        Object[] data = new Object[] {
                dragon.getID(),
                dragon.getName(),
                dragon.getCoordinates().getX(),
                dragon.getCoordinates().getY(),
                Localizer.getInstance().formatDate(dragon.getCreationDate()),
                dragon.getAge(),
                dragon.getDescription(),
                dragon.getWingspan(),
                dragon.getType(),
                dragon.getCave().getDepth(),
                dragon.getCave().getNumberOfTreasures(),
                dragon.getCreatorId()
        };
        addRow(data);
    }

    public void updateDragon(Dragon dragon) {
        deleteDragon(dragon);
        insertDragon(dragon);
    }

    public void deleteDragon(Dragon dragon) {
        OptionalInt optionalRowIndex = IntStream.range(0, getRowCount())
                .filter(i -> (getValueAt(i, ID_INDEX)).equals(dragon.getID()))
                .findFirst();
        int rowIndex = optionalRowIndex.orElse(ROW_UNKNOWN);
        if (rowIndex != ROW_UNKNOWN)
            removeRow(rowIndex);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

//    public static String[] COLUMN_KEYS = {
//            "id",
//            "name",
//            "x_cord",
//            "y_cord",
//            "date",
//            "age",
//            "description",
//            "wingspan",
//            "type",
//            "depth",
//            "treasures",
//            "creator_id"
//    };

    public static final List<LocaledUI> COLUMN_KEYS = Arrays.asList(
            LocaledUI.ID,
            LocaledUI.NAME,
            LocaledUI.X_COORDINATE,
            LocaledUI.Y_COORDINATE,
            LocaledUI.DATE,
            LocaledUI.AGE,
            LocaledUI.DESCRIPTION,
            LocaledUI.WINGSPAN,
            LocaledUI.TYPE,
            LocaledUI.DEPTH,
            LocaledUI.TREASURES,
            LocaledUI.CREATOR_ID
    );

    public static final int ID_INDEX = 0;

    public static final int ROW_UNKNOWN = -1;

    public static final int DATE_INDEX = 4;




    public void applyFilter(String pattern, DefaultRowSorter<? extends DefaultTableModel, ? extends Integer> sorter) {
        final int firstKeyIndex = 0;
        List<? extends RowSorter.SortKey> keys = sorter.getSortKeys();
        RowFilter<DefaultTableModel, Integer> rowFilter = null;
        if (!keys.isEmpty() && !pattern.isEmpty()) {
            int keyIndex = keys.get(firstKeyIndex).getColumn();
            try {
                rowFilter = RowFilter.regexFilter(pattern, keyIndex);
            } catch (PatternSyntaxException e) {
                return;
            }
        }
        sorter.setRowFilter(rowFilter);
    }


    public void setDefaultTable() {
        setColumnIdentifiers(COLUMN_KEYS.stream().map(i -> Localizer.getInstance().getStringByKey(i.key)).toArray());
        IntStream.range(0, getRowCount()).forEach(i ->
                setValueAt(
                        Localizer
                                .getInstance()
                                .formatDate(getValueAt(i, DATE_INDEX)), i, DATE_INDEX));
    }
}
