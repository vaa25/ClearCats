package com.vaa25.clearcats;

import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.util.HashSet;

public class TargetFolder extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField dTextField;
    private JButton button1;
    private JCheckBox checkBox1;
    private JCheckBox checkBox2;
    private JButton button2;
    private JTextField dTextField2;
    private JCheckBox сheckBox3;
    private JLabel label1;
    private JTextArea textArea1;
    private JScrollPane scrollPane;
    private HashSet<FileElement> files = new HashSet<FileElement>();  //непустые файлы
//    private HashSet<FileElement> dublicates = new HashSet<FileElement>();  //повторяющиеся файлы

    public TargetFolder() {
        setTitle("Удаление пустых папок");
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        buttonOK.setText("Start");
        buttonOK.setEnabled(false);
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });
        buttonCancel.setText("Exit");
        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });
// call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

// call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                JFileChooser selectTargetFolder = new JFileChooser();
                selectTargetFolder.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int returnVal = selectTargetFolder.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    System.out.println("You chose to open this folder: " +
                            selectTargetFolder.getSelectedFile().getAbsolutePath());
                    dTextField.setText(selectTargetFolder.getSelectedFile().getAbsolutePath());
                    buttonOK.setEnabled(true);
                }
                selectTargetFolder.setVisible(true);
            }
        });
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser selectTargetFolder = new JFileChooser();
                selectTargetFolder.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int returnVal = selectTargetFolder.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    System.out.println("You chose to open this folder: " +
                            selectTargetFolder.getSelectedFile().getAbsolutePath());
                    dTextField2.setText(selectTargetFolder.getSelectedFile().getAbsolutePath());
                    checkBox2.setEnabled(true);
                }
                selectTargetFolder.setVisible(true);
            }
        });
    }

    private void onOK() {
        label1.setText("");
        textArea1.setText("");
        buttonOK.setEnabled(false);
        File file = new File(dTextField.getText());

//        Создание тестовых папки и файла
//        targetFolder=new File("E:/Downloads");
//        checkBox2.setSelected(true);
//        new File(targetFolder,"TestDir").mkdir();
//        new File("E:/install","TestDir").mkdir();
//        try {
//            new File("E:/install","TestFile.txt").createNewFile();
//        } catch (IOException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
//        try {
//            new File(targetFolder+"/TestDir","TestFile.txt").createNewFile();
//        } catch (IOException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
        files.clear();

//        ProcessChecker processChecker=new ProcessChecker(files,capchaDisplay);
//        processChecker.start();

        MainProcess mainProcess = new MainProcess(file, checkBox1.isSelected(), checkBox2.isSelected(), сheckBox3.isSelected(), files);
        mainProcess.setTextArea(textArea1);
        mainProcess.start();
        try {
            mainProcess.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        System.out.println("Обработано: "+files.size());


//        processChecker.interrupt();
        System.out.println("Количество непустых файлов " + files.size());
        if (checkBox2.isSelected()) {
            File file2 = new File(dTextField2.getText());
//            file2=new File("E:/install");
            VerifyProcess verifyProcess = new VerifyProcess(file2, files);
            verifyProcess.setTextArea(textArea1);
            verifyProcess.start();
            try {
                verifyProcess.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (сheckBox3.isSelected()) {
                mainProcess = new MainProcess(file, false, false, true, files);
                mainProcess.setTextArea(textArea1);
                mainProcess.start();
                try {
                    mainProcess.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        }
        label1.setText("Количество непустых файлов: " + files.size());
        label1.setVisible(true);
        scrollPane.setVisible(true);
        pack();
        repaint();


        buttonOK.setEnabled(true);
    }


    private void onCancel() {
        dispose();
    }


}
