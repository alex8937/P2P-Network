import java.net.*;
import java.io.*;
import java.nio.*;
import java.nio.file.*;
import java.nio.channels.*;
import java.util.*;

  
public class Server {
	
    private ServerSocket ss  =  null;  
    public Server(){  
          
    } 


    public static int splitFile(File f) throws IOException {
        int partCounter  =  1;//File extension start from 001,002,003...

        int sizeOfFiles  =  1024 * 100;// 100 KB
        byte[] buffer  =  new byte[sizeOfFiles];

        try (BufferedInputStream bis  =  new BufferedInputStream(
            new FileInputStream(f))) {//try-with-resources to ensure closing stream
            String name  =  f.getName();

            int tmp  =  0;
            while ((tmp  =  bis.read(buffer)) > 0) {
                //write each chunk of data into separate file with different number in name
                File newFile  =  new File(f.getParent(), name + "."
                        + String.format("%03d", partCounter++));
                try (FileOutputStream out  =  new FileOutputStream(newFile)) {
                    out.write(buffer, 0, tmp);//tmp is chunk size
                }
            }
		return partCounter-1;
        }
	}
	
    public void sendFile(String filePath,int port){  
        DataOutputStream dos = null;  
        DataInputStream dis = null;  
          
        Socket socket = null;  
        try {  
            File file = new File(filePath);  
            ss = new ServerSocket(port);  
            socket = ss.accept();  
            dos = new DataOutputStream(socket.getOutputStream());  
            dis = new DataInputStream(new BufferedInputStream(new FileInputStream(filePath)));  
              
            int buffferSize = 1024;  
            byte[]bufArray = new byte[buffferSize];  
            dos.writeUTF(file.getName());   
            dos.flush();   
            dos.writeLong((long) file.length());   
            dos.flush();   
            while (true) {   
                int read  =  0;   
                if (dis !=  null) {   
                  read  =  dis.read(bufArray);   
                }   
  
                if (read == -1) {   
                  break;   
                }   
                dos.write(bufArray, 0, read);   
              }   
              dos.flush();  
        } catch (FileNotFoundException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } finally {   
              // Close all connections   
              try {   
                if (dos !=  null)   
                  dos.close();   
              } catch (IOException e) {   
              }   
              try {   
                if (dis !=  null)   
                  dis.close();   
              } catch (IOException e) {   
              }   
              try {   
                if (socket !=  null)   
                  socket.close();   
              } catch (IOException e) {   
              }   
              try {   
                if (ss !=  null)   
                  ss.close();   
              } catch (IOException e) {   
              }   
            }   
    }  

    public void sendmsg(String msg,int port){  
        DataOutputStream dos = null;        
        Socket socket = null;  
        try {  
            ss = new ServerSocket(port);  
            socket = ss.accept();  
            dos = new DataOutputStream(socket.getOutputStream());  
            dos.writeUTF(msg);   
            dos.flush();   
		}catch (FileNotFoundException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } finally {   
              // Close all connections   
              try {   
                if (dos !=  null)   
                  dos.close();   
              } catch (IOException e) {   
              }      
              try {   
                if (socket !=  null)   
                  socket.close();   
              } catch (IOException e) {   
              }   
              try {   
                if (ss !=  null)   
                  ss.close();   
              } catch (IOException e) {   
              }   
            }   
    }  
	
