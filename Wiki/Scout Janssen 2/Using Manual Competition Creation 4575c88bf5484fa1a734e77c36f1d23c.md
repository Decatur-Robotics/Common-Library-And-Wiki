# Using Manual Competition Creation

- If you get errors connecting to the database, make sure that your IP is allowed in the DB auth settings. When adding your IP, set it to only allow for 6 hours.
- Before starting, get the .env file from either @Renato Dell'Osso IV  or @Avyn Ebersold. It goes inside the SJ2CompGenerator folder. ************************************************Keep the .env out of Git!************************************************
- If you’re having issues, talk to @Renato Dell'Osso IV, or @Avyn Ebersold if Renato can’t be reached.
- Clone the repository from here:

[https://github.com/Decatur-High-GlobalDynamics/SJ2CompGenerator](https://github.com/Decatur-High-GlobalDynamics/SJ2CompGenerator)

- Second, inside SJ2CompGenerator, run:

```jsx
npm run dev
```

- Then, once you navigate to localhost:3000, you’ll see the following fields:
    
    ![Untitled](Using%20Manual%20Competition%20Creation%204575c88bf5484fa1a734e77c36f1d23c/Untitled.png)
    
- Fill in “Competition Name” with what you want the competition to be see as by scouters (ex. Carrollton Qualifier 2023)
- Fill in “TBA ID” with the ID of the competition from The Blue Alliance (can be found in URLs like the following)

```jsx
https://www.thebluealliance.com/event/2023gacar
```

- The third field is the Team ID, and can be found in the database. If you need to change this, change the NEXT_PUBLIC_TEAM_ID in the .env file. ****************************************This is autofilled, so you can leave it blank.****************************************
- The fourth field is the Season ID, and can be found in the database. It’s easiest to just go change the NEXT_PUBLIC_SEASON_ID variable in the .env, as the website autofills from this. ****************************************This is autofilled, so you can leave it blank.****************************************
- Finally, set the number of matches. You’ll need to enter 6x the number of teams you have matches. For example, for a competition with 20 matches, you’d need to enter 120 team numbers. It’s recommended to get a second person to read you the schedule as you enter the team numbers.
- When done entering the team numbers, click “Create Competition” **(only once)** and check the console to make sure it worked.