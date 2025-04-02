package org.javaLab5.inputManager;

import lombok.Getter;

import java.util.Scanner;

@Getter
public class SmartScanner {
    private final Scanner scanner;
    private final SmartScannerType type;
    private final String name;
    private boolean closed = false;

    public SmartScanner(Scanner scanner, SmartScannerType type, String name) {
        this.scanner = scanner;
        this.type = type;
        this.name = name;
    }

    public void close(){
        this.scanner.close();
        this.closed = true;
    }

    public String nextLine(){
        String line = this.scanner.nextLine();

        if (line.isEmpty()){
            return "";
        }

        if (SmartScannerType.CONSOLE != this.type){
            System.out.println(line.trim());
        }

        return line.trim();
    }

    public boolean hasNextLine(){
        return this.scanner.hasNextLine();
    }

    @Override
    public String toString(){
        return "SmartScanner{\ntype="+type+",\nname="+name+",\nclosed="+closed;
    }
}
