package com.bbolab.gaonna.core.util;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;

// TODO: move this fetcher to batch module
@Slf4j
public class GeoDataFetcher {
    private static final String nsdi_home = "http://www.nsdi.go.kr/lxportal/?menuno=2679";
    private static final String nsdi_login = "https://www.nsdi.go.kr/lxportal/?menuno=2971&redirect=%s";
    private static final String[] sensus_list = {
            "http://data.nsdi.go.kr/dataset/20171206ds00002",
            "http://data.nsdi.go.kr/dataset/20171206ds00003",
            "http://data.nsdi.go.kr/dataset/20171206ds00001"
    };
    private static final String sensus_l1 = "http://data.nsdi.go.kr/dataset/20171206ds00002";
    private static final String sensus_l2 = "http://data.nsdi.go.kr/dataset/20171206ds00003";
    private static final String sensus_l3 = "http://data.nsdi.go.kr/dataset/20171206ds00001";

    public static void GetGeoData(String id, String password) throws InterruptedException {
        // initialize chromedriver
        ChromeOptions options = new ChromeOptions();
        options.addArguments("-incognito");
        options.addArguments("--disable-web-security");
        ChromeDriver driver = new ChromeDriver(options);

        // login
        String login_path = String.format(nsdi_login, nsdi_home);
        driver.get(login_path);

        var inputIdElem = driver.findElementById("userid");
        var inputPwElem = driver.findElementById("userpasswd");

        inputIdElem.sendKeys(id);
        inputPwElem.sendKeys(password);

        var formLoginBtn = driver.findElementById("submit_b");
        formLoginBtn.click();

        // find data table resource
        for (String sensusUrl: sensus_list) {
            driver.get(sensusUrl);
            var element = driver.findElementById("data_table_resource");

            // iterate table rows
            for (WebElement tmpElem : element.findElements(By.ByCssSelector.cssSelector("tbody > tr"))) {
                String filename = tmpElem.findElement(By.ByCssSelector.cssSelector("td:nth-child(3)")).getText();

                // check filename
                if (filename.contains(".zip")) {
                    log.debug("found zip file: {}", filename);

                    var btnDownload = tmpElem.findElement(By.ByCssSelector.cssSelector("button.dataset-download-btn"));
                    btnDownload.click();
                }
            }
            Thread.sleep(2000);
        }

        driver.close();
    }
}
