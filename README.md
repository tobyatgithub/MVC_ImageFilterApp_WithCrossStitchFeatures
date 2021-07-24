# Final Project: Cross Stitch with GUI
### 1. About
In this project, we extend our previous ImageModel project (project 3) and 
ImageController project (project 4). 
This time, we make it into a full MVC **Cross-Stitch pattern Application**. In 
this app, user can choose three modes:
1. process via a pre-defined text file
2. process via an interactive command line terminal
3. process via an interactive GUI
More info about Cross Stitch
[click here](https://en.wikipedia.org/wiki/Cross-stitch).

### 2. List of features
(new features)  
- View (GUI)!
  - A menu bar with all image-processing and cross-stitch features.
  - The image that is being processed is displayed on the screen. 
  - User can exchange one color for another in a cross-stitch pattern 
    by clicking on the color in a displayed pattern and allowing the 
    user to select a different color from the DMC color options.
  - User can pick one color in a cross-stitch pattern that will then 
    be removed from the pattern completely.
  - User can specify the image to be loaded and saved via a pop out 
    file chooser.
  - User can interactively create and execute a batch-script as part 
    of the program (via the `BatchInput` Menu)
  - When the user specifies an operation, its result is displayed on 
    the GUI.

(old features from last part.)
- Include all the features from part 1 of this assignment including blur,
  sharpen, grayscale, sepia, and dithering in the controller by implementing
  command design pattern.
- Create mosaic and pixelations of images.
- Create a cross-stitch pattern for an image.
- loading and saving images through the controller (via a txt file.)
- filtering images: apply different filters on a given image.
  - image blur: blur the image to reduce resolution.
  - image sharpening: accentuates edges to make an image look sharper.
  - color transform: modifies the color of a pixel based on its own color.
  - greyscale: convert a color image into a greyscale image.
  - sepia tone: add a characteristic reddish brown tone to a color image
  - reduce color density: reduce the number of colors in the image.

### 3. How to run

1. to start the GUI from jar file:  
   clone this repo, and then in terminal
```bash
cd res/
java -jar Project06-CrossStitchMVC_withFeature.jar -interactive
```
2. to start the interactive terminal from jar file:
```bash
cd res/
java -jar Project06-CrossStitchMVC_withFeature.jar -command
```
3. to process a predefined text script from jar file:
```bash
cd res/
java -jar Project06-CrossStitchMVC_withFeature.jar -script <pathToScript>
```

### 4. How to use the program
Start the program via the one of the three methods specified in the 
`How to run` session.  
The GUI one is the most easy and user friendly choice.  
After you activated the GUI, there are menu and buttons on it where
you can choose to load image, save image, and apply different image
effects onto it.  
You can also find example images in the `/res` folder.  
[EXAMPLE IMAGE TO ADD INTO README]  
[EXAMPLE VIDEO TO ADD INTO README]

### 5. Description of example runs
In the `/res` folder you will find a few screen shots of the GUI part 
of this program.  
- The `displayOriginal.jpg` shows you what the GUI will look like after
you click the `OpenImage` button or `File -> Open Image` via menu bar.
- Next, the `pixelationEffect.png` shows you the result after you apply 
  the Pixelation effect onto the example image we loaded in last step.
  You can apply this effect via `Effect -> Pixelation` via menu bar.
- Then, the `generatePattern.png` shows you the result after you apply 
  generate-pattern effect onto the image in last step. You can do
  this by `Cross Stitch -> Generate Pattern` via menu bar. 
- Lastly, `textBatchInterface.png` shows you the GUI for processing text
  commands. Just as if you are doing it in terminal!


### 6. Project design meeting update
1. I refactored my whole design to adapt to the `Feature` design in the 
   full mvc example. I wasn't very convinced at first but gradually found
   it is quite handy.
   
2. I also separated different controllers for different scenarios to apply 
   the S (single responsibility principle). In the initial design, there were 
   only one controller but now I have three (one for the image GUI, one for the 
   commands, and one for the text command GUI.)
   
3. I also added specific view class for text GUI.
4. More tests are added targeting each different controllers.
5. Mock models and views have been moved to `/test` folder
6. Added `nullPointChecker` to all the methods with inputs.
7. Modified and filled javadoc for all the interfaces, classes, and public methods.
8. Modified and reflected on the previous project feedbacks (such as tests,
   checkers, and return types.)

### 7. Assumptions
Here we have plenty of hidden assumption. For example:
- We assume that you have to apply pixelation before you can generate 
  a cross stitch pattern. The app will warn the user if not done so.
- We assume that you can only replace color by another dmc color (which
  is a small subset of all RGB color space.)
- We also assume that you will need to have valid image file to load (
  for example, png, jpg, jpeg, etc.) Not every single kind of image file is
  supported.
- We assume that all the image we will deal with are 3 dimensional. 
  More specifically, are all RGB based colored images.  
- We assume that the user can handle utf-8 and utf-16 characters with their 
  editors to view the result of patternGeneration correctly.


### 8. Limitations 
This project contains many limitations that I wish I could have more time to
solve and improve. For example:
1. The color choose panel for changing color is not ideal (I want a grid pane 
   instead of a drop down list.)
2. Many parts of the extra credit stuff are still missing or is only in the form
   of minimum viable status...
   - for example, the add overlay text asks four question via four different pop
    outs, I can combine them into one.
   - the procesdure of adding the overlay text into the text pattern is somewhat 
     convoluted... could be simplified.
   - show symbols on the super pixels is close but still a little bit off due to
    symbol choices (some ones are longer than the others.) It will work perfectly
     if I could have time to unify the symbol I choose.
3. The program will expect the user to provide a batch txt file nicely. 
   No security measurement has been considered here.

4. Dithering is by default enforced to 8 color dithering because it will 
   take 3 separate inputs (which is totally doable...just I don't have 
   enough time this week.)
   

### 9. Image Copyright
The images are either my own or are free for commercial and non-commercial 
use from unsplash.com