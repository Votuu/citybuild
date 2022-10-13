package net.xcyan.citybuild.api.scanner;

import java.util.UUID;

public abstract class CitybuildScanner {

    private UUID scannerId;
    private String name;

    private String message;
    private int inputTime;

    private String result;

    public CitybuildScanner(String name, String message, int inputTime) {
        this.scannerId = UUID.randomUUID();
        this.name = name;

        this.message = message;
        this.inputTime = inputTime;
    }

    public CitybuildScanner(String name, String message) {
        this(name, message, 30);
    }

    public String message() {
        return message;
    }
    public int inputTime() {
        return inputTime;
    }

    public String result() {
        return result;
    }
    public void result(String result) {
        this.result = result;
    }

    public abstract void cancel();
    public abstract void ready();
}
