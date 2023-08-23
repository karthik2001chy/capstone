package exception;

 

public class MyException extends RuntimeException {

 

public MyException(String s) { // Parameterized constructor to display the user defined message

super(s);

}

 

}