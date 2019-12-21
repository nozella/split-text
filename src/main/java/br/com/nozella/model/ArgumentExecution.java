package br.com.nozella.model;

import java.io.File;
import java.nio.file.Path;

public class ArgumentExecution {
    public final int pageSize = 1000;
    private File theFile;
    private String fileNamePattern;
    private long fileSize;
    private long counter = 1;

    public ArgumentExecution(final File argumentFile, long fileSize) {
        this.fileSize = fileSize;
        final String fileNamePattern;
        if (argumentFile.getName().contains("%s")) {
            fileNamePattern = argumentFile.getName();
        } else {
            fileNamePattern = argumentFile.getName() + "%s";
        }
        this.fileNamePattern = fileNamePattern;
        this.theFile = new File(argumentFile.getParent(), argumentFile.getName().replaceAll("%s", ""));
    }

    public Path getFilePath() {
        return this.theFile.toPath();
    }

    public long getFileSize() {
        return this.fileSize;
    }

    public Path getActualFilePath() {
        final String counter = String.format("_%s_", this.counter);
        final String fileName = String.format(this.fileNamePattern, counter);
        return new File(this.theFile.getParent(), fileName).toPath();
    }

    public void increment() {
        this.counter++;
    }
}
