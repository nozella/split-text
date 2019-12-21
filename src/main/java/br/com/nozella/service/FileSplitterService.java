package br.com.nozella.service;

import br.com.nozella.exception.SplitTextException;
import br.com.nozella.bo.ArgumentExecution;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

class FileSplitterService {
    void split(final ArgumentExecution argumentExecution) throws SplitTextException {
        final List<String> lineList = new ArrayList<>();
        try (final BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(argumentExecution.getFilePath()))) {
            String line;
            int pageRow = 0, fileSize = 0;
            while ((line = bufferedReader.readLine()) != null) {
                pageRow++;
                fileSize++;
                lineList.add(line);
                if (fileSize >= argumentExecution.getFileSize()) {
                    pageRow = 0;
                    fileSize = 0;
                    this.writeFile(lineList, argumentExecution.increment());
                    lineList.clear();
                } else if (pageRow >= argumentExecution.getPageSize()) {
                    pageRow = 0;
                    this.writeFile(lineList, argumentExecution);
                    lineList.clear();
                }
            }

        } catch (final IOException e) {
            throw new SplitTextException(-10, "Error on read file", e);
//            System.err.format("IOException: %s%n", e);
        }

    }

    private void writeFile(final List<String> rowList, final ArgumentExecution argumentExecution) throws SplitTextException {
        try {
            Files.write(Paths.get(argumentExecution.getActualFilePath()), rowList);
        } catch (final IOException e) {
            throw new SplitTextException(-20, "Error on write file", e);
        }

    }
}
