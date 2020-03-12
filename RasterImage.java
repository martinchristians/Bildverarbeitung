// BV Ue1 WS2019/20 Vorgabe
//
// Copyright (C) 2019 by Klaus Jung
// All rights reserved.
// Date: 2019-09-28

package bv_ws1920;

import java.io.File;
import java.util.Arrays;
import java.util.Random;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

public class RasterImage {
	
	private static final int gray  = 0xffa0a0a0;

	public int[] argb;	// pixels represented as ARGB values in scanline order
	public int width;	// image width in pixels
	public int height;	// image height in pixels
	
	public RasterImage(int width, int height) {
		// creates an empty RasterImage of given size
		this.width = width;
		this.height = height;
		argb = new int[width * height];
		Arrays.fill(argb, gray);
	}
	
	public RasterImage(RasterImage src) {
		// copy constructor
		this.width = src.width;
		this.height = src.height;
		argb = src.argb.clone();
	}
	
	public RasterImage(File file) {
		// creates an RasterImage by reading the given file
		Image image = null;
		if(file != null && file.exists()) {
			image = new Image(file.toURI().toString());
		}
		if(image != null && image.getPixelReader() != null) {
			width = (int)image.getWidth();
			height = (int)image.getHeight();
			argb = new int[width * height];
			image.getPixelReader().getPixels(0, 0, width, height, PixelFormat.getIntArgbInstance(), argb, 0, width);
		} else {
			// file reading failed: create an empty RasterImage
			this.width = 256;
			this.height = 256;
			argb = new int[width * height];
			Arrays.fill(argb, gray);
		}
	}
	
	public RasterImage(ImageView imageView) {
		// creates a RasterImage from that what is shown in the given ImageView
		Image image = imageView.getImage();
		width = (int)image.getWidth();
		height = (int)image.getHeight();
		argb = new int[width * height];
		image.getPixelReader().getPixels(0, 0, width, height, PixelFormat.getIntArgbInstance(), argb, 0, width);
	}
	
	public void setToView(ImageView imageView) {
		// sets the current argb pixels to be shown in the given ImageView
		if(argb != null) {
			WritableImage wr = new WritableImage(width, height);
			PixelWriter pw = wr.getPixelWriter();
			pw.setPixels(0, 0, width, height, PixelFormat.getIntArgbInstance(), argb, 0, width);
			imageView.setImage(wr);
		}
	}
	
	
	// image point operations to be added here
	
	public void convertToGray() {
		// TODO: convert the image to grayscale
		for (int pos = 0;pos<argb.length;pos++){
		
			int r = (argb[pos] >> 16) & 0xff;
			int g = (argb[pos] >>  8) & 0xff;
			int b =  argb[pos]        & 0xff;
			
			int grau = (r+g+b)/3;
			
			argb[pos] = (0xFF000000) | (grau<<16) | (grau<<8) | grau;
		}
	}
	
	/**
	 * @param quantity The fraction of pixels that need to be modified
	 * @param strength The brightness to be added or subtracted from a pixel's gray level
	 */
	public void addNoise(double quantity, int strength) {
		// TODO: add noise with the given quantity and strength
		Random rand = new Random();
		for(int i=0; i<quantity*argb.length;i++){
			int pos=rand.nextInt(argb.length-1);
			int r = (argb[pos] >> 16) & 0xff;
			int g = (argb[pos] >>  8) & 0xff;
			int b =  argb[pos]        & 0xff;
			
			int grau = (r+g+b)/3;
			
			if(i%2==0){ //bestimmen, ob pixel dunkler oder heller werden soll.
				grau = grau-strength;
			} else {
				grau = grau+strength;
			}
			
			if(grau>255){
				grau=255;
			} if(grau<0){
				grau=0;
			}
			
			argb[pos] = (0xFF000000) | (grau<<16) | (grau<<8) | grau;
		}
	}
	

}
