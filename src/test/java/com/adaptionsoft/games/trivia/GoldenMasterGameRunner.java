package com.adaptionsoft.games.trivia;

import com.adaptionsoft.games.uglytrivia.Game;

import java.io.File;
import java.io.PrintStream;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Random;


public class GoldenMasterGameRunner {

    private static boolean notAWinner;

    public static void main(String[] args) throws Exception {
        final FileSystem fileSystem = FileSystems.getDefault();
        final Path goldenMasterRoot = fileSystem.getPath("src", "test", "data", "golden-master");
        final Path goldenMasterRunRoot = fileSystem.getPath("src", "test", "data", "golden-master-run");
        if (!fileSystem.isOpen())
            throw new RuntimeException("The golden master file system is not open.");

        final File goldenMasterRootAsFile = ensureDirectoryExists(goldenMasterRoot);
        final File goldenMasterRunRootAsFile = ensureDirectoryExists(goldenMasterRunRoot);

        final long gameSeed = 812736L;

        final String gameOutputFileName = String.format("game-%d.txt", gameSeed);
        final File goldenMasterFile = new File(goldenMasterRootAsFile, gameOutputFileName);

        final File gameOutputFile;
        if (goldenMasterFile.exists()) {
            gameOutputFile = new File(goldenMasterRunRootAsFile, gameOutputFileName);
        } else {
            gameOutputFile = goldenMasterFile;
        }

        System.setOut(new PrintStream(gameOutputFile));

        Game aGame = new Game();

        aGame.add("Chet");
        aGame.add("Pat");
        aGame.add("Sue");

        Random rand = new Random(gameSeed);

        do {

            aGame.roll(rand.nextInt(5) + 1);

            if (rand.nextInt(9) == 7) {
                notAWinner = aGame.wrongAnswer();
            } else {
                notAWinner = aGame.wasCorrectlyAnswered();
            }


        } while (notAWinner);

    }

    // REFACTOR Move to generate File library. Doesn't Java I/O have this?!
    private static File ensureDirectoryExists(final Path pathToDirectory) {
        final File directoryThatMustExist = pathToDirectory.toFile();
        directoryThatMustExist.mkdirs();
        if (!directoryThatMustExist.exists())
            throw new RuntimeException(String.format("I couldn't create the directory at path [%s]", pathToDirectory));

        return directoryThatMustExist;
    }
}
