package com.example.imageclassifier;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ModelConfig {
    public static String MODEL_FILENAME = "driver_imgs.tflite";
    public static final int INPUT_IMG_BATCH_SIZE = 1;
    public static final int INPUT_IMG_SIZE_WIDTH = 224;
    public static final int INPUT_IMG_SIZE_HEIGHT = 224;
    public static final int FLOAT_TYPE_SIZE = 4;
    public static final int PIXEL_SIZE = 3;
    public static final int MODEL_INPUT_SIZE = FLOAT_TYPE_SIZE * INPUT_IMG_BATCH_SIZE * INPUT_IMG_SIZE_WIDTH * INPUT_IMG_SIZE_HEIGHT * PIXEL_SIZE;

    public static final List<String> OUTPUT_LABELS = Collections.unmodifiableList(
            Arrays.asList("Safe Driving", "Texting-Right", "Talking-Right", "Texting-Left", "Talking-Left", "Radio", "Drinking", "Reaching Behind", "Hair and Makeup", "Talking to Passenger"));

    public static final int MAX_CLASSIFICATION_RESULTS = 3;
    public static final float CLASSIFICATION_THRESHOLD = 0.1f;
}
