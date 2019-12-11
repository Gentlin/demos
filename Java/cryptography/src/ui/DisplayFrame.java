package ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.TextEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import ui.GBC;
import cryptography.*;
public class DisplayFrame extends JFrame{
	
	JLabel text1 = new JLabel("算法");
	JLabel text2 = new JLabel("密钥");
	String algorithm[]={"DES", "AES", "RSA"};        
	JComboBox<String> cb = new JComboBox<String>(algorithm); 
	
	JTextField tf_key = new JTextField(); 
	
	JTextArea jta1 = new JTextArea(16, 20);
	JScrollPane jsp1 = new JScrollPane(jta1);
	JTextArea jta2 = new JTextArea(16, 20);
	JScrollPane jsp2 = new JScrollPane(jta2);
	JButton btnEncrypt = new JButton("加密>>");
	JButton btnDecrypt = new JButton("解密<<");
	
	JLabel JLB_inputFile = new JLabel("输入文件");
	JLabel JLB_outputFile = new JLabel("输出文件");
	JTextField JTF_inputPath = new JTextField();
	JTextField JTF_outputPath = new JTextField();
	JButton btnSetInput = new JButton("...");
	JButton btnSetOutput = new JButton("...");
	JButton btnEncryptFile = new JButton("加密文件");
	JButton btnDecryptFile = new JButton("解密文件");
	JButton btnSetParm = new JButton("详细参数");
	int screenWidth = 800;
	int screenHeight = 500;
	
	enum AlgoType{
		DES, AES, RSA
	}
	private AlgoType type = AlgoType.DES;

	enum Mode {
		ECB, CBC, CFB, OFB, CTR;
	}
    private byte[] cipher;
    private byte[] plain;
    private byte[] fileCipher;
    private byte[] filePlain;
	private Mode mode = Mode.ECB;
	private SetDESParmDlg setDESParmDlg;
	private SetRSAParmDlg setRSAParmDlg;
	private RSA rsa = new RSA();
	private byte[] key = new byte[16];
	private byte[] IV  = new byte[8];
	private void setKey(String str) {
		byte[] bytes = str.getBytes();
		if(bytes.length >= 16) {
			for (int i = 0; i < 16; i++) {
				key[i] = bytes[i];
			}
		}
		else {
			for (int i = 0; i < bytes.length; i++) {
				key[i] = bytes[i];
			}
			for (int i = bytes.length; i < 16; i++) {
				key[i] = 0;			
			}
		}
	}
	private void setIV(String str) {
		byte[] bytes = str.getBytes();
		if(bytes.length >= 8) {
			for (int i = 0; i < 8; i++) {
				IV[i] = bytes[i];
			}
		}
		else {
			for (int i = 0; i < bytes.length; i++) {
				IV[i] = bytes[i];
			}
			for (int i = bytes.length; i < 8; i++) {
				IV[i] = 0;			
			}
		}
	}
    private static int charToNibble(char c) {
        if (c >= '0' && c <= '9') {
            return (c - '0');
        } else if (c >= 'a' && c <= 'f') {
            return (10 + c - 'a');
        } else if (c >= 'A' && c <= 'F') {
            return (10 + c - 'A');
        } else {
            return 0;
        }
    }

    private static byte[] hexStringToBytes(String s) {
        s = s.replace(" ", "");
        byte[] ba = new byte[s.length() / 2];
        if (s.length() % 2 > 0) {
            s = s + '0';
        }
        for (int i = 0; i < s.length(); i += 2) {
            ba[i / 2] = (byte) (charToNibble(s.charAt(i)) << 4 | charToNibble(s.charAt(i + 1)));
        }
        return ba;
    }

