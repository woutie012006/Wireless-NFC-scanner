import javax.swing.*;
import java.util.ArrayList;
import com.wouterv.server_nfc_connector.Server;
/**
 * Created by wouter on 25-8-2015.
 */
public class MainForm
{
    private JPanel panel;
    private JLabel lbl1;
    private String lastTag;
    private ArrayList<String> lastTech = new ArrayList<>();


    public static void main(String[] args) throws Exception
    {

        JFrame frame = new JFrame("MainForm");
        MainForm mf = new MainForm();
        frame.setContentPane(mf.panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(100, 100);

    }
    public MainForm()
    {
        lbl1.setText("");
        Server s = new Server("Thread 1");
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
