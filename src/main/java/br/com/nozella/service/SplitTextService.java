package br.com.nozella.service;

import br.com.nozella.exception.SplitTextException;
import br.com.nozella.bo.ArgumentExecution;
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
            this.parameterUtil.validateArgumentExecution(argumentExecution);
            this.fileSplitterService.split(argumentExecution);
            return 0;
        } catch (final SplitTextException e) {
            e.printStackTrace();
            return e.getCode();
        } catch (final Throwable t) {
            t.printStackTrace();
            return 1;
        }
    }

}
