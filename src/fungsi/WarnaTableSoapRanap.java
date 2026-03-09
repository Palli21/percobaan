/*
 * Warnah tabel SOAP
 * and open the template in the editor.
 */

package fungsi;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Owner
 */
public class WarnaTableSoapRanap extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (row % 2 == 1){
            component.setBackground(new Color(255,244,244));
        }else{
            component.setBackground(new Color(255,255,255)); 
        }
        if(table.getValueAt(row,25).toString().equals("Sbar")){
            component.setBackground(new Color(234,153,153));
            component.setForeground(new Color(50,50,50)); 
        }
        if(table.getValueAt(row,25).toString().equals("Tbak")){
            component.setBackground(new Color(182,215,168));
            component.setForeground(new Color(50,50,50));     
       }
        return component;
    }

}
