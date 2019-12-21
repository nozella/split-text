package br.com.nozella;

import br.com.nozella.service.SplitTextService;

import java.util.Scanner;

public class App implements Runnable {

    public static void main(final String... args) {
        new App(args).run();
//        System.out.println("type something");
//        System.out.println(scanKeyboard());
//        System.out.println("type another something");
//        System.out.println(scanKeyboard());
    }

    private static String scanKeyboard() {
        return new Scanner(System.in).next();
    }

    private final SplitTextService splitTextService;
    private final String[] args;

    private App(final String... args){
        this.args = args;
        this.splitTextService = new SplitTextService();

    }

    @Override
    public void run() {
        System.exit(this.splitTextService.execute(this.args));
    }
}
