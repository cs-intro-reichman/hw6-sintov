import java.awt.Color;
import java.awt.Image;

/** A library of image processing functions. */
public class Runigram {

	public static void main(String[] args) {
	    
		//// Hide / change / add to the testing code below, as needed.
		
		// Tests the reading and printing of an image:	
		Color[][] tinypic = read("tinypic.ppm");
		print(grayScaled(tinypic));
		print(scaled(tinypic,3,5));
	

		// Creates an image which will be the result of various 
		// image processing operations:
		Color[][] image;

		// Tests the horizontal flipping of an image:
		//image = flippedHorizontally(tinypic);
		//System.out.println();
		//print(image);


		
		//// Write here whatever code you need in order to test your work.
		//// You can continue using the image array.
	}

	/** Returns a 2D array of Color values, representing the image data
	 * stored in the given PPM file. */
	public static Color[][] read(String fileName) {
		In in = new In(fileName);
		// Reads the file header, ignoring the first and the third lines.
		in.readString();
		int numCols = in.readInt();
		int numRows = in.readInt();
		in.readInt();
		// Creates the image array
		Color[][] image = new Color[numRows][numCols];
		int M = image.length;
		int N = image[0].length;
		for (int i=0; i<M; i++){
			for (int j=0; j<N; j++){
				Color newcColor = new Color(in.readInt(), in.readInt(), in.readInt());
				image[i][j] = newcColor;
			}
		}
		// Reads the RGB values from the file into the image array. 
		// For each pixel (i,j), reads 3 values from the file,
		// creates from the 3 colors a new Color object, and 
		// makes pixel (i,j) refer to that object.
		//// Replace the following statement with your code.
		return image;
	}

    // Prints the RGB values of a given color.
	private static void print(Color c) {
	    System.out.print("(");
		System.out.printf("%3s,", c.getRed());   // Prints the red component
		System.out.printf("%3s,", c.getGreen()); // Prints the green component
        System.out.printf("%3s",  c.getBlue());  // Prints the blue component
        System.out.print(")  ");
	}

	// Prints the pixels of the given image.
	// Each pixel is printed as a triplet of (r,g,b) values.
	// This function is used for debugging purposes.
	// For example, to check that some image processing function works correctly,
	// we can apply the function and then use this function to print the resulting image.
	private static void print(Color[][] image) {
		int M = image.length;
		int N = image[0].length;
		for (int i=0; i<M; i++){
			for (int j=0; j<N; j++){
				print(image[i][j]);
			}
		}
	}
	
	/**
	 * Returns an image which is the horizontally flipped version of the given image. 
	 */
	public static Color[][] flippedHorizontally(Color[][] image) {
		Color[][] horizontalFlipImage = new Color[image.length][image[0].length];
		int N = image[0].length;
		for (int i = 0; i < horizontalFlipImage.length; i++){
			for(int j = 0; j < horizontalFlipImage[0].length; j++){
				horizontalFlipImage[i][j] = image[i][N-j-1];
			}
		} 
		return horizontalFlipImage;
	}
	
	/**
	 * Returns an image which is the vertically flipped version of the given image. 
	 */
	public static Color[][] flippedVertically(Color[][] image){
		Color[][] verticalFlipImage = new Color[image.length][image[0].length];
		int M = image.length;
		for (int i = 0; i < verticalFlipImage.length; i++){
			for(int j = 0; j < verticalFlipImage[0].length; j++){
				verticalFlipImage[i][j] = image[M-i-1][j];
			}
		} 
		//// Replace the following statement with your code
		return verticalFlipImage;
	}
	
	// Computes the luminance of the RGB values of the given pixel, using the formula 
	// lum = 0.299 * r + 0.587 * g + 0.114 * b, and returns a Color object consisting
	// the three values r = lum, g = lum, b = lum.
	private static Color luminance(Color pixel) {
		int lum = (int)(0.299*pixel.getRed() + 0.587*pixel.getGreen() + 0.114*pixel.getBlue());
		Color lumPixel = new Color(lum,lum,lum);
		return lumPixel;
	}
	
