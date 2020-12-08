package common;

import org.apache.poi.hssf.usermodel.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import pages.LoginPage;
import java.io.*;
import java.nio.file.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Utility extends LoginPage {
    public static String getUrl(String env) {
        String url="";
        if(env.equalsIgnoreCase("prod")){
            url=url_prod;
        }
        return url;
    }

    public static void captureScreenshot(WebDriver driver , String screenshotname)
    {
        Path dest = Paths.get(System.getProperty("user.dir")+"/Screenshoots/",screenshotname+".png");
        try {
            Files.createDirectories(dest.getParent());
            FileOutputStream out = new FileOutputStream(dest.toString());
            out.write(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));
            out.close();
        } catch (IOException e) {
            System.out.println("Exception while taking Screenshot"+e.getMessage());
        }
    }

    public static void waitUntilElementDisplayed(WebDriver driver, int sec, By xpath) {
        WebDriverWait wait = new WebDriverWait(driver, sec);
        wait.until(ExpectedConditions.elementToBeClickable(xpath));
    }

    public static void clickVisibleElement(String element, int time) {
        WebElement el = (new WebDriverWait(driver, time))
                .until(ExpectedConditions.elementToBeClickable(By.xpath(element)));
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", el);
    }

    public static void waitClickElement(String element, String expected, int time) {
        WebElement el = new WebDriverWait(driver, time)
                .until(ExpectedConditions.elementToBeClickable(By.xpath(element)));
        el.click();
        WebElement exp = new WebDriverWait(driver, time)
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(expected)));
        Assert.assertTrue(exp.isDisplayed());
    }

    public static void waitClickEl(String element, int time) {
        WebElement el = new WebDriverWait(driver, time)
                .until(ExpectedConditions.elementToBeClickable(By.xpath(element)));
        el.click();
        driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
    }

    public static String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static void writeOutputHtml(List<String> output) throws Exception {
        File path = new File(System.getProperty("user.dir"), "/Reports/");
        File textFile = new File(path, getDate()+"_MostExpensiveSellListingsNYC.html");
        BufferedWriter out = new BufferedWriter(new FileWriter(textFile));
        for (int j = 0; j < output.size(); j++) {
            out.write("<a href="+output.get(j)+">"+(j+1)+") "+output.get(j)+"</a><br/>");
        }
        out.close();
    }

    public static void writeOutputToXLS(List<String> output) throws Exception {
        String file = System.getProperty("user.dir")+"/Reports/"+getDate()+"_MostExpensiveSellListingsNYC.xls";
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("NY");
        HSSFRow rowhead = sheet.createRow((short)0);
        rowhead.createCell(0).setCellValue("URLs");
        for (int j = 0; j < output.size(); j++) {
            HSSFRow rows = sheet.createRow((short)j+1);
            rows.createCell(0).setCellValue(output.get(j));
        }
        FileOutputStream fileOut = new FileOutputStream(file);
        workbook.write(fileOut);
        fileOut.close();
        workbook.close();
    }
}

