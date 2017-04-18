package benchmarks;

import ev3dev.sensors.Battery;
import ev3dev.utils.Sysfs;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public @Slf4j class IOBenchmarkTest1 {

    private static final Path path = Paths.get("/sys/class/power_supply/brickpi-battery/voltage_now");
    private static final String wPath = "/sys/class/lego-port/port2/mode";
    private static final String wValue = "tacho-motor";
    private static final Path writePath = Paths.get(wPath);
    static volatile double result = 0;

    private static final Battery battery = Battery.getInstance();

    public static void main(String args[]) throws Exception {

        log.info("Testing IO handling with EV3Dev");

        log.info("Read tests");
        //classicIOInteractionTest();
        NIOInteractionTest();

        log.info("Write tests");
    }

    private static void NIOInteractionTest() throws IOException {

        log.info("NIO Interaction");
        log.info("Read operations");

        /*
        result = readNTimesNIO(100);
        result = readNTimesNIO(100);
        result = readNTimesNIO(100);
        result = readNTimesNIO(1000);
        result = readNTimesNIO(1000);
        result = readNTimesNIO(1000);
        result = readNTimesNIO(10000);
        result = readNTimesNIO(10000);
        result = readNTimesNIO(10000);
        result = readNTimesNIO(100000);
        result = readNTimesNIO(100000);
        result = readNTimesNIO(100000);
        */

        log.info("Write operations");

        /*
        writeNTimesNIO(100);
        writeNTimesNIO(100);
        writeNTimesNIO(100);
        writeNTimesNIO(1000);
        writeNTimesNIO(1000);
        writeNTimesNIO(1000);
        */
        //writeNTimesNIO(10000);
        //writeNTimesNIO(10000);
        //writeNTimesNIO(10000);
        //writeNTimesNIO(100000);
        //writeNTimesNIO(100000);
        //writeNTimesNIO(100000);

        writeNTimesNIO2(100);
        writeNTimesNIO2(100);
        writeNTimesNIO2(100);
        writeNTimesNIO2(1000);
        writeNTimesNIO2(1000);
        writeNTimesNIO2(1000);

        final Battery battery = Battery.getInstance();
        log.info("Battery: {}", battery.getVoltage());
    }

    private static void classicIOInteractionTest(){

        log.info("EV3Dev-lang-java v0.5.0 IO Interaction");
        log.info("Read operations");

        result = readNTimes(100);
        result = readNTimes(100);
        result = readNTimes(100);
        result = readNTimes(1000);
        result = readNTimes(1000);
        result = readNTimes(1000);
        result = readNTimes(10000);
        result = readNTimes(10000);
        result = readNTimes(10000);
        result = readNTimes(100000);
        result = readNTimes(100000);
        result = readNTimes(100000);

        log.info("Write operations");

        result = writeNTimes(100);
        result = writeNTimes(100);
        result = writeNTimes(100);
        result = writeNTimes(1000);
        result = writeNTimes(1000);
        result = writeNTimes(1000);
        //result = writeNTimes(10000);
        //result = writeNTimes(10000);
        //result = writeNTimes(10000);
        //result = writeNTimes(100000);
        //result = writeNTimes(100000);
        //result = writeNTimes(100000);

        final Battery battery = Battery.getInstance();
        log.info("Battery: {}", battery.getVoltage());
    }

    private static double readNTimesNIO(final int times){

        final List<Long> processingTime = new ArrayList<>();
        for(int x = 0; x < times; x++ ){
            processingTime.add(readFloatNIO());
        }

        final Double result = processingTime.stream()
                .mapToLong(Long::longValue)
                .average()
                .getAsDouble();

        log.info("Iterations: {}, time: {}", times, result);
        return result;
    }

    private static double readNTimes(final int times){

        final List<Long> processingTime = new ArrayList<>();
        for(int x = 0; x < times; x++ ){
            processingTime.add(readBatteryClassicStyle());
        }

        final Double result = processingTime.stream()
                .mapToLong(Long::longValue)
                .average()
                .getAsDouble();

        log.info("Iterations: {}, time: {}", times, result);
        return result;
    }

    private static double writeNTimes(final int times){

        final List<Long> processingTime = new ArrayList<>();
        for(int x = 0; x < times; x++ ){
            processingTime.add(writePortClasicStyle());
        }

        final Double result = processingTime.stream()
                .mapToLong(Long::longValue)
                .average()
                .getAsDouble();

        log.info("Iterations: {}, time: {}", times, result);
        return result;
    }

    private static double writeNTimesNIO(final int times){

        final List<Long> processingTime = new ArrayList<>();
        for(int x = 0; x < times; x++ ){
            processingTime.add(writeStringNIO());
        }

        final Double result = processingTime.stream()
                .mapToLong(Long::longValue)
                .average()
                .getAsDouble();

        log.info("Iterations: {}, time: {}", times, result);
        return result;
    }

    private static double writeNTimesNIO2(final int times) throws IOException {

        final List<Long> processingTime = new ArrayList<>();
        for(int x = 0; x < times; x++ ){
            processingTime.add(writeStringNIO2());
        }

        final Double result = processingTime.stream()
                .mapToLong(Long::longValue)
                .average()
                .getAsDouble();

        log.info("Iterations: {}, time: {}", times, result);
        return result;
    }

    private static long readBatteryClassicStyle(){
        long startTime = System.currentTimeMillis();
        battery.getVoltage();
        long endTime = System.currentTimeMillis();
        long duration = (endTime - startTime);
        return duration;
    }

    private static long writePortClasicStyle(){

        long startTime = System.currentTimeMillis();
        Sysfs.writeString(wPath, wValue);
        long endTime = System.currentTimeMillis();
        long duration = (endTime - startTime);
        return duration;

    }

    public static boolean fileExists(Path pathToFind) {
        return Files.exists(pathToFind);
    }

    public static String readValueFromFile() {
        if (!fileExists(path)) {
            System.out.println("File not found: " + path.toString());
            return null;
        }
        try {
            return new String(Files.readAllBytes(path));
        } catch (Exception e) {
            System.out.println("Cannot write to file: " + path.toString());
            e.printStackTrace();
            return null;
        }

    }

    public static boolean writeToFile(String content) {

        /*
        if(!fileExists(writePath)) {
            System.out.println("File not found: " + writePath.toString());
            return false;
        }

        try {
            Files.write(writePath, content.getBytes(), StandardOpenOption.WRITE);
        } catch (Exception e) {
            System.out.println("Cannot write to file: " + writePath.toString());
            e.printStackTrace();
            return false;
        }
        */

        try(BufferedWriter writer = Files.newBufferedWriter(writePath, Charset.forName("UTF-8"))){
            writer.write(wValue);
        }catch(IOException ex){
            ex.printStackTrace();
        }


        return true;
    }

    public static void writeToFileNIOWay(final File file, final String value) throws IOException {
        final FileOutputStream fileOutputStream = new FileOutputStream(file, true);
        final FileChannel fileChannel = fileOutputStream.getChannel();
        final ByteBuffer byteBuffer = ByteBuffer.wrap(value.getBytes(Charset.forName("UTF-8")));
        fileChannel.write(byteBuffer);
        //fileChannel.close();
    }


    public static long readFloatNIO() {

        long startTime = System.currentTimeMillis();
        Float.parseFloat(readValueFromFile());
        long endTime = System.currentTimeMillis();
        long duration = (endTime - startTime);
        return duration;
    }

    public static long writeStringNIO() {

        long startTime = System.currentTimeMillis();
        writeToFile(wValue);
        long endTime = System.currentTimeMillis();
        long duration = (endTime - startTime);
        return duration;
    }

    public static long writeStringNIO2() throws IOException {

        long startTime = System.currentTimeMillis();
        writeToFileNIOWay(new File(wPath), wValue);
        long endTime = System.currentTimeMillis();
        long duration = (endTime - startTime);
        return duration;
    }

}
