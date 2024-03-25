package com.example.drohne23.drone;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class DroneClient
{
    private PrintWriter out;
    private boolean running;

    private static DroneDataListener droneDataListener;

    public DroneClient() {}

    public void setDroneDataListener(DroneDataListener listener)
    {
        droneDataListener = listener;
    }

    public void start()
    {
        String hostName = "127.0.0.1";
        int portNumber = 10666;

        try (
                Socket socket = new Socket(hostName, portNumber);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ) {
            String fromServer;
            this.out = out;
            Map<String, Object> commands = new HashMap<>();
            ObjectMapper mapper = new ObjectMapper();

            while ((fromServer = in.readLine()) != null && !socket.isClosed())
            {
                DroneData data = mapper.readValue(fromServer, DroneData.class);
                if (droneDataListener != null) droneDataListener.onDroneDataReceived(data);
                out.println("Client Nachricht");
                if (socket.isClosed())
                {
                    break;
                }
                Thread.sleep(10);
            }
            running = true;
        } catch (Exception e)
        {
            System.err.println("An error occurred while communicating with the server: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public PrintWriter getOut()
    {
        return out;
    }
    public static void addCommand(@NotNull Map<String, Object> commands, String type, String text)
    {
        commands.put(type, text);
    }
    public static void sendCommands(Map<String, Object> commands, @NotNull PrintWriter out)
    {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String commandsJson = mapper.writeValueAsString(commands);
            out.println(commandsJson);
        } catch (JsonProcessingException e)
        {
            System.err.println("An error occurred while sending commands: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public static void startMovement(Map<String, Object> commands, String direction, PrintWriter out)
    {
        addCommand(commands, "Movement", direction);
        addCommand(commands, "Action", "start");
        sendCommands(commands, out);
        commands.clear();
    }
    public static void stopMovement(Map<String, Object> commands, String direction, PrintWriter out)
    {
        addCommand(commands, "Movement", direction);
        addCommand(commands, "Action", "stop");
        sendCommands(commands, out);
        commands.clear();
    }
    public static void changeMass(Map<String, Object> commands, float mass, PrintWriter out)
    {
        addCommand(commands, "Mass", Float.toString(mass));
        sendCommands(commands, out);
        commands.clear();
    }
    public static void changeGravity(Map<String, Object> commands, boolean useGravity, PrintWriter out)
    {
        addCommand(commands, "UseGravity", Boolean.toString(useGravity));
        sendCommands(commands, out);
        commands.clear();
    }
    public static void changeHoehe(Map<String, Object> commands, float hoehe, PrintWriter out)
    {
        addCommand(commands, "Hoehe", Float.toString(hoehe));
        sendCommands(commands, out);
        commands.clear();
    }
    public static void changeDrag(Map<String, Object> commands, float drag, PrintWriter out)
    {
        addCommand(commands, "Drag", Float.toString(drag));
        sendCommands(commands, out);
        commands.clear();
    }
    public static void changeScale(Map<String, Object> commands, float x, float y, float z, PrintWriter out) throws JsonProcessingException
    {
        Map<String, Float> scale = new HashMap<>();
        scale.put("x", x);
        scale.put("y", y);
        scale.put("z", z);
        addCommand(commands, "Scale", new ObjectMapper().writeValueAsString(scale));
        sendCommands(commands, out);
        commands.clear();
    }
}
