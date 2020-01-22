package ru.news.util;

import ru.news.exceptions.PaginationError;

import javax.servlet.http.HttpServletRequest;

/**
 * Pagination builder class.
 *
 * @version 1.0
 */
public class PaginationBuilder {
    private String nameController = "main";

    /**
     * The name of the first tab pagination.
     */
    private String firstPage = "Первая";

    /**
     * The name of the first button pagination.
     */
    private String number1Button = "1";

    /**
     * The name of the second button pagination.
     */
    private String number2Button = "2";

    /**
     * The name of the third button pagination.
     */
    private String number3Button = "3";

    /**
     * The name of the last tab pagination.
     */
    private String lastPage = "Последняя";

    /**
     * The last pagination page.
     */
    private Integer lastPageValue = 0;

    /**
     * The number of records in the database table.
     */
    private Integer countRecordsInTable = 0;

    /**
     * The link of the first tab.
     */
    private String firstPageLink = "";

    /**
     * The link of the previous page.
     */
    private String previousPageLink = "";

    /**
     * The link of the first button.
     */
    private String number1ButtonLink = "";

    /**
     * The link of the second button.
     */
    private String number2ButtonLink = "";

    /**
     * The link of the third button.
     */
    private String number3ButtonLink = "";

    /**
     * The link of the next page.
     */
    private String nextPageLink = "";

    /**
     * The link of the last tab.
     */
    private String lastPageLink = "";

    /**
     * The number of entries to display.
     */
    private int countRecordsShown;

    /**
     * The number of records in the database table.
     */
    private Integer numberPageFromPagination;

    /**
     * Received HttpServletRequest.
     */
    private HttpServletRequest httpServletRequest;

    /**
     * Constructs a PaginationBuilder
     *
     * @param httpServletRequest       is the received http request
     * @param countRecordsInTable      - count table records
     * @param numberPageFromPagination - selected pagination page
     * @param countRecordsShown        - number of records displayed on the page
     * @throws Exception
     */
    public PaginationBuilder(HttpServletRequest httpServletRequest,
                             Integer countRecordsInTable,
                             Integer numberPageFromPagination,
                             Integer countRecordsShown) {
        this.httpServletRequest = httpServletRequest;
        this.numberPageFromPagination = numberPageFromPagination;
        this.countRecordsShown = countRecordsShown;
        this.countRecordsInTable = countRecordsInTable;

        buildButtonAttributes(httpServletRequest,
                countRecordsInTable, numberPageFromPagination, countRecordsShown);
    }

    public PaginationBuilder(HttpServletRequest httpServletRequest,
                             Integer countRecordsInTable,
                             Integer numberPageFromPagination,
                             Integer countRecordsShown,
                             String nameController) {
        this.httpServletRequest = httpServletRequest;
        this.numberPageFromPagination = numberPageFromPagination;
        this.countRecordsShown = countRecordsShown;
        this.countRecordsInTable = countRecordsInTable;
        this.nameController = nameController;

        buildButtonAttributes(httpServletRequest,
                countRecordsInTable, numberPageFromPagination, countRecordsShown);
    }

    /**
     * Checking the fact that we are on the first page.
     *
     * @return true, if we are on the first page
     */
    public Boolean isTheFirstPage() {
        return numberPageFromPagination == 0;
    }

    /**
     * Checking the fact that we are on the last page.
     *
     * @return true, if we are on the last page
     */
    public Boolean isTheLastPage() {
        return numberPageFromPagination.equals(lastPageValue);
    }

    /**
     * Checking the activity of the first button in pagination.
     *
     * @return true, if the first tab is active
     */
    public Boolean isTheFirstButtonActive() {
        return isTheFirstPage();
    }

    /**
     * Checking the activity of the second button in pagination.
     *
     * @return true, if the second tab is active
     */
    public Boolean isTheSecondButtonActive() {
        return !isTheFirstButtonActive() && !isTheThirdButtonActive();
    }

