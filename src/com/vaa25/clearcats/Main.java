package com.vaa25.clearcats;

import java.awt.*;

/**
 * @author Alexander Vlasov
 */
public class Main {
    public static void main(String[] args) {
        TargetFolder dialog = new TargetFolder();
        dialog.pack();
        dialog.setMinimumSize(dialog.getSize());
        dialog.setMaximumSize(dialog.getSize());
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        dialog.setLocation((dim.width - dialog.getWidth()) / 2, (dim.height - dialog.getHeight()) / 2);
        dialog.setVisible(true);
        System.exit(0);
    }
}
