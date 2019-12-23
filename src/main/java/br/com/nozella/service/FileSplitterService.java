package br.com.nozella.service;

import br.com.nozella.exception.SplitTextException;
import br.com.nozella.model.ArgumentExecution;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

class FileSplitterService {
    void split(final ArgumentExecution argumentExecution) throws SplitTextException {
        final List<String> lineList = new ArrayList<>();
        try (final BufferedReader bufferedReader = Files.newBufferedReader(argumentExecution.getFilePath())) {
            String line;
            long pageRow = 0L, fileSize = 0L;
            while ((line = bufferedReader.readLine()) != null) {
                pageRow++;
                fileSize++;
                lineList.add(line);
                if (fileSize >= argumentExecution.getFileSize()) {
                    pageRow = 0L;
                    fileSize = 0L;
                    this.writeEndFile(lineList, argumentExecution);
                    System.out.println(String.format("Arquivo criado: %s", argumentExecution.getActualFilePath()));
                    argumentExecution.increment();
                    lineList.clear();
                } else if (pageRow >= argumentExecution.pageSize) {
                    pageRow = 0L;
                    this.writeFile(lineList, argumentExecution);
                    lineList.clear();
                }
            }
            this.writeEndFile(lineList, argumentExecution);
            System.out.println(String.format("Arquivo criado: %s", argumentExecution.getActualFilePath()));
        } catch (final IOException e) {
            throw new SplitTextException(10, "Error on read file", e);
        }

    }

    private void writeFile(final List<String> rowList, final ArgumentExecution argumentExecution) throws SplitTextException {
        try {
            Files.write(argumentExecution.getActualFilePath(), rowList, StandardOpenOption.APPEND, StandardOpenOption.CREATE);
        } catch (final IOException e) {
            throw new SplitTextException(20, "Error on write file", e);
        }
    }

    private void writeEndFile(final List<String> rowList, final ArgumentExecution argumentExecution) throws SplitTextException {
        try {
            final String last = rowList.remove(rowList.size() - 1);
            Files.write(argumentExecution.getActualFilePath(), rowList, StandardOpenOption.APPEND, StandardOpenOption.CREATE);
            Files.write(argumentExecution.getActualFilePath(), last.getBytes(), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
        } catch (final IOException e) {
            throw new SplitTextException(20, "Error on write file", e);
        }
    }
}
