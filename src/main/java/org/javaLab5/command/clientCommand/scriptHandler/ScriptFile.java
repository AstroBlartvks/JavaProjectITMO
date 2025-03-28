package org.javaLab5.command.clientCommand.scriptHandler;

import lombok.Getter;

import java.util.Scanner;


@Getter
public class ScriptFile {

    private final Scanner fileScanner;
    private final String scriptName;

    public ScriptFile(String scriptName, Scanner fileScanner){
        this.scriptName = scriptName;
        this.fileScanner = fileScanner;
    }
}
