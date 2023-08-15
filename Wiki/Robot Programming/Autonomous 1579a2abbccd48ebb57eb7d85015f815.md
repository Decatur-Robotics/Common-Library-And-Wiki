# Autonomous

****************************Creating an Autonomous****************************

1. Navigate to RobotContainer.java
2. Create a new, private final variable of type Command. This will store our autonomous.
    
    ```java
    private final Command lowBalance
    ```
    
3. Set the command to a new instance of a pre-existing command.
    
    ```java
    private final Command lowBalance = 
        new SetElevatorTargetCommand(Constants.bottomElevatorTargetPosition, 
    			true, elevator);
    ```
    
4. Now you have a complete autonomous 

************************************************************Putting autonomous choices into shuffleboard for match selection************************************************************

See [Shuffleboard Logging](Shuffleboard%20Logging%20aa16cff786674532bcc87fbff2e12448.md) for more information regarding Shuffleboard and how we log data

1. Create a new variable of type SendableChooser<Command>, and set it equal to a new sendable chooser. This allows us to select different options in shuffleboard
    
    ```jsx
    SendableChooser<Command> autoChooser = new SendableChooser<>()
    ```
    
2. Create a method inside [RobotContainer.java](http://RobotContainer.java) called addAutoChoicesToGui, like so:
    
    ```java
    private void addAutoChoicesToGui()
    ```
    
3. For the default autonomous option, inside addAutoChoicesToGui, do:
    
    ```java
    autoChooser.setDefaultOption("Default Name", defaultAutonomous);
    ```
    
4. For each other autonomous option, do the following:
    
    ```java
    autoChooser.addOption("Autonomous Name", autonomousCommand);
    ```
    
5. Then, at the end of addAutoChoicesToGui, do:
    
    ```java
    shuffleboard.add(autoChooser);
    ```
    
    This does assume that shuffleboard is a ShuffleboardTab. ShuffleboardTab is the class that represents a tab on Shuffleboard.
    
6. Lastly, add the following method to RobotContainer.java:
    
    ```java
    public Command getAutonomousCommand() {
    	return autoChooser.getSelected();
    }
    ```