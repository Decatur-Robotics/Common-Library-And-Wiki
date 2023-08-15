# Vision

We use vision for two things. Robot alignment with the field and object detection. For alignment we use Apriltags (or sometimes limelight but that is being phased out of use), which look like QR codes on the field.

Vision uses OpenCV. (Make sure to include instructions on how to install OpenCV)
Most code runs in a while true loop. At the beginning of this loop, it is important to include a keyboard override to quit the program if needed.

**********************************************Creating Camera Capture**********************************************
`cap = cv2.VideoCapture(0)`

********************************************Reading Camera Capture********************************************
`_, img = cap.read()`

************************************Keyboard Override************************************
`if keyboard.is_pressed("z"):
  exit()`

************************************************Resizing************************************************
`img = cv2.resize(img, (x_size, y_size))`

********************************************************************************************************************************************Displaying the Image (DON’T DO THIS FOR THE FINAL RASPBERRY PI CODE)********************************************************************************************************************************************
`cv2.imshow("image", img)`
`cv2.waitKey(5)`

**********************************************************Specific Vision Documentation**********************************************************

[Object Vision](Vision%20b6111312d7ee4ffa927f3a6dadef6338/Object%20Vision%206ba307ce43fb4af8b06cf1898e87f029.md)

[Apriltag Vision](Vision%20b6111312d7ee4ffa927f3a6dadef6338/Apriltag%20Vision%20a7c6917116c348e5a794d51ffd18d8da.md)

[Camera on Shuffleboard](Vision%20b6111312d7ee4ffa927f3a6dadef6338/Camera%20on%20Shuffleboard%20f5870c5162fa4f10933f9e28f7ae75ed.md)