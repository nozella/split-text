package br.com.nozella;

import br.com.nozella.service.SplitTextService;

public class App implements Runnable {

    public static void main(final String... args) {
        new App(args).run();
    }

    private final SplitTextService splitTextService;
    private final String[] args;

    private App(final String... args){
        this.args = args;
        this.splitTextService = new SplitTextService();

    }

    @Override
    public void run() {
        int code = this.splitTextService.execute(this.args);
        if (code != 0) {
            System.exit(code);
        }
    }
}
