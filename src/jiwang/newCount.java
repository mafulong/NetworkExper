package jiwang;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;



/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/


@SuppressWarnings("serial")
public class newCount extends JDialog{
	//test
//	public static void main(String[] args)throws Exception {
//		JFrame frame = new JFrame();
//		newCount inst=new newCount(frame);
//		inst.setVisible(true);
//	}
	
	static int ctcp=0;
	static int cudp=0;
	static int cicmp=0;
	static int carp=0;
	static int cGuangBo=0;
	
	static double dtcp=0;
	static double dudp=0;
	static double dicmp=0;
	static double darp=0;
	static double dGuangBo=0;
	
	private JLabel jLabel1;
	private JTable jTable1;

	public newCount(JFrame frame) {
		super(frame, "统计", true);
		initGUI();
		this.setLocationRelativeTo(null);
		try {
			
		}catch(Exception e) {
			
		}
	}
	
	private void initGUI() {
		try {
			{
				getContentPane().setLayout(null);
				this.setName("no");
				this.setSize(406, 332);
				{
					TableModel jTable1Model = 
							new DefaultTableModel(
									new String[][] { {"类型","数量","流量(kb)"},
										{"TCP",String.valueOf(ctcp),String.valueOf(dtcp) },
										{"UDP",String.valueOf(cudp),String.valueOf(dudp) },
										{"ARP",String.valueOf(carp) ,String.valueOf(darp)},
										{"ICMP",String.valueOf(cicmp),String.valueOf(dicmp)},
										{"广播包",String.valueOf(cGuangBo),String.valueOf(dGuangBo)}
									},
									new String[] { "Column 1", "Column 2","Column 3" });
					jTable1 = new JTable();
					getContentPane().add(jTable1);
					jTable1.setModel(jTable1Model);
					jTable1.setBounds(19, 61, 359, 141);
					jTable1.setFont(new java.awt.Font("Microsoft YaHei UI",0,16));
					jTable1.setRowHeight(22);
				}
				{
					jLabel1 = new JLabel();
					getContentPane().add(jLabel1);
					jLabel1.setText("\u6570\u636e\u5305\u6570\u91cf\u7edf\u8ba1\u7ed3\u679c");
					jLabel1.setBounds(125, 12, 147, 43);
					jLabel1.setFont(new java.awt.Font("Microsoft YaHei UI",0,16));
				}

			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
