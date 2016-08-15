import java.net.*;
import java.io.*;
import java.nio.*;
import java.nio.file.*;
import java.nio.channels.*;
import java.util.*;



public class Client2 {
    private ServerSocket ss = null; 
	private static int no = 2;	
	private static String dir = "0";
    public Client2(){  
          
    }  
    public void receiveFile(String savePath,String ip,int port){  
        Socket socket = null;  
        try {   
            socket = new Socket(ip,port);   
          } catch (UnknownHostException e1) {   
            e1.printStackTrace();   
          } catch (IOException e1) {   
            e1.printStackTrace();   
          }   
        DataInputStream dis = null;  
        try {   
            dis = new DataInputStream(new BufferedInputStream(socket   
                .getInputStream()));   
          } catch (IOException e1) {   
            e1.printStackTrace();   
          }   
        int bufferSize = 1024*100;   
        // bufferzone   
        byte[] buf = new byte[bufferSize];   
        int passedlen = 0;   
        long len = 0;   
        // Get file name   
		try{  
			savePath +=  dis.readUTF();   
			DataOutputStream fileOut = new DataOutputStream(   
            new BufferedOutputStream(new BufferedOutputStream(   
            new FileOutputStream(savePath))));   
			len = dis.readLong();   
			//System.out.println("Length of the file:" + len + "    KB");   
			//System.out.println("Start to receive the file!");   
			while (true) {   
				int read = 0;   
				if (dis!=  null) {   
					read = dis.read(buf);   
				}   
				passedlen +=  read;   
				if (read == -1) {   
					break;   
				}	   
//            	System.out.println("File received" + (passedlen * 100 / len) + "%");   
				fileOut.write(buf, 0, read);   
			}   
			System.out.println("Finish received:" + savePath);    
			fileOut.close();   
        } catch (Exception e) {   
				e.printStackTrace();   
				return;   
        }   
    } 
	
   public void sendFile00(String filePath,Socket socket){  
        DataOutputStream dos = null;  
        DataInputStream dis = null;  
        try {  
            File file = new File(filePath);  
            dos = new DataOutputStream(socket.getOutputStream());  
            dis = new DataInputStream(new BufferedInputStream(new FileInputStream(filePath)));  
              
            int buffferSize = 1024*100;  
            byte[]bufArray = new byte[buffferSize];  
            dos.writeUTF(file.getName());   
            dos.flush();   
            dos.writeLong((long) file.length());   
            dos.flush();   
            while (true) {   
                int read = 0;   
                if (dis!=  null) {   
                  read = dis.read(bufArray);   
                }   
  
                if (read == -1) {   
                  break;   
                }   
                dos.write(bufArray, 0, read);   
            }   
            dos.flush();
			socket.close();	
        } catch (FileNotFoundException e) {  
            // TODO Auto-generated catch block  
        //    e.printStackTrace();  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
         //   e.printStackTrace();  
        }catch (Exception e) {  }
		finally {   
              // Close all connections   
              try {   
                if (dos != null)   
                  dos.close();   
              } catch (IOException e) {   
              }   
              try {   
                if (dis != null)   
                  dis.close();   
              } catch (IOException e) {   
              }
              try {   
                if (socket != null)   
                  socket.close();   
              } catch (IOException e) {   
              } 			  
            }   
    } 
	
    public void receiveFile00(String savePath,Socket socket){    
        DataInputStream dis = null;  
        try {   
            dis = new DataInputStream(new BufferedInputStream(socket   
                .getInputStream()));   
          } catch (IOException e1) {   
            e1.printStackTrace();   
          }   
        int bufferSize = 1024;   
        // bufferzone   
        byte[] buf = new byte[bufferSize];   
        int passedlen = 0;   
        long len = 0;   
        // Get file name   
		try{  
			savePath +=  dis.readUTF();   
			DataOutputStream fileOut = new DataOutputStream(   
            new BufferedOutputStream(new BufferedOutputStream(   
            new FileOutputStream(savePath))));   
			len = dis.readLong();   
			//System.out.println("Length of the file:" + len + "    KB");   
			//System.out.println("Start to receive the file!");   
			while (true) {   
				int read = 0;   
				if (dis!=  null) {   
					read = dis.read(buf);   
				}   
				passedlen +=  read;   
				if (read == -1) {   
					break;   
				}	   
//            	System.out.println("File received" + (passedlen * 100 / len) + "%");   
				fileOut.write(buf, 0, read);   
			}   
			System.out.println("Finish received:" + savePath);    
			fileOut.close();   
        } catch (Exception e) {   
				e.printStackTrace();   
				return;   
        }   
    } 
	
    public String receivemsg(String ip,int port){  
        Socket socket = null;  
        try {   
            socket = new Socket(ip,port);   
          } catch (UnknownHostException e1) {   
            e1.printStackTrace();   
          } catch (IOException e1) {   
            e1.printStackTrace();   
          }   
        DataInputStream dis = null;  
        try {   
            dis = new DataInputStream(new BufferedInputStream(socket   
                .getInputStream()));   
          } catch (IOException e1) {   
            e1.printStackTrace();   
          }   
  
		try{  
			String msg = dis.readUTF(); 
			return msg;			
        } catch (Exception e) {   
				e.printStackTrace();   
				return "";   
        }

    } 
	
    public static void mergeFiles0(List<File> files, File into)throws IOException {
		try (BufferedOutputStream mergingStream = new BufferedOutputStream(
            new FileOutputStream(into))) {
			for (File f : files) {
				Files.copy(f.toPath(), mergingStream);
			}
		}
    }

