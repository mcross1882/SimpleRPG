# SimpleRPG
A simple text based RPG.

*Below are commands that have been implemented so far*

`buy [weapon|armor|item] [name]`

Purchase a given item. You must be in a store for this command to work

`sell [weapon|armor|item] [name]`

Sell a given item. You must be in a store for this command to work

`list [items|weapons|armor]`

List a given set of items in a store

`equip [weapon|armor] [name]`

Equip a given item from your inventory

`drop [weapon|armor|item] [name]`

Drop a weapon, armor, or item from the inventory

`use [item name]`

Apply or use a regular item in your inventory

`inventory [weapons|armor|items]`

Show the contents of the inventory. You can manually specify what to show by
putting the categories after the `inventory` command like so

`inventory armor items`

The above command will only show armor and items. If no categories are provided
then all inventory contents will be shown. `show` can also be used in place of `inventory`

`stats [strength|magic|stamina]`

Display a stat level. This command works similar to the `inventory` command and
can either display all or a partial list of stats

`goto [location]`

Goto a new location. To find available places use the `places` command

`roll`

Roll a dice

`places`

Display a list of stores you can goto

`where`

Display your current location

`leave|exit|quit`

Exit the game
