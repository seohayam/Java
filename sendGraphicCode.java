import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.util.*;
import java.lang.*;
import java.io.*;
import java.lang.Math.*;
import java.net.*;

public class sendGraphicCode extends Frame implements  ActionListener{
    //名前
    static TextArea ta1,ta2,ta3,ta4,ta5,ta6;
    static TextField tf1;
    static Button b1,b2,b3,b4,b5,b6,b7,b8;
    static Socket sock;
    static InputStream  in;
    static DataInputStream  dis;
    static OutputStream  out;
    static DataOutputStream  dos;
    static String AUIP,selfIP;
    static String SndTP;

    public sendGraphicCode(){  
        super("sendGraphicCode");
        setLayout(null);
        this.setSize(1000,400);
        setVisible(true);

        Label L1 = new Label("message");
        L1.setSize(100,30);
        L1.setLocation(10,20);
        this.add(L1);

        Label L2 = new Label("configuratio");
        L2.setSize(100,30);
        L2.setLocation(600,20);
        this.add(L2);

        Label L3 = new Label("expression");
        L3.setSize(100,30);
        L3.setLocation(390,20);
        this.add(L3);

        ta1 = new TextArea();
        ta1.setSize(350,200);
        ta1.setLocation(10,60);
        ta1.setVisible(true);
        this.add(ta1);
        ta2 = new TextArea();
        ta2.setSize(350,60);
        ta2.setLocation(600,50);
        ta2.setVisible(true);
        this.add(ta2);
        
        //smile
        ta3 = new TextArea();
        ta3.setSize(350,60);
        ta3.setLocation(600,450);
        ta3.setVisible(true);
        ta3.setEditable(false);
        this.add(ta3);
        //cry
        ta4 = new TextArea();
        ta4.setSize(350,60);
        ta4.setLocation(600,450);
        ta4.setVisible(true);
        ta4.setEditable(false);
        this.add(ta4);
        //angry
        ta5 = new TextArea();
        ta5.setSize(350,60);
        ta5.setLocation(600,450);
        ta5.setVisible(true);
        ta5.setEditable(false);
        this.add(ta5);
        //good
        ta6 = new TextArea();
        ta6.setSize(350,60);
        ta6.setLocation(600,450);
        ta6.setVisible(true);
        ta6.setEditable(false);
        this.add(ta6);


        tf1 = new TextField("127.0.0.1");
        tf1.setSize(350,20);
        tf1.setLocation(600,120);
        tf1.setVisible(true);
        this.add(tf1);

        b1 = new Button("Connect");
        b1.setSize(80,30);
        b1.setLocation(600,150);
        b1.setVisible(true);
        b1.addActionListener(this);
        this.add(b1);
        b2 = new Button("DisCnct");
        b2.setSize(80,30);
        b2.setLocation(700,150);
        b2.setVisible(true);
        b2.addActionListener(this);
        this.add(b2);
        b3 = new Button("Send");
        b3.setSize(80,30);
        b3.setLocation(250,280);
        b3.setVisible(true);
        b3.addActionListener(this);
        this.add(b3);
        b4 = new Button("End");
        b4.setSize(80,30);
        b4.setLocation(800,150);
        b4.setVisible(true);
        b4.addActionListener(this);
        this.add(b4);
        
        b5 = new Button("Smile");
        b5.setSize(100,30);
        b5.setLocation(390,60);
        b5.setVisible(true);
        b5.addActionListener(this);
        this.add(b5);

        b6 = new Button("Cry");
        b6.setSize(100,30);
        b6.setLocation(390,100);
        b6.setVisible(true);
        b6.addActionListener(this);
        this.add(b6);

        b7 = new Button("Angry");
        b7.setSize(100,30);
        b7.setLocation(390,150);
        b7.setVisible(true);
        b7.addActionListener(this);
        this.add(b7);

        b8 = new Button("Good");
        b8.setSize(100,30);
        b8.setLocation(390,200);
        b8.setVisible(true);
        b8.addActionListener(this);
        this.add(b8);


        addWindowListener(new WindowEvevtHandler());
        ta3.setText("g clear\ng drawOval,300,120,150,150\ng fillOval,320,150,30,30\ng fillOval,390,150,30,30\ng drawLine,320,205,330,235\ng drawLine,420,205,410,235\ng drawLine,330,235,410,235\n");
        ta4.setText("g clear\ng drawOval,300,120,150,150\ng fillOval,320,150,30,30\ng fillOval,330,180,10,10\ng fillOval,330,200,10,10\ng fillOval,330,220,10,10\ng fillOval,390,150,30,30\ng fillOval,400,180,10,10\ng fillOval,400,200,10,10\ng fillOval,400,220,10,10\ng drawLine,340,240,400,240\n");
        ta5.setText("g clear\ng drawOval,300,120,150,150\ng fillOval,320,150,30,30\ng fillOval,390,150,30,30\ng drawLine,340,240,400,240\ng drawLine,400,100,400,120\ng drawLine,420,100,420,120\ng drawLine,420,60,420,80\ng drawLine,400,60,400,80\ng drawLine,420,80,440,80\ng drawLine,420,100,440,100\ng drawLine,380,80,400,80\ng drawLine,380,100,400,100\n");
        ta6.setText("g clear\ng setPosition,249,157\ng drawString,Good\n");
        this.setVisible(true);
    }