    private static String hex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            sb.append(String.format("%02X ", bytes[i]));
        }
        return sb.toString();
    }
    
	public DisplayFrame() {	
		this.setKey("password");
		this.setIV("password");

		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension d = tk.getScreenSize();
		//this.setSize(screenWidth, screenHeight);
	
		GridBagLayout gblayout = new GridBagLayout();		
		this.setLayout(gblayout);
		
		
		JPanel panel1 = new JPanel();
		panel1.add(text1);
		panel1.add(cb);
		cb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				switch (cb.getSelectedIndex()) {
				case 0:
					type = AlgoType.DES;
					break;
				case 1:
					type = AlgoType.AES;
					break;
				case 2:
					type = AlgoType.RSA;
					break;
				default:
					break;
				}
			}
		});
		panel1.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
		//panel1.setPreferredSize(new Dimension(screenWidth, screenHeight/8));
		
		this.add(panel1, new GBC(0, 0).setAnchor(GBC.WEST));
		
		JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
		panel2.add(text2);
		tf_key.setText(new String(key));
		tf_key.setColumns(16);
		tf_key.setAutoscrolls(false);
		tf_key.setToolTipText("只截取 16个字节，不足则补0");

		tf_key.getDocument().addDocumentListener(new DocumentListener() {	
		
			@Override
			public void changedUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				DisplayFrame.this.setKey(tf_key.getText());
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				DisplayFrame.this.setKey(tf_key.getText());
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				DisplayFrame.this.setKey(tf_key.getText());
			}
			
		});


		panel2.add(tf_key);
		//btnSetParm.setSize((int) (tf_key.getHeight() * 0.6), tf_key.getHeight());
		btnSetParm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(type == AlgoType.DES) {
					if(setDESParmDlg == null)setDESParmDlg = new SetDESParmDlg(DisplayFrame.this);
					setDESParmDlg.setVisible(true);
				} else if(type == AlgoType.AES) {
					JOptionPane.showMessageDialog(null, "AES没有更多参数可以设置！");
				} else if(type == AlgoType.RSA) {
					if(setRSAParmDlg == null) setRSAParmDlg = new SetRSAParmDlg(DisplayFrame.this);
					setRSAParmDlg.setVisible(true);
				}
			}		
		});
		panel2.add(btnSetParm);
		this.add(panel2, new GBC(0, 1).setAnchor(GBC.WEST));
		
		GridBagLayout gblayout2 = new GridBagLayout();
		JPanel panel3 = new JPanel();
		panel3.setLayout(gblayout2);
		jta1.setLineWrap(true);
		jta2.setLineWrap(true);
		panel3.add(jsp1, new GBC(0, 0, 3, 4).setAnchor(GBC.WEST));		
		panel3.add(btnEncrypt, new GBC(4, 0, 1, 2).setAnchor(GBC.CENTER));
		panel3.add(btnDecrypt, new GBC(4, 2, 1, 1).setAnchor(GBC.CENTER));
		panel3.add(jsp2, new GBC(5, 0, 3, 4).setAnchor(GBC.WEST));		
		this.add(panel3, new GBC(0, 2, 3, 1).setAnchor(GBC.CENTER));
		btnEncrypt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				plain = jta1.getText().getBytes();
				if(plain  == null || plain.length == 0)return;
				if(type == AlgoType.DES) {
					switch(mode) {
						case ECB: cipher = DES.encryptECB(plain, key); break;
						case CBC: cipher = DES.encryptCBC(plain, key); break;
						case CFB: cipher = DES.encryptCFB(plain, key); break;
						case CTR: cipher = DES.encryptCTR(plain, key, 0); break;
						case OFB: 
							long iv = DES.getLongFromBytes(IV, 0);
							cipher = DES.encryptOFB(plain, key, iv); 						
						break;
						default:
							 cipher = DES.encryptECB(plain, key);
					}
					jta2.setText(hex(cipher));
				} else if (type == AlgoType.AES) {
					cipher = AES.encrypt(plain, key);	
					jta2.setText(hex(cipher));			
				} else if (type == AlgoType.RSA) {
					ArrayList<BigInteger> cipher = rsa.encrypt(plain);
					StringBuilder result = new StringBuilder();
					for(BigInteger i:cipher) {
						result.append(i.toString()).append("\n");
					}
					jta2.setText(result.toString());
				}
				
			}
		});
		btnDecrypt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cipher = hexStringToBytes(jta2.getText());
				
				if(cipher == null || cipher.length == 0)return;
				if(type == AlgoType.DES) {
					switch(mode) {
						case ECB: plain = DES.decryptECB(cipher, key); break;
						case CBC: plain = DES.decryptCBC(cipher, key); break;
						case CFB: plain = DES.decryptCFB(cipher, key); break;
						case CTR: plain = DES.decryptCTR(cipher, key, 0); break;
						case OFB: 
							long iv = DES.getLongFromBytes(IV, 0);
							plain = DES.decryptOFB(cipher, key, iv); 						
						break;
						default:
							 plain = DES.decryptECB(cipher, key);
					}
					jta1.setText(new String(plain));
				} else if(type == AlgoType.AES) {
					plain = AES.decrypt(cipher, key);
					jta1.setText(new String(plain));
				} else if(type == AlgoType.RSA) {
					String[] rsaCipherStr = jta2.getText().split("\n");
					ArrayList<BigInteger> rsaCipher = new ArrayList<BigInteger>(); 
					for(String str : rsaCipherStr) {
						rsaCipher.add(new BigInteger(str));
					}
					byte[] rsaPlain = rsa.decrypt(rsaCipher);
					jta1.setText(new String(rsaPlain));
				}
				
			}
		});
		
		JPanel panel4 = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
		panel4.add(JLB_inputFile);
		JTF_inputPath.setColumns(16);
		panel4.add(JTF_inputPath);
		panel4.add(btnSetInput);
		btnSetInput.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				JFileChooser fileChooser = new JFileChooser(".");
				fileChooser.setMultiSelectionEnabled(false);
				
				fileChooser.showOpenDialog(DisplayFrame.this);
				
				File file = fileChooser.getSelectedFile();
				if(file != null)JTF_inputPath.setText(file.getPath());
				fileChooser.setVisible(true);
			}		
		});
		btnSetOutput.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				JFileChooser fileChooser = new JFileChooser(".");
				fileChooser.setMultiSelectionEnabled(false);
				fileChooser.showOpenDialog(DisplayFrame.this);
				File file = fileChooser.getSelectedFile();
				if(file != null)JTF_outputPath.setText(file.getPath());
				fileChooser.setVisible(true);
			}		
		});
		panel4.add(btnEncryptFile);
		btnEncryptFile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(type == AlgoType.RSA) {
					JOptionPane.showMessageDialog(null, "RSA模式不支持文件操作");
					return;
				}
				// TODO Auto-generated method stub
				Path inputPath = Paths.get(JTF_inputPath.getText());
				Path outputPath = Paths.get(JTF_outputPath.getText());
				if(!Files.exists(inputPath)) {
					JOptionPane.showMessageDialog(null, "请输入合法的输入路径");
					return;
				}
				if(!Files.exists(outputPath)) {
					JOptionPane.showMessageDialog(null, "请输入合法的输出路径");
					return;
				}
				try {
					filePlain = Files.readAllBytes(inputPath);
					if(filePlain  == null || filePlain.length == 0)return;
					if(type == AlgoType.DES) {
						switch(mode) {
							case ECB: fileCipher = DES.encryptECB(filePlain, key); break;
							case CBC: fileCipher = DES.encryptCBC(filePlain, key); break;
							case CFB: fileCipher = DES.encryptCFB(filePlain, key); break;
							case CTR: fileCipher = DES.encryptCTR(filePlain, key, 0); break;
							case OFB: 
								long iv = DES.getLongFromBytes(IV, 0);
								fileCipher = DES.encryptOFB(filePlain, key, iv); 						
							break;
							default:
								fileCipher = DES.encryptECB(filePlain, key);
						}
						Files.write(outputPath, fileCipher);
					} else if (type == AlgoType.AES) {
						fileCipher = AES.encrypt(filePlain, key);	
						Files.write(outputPath, fileCipher);			
					} else if (type == AlgoType.RSA) {
						ArrayList<BigInteger> cipher = rsa.encrypt(plain);
						StringBuilder result = new StringBuilder();
						for(BigInteger i:cipher) {
							result.append(i.toString()).append("\n");
						}
						Files.write(outputPath, result.toString().getBytes());	
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		});
		btnDecryptFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(type == AlgoType.RSA) {
					JOptionPane.showMessageDialog(null, "RSA模式不支持文件操作");
					return;
				}
				// TODO Auto-generated method stub
				Path inputPath = Paths.get(JTF_inputPath.getText());
				Path outputPath = Paths.get(JTF_outputPath.getText());
				if(!Files.exists(inputPath)) {
					JOptionPane.showMessageDialog(null, "请输入合法的输入路径");
					return;
				}
				if(!Files.exists(outputPath)) {
					JOptionPane.showMessageDialog(null, "请输入合法的输出路径");
					return;
				}
				try {
					fileCipher = Files.readAllBytes(inputPath);
					if(fileCipher == null || fileCipher.length == 0)return;
					if(type == AlgoType.DES) {
						switch(mode) {
							case ECB: filePlain = DES.decryptECB(fileCipher, key); break;
							case CBC: filePlain = DES.decryptCBC(fileCipher, key); break;
							case CFB: filePlain = DES.decryptCFB(fileCipher, key); break;
							case CTR: filePlain = DES.decryptCTR(fileCipher, key, 0); break;
							case OFB: 
								long iv = DES.getLongFromBytes(IV, 0);
								filePlain = DES.decryptOFB(fileCipher, key, iv); 						
							break;
							default:
								filePlain = DES.decryptECB(fileCipher, key);
						}
						Files.write(outputPath, filePlain);
					} else if(type == AlgoType.AES) {
						filePlain = AES.decrypt(fileCipher, key);
						Files.write(outputPath, filePlain);
					} else if(type == AlgoType.RSA) {
						String[] rsaCipherStr = jta2.getText().split("\n");
						ArrayList<BigInteger> rsaCipher = new ArrayList<BigInteger>(); 
						for(String str : rsaCipherStr) {
							rsaCipher.add(new BigInteger(str));
						}
						byte[] rsaFilePlain = rsa.decrypt(rsaCipher);
						Files.write(outputPath,rsaFilePlain);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		});
		this.add(panel4, new GBC(0, 6).setAnchor(GBC.WEST));
		
		JPanel panel5 = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
		panel5.add(JLB_outputFile);
		JTF_outputPath.setColumns(16);
		panel5.add(JTF_outputPath);
		panel5.add(btnSetOutput);
		panel5.add(btnDecryptFile);
		this.add(panel5, new GBC(0, 7).setAnchor(GBC.WEST));
		pack();
		this.setLocation(d.width / 2 - this.getWidth() / 2, d.height / 2 - this.getHeight() / 2 );	
	}
	private class SetDESParmDlg extends JDialog  {	
		public SetDESParmDlg(JFrame parent)  {
			super(parent, true);
			
			this.setTitle("DES参数设置");
			
			JRadioButton[] rbs = new JRadioButton[] {
					new JRadioButton("ECB"),
					new JRadioButton("CBC"),
					new JRadioButton("CFB"),
					new JRadioButton("OFB"),
					new JRadioButton("CTR")					
			};
			ButtonGroup bg = new ButtonGroup();
			switch(mode){
				case ECB: rbs[0].setSelected(true); break;
				case CBC: rbs[1].setSelected(true); break;
				case CFB: rbs[2].setSelected(true); break;
				case OFB: rbs[3].setSelected(true); break;
				case CTR: rbs[4].setSelected(true); break;
			}
			
			for (int i = 0; i < 5; i++) {
				bg.add(rbs[i]);
				rbs[i].setActionCommand(String.valueOf(i));
				final int ii = i;
				rbs[i].addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
					    switch(ii) {
					    	case 0: mode = Mode.ECB;break;
					    	case 1: mode = Mode.CBC;break;
					    	case 2: mode = Mode.CFB;break;
					    	case 3: mode = Mode.OFB;break;
					    	case 4: mode = Mode.CTR;break;
					    }
					}
				});
			}
			GridBagLayout gblayout = new GridBagLayout();		
			this.setLayout(gblayout);
			JPanel row1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
			for (int i = 0; i < 5; i++) {
				row1.add(rbs[i]);
			}
			JLabel label1 = new JLabel("IV");
			JPanel row2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
			row2.add(label1);
			final JTextField jtf = new JTextField();
		//	jtf.addInputMethodListener(new InputM);
			jtf.setText(new String(key));
			jtf.setColumns(16);
			row2.add(jtf);
			this.add(row1, new GBC(0, 0).setAnchor(GBC.WEST));
			this.add(row2, new GBC(0, 1).setAnchor(GBC.WEST));
			setResizable(false);		
			final JButton okButton = new JButton("确定");
			okButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String str = jtf.getText();
					setIV(str);
					SetDESParmDlg.this.setVisible(false);
				}
			});
			this.add(okButton, new GBC(0, 2).setAnchor(GBC.CENTER));
			pack();
			this.setDefaultCloseOperation(HIDE_ON_CLOSE);
			Toolkit tk = Toolkit.getDefaultToolkit();
			Dimension d = tk.getScreenSize();
			this.setModal(true);
			
			this.setLocation(d.width / 2 - this.getWidth()/2, d.height / 2 -  this.getHeight() /2);	
		}
	
	}
	
	private class SetRSAParmDlg extends JDialog{
		public SetRSAParmDlg(JFrame parent) {
			super(parent, true);
			this.setModal(true);
			
			GridBagLayout gblayout = new GridBagLayout();
			this.setLayout(gblayout);
			
			this.add(new Label("质数p"), new GBC(0, 0).setAnchor(GBC.CENTER));
			final int row = 8;
			final int col = 20;
			final JTextArea p = new JTextArea(row, col);
			p.setEditable(false);
			JScrollPane p1 = new JScrollPane(p);
		//	p1.setAutoscrolls(true);
			//p.setColumns(16);
			
			p.setLineWrap(true);
			p.setText(rsa.getPrimeP().toString());
			this.add(p1, new GBC(1, 0).setAnchor(GBC.WEST));
			
			this.add(new JLabel("质数q"), new GBC(0, 1).setAnchor(GBC.CENTER));
			final JTextArea q = new JTextArea(row, col);
			JScrollPane q1 = new JScrollPane(q);
		//	q1.setAutoscrolls(true);
			q.setLineWrap(true);
			q.setText(rsa.getPrimeQ().toString());
			q.setEditable(false);	
			this.add(q1, new GBC(1, 1).setAnchor(GBC.WEST));
			
			this.add(new JLabel("公钥"), new GBC(0, 2).setAnchor(GBC.CENTER));
			final JTextArea publicKey = new JTextArea(row, col);
			JScrollPane publicKey1 = new JScrollPane(publicKey);
		//	publicKey1.setAutoscrolls(true);
			publicKey.setLineWrap(true);
			publicKey.setEditable(false);
			publicKey.setText(rsa.getPublicKey().toString());		
			this.add(publicKey1, new GBC(1, 2).setAnchor(GBC.WEST));
			
			this.add(new JLabel("私钥"), new GBC(0, 3).setAnchor(GBC.CENTER));
			final JTextArea privateKey = new JTextArea(row, col);
			JScrollPane privateKey1 = new JScrollPane(privateKey);
		//	privateKey1.setAutoscrolls(true);
			privateKey.setLineWrap(true);
			privateKey.setEditable(false);
			privateKey.setText(rsa.getPrivateKey().toString());
			this.add(privateKey1, new GBC(1,3).setAnchor(GBC.WEST));
			
			JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
			JButton setKeyBtn = new JButton("随机生成");
			setKeyBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					rsa.generateKey();
					p.setText(rsa.getPrimeP().toString());
					q.setText(rsa.getPrimeQ().toString());
					publicKey.setText(rsa.getPublicKey().toString());
					privateKey.setText(rsa.getPrivateKey().toString());
				}
			});
			btnPanel.add(setKeyBtn);
			
			JButton okBtn = new JButton("确定");
			okBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					setRSAParmDlg.setVisible(false);
				}
			});
			btnPanel.add(okBtn);
			
			this.add(btnPanel, new GBC(0,4, 2, 1).setAnchor(GBC.WEST));
			pack();
			this.setResizable(false);
			this.setDefaultCloseOperation(HIDE_ON_CLOSE);
			Toolkit tk = Toolkit.getDefaultToolkit();
			Dimension d = tk.getScreenSize();	
			this.setLocation(d.width / 2 - this.getWidth()/2, d.height / 2 -  this.getHeight() /2);	
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				DisplayFrame display = new DisplayFrame();
				display.setVisible(true);
				display.setResizable(false);
				display.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}
		});
	}

}
