package me.diley;

import java.io.Serializable;
import java.math.BigDecimal;

public abstract class Pracownik implements Serializable {
    private String Pesel;

    private String Name;
    private String LastName;

    protected String Position;

    private BigDecimal Salary;
    private String PhoneNumber;

    public void setPesel(String pesel){
        this.Pesel = pesel;
    }
    public String getPesel(){
        return this.Pesel;
    }

    public void setName(String name){
        this.Name = name;
    }
    public String getName(){
        return this.Name;
    }
    public void setLastName(String lastname){
        this.LastName = lastname;
    }
    public String getLastName(){
        return this.LastName;
    }
    public abstract void setPosition();
    public abstract String getPosition();

    public void setSalary(BigDecimal salary){
        this.Salary = salary;
    }
    public BigDecimal getSalary(){
        return this.Salary;
    }

    public void setPhoneNumber(String phonenumber){
        this.PhoneNumber = phonenumber;
    }
    public String getPhoneNumber(){
        return this.PhoneNumber;
    }
    public abstract void showEmployee();
}