    /**
     * Checking the activity of the third button in pagination.
     *
     * @return true, if the third tab is active
     */
    public Boolean isTheThirdButtonActive() {
        return (lastPageValue > 1) && numberPageFromPagination.equals(lastPageValue);
    }

    public String getFirstPage() {
        return firstPage;
    }

    public String getNumber1Button() {
        return number1Button;
    }

    public String getNumber2Button() {
        return number2Button;
    }

    public String getNumber3Button() {
        return number3Button;
    }

    public String getLastPage() {
        return lastPage;
    }

    public Integer getLastPageValue() {
        return lastPageValue;
    }

    public Integer getCountRecordsInTable() {
        return countRecordsInTable;
    }

    public String getFirstPageLink() {
        return firstPageLink;
    }

    public String getNumber1ButtonLink() {
        return number1ButtonLink;
    }

    public String getNumber2ButtonLink() {
        return number2ButtonLink;
    }

    public String getNumber3ButtonLink() {
        return number3ButtonLink;
    }

    public String getLastPageLink() {
        return lastPageLink;
    }

    public int getCountRecordsShown() {
        return countRecordsShown;
    }

    public Integer getNumberPageFromPagination() {
        return numberPageFromPagination;
    }

    public String getPreviousPageLink() {
        return previousPageLink;
    }

    public HttpServletRequest getHttpServletRequest() {
        return httpServletRequest;
    }

    public String getNextPageLink() {
        return nextPageLink;
    }

    private void buildButtonAttributes(HttpServletRequest httpServletRequest, Integer countRecordsInTable,
                                       Integer numberPageFromPagination, Integer countRecordsShown) {
        calculateLastPageValue(countRecordsInTable, countRecordsShown);
        checkReceivedPage(numberPageFromPagination);
        buildDynamicButtons(httpServletRequest, numberPageFromPagination);
        buildStaticButtons(httpServletRequest, numberPageFromPagination);
    }

    private void buildDynamicButtons(HttpServletRequest httpServletRequest, Integer numberPageFromPagination) {
        if (lastPageValue > 1) {
            createPaginationWithThreeButtons(httpServletRequest, numberPageFromPagination);
        } else if (lastPageValue == 1) {
            createPaginationWithTwoButtons(httpServletRequest);
        } else if (lastPageValue == 0) {
            createPaginationWithOneButton(httpServletRequest);
        } else {
            throw new PaginationError("При расчете пагинации получено " +
                    "отрицательное количество записей в таблице БД");
        }
    }

    private void createPaginationWithThreeButtons(HttpServletRequest httpServletRequest,
                                                  Integer numberPageFromPagination) {
        if (numberPageFromPagination > 0) {
            if (numberPageFromPagination.equals(lastPageValue)) {
                createPaginationForTheLastPage(httpServletRequest, numberPageFromPagination);
            } else {
                createPaginationForTheMiddlePage(httpServletRequest, numberPageFromPagination);
            }
        } else {
            createPaginationForTheStartPage(httpServletRequest, numberPageFromPagination);
        }
    }

    private void createPaginationForTheMiddlePage(HttpServletRequest httpServletRequest,
                                                  Integer numberPageFromPagination) {
        number1Button = numberPageFromPagination.toString();
        number2Button = String.valueOf(numberPageFromPagination + 1);
        number3Button = String.valueOf(numberPageFromPagination + 2);

        number1ButtonLink = AbsoluteUrlBuilder.getUrl(httpServletRequest,
                "/"+nameController+"/" + (numberPageFromPagination - 1));
        number2ButtonLink = AbsoluteUrlBuilder.getUrl(httpServletRequest,
                "/"+nameController+"/" + numberPageFromPagination);
        number3ButtonLink = AbsoluteUrlBuilder.getUrl(httpServletRequest,
                "/"+nameController+"/" + (numberPageFromPagination + 1));
    }

