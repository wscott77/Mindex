package com.mindex.challenge.data;

public class Compensation {
    private Employee employee;
    private Salary salary;

    public Compensation(){
    }

    public Compensation(Employee employee, Salary salary) {
        this.employee = employee;
        this.salary = salary;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Salary getSalary() {
        return salary;
    }

    public void setSalary(Salary salary) {
        this.salary = salary;
    }
}
