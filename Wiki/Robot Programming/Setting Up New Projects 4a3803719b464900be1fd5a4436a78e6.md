# Setting Up New Projects

Have one team member create a WPILib project, by clicking the WPILib icon (in the top right) and searching for create a new project. Select template, then Java, then Command Robot.
Once you’ve created the new project, copy the Phoenix.json and REVLib.json files from [here](https://github.com/Decatur-High-GlobalDynamics/FRC-Showbot/tree/master/vendordeps) into the vendordeps folder of the new project. Then, copy the following files from [here](https://github.com/Decatur-High-GlobalDynamics/FRC-Showbot/tree/master/src/main/java/frc/robot) into the src/main/java/frc/robot folder:
-ITeamTalon.java
-PidParameters.java
-Scalar.java
-TeamSparkMAX.java
-TeamTalonFX.java
-TeamUtils.java

Once you’ve copied over the files, publish the project to GitHub. Make sure to select Global Dynamics as the owning organization. Add a Dev branch based on the main branch. Then have the other team members clone the repository.