    private void createPaginationForTheLastPage(HttpServletRequest httpServletRequest, Integer numberPageFromPagination) {
        number1Button = String.valueOf(numberPageFromPagination - 1);
        number2Button = numberPageFromPagination.toString();
        number3Button = String.valueOf(numberPageFromPagination + 1);

        number1ButtonLink = AbsoluteUrlBuilder.getUrl(httpServletRequest,
                "/"+nameController+"/" + (numberPageFromPagination - 2));
        number2ButtonLink = AbsoluteUrlBuilder.getUrl(httpServletRequest,
                "/"+nameController+"/" + (numberPageFromPagination - 1));
        number3ButtonLink = AbsoluteUrlBuilder.getUrl(httpServletRequest,
                "/"+nameController+"/" + numberPageFromPagination);
    }

    private void createPaginationForTheStartPage(HttpServletRequest httpServletRequest, Integer numberPageFromPagination) {
        number1Button = String.valueOf(numberPageFromPagination + 1);
        number2Button = String.valueOf(numberPageFromPagination + 2);
        number3Button = String.valueOf(numberPageFromPagination + 3);

        number1ButtonLink = AbsoluteUrlBuilder.getUrl(httpServletRequest,
                "/"+nameController+"/" + numberPageFromPagination);
        number2ButtonLink = AbsoluteUrlBuilder.getUrl(httpServletRequest,
                "/"+nameController+"/" + (numberPageFromPagination + 1));
        number3ButtonLink = AbsoluteUrlBuilder.getUrl(httpServletRequest,
                "/"+nameController+"/" + (numberPageFromPagination + 2));
    }

    private void createPaginationWithTwoButtons(HttpServletRequest httpServletRequest) {
        number3Button = "";
        number3ButtonLink = "";

        number2ButtonLink = AbsoluteUrlBuilder.getUrl(httpServletRequest,
                "/"+nameController+"/" + String.valueOf(lastPageValue));

        number1ButtonLink = AbsoluteUrlBuilder.getUrl(httpServletRequest,
                "/"+nameController+"/" + String.valueOf(lastPageValue - 1));
    }

    private void createPaginationWithOneButton(HttpServletRequest httpServletRequest) {
        number2Button = "";
        number2ButtonLink = "";

        number3Button = "";
        number3ButtonLink = "";

        number1ButtonLink = AbsoluteUrlBuilder.getUrl(httpServletRequest,
                "/"+nameController+"/" + String.valueOf(lastPageValue));
    }

    private void buildStaticButtons(HttpServletRequest httpServletRequest, Integer numberPageFromPagination) {
        firstPageLink = AbsoluteUrlBuilder.getUrl(httpServletRequest, "/"+nameController+"/0");
        lastPageLink = AbsoluteUrlBuilder.getUrl(httpServletRequest,
                "/"+nameController+"/" + String.valueOf(lastPageValue));

        String linkToPreviousPage = AbsoluteUrlBuilder.getUrl(httpServletRequest,
                "/"+nameController+"/" + String.valueOf(numberPageFromPagination - 1));
        previousPageLink = numberPageFromPagination == 0 ? "#" : linkToPreviousPage;

        String linkToNextPage = AbsoluteUrlBuilder.getUrl(httpServletRequest,
                "/"+nameController+"/" + String.valueOf(numberPageFromPagination + 1));
        nextPageLink = numberPageFromPagination.equals(lastPageValue) ? "#" : linkToNextPage;
    }

    private void checkReceivedPage(Integer numberPageFromPagination) {
        if (numberPageFromPagination < 0 ||
                numberPageFromPagination.intValue() > lastPageValue.intValue()) {
            throw new PaginationError("Переданная страница не существует!" + numberPageFromPagination + ">" + lastPageValue);
        }
    }

    private void calculateLastPageValue(Integer countRecordsInTable, Integer countRecordsShown) {
        if (this.countRecordsInTable % countRecordsShown == 0 && countRecordsInTable != 0) {
            lastPageValue = this.countRecordsInTable / countRecordsShown - 1;
        } else {
            lastPageValue = this.countRecordsInTable / countRecordsShown;
        }
    }
}