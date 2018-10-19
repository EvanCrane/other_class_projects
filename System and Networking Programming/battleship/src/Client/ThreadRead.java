package Client;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ThreadRead extends Thread{

	private Game game;
	private Socket servSock;
	private DataInputStream inStream;
	private ClientDataMessage inMsg;
	
	public ThreadRead(Game g, Socket s) throws IOException
	{
		game = g;
		servSock = s;
		
		inStream = new DataInputStream(servSock.getInputStream());
	}
	
	public void run() {
		// TODO Auto-generated method stub
		String msg = "";
		while(true)
		{
			try {
				msg = inStream.readUTF();
					
				System.out.println(msg);
					game.handleMsg(msg);
				if(msg.charAt(0)=='5')
					break;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			inStream.close();
			servSock.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
