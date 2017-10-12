package jiwang;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JSeparator;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

//import netcap.*;
import jpcap.*;
import jpcap.packet.*;
import java.util.*;
import java.sql.Timestamp;

/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI
 * Builder, which is free for non-commercial use. If Jigloo is being used
 * commercially (ie, by a corporation, company or business for any purpose
 * whatever) then you should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details. Use of Jigloo implies
 * acceptance of these licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN
 * PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR
 * ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
public class JFrameMain extends javax.swing.JFrame implements ActionListener {

	private JMenuItem exitMenuItem;
	private JSeparator jSeparator2;

	private JMenuItem stopMenuItem;
	private JMenuItem startMenuItem;
	private JMenu Menu;
	private JMenuBar jMenuBar1;
	//10-10new menu
	private JMenuItem newSave;
	private JMenuItem newAnalysis;
	private JMenuItem newChart;
	//end
	
	JTable tabledisplay = null;
	Vector rows, columns;
	DefaultTableModel tabModel;
	JScrollPane scrollPane;
	JLabel statusLabel;

	Netcaptor captor = new Netcaptor();

	/**
	 * Auto-generated main method to display this JFrame
	 */
	public static void main(String[] args) {
		JFrameMain inst = new JFrameMain();
		inst.setVisible(true);
	}

	public JFrameMain() {
		super();
		initGUI();
		this.setLocationRelativeTo(null);
	}

	private void initGUI() {
		try {
			setSize(800, 600);
			{
				jMenuBar1 = new JMenuBar();
				setJMenuBar(jMenuBar1);
				{
				
					//设置菜单
					Menu = new JMenu();
					jMenuBar1.add(Menu);
					Menu.setText("菜单");
					Menu.setPreferredSize(new java.awt.Dimension(35, 21));
					{
						startMenuItem = new JMenuItem();
						Menu.add(startMenuItem);
						startMenuItem.setText("开始");
						startMenuItem.setActionCommand("start");
						startMenuItem.addActionListener(this);
					}
					{
						stopMenuItem = new JMenuItem();
						Menu.add(stopMenuItem);
						stopMenuItem.setText("停止");
						stopMenuItem.setActionCommand("stop");
						stopMenuItem.addActionListener(this);
					}
					{
						newAnalysis = new JMenuItem();
						Menu.add(newAnalysis);
						newAnalysis.setText("结果统计");
						newAnalysis.setActionCommand("newanalysis");
						newAnalysis.addActionListener(this);
					}
					//10-10 new add.
					{
						newChart=new JMenuItem();
						Menu.add(newChart);
						newChart.setText("图表显示");
						newChart.setActionCommand("newchart");
						newChart.addActionListener(this);
					}
					{
						newSave=new JMenuItem();
						Menu.add(newSave);
						newSave.setText("结果保存");
						newSave.setActionCommand("save");
						newSave.addActionListener(this);
					}
					{
						jSeparator2 = new JSeparator();
						Menu.add(jSeparator2);
					}
					{
						exitMenuItem = new JMenuItem();
						Menu.add(exitMenuItem);
						exitMenuItem.setText("Exit");
						exitMenuItem.setActionCommand("exit");
						exitMenuItem.addActionListener(this);
					}
					
				}
			}

			rows = new Vector();
			columns = new Vector();

			columns.addElement("数据报时间");
			columns.addElement("源IP地址");
			columns.addElement("目的IP地址");
			columns.addElement("首部长度");
			columns.addElement("数据长度");
			columns.addElement("是否分段");
			columns.addElement("分段偏移量");
			columns.addElement("首部内容");
			columns.addElement("数据内容");

			tabModel = new DefaultTableModel();
			tabModel.setDataVector(rows, columns);
			tabledisplay = new JTable(tabModel);
//			scrollPane = new JScrollPane(tabledisplay);

			statusLabel = new JLabel("");
			this.getContentPane().add(statusLabel, BorderLayout.SOUTH);
			{
				getContentPane().add(new JScrollPane(tabledisplay), BorderLayout.CENTER);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void actionPerformed(ActionEvent event) {
		String cmd = event.getActionCommand();
//		System.out.println(cmd);
		if (cmd.equals("start")) {
			captor.capturePacketsFromDevice();
			captor.setJFrame(this);
		} else if (cmd.equals("stop")) {
			captor.stopCapture();
		} else if (cmd.equals("exit")) {
			System.exit(0);
		}else if(cmd.equals("newanalysis")) {
			new newCount(this).setVisible(true);
		}else if(cmd.equals("save")) {
			//保存
			String str="tcp:"+newCount.ctcp+"	udp:"+newCount.cudp+"	icmp:"+newCount.cicmp+
					"	arp:"+newCount.carp+"	广播包:"+newCount.cGuangBo;
			SaveFile sf = new SaveFile();
			sf.saveFile(this, str);
		}else if(cmd.equals("newchart")) {
			new  newChart1(this).setVisible(true);
		}
	}

	@SuppressWarnings("unchecked")
	public void dealPacket(Packet packet) {
		try {
			Vector r = new Vector();
			String strtmp;
			Timestamp timestamp = new Timestamp((packet.sec * 1000) + (packet.usec / 1000));

			r.addElement(timestamp.toString()); // 数据报时间
			r.addElement(((IPPacket) packet).src_ip.toString()); // 源IP地址
			r.addElement(((IPPacket) packet).dst_ip.toString()); // 目的IP地址
			if(((IPPacket) packet).dst_ip.toString().equals("255.255.255.255")) {
				newCount.cGuangBo++;
				newCount.dGuangBo+=(double)packet.len/1024;
			}
			r.addElement(packet.header.length); // 首部长度
			r.addElement(packet.data.length); // 数据长度
			r.addElement(((IPPacket) packet).dont_frag == true ? "分段" : "不分段"); // 是否不分段
			r.addElement(((IPPacket) packet).offset); // 数据长度

			strtmp = "";
			for (int i = 0; i < packet.header.length; i++) {
				strtmp += Byte.toString(packet.header[i]);
			}
			r.addElement(strtmp); // 首部内容

			strtmp = "";
			for (int i = 0; i < packet.data.length; i++) {
				strtmp += Byte.toString(packet.data[i]);
			}
			r.addElement(strtmp); // 数据内容

			rows.addElement(r);
			tabledisplay.addNotify();
		} catch (Exception e) {

		}
	}
}