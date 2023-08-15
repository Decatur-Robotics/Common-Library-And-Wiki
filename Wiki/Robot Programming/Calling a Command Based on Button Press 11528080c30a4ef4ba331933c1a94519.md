# Calling a Command Based on Button Press

Buttons on the logitech controller correspond to integers. We name these ints with their buttons in the LogitechControllerButtons class. Make sure you have this class inside the project.

We assign button bindings (to control robot elements) inside the RobotContainer class. 
Buttons can be bound to either the primary or secondary controller. Put your bindings under the corresponding method, either configurePrimaryBindings( ), or configureSecondaryBindings( ).

To initialize a button use the type “JoystickButton” and define it as a new JoystickButton constructor, and pass in the controller the button will use, and the button’s identity from LCB (logitech controller buttons)

```jsx
JoystickButton a = new JoystickButton(secondaryController, LogitechControllerButtons.a);
```

For D-pad inputs be sure to use “POVButton” instead as the type and the constructor. These are still under LCB, so don’t worry about adding more files.

```jsx
POVButton up = new POVButton(secondaryController, LogitechControllerButtons.up);
```

After defining the button, assign a command to the button you want to run when it is either on press (onTrue), button released (onFalse), while pressed (whileTrue), and while not pressed (whileFalse).

On press: when you first press the button, the command is called once. While pressed: the command is called continuously while the button is held down. 

For example, the following buttons are assigned to separate commands:

```jsx
a.onTrue(new ShootBallCommand(0, shooter));
up.onFalse(new SetElevatorTargetCommand(Constants.topElevatorTargetPosition, elevator));
```