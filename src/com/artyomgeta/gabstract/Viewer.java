package com.artyomgeta.gabstract;

import javax.swing.*;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

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

    private String  abstractName = "Test";
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
        this.setBounds(new Rectangle(0,0, Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setContentPane(panel1);
        this.setUI();
    }

    private void setUI() {
        editorPane1.setEditable(false);
        this.setContent();

    }

    private void setContent() {
        HTMLEditorKit kit = new HTMLEditorKit();
        editorPane1.setEditorKit(kit);
        StyleSheet styleSheet = kit.getStyleSheet();
        try {
            styleSheet.importStyleSheet(new URL("file:///home/artyom/IdeaProjects/GAbstract/Abstracts/Test/styles/style.css"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        String html = "";
        try {
            StringBuilder sb = new StringBuilder();
            BufferedReader br = new BufferedReader(new FileReader("Abstracts/Test/index.html"));
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

        for (int i = 0; i < slidesLength; i++) {
            if (!slides[i].equals("styles")) {
                defaultListModel.addElement(slides[i].replace(".html", ""));
            }
        }
        list1.setModel(defaultListModel);
    }



}
