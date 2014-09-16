package com.nutty.EPQ.app;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import android.os.AsyncTask;

public class AsyncTaskRunner extends AsyncTask<String, String, String> {

	/*
	 * this is the AsyncTaskRunner for the network operations
	 * 
	 * @author Tom Hazell (C) 2014 Tom Hazell 
	 * 
	 * (non-Javadoc)
	 * @see android.os.AsyncTask#doInBackground(java.lang.Object[])
	 */

	@Override
	protected String doInBackground(String... params) {
		// define veriables including the bytearrays for the data to be sent and
		// recived in, the Socket and the InetAddres
		Boolean keepgoing = true;
		String hostname = params[0];
		String Message;

		byte[] sendData0 = new byte[4];
		byte[] sendData1 = new byte[4];
		byte[] sendData2 = new byte[4];
		byte[] reciveData0 = new byte[4];

		DatagramSocket datagramSocket = null;
		InetAddress receiverAddress = null;

		publishProgress("", "3");// clears the error (if there was one)
		publishProgress("Pleas wait while a connection is made and tested", "1");// sets
																					// the
																					// instructions
																					// field
		try {
			datagramSocket = new DatagramSocket();// will bind the sockets to
													// any port on the defult
													// adapter
			receiverAddress = InetAddress.getByName(hostname); // turns the
																// paramiter
																// passed in
																// into an
																// InetAddress(IP
																// adres object)
			Message = "Connected";

		} catch (SocketException e) {
			e.printStackTrace();
			Message = "SocketException: " + e.toString();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			Message = "UnknownHostException: " + e.toString();
		}

		// the message will be conected if there where no errors. if there are
		// it will quit the runner and return the message to onpost exicute
		if (Message != "Connected") {
			return Message;
		}

		publishProgress("Testing Connection", "0");// if there is no server this
													// can take up to 4s to
													// check the connecton so
													// notify the user

		int count = 64;// defines the counter so that on first loop it will
						// check the connection
		while (keepgoing) {
			if (MainActivity.command == 1) {
				if (count == 64) {// the counter will increment 32 times a
									// second so0 this will check the connection
									// once every 2 seconds
					count = 0;// reset the count
					int sendint = 6;// the value to send
					int retry = 0;// the counter that will increment with every
									// failed atempt to contact server
					boolean loop = true;

					// this will make the data to send and the packet to send it
					// in
					sendData2 = Integer.toString(sendint).getBytes();
					DatagramPacket packet2 = new DatagramPacket(sendData2,
							sendData2.length, receiverAddress, 40506);

					while (loop) {
						loop = false;// this means that unless told to continue
										// looping(loop = true before the end of
										// the loop) it will stop at the end
						try {
							datagramSocket.send(packet2);// atempt to send the
															// packet to the
															// server
						} catch (IOException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
							loop = true;
						}

						DatagramPacket receivePacket = new DatagramPacket(
								reciveData0, reciveData0.length);

						try {
							// if there was alredy an erro set the time out to
							// 1ms so it will instantly finish
							if (loop = true) {
								datagramSocket.setSoTimeout(1);
							} else {
								datagramSocket.setSoTimeout(1000);
							}
						} catch (SocketException e1) {
							e1.printStackTrace();
						}

						try {
							// try to recive a packet with the time out liseted
							// abuve
							datagramSocket.receive(receivePacket);

						} catch (SocketTimeoutException e) {
							// if it times out it will go here
							retry = retry + 1;// increments the rety counter
							loop = true;// so it will continue looping
							if (retry > 3) {// if it failes 4 times it will
											// close the socket then pass thru
											// the error to onpostexicute
								datagramSocket.close();
								MainActivity.running = false;
								return "server dose not appere to exist...";
							}

						} catch (IOException e) {
							e.printStackTrace();
							publishProgress("ERROR: " + e.toString(), "2");
						}
					}
					publishProgress("Connected", "0");
					publishProgress(
							"Hold the buttons down to move, if you want to quit tap the dissconect button",
							"1");
				}
				// make an array to store the values in before sending
				int[] Arrayout1 = { MainActivity.command, MainActivity.gofront,
						MainActivity.goside };

				// make the datat to be sent
				sendData0 = Integer.toString(Arrayout1[1]).getBytes();
				sendData1 = Integer.toString(Arrayout1[2] + 3).getBytes();

				// make the packets to send the data in
				DatagramPacket packet0 = new DatagramPacket(sendData0,
						sendData0.length, receiverAddress, 40506);
				DatagramPacket packet1 = new DatagramPacket(sendData1,
						sendData1.length, receiverAddress, 40506);

				try {
					// send the packets
					datagramSocket.send(packet0);
					datagramSocket.send(packet1);
				} catch (IOException e) {
					e.printStackTrace();
					publishProgress("error: " + e.toString(), "2");
					keepgoing = false;
					// if error then show and brake loop
				}

			} else {
				keepgoing = false;
			}

			try {
				Thread.sleep((1 / 32) * 1000);// wait 1/32nd of a sencond as to
												// not use all the cpu not
												// overload the network
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			count = count + 1;// increment count
		}
		datagramSocket.close();
		publishProgress("Dissconected", "0");
		MainActivity.running = false;
		// when keepgoing = false then close the socket and tell the
		// mainactivity that it has stoped
		return null;
	}

	protected void onPostExecute(String result) {
		// this will run when "return result"
		publishProgress(result, "3");
		publishProgress("Tap To Reconect", "2");
		publishProgress(
				"Tap the button abuve To Start sending instructions to the IP specifed.",
				"1");
	}

	@Override
	protected void onPreExecute() {
		// Things to be done before execution of long running operation. not
		// used
	}

	@Override
	protected void onProgressUpdate(String... arg) {
		// this runs when publishProgress() is run. the first paramiter is the
		// text and the sencond is the position.
		if (arg[1] == "0") {
			MainActivity.Status.setText(arg[0]);
		} else if (arg[1] == "1") {
			MainActivity.instructions.setText(arg[0]);
		} else if (arg[1] == "2") {
			MainActivity.connect.setText(arg[0]);
		} else if (arg[1] == "3") {
			MainActivity.info.setText(arg[0]);
		}
	}
}