# NetworkTables

| Function | Python | Java |
| --- | --- | --- |
| Send to Table | rioComms.send(Table, Key, Value) |  |
| Get from Table |  | TeamUtils.getFromNetworkTables(Table, Key) |

[List of Tables](NetworkTables%206da30219f0c44b7181f37ae2999e1b1e/List%20of%20Tables%205a1a755196bd421ab17a9b8ba3e8b8eb.md)

******************************What are NetworkTables?******************************
NetworkTables is a tool we use to communicate from the Pi to the Rio. We could use it to communicate between any two devices, but that’s our main use case. You set up tables with values (each has a specific key). 

******************************Reading the data:******************************
When you run the code under the function “Get from Table”, here’s how it works:

The parameters “Table” and “Key” are strings corresponding to the table (ex. apriltags) and key (tag1 X position).