package fungsi;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class WarnaRiwayatSoap extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        // Warna default zebra (striped) row
        if (row % 2 == 1){
            component.setBackground(new Color(255,244,244));
        } else {
            component.setBackground(new Color(255,255,255)); 
        }

        // Warna berdasarkan isi kolom ke-9 (indeks 8)
        String kode = table.getValueAt(row,8).toString();
        switch(kode){
            case "01SPPD":
                component.setBackground(new Color(244,204,204));
                break;
            case "02SPPD":
                component.setBackground(new Color(249,203,156));
                break;
            case "02SPOG":
                component.setBackground(new Color(213,166,189));
                break;
            case "01SPB":
                component.setBackground(new Color(255,242,204));
                break;
            case "02SPB":
                component.setBackground(new Color(111,168,220));
                break;
            case "01SPJ":
                component.setBackground(new Color(245,220,220));
                break;
            case "01SPN": // catatan: kode ini muncul dua kali di kode aslinya
                component.setBackground(new Color(147,196,125));
                break;
            
        }

        // Jika baris di-klik (selected), ganti warna tulisan
        if(isSelected){
            component.setForeground(Color.BLUE); // warna teks saat dipilih
        } else {
            component.setForeground(Color.BLACK); // warna teks normal
        }

        return component;
    }
}
