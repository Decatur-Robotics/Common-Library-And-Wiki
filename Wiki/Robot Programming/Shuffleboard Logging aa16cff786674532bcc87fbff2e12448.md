# Shuffleboard Logging

First, in RobotContainer, create a static ShuffleboardTab to store our tab for logging:

```java
public static ShuffleboardTab shuffleboard = Shuffleboard.getTab("SmartDashboard");
```

We can access this from anywhere by doing RobotContainer.shuffleboard.

To log a value, we can do (Replace Double with the data type you want to log and only run this code once for the value):

```java
shuffleboard.addDouble("Value Name", ()->value);
```

[Input from Shuffleboard](Shuffleboard%20Logging%20aa16cff786674532bcc87fbff2e12448/Input%20from%20Shuffleboard%204b3f39b1dec04fd5b52a4d8e41eafada.md)