	/**
	 * Returns an image which is the grayscaled version of the given image.
	 */
	public static Color[][] grayScaled(Color[][] image) {
		for(int i = 0; i<image.length; i++){
			for(int j = 0; j<image[0].length; j++){
				image[i][j] = luminance(image[i][j]);
			}
		}
		return image;
	}	
	
	/**
	 * Returns an image which is the scaled version of the given image. 
	 * The image is scaled (resized) to have the given width and height.
	 */
	public static Color[][] scaled(Color[][] image, int width, int height) {
		Color[][] scaledPic = new Color[height][width];
		for (int i=0; i<height; i++){
			for(int j=0; j<width; j++){
				scaledPic[i][j] = image[i*image.length/height][j*image[0].length/width];
			}
		}
		return scaledPic;
	}
	
	/**
	 * Computes and returns a blended color which is a linear combination of the two given
	 * colors. Each r, g, b, value v in the returned color is calculated using the formula 
	 * v = alpha * v1 + (1 - alpha) * v2, where v1 and v2 are the corresponding r, g, b
	 * values in the two input color.
	 */
	public static Color blend(Color c1, Color c2, double alpha) {
		int newRed = (int)(alpha * c1.getRed() + (1-alpha) * c2.getRed());
		int newGreen = (int)(alpha * c1.getGreen() + (1-alpha) * c2.getGreen());
		int newBlue = (int)(alpha * c1.getBlue() + (1-alpha) * c2.getBlue());
		Color newBlended = new Color(newRed,newGreen,newBlue);
		return newBlended;
	}
	
	/**
	 * Cosntructs and returns an image which is the blending of the two given images.
	 * The blended image is the linear combination of (alpha) part of the first image
	 * and (1 - alpha) part the second image.
	 * The two images must have the same dimensions.
	 */
	public static Color[][] blend(Color[][] image1, Color[][] image2, double alpha) {
		Color[][] blendedPic = new Color[image1.length][image1[0].length];
		for (int i=0; i<image1.length; i++){
			for(int j=0; j<image1[0].length; j++){
				blendedPic[i][j] = blend(image1[i][j], image2[i][j], alpha);
			}
		}
		return blendedPic;
	}

	/**
	 * Morphs the source image into the target image, gradually, in n steps.
	 * Animates the morphing process by displaying the morphed image in each step.
	 * Before starting the process, scales the target image to the dimensions
	 * of the source image.
	 */
	public static void morph(Color[][] source, Color[][] target, int n) {
		if (source.length != target.length || source[0].length != target[0].length){
			target = scaled(target, source[0].length, source.length);
		}
		double alpha = 0;
		for (int i=n; i>=0; i--){
			System.out.println("hi");
			alpha = (double)i/(double)n;
			Runigram.display(Runigram.blend(source, target, alpha));
			StdDraw.pause(500);
		}
	}
	
	/** Creates a canvas for the given image. */
	public static void setCanvas(Color[][] image) {
		StdDraw.setTitle("Runigram 2023");
		int height = image.length;
		int width = image[0].length;
		StdDraw.setCanvasSize(height, width);
		StdDraw.setXscale(0, width);
		StdDraw.setYscale(0, height);
        // Enables drawing graphics in memory and showing it on the screen only when
		// the StdDraw.show function is called.
		StdDraw.enableDoubleBuffering();
	}

	/** Displays the given image on the current canvas. */
	public static void display(Color[][] image) {
		int height = image.length;
		int width = image[0].length;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				// Sets the pen color to the pixel color
				StdDraw.setPenColor( image[i][j].getRed(),
					                 image[i][j].getGreen(),
					                 image[i][j].getBlue() );
				// Draws the pixel as a filled square of size 1
				StdDraw.filledSquare(j + 0.5, height - i - 0.5, 0.5);
			}
		}
		StdDraw.show();
	}
}

