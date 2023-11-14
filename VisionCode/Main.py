from RioComms import RioComms
from pupil_apriltags import Detector
import keyboard
import ast
import re
# Initialize RioComms and prepare vision
rioComms = RioComms("10.40.26.2")
tableName = "apriltags"
# Camera parameters for estimating apriltag pose
# cameraParams = (0, 0, 0, 0) # we need to calibrate camera to find the parameters (fx, fy, cx, cy)
# Apriltag size in meters
tagSize = 0.17
# Apriltag detector
# THESE VALUES NEED TO BE TUNED!!! ONLY FAMILY IS CORRECT
at_detector = Detector(
    families="tag36h11",
    nthreads=1,
    quad_decimate=0.3,
    quad_sigma=0.35,
    refine_edges=1,
    decode_sharpening=0.25,
    debug=0
)
# Video capture is 640 pixels by 480 pixels
cap = cv2.VideoCapture(0)
# Desired image size (should be small)
imgSizeX = 200
imgSizeY = 120
# Parameters for object detection
hueMin = 0
saturationMin = 0
valueMin = 0
hueMax = 180
saturationMax = 255
valueMax = 255
while True:
    # Read current frame from the camera
    img = cap.read()
    # Resize image to smaller size
    img = cv2.resize(img, (imgSizeX, imgSizeY))
    # Create HSV version of img (used for game piece detection)
    imgHSV = cv2.cvtColor(img, cv2.COLOR_BGR2HSV)
    # Create a grayscale version of img (used for apriltag detection)
    imgGrayscale = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
    # Create object mask
    objectMask = cv2.inRange(img, (hueMin, saturationMin, valueMin), (hueMax, saturationMax, valueMax))
    # 
