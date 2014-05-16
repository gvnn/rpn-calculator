# Reverse Polish notation calulator

Command-line based RPN calculator

[http://en.wikipedia.org/wiki/Reverse_Polish_notation](http://en.wikipedia.org/wiki/Reverse_Polish_notation)

## Intro

The calculator waits for user input and expects to receive strings containing whitespace
separated lists of numbers and operators.
 
Numbers are pushed on to the stack.  Operators operate on numbers that are on the stack.
 
Available operators are `+`, `-`, `*`, `/`, `sqrt`, `pow`, `undo`, `clear`
 
Operators pop their parameters off the stack, and push their results back onto the stack.
 
- The `clear` operator removes all items from the stack.

- The `undo` operator undoes the previous operation.  "undo undo" will undo the previous two operations.

- `sqrt` performs a square root on the top item from the stack

- `pow` returns the top item from the stack power 2

- The `+`, `-`, `*`, `/` operators perform addition, subtraction, multiplication and division respectively on the top two items from the stack. 
 
After processing an input string, the calculator displays the current contents of the stack as a space-separated list. Numbers are stored on the stack to at least 15 decimal places of precision, but displayed to 10 decimal places (or less if it causes no loss of precision).
 
If an operator cannot find a sufficient number of parameters on the stack, a warning is displayed:
 
`operator <operator> (position: <pos>): insufficient parameters`

After displaying the warning, all further processing of the string terminates and the current state of the stack is displayed.

## Requirements

- Implemented and tested using Java 7

- Tests require JUnit and Mockito

- Project dependencies and compiling managed by Maven


## Compile, Test, Run and Packaging

- Compile: `mvn compile`

- Test: `mvn test`

- Run: `mvn exec:java`

- Packaging: `mvn package`, compiled jar in *target/* folder