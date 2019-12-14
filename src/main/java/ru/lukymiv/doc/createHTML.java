package ru.lukymiv.doc;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static com.codeborne.selenide.Selenide.*;

public class createHTML {

    static String[] inter = new String[]{
            "Login_page",
            "Main_page",
            "Resource_monitor",
            "Send_message",
            "Queue_manager",
            "Domains_page",
            "Database",
            "Logs",
            "Settings",
            "User_page",
            "Dashboard",
            "Web_services",
            "REST",
            "Transaction_monitor",
            "REST_API"};

    static String[] urls = new String[]{
            "General_info",
            "Purpose_and_scope",
            "Architecture",
            "System_requirements",
            "Login_page",
            "Main_page",
            "Resource_monitor",
            "Send_message",
            "Queue_manager",
            "Domains_page",
            "Database",
            "Logs",
            "Settings",
            "User_page",
            "Dashboard",
            "Web_services",
            "REST",
            "Transaction_monitor",
            "REST_API",
            "Install",
            "Modes_exp",
            "Attachment"};

    static String projectMain = System.getProperty("user.home") + System.getProperty("file.separator")
            + "IdeaProjects" + System.getProperty("file.separator") + "adminMan" + System.getProperty("file.separator")
            + "src" + System.getProperty("file.separator") + "main";
    static String projectFiles =  projectMain + System.getProperty("file.separator")
            + "resources" + System.getProperty("file.separator") + "files";
    static String queue_manager =  projectFiles + System.getProperty("file.separator")
            + "Queue_manager.html";

    public static void main(String[] args) throws IOException {
        downloadFiles();
        File input = new File(queue_manager);
        Document doc = Jsoup.parse(input, "UTF-8", "");
        Element head = doc.head();
        head.select("style").get(0).append("      table, th, td {\n" +
                "          border: 1px solid black;\n" +
                "          border-collapse: collapse;\n" +
                "      }");
        Document finalDoc = Document.createShell("");
        finalDoc.select("head").append(head.html());
        finalDoc.select("body").append("<div id=\"app\"><div class=\"body-container\"><div class=\"documentation\"></div></div></div>");
        for (String name: urls) {
            File data = new File(projectFiles + System.getProperty("file.separator") + name + ".html");
            Document docData = Jsoup.parse(data, "UTF-8", "");
            String content = docData.select("div.documentation").html();
            if (Arrays.asList(inter).contains(name)) {
                int[] headers =  new int[]{5, 4, 3, 2, 1};
                for (int i: headers
                     ) {
                    content = replace(i, content);
                }
            }
            if (name.equals("Login_page")) content = "<h1>Интерфейс</h1>" + content;
            finalDoc.select("div.documentation").append(content);
        }
        final File f = new File("filename.html");

        FileUtils.writeStringToFile(f, finalDoc.outerHtml().replaceAll("img src=\"/images", "img src=\"./images"), "UTF-8");
        System.out.println();

    }

    public static String replace (int whatReplace, String content) {
        String header = "<h%s>";
        String headerClose = "</h%s>";
        content = content.replaceAll( String.format(header, whatReplace), String.format(header, whatReplace+1));
        return content.replaceAll( String.format(headerClose, whatReplace), String.format(headerClose, whatReplace+1));
    }

    private static void downloadFiles() throws FileNotFoundException {
        String osName = System.getProperty("os.name");
        if (osName.equals("Windows 10")) {
            ChromeDriverManager.getInstance().version("78.0.3904.105").setup();
        } else {
            ChromeDriverManager.getInstance().version("76.0.3809.126").setup();
        }
        Configuration.browser = "chrome";
        Configuration.baseUrl = "http://127.0.0.1:3000";
        Configuration.startMaximized = true;
        open("/#/doc");
//        LoginPage.loginClickButton("root", "root");
//        $x("//a[@href=\"#/doc\"]").click();
        WebDriver driver = WebDriverRunner.getWebDriver();
        Set<String> winSet = WebDriverRunner.getWebDriver().getWindowHandles();
        List<String> winList = new ArrayList<String>(winSet);
        String currentWindow = WebDriverRunner.getWebDriver().getWindowHandle();
        for (String win : winList) {
            if (!win.equals(currentWindow)) {
                Selenide.switchTo().window(win);
            }
        }
        for (int i=0; i<urls.length; i++) {
            String fileName = urls[i];
            $x("//a[@href=\"#/doc/"+fileName+"\"]").click();
            sleep(500);
            String data = driver.getPageSource();
            File file = new File(projectFiles + System.getProperty("file.separator") + fileName + ".html");
            PrintWriter printWriter = new PrintWriter(file);
            printWriter.println(data);
            printWriter.close();
        }
    }
}
