package com.pomvom.opencv;


import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
//import org.opencv.highgui.Highgui;
import org.opencv.core.*;
//import org.opencv.imgcodecs; // imread, imwrite, etc
import org.opencv.imgproc.Imgproc;
//import org.opencv.videoio;   // VideoCapture
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.Objdetect;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.lang.Boolean.TRUE;

public class FaceDetector {

    static {

        System.loadLibrary("opencv_core");

//        System.loadLibrary("libopencv_java320");
        System.out.println(Core.NATIVE_LIBRARY_NAME);
    }

    static final double SCALE_FACTOR=1.1;
    double maxFaceSizePrecent = 1;
    double minFaceSizePrecent = 0.05;

    public int facedetect(String imgpath) {

        System.out.println("\nRunning FaceDetector");
        ClassLoader cl = getClass().getClassLoader();

        Path p= Paths.get(System.getProperty("user.home") + "/Work/PaparazMe/PaparazMe-BL-OpenCV/build/resources/main/" + "haarcascade_frontalface_alt.xml");
        String resFile=p.getFileName().toString();
//        String resFile = System.getProperty("user.home") + "/Work/PaparazMe/PaparazMe-BL-OpenCV/build/resources/main/" + "haarcascade_frontalface_alt.xml";
        System.out.println(resFile);

        CascadeClassifier faceDetector = new CascadeClassifier(resFile);
        // System.getProperty("user.home") + "/.paparazme/" + "haarcascade_frontalface_alt.xml");

//        CascadeClassifier faceDetector = new CascadeClassifier(cl.getResource("haarcascade_frontalface_alt.xml").getPath().substring(1));
        Mat image = Imgcodecs.imread(imgpath);
//        String test=FaceDetector.class.getResource("lbpcascade_frontalface.xml").getFile();
        faceDetector.load(("lbpcascade_frontalface.xml"));
        MatOfRect faceDetections = new MatOfRect();
        Size minFaceSize= new Size(image.size().width * minFaceSizePrecent,image.size().height * minFaceSizePrecent);
        Size maxFaceSize= new Size(image.size().width * maxFaceSizePrecent, image.size().height * maxFaceSizePrecent);
        faceDetector.detectMultiScale(image, faceDetections, SCALE_FACTOR, 3, 0, minFaceSize, maxFaceSize);

        System.out.println(String.format("Detected %s faces", faceDetections.toArray().length));
//        Engine.logger.info(String.format("Detected %s faces", faceDetections.toArray().length));

//        if (faceDetections.toArray().length==0) Engine.nofacepicscounter++;

        for (Rect rect : faceDetections.toArray()) {
            Imgproc.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
                    new Scalar(0, 255, 0));
            Mat faceImage = image.submat(rect);
            Imgcodecs.imwrite(String.valueOf(System.currentTimeMillis()) + ".jpg", faceImage);
            System.out.println(String.valueOf(System.currentTimeMillis()) + ".jpg");
        }
//        for (Rect rect : faceDetections.toArray()) {
//            Imgproc.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
//                    new Scalar(0, 255, 0));
//            rectCrop = new Rect(rect.x, rect.y, rect.width, rect.height);
//        }

        //TODO if we would want to see the face rect
        String filename = "/home/gilad/Desktop/out.png";
        System.out.println(String.format("Writing %s", filename));
        Imgcodecs.imwrite(filename, image);
        return faceDetections.toArray().length;
    }

    public static void main(String[] args) {
//        System.out.println(java.lang.System.getenv("HOMEPATH"));
//        System.out.println(Core.NATIVE_LIBRARY_NAME);
        FaceDetector checkdetect=new FaceDetector();
        checkdetect.facedetect("/home/gilad/Desktop/out.jpg");
    }
}

//PARAMETERS FOR detectMultiScale:
/* scaleFactor – Parameter specifying how much the image size is reduced at each image scale.
                1.05 is a good possible value for this, which means you use a small step for resizing, i.e. reduce size by 5%,
                as smaller as it gets it makes the algo work slower
   minNeighbors – Parameter specifying how many neighbors each candidate rectangle should have to retain it.
                This parameter will affect the quality of the detected faces. Higher value results in less detections but with higher quality. 3~6 is a good value for it.

 FOR NOW BEST RES FOR MOST PICTURES GIVES :         faceDetector.detectMultiScale(image, faceDetections, 1.1, 3, 0 | Objdetect.CASCADE_SCALE_IMAGE,
                new Size(100,100), new Size());


 */