package ru.lukymiv.doc;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.FileUtils;

public class createHTML {



    public static void main(String[] args) throws IOException {
        String[] urls = new String[]{"http://localhost:8181/manager/#/doc/General_info",
                "http://localhost:8181/manager/#/doc/Purpose_and_scope",
                "http://localhost:8181/manager/#/doc/Architecture",
                "http://localhost:8181/manager/#/doc/Architecture",
                "http://localhost:8181/manager/#/doc/Architecture",
                "http://localhost:8181/manager/#/doc/Main_page",
                "http://localhost:8181/manager/#/doc/Resource_monitor",
                "http://localhost:8181/manager/#/doc/Send_message",
                "http://localhost:8181/manager/#/doc/Queue_manager"};
        Connection.Response response = HttpConnection.connect("http://localhost:8181/manager/login/")
                .data("username", "root")
                .data("password", "root")
                .method(Connection.Method.POST)
                .followRedirects(true)
                .execute();
        Document doc = Jsoup.connect("http://localhost:8181/manager/#/doc").cookies(response.cookies()).followRedirects(true).get();
        Element head = doc.head();
        Document finalDoc = Document.createShell("");
        finalDoc.select("head").append(head.html());
        final File f = new File("filename.html");
        FileUtils.writeStringToFile(f, finalDoc.outerHtml(), "UTF-8");


    }
}
