Gephi-Plugin "Architectural GraphML" {#mainpage}
=====================

# Introduction {#Intro}
This plug-in supports an architectural interpretation of graphs. 
Graphs can be created with help of the groundplan, it adds an areal dimension to the graph with help of layouts and provides coloring mechanisms for representation possibilities.

The single functionalities are described in the next chapter.

# Functionality Overview {#Functionality}

## Graph Creator {#Creator}
The creation of graphs is one of the main functionalities of Gephi. The creator
is started from Gephi and has its own GUI. When started, the tool asks what
groundplan shall be loaded. Additionally, already existing nodes can be loaded into
the creator and displayed above the groundplan. The GUI itself looks like this:

<div style="float: left">
	![Graph Creator](GephiPic6.png)
</div>
<div style="clear:both"> </div>
\image latex GephiPic6.png "Graph Creator" width=13cm

On the top, there are three buttons to add rooms, edges and finish the capturing
process. To add a room, simply click on the button "New ROOM". The mouse cursor 
changes to a red circle when moving above the groundplan. Click in the middle
of the room that shall be captured and the tool asks for the roomtype. Choose the
type of the room and click on "Ok". The tool expects now the corners of this room,
this can be seen by the new mouse cursor symbol: a small blue circle. Click into
all corners of the room and finish the capturing process by using the right mouse
button or click on "Finish ROOM". The room is now captured and displayed in the
groundplan in slightly lighter colors. The id of the room is automatically retrieved
from Gephi and the room data is synchronized with Gephi (and already displayed).

To add an edge, click on the button "Add EDGE". The cursor changes to a red
circle with a "1" inside to indicate the starting of the edge. Click on one (already
captured) room center, the cursor will change to a red circle with a "2" inside. Click
on the second room and the tool will ask for the connection type. Select the corresponding 
connection type, the edge capturing is exited automatically.

The third button "Finish" has to be clicked at the end of the capturing process.
Although the data is already synchronized with Gephi, this button creates all necessary
previously defined attributes.

The tickbox "Fill rooms" fills the inside of all rooms. This is a help for the user to see
if all rooms have been captured or if there are still white spaces in the groundplan.
On the left side of the GUI, there is a list of all captured nodes and edges with its
id and roomtype. If one is selected, he can be deleted by clicking on the bottom
button "Remove Node" respectively "Remove Edge".

On the bottom of the GUI is a hint list that helps with the next step or confirms
the successful termination of the previous step.

And finally on the right side of the GUI, the previously selected groundplan of the
graph is displayed with all captured edges and nodes. This part allows zooming in
and out to improve the capturing process.

## History Function {#History}
The history functionality allows to backup the current configuration in Gephi to a
history. The stored configuration gets an id and a date. Additionally, a custom
description can be added if favored. If e.g. some changes shall be undone, a stored
configuration can be loaded by selecting the desired configuration and clicking on
"Load selected configuration". Moreover, the history can also be cleared.
Technically, when saving a configuration, the complete "*.gephi" file is stored into
a sub-directory of the main file (called "GephiHistory"). This is why the user needs
to have writing rights at the location of the file. In addition, a log file is generated
that stores the id, time and description.

Here is the UI:
<div style="float: left">
	![History Function](GephiPic1.png)
</div>
<div style="clear:both"> </div>
\image latex GephiPic1.png "History Function"  width=10cm

## Layout {#Layout}
The possibility to display layouts is integrated in Gephi and allows the programmer
to add a custom layout into the layout list. The layout for this plug-in supports two
visualization methods:

### Groundplan mode: {#GroundplanMode}
The "groundplan mode" adds the room geometry around the nodes. The node is
seen as center of the room and the room geometry is placed around the current
location of the node. In addition, the layout modifies the visual appearance of the
graph. It adds node and edge labels according to previous entered data. The edges
are coloured and its weight is set according to predefined values. 

The following figure shows an example with nodes before applying the layout (left) and after (right).

<div style="float: left">
	![Layout: Groundplan mode](GephiPic2.png)
</div>
<div style="clear:both"> </div>
\image latex GephiPic2.png "Layout: Groundplan mode"  width=14cm

Once the layout is stopped, the room geometry is removed. Only the labels and edge coloring remains.

### Editing mode: {#EditingMode}
The "editing mode" works the same as the previously described "groundplan mode",
but first moves the room nodes to its "original location" in the groundplan according
to the AGraphML-data. Other than that it works the same way as the "groundplan
mode". A visual display is shown here:

<div style="float: left">
	![Layout: Editing mode](GephiPic3.png)
</div>
<div style="clear:both"> </div>
\image latex GephiPic3.png "Layout: Editing mode"  width=13cm


## Visualization Helper {#Visualization}
There are two helper tools implemented that optimize the graphical evaluation of the graphs.

### Colorizer {#Colorizer}
The "Colorizer" colors the nodes according to its attribute characteristics. The UI is shown in the following figure:

<div style="float: left">
	![UI of Colorizer](GephiPic4.png)
</div>
<div style="clear:both"> </div>
\image latex GephiPic4.png "UI of Colorizer"  width=10cm

The left drop down field allows to choose an attribute, its occurring characteristics are shown in the list below. A color can be chosen by double clicking on the corresponding list item in the left list. 
Some predefined coloring templates exist. They can be set in the right drop down list. A reset sets all nodes and edges to black.

### WeightManager {#WeightManager}
The weight manager sets the weight of the edges and therewith the thickness of the edges in the visualization of the graph. It works according to the same principals as the Colorizer. 
Note that useful thickness increments are between 0 and 1.
Its UI looks like this:

<div style="float: left">
	![UI of WeightManager](GephiPic5.png)
</div>
<div style="clear:both"> </div>
\image latex GephiPic5.png "UI of WeightManager"  width=10cm

## Exporter {#Exporter}
To export the data, an internal exporter to the GraphML-format is used. As the final AGraphML-fileformat was only defined at the end of the development process, the exported data does not fit the AGraphML-specification.
The connection to the Neo4J-database is also not implemented and data can't be exported to Neo4J. The connection to the "ar:searchbox" is also only manually possible.

# Installation {#Install}
For installation instructions, refer to \"install.md\".
Note that JDK version 7 is necessary to run Gephi.

# Architecture {#Architecture}
The architecture is described in a separate documentation that can be found under http://gitlab.ai.ar.tum.de/ksd-research-group/ksd-documentation-gephi-grasshopper-agraphml-plugins

# Directories {#Directories}
- __RefMan.html__:

  This help file as html

- __RefMan.pdf__:

  This help file as pdf

- __README.md__:

  Short introduction of this project for the Gitlab main page

- __install.md__:

  Installation instruction of this project

- __build.xml__:

  File from NetBeans project

- __manifest.mf__:

  File from NetBeans project
  
- __source__:

  Folder for source files of this project
  
- __nbproject__:
  
  Project files from NetBeans

- __doc__:

  Contains all components like descriptions and graphics for publication and documentation of this software

  + __Main_en.md__:

    Contains the user documentation of this system in English

  + __Doxyfile__:

    Doxygen-Configuration file

  + __make.bat__:

    Batch file that starts the document generation with doxygen and MiXteX

  + __pictures__:

    Contains all used graphics

  + __publication__:
  
    Contains all necessary files for publishing the plug-in at the Gephi marketplace
	
  + __install__:
  
    Contains the installation instructions as DOCX and PDF file

=====================
# Publication {#Publication}
This plug-in has been published in the Gephi Marketplace and can be found under https://marketplace.gephi.org/plugin/architectural-graphml/
