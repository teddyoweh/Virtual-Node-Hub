package src.main.java.app.server;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Dictionary;
import java.util.Hashtable;
import javax.swing.table.DefaultTableModel;
import java.net.*;
import java.io.*;


public class Interface extends JFrame implements ActionListener {
    public  Dictionary<String, UserHandler> clientmemory;
 
    private JLabel label;
    private JButton button;
    JMenu menu, submenu;  
    JPanel serverpanel = new JPanel(); 
    JLabel serverlabel = new JLabel("Server Host");
    JTextField servertf = new JTextField(10); 
    JLabel portlabel = new JLabel("Server Host");
    JTextField porttf = new JTextField(10); 
    JButton connectbtn = new JButton("Connect");
    public Interface() {
        super("Virtual Node Hub");
     
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();
        setSize(screenWidth, screenHeight);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // setLocationRelativeTo(null);




        JMenu menu, submenu, helpmenu;  
        JMenuItem i1, i2, i3, i4, i5,h1;  
 
 
        JMenuBar mb=new JMenuBar();  
        menu=new JMenu("Home");  
        helpmenu = new JMenu("Help");

        h1 = new JMenuItem("Info");
        i1=new JMenuItem("Item 1");  
        i2=new JMenuItem("Item 2");  
        i3=new JMenuItem("Item 3");  
        i4=new JMenuItem("Item 4");  
        i5=new JMenuItem("Item 5");  
        menu.add(i1); menu.add(i2); menu.add(i3);  
        helpmenu.add(h1);

   
    
        mb.add(menu); 
        mb.add(helpmenu);
        setJMenuBar(mb); 
        JPanel apppanel = new JPanel();

     
        connectbtn.addActionListener(this);
 
        serverpanel.add(serverlabel);
        serverpanel.add(servertf);
        serverpanel.add(portlabel);
        serverpanel.add(porttf);
        serverpanel.add(connectbtn);
        apppanel.add(serverpanel);
        DefaultTableModel tableModel = new DefaultTableModel();
        JTable table = new JTable(tableModel);
        tableModel.addColumn("#"); 
        tableModel.addColumn("Name");
        tableModel.addColumn("IP");
        tableModel.addColumn("MAC");
        tableModel.addColumn("Port");
        tableModel.addColumn("Status");
        
        int rowCount = tableModel.getRowCount();  
        String[] newRow = new String[5];  
        // newRow[0] = String.valueOf(rowCount + 1); 
        // newRow[1] = "Teddy's PC";
        // newRow[2] = "localhost";
        // newRow[3] = "3434:3434";
 
        
        // tableModel.addRow(newRow);  
        
        
        JLabel tableLabel = new JLabel("Connected Nodes");

        JScrollPane sp=new JScrollPane(table); 
        apppanel.add(tableLabel);
        apppanel.add(sp);          
        apppanel.setLayout(new BoxLayout(apppanel, BoxLayout.Y_AXIS));
        add(apppanel);

    }

 
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if (e.getSource() == connectbtn) {
            // Dictionary<String, String>clientdata = new Hashtable<>();
            // UserHandler data = new UserHandler(clientdata);
            // DeviceView window = new DeviceView(data);
            // window.setVisible(true);
            try {   
                    String serverhost; 
                    int serverport;
                    serverhost = servertf.getText();
                    serverport = Integer.parseInt(porttf.getText());
                    Thread t = new Server(serverhost,serverport,clientmemory);
                    t.start();
                   } catch (IOException ee) {
           System.out.println("Error");
                   }
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

  
        
    }



}
class UserHandler {
    public String ip;
    public String pc;
    public String port;

    public String title;
    public String  data;

    public UserHandler(Dictionary clientdata) {
        this.title = (String) clientdata.get("ip")+clientdata.get("port");
        this.ip = (String) clientdata.get("ip");
        this.pc = (String) clientdata.get("pc");
        this.port = (String) clientdata.get("port");
 
    }

    
}

 
 class Server extends Thread {
    private ServerSocket serverSocket;
    public Dictionary<String, UserHandler> clientsMem;
    public Server(String host,int port,Dictionary<String, UserHandler> clientsMem) throws IOException {
       serverSocket = new ServerSocket(port);
       serverSocket.setSoTimeout(10000);
       this.clientsMem =clientsMem;
    }
 
    public void run() {
       while(true) {
          try {
            System.out.println("Waiting for client on port " + 
            serverSocket.getLocalPort() + "...");
            Socket server = serverSocket.accept();       
            System.out.println("Just connected to " + server.getInetAddress());
            Dictionary<String, String>clientdata = new Hashtable<>();
          
            clientdata.put("ip", server.getInetAddress().toString());
            clientdata.put("port",Integer.toString(server.getPort()));
            clientdata.put("pc", "");
            UserHandler data = new UserHandler(clientdata);
            clientsMem.put(server.getInetAddress().toString()+Integer.toString(server.getPort()), data);
             DataInputStream in = new DataInputStream(server.getInputStream());
             
             System.out.println(in.readUTF());
             DataOutputStream out = new DataOutputStream(server.getOutputStream());
             out.writeUTF("Thank you for connecting to " + server.getLocalSocketAddress()
                + "\nGoodbye!");
             server.close();
             
          } catch (SocketTimeoutException s) {
             System.out.println("Socket timed out!");
             break;
          } catch (IOException e) {
             e.printStackTrace();
             break;
          }
       }
    }
    
    // public static void main(String [] args) {
    //    int port =9999;
    //    try {
    //       Thread t = new Server(port);
    //       t.start();
    //    } catch (IOException e) {
    //       e.printStackTrace();
    //    }
    // }
 }