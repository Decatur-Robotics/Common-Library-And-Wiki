# Wait for Time

To wait for a certain amount of time, we must first make a LocalDateTime to track the start time, like so:

```java
public LocalDateTime lastSwapTime;
```

Then, to start our timer:

```java
lastSwapTime = LocalDateTime.now();
```

Finally, to check if the timer has completed:

```java
if(LocalDateTime.now().compareTo(lastSwapTime.plusSeconds(Constants.TIMER_DURATION)) == 1) {
	//Timer has ended if this runs
	lastSwapTime = LocalDateTime.now(); //Reset the timer
}
```