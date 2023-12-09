package org.aoc2023;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class Day5 {
    private static String INPUT_FILE = "./input.txt";

    private String input;

    public Day5() throws IOException {
        this.input = readInput();
    }

    private static String readInput() throws IOException {
        Charset encoding = StandardCharsets.UTF_8;
        Path path = Path.of(INPUT_FILE);
        return Files.readString(path, encoding);
    }
}