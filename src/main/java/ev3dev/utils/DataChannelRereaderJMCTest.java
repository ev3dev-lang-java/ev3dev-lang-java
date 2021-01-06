package ev3dev.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

@Slf4j
public class DataChannelRereaderJMCTest {

    final static String fileName = "./race.txt";
    final static Integer limit = 200000;

    public static void createFiles() throws IOException {
        Files.writeString(Path.of(fileName), "test1234");
    }

    /**
     * Reader1 <- race.txt
     * Reader2 <- race.txt
     * Reader3 <- race.txt
     * Reader4 <- race.txt
     */
    public static void main(String[] args) throws Exception{

        createFiles();

        DataChannelRereader reader = new DataChannelRereader(fileName);

        CompletableFuture<String> rq1 = asyncReadFile(reader);
        CompletableFuture<String> rq2 = asyncReadFile(reader);
        CompletableFuture<String> rq3 = asyncReadFile(reader);
        CompletableFuture<String> rq4 = asyncReadFile(reader);

        CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(rq1, rq2, rq3, rq4);
        combinedFuture.join();

        /*
        then(rq1.isDone()).isTrue();
        then(rq2.isDone()).isTrue();
        then(rq3.isDone()).isTrue();
        then(rq4.isDone()).isTrue();

        then(rq1.join()).isEqualTo("Ok asyncReadFile");
        then(rq2.join()).isEqualTo("Ok asyncReadFile");
        then(rq3.join()).isEqualTo("Ok asyncReadFile");
        then(rq4.join()).isEqualTo("Ok asyncReadFile");

        System.out.println("End");
         */
    }

    private static void readFile(DataChannelRereader reader) {
        reader.readString();
        //then(reader.readString()).isEqualTo("test1234");
    }

    private static CompletableFuture<String> asyncReadFile(DataChannelRereader reader) {
        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> {
            IntStream
                .rangeClosed(1, limit)
                .forEach(i -> readFile(reader));
            return "Ok asyncReadFile";
        })
        .handle((input, exception) -> {
            if (exception != null) {
                LOGGER.warn(exception.getLocalizedMessage(), exception);
                return "Ko asyncReadFile";
            }
            return input;
        });
        return cf1;
    }
}
