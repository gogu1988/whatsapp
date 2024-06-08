package SendMessages;

import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;
import org.testng.annotations.Test;

import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.Scanner;

public class SendText {

    //https://web.whatsapp.com/send?phone=+919866840603

    @Test
    public void text() throws FindFailed, InterruptedException, IOException, AWTException {

        FileSystemView view = FileSystemView.getFileSystemView();
        File file1 = view.getHomeDirectory();
        String parentDirectory = file1.getPath();

        File contacts_file = new File(parentDirectory + "\\whatsapp_canvassing\\contacts.txt");

        BufferedReader contacts = new BufferedReader(new FileReader(contacts_file));

        BufferedWriter pass_writer = new BufferedWriter(new FileWriter(parentDirectory + "\\whatsapp_canvassing\\pass.txt", true));
        BufferedWriter fail_writer = new BufferedWriter(new FileWriter(parentDirectory + "\\whatsapp_canvassing\\fail.txt", true));
        BufferedWriter invalid_writer = new BufferedWriter(new FileWriter(parentDirectory + "\\whatsapp_canvassing\\invalid.txt", true));

        Screen screen = new Screen();

        Pattern BrowserURLPath = new Pattern(parentDirectory + "\\whatsapp_canvassing\\pics\\BrowserURLPath.png");
        Pattern typeMessage = new Pattern(parentDirectory + "\\whatsapp_canvassing\\pics\\TypeMessage.png");
        Pattern InvalidNumber = new Pattern(parentDirectory + "\\whatsapp_canvassing\\pics\\InvalidNumber.png");

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Clipboard clipboard = toolkit.getSystemClipboard();

        Robot robot = new Robot();

        String phoneNumber;

        Thread.sleep(10000);


        while ((phoneNumber = contacts.readLine()) != null) {

            Scanner pass_scanner = new Scanner(new FileInputStream(parentDirectory + "\\whatsapp_canvassing\\pass.txt"));
            Scanner fail_scanner = new Scanner(new FileInputStream(parentDirectory + "\\whatsapp_canvassing\\fail.txt"));
            Scanner invalid_scanner = new Scanner(new FileInputStream(parentDirectory + "\\whatsapp_canvassing\\invalid.txt"));


            if (!(phoneNumber.equals("") || phoneNumber.length() != 10)) {

                boolean presentInWhatsAppPass = false;
                while (pass_scanner.hasNextLine()) {
                    String whatsAppPassPhoneNumber = pass_scanner.nextLine();
                    if (phoneNumber.equals(whatsAppPassPhoneNumber)) {
                        presentInWhatsAppPass = true;
                        break;
                    }
                }

                boolean presentInWhatsAppInvalid = false;
                while (invalid_scanner.hasNextLine()) {
                    String whatsAppFailPhoneNumber = invalid_scanner.nextLine();
                    if (phoneNumber.equals(whatsAppFailPhoneNumber)) {
                        presentInWhatsAppInvalid = true;
                        break;
                    }
                }

                if (!(presentInWhatsAppPass || presentInWhatsAppInvalid)) {
                    System.out.println(phoneNumber);

                    try {

                        StringSelection url = new StringSelection("https://web.whatsapp.com/send?phone=" + phoneNumber);
                        clipboard.setContents(url, null);

                        screen.click(BrowserURLPath);
                        robot.keyPress(KeyEvent.VK_CONTROL);
                        robot.keyPress(KeyEvent.VK_V);
                        robot.keyRelease(KeyEvent.VK_V);
                        robot.keyRelease(KeyEvent.VK_CONTROL);
                        robot.keyPress(KeyEvent.VK_ENTER);
                        robot.keyRelease(KeyEvent.VK_ENTER);
                        Thread.sleep(15000);

                        try {
                            screen.find(InvalidNumber);
                            invalid_writer.append(phoneNumber);
                            invalid_writer.newLine();
                            invalid_writer.flush();
                        } catch (Exception e) {

                            try {
                                screen.click(typeMessage);
                                Thread.sleep(2000);

                                StringSelection message = new StringSelection("10వ తరగతి తర్వాత రెండు సంవత్సరాల కోర్సు చేసి ప్రభుత్వ ఉద్యోగం పొందగలగిన ఓకే ఒక్క కోర్సు \n" +
                                        "Agriculture Diploma course \n" +
                                        "\n" +
                                        "వివరాలకు సంప్రదించండి\n" +
                                        "\n" +
                                        "శ్యామల కృష్ణ అగ్రికల్చర్ పాలిటెక్నిక్ - తంబల్లపల్లె,అన్నమయ్య జిల్లా( ఉమ్మడి చిత్తూరు జిల్లా). ఆచార్య ఎన్.జి.రంగ వ్యవసాయ విశ్వవిద్యాలయానికి అనుబంధ సంస్థ\n" +
                                        "1. డిప్లొమా ఇన్ అగ్రికల్చర్-2 yrs(E.M)\n" +
                                        "2. డిప్లొమా ఇన్ సీడ్ టెక్నాలజీ-2 yrs(E.M)\n" +
                                        "3. డిప్లొమా ఇన్ అగ్రికల్చర్ ఇంజనీరింగ -3yrs(E.M) \n" +
                                        "అర్హత: 10th పాస్ \n" +
                                        "\n" +
                                        "అడ్మిషన్ల కొరకు contact\n" +
                                        "9804090401\n" +
                                        "9804090402\n" +
                                        "9804090403\n" +
                                        "\n" +
                                        "దరఖాస్తు కి చివరి తేది 20-06-2024");
                                clipboard.setContents(message, null);

                                robot.keyPress(KeyEvent.VK_CONTROL);
                                robot.keyPress(KeyEvent.VK_V);
                                robot.keyRelease(KeyEvent.VK_V);
                                robot.keyRelease(KeyEvent.VK_CONTROL);

                                robot.keyPress(KeyEvent.VK_ENTER);
                                robot.keyRelease(KeyEvent.VK_ENTER);
                                Thread.sleep(2000);

                                pass_writer.append(phoneNumber);
                                pass_writer.newLine();
                                pass_writer.flush();
                            } catch (Exception e1) {
                                fail_writer.append(phoneNumber);
                                fail_writer.newLine();
                                fail_writer.flush();
                            }
                        }

                    } catch (Exception e) {
                        fail_writer.append(phoneNumber);
                        fail_writer.newLine();
                        fail_writer.flush();

                    }

                }

            }

        }

        pass_writer.close();
        fail_writer.close();
        invalid_writer.close();

    }
}