    public static List<File> listOfFilesToMerge(File oneOfFiles){
        String tmpName = oneOfFiles.getName();//name.number
        String destFileName = tmpName.substring(0, tmpName.lastIndexOf('.'));//remove .{number}
        File[] files = oneOfFiles.getParentFile().listFiles(
            (File dir, String name) -> name.matches(destFileName + "[.]\\d+"));
        Arrays.sort(files);//ensuring order 001, 002, ..., 010, ...
        return Arrays.asList(files);
    }	
	
	public static void mergeFiles(File oneOfFiles, File into)throws IOException {
		mergeFiles0(listOfFilesToMerge(oneOfFiles), into);
	}
	
    public static void main(String[] args)throws IOException {
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
          String str00 = scanner.next();
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
		
		
      String numofile = new Client2().receivemsg("localhost", Integer.parseInt(serverport)+2);
      
      String numfromserver = new Client2().receivemsg("localhost", Integer.parseInt(serverport)+12);
      String Filename = new Client2().receivemsg("localhost", Integer.parseInt(serverport)+22);
      Path currentRelativePath = Paths.get("");
      dir = currentRelativePath.toAbsolutePath().toString();
      dir = removeLastChar(dir);		
      //System.out.println(numofile);	
      //System.out.println(numfromserver);	
      //System.out.println(Filename);
      int nfile = Integer.parseInt(numofile);
      for(int i = 1; i< = Integer.parseInt(numfromserver); i++){		
        new Client2().receiveFile(dir+"/client2/", "localhost", Integer.parseInt(serverport)+32);		
      }
      Thread snd = new Thread(new MyRunnable_snd(2,Integer.parseInt(c2to),Filename,nfile,Integer.parseInt(clinetport2)));
      Thread rec = new Thread(new MyRunnable_rec(c2from,Filename,nfile,Integer.parseInt(recport2)));
      snd.start();		
      rec.start();
      
      //File file0 = new File("C:/Users/Ultra-seven/Desktop/Java/client2/a.pdf.001");
      //File file1 = new File("C:/Users/Ultra-seven/Desktop/Java/client2/a.pdf");
      //new Client2().mergeFiles(file0, file1);
  }  

	public static class MyRunnable_snd implements Runnable {
		private int numofile;
		private int portmsg;
		private int no;
		private int no_target;
		private String Filename;
		public MyRunnable_snd(int no,int no_target,String Filename,int numofile,int portmsg) {
			this.numofile = numofile;
			this.portmsg = portmsg;
			this.no = no;
			this.no_target = no_target;
			this.Filename = Filename;
		}

		public void run() {

			int rest_snd = 10;
			while(rest_snd!= 0){
				try{

				rest_snd = 0;
				for(int j = 1;j< = numofile;j++){
					Path path1 = Paths.get(dir+"/client2/"+Filename+"."+String.format("%03d",j));				
					String no_t = no_target+"";
					Path path2 = Paths.get(dir+"/client"+no_t+"/"+Filename+"."+String.format("%03d",j));
					if(Files.notExists(path2)) rest_snd = rest_snd+1;
					if(Files.exists(path1)){
						if(Files.notExists(path2)){

							Socket sock = new Socket("localhost",portmsg);
//							System.out.println(j);
							try{
							new Client2().sendFile00(dir+"/client2/"+Filename+"."+String.format("%03d",j),sock);
							System.out.println("Try to send piece "+String.format("%03d",j)+" to Client"+no_t);
							}catch(Exception e) {}	
							sock.close();
														
						}
					}
				}
				
				}catch(Exception e) {}			
			}
		}
   }
	public static class MyRunnable_rec implements Runnable {
		private int portmsg;
		private int numofile;
		private String Filename;
		private String from;
		public MyRunnable_rec(String from,String Filename,int numofile,int portmsg) {
			this.portmsg = portmsg;
			this.Filename = Filename;
			this.numofile = numofile;
			this.from = from;
		}

		public void run() {

			int rest_rec = numofile;
			for(int j = 1;j< = numofile;j++){
				Path path1 = Paths.get(dir+"/client2/"+Filename+"."+String.format("%03d",j));				
				if(Files.exists(path1)) {
					rest_rec = rest_rec-1;	
				}		
			}	
			while(rest_rec!= 0){
				try{
					ServerSocket serverSock = new ServerSocket(portmsg);
					System.out.println("Listening to Client"+from);
					Socket clientSock = serverSock.accept();
//					System.out.println("Connected client");	
					new Client2().receiveFile00(dir+"/client2/",clientSock);	
//					System.out.println("received");	
					serverSock.close();
					clientSock.close();
				}catch(Exception e) {e.printStackTrace();}	
				rest_rec = 0;
				for(int j = 1;j< = numofile;j++){
					Path path1 = Paths.get(dir+"/client2/"+Filename+"."+String.format("%03d",j));				
					if(Files.notExists(path1)) rest_rec = rest_rec+1;	
				}
			}
			while(rest_rec == 0){
				try{
					System.out.println("All pieces obtained");	
					File file0 = new File(dir+"/client2/"+Filename+".002");
					File file1 = new File(dir+"/client2/"+Filename);
					new Client2().mergeFiles(file0, file1);
					System.out.println("Merge finished");
					rest_rec = rest_rec-1;
				}catch(Exception e) {e.printStackTrace();}						
			}
		}
	}
	private static String removeLastChar(String str) {
        return str.substring(0,str.length()-8);
    }
}