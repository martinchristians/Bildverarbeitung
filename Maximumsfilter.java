package bv_ws1920;

import java.util.ArrayList;
import java.util.Collections;

public class Maximumsfilter implements Filter {
	RasterImage sourceImage;
	RasterImage destinationImage;
	int kernelWidth;
	int kernelHeight;
	
	@Override
	public void setSourceImage(RasterImage sourceImage) {
		this.sourceImage = sourceImage;
	}

	@Override
	public void setDestinationImage(RasterImage destinationImage) {
		this.destinationImage = destinationImage;
	}

	@Override
	public void setKernelWidth(int kernelWidth) {
		this.kernelWidth = kernelWidth;
	}

	@Override
	public void setKernelHeight(int kernelHeight) {
		this.kernelHeight = kernelHeight;
	}

	@Override
	public void apply() {
	//Achsen-Werten von "SourceImage" deklarieren
	int width = sourceImage.width; //mulai dari 1
	int height = sourceImage.height;
	
	//deklarieren halb length in x und y Achse des Kernels
	int halbKernelX = (kernelWidth-1)/2;
	int halbKernelY = (kernelHeight-1)/2;

	//Verlauf allen Pixeln
	for(int y=0; y<height; y++){
		for(int x=0; x<width; x++){
			ArrayList<Integer> pixel = new ArrayList<Integer>(); 
			//Verlauf im Kernel. Hier wird alle Werte des Kernels in ArrayList heisst "pixel" gespeicher.
			for(int yKernel=(-halbKernelY); yKernel<=halbKernelY; yKernel++){
				for(int xKernel=(-halbKernelX); xKernel<=halbKernelX; xKernel++){
					int nx = x + xKernel;
					int ny = y + yKernel;
					//Randbehandlung: Konstantforsetzen
					if(nx<0){
						nx = 0;
					} if(nx>width-1){
						nx = width-1;
					} if(ny<0){
						ny = 0;
					} if(ny>height-1){
						ny = height-1;
					}
					int posKernel = ny * width + nx;
					int wertKernel = 0;
					wertKernel = sourceImage.argb[posKernel];
					
					/*
					//Randbehandlung: Konstanteen Wert
					int posKernel = ny * width + nx;
					int wertKernel = 0;
					if(nx<0 || nx>=width || ny<0 || ny>=height){
						wertKernel =  (0xFF000000) | (0<<16) | (0<<8) | 0; //alle Pixel bei Randbehandlung wird als weiss eingesetzt
					} else {
						wertKernel = sourceImage.argb[posKernel];
					}
					*/
					
					//neue argb Werte deklarieren
					int r = wertKernel & (0xFF);
					pixel.add(r);
				}
			}
		Collections.sort(pixel); //aufsteigen sortieren
		int maxPixel = pixel.get(pixel.size()-1);
		int newPos = y * width + x;
		destinationImage.argb[newPos] = (0xFF000000) | (maxPixel<<16) | (maxPixel<<8) | maxPixel;
		}
	}
	}
}
