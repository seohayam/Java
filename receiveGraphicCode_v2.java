import java.awt.*;
import java.io.*;
import java.util.*;
import java.lang.*;
import java.net.*;
import java.awt.event.*;
import java.awt.Robot;

public class receiveGraphicCode_v2 extends Frame{
    static TextArea ta1,ta2;
    static TextField tf1;
    static Button b1,b2,b3,b4;
    static   int xpos = 10;
    static Toolkit tk;
    static Dimension d;
    static Robot r;
    int p1=100,p2=100,p3=100,p4=150;
    Vector grVect;

    public receiveGraphicCode_v2(){ 
        super("receiveGraphicCode_v2");
        grVect = new Vector();
        ServerSocket serversock = null;
        boolean pl;
        Socket sock;
        int  id = 100;
        String name = "";
        int  id2;
        String scmd;
        OutputStream  out;
        DataOutputStream  dos;
        InputStream  in;
        DataInputStream  dis;  
        setLayout(null);
        setSize(500,400);
        setVisible(true);
        tf1 = new TextField();
        tf1.setSize(480,20);
        tf1.setLocation(10,340);
        tf1.setVisible(false);
        add(tf1);

        ta1 = new TextArea();
        ta1.setSize(200,200);
        ta1.setLocation(10,60);
        ta1.setVisible(true);
        this.add(ta1);
        
        this.setVisible(true);
        addWindowListener(new WindowEvevtHandler());
        try{
            serversock = new ServerSocket(9091);
        } catch(IOException e){
            System.out.println(e.toString());
        }

        while (true) {
            try{
                sock = serversock.accept();
                out = sock.getOutputStream();
                dos = new DataOutputStream(out);
                in = sock.getInputStream();
                dis = new DataInputStream(in);
                pl = true;
                while (pl){
                    scmd = dis.readUTF();
                    //System.out.println("Command: " + scmd);
                    String cm = getWord(scmd,1);
                    String dcmd = "";
                    if (cm.equals("g")) {
                        dcmd = scmd.substring(2,scmd.length());
                        if(dcmd.equals("clear")) grVect.removeAllElements();
                        else grVect.addElement(dcmd);
                        repaint();
                    }                    
                    if (cm.equals("b")) {
                        dcmd = scmd.substring(2,scmd.length());
                        if(dcmd.equals("clear")) grVect.removeAllElements();
                        else grVect.addElement(dcmd);
                    }                    
                    else if (cm.equals("text")) 
                                tf1.setText(scmd.substring(5,scmd.length()));
                    else if (cm.equals("end")){
                        pl = false;
                        System.out.println("END received! ");
                    }else if(cm.equals("g")){
                        tf1.setText(scmd);
                    }else{
                        ta1.setText(scmd);
                    }
                    dos.writeUTF("done");
                }
                dos.close();
                out.close();
                dis.close();
                in.close();
                sock.close();
        
            } catch (IOException e){
                System.out.println("Error e");
            }  
        }
    }
    ActionListener lsn = new ActionListener(){
        public void actionPerformed(ActionEvent e){
            String pushedButton = e.getActionCommand();
            if(pushedButton.equals("button")) method1();
        }
    };
    class WindowEvevtHandler extends WindowAdapter{
        public void windowClosing(WindowEvent e) {	
            System.exit(0);
        }
    }
    
    public void method1(){}

    public void paint(Graphics g) {
    Graphics2D g2 =(Graphics2D)g;
        g2.setColor(Color.blue);
        int spx=10,spy=60;
        String dcmd,cm;
        int dl = grVect.size();
        if(dl>0)
        for(int i=0; i < dl; i++){
            dcmd = (String)grVect.elementAt(i);
            cm = getItem(dcmd,1);
            if (cm.equals("drawLine")){
                int p1 = Integer.parseInt(getItem(dcmd,2));
                int p2 = Integer.parseInt(getItem(dcmd,3));
                int p3 = Integer.parseInt(getItem(dcmd,4));
                int p4 = Integer.parseInt(getItem(dcmd,5));
                g2.drawLine(p1,p2,p3,p4);
            } 
            else if (cm.equals("drawRect")){
                int p1 = Integer.parseInt(getItem(dcmd,2));
                int p2 = Integer.parseInt(getItem(dcmd,3));
                int p3 = Integer.parseInt(getItem(dcmd,4));
                int p4 = Integer.parseInt(getItem(dcmd,5));
                g2.drawRect(p1,p2,p3,p4);
            } 
            else if (cm.equals("fillRect")){
                int p1 = Integer.parseInt(getItem(dcmd,2));
                int p2 = Integer.parseInt(getItem(dcmd,3));
                int p3 = Integer.parseInt(getItem(dcmd,4));
                int p4 = Integer.parseInt(getItem(dcmd,5));
                g2.fillRect(p1,p2,p3,p4);
            }
            else if (cm.equals("drawOval")){
                int p1 = Integer.parseInt(getItem(dcmd,2));
                int p2 = Integer.parseInt(getItem(dcmd,3));
                int p3 = Integer.parseInt(getItem(dcmd,4));
                int p4 = Integer.parseInt(getItem(dcmd,5));
                g2.drawOval(p1,p2,p3,p4);
            }
	    else if (cm.equals("fillOval")){
                int p1 = Integer.parseInt(getItem(dcmd,2));
                int p2 = Integer.parseInt(getItem(dcmd,3));
                int p3 = Integer.parseInt(getItem(dcmd,4));
                int p4 = Integer.parseInt(getItem(dcmd,5));
                g2.fillOval(p1,p2,p3,p4);
            }
            else if (cm.equals("setColor")){
                int p1 = Integer.parseInt(getItem(dcmd,2));
                int p2 = Integer.parseInt(getItem(dcmd,3));
                int p3 = Integer.parseInt(getItem(dcmd,4));
                g2.setColor(new Color(p1,p2,p3));
            }
            else if (cm.equals("setFont")){
                String fontname = getItem(dcmd,2);
                int p1 = Integer.parseInt(getItem(dcmd,3));
                int p2 = Integer.parseInt(getItem(dcmd,4));
                g2.setFont(new Font(fontname,p1,p2));
            }
            else if (cm.equals("setPosition")){
                spx = Integer.parseInt(getItem(dcmd,2));
                spy = Integer.parseInt(getItem(dcmd,3));
            }
            else if (cm.equals("drawString")){
                String ss = dcmd.substring(11,dcmd.length());
                g2.drawString(ss,spx,spy);
            }
	    else if (cm.equals("BasicStroke")){
	    	int bst = Integer.parseInt(getItem(dcmd,2));
	    	BasicStroke bs = new BasicStroke(bst);
		g2.setStroke(bs);
	    }
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
    public static int numberOfWords(String s){
        String delim = " \n\r";
        StringTokenizer st = new StringTokenizer(s,delim);
        int itt= st.countTokens();
        return itt;
    }
    public static String getWord(String s, int nn){
        String delim = " \n\r";
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
    public static int numberOfItems(String s){
        String delim = ",";
        StringTokenizer st = new StringTokenizer(s,delim);
        int itt= st.countTokens();
        return itt;
    }
    public static String getItem(String s, int nn){
        String delim = ",";
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
        new receiveGraphicCode_v2();
    }
}
