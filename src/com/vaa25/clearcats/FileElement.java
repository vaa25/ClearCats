package com.vaa25.clearcats;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: Vlasov Alexander
 * Date: 27.07.13
 * Time: 13:47
 * To change this template use File | Settings | File Templates.
 */
public class FileElement implements Comparable<FileElement> {
    private String absolutePath;
    private String name;
    private long length;
    private File file;

    public FileElement(File file1) {
        absolutePath = file1.getAbsolutePath();
        name = file1.getName();
        length = file1.length();
        file = file1;
    }

    public boolean exists() {
        return file.exists();
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public boolean delete() {
        return file.delete();
    }

    @Override
    public int compareTo(FileElement o) {
        if (o.equals(this))
            return 0;
        if (o.length <= length) return -1;
        return 1;
    }

    @Override
    public String toString() {
        return name + " " + length;
    }

    @Override
    public boolean equals(Object o) {
        return o.toString().equals(toString());
    }
}
