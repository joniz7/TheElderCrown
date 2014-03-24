TheElderCrown
=============

Howto add a new entity
----------------------
* Add a new entity type in the EntityType enum
* Create new model class:
    * Extend BottomEntity, MidEntity or TopEntity
    * Implement the copy() method
* Create a new view class
* Add a case to WorldView#propertyChange, which creates view
