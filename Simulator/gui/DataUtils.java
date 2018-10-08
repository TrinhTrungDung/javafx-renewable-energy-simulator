package gui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import interfaces.AbstractComponent;
import vgu.consumer.Consumer;
import vgu.consumer.ConsumerFactory;
import vgu.control.Control;
import vgu.generator.Generator;
import vgu.generator.GeneratorFactory;

public class DataUtils {
	
	public static void generateConsumers(Control control) throws FileNotFoundException {
		PrintWriter writer = new PrintWriter(new File("consumers.csv"));
		StringBuilder builder = new StringBuilder();
		
		builder.append("Name");
		builder.append(",");
		builder.append("Max Power");
		builder.append(",");
		builder.append("Min Power");
		builder.append(",");
		builder.append("Max Change");
		builder.append(",");
		builder.append("Min Change");
		builder.append("\n");
		
		for (AbstractComponent consumer : control.getConsumers()) {
			builder.append(((Consumer) consumer).getName());
			builder.append(",");
			builder.append(consumer.getMaxPower());
			builder.append(",");
			builder.append(consumer.getMinPower());
			builder.append(",");
			builder.append(consumer.getMaxChange());
			builder.append(",");
			builder.append(consumer.getMinChange());
			builder.append("\n");
		}
		
		writer.write(builder.toString());
		writer.close();
	}
	
	public static void generateGenerators(Control control) throws FileNotFoundException {
		PrintWriter writer = new PrintWriter(new File("generators.csv"));
		StringBuilder builder = new StringBuilder();
		
		builder.append("Name");
		builder.append(",");
		builder.append("Max Power");
		builder.append(",");
		builder.append("Min Power");
		builder.append(",");
		builder.append("Max Change");
		builder.append(",");
		builder.append("Min Change");
		builder.append("\n");
		
		for (AbstractComponent generator : control.getGenerators()) {
			builder.append(((Generator) generator).getName());
			builder.append(",");
			builder.append(generator.getMaxPower());
			builder.append(",");
			builder.append(generator.getMinPower());
			builder.append(",");
			builder.append(generator.getMaxChange());
			builder.append(",");
			builder.append(generator.getMinChange());
			builder.append("\n");
		}
		
		writer.write(builder.toString());
		writer.close();
	}
	
}
