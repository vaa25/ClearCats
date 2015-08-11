package com.vaa25.clearcats;

import javax.swing.*;
import java.io.File;
import java.util.HashSet;

/**
 * Created with IntelliJ IDEA.
 * User: Vlasov Alexander
 * Date: 27.07.13
 * Time: 19:12
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractProcess extends Thread {
    protected File targetFolder;
    protected HashSet<FileElement> files;
    protected JTextArea textArea;
    private Thread[] threads;
    private int index = 0;

    public AbstractProcess(File targetFolder, HashSet<FileElement> files) {
        this.targetFolder = targetFolder;
        this.files = files;
    }

    protected void addMessage(String message) {
        System.out.println(message);
        textArea.append(message + "\n");

    }

    public void setTextArea(JTextArea textArea) {
        this.textArea = textArea;
    }

    @Override
    public void run() {
//        создаем массив потоков по числу файлов и вложенных каталогов, используются только по числу каталогов
        doProcessOrCreateNewBehaviors();
        //Ожидаем завершение каждого запущенного потока
        joinBehaviorsThreads();
        doPostprocess();
    }

    private void doProcessOrCreateNewBehaviors() {
        File[] listFiles = targetFolder.listFiles();
        threads = new Thread[listFiles.length];
        for (File behaviorFile : listFiles) {
            if (behaviorFile.isDirectory()) {
                threads[index] = createNewProcess(behaviorFile);
                threads[index++].start();
            } else {
                doProcess(behaviorFile);
            }

        }
    }

    private void joinBehaviorsThreads() {
        for (int i = 0; i < index; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    protected void killEmptyCat() {
        //Удаляем каталог, если он оказался пуст
        if (targetFolder.listFiles().length == 0) {
            if (targetFolder.exists()) {
                if (targetFolder.delete())
                    addMessage("Папка " + targetFolder.getAbsolutePath() + " была пустая, удалена");
                else addMessage("Папка " + targetFolder.getAbsolutePath() + " пустая, но ее удалить не получилось");
            }
        }
    }

    protected abstract void doPostprocess();

    protected abstract void doProcess(File file1);

    protected abstract Thread createNewProcess(File file1);


}
