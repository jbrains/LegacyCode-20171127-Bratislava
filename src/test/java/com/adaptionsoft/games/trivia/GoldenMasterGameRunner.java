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
        if (!fileSystem.isOpen())
            throw new RuntimeException("The golden master file system is not open.");

        final File goldenMasterRootAsFile = goldenMasterRoot.toFile();
        goldenMasterRootAsFile.mkdirs();
        if (!goldenMasterRootAsFile.exists())
            throw new RuntimeException(String.format("I couldn't create the golden master directory at path [%s]", goldenMasterRoot));

        System.setOut(new PrintStream(new File(goldenMasterRootAsFile, String.format("game-%d.txt", 812736L))));

        Game aGame = new Game();

        aGame.add("Chet");
        aGame.add("Pat");
        aGame.add("Sue");

        Random rand = new Random(812736L);

        do {

            aGame.roll(rand.nextInt(5) + 1);

            if (rand.nextInt(9) == 7) {
                notAWinner = aGame.wrongAnswer();
            } else {
                notAWinner = aGame.wasCorrectlyAnswered();
            }


        } while (notAWinner);

    }
}
