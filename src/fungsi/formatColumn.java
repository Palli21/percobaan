package fungsi;

import java.text.NumberFormat;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import widget.Table; // Asumsi Anda menggunakan kelas Table kustom Anda

/**
 *
 * @author dosen
 */
public class formatColumn {

    public static final int CURRENCY = 0;
    public static final int NUMBER = 1;
    public static final int RIGHT = 2;
    public static final int CENTER = 3;
    public static final int DATE = 4;

    public void setColumnFormat(Table table, int column, int format) {
        TableColumnModel tcm = table.getColumnModel();
        TableColumn tc = tcm.getColumn(column);

        switch (format) {
            case CURRENCY:
                tc.setCellRenderer(new CurrencyRenderer());
                break;
            case NUMBER:
                tc.setCellRenderer(new NumberRenderer());
                break;
            case RIGHT:
                tc.setCellRenderer(new RightAlignmentRenderer());
                break;
            case CENTER:
                tc.setCellRenderer(new CenterAlignmentRenderer());
                break;
            case DATE:
                tc.setCellRenderer(new DateRenderer());
                break;
        }
    }

    // Renderer untuk format mata uang
    private static class CurrencyRenderer extends DefaultTableCellRenderer {

        private final NumberFormat formatter;

        public CurrencyRenderer() {
            super();
            setHorizontalAlignment(SwingConstants.RIGHT);
            formatter = NumberFormat.getNumberInstance(Locale.getDefault()); // Menggunakan format angka default
        }

        @Override
        public void setValue(Object value) {
            if ((value != null) && (value instanceof Number)) {
                Number numberValue = (Number) value;
                value = formatter.format(numberValue.doubleValue());
            }
            super.setValue(value);
        }
    }

    // Renderer untuk format angka (tanpa simbol mata uang)
    private static class NumberRenderer extends DefaultTableCellRenderer {

        private final NumberFormat formatter;

        public NumberRenderer() {
            super();
            setHorizontalAlignment(SwingConstants.RIGHT);
            formatter = NumberFormat.getNumberInstance(Locale.getDefault());
        }

        @Override
        public void setValue(Object value) {
            if ((value != null) && (value instanceof Number)) {
                Number numberValue = (Number) value;
                value = formatter.format(numberValue.doubleValue());
            }
            super.setValue(value);
        }
    }

    // Renderer untuk perataan kanan
    private static class RightAlignmentRenderer extends DefaultTableCellRenderer {

        public RightAlignmentRenderer() {
            super();
            setHorizontalAlignment(SwingConstants.RIGHT);
        }
    }

    // Renderer untuk perataan tengah
    private static class CenterAlignmentRenderer extends DefaultTableCellRenderer {

        public CenterAlignmentRenderer() {
            super();
            setHorizontalAlignment(SwingConstants.CENTER);
        }
    }

    // Renderer untuk format tanggal
    private static class DateRenderer extends DefaultTableCellRenderer {

        private final DateFormat formatter;

        public DateRenderer() {
            super();
            setHorizontalAlignment(SwingConstants.CENTER);
            formatter = new SimpleDateFormat("dd-MM-yyyy"); // Format tanggal default, bisa disesuaikan
        }

        @Override
        public void setValue(Object value) {
            if ((value != null) && (value instanceof java.util.Date)) {
                java.util.Date dateValue = (java.util.Date) value;
                value = formatter.format(dateValue);
            }
            super.setValue(value);
        }
    }
}