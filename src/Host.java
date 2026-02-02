import java.net.*;
import java.util.Scanner;

public class Host {

    private String hostId;
    private String hostIp;
    private int hostPort;

    private String switchIp;
    private int switchPort;

    private DatagramSocket socket;

    public void config() {
        switchIp = "127.0.0.1";
        int port = 3000;
        switch (hostId) {
            case "A" :
                hostIp = "127.0.0.1";
                hostPort = 5001;
                break;
            case "B" :
                hostIp = "127.0.0.1";
                hostPort = 5002;
                break;
            case "C":
                hostIp = "127.0.0.1";
                hostPort = 5003;
                break;
            case "D":
                hostIp = "127.0.0.1";
                hostPort = 5004;
                break;
        }
    }
    public void startReceiver() {
        Thread receiver = new Thread(() -> {
            try {
                byte[] buffer = new byte[1024];
                while (true) {
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    socket.receive(packet);
                    String frame = new String(packet.getData(), 0, packet.getLength());
                    handleFrame(frame);
                }
            }catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
        receiver.setDaemon(true);
        receiver.start();
    }

    private void handleFrame(String frame) {

        String[] parts = frame.split(":");

        if (parts.length < 3) {
            System.out.println("Bad frame received: " + frame);
            return;
        }

        String src = parts[0];
        String dst = parts[1];
        String msg = parts[2];


        if (dst.equals(hostId)) {
            System.out.println("Message from " + src + ": " + msg);
        } else {
            System.out.println("Frame for " + dst + " received at " + hostId + " (MAC mismatch)");
        }
    }

    private void startSender() {
        while (true) {
            Scanner scan = new Scanner(System.in);
            System.out.println("Enter IP address and port: ");
            String ipEntered = scan.nextLine().trim();

            System.out.println("Enter message: ");
            String messageEntered = scan.nextLine().trim();

            String frame = hostId + ":" + ipEntered + ":" + messageEntered;
            sendFrame(frame);
        }
    }

    private void sendFrame(String frame) {

    }

    private void start() {

    }

    public static void main(String[] args) throws Exception {
    }
}