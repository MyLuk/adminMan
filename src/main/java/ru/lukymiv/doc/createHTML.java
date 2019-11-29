package ru.lukymiv.doc;

import com.codeborne.selenide.Configuration;
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

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

public class createHTML {

    static String[] urls = new String[]{"General_info",
            "Purpose_and_scope",
            "Architecture",
            "Architecture",
            "Architecture",
            "Main_page",
            "Resource_monitor",
            "Send_message",
            "Queue_manager",
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

    public static void main(String[] args) throws IOException {
        downloadFiles();
        File input = new File("/home/michael/IdeaProjects/adminMan/src/main/recources/files/Queue_manager.html");
        Document doc = Jsoup.parse(input, "UTF-8", "");
        Element head = doc.head();
        Document finalDoc = Document.createShell("");
        finalDoc.select("head").append(head.html());
        finalDoc.select("body").append("<div id=\"app\"><div class=\"body-container\"><div class=\"documentation\"></div></div></div>");
        for (String name: urls) {
            File data = new File("/home/michael/IdeaProjects/adminMan/src/main/recources/files/"+name+".html");
            Document docData = Jsoup.parse(data, "UTF-8", "");
            String content = docData.select("div.documentation").html();
            finalDoc.select("div.documentation").append(content);
        }
        final File f = new File("filename.html");
        FileUtils.writeStringToFile(f, finalDoc.outerHtml(), "UTF-8");
        System.out.println();

    }

    private static void downloadFiles() throws FileNotFoundException {
        ChromeDriverManager.getInstance().version("76.0.3809.126").setup();
        Configuration.browser = "chrome";
        Configuration.baseUrl = "http://127.0.0.1:8181";
        Configuration.startMaximized = true;
        open("/");
        LoginPage.loginClickButton("root", "root");
        $x("//a[@href=\"#/doc\"]").click();
        WebDriver driver = WebDriverRunner.getWebDriver();
        for (int i=0; i<urls.length; i++) {
            String fileName = urls[i];
            $x("//a[@href=\"#/doc/"+fileName+"\"]").click();
            String data = driver.getPageSource();
            File file = new File("/home/michael/IdeaProjects/adminMan/src/main/recources/files/" + fileName+".html");
            PrintWriter printWriter = new PrintWriter(file);
            printWriter.println(data);
            printWriter.close();
        }
    }
}
