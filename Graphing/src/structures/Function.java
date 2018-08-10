package structures;

import exceptions.StackOverflowException;
import exceptions.StackUnderflowException;
import exceptions.UnequalBracketsException;

public class Function {
	
	private Stack<String> postFixStack;
	private String parameter;
	private String expression;
	
	
	
	
	public Function(String expression) throws StackOverflowException, StackUnderflowException, UnequalBracketsException {
		this.expression = expression.replace(" ", "");
		this.parameter = "x";
		BinaryTree tree = createTree(this.expression);
		this.postFixStack = tree.traverse();
		//dont reverse stack at the start
		this.postFixStack.reverse();
				
	}
	
	public double evaluate(double x) throws StackUnderflowException, StackOverflowException {
		Stack<String> subStack = substitute(x);
		//System.out.println(subStack);
		Stack<Double> numStack = new Stack<Double>(this.postFixStack.getHeight());
		for(int i=subStack.getHeight(); i>0 ;i--) {
			String pop = subStack.pop();
			double a,b;
			switch(pop) {
			case "+":
				b = numStack.pop();
				a = numStack.pop();
				numStack.push(a+b);
				break;
			case "-":
				b = numStack.pop();
				a = numStack.pop();
				numStack.push(a-b);
				break;
			case "*":
				b = numStack.pop();
				a = numStack.pop();
				numStack.push(a*b);
				break;
			case "/":
				b = numStack.pop();
				a = numStack.pop();
				numStack.push(a/b);
				break;
			case "^":
				b = numStack.pop();
				a = numStack.pop();
				numStack.push(Math.pow(a,b));
				break;
			default:
				numStack.push(Double.valueOf(pop));
				break;
			}
		}
		return numStack.pop();
		
	}
	
	private Stack<String> substitute(double x) throws StackUnderflowException, StackOverflowException {
		Stack<String> copy = new Stack<String>(this.postFixStack);
		Stack<String> subStack = new Stack<String>(this.postFixStack.getHeight());
		for(int i=copy.getHeight(); i>0 ;i--) {
			String pop = copy.pop();
			if(pop == this.parameter) {
				pop = Double.toString(x);
			} 
			subStack.push(pop);
		}
		subStack.reverse();
		return subStack;
	}
	
	private static BinaryTree createTree(String expression) throws UnequalBracketsException {
		expression = checkBracket(expression);
		int leastSigOperatorPos = leastSigOperatorPos(expression);
		if(leastSigOperatorPos == -1) {		//base case
			return new BinaryTree(expression);
		} else {
			String operator = String.valueOf(expression.charAt(leastSigOperatorPos));
			String a = expression.substring(0, leastSigOperatorPos);
			String b = expression.substring(leastSigOperatorPos+1);
			System.out.println(operator);
			System.out.println(a);
			System.out.println(b);
			return new BinaryTree(operator,createTree(a),createTree(b));
		}
	}
	
	private static int leastSigOperatorPos(String input) {
		int parenthesis = 0;
		int leastSigOperatorPos = -1;
		int leastSigOpcode = 1000;
		//used a string to store the operators, as it is essentially a char array but with added utility, such as finding elements
		final String operators = "+-*/^";
		int currentOpcode;
		
		for(int i=0; i<input.length(); i++) {
			char currentChar = input.charAt(i);
			currentOpcode = operators.indexOf(currentChar);
			if(currentOpcode>=0) {
				if((currentOpcode <= leastSigOpcode) && (parenthesis == 0)) {
					leastSigOperatorPos = i;
					leastSigOpcode = currentOpcode;
				}
			} else if(currentChar == '(') {
				parenthesis++;
			} else if(currentChar == ')') {
				parenthesis--;
			}
		}
		return leastSigOperatorPos;
	}
	

	
	private static String checkBracket(String input) throws UnequalBracketsException {
		boolean done = false;
		while(!done) {
			done = true;
			if((input.charAt(0) == '(') && (input.charAt(input.length()-1) == ')')) {
				int countMatching = 1;
				for(int i=1; i<input.length() - 1; i++) {
					if (countMatching == 0) {
						return input;
					} else if(input.charAt(i) == '(') {
						countMatching++;
					} else if(input.charAt(i) == ')') {
						countMatching--;
					}
					
				}
				if(countMatching == 1) {
					input = input.substring(1, input.length() - 1);
					done = false;
				} else {
					throw new UnequalBracketsException(input);
				}
				
			}
		}
		return input;
	}
	
	@Override
	public String toString() {
		return this.expression;
	}
	
	public static void main(String[] args) throws StackOverflowException, StackUnderflowException, UnequalBracketsException {
		/*
		Function f = new Function("4 + 3x");
		Stack<String> s = new Stack<String>(6);
		s.push("-");
		s.push("*");
		s.push("3");
		s.push("x");
		s.push("4");
		System.out.println(s);
		f.postFixStack = s;
		for(int i=0; i<10; i++) {
			System.out.println(i+" - "+f.evaluate(i));
		}
		
		*/
		//System.out.println(leastSigOperatorPos("3x*4^3+"));
		System.out.println(checkBracket("(((3x+2)))"));
		BinaryTree t = createTree("((((x+6))+1)/(x+2))");
		Stack s = t.traverse();
		s.reverse();
		System.out.println(s);
	}

}