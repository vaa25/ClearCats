package com.vaa25.clearcats;

import java.io.File;
import java.util.HashSet;

/**
 * Created with IntelliJ IDEA.
 * User: Vlasov Alexander
 * Date: 27.07.13
 * Time: 19:07
 * To change this template use File | Settings | File Templates.
 */
public class VerifyProcess extends AbstractProcess {


    public VerifyProcess(File file, HashSet<FileElement> files) {
        super(file, files);
    }


    @Override
    protected void doPostprocess() {
    }

    @Override
    protected void doProcess(File file1) {
        FileElement temp = new FileElement(file1);
        //Сравниваем файл с каждым из списка
        for (FileElement elem : files) {
            //Если файл уже в списке, то после проверки MD5 удаляем его с очищаемого каталога
            if (elem.equals(temp)) {
                if (new MD5().count(elem.getAbsolutePath()).equals(new MD5().count(file1.getAbsolutePath()))) {
                    if (elem.exists()) {
                        if (elem.delete()) addMessage("Файл " + elem.getAbsolutePath() + " имел копию, удален");
                        else
                            addMessage("Файл " + elem.getAbsolutePath() + " имеет копию, но его удалить не получилось");
                    }
                }
            }
        }
    }

    @Override
    protected Thread createNewProcess(File file1) {
        VerifyProcess verifyProcess = new VerifyProcess(file1, files);
        verifyProcess.setTextArea(textArea);
        return verifyProcess;
    }

}
