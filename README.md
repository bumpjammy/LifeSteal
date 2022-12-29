# LifeSteal

A Spigot plugin that allows users to play the classic LifeSteal gamemode

## Features
- Players can gain a heart when killing another player
- Players lose a heart when they are killed
- Players can withdraw hearts with the /withdrawheart command, which gives a special item which can then be redeemed for a heart
- Players are kicked from the game when they have no hearts left

## What Could Be Improved
- Create a separate class for managing player hearts rather than including the logic in the `EventManager` class
- Add more specific error messages when an `IOException` is thrown in the `changeHearts` and `getHearts` methods
- Add a way for players to gain hearts through gameplay (e.g. completing quests or defeating bosses)
- Add a config to customize things such as the starting hearts or the /withdrawheart item
- Add admin commands to manage a player's health
