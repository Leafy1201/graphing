package layer;

import exceptions.StackOverflowException;
import exceptions.StackUnderflowException;
import exceptions.UnequalBracketsException;
import structures.ExplicitFunction;

public class ExplicitXFunctionCartesianLayer extends CartesianLayer {

	private ExplicitFunction f;

	public ExplicitXFunctionCartesianLayer(String expression) {
		super();

		try {
			this.f = new ExplicitFunction(expression, 'x');
		} catch (StackOverflowException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (StackUnderflowException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnequalBracketsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (StringIndexOutOfBoundsException e) {
			e.printStackTrace();
		}
	}
	
	public ExplicitXFunctionCartesianLayer(ExplicitFunction f) {
		super();
		this.f = f;
	}

	public void draw() {
		this.clearCanvas();
		gc.setLineWidth(2);
		gc.setStroke(color);
		double x1, x2, y1, y2;
		x1 = this.minX.doubleValue();
		try {
			y1 = f.evaluate(x1);
		} catch (Exception e) {
			return;
		}

		double step = (this.maxX.doubleValue() - this.minX.doubleValue()) / this.steps.doubleValue();

		for (x2 = this.minX.doubleValue() + step; x2 < this.maxX.doubleValue(); x2 = x2 + step) {
			try {
				// System.out.println(this.convertX(x1) + " - " + this.convertY(y1));
				y2 = f.evaluate(x2);
				gc.strokeLine(this.convertX(x1), this.convertY(y1), this.convertX(x2), this.convertY(y2));
				x1 = x2;
				y1 = y2;
			} catch (ArithmeticException e) {
				x1 = x2 + step;
				x2 = x1;
				y1 = f.evaluate(x1);
			}

		}

	}

	@Override
	public String toString() {
		return this.f.toString();
	}

}
