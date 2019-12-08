package com.nolz3003;

/**
 * The Employee class is a model for a new employee.
 *
 * @author austinnolz - The model class for an employee, which details employee data and methods.
 */
public class Employee {

  /**
   * An Employee constructor accepting a full name and password.
   *
   * @param name - Employee full name
   * @param pw - password
   */
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

  private String getUsername() {
    return username;
  }

  private void setUsername() {
    this.username = firstName.charAt(0) + lastName;
  }

  private String getEmail() {
    return email;
  }

  private void setEmail() {
    this.email = firstName + "." + lastName + "@oracleacademy.Test";
  }

  private String getPassword() {
    return password;
  }

  private void setPassword(String password) {
    this.password = password;
  }

  /**
   * The isValidPassword method only sets the password if it fits the regex patterns: uppercase
   * letter, lowercase letter, and special character.
   *
   * @param pw - password to be checked.
   */
  private void isValidPassword(String pw) {
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