    //コネクション
    public static void connectToAUPlayer(String IPString) {
        try {
            sock = new Socket(IPString,9091);
            in = sock.getInputStream();
            dis = new DataInputStream(in);
            out = sock.getOutputStream();
            dos = new DataOutputStream(out);
            System.out.println("Connected to " + IPString);
            ta2.setText("Connected to " + IPString);
        }
        catch (IOException e){
            System.out.println("Connection not established!");
            ta2.setText("Connection not established!");
        }
    }

    public static void sendCommand(String scmd) {
	String res;
        try {
            dos.writeUTF(scmd);
            res = dis.readUTF();
	    System.out.println(":"+res);
        }
        catch (IOException e){
            System.out.println("Command not transferd!!");
        }
    }

    //static ActionListener lsn = new ActionListener(){
        public void actionPerformed(ActionEvent e){
            String pushedButton = e.getActionCommand();
            System.out.println(pushedButton);
            if(pushedButton.equals("Connect")) doConnect();
            else if(pushedButton.equals("DisConnect")) doDisConnect();

            else if(pushedButton.equals("Send")) doMessage();

            else if(pushedButton.equals("Smile")) doSmile();
            else if(pushedButton.equals("Cry")) doCry();
            else if(pushedButton.equals("Angry")) doAngry();
            else if(pushedButton.equals("Good")) doGood();

            else if(pushedButton.equals("End")) doEnd();
        }
    //};

    class WindowEvevtHandler extends WindowAdapter{
        public void windowClosing(WindowEvent e) {	
            System.exit(0);
        }
    }

    public static void doConnect(){
        AUIP = tf1.getText();
        connectToAUPlayer(AUIP);
        selfIP = AUIP;
    }

    public static void doDisConnect(){
    }

    public static void doMessage()
    {
        String ss = ta1.getText();
            sendCommand(ss);
    }

    //終わり、コマンドに終わりと送る
    public static void doEnd(){
	sendCommand("End ");
    }
    
    //smileの命令
    public static void doSmile(){
        String ss = ta3.getText();
        int nl = numberOfLines(ss);
        for(int i=1; i<=nl; i++){
            sendCommand(getLine(ss,i));
        }
    }
    //cryの命令
    public static void doCry(){
        String ss = ta4.getText();
        int nl = numberOfLines(ss);
        for(int i=1; i<=nl; i++){
            sendCommand(getLine(ss,i));
        }
    }
    //angryの命令
    public static void doAngry(){
        String ss = ta5.getText();
        int nl = numberOfLines(ss);
        for(int i=1; i<=nl; i++){
            sendCommand(getLine(ss,i));
        }
    }
    //goodの命令
    public static void doGood(){
        String ss = ta6.getText();
        int nl = numberOfLines(ss);
        for(int i=1; i<=nl; i++){
            sendCommand(getLine(ss,i));
        }
    }

    public static int numberOfLines(String s){
        String delim = "\n";
        StringTokenizer st = new StringTokenizer(s,delim);
        int itt= st.countTokens();
        return itt;
    }

    public static String getLine(String s, int nn){
        String delim = "\n";
        StringTokenizer st = new StringTokenizer(s,delim);
        int itt= st.countTokens();
        String sr = "";
        if (nn <= itt) {
            for (int i=0 ; i<nn; i++){
                sr = st.nextToken(delim);
            }
        }
        return sr;
    }

    public static void main(String args[]) {
        new sendGraphicCode();
    }
}



