import javax.swing.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import com.wouterv.server_nfc_connector.Server;
/**
 * Created by wouter on 25-8-2015.
 */
public class MainForm
{
    private JPanel panel;
    private JLabel lbl1;
    private JLabel lblIp;
    private String lastTag;
    private ArrayList<String> lastTech = new ArrayList<>();


    public static void main(String[] args) throws Exception
    {

        JFrame frame = new JFrame("MainForm");
        MainForm mf = new MainForm();
        frame.setContentPane(mf.panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setSize(200, 300);
        frame.setVisible(true);

    }
    public MainForm()
    {
        lbl1.setText("");
        try {
            lblIp.setText("IP-adres : " + InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        Server s = new Server("Thread 1", lbl1);
        s.start();
    }
    public void setLbl1(String Text)
    {
        lbl1.setText(Text);
    }

    public String getLastTag()
    {
        return lastTag;
    }

    public void setLastTag(String lastTag)
    {
        this.lastTag = lastTag;
    }

    public ArrayList<String> getLastTech()
    {
        return lastTech;
    }

    public void setLastTech(ArrayList<String> lastTech)
    {
        this.lastTech = lastTech;
    }
}
