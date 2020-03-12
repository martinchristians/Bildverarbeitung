package bv_ws1920;

import java.util.ArrayList;
import java.util.Collections;

public class Minimumsfilter implements Filter {
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
	int width = sourceImage.width;
	int height = sourceImage.height;
	
	int halbKernelX = (kernelWidth-1)/2;
	int halbKernelY = (kernelHeight-1)/2;

	for(int y=0; y<height-1; y++){
		for(int x=0; x<width-1; x++){
			ArrayList<Integer> pixel = new ArrayList<Integer>();
			for(int yKernel=(-halbKernelY); yKernel<=halbKernelY; yKernel++){
				for(int xKernel=(-halbKernelX); xKernel<=halbKernelX; xKernel++){
					int nx = x + xKernel;
					int ny = y + yKernel;
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
					
					int r = wertKernel & (0xFF);
					pixel.add(r);
				}
			}
		Collections.sort(pixel);
		int minPixel = pixel.get(0);
		int newPos = y * width + x;
		destinationImage.argb[newPos] = (0xFF000000) | (minPixel<<16) | (minPixel<<8) | minPixel;
		}
	}
	}
}
