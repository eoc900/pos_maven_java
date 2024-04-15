package com.eoc900.views;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Preview {

    public static JPanel total(String[][] items) {
        JPanel printTotals = new JPanel();
        printTotals.setLayout(new BoxLayout(printTotals, BoxLayout.Y_AXIS));

        for (int i = 0; i < items.length; i++) {
            if (items[i][0] == null) {
                break;
            }
            JLabel text = new JLabel(items[i][3] + "    " + items[i][1] + "    " + items[i][2]);
            printTotals.add(text);
        }
        return printTotals;
    }
}
