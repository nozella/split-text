package br.com.nozella.util;

import br.com.nozella.exception.SplitTextException;
import br.com.nozella.model.ArgumentExecution;

import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

public class ParameterUtil {
    public ArgumentExecution buildParameterExecution(final String... args) throws SplitTextException {
        if (args.length == 0) {
            return this.askForParameters();
        } else if (args.length != 2) {
            throw new SplitTextException(2, String.format("Parametros de entrada invalidos, verifique: %s", Arrays.asList(args)));
        }
        final File file = new File(args[0]);
        if (!this.isValidFile(new File(file.getParent(), file.getName().replaceAll("%s", "")))){
            throw new SplitTextException(2, String.format("Parametros de entrada invalidos, verifique: %s", Arrays.asList(args)));
        }
        String maxString = args[1];
        if (!this.isValidNumber(maxString)) {
            throw new SplitTextException(2, String.format("Parametros de entrada invalidos, verifique: %s", Arrays.asList(args)));
        }
        return new ArgumentExecution(file, Long.valueOf(maxString));
    }

    private ArgumentExecution askForParameters() throws SplitTextException {
        final File file = this.getFilePath();
        final long maxSize = this.getMaxSize();
        return new ArgumentExecution(file, maxSize);
    }

    private long getMaxSize() throws SplitTextException {
        System.out.println("Qual é o numero maximo de linhas por arquivo?\nDigite 'sair' para cancelar");
        final String maxString = this.scanForExit();
        if (this.isValidNumber(maxString)) {
            return Long.valueOf(maxString);
        }
        System.out.println(String.format("O numero maximo de linhas informado nao esta valido\nverifique o valor informado: %s", maxString));
        return this.getMaxSize();
    }

    private String scanForExit() throws SplitTextException {
        final String string = this.scanKeyboard();
        if (string == null || string.equalsIgnoreCase("sair")) {
            throw new SplitTextException(0, "O usuario escolheu sair");
        }
        return string;
    }

    private File getFilePath() throws SplitTextException {
        System.out.println("Qual é o caminho completo para o arquivo?\nDigite 'sair' para cancelar");
        final String filePath = this.scanForExit();
        final File file = new File(filePath);
        if (!file.getName().contains("%s")) {
            System.out.println("O nome do arquivo informado nao possui marcacao para onde colocar o contador\no contador sera inserido no final do nome original do arquivo. Ex:");
            System.out.println(String.format("%s.123", filePath));
            System.out.println("Se deseja adicionar o contator em uma parte especifica, adicione a marcacao '%s' em alguma parte do nome do arquivo, Ex:");
            System.out.println("/tmp/myFile%s.txt");
            System.out.println("Se deseja corrigir isso digite SIM, caso contrario digite qualquer outra coisa e tecle ENTER");
            final String answer = this.scanKeyboard();
            if (answer != null && answer.equalsIgnoreCase("SIM")) {
                return this.getFilePath();
            }
        }
        if (this.isValidFile(new File(file.getParent(), file.getName().replaceAll("%s", "")))){
            return file;
        }
        return this.getFilePath();
    }


    private boolean isValidNumber(final String maxString) {
        return maxString != null
                && !maxString.trim().isEmpty()
                && maxString.replaceAll("[0-9]", "").isEmpty();
    }

    private boolean isValidFile(final File file) {
        if (!file.isFile()){
            System.out.println(String.format("O caminho para o arquivo nao aponta para um arquivo valido\nverifique o caminho informado: %s", file.getPath()));
            return false;
        }
        if (!file.canRead()) {
            System.out.println(String.format("O arquivo informado nao pode ser lido\nverifique as permissoes de acesso ao arquivo informado: %s", file.getPath()));
            return false;
        }
        final File directory = new File(file.getParent());
        if (!directory.canWrite()) {
            System.out.println(String.format("Nao tenho permissao para escrever no diretorio do arquivo\nverifique as permissoes do diretorio informado: %s", directory.getPath()));
            return false;
        }
        return true;
    }

    private String scanKeyboard() {
        return new Scanner(System.in).next();
    }
}
