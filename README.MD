## Game Server and 2 Player Apps

# 1. Game Service

This service have end point's for
 - register presence of player
 - create a new game
 - get games with movement a player to play next
 - add movement to a particular game
 
When a new game/ movement is created, this service fire event with necessary details.

# 2. Player Services

Bother Player1 and Player2 work similar way. game-service should be running before starting player services.
This services register with player service and provide CLI menu to create new game, list games
This services automatically responds to any pending game movements.


# Notes

game-service publish same event for both consumers. consumers check and decide whether they have to process it or not
require Docker, since the game-service start testContainer of ActiveMQ for demo purpose
No services keep states in any db.
all services under same root project. 