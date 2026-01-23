import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class Host {

    private String myID;
    private ConfigParser.DeviceInfo myInfo;
    private ConfigParser.DeviceInfo switchInfo;

    private DatagramSocket socket;

    public Host(String id, String configFile) throws Exception {
        this.myID = id;

        ConfigParser parser = new ConfigParser(configFile);
        myInfo = parser.getDevice(id);

        if (myInfo == null) {
            throw new RuntimeException("Host ID not found in config file.");
        }


        String switchID = myInfo.neighbors.get(0);
        switchInfo = parser.getDevice(switchID);

        socket = new DatagramSocket(myInfo.port);

        System.out.println("Host " + myID + " started on port " + myInfo.port);
        System.out.println("Connected to switch " + switchID + " at " + switchInfo.ip + ":" + switchInfo.port);

        startReceiverThread();
    }

    private void startReceiverThread() {
        Thread t = new Thread(() -> {
            byte[] buf = new byte[2048];

            while (true) {
                try {
                    DatagramPacket packet = new DatagramPacket(buf, buf.length);
                    socket.receive(packet);

                    String raw = new String(packet.getData(), 0, packet.getLength());
                    Frame f = Frame.parse(raw);

                    if (!f.dst.equals(myID)) {
                        System.out.println("[DEBUG] Received flooded frame not meant for me. SRC=" + f.src);
                    } else {
                        System.out.println("Message from " + f.src + ": " + f.msg);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        t.setDaemon(true);
        t.start();
    }

    public void sendMessage(String dst, String msg) throws IOException {
        Frame f = new Frame(myID, dst, msg);
        String raw = f.serialize();

        byte[] data = raw.getBytes();
        DatagramPacket packet = new DatagramPacket(
                data,
                data.length,
                InetAddress.getByName(switchInfo.ip),
                switchInfo.port
        );

        socket.send(packet);
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.out.println("Usage: java Host <ID> <config-file>");
            return;
        }

        Host host = new Host(args[0], args[1]);

        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("Enter destination and message (e.g., D hello): ");
            String dst = sc.next();
            String msg = sc.nextLine().trim();

            host.sendMessage(dst, msg);
        }
    }
}