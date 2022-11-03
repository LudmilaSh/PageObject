package ru.netology.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.DataHelper.*;

public class PageObjectTest {


    @Test
    public void shouldTransferFromFirstToSecond() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode();
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var firstCardBalance = dashboardPage.getFirstCardBalance();
        var secondCardBalance = dashboardPage.getSecondCardBalance();
        var amount = generateValidAmount(firstCardBalance);
        var firstCardBalanceAfter = firstCardBalance - amount;
        var secondCardBalanceAfter = secondCardBalance + amount;
        var transferPage = dashboardPage.transferSecondCard();
        dashboardPage = transferPage.makeValidTransfer(String.valueOf(amount), getFirstCardInfo());
        var actualBalanceFirstCard = dashboardPage.getFirstCardBalance();
        var actualBalanceSecondCard = dashboardPage.getSecondCardBalance();
        Assertions.assertEquals(firstCardBalanceAfter, actualBalanceFirstCard);
        Assertions.assertEquals(secondCardBalanceAfter, actualBalanceSecondCard);


    }

    @Test
    public void shouldTransferFromSecondToFirst() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode();
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var firstCardBalance = dashboardPage.getFirstCardBalance();
        var secondCardBalance = dashboardPage.getSecondCardBalance();
        var amount = generateValidAmount(firstCardBalance);
        var firstCardBalanceAfter = firstCardBalance + amount;
        var secondCardBalanceAfter = secondCardBalance - amount;
        var transferPage = dashboardPage.transferFirstCard();
        dashboardPage = transferPage.makeValidTransfer(String.valueOf(amount), getSecondCardInfo());
        var actualBalanceFirstCard = dashboardPage.getFirstCardBalance();
        var actualBalanceSecondCard = dashboardPage.getSecondCardBalance();
        Assertions.assertEquals(firstCardBalanceAfter, actualBalanceFirstCard);
        Assertions.assertEquals(secondCardBalanceAfter, actualBalanceSecondCard);


    }

    @Test
    public void TransferUnderLimit() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode();
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var firstCardBalance = dashboardPage.getFirstCardBalance();
        var secondCardBalance = dashboardPage.getSecondCardBalance();
        var amount = generateInvalidAmount(firstCardBalance);
        var transferPage = dashboardPage.transferSecondCard();
        dashboardPage = transferPage.makeValidTransfer(String.valueOf(amount), getFirstCardInfo());
        transferPage.errorMessage("Ошибка!");


    }
}
