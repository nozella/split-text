package br.com.nozella.service;

import br.com.nozella.exception.SplitTextException;
import br.com.nozella.model.ArgumentExecution;
import br.com.nozella.util.ParameterUtil;

public class SplitTextService {

    private final ParameterUtil parameterUtil;
    private final FileSplitterService fileSplitterService;

    public SplitTextService() {
        this.parameterUtil = new ParameterUtil();
        this.fileSplitterService = new FileSplitterService();
    }

    public int execute(final String... args) {
        try {
            final ArgumentExecution argumentExecution = this.parameterUtil.buildParameterExecution(args);
            this.fileSplitterService.split(argumentExecution);
            return 0;
        } catch (final SplitTextException e) {
            int exitCode = e.getCode();
            if (exitCode == 0) {
                System.out.println(e.getMessage());
            } else {
                e.printStackTrace();
            }
            return exitCode;
        } catch (final Throwable t) {
            t.printStackTrace();
            return 1;
        }
    }

}
