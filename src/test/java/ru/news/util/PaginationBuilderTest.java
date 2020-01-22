package ru.news.util;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.testng.Assert;
import ru.news.exceptions.PaginationError;

import java.util.HashMap;

@RunWith(DataProviderRunner.class)
public class PaginationBuilderTest {
    private String testURL = "/main";

    @DataProvider
    public static Object[][] dataProviderAdd() {
        Integer[][] integers = new Integer[4 * 10][2];
        for (int i = 1; i < 5; i++) {
            for (int j = 0; j < 10; j++) {
                integers[j * 4 + i - 1][0] = i;
                integers[j * 4 + i - 1][1] = j;
            }
        }

        return integers;
    }

    @Test
    @UseDataProvider("dataProviderAdd")
    public void testSuccessGettingButtonAttributes(Integer i, Integer j) throws Exception {
        HashMap<String, String> expectedButtonAttributes = getButtonAttributes(i, j, testURL);
        if (expectedButtonAttributes == null) {
            return;
        }

        //Проверка имен кнопок
        Assert.assertEquals(getPaginationBuilder(i, j).getNumber1Button(), expectedButtonAttributes.get("1"));
        Assert.assertEquals(getPaginationBuilder(i, j).getNumber2Button(), expectedButtonAttributes.get("2"));
        Assert.assertEquals(getPaginationBuilder(i, j).getNumber3Button(), expectedButtonAttributes.get("3"));

        //Проверка линков кнопок
        Assert.assertEquals(getPaginationBuilder(i, j).getNumber1ButtonLink(), expectedButtonAttributes.get("4"));
        Assert.assertEquals(getPaginationBuilder(i, j).getNumber2ButtonLink(), expectedButtonAttributes.get("5"));
        Assert.assertEquals(getPaginationBuilder(i, j).getNumber3ButtonLink(), expectedButtonAttributes.get("6"));

        //Проверка линков для предыдущей кнопки
        Assert.assertEquals(getPaginationBuilder(i, j).getPreviousPageLink(), expectedButtonAttributes.get("7"));

        //Проверка линков для следующей кнопки
        Assert.assertEquals(getPaginationBuilder(i, j).getNextPageLink(), expectedButtonAttributes.get("8"));

        //Проверка активности кнопок
        Assert.assertEquals(getPaginationBuilder(i, j).isTheFirstButtonActive().toString(),
                expectedButtonAttributes.get("9"));
        Assert.assertEquals(getPaginationBuilder(i, j).isTheSecondButtonActive().toString(),
                expectedButtonAttributes.get("10"));
        Assert.assertEquals(getPaginationBuilder(i, j).isTheThirdButtonActive().toString(),
                expectedButtonAttributes.get("11"));
    }

    @Test(expected = PaginationError.class)
    @UseDataProvider("dataProviderAdd")
    public void testCreatePaginationError(Integer i, Integer j) throws Exception {
        if (predButtonExceptions(i, j)) {
            getPaginationBuilder(i, j);
        } else {
            throw new PaginationError("Тестовое исключение пагинации.");
        }
    }

    @Test
    public void testGetFirstPageLink() throws Exception {
        Assert.assertEquals(getPaginationBuilder(4, 5).getFirstPageLink(),
                "http://localhost:8080" + testURL + "/0");
    }

    @Test
    public void testGetLastPageLink() throws Exception {
        Assert.assertEquals(getPaginationBuilder(1, 0).getLastPageLink(),
                "http://localhost:8080" + testURL + "/0");
        Assert.assertEquals(getPaginationBuilder(2, 1).getLastPageLink(),
                "http://localhost:8080" + testURL + "/1");
        Assert.assertEquals(getPaginationBuilder(3, 2).getLastPageLink(),
                "http://localhost:8080" + testURL + "/2");
        Assert.assertEquals(getPaginationBuilder(4, 5).getLastPageLink(),
                "http://localhost:8080" + testURL + "/7");
    }

