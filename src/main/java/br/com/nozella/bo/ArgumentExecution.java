package br.com.nozella.bo;

public class ArgumentExecution {
    private String filePath;
    private int fileSize;
    private int pageSize;

    public String getFilePath() {
        return this.filePath;
    }

    public int getFileSize() {
        return this.fileSize;
    }

    public int getPageSize() {
        return pageSize;
    }

    public String getActualFilePath() {
        return null;
    }

    public ArgumentExecution increment() {
        return this;
    }
}
