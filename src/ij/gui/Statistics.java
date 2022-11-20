package ij.gui;

import ij.ImagePlus;
import ij.measure.Measurements;
import ij.process.ImageProcessor;
import ij.process.ImageStatistics;
import ij.process.ColorProcessor;
import ij.process.ByteProcessor;

public class Statistics implements Measurements {
    
    public static ImageStatistics RGBHistogram(ImagePlus imp, int bins, double histMin, double histMax) {
		ImageProcessor ip = (ColorProcessor)imp.getProcessor();
		ip = ip.crop();
		int w = ip.getWidth();
		int h = ip.getHeight();
		ImageProcessor ip2 = new ByteProcessor(w*3, h);
		ByteProcessor temp = null;
		for (int i=0; i<3; i++) {
			temp = ((ColorProcessor)ip).getChannel(i+1,temp);
			ip2.insert(temp, i*w, 0);
		}
		ImagePlus imp2 = new ImagePlus("imp2", ip2);
		return imp2.getStatistics(AREA+MEAN+MODE+MIN_MAX, bins, histMin, histMax);
	}

	public static int scaleDown(ImageProcessor ip, double threshold) {
		double min = ip.getMin();
		double max = ip.getMax();
		if (max>min)
			return (int)(((threshold-min)/(max-min))*255.0);
		else
			return 0;
	}
}
