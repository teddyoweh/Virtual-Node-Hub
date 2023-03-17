package src.main.java.app.server;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Dictionary;
import java.util.Hashtable;
import javax.swing.table.DefaultTableModel;
 

public class Interface extends JFrame implements ActionListener {

    private JLabel label;
    private JButton button;

    public Interface() {
        super("Virtual Node Hub");
        setLayout(new FlowLayout());
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();
        setSize(screenWidth, screenHeight);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        
        DefaultTableModel tableModel = new DefaultTableModel();
        JTable table = new JTable(tableModel);
        tableModel.addColumn("Name");
        tableModel.addColumn("IP");
        tableModel.addColumn("MAC");
        tableModel.addColumn("Port");
        tableModel.addColumn("Connect");
        tableModel.addRow(new Object[] { "Teddy's PC", "localhost", "3434:3434", "343",new JCheckBox("Java", true)   });
       
                
        JScrollPane sp=new JScrollPane(table);    
        add(sp);          

        button = new JButton("Open new window");
        button.addActionListener(this);
        add(button);

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {
            Dictionary<String, String>clientdata = new Hashtable<>();
            UserHandler data = new UserHandler(clientdata);
            DeviceView window = new DeviceView(data);
            window.setVisible(true);
        }
    }

    public static void main(String[] args) {
        Interface app = new Interface();
        app.setVisible(true);
    }
}

 

class DeviceView extends JFrame {
 

    public DeviceView(UserHandler data) {
        super(data.title);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JLabel titleLabel = new JLabel(data.title);
        add(titleLabel);

        JLabel dataLabel = new JLabel(data.additionalData);
        add(dataLabel);
        
    }



}
class UserHandler {
    public String ip;
    public String pc;
    public String port;

    public String title;
    public String additionalData;

    public UserHandler(Dictionary clientdata) {
        this.title = (String) clientdata.get("ip");
 
    }

    
}