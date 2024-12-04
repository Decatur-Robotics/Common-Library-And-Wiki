from RioComms import RioComms
import cv2
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
cap = cv2.VideoCapture(1)

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
    # End program if "z" button is pressed
    if keyboard.is_pressed("z"):
        exit()

    # Read current frame from the camera
    _, img = cap.read()

    # Resize image to smaller size
    img = cv2.resize(img, (imgSizeX, imgSizeY))

    #
    # GAME PIECE DETECTION
    #

    # Create HSV version of img (used for game piece detection)
    imgHSV = cv2.cvtColor(img, cv2.COLOR_BGR2HSV)

    # Create object mask
    kernel = cv2.getStructuringElement(cv2.MORPH_ELLIPSE, ksize=(25, 25))
    objectMask = cv2.inRange(imgHSV, (hueMin, saturationMin, valueMin), (hueMax, saturationMax, valueMax))
    objectMask = cv2.medianBlur(objectMask, 25)
    objectMask = cv2.erode(objectMask, kernel)
    objectMask = cv2.dilate(objectMask, kernel)

    # Find object contours
    objectCnts = cv2.findContours(objectMask.copy(), cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)

    # Find the contours of visible objects
    objectCntsAreas = []

    for i in objectCnts[0]:
        M = cv2.moments(i)

        objectCntsAreas.append(M["m00"])

    if len(objectCntsAreas) > 0:
        # Find the largest (and theoretically closest) object
        maxValue = max(objectCntsAreas)

        maxObjectCntsIndex = objectCntsAreas.index(maxValue)

        # Find pixel position of object
        M = cv2.moments(objectCnts[0][maxObjectCntsIndex])

        cX = int((M["m10"] / M["m00"])) - (imgSizeX / 2)
        cY = int((M["m01"] / M["m00"])) - (imgSizeY / 2)

        # Send pixel position of object to network tables if an object is visible
        rioComms.send("objects", "Object X", cX)
        rioComms.send("objects", "Object Y", cY)
        rioComms.send("objects", "Object Visible", 1)

    else:
        # Send zeroes to network tables if no object is visible
        rioComms.send("objects", "Object X", 0)
        rioComms.send("objects", "Object Y", 0)
        rioComms.send("objects", "Object Visible", 0)

    #
    # APRILTAG DETECTION
    #

    # Create grayscale version of img (used for apriltag detection)
    imgGrayscale = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)

    # Detect apriltags
    apriltags = at_detector.detect(imgGrayscale)

    # Remove apriltags that might not be identified correctly
    # HAMMING = 0 WAS USED FOR 16h5, WITH 36h11 WE SHOULD BE ABLE TO INCREASE THIS TO 7+
    apriltags = [x for x in apriltags if x.hamming == 0]

    for i in range(len(apriltags)):
        # Convert returned apriltag center into a readable form
        centerXY = ast.literal_eval((re.sub(" +", " ", ((str(apriltags[i].center).replace("[", "")).replace("]", "")).strip())).replace(" ", ", "))

        # Calculate pixel position of apriltag center
        centerXY = list(centerXY)
        centerXY[0] -= (imgSizeX / 2)
        centerXY[1] -= (imgSizeY / 2)

        # Send apriltag position to network tables
        rioComms.send("apriltags", "Tag " + str(apriltags[i].tag_id) + " X", centerXY[0])
        rioComms.send("apriltags", "Tag " + str(apriltags[i].tag_id) + " Y", centerXY[1])

    cv2.imshow("image", img)
    cv2.imshow("image HSV", imgHSV)
    cv2.imshow("object mask", objectMask)

    cv2.waitKey(5)
