package com.georgevich.bruno.ibill;

import java.io.Serializable;

/**
 * Created by bruno on 15/03/16.
 */
public class Bill implements Serializable{
    private String m_value;
    private String m_name;
    private String m_dueDate;

    public Bill(double v, String n, String d) {
        m_value=String.valueOf(v); m_name=n; m_dueDate=d;
    }
    public Bill(String v, String n, String d) {
        m_value=v; m_name=n; m_dueDate=d;
    }

    public String getDueDate() {
        return m_dueDate;
    }

    public String geName() {
        return m_name;
    }

    public String getValue() {
        return "$" + m_value;
    }

    public double getNumberedValue() {
        return Double.parseDouble(m_value);
    }

    public String printDetailed() {
        return " (" + m_dueDate + ") -> " + m_name + " : " + getValue();
    }
}
