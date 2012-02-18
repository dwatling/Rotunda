Rotunda v 20120218
by Dan Watling
-------------------

Rotunda is a very basic 2d game engine for Android. Many many features are missing, but the goal is to build a mature engine over time that could be used to create just about any simple 2d game very easily. 

#### Current features
* Basic animation support (scaling, translation, rotation, alpha)
* Events
* Sounds
* Actors
* Anchor points
 
#### TODO
* Examples and API docs
* Build a rendering layer for easy "swapping" of renderers
	* CanvasRenderer
	* OpenGLRenderer
* Animation interpolators
	* Bounce interpolater, etc.
* AnimationSequence should support concurrent animations. Currently it only supports sequential.
----

Rotunda is an Android library project. To use in your own Eclipse project:

1. Import Rotunda into Eclipse and open it.
2. Right click on your project, then choose properties
3. On the project settings dialog, select Android category
4. In the "Library" section click the "Add" button and choose Rotunda.

At the moment, there are no examples to go off of, so I'm afraid you're on your own until I get something together. There are bound to be bugs as this is still very early in its development.
