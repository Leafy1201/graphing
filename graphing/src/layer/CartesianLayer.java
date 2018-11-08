package layer;

import application.PlotPaneController;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public abstract class CartesianLayer extends Layer {
	
	@Override
	public abstract void draw();
	
	@Override
	public void bindProperties(PlotPaneController controller) {
		this.steps = new SimpleIntegerProperty(controller.getSteps().intValue());
		this.minX = new SimpleDoubleProperty(controller.getInputLayer().getMinX().doubleValue());
		this.maxX = new SimpleDoubleProperty(controller.getInputLayer().getMaxX().doubleValue());
		this.minY = new SimpleDoubleProperty(controller.getInputLayer().getMinY().doubleValue());
		this.maxY = new SimpleDoubleProperty(controller.getInputLayer().getMaxY().doubleValue());
		this.pixelWorthX = new SimpleDoubleProperty(controller.getPixelWorthX().doubleValue());
		this.pixelWorthY = new SimpleDoubleProperty(controller.getPixelWorthY().doubleValue());
		
		
		canvas.heightProperty().bind(controller.getPlotPane().heightProperty());
		canvas.widthProperty().bind(controller.getPlotPane().widthProperty());
		
	
		
		this.steps.bind(controller.getSteps());
		this.minX.bind(controller.getInputLayer().getMinX());
		this.maxX.bind(controller.getInputLayer().getMaxX());
		this.minY.bind(controller.getInputLayer().getMinY());
		this.maxY.bind(controller.getInputLayer().getMaxY());
		this.pixelWorthX.bind(controller.getPixelWorthX());
		this.pixelWorthY.bind(controller.getPixelWorthY());
		
		//System.out.println("BOUND");
	}
	
	protected double convertX(double x) {
		x = x - this.minX.doubleValue();
		x = x /this.pixelWorthX.doubleValue();
		return x;
	}
	
	protected double convertY(double y) {
		y = this.maxY.doubleValue() - y;
		y = y/this.pixelWorthY.doubleValue();
		return y;
	}
	
}