    //Предикат для проверки формирования PaginationError
    private boolean predButtonExceptions(Integer i, Integer j) {
        return i == 1 && j > 0 ||
                i == 2 && j > 1 ||
                i == 3 && j > 2 ||
                i == 4 && j > 7;
    }

    //Подготовка данных для проверки атрибутов
    private HashMap<String, String> getButtonAttributes(Integer i,
                                                        Integer j,
                                                        String testURL) throws Exception {
        HashMap<String, String> expectedButtonAttributes = new HashMap<>();
        //Справочник ключей:
        // 1 - Наименование 1 кнопки
        // 2 - Наименование 2 кнопки
        // 3 - Наименование 3 кнопки
        // 4 - Наименование линка для 1 кнопки
        // 5 - Наименование линка для 2 кнопки
        // 6 - Наименование линка для 3 кнопки
        // 7 - Линк предыдущей страницы
        // 8 - Линк следующей страницы
        // 9 - Активность 1 кнопки
        // 10 - Активность 2 кнопки
        // 11 - Активность 3 кнопки

        if (i == 1) {
            if (j == 0) {
                expectedButtonAttributes.put("1", "1");
                expectedButtonAttributes.put("2", "");
                expectedButtonAttributes.put("3", "");
                expectedButtonAttributes.put("4", "http://localhost:8080" + testURL + "/0");
                expectedButtonAttributes.put("5", "");
                expectedButtonAttributes.put("6", "");
                expectedButtonAttributes.put("7", "#");
                expectedButtonAttributes.put("8", "#");
                expectedButtonAttributes.put("9", String.valueOf(true));
                expectedButtonAttributes.put("10", String.valueOf(false));
                expectedButtonAttributes.put("11", String.valueOf(false));
            } else {
                return null;
            }
        } else if (i == 2) {
            if (j < 2) {
                expectedButtonAttributes.put("1", "1");
                expectedButtonAttributes.put("2", "2");
                expectedButtonAttributes.put("3", "");
                expectedButtonAttributes.put("4", "http://localhost:8080" + testURL + "/0");
                expectedButtonAttributes.put("5", "http://localhost:8080" + testURL + "/1");
                expectedButtonAttributes.put("6", "");
                expectedButtonAttributes.put("7", ((j == 0) ? "#" : "http://localhost:8080" + testURL +
                        "/" + String.valueOf(j - 1)));
                expectedButtonAttributes.put("8", ((j == 1) ? "#" : "http://localhost:8080" + testURL +
                        "/" + String.valueOf(j + 1)));
                expectedButtonAttributes.put("9", ((j == 0) ? String.valueOf(true) : String.valueOf(false)));
                expectedButtonAttributes.put("10", ((j == 1) ? String.valueOf(true) : String.valueOf(false)));
                expectedButtonAttributes.put("11", String.valueOf(false));
            } else {
                return null;
            }
        } else if (i == 3) {
            if (j < 3) {
                expectedButtonAttributes.put("1", "1");
                expectedButtonAttributes.put("2", "2");
                expectedButtonAttributes.put("3", "3");
                expectedButtonAttributes.put("4", "http://localhost:8080" + testURL + "/0");
                expectedButtonAttributes.put("5", "http://localhost:8080" + testURL + "/1");
                expectedButtonAttributes.put("6", "http://localhost:8080" + testURL + "/2");
                expectedButtonAttributes.put("7", ((j == 0) ? "#" : "http://localhost:8080" + testURL +
                        "/" + String.valueOf(j - 1)));
                expectedButtonAttributes.put("8", ((j == 2) ? "#" : "http://localhost:8080" + testURL +
                        "/" + String.valueOf(j + 1)));
                expectedButtonAttributes.put("9", ((j == 0) ? String.valueOf(true) : String.valueOf(false)));
                expectedButtonAttributes.put("10", ((j == 1) ? String.valueOf(true) : String.valueOf(false)));
                expectedButtonAttributes.put("11", ((j == 2) ? String.valueOf(true) : String.valueOf(false)));
            } else {
                return null;
            }
        } else if (i == 4) {
            if (j < 2) {
                expectedButtonAttributes.put("1", "1");
                expectedButtonAttributes.put("2", "2");
                expectedButtonAttributes.put("3", "3");
                expectedButtonAttributes.put("4", "http://localhost:8080" + testURL + "/0");
                expectedButtonAttributes.put("5", "http://localhost:8080" + testURL + "/1");
                expectedButtonAttributes.put("6", "http://localhost:8080" + testURL + "/2");
                expectedButtonAttributes.put("7", ((j == 0) ? "#" : "http://localhost:8080" + testURL +
                        "/" + String.valueOf(j - 1)));
                expectedButtonAttributes.put("8", "http://localhost:8080" + testURL +
                        "/" + String.valueOf(j + 1));
                expectedButtonAttributes.put("9", ((j == 0) ? String.valueOf(true) : String.valueOf(false)));
                expectedButtonAttributes.put("10", ((j == 1) ? String.valueOf(true) : String.valueOf(false)));
                expectedButtonAttributes.put("11", String.valueOf(false));

            } else if (j > 1 && j < 7) {
                expectedButtonAttributes.put("1", String.valueOf(j));
                expectedButtonAttributes.put("2", String.valueOf(j + 1));
                expectedButtonAttributes.put("3", String.valueOf(j + 2));
                expectedButtonAttributes.put("4", "http://localhost:8080" + testURL + "/" + String.valueOf(j - 1));
                expectedButtonAttributes.put("5", "http://localhost:8080" + testURL + "/" + String.valueOf(j));
                expectedButtonAttributes.put("6", "http://localhost:8080" + testURL + "/" + String.valueOf(j + 1));
                expectedButtonAttributes.put("7", "http://localhost:8080" + testURL + "/" + String.valueOf(j - 1));
                expectedButtonAttributes.put("8", "http://localhost:8080" + testURL + "/" + String.valueOf(j + 1));
                expectedButtonAttributes.put("9", String.valueOf(false));
                expectedButtonAttributes.put("10", String.valueOf(true));
                expectedButtonAttributes.put("11", String.valueOf(false));
            } else if (j == 7) {
                expectedButtonAttributes.put("1", "6");
                expectedButtonAttributes.put("2", "7");
                expectedButtonAttributes.put("3", "8");
                expectedButtonAttributes.put("4", "http://localhost:8080" + testURL + "/5");
                expectedButtonAttributes.put("5", "http://localhost:8080" + testURL + "/6");
                expectedButtonAttributes.put("6", "http://localhost:8080" + testURL + "/7");
                expectedButtonAttributes.put("7", "http://localhost:8080" + testURL + "/" + String.valueOf(j - 1));
                expectedButtonAttributes.put("8", "#");
                expectedButtonAttributes.put("9", String.valueOf(false));
                expectedButtonAttributes.put("10", String.valueOf(false));
                expectedButtonAttributes.put("11", String.valueOf(true));
            } else {
                return null;
            }
        }
        return expectedButtonAttributes;
    }

    //Билдер для разных кейсов - разное количество записей возвращаемых в запросе из БД
    private PaginationBuilder getPaginationBuilder(int testType, int page) throws Exception {
        MockHttpServletRequest httpServletRequest = new MockHttpServletRequest();
        httpServletRequest.setRequestURI(testURL);
        httpServletRequest.setServerPort(8080);

        switch (testType) {
            case 1:
                return new PaginationBuilder(httpServletRequest,
                        1,
                        page,
                        10);
            case 2:
                return new PaginationBuilder(httpServletRequest,
                        14,
                        page,
                        10);
            case 3:
                return new PaginationBuilder(httpServletRequest,
                        25,
                        page,
                        10);
            case 4:
                return new PaginationBuilder(httpServletRequest,
                        77,
                        page,
                        10);
        }
        return null;
    }
}