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
    public void startReciever() {
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

    }

    public static void main(String[] args) throws Exception {
    }
}