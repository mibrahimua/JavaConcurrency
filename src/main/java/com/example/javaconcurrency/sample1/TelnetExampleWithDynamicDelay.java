package com.example.javaconcurrency.sample1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;

public class TelnetExampleWithDynamicDelay {

    public static void main(String[] args) throws IOException, InterruptedException {
        String server = "example.com"; // Replace with the hostname or IP address of the Telnet server
        int port = 23; // Replace with the port number used by the Telnet server

        // Connect to the Telnet server
        Socket socket = new Socket(server, port);
        System.out.println("Connected to Telnet server");

        // Get input and output streams for the Telnet session
        InputStream input = socket.getInputStream();
        PrintStream output = new PrintStream(socket.getOutputStream());

        // Create a dynamic thread pool to execute multiple commands concurrently
        ExecutorService executor = Executors.newCachedThreadPool();

        // Define a list of commands to send to the Telnet server
        List<String> commands = new ArrayList<>();
        commands.add("help");
        commands.add("status");
        commands.add("config");

        // Create a list of Callable tasks to execute the commands and read the response
        List<Callable<Void>> tasks = new ArrayList<>();
        for (String command : commands) {
            tasks.add(() -> {
                int retries = 3;
                while (retries > 0) {
                    try {
                        output.println(command);
                        output.flush();
                        Scanner scanner = new Scanner(input);
                        while (scanner.hasNextLine()) {
                            String line = scanner.nextLine();
                            System.out.println(Thread.currentThread().getName() + ": " + line);
                            // Add dynamic delay to the while loop
                            int randomDelay = ThreadLocalRandom.current().nextInt(1, 11);
                            TimeUnit.SECONDS.sleep(randomDelay);
                        }
                        break; // Exit while loop if command is successful
                    } catch (Exception e) {
                        System.out.println("Command failed: " + command);
                        e.printStackTrace();
                        retries--;
                        if (retries == 0) {
                            System.out.println("Max retries exceeded for command: " + command);
                        } else {
                            System.out.println("Retrying command: " + command);
                        }
                    }
                }
                return null;
            });
        }

        // Invoke all tasks synchronously and wait for them to complete
        List<Future<Void>> futures = executor.invokeAll(tasks);
        for (Future<Void> future : futures) {
            try {
                future.get(); // Wait for each task to complete
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        // Shut down the executor and close the Telnet session and the socket
        executor.shutdown();
        input.close();
        output.close();
        socket.close();
        System.out.println("Disconnected from Telnet server");
    }
}
