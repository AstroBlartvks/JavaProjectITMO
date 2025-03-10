package org.AstrosLab.inputManager;

import lombok.Setter;
import lombok.Getter;

import java.util.Scanner;

public class ScannerManager {
    @Getter
    @Setter
    private static Scanner scanner = new Scanner(System.in);
}
