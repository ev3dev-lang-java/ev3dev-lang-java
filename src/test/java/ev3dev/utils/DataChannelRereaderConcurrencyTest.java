package ev3dev.utils;

import java.io.File;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.Assert.assertTrue;

@Slf4j
public class DataChannelRereaderConcurrencyTest {

    final String fileName = "./pairs.txt";
    final String fileName2 = "./odds.txt";
    final Integer limit = 100;

    @Before
    @SneakyThrows
    public void createFiles() {
        new File(fileName).createNewFile();
        new File(fileName2).createNewFile();
    }

    /**
     * Writer1 -> pairs.txt
     * Reader1 <- pairs.txt
     * Reader2 <- pairs.txt
     *
     * Writer1 -> odds.txt
     * Reader1 <- odds.txt
     * Reader2 <- odds.txt
     */
    @Test
    public void given_multiple_DataChannelRereader_when_execute_concurrently_then_Ok() {

        CompletableFuture<String> request1 = asyncWriteFile(true);
        CompletableFuture<String> request2 = asyncWriteFile(false);
        CompletableFuture<String> request3 = asyncReadFile(true);
        CompletableFuture<String> request4 = asyncReadFile(false);
        CompletableFuture<String> request5 = asyncReadFile2(true);
        CompletableFuture<String> request6 = asyncReadFile2(false);

        CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(
            request1,
            request2,
            request3,
            request4,
            request5,
            request6);

        combinedFuture.join();

        assertTrue(request1.isDone());
        assertTrue(request2.isDone());
        assertTrue(request3.isDone());
        assertTrue(request4.isDone());
        assertTrue(request5.isDone());
        assertTrue(request6.isDone());

        System.out.println(request1.join());
        System.out.println(request2.join());
        System.out.println(request3.join());
        System.out.println(request4.join());
        System.out.println(request5.join());
        System.out.println(request6.join());

        //System.out.println(count);

        System.out.println("End");
    }

    private void readFile(String file, Boolean flag) {
        Integer value = Sysfs.readInteger(file);
        if (flag) {
            then(value % 2 == 0).isTrue();
        } else {
            then(value % 2 != 0).isTrue();
        }
    }

    private void readFile2(String file, Boolean flag) {
        DataChannelRereader dataChannelRereader = new DataChannelRereader(file);
        Integer value = Integer.parseInt(dataChannelRereader.readString());
        if (flag) {
            then(value % 2 == 0).isTrue();
        } else {
            then(value % 2 != 0).isTrue();
        }
    }

    private CompletableFuture<String> asyncReadFile(boolean flag) {
        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> {
            IntStream
                .rangeClosed(1, limit)
                .forEach(i -> {
                    if (flag) {
                        readFile(fileName, flag);
                    } else {
                        readFile(fileName2, flag);
                    }
                });
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

    private CompletableFuture<String> asyncReadFile2(boolean flag) {
        CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> {
            IntStream
                .rangeClosed(1, limit)
                .forEach(i -> {
                    if (flag) {
                        readFile2(fileName, flag);
                    } else {
                        readFile2(fileName2, flag);
                    }
                });
            return "Ok asyncReadFile2";
        })
        .handle((input, exception) -> {
            if (exception != null) {
                LOGGER.warn(exception.getLocalizedMessage(), exception);
                return "Ko asyncReadFile2";
            }
            return input;
        });
        return cf;
    }

    private void writeFile(String file, String value) {
        Sysfs.writeString(file, value);
    }

    private CompletableFuture<String> asyncWriteFile(boolean flag) {
        CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> {
            IntStream
                .rangeClosed(1, limit)
                .filter(i -> {
                    if (flag) {
                        return i % 2 == 0;
                    } else {
                        return i % 2 != 0;
                    }
                })
                .forEach(i -> {
                    if (flag) {
                        writeFile(fileName, String.valueOf(i));
                    } else {
                        writeFile(fileName2, String.valueOf(i));
                    }
                });
            return "Ok asyncWriteFile";
        })
        .handle((input, exception) -> {
            if (exception != null) {
                LOGGER.warn(exception.getLocalizedMessage(), exception);
                return "Ko asyncWriteFile";
            }
            return input;
        });

        return cf;
    }

    /**
     * Writer1 -> pairs.txt
     * Reader1 <- pairs.txt
     *
     * Writer1 -> odds.txt
     * Reader1 <- odds.txt
     */
    @Test
    public void given_multiple_DataChannelRereader_when_execute_concurrently_then_Ok2() {

        CompletableFuture<String> request1 = asyncWriteFile(true);
        CompletableFuture<String> request2 = asyncWriteFile(false);
        CompletableFuture<String> request3 = asyncReadFile(true);
        CompletableFuture<String> request4 = asyncReadFile(false);

        CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(
            request1,
            request2,
            request3,
            request4);

        combinedFuture.join();

        assertTrue(request1.isDone());
        assertTrue(request2.isDone());
        assertTrue(request3.isDone());
        assertTrue(request4.isDone());

        System.out.println(request1.join());
        System.out.println(request2.join());
        System.out.println(request3.join());
        System.out.println(request4.join());
        //System.out.println(count);

        System.out.println("End");
    }
}
