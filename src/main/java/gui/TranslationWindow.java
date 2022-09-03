package gui;

import model.Passage;
import model.TwineJson;
import util.JsonProcessor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class TranslationWindow extends JFrame {

    JTextArea sourceArea;
    JTextArea translatedArea;
    JPanel mainPanel;
    JPanel textPanel;
    JPanel buttonPanel;
    JPanel sourcePanel;
    JPanel translatedPanel;
    BoxLayout mainLayout;
    BoxLayout textLayout;
    BoxLayout buttonLayout;
    BoxLayout sourceLayout;
    BoxLayout translatedLayout;
    JButton openButton;
    JButton saveButton;
    JButton prevButton;
    JButton nextButton;
    JLabel sourceLabel;
    JLabel translatedLabel;
    JFileChooser fileChooser;

    TwineJson sourceJson;
    TwineJson translatedJson;
    int currentPassage = 0;
    int currentLink = -1;

    public TranslationWindow() {
        super("TranslationTool");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        sourceArea = new JTextArea();
        sourceArea.setLineWrap(true);
        sourceArea.setEditable(false);
        translatedArea = new JTextArea();
        translatedArea.setLineWrap(true);
        fileChooser = new JFileChooser();

        mainPanel = new JPanel();
        textPanel = new JPanel();
        buttonPanel = new JPanel();
        sourcePanel = new JPanel();
        translatedPanel = new JPanel();

        sourceLabel = new JLabel("Source:");
        translatedLabel = new JLabel("Translation:");

        textLayout = new BoxLayout(textPanel, BoxLayout.X_AXIS);
        buttonLayout = new BoxLayout(buttonPanel, BoxLayout.X_AXIS);
        mainLayout = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
        sourceLayout = new BoxLayout(sourcePanel, BoxLayout.Y_AXIS);
        translatedLayout = new BoxLayout(translatedPanel, BoxLayout.Y_AXIS);

        openButton = new JButton("Open");
        openButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int res = fileChooser.showOpenDialog(TranslationWindow.this);
                if(res == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    try {
                        sourceJson = JsonProcessor.load(file);
                        translatedJson = JsonProcessor.load(file);

                        sourceArea.setText(sourceJson.getPassages().get(currentPassage).getText().split("\\[")[0]);
                        translatedArea.setText(translatedJson.getPassages().get(currentPassage).getText());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int res = fileChooser.showSaveDialog(TranslationWindow.this);
                if(res == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    try {
                        JsonProcessor.save(translatedJson, file);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        prevButton = new JButton("Prev");
        prevButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(currentLinkIsNotFirst()) {
                    storeLinkTranslation();
                    currentLink--;
                    setTextFromCurrentLinks();
                } else if(currentLinkIsFirst()) {
                    storeLinkTranslation();
                    currentLink--;
                    setTextFromPassage();
                } else if(passageIsNotFirst()) {
                    storePassageTranslation();
                    currentPassage--;
                    setTextFromLinkBeforePassage();
                }
            }
        });

        nextButton = new JButton("Next");
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(atPassage()) {
                    storePassageTranslation();
                    currentLink++;
                    setTextFromCurrentLinks();
                } else if(currentLinkIsNotLast()) {
                    storeLinkTranslation();
                    currentLink++;
                    setTextFromCurrentLinks();
                } else if(currentPassageIsNotLast()) {
                    storeLinkTranslation();
                    currentPassage++;
                    currentLink = -1;
                    setTextFromPassage();
                }
            }
        });

        sourcePanel.setLayout(sourceLayout);
        sourcePanel.add(sourceLabel);
        sourcePanel.add(new JScrollPane(sourceArea));

        translatedPanel.setLayout(translatedLayout);
        translatedPanel.add(translatedLabel);
        translatedPanel.add(new JScrollPane(translatedArea));

        buttonPanel.setLayout(buttonLayout);
        buttonPanel.add(openButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(prevButton);
        buttonPanel.add(nextButton);

        textPanel.setLayout(textLayout);
        textPanel.add(sourcePanel);
        textPanel.add(translatedPanel);

        mainPanel.setLayout(mainLayout);
        mainPanel.add(textPanel);
        mainPanel.add(buttonPanel);

        setContentPane(mainPanel);
        setSize(800, 600);
        setVisible(true);
    }

    private boolean passageIsNotFirst() {
        return currentPassage != 0;
    }

    private boolean currentLinkIsNotFirst() {
        return currentLink > 0;
    }

    private boolean currentLinkIsFirst() {
        return currentLink == 0;
    }

    private boolean currentLinkIsNotLast() {
        return currentLink < sourceJson.getPassages().get(currentPassage).getLinks().size() - 1;
    }

    private boolean currentPassageIsNotLast() {
        return currentPassage != (sourceJson.getPassages().size() - 1);
    }

    private boolean atPassage() {
        return currentLink == -1;
    }

    private void storeLinkTranslation() {
        String trText = translatedArea.getText();
        Passage tCP = translatedJson.getPassages().get(currentPassage);
        Passage sCP = sourceJson.getPassages().get(currentPassage);
        tCP.getLinks().get(currentLink).setName(trText);
    }

    private void setTextFromCurrentLinks() {
        Passage tCP = translatedJson.getPassages().get(currentPassage);
        Passage sCP = sourceJson.getPassages().get(currentPassage);
        sourceArea.setText(sCP.getLinks().get(currentLink).getName());
        translatedArea.setText(tCP.getLinks().get(currentLink).getName());
    }

    private void setTextFromPassage() {
        sourceArea.setText(sourceJson.getPassages().get(currentPassage).getText().split("\\[")[0]);
        translatedArea.setText(translatedJson.getPassages().get(currentPassage).getText());
    }

    private void storePassageTranslation() {
        String trText = translatedArea.getText();
        translatedJson.getPassages().get(currentPassage).setText(trText);
    }

    private void setTextFromLinkBeforePassage() {
        Passage tCP = translatedJson.getPassages().get(currentPassage);
        Passage sCP = sourceJson.getPassages().get(currentPassage);
        currentLink = tCP.getLinks().size() - 1;
        sourceArea.setText(sCP.getLinks().get(currentLink).getName());
        translatedArea.setText(tCP.getLinks().get(currentLink).getName());
    }
}
