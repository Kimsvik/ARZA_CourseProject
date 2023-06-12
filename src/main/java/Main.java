import java.io.IOException;
import java.util.ArrayList;
import java.util.TimerTask;

public class Main {
    public static void main(String[] args) throws IOException {
        GoosePacket goosePacket = new GoosePacket();
        GooseSender gooseControlBlock = new GooseSender();
        gooseControlBlock.setGoEna(true);
        gooseControlBlock.setAppID("Realtek PCIe GbE Family Controller");

        /* WAN Miniport (IPv6)
        * WAN Miniport (Network Monitor)
        * Bluetooth Device (Personal Area Network)
        * Intel(R) Wireless-AC 9560 160MHz Interface id: 0
        (\Device\NPF_{C1D8D924-3C0B-4C07-B195-E678FB46E15C})
        * Microsoft Wi-Fi Direct Virtual Adapter #2
        * Microsoft Wi-Fi Direct Virtual Adapter
        * Adapter for loopback traffic capture
        */

        gooseControlBlock.setIface();
        int period = 100; // мс
        ArrayList<Integer> periods = new ArrayList<>();
        ArrayList<Integer> periods1 = new ArrayList<>();
        ArrayList<Integer> periods2 = new ArrayList<>();
        goosePacket.createGoosePacket();
        gooseControlBlock.setGoosePacket(goosePacket);
        gooseControlBlock.sendGOOSEMessage();
        periods2.add(0);
        for (int i = 4; i < 100; i = i * 2) {
            periods1.add(i);
            periods2.add(i);
        }
        for (int i = 0; i < 4; i++) {
            periods1.add(100);
            periods2.add(100);
        }
        for (int i = 0; i < periods1.size(); i++) {
            //int j = periods1.get(i);
            java.util.Timer timer1 = new java.util.Timer();
            timer1.scheduleAtFixedRate(new TimerTask() {
                @Override public void run() {
                    //periods2.add(j);
                    gooseControlBlock.setPeriods(periods2);
                    goosePacket.createGoosePacket();
                    gooseControlBlock.setGoosePacket(goosePacket);
                    gooseControlBlock.sendGOOSEMessage();
                }
                }, 0, periods1.get(i));
            try {
                Thread.sleep(periods1.get(i)-1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            timer1.cancel();
        }

        //gooseControlBlock.startReceiving();
    }
}
