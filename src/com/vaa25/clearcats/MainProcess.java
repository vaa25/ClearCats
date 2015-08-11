package com.vaa25.clearcats;

import java.io.File;
import java.util.HashSet;

/**
 * Объект смотрит в каталог и порождает на каждый содержащийся в нем каталог объект этого же
 * класса в новом потоке, а каждый пустой файл, если надо, удаляет.
 * После завершения работы всех порожденных потоков объект смотрит, не пустой ли стал этот
 * каталог, и если пустой, то удаляет его.
 * <p/>
 * Date: 21.07.13
 * Time: 7:36
 *
 * @author Alexander Vlasov
 */
public class MainProcess extends AbstractProcess {
    private boolean toDeleteEmptyFiles;
    private boolean toDeleteCopiedFiles;
    private boolean toDeleteEmptyFolders;

    public MainProcess(File file,
                       boolean toDeleteEmptyFiles,
                       boolean toDeleteCopiedFiles,
                       boolean toDeleteEmptyFolders,
                       HashSet<FileElement> files) {
        super(file, files);
        this.toDeleteEmptyFiles = toDeleteEmptyFiles;
        this.toDeleteEmptyFolders = toDeleteEmptyFolders;
        this.toDeleteCopiedFiles = toDeleteCopiedFiles;
    }

    @Override
    protected void doPostprocess() {
        if (toDeleteEmptyFolders) killEmptyCat();
    }

    @Override
    protected void doProcess(File file1) {
        if (toDeleteEmptyFiles && file1.length() == 0)
            if (file1.exists()) {
                if (file1.delete()) addMessage("Файл " + file1.getAbsolutePath() + " был пустой, удален");
                else addMessage("Файл " + file1.getAbsolutePath() + " пустой, но его удалить не получилось");
            } else if (toDeleteCopiedFiles) {
                //Закидываем файл в список
                synchronized (files) {
                    files.add(new FileElement(file1));
                }
            }
    }

    @Override
    protected Thread createNewProcess(File file1) {
        MainProcess mainProcess = new MainProcess(file1, toDeleteEmptyFiles, toDeleteCopiedFiles, toDeleteEmptyFolders, files);
        mainProcess.setTextArea(textArea);
        return mainProcess;
    }
}
