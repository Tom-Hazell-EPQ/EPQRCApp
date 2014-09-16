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

	@Override
	protected String doInBackground(String... params) {
		Boolean keepgoing = true;
		String hostname = params[0];
		String Message;

		int[] Arrayout = { 0, 0, 0 };
		byte[] sendData0 = new byte[4];
		byte[] sendData1 = new byte[4];
		byte[] sendData2 = new byte[4];
		byte[] reciveData0 = new byte[4];

		DatagramSocket datagramSocket = null;
		InetAddress receiverAddress = null;

		publishProgress("", "3");
		publishProgress("Pleas wait while a connection is made and tested", "1");
		try {
			datagramSocket = new DatagramSocket();
			receiverAddress = InetAddress.getByName(hostname);
			Message = "Connected";

		} catch (SocketException e) {
			e.printStackTrace();
			Message = "SocketException: " + e.toString();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			Message = "UnknownHostException: " + e.toString();
		}

		if (Message != "Connected") {
			return "Message";
		}
		
		publishProgress("Testing Connection", "0");
		
		int count = 32;
		while (keepgoing) {
			if (MainActivity.command == 1) {
				if (count == 32) {
					count = 0;
					int sendint = 6;
					int retry = 0;
					boolean loop = true;
					sendData2 = Integer.toString(sendint).getBytes();
					DatagramPacket packet2 = new DatagramPacket(sendData2,
							sendData2.length, receiverAddress, 40506);
					while (loop) {
						loop = false;
						try {
							datagramSocket.send(packet2);
						} catch (IOException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}

						DatagramPacket receivePacket = new DatagramPacket(
								reciveData0, reciveData0.length);

						try {
							datagramSocket.setSoTimeout(1000);
						} catch (SocketException e1) {
							e1.printStackTrace();
							System.out.println(e1.toString());
						}

						try {
							datagramSocket.receive(receivePacket);

						} catch (SocketTimeoutException e) {
							retry = retry + 1;
							loop = true;
							if (retry > 4) {
								publishProgress("ERROR: " + e.toString(), "2");
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
					publishProgress("Hold the buttons down to move, if you want to quit ", "1");
				}

				int[] Arrayout1 = { MainActivity.command, MainActivity.gofront,
						MainActivity.goside };

				sendData0 = Integer.toString(Arrayout1[1]).getBytes();
				sendData1 = Integer.toString(Arrayout1[2] + 3).getBytes();

				DatagramPacket packet0 = new DatagramPacket(sendData0,
						sendData0.length, receiverAddress, 40506);
				DatagramPacket packet1 = new DatagramPacket(sendData1,
						sendData1.length, receiverAddress, 40506);
				try {
					datagramSocket.send(packet0);
					datagramSocket.send(packet1);
				} catch (IOException e) {
					e.printStackTrace();
					publishProgress("error: " + e.toString(), "2");
					keepgoing = false;
				}

			} else {
				keepgoing = false;
			}

			try {
				Thread.sleep((1 / 32) * 1000); // (1 / 16) *
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			count = count + 1;
		}
		datagramSocket.close();
		publishProgress("Dissconected", "0");
		MainActivity.running = false;

		return null;
	}

	protected void onPostExecute(String result) {
		// execution of result of Long time consuming operation
		publishProgress(result, "3");
		publishProgress("Tap To Reconect", "2");
		publishProgress("Not Connected", "0");
		publishProgress("Tap the button abuve To Start sending instructions to the IP specifed.", "1");
	}

	@Override
	protected void onPreExecute() {
		// Things to be done before execution of long running operation. For
		// example showing ProgessDialog
	}

	@Override
	protected void onProgressUpdate(String... arg) {
		if (arg[1] == "0") {
			MainActivity.Status.setText(arg[0]);
		} else if (arg[1] == "1") {
			MainActivity.instructions.setText(arg[0]);
		} else if (arg[1] == "2") {
			MainActivity.connect.setText(arg[0]);
		} else if (arg[1] == "3"){
			MainActivity.info.setText(arg[0]);
		}
	}
}