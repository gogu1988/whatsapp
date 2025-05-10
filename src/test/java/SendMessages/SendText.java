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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

class SendText {

    //https://web.whatsapp.com/send?phone=+919866840603

    @Test
    public void text() throws FindFailed, InterruptedException, IOException, AWTException {

        FileSystemView view = FileSystemView.getFileSystemView();
        File file1 = view.getHomeDirectory();
//        String parentDirectory = file1.getPath();
        String picDirectory = getCurrentDir();
        String parentDirectory = getDesktopPath();

        File contacts_file = new File(parentDirectory + "/whatsapp_canvassing/contacts.txt");

        BufferedReader contacts = new BufferedReader(new FileReader(contacts_file));

        BufferedWriter pass_writer = new BufferedWriter(new FileWriter(parentDirectory + "/whatsapp_canvassing/pass.txt", true));
        BufferedWriter fail_writer = new BufferedWriter(new FileWriter(parentDirectory + "/whatsapp_canvassing/fail.txt", true));
        BufferedWriter invalid_writer = new BufferedWriter(new FileWriter(parentDirectory + "/whatsapp_canvassing/invalid.txt", true));

        Screen screen = new Screen();

        Pattern BrowserURLPath = new Pattern(picDirectory + "/whatsapp_canvassing/pics/BrowserURLPath.png");
        Pattern typeMessage = new Pattern(picDirectory + "/whatsapp_canvassing/pics/TypeMessage.png");
        Pattern InvalidNumber = new Pattern(picDirectory + "/whatsapp_canvassing/pics/InvalidNumber.png");

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Clipboard clipboard = toolkit.getSystemClipboard();

        Robot robot = new Robot();

        String phoneNumber;

        Thread.sleep(10000);


        while ((phoneNumber = contacts.readLine()) != null) {

            Scanner pass_scanner = new Scanner(Files.newInputStream(Paths.get(parentDirectory + "/whatsapp_canvassing/pass.txt")));
            Scanner fail_scanner = new Scanner(Files.newInputStream(Paths.get(parentDirectory + "/whatsapp_canvassing/fail.txt")));
            Scanner invalid_scanner = new Scanner(Files.newInputStream(Paths.get(parentDirectory + "/whatsapp_canvassing/invalid.txt")));


            if (phoneNumber.length() == 10) {

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
                        Thread.sleep(5000);
                        robot.keyPress(KeyEvent.VK_ENTER);
                        robot.keyRelease(KeyEvent.VK_ENTER);
                        Thread.sleep(25000);

                        try {
                            screen.find(InvalidNumber);
                            invalid_writer.append(phoneNumber);
                            invalid_writer.newLine();
                            invalid_writer.flush();
                        } catch (Exception e) {

                            try {
                                screen.click(typeMessage);
                                Thread.sleep(2000);

                                StringSelection message = new StringSelection("శ్యామల కృష్ణ అగ్రికల్చరల్ పాలిటెక్నిక్ కాలేజీ (affiliated to ANGRAU) Lakkasamudram, Thamballpalli (Mandal) \n" +
                                        "Annamayya Dist. AP.\n" +
                                        "\n" +
                                        "\"Free Seat\" \n" +
                                        "పదవతరగతి తరువాత కేవలం మూడు సంవత్సరాల అగ్రికల్చరల్ పాలిటెక్నిక్ కోర్స్ చదివి 100%  గవర్నమెంట్ జాబ్ ఎలిజిబిలిటీ (Rs. 32000 Monthly Salary) మరియు 100% ప్రైవేట్ జాబ్ (Rs.20000 to 25000 Monthly Salary) ప్లేసెమెంట్ ద్వారా ఇప్పించబడును.\n" +
                                        "\n" +
                                        "ఉన్నత విద్యావకాశాలు:\n" +
                                        "ఈ కోర్స్ పూర్తి చేసిన తరువాత డిగ్రీ లో  చేరవచ్చును. AGRICET పరీక్ష ద్వారా 20% రిజర్వేషన్ తో సులభంగా B.Sc Agriculture లో లేటరల్ ఎంట్రీ ద్వారా 2వ సంవత్సరం లో చేరవచ్చును.\n" +
                                        "\n" +
                                        "ఈ  కోర్స్ లో చేరుటకు పదవతరగతి మార్కుల మెరిట్ ఆధారంగానే సీట్లు కేటాయించబడును. \n" +
                                        "\n" +
                                        "అడ్మిషన్ కొరకు సంప్రదించండి:\n" +
                                        "Principal: Dr.B.Krishna Reddy-M.Sc. (Ag) PhD. \n" +
                                        "Cell: 9804090401, 9804090402");
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

    public static String getDesktopPath() {
        String userHome = System.getProperty("user.home");
        String os = System.getProperty("os.name").toLowerCase();
        // Common for Linux
        return Paths.get(userHome, "Desktop").toString();
    }

    public static String getCurrentDir(){
        return System.getProperty("user.dir")+"/src/test/resources";
    }
}