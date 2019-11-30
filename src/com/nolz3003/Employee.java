package com.nolz3003;

/**
 * Employee class.
 *
 * @author austinnolz - The model class for an employee, which details employee data and methods.
 */
public class Employee {

  public Employee(String name, String pw) {

    if (name.matches("[a-zA-Z]+\\s[a-zA-Z]+")) {
      setName(new StringBuilder(name));
      String[] firstAndLast = name.split("\\s");
      firstName = firstAndLast[0];
      lastName = firstAndLast[1].toLowerCase();

      setUsername();
      setEmail();

    } else {
      username = "default";
      this.name = new StringBuilder(name);
      email = "user@oracleacademy.Test";
    }

    isValidPassword(pw);

  }

  private StringBuilder name;

  private String firstName;

  private String lastName;

  private String username;

  private String email;

  private String password;

  public StringBuilder getName() {
    return name;
  }

  public void setName(StringBuilder name) {
    this.name = name;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername() {
    this.username = firstName.charAt(0) + lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail() {
    this.email = firstName + "." + lastName + "@oracleacademy.Test";
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void isValidPassword(String pw) {
    if (pw.matches(".*[A-Z].*") && pw.matches(".*[a-z].*") && !pw.matches("[a-zA-Z0-9]*")) {
      setPassword(pw);
    } else {
      setPassword("pw");
    }
  }

  @Override
  public String toString() {
    return "Employee Details\n"
        + "Name : " + getName() + "\n"
        + "Username : " + getUsername() + "\n"
        + "Email : " + getEmail() + "\n"
        + "Initial Password : " + getPassword();

  }
}
