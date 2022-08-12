package edu.school21.printer.app;
import java.awt.image.BufferedImage;

import edu.school21.printer.logic.Logic;

public class Main {
	private static char WhitePixel;
	private static char BlackPixel;
	private static BufferedImage image;
	public static void main(String[] args) {
		if (args.length != 2){
			System.out.println("invalid input");
			return;
		}
		WhitePixel = args[0].charAt(0);
		BlackPixel = args[1].charAt(0);

		Logic logic = new Logic();
		logic.printImage(BlackPixel, WhitePixel, "src/resources/image.bmp");
	}
}