    public static void main(String []args)throws IOException {
        Path currentRelativePath0 = Paths.get("");
        String dir_con = currentRelativePath0.toAbsolutePath().getParent().toString();
        File file_con = new File(dir_con+"/config.txt");
        Scanner scanner = new Scanner(file_con).useDelimiter(",");

        int mm = 0;
        String serverport,clinetport2,clinetport3,clinetport4,clinetport5,clinetport1;
        String c1from,c1to,c2from,c2to,c3from,c3to,c4from,c4to,c5from,c5to;                
        String recport1,recport2,recport3,recport4,recport5;
		
        serverport = "";clinetport1 = "";clinetport2 = "";clinetport3 = "";clinetport4 = "";clinetport5 = "";
        c1from = "";c1to = "";c2from = "";c2to = "";c3from = "";c3to = "";c4from = "";c4to = "";c5from = "";c5to = "";
        recport1 = "";recport2 = "";recport3 = "";recport4 = "";recport5 = "";
		
        while (scanner.hasNext()) {
            String str00  =  scanner.next();
            //System.out.println(str00);
            mm++;
            if(mm == 1) serverport = str00.replace("\n", "").replace("\r", "");
            if(mm == 2) clinetport1 = str00.replace("\n", "").replace("\r", "");
            if(mm == 3) c1to = str00.replace("\n", "").replace("\r", "");			
            if(mm == 4) recport1 = str00.replace("\n", "").replace("\r", ""); 
            if(mm == 5) c1from = str00.replace("\n", "").replace("\r", ""); 
            
            if(mm == 6) clinetport2 = str00.replace("\n", "").replace("\r", "");
            if(mm == 7) c2to = str00.replace("\n", "").replace("\r", "");
            if(mm == 8) recport2 = str00.replace("\n", "").replace("\r", "");	
            if(mm == 9) c2from = str00.replace("\n", "").replace("\r", "");
            
            if(mm == 10) clinetport3 = str00.replace("\n", "").replace("\r", "");                                                
            if(mm == 11) c3to = str00.replace("\n", "").replace("\r", "");                        
            if(mm == 12) recport3 = str00.replace("\n", "").replace("\r", "");	
            if(mm == 13) c3from = str00.replace("\n", "").replace("\r", ""); 		
            
            if(mm == 14) clinetport4 = str00.replace("\n", "").replace("\r", "");                                                
            if(mm == 15) c4to = str00.replace("\n", "").replace("\r", "");                        
            if(mm == 16) recport4 = str00.replace("\n", "").replace("\r", "");
            if(mm == 17) c4from = str00.replace("\n", "").replace("\r", ""); 
            
            if(mm == 18) clinetport5 = str00.replace("\n", "").replace("\r", "");                                                
            if(mm == 19) c5to = str00.replace("\n", "").replace("\r", "");                        
            if(mm == 20) recport5 = str00.replace("\n", "").replace("\r", "");
            if(mm == 21) c5from = str00.replace("\n", "").replace("\r", "");
			
      }
        //System.out.println(serverport);
		//System.out.println(c5to);
      System.out.println("Please first enter your filename before transmission: ");		
      Scanner scan  =  new Scanner(System.in);
      String Filename  =  scan.next();
      Path currentRelativePath  =  Paths.get("");
      String dir  =  currentRelativePath.toAbsolutePath().toString();
      //System.out.println("Current relative path is: " + dir);
      int numofile = new Server().splitFile(new File(dir+"/"+Filename));	
      System.out.println("File is splitted into "+numofile+" pieces");
      Thread t1  =  new Thread(new MyRunnable(1,Filename,numofile,Integer.parseInt(serverport)+1,dir));
      Thread t2  =  new Thread(new MyRunnable(2,Filename,numofile,Integer.parseInt(serverport)+2,dir));
      Thread t3  =  new Thread(new MyRunnable(3,Filename,numofile,Integer.parseInt(serverport)+3,dir));
      Thread t4  =  new Thread(new MyRunnable(4,Filename,numofile,Integer.parseInt(serverport)+4,dir));
      Thread t5  =  new Thread(new MyRunnable(5,Filename,numofile,Integer.parseInt(serverport)+5,dir));
      t1.start();
      t2.start();
      t3.start();
      t4.start();
      t5.start();
	/*		new Server().sendmsg(numofile+"",8100);
		for(int i = 1; i< = numofile; i = i+2){
			new Server().sendFile("C:/Users/Ultra-seven/Desktop/Java/server/a.pdf."+String.format("%03d",i), 8001);  
		}
		new Server().sendmsg(numofile+"",8200);
		for(int i = 0; i< = numofile; i = i+2){
			new Server().sendFile("C:/Users/Ultra-seven/Desktop/Java/server/a.pdf."+String.format("%03d",i), 8002);  
		}*/
    }
	public static class MyRunnable implements Runnable {
		private int numofile;
		private int portmsg;
		private int no;
		private String Filename;
		private String dir;
		public MyRunnable(int no,String Filename,int numofile,int portmsg,String dir) {
			this.numofile  =  numofile;
			this.portmsg = portmsg;
			this.no = no;
			this.Filename = Filename;
			this.dir = dir;			
		}

		public void run() {
			new Server().sendmsg(numofile+"",portmsg);
			int nsentfromserver = (int)Math.ceil((1.0*numofile-(1.0*no-1.0))/5.0);
			//System.out.println(nsentfromserver);
			new Server().sendmsg(nsentfromserver+"",portmsg+10);
			new Server().sendmsg(Filename,portmsg+20);
			for(int i = 1; i< = nsentfromserver; i++){
				new Server().sendFile(dir+"/"+Filename+"."+String.format("%03d",no+(i-1)*5), portmsg+30); 
				int j = no+(i-1)*5;
				System.out.println("Piece "+j+" is sent to client "+no);
			}
		}
   }


	
} 
