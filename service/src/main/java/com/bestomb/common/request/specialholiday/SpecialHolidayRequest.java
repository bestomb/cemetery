package com.bestomb.common.request.specialholiday;

public class SpecialHolidayRequest {

    private String id;

    private Integer dateMonth;

    private Integer dateDay;

    private String message;

    private Integer type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getDateMonth() {
        return dateMonth;
    }

    public void setDateMonth(Integer dateMonth) {
        this.dateMonth = dateMonth;
    }

    public Integer getDateDay() {
        return dateDay;
    }

    public void setDateDay(Integer dateDay) {
        this.dateDay = dateDay;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
