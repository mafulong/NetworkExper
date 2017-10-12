package jiwang;

import java.io.File;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import jpcap.JpcapCaptor;
import jpcap.PacketReceiver;
import jpcap.JpcapWriter;
import jpcap.packet.ARPPacket;
import jpcap.packet.ICMPPacket;
import jpcap.packet.IPPacket;
import jpcap.packet.Packet;
import jpcap.packet.TCPPacket;
import jpcap.packet.UDPPacket;

public class Netcaptor {

	JpcapCaptor jpcap = null;
	JFrameMain frame;

	/**
	 * @wbp.parser.entryPoint
	 */
	public void setJFrame(JFrameMain frame) {
		this.frame = frame;
	}

	public void capturePacketsFromDevice() {

		if (jpcap != null)
			jpcap.close();

		jpcap = Jcapturedialog.getJpcap(frame);

		if (jpcap != null) {
			startCaptureThread();
		}

	}

	private Thread captureThread;

	private void startCaptureThread() {

		if (captureThread != null)
			return;
		captureThread = new Thread(new Runnable() {
			public void run() {
				while (captureThread != null) {
					jpcap.processPacket(1, handler);
				}
			}
		});
		captureThread.setPriority(Thread.MIN_PRIORITY);
		captureThread.start();
	}

	void stopcaptureThread() {
		captureThread = null;
	}

	public void stopCapture() {
		System.out.println("已正确停止");
		stopcaptureThread();
	}

	//2017-10-10 new add.
	public void newdeal(Packet packet) {
		if(packet.getClass().equals(UDPPacket.class)) {
			newCount.ctcp++;
			newCount.dtcp+=(double)packet.len/1024;
		}
		else if(packet.getClass().equals(TCPPacket.class)) {
			newCount.cudp++;
			newCount.dudp+=(double)packet.len/1024;
		}
		else if(packet.getClass().equals(ARPPacket.class)) {
			newCount.carp++;
			newCount.darp+=(double)packet.len/1024;
		}
		else if(packet.getClass().equals(ICMPPacket.class)) {
			newCount.cicmp++;
			newCount.dicmp+=(double)packet.len/1024;
		}
		//
//		else if(((IPPacket) packet).dst_ip.toString().equals("255.255.255.255")) {
//			System.out.println("广播包");
//			newCount.cGuangBo++;
//		}
//		迁至JFrameMain.java中判断是否为广播包，通过地址判断
	}
	private PacketReceiver handler = new PacketReceiver() {
		public void receivePacket(Packet packet) {
			frame.dealPacket(packet);
			System.out.println(packet.toString());
			newdeal(packet);
		}

	};
}