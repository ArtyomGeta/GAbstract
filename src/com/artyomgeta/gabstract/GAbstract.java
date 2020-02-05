package com.artyomgeta.gabstract;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Objects;

public abstract class GAbstract {

    public static void main(String... args) {
        new Viewer("Test").run();
    }

    public static int returnSlidesLength(String abstractName) {
        return Objects.requireNonNull(new File("Abstracts/" + abstractName + "/slides/").listFiles()).length;
    }


    @NotNull
    public static String[] returnSlides(String abstractName) {
        String[] returnable = new String[returnSlidesLength(abstractName)];
        for (int i = 0; i < returnSlidesLength(abstractName); i++) {
            returnable[i] = Objects.requireNonNull(new File("Abstracts/" + abstractName + "/slides/").listFiles())[i].getName();
        }
        return returnable;
    }
}
