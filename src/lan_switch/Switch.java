package lan_switch;

import config.ConfigParser;

import java.lang.reflect.Array;
import java.util.Scanner;

public class Switch {
    private int ID;
    private int port;
    private Array neighbors;

    public int getID() {
        return ID;
    }

    public int getPort() {
        return port;
    }

    public Array getNeighbors() {
        return neighbors;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setNeighbors(Array neighbors) {
        this.neighbors = neighbors;
    }

    public void setSwitchInfo(){

    }

    private void sendFrame(String sourceMacAddress, String destinationMacAddress, String userData){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the ID of the device you'd like to message: ");
        sourceMacAddress = scanner.nextLine();
        ConfigParser.getConfigForDevice(sourceMacAddress);
    }

    private void recieveFrame(String sourceMacAddress, String destinationMacAddress, String data){

    }
    private void createSwitchTable(String sourceMacAddress, String port){

    }
    private void updateSwitchTable(String sourceMacAddress, String port) {

    }
}