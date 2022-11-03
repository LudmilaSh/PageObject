package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private SelenideElement heading = $("[data-test-id=dashboard]");

    private static ElementsCollection cards = $$(".list__item div");
    private SelenideElement firstCardButton = $("[data-test-id = '92df3f1c-a033-48e6-8390-206f6b1f56c0'] [class=button__text]");
    private SelenideElement secondCardButton = $("[data-test-id= '0f3f5c2a-249e-4c3d-8287-09f7a039391d'] [class=button__text]");
    private static final String balanceStart = "баланс: ";
    private static final String balanceFinish = " р.";

    public DashboardPage() {
        heading.shouldBe(visible);
    }

    public int getFirstCardBalance() {
        var text = cards.first().text();
        return extractBalance(text);
    }

    public int getSecondCardBalance() {
        var text = cards.last().text();
        return extractBalance(text);
    }


    public TransferPage transferFirstCard(){
        firstCardButton.click();
        return new TransferPage();
    }

    public TransferPage transferSecondCard(){
        secondCardButton.click();
        return new TransferPage();
    }

    private int extractBalance(String text) {
        var start = text.indexOf(balanceStart);
        var finish = text.indexOf(balanceFinish);
        var value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }


}
