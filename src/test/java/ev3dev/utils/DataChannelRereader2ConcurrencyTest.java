package ev3dev.utils;

import java.io.File;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.BDDAssertions.then;

@Slf4j
public class DataChannelRereader2ConcurrencyTest {

    final String fileName = "./pairs.txt";
    final String fileName2 = "./odds.txt";
    final Integer limit = 1000;

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

        then(request1.isDone()).isTrue();
        then(request2.isDone()).isTrue();
        then(request3.isDone()).isTrue();
        then(request4.isDone()).isTrue();
        then(request5.isDone()).isTrue();
        then(request6.isDone()).isTrue();

        then(request1.join()).isEqualTo("Ok asyncWriteFile");
        then(request2.join()).isEqualTo("Ok asyncWriteFile");
        then(request3.join()).isEqualTo("Ok asyncReadFile");
        then(request4.join()).isEqualTo("Ok asyncReadFile");
        then(request5.join()).isEqualTo("Ok asyncReadFile2");
        then(request6.join()).isEqualTo("Ok asyncReadFile2");

        System.out.println("End");
    }

    private void readFile(String file, Boolean flag) {
        Integer value = Sysfs2.readInteger(file);
        if (flag) {
            then(value % 2 == 0).isTrue();
        } else {
            then(value % 2 != 0).isTrue();
        }
    }

    private void readFile2(String file, Boolean flag) {
        DataChannelRereader2 dataChannelRereader = new DataChannelRereader2(file);
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
        Sysfs2.writeString(file, value);
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

        then(request1.isDone()).isTrue();
        then(request2.isDone()).isTrue();
        then(request3.isDone()).isTrue();
        then(request4.isDone()).isTrue();

        then(request1.join()).isEqualTo("Ok asyncWriteFile");
        then(request2.join()).isEqualTo("Ok asyncWriteFile");
        then(request3.join()).isEqualTo("Ok asyncReadFile");
        then(request4.join()).isEqualTo("Ok asyncReadFile");

        System.out.println("End");
    }

    /**
     * Reader1 <- pairs.txt
     * Reader1 <- odds.txt
     */
    @Test
    public void given_multiple_DataChannelRereader_when_execute_concurrently_then_Ok3() {

        writeFile(fileName, "2");
        writeFile(fileName2, "1");

        CompletableFuture<String> request1 = asyncReadFile(true);
        CompletableFuture<String> request2 = asyncReadFile(false);

        CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(
            request1,
            request2);

        combinedFuture.join();

        then(request1.isDone()).isTrue();
        then(request2.isDone()).isTrue();

        then(request1.join()).isEqualTo("Ok asyncReadFile");
        then(request2.join()).isEqualTo("Ok asyncReadFile");

        System.out.println("End");
    }

    /**
     * Writer1 -> pairs.txt Once
     * Writer1 -> odds.txt Once
     * Reader1 <- pairs.txt
     * Reader1 <- odds.txt
     * Reader2 <- pairs.txt
     * Reader2 <- odds.txt
     */
    @Test
    public void given_multiple_DataChannelRereader_when_execute_concurrently_then_Ok4() {

        writeFile(fileName, "2");
        writeFile(fileName2, "1");

        CompletableFuture<String> request1 = asyncReadFile(true);
        CompletableFuture<String> request2 = asyncReadFile(false);
        CompletableFuture<String> request3 = asyncReadFile2(true);
        CompletableFuture<String> request4 = asyncReadFile2(false);

        CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(
            request1,
            request2,
            request3,
            request4);

        combinedFuture.join();

        then(request1.isDone()).isTrue();
        then(request2.isDone()).isTrue();
        then(request3.isDone()).isTrue();
        then(request4.isDone()).isTrue();

        then(request1.join()).isEqualTo("Ok asyncReadFile");
        then(request2.join()).isEqualTo("Ok asyncReadFile");
        then(request3.join()).isEqualTo("Ok asyncReadFile2");
        then(request4.join()).isEqualTo("Ok asyncReadFile2");

        System.out.println("End");
    }
}
