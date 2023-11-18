package main;

import checker.Checker;
import checker.CheckerConstants;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;

import commands.searchbar.*;
import commands.player.*;
import commands.stats.*;
import commands.playlist.*;
import commands.Command;

import entities.Library;
import fileio.input.LibraryInput;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;



/**
 * The entry point to this homework. It runs the checker that tests your implentation.
 */
public final class Main {
    static final String LIBRARY_PATH = CheckerConstants.TESTS_PATH + "library/library.json";

    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * DO NOT MODIFY MAIN METHOD
     * Call the checker
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        File directory = new File(CheckerConstants.TESTS_PATH);
        Path path = Paths.get(CheckerConstants.RESULT_PATH);

        if (Files.exists(path)) {
            File resultFile = new File(String.valueOf(path));
            for (File file : Objects.requireNonNull(resultFile.listFiles())) {
                file.delete();
            }
            resultFile.delete();
        }
        Files.createDirectories(path);

        for (File file : Objects.requireNonNull(directory.listFiles())) {
            if (file.getName().startsWith("library")) {
                continue;
            }

            String filepath = CheckerConstants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getName(), filepath);
            }
        }

        Checker.calculateScore();
    }

    /**
     * @param filePathInput for input file
     * @param filePathOutput for output file
     * @throws IOException in case of exceptions to reading / writing
     */


    public static void action(final String filePathInput,
                              final String filePathOutput) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        LibraryInput libraryInput = objectMapper.readValue(new File(LIBRARY_PATH), LibraryInput.class);

        ArrayNode outputs = objectMapper.createArrayNode();

        // TODO add your implementation

        ObjectMapper mapper = new ObjectMapper();
        File readJSONFile = new File(CheckerConstants.TESTS_PATH + filePathInput);
        List<Command> commands = null;
        try {
            commands = mapper.readValue(readJSONFile, new TypeReference<List<Command>>() {});
            // Process the commands as needed
        } catch (Exception e) {
            e.printStackTrace();
        }

//		assert commands != null;
//		for (Command command : commands) {
////            System.out.println("Command Type: " + command.getCommand());
//            System.out.println("Timestamp: " + command.getTimestamp());
//
//            // Since Command is an abstract class, you need to check the actual type of each command
//            // and cast it to the appropriate subclass to access specific fields
//            if (command instanceof Search) {
//                Search searchCommand = (Search) command;
//                System.out.println("Username: " + searchCommand.getUsername());
//                System.out.println("Search Type: " + searchCommand.getType());
//                System.out.println("Filters: " + searchCommand.getFilters());
//            }
//        }

        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File(filePathOutput), outputs);
    }
}


