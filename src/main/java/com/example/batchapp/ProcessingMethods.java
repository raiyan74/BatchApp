package com.example.batchapp;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;



public class ProcessingMethods {

    private static final Logger logger = Logger.getLogger(ProcessingMethods.class.getName());
    static void processImages(String inputFolder, String logoFilePath, String laceFilePath, String outputFolder, int opacity, int logoHorValue, int logoVerValue, int logoSizeValue) throws IOException {

        float alpha = (float) opacity /100;
        // Read the PNG logo file
        BufferedImage logoImage = ImageIO.read(new File(logoFilePath));

        //Read the PNG lace file
        BufferedImage laceImage = ImageIO.read(new File(laceFilePath));

        // Iterate through JPG files in the input folder
        File folder = new File(inputFolder.replace("Selected Input Folder: ", ""));


        File[] listOfFiles = folder.listFiles();


        if (listOfFiles != null) {

            for (File file : listOfFiles) {
                //checks if file is either jpg or jpeg
                if (file.isFile() && (file.getName().toLowerCase().endsWith(".jpg") || file.getName().toLowerCase().endsWith(".jpeg")) ) {
                    try {
                        // Read the input JPG file
                        BufferedImage jpgImage = ImageIO.read(file);

                        // Perform image manipulation (e.g., overlay logo on JPG image)

                        //depending on the logo size input the divisor for the image dimension changes
                        int divisor = switch (logoSizeValue) {
                            case 1 -> 7;
                            case 2 -> 6;
                            case 3 -> 5;
                            case 4 -> 4;
                            case 5 -> 3;
                            default -> 1; // Default value if logoSizeValue doesn't match any case
                        };

                        //resizing logo based on image.
                        int logoWidth = jpgImage.getWidth() / divisor; // Example: Set logo width to 1/divisor th of the image width
                        int logoHeight = jpgImage.getHeight() / divisor; // Example: Set logo height to 1/divisor th of the image height
                        BufferedImage resizedLogo = resizeImage(logoImage, logoWidth, logoHeight);

                        //resizing lace based on image.
                        int laceWidth = jpgImage.getWidth(); //lace width is same as image width
                        int laceHeight = laceWidth / 11; // Set height based on width
                        BufferedImage resizedLace = resizeImage(laceImage, laceWidth, laceHeight);

                        Graphics2D g2d = jpgImage.createGraphics();//initialize image


                        int logoSpacing_x = 0;
                        int logoSpacing_y = 0;

                        if (logoHorValue == 5) {
                            logoSpacing_x = (jpgImage.getWidth() / 100) * logoHorValue;
                        } else if (logoHorValue == 80) {
                            logoSpacing_x = (jpgImage.getWidth() / 100 * logoHorValue) - (logoWidth / 2);
                        }

                        if (logoVerValue == 5) {
                            logoSpacing_y = (jpgImage.getHeight() / 100) * logoVerValue;
                        } else if (logoVerValue == 90) {
                            logoSpacing_y = (jpgImage.getHeight() / 100 * logoVerValue) - (logoHeight / 4);
                        }

                        g2d.drawImage(resizedLogo, logoSpacing_x, logoSpacing_y, null); // Draw the logo at position




/*
                        if(logoHorValue==5 && logoVerValue==5) {

                            int logoSpacing_x = ((jpgImage.getWidth() / 100) * (logoHorValue));
                            //System.out.println(logoSpacing_x);
                            int logoSpacing_y = ((jpgImage.getHeight() / 100) * (logoVerValue));
                            //System.out.println(logoSpacing_y);


                            // Combine images by drawing the logo on top of the jpgImage
                            g2d.drawImage(resizedLogo, logoSpacing_x, logoSpacing_y, null); // Draw the logo at position
                        }


                        if(logoHorValue==80 && logoVerValue==5) {

                            int logoSpacing_x = ((jpgImage.getWidth() / 100) * (logoHorValue)) - (logoWidth / 2);
                            //System.out.println(logoSpacing_x);
                            int logoSpacing_y = ((jpgImage.getHeight() / 100) * (logoVerValue));
                            //System.out.println(logoSpacing_y);


                            // Combine images by drawing the logo on top of the jpgImage
                            g2d.drawImage(resizedLogo, logoSpacing_x, logoSpacing_y, null); // Draw the logo at position
                        }

                        if(logoHorValue==5 && logoVerValue==90) {

                            int logoSpacing_x = ((jpgImage.getWidth() / 100) * (logoHorValue));
                            //System.out.println(logoSpacing_x);
                            int logoSpacing_y = ((jpgImage.getHeight() / 100) * (logoVerValue)) - (logoHeight / 4);
                            //System.out.println(logoSpacing_y);


                            // Combine images by drawing the logo on top of the jpgImage
                            g2d.drawImage(resizedLogo, logoSpacing_x, logoSpacing_y, null); // Draw the logo at position
                        }

                        if(logoHorValue==80 && logoVerValue==90) {

                            int logoSpacing_x = ((jpgImage.getWidth() / 100) * (logoHorValue)) - (logoWidth / 2);
                            //System.out.println(logoSpacing_x);
                            int logoSpacing_y = ((jpgImage.getHeight() / 100) * (logoVerValue)) - (logoHeight / 4);
                            //System.out.println(logoSpacing_y);


                            // Combine images by drawing the logo on top of the jpgImage
                            g2d.drawImage(resizedLogo, logoSpacing_x, logoSpacing_y, null); // Draw the logo at position
                        }
*/


                        //changing opacity
                        AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
                        g2d.setComposite(alphaComposite);

                        int yCordinateForLace = (int) (jpgImage.getHeight() * 0.75);
                        g2d.drawImage(resizedLace, 0, yCordinateForLace, null); // Draw the lace at position (0, 3 quarter the way)

                        g2d.dispose();

                        // Save the modified image to the output folder as JPG
                        // Determine the output file extension based on the input file extension
                        String outputExtension = file.getName().toLowerCase().endsWith(".jpg") ? "jpg" : "jpeg";

                        // Save the modified image to the output folder with the determined extension
                        File outputImage = new File(outputFolder.replace("Selected Output Folder: ", ""),
                                file.getName().replaceFirst("[.][^.]+$", "_logoed." + outputExtension));
                        ImageIO.write(jpgImage, outputExtension, outputImage);


                    } catch (IOException e) {
                        logger.log(Level.SEVERE, "An error occurred", e);
                    }

                }
            }
        }

    }

    //the weight and height in the parameter is the desired size, not the original size
    private static BufferedImage resizeImage(BufferedImage originalImage, int width, int height) {
        double aspectRatio = (double) originalImage.getWidth() / originalImage.getHeight();

        // Calculate new width and height while maintaining the aspect ratio
        int newWidth = width;
        int newHeight = (int) (newWidth / aspectRatio);

        // If the calculated new height exceeds the provided height, adjust the width and height accordingly
        if (newHeight > height) {
            newHeight = height;
            newWidth = (int) (newHeight * aspectRatio);
        }

        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImage.createGraphics();

        // Use a high-quality resampling filter
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2.drawImage(originalImage, 0, 0, newWidth, newHeight, null);

        g2.dispose();
        return resizedImage;
    }



}
