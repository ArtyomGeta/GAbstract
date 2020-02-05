package com.artyomgeta.gabstract;

import javax.swing.*;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Objects;

import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

@SuppressWarnings("rawtypes")
public class Viewer extends JFrame {
    private JPanel panel1;
    private JList<String> list1;
    private JEditorPane editorPane1;
    private JButton fullScreenButton;
    private JButton saveButton;
    private JButton addButton;
    private JButton removeButton;
    private JButton editButton;
    private JButton disableButton;
    private JComboBox comboBox1;
    private JButton playButton;
    private JButton stopButton;
    private JComboBox comboBox2;
    private JButton playButton1;
    private JButton listVisibilityButton;
    private JPanel listPanel;

    private String abstractName = "Test";
    private int slidesLength = GAbstract.returnSlidesLength(abstractName);
    private String[] slides = GAbstract.returnSlides(abstractName);
    private DefaultListModel<String> defaultListModel = new DefaultListModel<String>();

    public Viewer(String abstractName) {
        System.out.println("Slides length: " + slidesLength);
        System.out.println("Slides: " + Arrays.toString(slides));

    }

    private String[] abstracts = GAbstract.returnSlides(abstractName);

    public void run() {
        this.setTitle("GAbstract");
        this.setBounds(new Rectangle(0, 0, Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setContentPane(panel1);
        this.setUI();
    }

    private void setUI() {
        editorPane1.setEditable(false);
        this.setContent(0);
        this.list1.addListSelectionListener(e -> {
            this.setContent(list1.getSelectedIndex());
        });
        this.listVisibilityButton.addActionListener(e -> {
            if (listPanel.isVisible()) {
                listPanel.setVisible(false);
                listVisibilityButton.setText("Show list");
            } else {
                listPanel.setVisible(true);
                listVisibilityButton.setText("Close list");
            }
        });

        final JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem menuItem2 = new JMenuItem("Edit Slide");
        popupMenu.add(menuItem2);
        popupMenu.add(new JPopupMenu.Separator());
        JMenuItem menuItem3 = new JMenuItem("Delete Slide");
        popupMenu.add(menuItem3);

        list1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                if (SwingUtilities.isRightMouseButton(me)) {
                    popupMenu.show(list1, me.getX(), me.getY());
                }
            }
        });


        this.setListData();
    }

    private void setContent(int slide) {
        HTMLEditorKit kit = new HTMLEditorKit();
        editorPane1.setEditorKit(kit);
        StyleSheet styleSheet = kit.getStyleSheet();
        try {
            styleSheet.importStyleSheet(new URL("file:///" + new File("Abstracts/" + abstractName + "/style.css").getAbsolutePath()));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        String html = "";
        try {
            StringBuilder sb = new StringBuilder();
            BufferedReader br;

            br = new BufferedReader(new FileReader("Abstracts/" + abstractName + "/slides/" + Objects.requireNonNull(new File("Abstracts/" + abstractName + "/slides").listFiles())[slide].getName()));

            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            br.close();
            html = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
            showMessageDialog(this, "Не найден файл!", "Ошибка", ERROR_MESSAGE);
        }

        Document doc = kit.createDefaultDocument();
        editorPane1.setDocument(doc);
        editorPane1.setText(html);

    }

    private void setListData() {
        for (int i = 0; i < slidesLength; i++) {
            defaultListModel.addElement(slides[i].replace(".html", "").replace("index", "Slide "));
        }
        list1.setModel(defaultListModel);
    }


}
