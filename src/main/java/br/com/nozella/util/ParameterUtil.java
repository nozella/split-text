package br.com.nozella.util;

import br.com.nozella.exception.SplitTextException;
import br.com.nozella.bo.ArgumentExecution;

public class ParameterUtil {
    public ArgumentExecution buildParameterExecution(String... args) throws SplitTextException {
        return new ArgumentExecution();
    }

    public void validateArgumentExecution(final ArgumentExecution argumentExecution) throws SplitTextException {

    }
}
