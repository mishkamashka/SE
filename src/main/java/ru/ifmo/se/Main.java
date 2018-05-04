package ru.ifmo.se;

public class Main {
    public static void main(String[] args) {
        App app = new App();
        Runtime.getRuntime().addShutdownHook(new Thread(app::save));
        app.start();
        //I need commits all of which compile and run tests
        // And more words
        // Actually I'm changing Main
    }
}
