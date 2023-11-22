package main;

import checker.Checker;
import checker.CheckerConstants;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;

import commands.Command;

import entities.MainPlayer;
import entities.UserPlayer;
import fileio.input.LibraryInput;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;


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
     *
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
     * @param filePathInput  for input file
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
            commands = mapper.readValue(readJSONFile, new TypeReference<List<Command>>() {
            });
            // Process the commands as needed
        } catch (Exception e) {
            e.printStackTrace();
        }

        assert commands != null;

        // copy the library from the input library to a new object (a clone library)
//		Library library = Library.initializeLibrary(libraryInput);

        MainPlayer player = new MainPlayer(libraryInput);
//5730 timestamp error david27
        // found bug with playPause. after rewriting the time logic in commit 9
        // the play/pause command doesn't stop the time anymore
        // lodedTimestamp + currentAduioDuration - pausedTime < currentTime
        for (Command command : commands) {
            // if the command has a user, so is diferent from getTop5 commands
            if (command.getUsername() != null) {
                UserPlayer userPlayer = player.getLibrary().getUserWithUsername(command.getUsername()).getPlayer();

                if (command.getTimestamp() == 5750)
                    System.out.println("!!!!");

                userPlayer.updateTime(command.getTimestamp());
            }

            command.execute(outputs, player);
        }


        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File(filePathOutput), outputs);
    }
}


